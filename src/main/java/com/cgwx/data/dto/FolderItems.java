package com.cgwx.data.dto;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class FolderItems {
    private int id;
    private int parentId;
    private String name;
    private List<FolderItems> children;
    //private  List<ClientFileInfo> ProductList;

    private String geoJson;
    private String thumbUrl;
    private String downloadUrl;

    public String getGeoJson() {
        return geoJson;
    }

    public void setGeoJson(String geoJson) {
        this.geoJson = geoJson;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public FolderItems(){

    }
    public FolderItems(int id,int parentId,String name,List<FolderItems> children){
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.children = children;
    }
    public FolderItems(int id,int parentId,String name){
        this.id = id;
        this.parentId = parentId;
        this.name = name;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FolderItems> getChildren() {
        return children;
    }

    public void setChildren(List<FolderItems> children) {
        this.children = children;
    }

//    public List<ClientFileInfo> getProductList() {
//        return ProductList;
//    }
//
//    public void setProductList(List<ClientFileInfo> productList) {
//        ProductList = productList;
//    }

    @Override
    public String toString() {

        return JSON.toJSONString(this);
    }

}
