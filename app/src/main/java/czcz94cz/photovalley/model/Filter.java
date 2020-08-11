package czcz94cz.photovalley.model;


public class Filter {
    private int id;
    private String name;
    private String coverImage;
    private String modelFile;

    public Filter(int id, String name, String coverImage) {
        this.id = id;
        this.name = name;
        this.coverImage = coverImage;
        this.modelFile = null;
    }

    public Filter(int id, String name, String coverImage, String modelFile) {
        this.id = id;
        this.name = name;
        this.coverImage = coverImage;
        this.modelFile = modelFile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getModelFile() {
        return modelFile;
    }

    public void setModelFile(String modelFile) {
        this.modelFile = modelFile;
    }
}
