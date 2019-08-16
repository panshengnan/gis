package com.cgwx.service;

import com.cgwx.data.dto.FolderItems;

import java.util.List;

public interface IClientProductService {

    boolean moveFolder(int sourceId,int descId,int clientId);
    boolean RenameFolder(int sourceId,String newName,int clientId);
    boolean deleteFolder(int folderId,int clientId);
    boolean addFolder(String folderName,int parentId,int clientId);

    boolean moveFlie(int productId,int descId,int clientId);
    List<FolderItems> buildFolderTree(int clientId);
    List<FolderItems> listToStree(List<FolderItems> list);

    List<FolderItems> getClientFileByType(int clientId);
    List<FolderItems> getClientFileByClass(int clientId);

}
