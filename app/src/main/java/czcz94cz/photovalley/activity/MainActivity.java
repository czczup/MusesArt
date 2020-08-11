package czcz94cz.photovalley.activity;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.gyf.immersionbar.ImmersionBar;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


import czcz94cz.photovalley.R;
import tyrantgit.explosionfield.ExplosionField;
import czcz94cz.photovalley.utils.Utility;
import czcz94cz.photovalley.features.imagepickersheet.ImagePickerSheetView;
import czcz94cz.photovalley.features.albumpicker.AlbumPickerActivity;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends AppCompatActivity{

    public static final int TAKE_PHOTO = 0x10;
    public static final int CHOOSE_PHOTO_FROM_ALBUM = 0x11;
    private static final int CAMERA_PERMISSION = 0x12;
    private static final int ALBUM_PERMISSION = 0x13;
    private ExplosionField mExplosionField;
    private ImageView civTakePhoto;
    private ImageView civOpenAlbum;
    private BottomSheetLayout mBottomSheetLayout;
    private File mCameraFile = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableAPIDialog();
        initImmersionBar();
        setContentView(R.layout.activity_main);
        civTakePhoto = findViewById(R.id.take_photo);
        civOpenAlbum = findViewById(R.id.open_album);
        mBottomSheetLayout = findViewById(R.id.bottomsheet);
        mBottomSheetLayout.setPeekOnDismiss(true);
        mExplosionField = ExplosionField.attach2Window(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Utility.reSetView(civTakePhoto);
        Utility.reSetView(civOpenAlbum);

        //拍照
        civTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExplosionField.explode(v);
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSION);
                } else { //有权限直接调用系统相机拍照
                    openCamera();
                }
            }
        });

        //从相册选择
        civOpenAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExplosionField.explode(v);
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, ALBUM_PERMISSION);
                } else {
                    showSheet();
                }
            }
        });

        //监听弹出菜单状态
        mBottomSheetLayout.addOnSheetStateChangeListener(new BottomSheetLayout.OnSheetStateChangeListener(){
            @Override
            public void onSheetStateChanged(BottomSheetLayout.State state) {
                if (state == BottomSheetLayout.State.HIDDEN) {
                    Utility.reSetView(civOpenAlbum);
                }
            }
        });
    }

    /**
     * 反射 禁止弹窗
     */
    private void disableAPIDialog(){
        if (Build.VERSION.SDK_INT < 28)return;
        try {
            Class clazz = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
            currentActivityThread.setAccessible(true);
            Object activityThread = currentActivityThread.invoke(null);
            Field mHiddenApiWarningShown = clazz.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                Log.d("MainActivity", "onActivityResult: "+resultCode+","+data);
                if (resultCode == RESULT_OK) {
                    Uri photoUri = Uri.fromFile(mCameraFile);
                    if (photoUri != null) {
                        notifyMediaUpdate(mCameraFile);
                        Intent intent = new Intent(this, EditActivity.class);
                        intent.putExtra("image", photoUri.toString());
                        startActivity(intent);
                    }
                }
                break;
            case CHOOSE_PHOTO_FROM_ALBUM:
                //从相册选择图片
                if (resultCode == Activity.RESULT_OK && data != null) {
                    String result = data.getStringExtra(AlbumPickerActivity.SELECT_RESULTS);
                    if (result != null) {
                        Uri selectedImage = Uri.parse(result);
                        Intent intent = new Intent(MainActivity.this, EditActivity.class);
                        intent.putExtra("image", selectedImage.toString());
                        startActivity(intent);
                    }
                }
                break;
            default:
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ALBUM_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showSheet();
                } else {
                    Toast.makeText(this, getString(R.string.denied_permission), Toast.LENGTH_SHORT).show();
                }
                break;
            case CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, getString(R.string.denied_permission), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }


    public void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            mCameraFile = Utility.createImageFile();
            Uri imageUri = Utility.getFileUri(MainActivity.this,
                    "czcz94cz.photovalley.fileprovider", mCameraFile);
            Log.d("MainActivity", "openCamera: "+imageUri);
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, TAKE_PHOTO);
        }

    }

    public void showSheet() {
        ImagePickerSheetView sheetView = new ImagePickerSheetView.Builder(this)
                .setMaxItems(33)
                .setShowCameraOption(false)
                .setShowPickerOption(true)
                .setImageProvider(new ImagePickerSheetView.ImageProvider() {
                    @Override
                    public void onProvideImage(ImageView imageView, Uri imageUri, int size) {
                        RequestOptions options = new RequestOptions()
                                .centerCrop();
                        Glide.with(MainActivity.this)
                                .load(imageUri)
                                .apply(options)
                                .transition(withCrossFade())
                                .into(imageView);
                    }
                })
                .setOnTileSelectedListener(new ImagePickerSheetView.OnTileSelectedListener() {
                    @Override
                    public void onTileSelected(ImagePickerSheetView.ImagePickerTile selectedTile) {
                        mBottomSheetLayout.dismissSheet();
                        if (selectedTile.isPickerTile()) {
                            startActivityForResult(new Intent(MainActivity.this, AlbumPickerActivity.class), CHOOSE_PHOTO_FROM_ALBUM);
                        } else if (selectedTile.isImageTile()) {
                            Intent intent = new Intent(MainActivity.this, EditActivity.class);
                            intent.putExtra("image", selectedTile.getImageUri().toString());
                            startActivity(intent);
                        }
                    }
                })
                .setTitle("请选择一张照片")
                .create();
        mBottomSheetLayout.showWithSheetView(sheetView);
    }

    public void notifyMediaUpdate(File file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    private void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .init();
    }
}

