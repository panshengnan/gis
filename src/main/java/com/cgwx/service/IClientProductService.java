package com.cgwx.service;

import com.cgwx.data.dto.FolderItems;
import com.cgwx.data.entity.GisClientFile;
import net.sf.json.JSONObject;

import java.io.File;
import java.util.List;

public interface IClientProductService {
    JSONObject getProductDetail(String productId);
    long getClientId();
    boolean UpdateClientProduct(JSONObject jsonObject1,JSONObject jsonObject2);
    boolean moveFolder(int sourceId,int descId,long clientId);
    boolean RenameFolder(int sourceId,String newName,long clientId);
    boolean deleteFolder(int folderId,long clientId);
    boolean addFolder(String folderName,int parentId,long clientId);
    boolean initFolder(Long clientId);
    boolean is_exist(Long clientId);
    boolean moveFlie(int productId,int descId,long clientId);
    List<FolderItems> buildFolderTree(long clientId);
    List<FolderItems> listToTree(List<FolderItems> list);

    List<FolderItems> getClientFileByType(long clientId);
    List<FolderItems> getClientFileByClass(long clientId);
    File downloadFile(String urlPath, String downloadDir);

}
