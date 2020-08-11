package czcz94cz.photovalley.features.albumpicker;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by VanXN on 2018/3/8.
 */

public class AlbumPickerConfig extends AppCompatActivity{

    protected static boolean isMultiSelect = false;
    protected static boolean isShowGif = false;
    protected static int maxSize = 2;//最大选择数量,当是多选的时候,单选直接忽略

    public static boolean isIsMultiSelect() {
        return isMultiSelect;
    }

    public static void setIsMultiSelect(boolean isMultiSelect) {
        AlbumPickerConfig.isMultiSelect = isMultiSelect;
    }

    public static boolean isIsShowGif() {
        return isShowGif;
    }

    public static void setIsShowGif(boolean isShowGif) {
        AlbumPickerConfig.isShowGif = isShowGif;
    }

    public static int getMaxSize() {
        return maxSize;
    }

    public static void setMaxSize(int maxSize) {
        AlbumPickerConfig.maxSize = maxSize;
    }
}
