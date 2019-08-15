package com.cgwx.service;

import com.cgwx.data.dto.ClientFileByClass;
import com.cgwx.data.dto.ClientFileByType;
import com.cgwx.data.dto.ClientFileInfo;
import com.cgwx.data.dto.FolderItems;
import com.cgwx.data.entity.GisClientFile;

import java.util.List;

public interface IClientProductService {

    boolean moveFolder(int sourceId,int descId,int clientId);
    boolean RenameFolder(int sourceId,String newName,int clientId);
    boolean deleteFolder(int folderId,int clientId);
    boolean addFolder(int folderId,String folderName,int parentId,int clientId);

    boolean moveFlie(int productId,int descId,int clientId);
    List<FolderItems> buildFolderTree(int clientId);
    List<FolderItems> listToStree(List<FolderItems> list);

    List<ClientFileByType> getClientFileByType(int clientId);
    List<ClientFileByClass> getClientFileByClass(int clientId);

}
