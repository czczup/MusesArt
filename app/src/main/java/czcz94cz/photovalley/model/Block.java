package czcz94cz.photovalley.model;

import org.opencv.core.Mat;

public class Block {
    private Integer id;
    private Mat mat;

    public Block(Integer id, Mat mat) {
        this.id = id;
        this.mat = mat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Mat getMat() {
        return mat;
    }

    public void setMat(Mat mat) {
        this.mat = mat;
    }
}
