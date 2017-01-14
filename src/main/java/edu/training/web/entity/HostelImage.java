package edu.training.web.entity;

/**
 * Created by Roman on 11.01.2017.
 */
public class HostelImage extends Entity {
    private int imageId;
    private int hostelId;
    private boolean isMain;
    private String fileName;

    public HostelImage() {
    }

    public HostelImage(int hostelId, boolean isMain, String fileName) {
        this.hostelId = hostelId;
        this.isMain = isMain;
        this.fileName = fileName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getHostelId() {
        return hostelId;
    }

    public void setHostelId(int hostelId) {
        this.hostelId = hostelId;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
