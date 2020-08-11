package czcz94cz.photovalley.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.sdsmdg.tastytoast.TastyToast;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import czcz94cz.photovalley.R;
import czcz94cz.photovalley.adapter.EditAdapter;
import czcz94cz.photovalley.common.Configuration;
import czcz94cz.photovalley.common.ModelConfig;
import czcz94cz.photovalley.model.Block;
import czcz94cz.photovalley.model.Filter;
import czcz94cz.photovalley.model.Preprocessing;
import czcz94cz.photovalley.styleTransfer.ModelSelector;
import czcz94cz.photovalley.styleTransfer.StyleTransfer;
import czcz94cz.photovalley.utils.ImageSaveUtils;
import czcz94cz.photovalley.utils.OpenCVUtils;
import czcz94cz.photovalley.styleTransfer.StyleTransferUtils;


public class EditActivity extends AppCompatActivity {
    private EditAdapter editAdapter;
    private String originalImageString;
    private String originalImagePath;
    private ImageView imageView;
    private List<Filter> filters = new ArrayList<>();
    private ProgressDialog progressDialog;
    private Map<Integer, Bitmap> map = new HashMap<>();
    private TextView showIntensity;

    private Bitmap originalBitmap; // 简单压缩后的图像
    private List<Mat> matInputList; // 处理后的图像
    private Bitmap stylizedBitmap; // 风格化的图像
    private Bitmap mixedBitmap; // 风格强度混合的图像
    private List<Block> blockList; // 乱序的块列表
    private float posX;
    private float currentPosX;
    private int alpha = 255;
    private String mode = "low"; // 默认为低分辨率模式
    private Handler runThread;
    private Lock lock = new ReentrantLock();
    private static final String STORAGE_DIRECTORY = "mace_styleTransfer_demo";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initHandler();
        initImmersionBar();
        loadOpenCV();
        initToolBar();
        initImage();
        initData();
        initRecyclerView();
    }

    private void initHandler() {
        HandlerThread thread = new HandlerThread("initThread");
        thread.start();
        runThread = new Handler(thread.getLooper());
    }

    private void loadOpenCV() { //初始化
        if (OpenCVLoader.initDebug()) {
            Log.d("opencv", "loadOpenCV: OpenCVLoader初始化成功");
        }
    }

    private void initData() {
        filters = ModelSelector.getModelInfo(originalImageString);
    }

    private void initDialog(String filtername) {
        progressDialog = new ProgressDialog(EditActivity.this);
        progressDialog.setTitle(filtername+"正在绘制中...");
        progressDialog.setMessage("稍等片刻，艺术触手可及");//对话框消息
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    private void initRecyclerView() {
        editAdapter = new EditAdapter(R.layout.edit_item, filters);
        editAdapter.openLoadAnimation();
        editAdapter.setNotDoAnimationCount(3);
        editAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Filter filter = filters.get(position);
                if (filter.getId() == 0) { // 原图
                    loadImage(originalBitmap, filter.getId());
                } else {
                    if (map.containsKey(filter.getId())) { // 如果已经处理过了
                        loadImage(map.get(filter.getId()), filter.getId());
                    } else { // 未处理过的图像
                        initDialog(filter.getName());
                        requestStyleTransfer(mode, filter.getId());
                    }
                }
            }
            private void requestStyleTransfer(String mode, int filterId) {
                runThread.post(new Runnable() {
                    @Override
                    public void run() {
                        initMace(filterId);
                        if (mode.equals("low")) {
                            stylizedBitmap = StyleTransferUtils.stylizeLowResolution(matInputList, originalBitmap);
                        } else {
                            stylizedBitmap = StyleTransferUtils.styleTransferOtherResolution(matInputList, blockList, originalBitmap);
                        }
                        map.put(filterId, stylizedBitmap); // TODO:缓存
                        loadImage(stylizedBitmap, filterId);
                    }
                });
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(editAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayout.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void initMace(int filterId) {
        lock.lock();
        ModelConfig modelConfig = ModelSelector.select(filterId);
        if (modelConfig == null) {
            Log.e("StyleTransfer", "Initialize failed: there is no proper model to use");
        } else {
            Configuration config = new Configuration(STORAGE_DIRECTORY, modelConfig);
            int status = StyleTransfer.instance.initialize(getAssets(), config);
            if (status != 0) {
                Log.e("Transformation", "Initialize failed: create engine failed");
            } else {
                Log.i("Transformation", "Initialize successful");
            }
        }
        lock.unlock();
    }

    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
        toolbar.inflateMenu(R.menu.edit_menu);
        toolbar.inflateMenu(R.menu.mode_menu);
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.low_resolution));

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.download) {
                    Log.d("save", "onMenuItemClick: 正在保存");
                    if (stylizedBitmap != null) {
                        saveImage();
                    } else {
                        TastyToast.makeText(getApplicationContext(), "请勿保存原图", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                } else if (item.getItemId() == R.id.low_resolution) {
                    TastyToast.makeText(getApplicationContext(), "低分辨率模式", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.low_resolution));
                    mode = "low";
                    originalBitmap = OpenCVUtils.resize(originalImagePath, mode);
                    matInputList = StyleTransferUtils.preprocessingOfLowResolution(originalBitmap);
                    map = new HashMap<>(); // 清空缓存
                    stylizedBitmap = null;
                    showOriginalImage();
                } else if (item.getItemId() == R.id.mid_resolution) {
                    TastyToast.makeText(getApplicationContext(), "中分辨率模式", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.mid_resolution));
                    mode = "mid";
                    originalBitmap = OpenCVUtils.resize(originalImagePath, mode);
                    Preprocessing result = StyleTransferUtils.preprocessingOfOtherResolution(originalBitmap);
                    matInputList = result.getMatInputList();
                    blockList = result.getBlockList();
                    map = new HashMap<>(); // 清空缓存
                    stylizedBitmap = null;
                    showOriginalImage();
                } else if (item.getItemId() == R.id.high_resolution) {
                    TastyToast.makeText(getApplicationContext(), "高分辨率模式", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.high_resolution));
                    mode = "high";
                    originalBitmap = OpenCVUtils.resize(originalImagePath, mode);
                    Preprocessing result = StyleTransferUtils.preprocessingOfOtherResolution(originalBitmap);
                    matInputList = result.getMatInputList();
                    blockList = result.getBlockList();
                    map = new HashMap<>(); // 清空缓存
                    stylizedBitmap = null;
                    showOriginalImage();
                }
                return false;
            }
        });
    }

    private void showOriginalImage() {
        Glide.with(this)
                .load(Uri.parse(originalImageString))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }

    private void saveImage() {
        if (mixedBitmap != null) {
             ImageSaveUtils.saveImageToGallery(EditActivity.this, mixedBitmap);
        } else {
             ImageSaveUtils.saveImageToGallery(EditActivity.this, stylizedBitmap);
        }
        TastyToast.makeText(getApplicationContext(), "保存成功", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
    }

    private Bitmap tweakStyleIntensity(int number) {
        if (stylizedBitmap != null && originalBitmap != null) {
            int width = originalBitmap.getWidth();
            int height = originalBitmap.getHeight();
            Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newBitmap);
            canvas.drawBitmap(originalBitmap, 0, 0, null);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setAlpha(number);
            canvas.drawBitmap(stylizedBitmap, 0, 0, paint);
            canvas.save();
            canvas.restore();
            mixedBitmap = newBitmap;
            return newBitmap;
        }
        return originalBitmap;
    }

    private void updateStyleIntensity(double move) {
        Log.d("move", "updateStyleIntensity: "+move);
        alpha += (int)(move/500*255);
        alpha = alpha < 0 ? 0 : alpha;
        alpha = alpha > 255 ? 255 : alpha;

        Bitmap bitmap = tweakStyleIntensity(alpha);
        imageView.setImageBitmap(bitmap);
        showIntensity.setText("风格强度： +"+(int)(alpha/255.0*100));
        showIntensity.setVisibility(View.VISIBLE);
        showIntensity.animate()
                .alpha(1f)
                .setDuration(200)
                .setListener(null);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                EditActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showIntensity.animate()
                                .alpha(0f)
                                .setDuration(2000)
                                .setListener(null);
                    }
                });
            }
        };
        timer.schedule(task, 2000);
    }

    private void initImage() {
        showIntensity = findViewById(R.id.show_intensity);
        imageView = findViewById(R.id.large_image_view);
        originalImageString = getIntent().getStringExtra("image");

        Glide.with(this)
                .load(originalImageString)
                .into(imageView);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                double move_x = 0;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        posX = event.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        currentPosX = event.getX();
                        move_x = currentPosX - posX;
                        if (stylizedBitmap != null) {
                            if (move_x > 0) {
                                updateStyleIntensity(move_x);
                                Log.e("touch", "向右"+(currentPosX - posX));
                            } else if (move_x < 0) {
                                updateStyleIntensity(move_x);
                                Log.e("touch", "向左"+(currentPosX - posX));
                            }
                        }
                        posX = currentPosX;
                        break;
                    case MotionEvent.ACTION_UP: break;
                    default: break;
                }
                return true;
            }
        });
        if (originalImageString.startsWith("/")) {
            originalImageString = "file://" + originalImageString;
            originalImageString = originalImageString.replace(" ", "%20");
        }
        try {
            Log.d("EditActivity", "initImage: "+originalImageString);
            File file = new File(new URI(originalImageString));
            originalImagePath = file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }

        originalBitmap = OpenCVUtils.resize(originalImagePath, "low"); // 进入时默认为低分辨率模式
        matInputList = StyleTransferUtils.preprocessingOfLowResolution(originalBitmap);
    }

    private void loadImage(Bitmap bitmap, int filterId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
                progressDialog.cancel();
            }
        });
        if (filterId == 0) {
            stylizedBitmap = null;
            mixedBitmap = null;
        } else {
            stylizedBitmap = bitmap;
            mixedBitmap = null;
            alpha = 255;
        }
    }

    private void initImmersionBar() {
        ImmersionBar.with(this)
                .barColor(R.color.white)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .init();
    }
}
