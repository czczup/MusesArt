package czcz94cz.photovalley.utils;

import android.graphics.Bitmap;
import android.util.Log;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import czcz94cz.photovalley.model.Block;


public class OpenCVUtils {
    public static int basicWidth = 16;
    public static int paddingWidth = 16;
    public static int blockWidth = basicWidth + 2 * paddingWidth;
    public static int edgeWidth = 8;
    public static int fuseWidth = paddingWidth - edgeWidth;

    public static List<Mat> unpadding(List<Mat> images, int paddingWidth) {
        int width = images.get(0).cols();
        int height = images.get(0).rows();
        List<Mat> unpaddingImages = new ArrayList<>();
        Rect rect = new Rect(paddingWidth, paddingWidth, width-2*paddingWidth, height-2*paddingWidth);
        for (Mat mat : images) {
            Mat croped = new Mat(mat, rect);
            unpaddingImages.add(croped);
        }
        return unpaddingImages;
    }

    public static Mat smooth(Mat mat) {
        Mat temp1 = new Mat();
        Imgproc.bilateralFilter(mat, temp1, 0, 10, 10);
        mat.release();
        Mat temp2 = new Mat();
        Imgproc.bilateralFilter(temp1, temp2, 0, 10, 10);
        temp1.release();
        Mat temp3 = new Mat();
        Imgproc.bilateralFilter(temp2, temp3, 0, 10, 10);
        temp2.release();
        Mat temp4 = new Mat();
        Imgproc.bilateralFilter(temp3, temp4, 0, 10, 10);
        temp3.release();
        return temp4;
    }

    public static Mat restore(List<Mat> imageList, Bitmap originalBitmap) {
        List<Mat> imageList_ = unpadding(imageList, edgeWidth);

        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        int rowNum = (int) Math.ceil(height/(double)basicWidth);
        int colNum = (int) Math.ceil(width/(double)basicWidth);
        List<List<Mat>> tempList = new ArrayList<>();
        for (int i = 0; i < imageList_.size(); i+=colNum) {
            tempList.add(imageList_.subList(i, i+colNum));
        }

        List<Mat> rowImages = new ArrayList<>();
        int newBlockWidth = blockWidth - 2 * edgeWidth;

        Mat horizontalAlpha = new Mat(newBlockWidth, 2*fuseWidth, CvType.CV_32F);
        float[] horizontalAlpha_ = new float[2*fuseWidth*newBlockWidth];
        for (int row = 0; row < newBlockWidth; row++) {
            for (int col = 0; col < 2*fuseWidth; col++) {
                int position = (row*2*fuseWidth+col);
                horizontalAlpha_[position] = col / (float)(2*fuseWidth);
            }
        }
        horizontalAlpha.put(0, 0, horizontalAlpha_);
        Mat one = Mat.ones(newBlockWidth, 2*fuseWidth, CvType.CV_32F);
        Mat horizontalBeta = new Mat();
        Core.subtract(one, horizontalAlpha, horizontalBeta);
        one.release(); // 释放内存

        int widthExpand = (int) Math.ceil(width/(double)basicWidth) * basicWidth + 2 * paddingWidth;
        Mat verticalAlpha = new Mat(2*fuseWidth, widthExpand-2*edgeWidth, CvType.CV_32F);
        float[] verticalAlpha_ = new float[(widthExpand-2*edgeWidth)*(2*fuseWidth)];
        for (int row = 0; row < 2*fuseWidth; row++) {
            float alphaValue = row / (float)(2*fuseWidth);
            for (int col = 0; col < (widthExpand-2*edgeWidth); col++) {
                int position = (row*((widthExpand-2*edgeWidth))+col);
                verticalAlpha_[position] = alphaValue;
            }
        }
        verticalAlpha.put(0, 0, verticalAlpha_);
        Mat one2 = Mat.ones(2*fuseWidth, widthExpand-2*edgeWidth, CvType.CV_32F);
        Mat verticalBeta = new Mat();
        Core.subtract(one2, verticalAlpha, verticalBeta);
        one2.release(); // 释放内存

        Rect rectOverlap2 = new Rect(0, 0, 2*fuseWidth, newBlockWidth);
        Rect rectRight = new Rect(2*fuseWidth, 0, newBlockWidth-2*fuseWidth, newBlockWidth);

        for (int i = 0; i < tempList.size(); i++) {
            List<Mat> row = tempList.get(i);
            Mat rowImage = null;
            for (int j = 0; j < row.size(); j++) {
                Mat item = row.get(j);
                if (j == 0) {
                    rowImage = item;
                } else {
                    if (fuseWidth != 0) {
                        Rect rectLeft = new Rect(0, 0, rowImage.cols()-2*fuseWidth, newBlockWidth);
                        Rect rectOverlap1 = new Rect(rowImage.cols()-2*fuseWidth, 0, 2*fuseWidth, newBlockWidth);
                        Mat left = new Mat(rowImage, rectLeft);
                        Mat overlap1 = new Mat(rowImage, rectOverlap1);
                        rectLeft = null; // 释放
                        rectOverlap1 = null;
                        Mat overlap2 = new Mat(item, rectOverlap2);
                        Mat right = new Mat(item, rectRight);
                        Mat overlap = fuse(overlap1, overlap2, horizontalAlpha, horizontalBeta);

                        List<Mat> list = new ArrayList<>();
                        list.add(left);
                        list.add(overlap);
                        list.add(right);
                        Mat hconcat = new Mat();
                        Core.hconcat(list, hconcat);
                        rowImage.release();
                        rowImage = hconcat;
                        item.release();

                        list.clear(); // 释放
                        overlap1.release();
                        overlap2.release();
                        left.release();
                        right.release();
                        overlap.release();
                    } else {
                        List<Mat> list = new ArrayList<>();
                        list.add(rowImage);
                        list.add(item);
                        Core.hconcat(list, rowImage);
                        list.clear();
                    }
                }
            }
            Log.d("OpenCVUtils", "restore: Horizontal fusion "+(i+1)+"/"+rowNum);
            rowImages.add(rowImage);
            for (Mat mat:row) {
                mat.release();
            }
        }
        tempList.clear(); // 释放内存

        Mat image = null;
        int rowImageWidth = rowImages.get(0).cols();
        rectOverlap2 = new Rect(0, 0, rowImageWidth, 2*fuseWidth);
        Rect rectBottom = new Rect(0, 2*fuseWidth, rowImageWidth, newBlockWidth-2*fuseWidth);
        for (int i = 0; i < rowImages.size(); i++) {
            Mat row = rowImages.get(i);
            if (i == 0) {
                image = row;
            } else {
                if (fuseWidth != 0) {
                    Rect rectTop = new Rect(0, 0, image.cols(), image.rows()-2*fuseWidth);
                    Rect rectOverlap1 = new Rect(0, image.rows()-2*fuseWidth, image.cols(), 2*fuseWidth);
                    Mat top = new Mat(image, rectTop);
                    Mat overlap1 = new Mat(image, rectOverlap1);
                    rectTop = null; // 释放内存
                    rectOverlap1 = null;
                    Mat overlap2 = new Mat(row, rectOverlap2);
                    Mat bottom = new Mat(row, rectBottom);
                    Mat overlap = fuse(overlap1, overlap2, verticalAlpha, verticalBeta);

                    List<Mat> temp = new ArrayList<>();
                    temp.add(top);
                    temp.add(overlap);
                    temp.add(bottom);
                    Mat vconcat = new Mat();
                    Core.vconcat(temp, vconcat);
                    image.release();
                    image = vconcat;
                    row.release();
                    temp.clear(); // 释放内存
                    overlap1.release();
                    overlap2.release();
                    top.release();
                    overlap.release();
                    bottom.release();
                } else {
                    List<Mat> temp = new ArrayList<>();
                    temp.add(image);
                    temp.add(row);
                    Core.vconcat(temp, image);
                    temp.clear();
                }
            }

        }
        Utility.releaseMatList(rowImages);
        return image;
    }

    private static Mat fuse(Mat overlap1, Mat overlap2, Mat alpha, Mat beta) {
        Mat overlap1_ = new Mat();
        Mat overlap2_ = new Mat();
        overlap1.convertTo(overlap1_, CvType.CV_32FC3);
        overlap2.convertTo(overlap2_, CvType.CV_32FC3);
        List<Mat> mv1 = new ArrayList<>();
        Core.split(overlap1_, mv1);
        overlap1_.release(); // 释放内存
        List<Mat> mv2 = new ArrayList<>();
        Core.split(overlap2_, mv2);
        overlap2_.release(); // 释放内存
        List<Mat> channels = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            Mat temp1 = mv1.get(i);
            Mat temp2 = mv2.get(i);
            Mat result1 = new Mat();
            Mat result2 = new Mat();
            Mat result = new Mat();
            Core.multiply(temp1, beta, result1);
            temp1.release();
            Core.multiply(temp2, alpha, result2);
            temp2.release();
            Core.add(result1, result2, result);
            result1.release(); // 释放内存
            result2.release(); // 释放内存
            channels.add(result);
        }
        Utility.releaseMatList(mv1); // 释放内存
        Utility.releaseMatList(mv2); // 释放内存
        Mat result = new Mat();
        Mat result_ = new Mat();
        Core.merge(channels, result);
        Utility.releaseMatList(channels); // 释放内存
        result.convertTo(result_, CvType.CV_8UC3);
        result.release();
        return result_;
    }

    public static List<Block> recut(List<Mat> stylizedMats, List<Block> blockList, Bitmap originalBitmap) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        int totalBlockNum = (int) (Math.ceil(width/(double)basicWidth) * Math.ceil(height/(double)basicWidth));

        List<Mat> newMatList = new ArrayList<>();
        for (Mat stylizedMat: stylizedMats) {
            List<Mat> tempList = cut(stylizedMat, blockWidth, 0,
                    stylizedMat.cols(), stylizedMat.rows());
            newMatList.addAll(tempList);
        }
        Utility.releaseMatList(stylizedMats);
        newMatList = newMatList.subList(0, totalBlockNum);

        List<Block> newBlockList = new ArrayList<>();
        for (int i = 0; i < newMatList.size(); i++) {
            Block block = blockList.get(i);
            block.setMat(newMatList.get(i));
            newBlockList.add(block);
        }
        return newBlockList;
    }

    public static Bitmap resize(String imagePath, String mode) {
        Mat img = Imgcodecs.imread(imagePath, Imgcodecs.IMREAD_COLOR);
        Mat resizedImg = new Mat();
        int width = img.cols();
        int height = img.rows();
        Log.d("OpenCVUtils", "before resize: "+width+","+height);
        double rate;
        if (mode.equals("low")) { // 低分辨率模式：最大边的长度为920px
            rate = Math.max(width, height) / 920.0;
            width = (int)(width / rate);
            height = (int)(height / rate);
        } else if (mode.equals("mid")) { // 中分辨率模式：总像素等于2000*2000px
            rate = Math.sqrt(width*height/(2000*2000.0));
            width = (int)(width / rate);
            height = (int)(height / rate);
        } else { // 高分辨率模式：总像素等于3000*3000px
            rate = Math.sqrt(width*height/(3000*3000.0));
            width = (int)(width / rate);
            height = (int)(height / rate);
        }
        Log.d("OpenCVUtils", "resize parameter: "+width+","+height+","+rate);
        Imgproc.resize(img, resizedImg, new Size(width, height));
        Log.d("OpenCVUtils", "after resize: "+resizedImg.cols()+","+resizedImg.rows());
        return mat2bitmap(resizedImg);
    }

    public static Mat deExpand(Mat mat, Bitmap origin) {
        int originalWidth = origin.getWidth();
        int originalHeight = origin.getHeight();
        int width = mat.cols();
        int height = mat.rows();
        int x = (width - originalWidth) / 2;
        int y = (height - originalHeight) / 2;
        Log.d("OpenCVUtils", "deExpand: "+x+","+y+","+originalWidth+"," + originalHeight);
        Rect rect = new Rect(x, y, originalWidth, originalHeight);
        Mat croped = new Mat(mat, rect);
        return croped;
    }

    public static Mat expand(Bitmap bitmap, String mode) {
        if (mode.equals("low")) { // 低分辨率模式：将图像扩大为960*960px
            Mat temp = new Mat();
            int maxWidth = 960, maxHeight = 960;
            Mat mat = bitmap2mat(bitmap);
            int width = mat.cols(), height = mat.rows();
            int left = (maxWidth - width) / 2;
            int right = (maxWidth - width) - left;
            int top = (maxHeight - height) / 2;
            int bottom = (maxHeight - height) - top;
            Log.d("OpenCVUtils", "copyMakeBorder: "+top+","+bottom+","+left+","+right);
            Core.copyMakeBorder(mat, temp, top, bottom, left, right, Core.BORDER_REFLECT);
            mat.release();
            Log.d("OpenCVUtils", "expand: "+temp.cols()+","+temp.rows());
            return temp;
        } else {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int widthExpand = (int) Math.ceil(width/(double)basicWidth) * basicWidth + 2 * paddingWidth;
            int heightExpand = (int) Math.ceil(height/(double)basicWidth) * basicWidth + 2 * paddingWidth;
            Mat temp = new Mat();
            Mat mat = bitmap2mat(bitmap);
            int left = (widthExpand - width) / 2;
            int right = (widthExpand - width) - left;
            int top = (heightExpand - height) / 2;
            int bottom = (heightExpand - height) - top;
            Log.d("OpenCVUtils", "copyMakeBorder: "+top+","+bottom+","+left+","+right);
            Core.copyMakeBorder(mat, temp, top, bottom, left, right, Core.BORDER_REFLECT);
            mat.release();
            Log.d("OpenCVUtils", "expand: "+temp.cols()+","+temp.rows());
            return temp;
        }
    }

    public static List<Mat> cut(Mat mat, int basicWidth, int paddingWidth, int width, int height) {
        int rowNum = (int) Math.ceil(height / (double) basicWidth);
        int colNum = (int) Math.ceil(width / (double) basicWidth);
        int blockWidth = basicWidth + 2 * paddingWidth;
        List<Mat> imageList = new ArrayList<>();
        for (int j = 0; j < rowNum; j++) {
            int b = j * basicWidth;
            for (int i = 0; i < colNum; i++) {
                int a = i * basicWidth;
                Rect rect = new Rect(a, b, blockWidth, blockWidth);
                Mat croped = new Mat(mat, rect);
                rect = null; // 释放内存
                imageList.add(croped);
            }
        }
        return imageList;
    }

    public static List<Mat> concat(List<Mat> matList, int maxWidth) {
        int blockNum = (maxWidth/blockWidth) * (maxWidth/blockWidth);
        int subimageNum = (int) Math.ceil(matList.size() / (double) blockNum);
        int appendNum = subimageNum * blockNum - matList.size();
        for (int i = 0; i < appendNum; i++) {
            matList.add(matList.get(i));
        }
        List<List<Mat>> imageList = new ArrayList<>();
        for (int i = 0; i < matList.size(); i=i+blockNum) {
            List<Mat> temp = matList.subList(i, i+blockNum); // 将图像块按子图像进行划分
            imageList.add(temp);
        }
        List<Mat> mats = list2image(imageList, blockWidth, maxWidth); // 将图像块转换为子图像
//        Utility.releaseMatList(matList);
        imageList.clear();
        return mats;
    }

    private static List<Mat> list2image(List<List<Mat>> imageList, int blockWidth, int maxWidth) {
        int num = maxWidth / blockWidth;
        List<Mat> images = new ArrayList<>();
        for (List<Mat> subList : imageList) {
            List<List<Mat>> tempList = new ArrayList<>();
            for (int i = 0; i < subList.size(); i=i+num) {
                tempList.add(subList.subList(i, i + num));
            }
            List<Mat> rowImgs = new ArrayList<>();
            for (int i = 0; i < tempList.size(); i++) {
                Mat rowImg = new Mat();
                Core.hconcat(tempList.get(i), rowImg);
                rowImgs.add(rowImg);
            }
            tempList.clear();
            Mat newImage = new Mat();
            Core.vconcat(rowImgs, newImage);
            Utility.releaseMatList(rowImgs);
            images.add(newImage);
        }
        return images;
    }

    public static Mat bitmap2mat(Bitmap bitmap) {
        Mat mat = new Mat();
        Mat temp = new Mat();
        Utils.bitmapToMat(bitmap, mat);
        Imgproc.cvtColor(mat, temp, Imgproc.COLOR_RGB2BGR);
        mat.release();
        return temp;
    }

    public static Bitmap mat2bitmap(Mat mat) {
        int width = mat.cols();
        int height = mat.rows();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Mat temp = new Mat();
        Imgproc.cvtColor(mat, temp, Imgproc.COLOR_BGR2RGB);
        Utils.matToBitmap(temp, bitmap);
        temp.release(); // 释放内存
        return bitmap;
    }
}
