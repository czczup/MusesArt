package czcz94cz.photovalley.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

public class ImageSaveUtils {
    //保存文件到指定路径
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 保存图片目录 （根目录下XXX）
        File appDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/photovalley");
        if (!appDir.exists()) {
            Boolean isSuccess= appDir.mkdirs();
        }
        // 文件名
        String fileName = "download_"+System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Bitmap getOutputImage(float[] output, int width, int height){
        int outputWidth = width;
        int outputHeight = height;
        Bitmap bitmap = Bitmap.createBitmap(outputWidth, outputHeight, Bitmap.Config.ARGB_8888);
        int [] pixels = new int[outputWidth * outputHeight];
        for (int i = 0; i < outputWidth * outputHeight; i++) {
            pixels[i] = 0xFF << 24 | (int)output[i*3] << 16 | (int)output[i*3+1] << 8 | (int)output[i*3+2];
        }
        bitmap.setPixels(pixels, 0, outputWidth, 0, 0, outputWidth, outputHeight);
        pixels = null;
        return bitmap;
    }
}
