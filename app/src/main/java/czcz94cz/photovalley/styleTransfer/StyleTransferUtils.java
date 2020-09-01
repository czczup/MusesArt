package czcz94cz.photovalley.styleTransfer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.os.SystemClock;
import android.util.Log;

import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import czcz94cz.photovalley.model.Block;
import czcz94cz.photovalley.model.Preprocessing;
import czcz94cz.photovalley.utils.OpenCVUtils;
import czcz94cz.photovalley.utils.Utility;

public class StyleTransferUtils {

    public static Bitmap stylizeLowResolution(List<Mat> matInputList, Bitmap originalBitmap) {
        Mat stylizedMat = styleTransfer(matInputList).get(0);
        Bitmap result = postprocessingOfLowResolution(stylizedMat, originalBitmap);
        return result;
    }

    public static Bitmap postprocessingOfLowResolution(Mat stylizedMat, Bitmap originalBitmap) { // 低分辨率后处理
        Mat mat = OpenCVUtils.deExpand(stylizedMat, originalBitmap);
        return OpenCVUtils.mat2bitmap(mat);
    }

    public static List<Mat> styleTransfer(List<Mat> mats) {
        long startTime = SystemClock.uptimeMillis();
        List<Mat> stylizedMats = new ArrayList<>();
        int cnt = 0;
        for (Mat mat: mats) {
            Bitmap bitmap = OpenCVUtils.mat2bitmap(mat); // 不要释放mat
//            mat.release(); // TODO 正式部署时不要释放mat
            Bitmap stylizedBitmap = StyleTransfer.instance.transfer(bitmap);
            bitmap.recycle(); // 释放内存
            Mat stylizedMat = OpenCVUtils.bitmap2mat(stylizedBitmap);
            stylizedBitmap.recycle();
            stylizedMats.add(stylizedMat);
            cnt++;
            Log.d("StyleTransferUtils", "styleTransfer: "+cnt+"/"+mats.size());
        }
//        mats.clear(); // TODO 正式部署时不要释放mat
        long styleTransferTime = SystemClock.uptimeMillis() - startTime;
        StyleTransfer.instance.deleteEngine(); // 释放模型占用内存
        Log.d("StyleTransferUtils", "styleTransfer time: "+styleTransferTime);
        return stylizedMats;
    }


    public static Bitmap styleTransferOtherResolution(List<Mat> mats, List<Block> blockList,
                                                      Bitmap originalBitmap) { // 中高分辨率风格迁移
        List<Mat> stylizedMats = styleTransfer(mats); // 风格迁移
        Mat mat = postprocessingOfOtherResolution(stylizedMats, blockList, originalBitmap); // 后处理
        return OpenCVUtils.mat2bitmap(mat);
    }

    public static Mat postprocessingOfOtherResolution(List<Mat> stylizedMats,
                                                         List<Block> blockList, Bitmap originalBitmap) {
        long startTime = SystemClock.uptimeMillis();
        blockList = OpenCVUtils.recut(stylizedMats, blockList, originalBitmap); // 替换blocklist中的图片
        List<Mat> imageList = sort(blockList); // 根据id

        Mat restoredMat = OpenCVUtils.restore(imageList, originalBitmap); // 羽化拼接
        Utility.releaseBlockList(blockList);
        Utility.releaseMatList(stylizedMats);
        Utility.releaseMatList(imageList);

        Mat result = OpenCVUtils.deExpand(restoredMat, originalBitmap); // 还原
        result = OpenCVUtils.smooth(result);
        long postprocessingTime = SystemClock.uptimeMillis() - startTime;
        Log.d("StyleTransferUtils", "postprocessing time: "+postprocessingTime);

        return result;
    }

    public static List<Mat> sort(List<Block> blockList) {
        Collections.sort(blockList, (l1, l2) -> l1.getId().compareTo(l2.getId()));
        List<Mat> imageList = new ArrayList<>();
        for (Block block : blockList) {
            imageList.add(block.getMat());
        }
        return imageList;
    }

    public static List<Mat> preprocessingOfLowResolution(Bitmap bitmap) { // 低分辨率预处理
        List<Mat> mats = new ArrayList<>();
        mats.add(OpenCVUtils.expand(bitmap, "low"));
        return mats;
    }

    public static Preprocessing preprocessingOfOtherResolution(Bitmap bitmap){ // 中高分辨率预处理
        long startTime = SystemClock.uptimeMillis();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int basicWidth = 16, paddingWidth = 16;
        Mat matExpand = OpenCVUtils.expand(bitmap, "other");
        List<Mat> imageList = OpenCVUtils.cut(matExpand, basicWidth, paddingWidth, width, height);
        List<Block> blockList = shuffle(imageList);
        imageList.clear();
        List<Block> newBlockList = new ArrayList<>(); // 用于优化内存使用
        List<Mat> shuffledImageList = new ArrayList<>();
        for (int i = 0; i < blockList.size(); i++) {
            Block block = blockList.get(i);
            shuffledImageList.add(block.getMat());
            newBlockList.add(new Block(block.getId(), null));
        }
        List<Mat> subimageList = OpenCVUtils.concat(shuffledImageList, 1000);
        blockList.clear();
        long preprocessingTime = SystemClock.uptimeMillis() - startTime;
        Log.d("StyleTransferUtils", "preprocessing time: "+preprocessingTime);
        return new Preprocessing(subimageList, newBlockList);
    }




    private static List<Block> shuffle(List<Mat> imageList) {
        List<Block> blockList = new ArrayList<>();
        for (int i = 0; i < imageList.size(); i++) {
            blockList.add(new Block(i, imageList.get(i)));
        }
        Collections.shuffle(blockList);
        return blockList;
    }
}
