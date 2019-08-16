package com.cgwx.service.impl;

import com.cgwx.dao.GisClientFileMapper;
import com.cgwx.dao.GisClientFolderMapper;
import com.cgwx.data.dto.ClientFileInfo;
import com.cgwx.data.dto.FolderItems;
import com.cgwx.data.entity.GisClientFolder;
import com.cgwx.service.IClientProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

                folderItems1.setId(clientFileInfo.getLogicId());
                folderItems1.setName(clientFileInfo.getProductName());
                folderItems1.setParentId(gisClientFolderList.get(i).getFolderId());
                folderItems1.setGeoJson(clientFileInfo.getGeoJson().toString());
                folderItems1.setDownloadUrl(clientFileInfo.getDownloadUrl());
                folderItems1.setThumbUrl(clientFileInfo.getThumbUrl());
                folderItemsList1.add(folderItems1);
            }
            folderItems.setChildren(folderItemsList1);
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
    public boolean addFolder(String folderName,int parentId,int clientId){
        int folderId=gisClientFolderMapper.getMaxFolderId(clientId);
        GisClientFolder gisClientFolder = new GisClientFolder();
        gisClientFolder.setClientId(clientId);
        gisClientFolder.setFolderId(folderId+1);
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
    public List<FolderItems> getClientFileByType(int clientId){

        List<FolderItems> folderItemsList = new ArrayList<>();

        FolderItems folderItemsRoot = new FolderItems();
        folderItemsRoot.setId(0);
        folderItemsRoot.setParentId(-1);
        folderItemsRoot.setName("全部数据");
        folderItemsList.add(folderItemsRoot);

        FolderItems folderItems0 = new FolderItems();
        folderItems0.setId(1);
        folderItems0.setParentId(0);
        folderItems0.setName("普通光学");
        List<ClientFileInfo> clientFileInfoList1 = gisClientFileMapper.getProductListByTppe(clientId,"普通光学");
        List<FolderItems> folderItemsList1 = new ArrayList<>();
        for(ClientFileInfo clientFileInfo :clientFileInfoList1){
            FolderItems folderItems1 = new FolderItems();
            folderItems1.setId(clientFileInfo.getLogicId());
            folderItems1.setName(clientFileInfo.getProductName());
            folderItems1.setGeoJson(clientFileInfo.getGeoJson().toString());
            folderItems1.setDownloadUrl(clientFileInfo.getDownloadUrl());
            folderItems1.setThumbUrl(clientFileInfo.getThumbUrl());
            folderItemsList1.add(folderItems1);
        }
        folderItems0.setChildren(folderItemsList1);
        folderItemsList.add(folderItems0);

        FolderItems folderItems1 = new FolderItems();
        folderItems1.setId(1);
        folderItems1.setParentId(0);
        folderItems1.setName("夜光");
        List<ClientFileInfo> clientFileInfoList2 = gisClientFileMapper.getProductListByTppe(clientId,"夜光");
        List<FolderItems> folderItemsList2 = new ArrayList<>();
        for(ClientFileInfo clientFileInfo :clientFileInfoList2){
            FolderItems folderItems2 = new FolderItems();
            folderItems2.setId(clientFileInfo.getLogicId());
            folderItems2.setName(clientFileInfo.getProductName());
            folderItems2.setGeoJson(clientFileInfo.getGeoJson().toString());
            folderItems2.setDownloadUrl(clientFileInfo.getDownloadUrl());
            folderItems2.setThumbUrl(clientFileInfo.getThumbUrl());
            folderItemsList2.add(folderItems2);
        }
        folderItems1.setChildren(folderItemsList2);
        folderItemsList.add(folderItems1);

        FolderItems folderItems2 = new FolderItems();
        folderItems2.setId(1);
        folderItems2.setParentId(0);
        folderItems2.setName("视频");
        List<ClientFileInfo> clientFileInfoList3 = gisClientFileMapper.getProductListByTppe(clientId,"视频");
        List<FolderItems> folderItemsList3 = new ArrayList<>();
        for(ClientFileInfo clientFileInfo :clientFileInfoList3){
            FolderItems folderItems3 = new FolderItems();
            folderItems3.setId(clientFileInfo.getLogicId());
            folderItems3.setName(clientFileInfo.getProductName());
            folderItems3.setGeoJson(clientFileInfo.getGeoJson().toString());
            folderItems3.setDownloadUrl(clientFileInfo.getDownloadUrl());
            folderItems3.setThumbUrl(clientFileInfo.getThumbUrl());
            folderItemsList2.add(folderItems3);
        }
        folderItems2.setChildren(folderItemsList3);
        folderItemsList.add(folderItems2);

        FolderItems folderItems3 = new FolderItems();
        folderItems3.setId(1);
        folderItems3.setParentId(0);
        folderItems3.setName("多光谱");
        List<ClientFileInfo> clientFileInfoList4 = gisClientFileMapper.getProductListByTppe(clientId,"多光谱");
        List<FolderItems> folderItemsList4 = new ArrayList<>();
        for(ClientFileInfo clientFileInfo :clientFileInfoList3){
            FolderItems folderItems4 = new FolderItems();
            folderItems4.setId(clientFileInfo.getLogicId());
            folderItems4.setName(clientFileInfo.getProductName());
            folderItems4.setGeoJson(clientFileInfo.getGeoJson().toString());
            folderItems4.setDownloadUrl(clientFileInfo.getDownloadUrl());
            folderItems4.setThumbUrl(clientFileInfo.getThumbUrl());
            folderItemsList2.add(folderItems4);
        }
        folderItems3.setChildren(folderItemsList4);
        folderItemsList.add(folderItems3);

        List<FolderItems> root =  new ArrayList<>();
        root=listToStree(folderItemsList);
        return root;
    }
    @Override
    public List<FolderItems> getClientFileByClass(int clientId){

        List<FolderItems> folderItemsList = new ArrayList<>();

        FolderItems folderItemsRoot = new FolderItems();
        folderItemsRoot.setId(0);
        folderItemsRoot.setParentId(-1);
        folderItemsRoot.setName("全部数据");
        folderItemsList.add(folderItemsRoot);

        FolderItems folderItems0 = new FolderItems();
        folderItems0.setId(1);
        folderItems0.setParentId(0);
        folderItems0.setName("标准产品");
        List<ClientFileInfo> clientFileInfoList1 = gisClientFileMapper.getProductListByClass(clientId,"标准产品");
        List<FolderItems> folderItemsList1 = new ArrayList<>();
        for(ClientFileInfo clientFileInfo :clientFileInfoList1){
            FolderItems folderItems1 = new FolderItems();
            folderItems1.setId(clientFileInfo.getLogicId());
            folderItems1.setName(clientFileInfo.getProductName());
            folderItems1.setGeoJson(clientFileInfo.getGeoJson().toString());
            folderItems1.setDownloadUrl(clientFileInfo.getDownloadUrl());
            folderItems1.setThumbUrl(clientFileInfo.getThumbUrl());
            folderItemsList1.add(folderItems1);
        }
        folderItems0.setChildren(folderItemsList1);
        folderItemsList.add(folderItems0);

        FolderItems folderItems1 = new FolderItems();
        folderItems1.setId(1);
        folderItems1.setParentId(0);
        folderItems1.setName("高级产品");
        List<ClientFileInfo> clientFileInfoList2 = gisClientFileMapper.getProductListByClass(clientId,"高级产品");
        List<FolderItems> folderItemsList2 = new ArrayList<>();
        for(ClientFileInfo clientFileInfo :clientFileInfoList2){
            FolderItems folderItems2 = new FolderItems();
            folderItems2.setId(clientFileInfo.getLogicId());
            folderItems2.setName(clientFileInfo.getProductName());
            folderItems2.setGeoJson(clientFileInfo.getGeoJson().toString());
            folderItems2.setDownloadUrl(clientFileInfo.getDownloadUrl());
            folderItems2.setThumbUrl(clientFileInfo.getThumbUrl());
            folderItemsList2.add(folderItems2);
        }
        folderItems1.setChildren(folderItemsList2);
        folderItemsList.add(folderItems1);

        FolderItems folderItems2 = new FolderItems();
        folderItems2.setId(1);
        folderItems2.setParentId(0);
        folderItems2.setName("专题产品");
        List<ClientFileInfo> clientFileInfoList3 = gisClientFileMapper.getProductListByClass(clientId,"专题产品");
        List<FolderItems> folderItemsList3 = new ArrayList<>();
        for(ClientFileInfo clientFileInfo :clientFileInfoList3){
            FolderItems folderItems3 = new FolderItems();
            folderItems3.setId(clientFileInfo.getLogicId());
            folderItems3.setName(clientFileInfo.getProductName());
            folderItems3.setGeoJson(clientFileInfo.getGeoJson().toString());
            folderItems3.setDownloadUrl(clientFileInfo.getDownloadUrl());
            folderItems3.setThumbUrl(clientFileInfo.getThumbUrl());
            folderItemsList2.add(folderItems3);
        }
        folderItems2.setChildren(folderItemsList3);
        folderItemsList.add(folderItems2);

        List<FolderItems> root =  new ArrayList<>();
        root=listToStree(folderItemsList);
        return root;

    }
}
