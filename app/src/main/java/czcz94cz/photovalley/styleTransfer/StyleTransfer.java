package czcz94cz.photovalley.styleTransfer;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;

import com.xiaomi.mace.MaceJni;

import java.nio.FloatBuffer;

import czcz94cz.photovalley.common.AppConfig;
import czcz94cz.photovalley.common.Configuration;
import czcz94cz.photovalley.common.ModelConfig;
import czcz94cz.photovalley.common.ModelInfo;
import czcz94cz.photovalley.utils.ImageSaveUtils;

public class StyleTransfer {
    private static final String MODEL_GRAPH_FILE_EXTENSION = ".pb";
    private static final String MODEL_WEIGHT_FILE_EXTENSION = ".data";

    private Configuration config;
    // input float array

    public static StyleTransfer instance = new StyleTransfer();

    private StyleTransfer() {}

    public int initialize(AssetManager assetManager, final Configuration config) {
        this.config = config;
        ModelConfig modelConfig = config.getModelConfig();
        ModelInfo modelInfo = modelConfig.getModelInfo();
        AppConfig appConfig = config.getAppConfig();
        if (modelConfig.getDeviceType().compareTo("GPU") == 0) {
            int status = MaceJni.createGPUContext(appConfig.getStoragePath());
            if (status != 0) {
                Log.e("Transfer Initialize", "Create GPU Context failed");
                return status;
            }
        }
        int status = MaceJni.createEngine(assetManager, modelInfo.getFilename(),
                appConfig.getOmpNumThreads(), appConfig.getCpuAffinityPolicy(),
                appConfig.getGpuPerfHint(), appConfig.getGpuPriorityHint(),
                modelInfo.getFilename() + MODEL_GRAPH_FILE_EXTENSION,
                modelInfo.getFilename() + MODEL_WEIGHT_FILE_EXTENSION,
                modelInfo.getInputNames(), modelInfo.getOutputNames(),
                modelConfig.getModelInputShape(0), modelConfig.getModelOutputShape(0),
                modelConfig.getDeviceType());
        return status;
    }


    public Bitmap transfer(Bitmap bitmap) {
        FloatBuffer floatBuffer = preProcess(bitmap);
        long startTime = SystemClock.uptimeMillis();
        float[] transferData = MaceJni.inference(config.getModelConfig().getModelName(), floatBuffer.array());
        floatBuffer.clear();
        floatBuffer = null;
        long inferenceTime = SystemClock.uptimeMillis() - startTime;
        Log.d("StyleTransfer", "transfer time: "+inferenceTime);
        Bitmap result = ImageSaveUtils.getOutputImage(transferData, bitmap.getWidth(), bitmap.getHeight());
        transferData = null;
        return result;
    }

    public void deleteEngine() {
        MaceJni.deleteEngine(config.getModelConfig().getModelName());
    }

    private FloatBuffer preProcess(Bitmap bitmap) {
        int[] colorValues = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(colorValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        float[] floatValues = new float[bitmap.getWidth() * bitmap.getHeight() * 3];
        FloatBuffer floatBuffer = FloatBuffer.wrap(floatValues, 0, bitmap.getHeight() * bitmap.getWidth() * 3);
        floatBuffer.rewind();
        for (int i = 0; i < colorValues.length; i++) {
            floatBuffer.put((colorValues[i] >> 16) & 0xFF);
            floatBuffer.put((colorValues[i] >> 8) & 0xFF);
            floatBuffer.put((colorValues[i] & 0xFF));
        }
        colorValues = null;
        floatValues = null;
        return floatBuffer;
    }

}

