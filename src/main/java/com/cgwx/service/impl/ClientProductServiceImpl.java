package com.cgwx.service.impl;

import com.cgwx.dao.GisClientFileMapper;
import com.cgwx.dao.GisClientFolderMapper;
import com.cgwx.data.dto.ClientFileByClass;
import com.cgwx.data.dto.ClientFileByType;
import com.cgwx.data.dto.ClientFileInfo;
import com.cgwx.data.dto.FolderItems;
import com.cgwx.data.entity.GisClientFolder;
import com.cgwx.service.IClientProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ClientProductServiceImpl implements IClientProductService {

    @Autowired
    GisClientFolderMapper gisClientFolderMapper;
    @Autowired
    GisClientFileMapper gisClientFileMapper;

    @Override
    public  List<FolderItems> buildFolderTree(int clientId) {

        List<GisClientFolder> gisClientFolderList = new ArrayList<>();
        gisClientFolderList = gisClientFolderMapper.getFoldersByclientId(clientId);
        List<FolderItems> folderItemsList = new ArrayList<>();
        for(int i = 0;i<gisClientFolderList.size();i++){
            FolderItems folderItems = new FolderItems();
            folderItems.setId(gisClientFolderList.get(i).getFolderId());
            folderItems.setName(gisClientFolderList.get(i).getFolderName());
            folderItems.setParentId(gisClientFolderList.get(i).getParentId());
            List<ClientFileInfo> clientFileInfoList = new ArrayList<>();
            clientFileInfoList = gisClientFileMapper.getProductListByFolderId(clientId,gisClientFolderList.get(i).getFolderId());
            List<FolderItems> folderItemsList1 = new ArrayList<>();
            for(ClientFileInfo clientFileInfo :clientFileInfoList){
                FolderItems folderItems1 = new FolderItems();
                folderItems1.setId(clientFileInfo.getProductId());
                folderItems1.setName(clientFileInfo.getProductName());
//                folderItems1.setParentId(clientFileInfo.getParentId());
                folderItems1.setGeoJson(clientFileInfo.getGeoJson());
                folderItems1.setDownloadUrl(clientFileInfo.getDownloadUrl());
                folderItems1.setThumbUrl(clientFileInfo.getThumbUrl());
                folderItemsList1.add(folderItems1);
            }
            folderItems.setChildren(folderItemsList1);
//            folderItems.setProductList(clientFileInfoList);
            folderItemsList.add(folderItems);
        }
        List<FolderItems> root =  new ArrayList<>();
        root=listToStree(folderItemsList);

        return root;
    }
    @Override
    public   List<FolderItems> listToStree(List<FolderItems> list) {
        List<FolderItems> treeList = new ArrayList<FolderItems>();
        for (FolderItems tree : list) {
            //找到根
            if (tree.getParentId() == -1) {
                treeList.add(tree);
            }
            //找到子
            for (FolderItems treeNode : list) {
                if (treeNode.getParentId() == tree.getId()) {
                    if (tree.getChildren() == null) {
                        tree.setChildren(new ArrayList<FolderItems>());
                    }
                    tree.getChildren().add(treeNode);
                }
            }
        }
        return treeList;
    }

    @Override
    public boolean moveFolder(int sourceId,int descId,int clientId){
        if(gisClientFolderMapper.modifyFolderParentIdByFolderId(clientId,sourceId,descId)){
//            List<ClientFileInfo> clientFileInfoList = gisClientFileMapper.getProductListByFolderId(clientId,sourceId);
//            for(ClientFileInfo clientFileInfo:clientFileInfoList){
//                gisClientFileMapper.modifyFolderByProductId(clientFileInfo.getProductId(),descId);


//            }
            //gisClientFileMapper.modifyFolderByFolderAndClient(clientId,sourceId,descId);
            return true;
        }
        else
            return false;
    }
    @Override
    public boolean moveFlie(int productId,int descId,int clientId){
        if(gisClientFileMapper.modifyFolderByProductId(productId,descId))
        return true;
        else
            return false;
    }
    @Override
    public boolean deleteFolder(int folderId,int clientId){
        if(gisClientFolderMapper.deleteFolderByFolderId(folderId,clientId))
            return true;
        else
            return false;
    }
    @Override
    public boolean addFolder(int folderId,String folderName,int parentId,int clientId){
        GisClientFolder gisClientFolder = new GisClientFolder();
        gisClientFolder.setClientId(clientId);
        gisClientFolder.setFolderId(folderId);
        gisClientFolder.setFolderName(folderName);
        gisClientFolder.setParentId(parentId);
       if(gisClientFolderMapper.insert(gisClientFolder)!=0)
            return true;
        else
            return false;
    }
    @Override
    public  boolean RenameFolder(int sourceId,String newName,int clientId) {

        if (gisClientFolderMapper.UpdateFolderNameByFolderId(sourceId, newName, clientId))
            return true;
        else
            return false;
    }
    @Override
    public List<ClientFileByType> getClientFileByType(int clientId){
        List<ClientFileByType> clientFileByTypeList = new ArrayList<>();

        return clientFileByTypeList;
    }
    @Override
    public List<ClientFileByClass> getClientFileByClass(int clientId){
        List<ClientFileByClass> clientFileByClassList = new ArrayList<>();

        return clientFileByClassList;

    }
}
