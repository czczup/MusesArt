package czcz94cz.photovalley.model;

import org.opencv.core.Mat;

import java.util.List;

public class Preprocessing {
    private List<Mat> matInputList;
    private List<Block> blockList;

    public Preprocessing(List<Mat> matInputList, List<Block> blockList) {
        this.matInputList = matInputList;
        this.blockList = blockList;
    }

    public List<Mat> getMatInputList() {
        return matInputList;
    }

    public void setBitmapInputList(List<Mat> matInputList) {
        this.matInputList = matInputList;
    }

    public List<Block> getBlockList() {
        return blockList;
    }

    public void setBlockList(List<Block> blockList) {
        this.blockList = blockList;
    }
}
