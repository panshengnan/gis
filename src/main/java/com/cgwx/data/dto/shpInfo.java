package com.cgwx.data.dto;

public class shpInfo {
    private String imageSourc;
    private  String imageFile;
    private String acquisitio;
    private String sensor;
    private String numChannel;
    private String chanType;
    private String cloudCover;
    private String islands;
    private Object imageGeometry;
    private String productId;
    private int blockId;

    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    public String getImageSourc() {
        return imageSourc;
    }

    public void setImageSourc(String imageSourc) {
        this.imageSourc = imageSourc;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getAcquisitio() {
        return acquisitio;
    }

    public void setAcquisitio(String acquisitio) {
        this.acquisitio = acquisitio;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getNumChannel() {
        return numChannel;
    }

    public void setNumChannel(String numChannel) {
        this.numChannel = numChannel;
    }

    public String getChanType() {
        return chanType;
    }

    public void setChanType(String chanType) {
        this.chanType = chanType;
    }

    public String getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(String cloudCover) {
        this.cloudCover = cloudCover;
    }

    public String getIslands() {
        return islands;
    }

    public void setIslands(String islands) {
        this.islands = islands;
    }

    public Object getImageGeometry() {
        return imageGeometry;
    }

    public void setImageGeometry(Object imageGeometry) {
        this.imageGeometry = imageGeometry;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
