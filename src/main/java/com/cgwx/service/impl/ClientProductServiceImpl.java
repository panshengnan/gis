package com.cgwx.service.impl;

import com.cgwx.aop.UserManagement;
import com.cgwx.dao.GisClientFileMapper;
import com.cgwx.dao.GisClientFolderMapper;
import com.cgwx.dao.GisStandardProductTypeMapper;
import com.cgwx.data.dto.ClientData;
import com.cgwx.data.dto.ClientFileInfo;
import com.cgwx.data.dto.FolderItems;
import com.cgwx.data.dto.StandardProductDetail;
import com.cgwx.data.entity.GisClientFile;
import com.cgwx.data.entity.GisClientFolder;
import com.cgwx.service.IClientProductService;
import com.cgwx.service.IMetadataService;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ClientProductServiceImpl implements IClientProductService {

    @Autowired
    GisClientFolderMapper gisClientFolderMapper;
    @Autowired
    GisClientFileMapper gisClientFileMapper;
    @Autowired
    GisStandardProductTypeMapper gisStandardProductTypeMapper;
    @Value("${MetaQueryUrl}")
    private String MetaQueryUrl;

    @Override
    public long getClientId(){
        UserManagement userManagement = new UserManagement();
        JSONObject jsonObject = JSONObject.fromObject(userManagement.getAccountId());
//        System.out.println(userManagement.getAccountId());
        //System.out.println(jsonObject);
        String ClientId = jsonObject.getString("data");
        return Long.parseLong(ClientId);
}
    @Override
    public JSONObject getProductDetail(String productId){

        try {
//            String url = "http://10.10.105.100:18037/metadataapi/metadataQuery/getProductInfoByProductId";
            System.out.println("对端link:" + MetaQueryUrl);
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset("UTF-8");
            PostMethod postMethod = new PostMethod(MetaQueryUrl);
            StringRequestEntity requestEntity = new StringRequestEntity("", "application/xml", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            postMethod.addRequestHeader("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJyYW5kb21LZXkiOiJ2eHF6MXoiLCJzdWIiOiJsaXVib3dlbiIsImlhdCI6MTU2MTAyNzE2MH0.ZK0-pxesckdLtcQVEiyg3jq00i1ljvtE1rqHXhvf2wcQ9X7eO3pY0Ro1H1MNdFjpafVmMoNDx4fw_-pkBhp6Ew");
            postMethod.setParameter("productId", productId);
            httpClient.executeMethod(postMethod);
            String resultJson = new String(postMethod.getResponseBody());
            System.out.println(resultJson);
            return JSONObject.fromObject(resultJson);
        } catch (Exception var10) {
            System.out.println("报错了");
        }
        return JSONObject.fromObject("");
    }
    @Override
    public boolean UpdateClientProduct(JSONObject jsonObject1,JSONObject jsonObject2)
    {
        GisClientFile gisClientFile = new GisClientFile();
        long clientId = Long.parseLong(jsonObject1.getString( "clientId"));
        String productId =jsonObject1.getString( "productId");
        if(gisClientFileMapper.getproductId(productId,clientId)!=0){
            return false;
        }else{
            String downloadUrl =jsonObject1.getString( "downloadUrl");
            SimpleDateFormat df = new SimpleDateFormat("MMddHHmmss");//设置日期格式
            String currentTime = df.format(new Date());
            int logicId = Integer.parseInt(currentTime);
            System.out.println(logicId);
            gisClientFile.setClientId(clientId);
            gisClientFile.setFolderId(0);
            gisClientFile.setLogicId(logicId);
            gisClientFile.setProductId(productId);
            gisClientFile.setProductName(productId);
            gisClientFile.setProductClass("标准产品");
            int typeIndex = Integer.parseInt(jsonObject2.getString("productType"));
            String productType = gisStandardProductTypeMapper.selectTypeByIndex(typeIndex);
            gisClientFile.setProductType(productType);
            gisClientFile.setDownloadUrl(downloadUrl);
            gisClientFile.setThumbUrl("http://10.10.105.100:18037/metadataapi"+jsonObject2.getString("thumbnail"));
            String geojson = jsonObject2.getString("imageGeo");
            if(gisClientFileMapper.insert(gisClientFile)!=0){
                gisClientFileMapper.updateClientFileImgGeo(productId, geojson);
                return true;
            }else{
                return false;
            }
        }

    }
    @Override
    public  List<FolderItems> buildFolderTree(long clientId) {

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
            System.out.println(clientFileInfoList);

            List<FolderItems> folderItemsList1 = new ArrayList<>();

            for(ClientFileInfo clientFileInfo :clientFileInfoList){
                FolderItems folderItems1 = new FolderItems();
                folderItems1.setId(clientFileInfo.getLogicId());
                folderItems1.setName(clientFileInfo.getProductName());
                folderItems1.setParentId(gisClientFolderList.get(i).getFolderId());
                folderItems1.setGeoJson(clientFileInfo.getGeoJson().toString());
                folderItems1.setDownloadUrl(clientFileInfo.getDownloadUrl());
                folderItems1.setThumbUrl(clientFileInfo.getThumbUrl());
                folderItems1.setLayerName(clientFileInfo.getLayerName());
                folderItems1.setProductId(clientFileInfo.getProductId());
                folderItemsList1.add(folderItems1);
            }
            folderItems.setChildren(folderItemsList1);
            folderItemsList.add(folderItems);
        }
        List<FolderItems> root =  new ArrayList<>();
        root=listToTree(folderItemsList);

        return root;
    }
    @Override
    public   List<FolderItems> listToTree(List<FolderItems> list) {
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
    public boolean moveFolder(int sourceId,int descId,long clientId){
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
    public boolean moveFlie(int productId,int descId,long clientId){
        if(gisClientFileMapper.modifyFolderByProductId(productId,descId))
        return true;
        else
            return false;
    }
    @Override
    public boolean deleteFolder(int folderId,long clientId){
        if(gisClientFolderMapper.deleteFolderByFolderId(folderId,clientId))
            return true;
        else
            return false;
    }
    @Override
    public boolean addFolder(String folderName,int parentId,long clientId){
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
    public  boolean RenameFolder(int sourceId,String newName,long clientId) {

        if (gisClientFolderMapper.UpdateFolderNameByFolderId(sourceId, newName, clientId))
            return true;
        else
            return false;
    }
    @Override
    public List<FolderItems> getClientFileByType(long clientId){

        List<FolderItems> folderItemsList = new ArrayList<>();

        FolderItems folderItemsRoot = new FolderItems();
        folderItemsRoot.setId(0);
        folderItemsRoot.setParentId(-1);
        folderItemsRoot.setName("全部数据");
        folderItemsList.add(folderItemsRoot);

        FolderItems folderItems0 = new FolderItems();
        folderItems0.setId(1);
        folderItems0.setParentId(0);
        folderItems0.setName("普通光学影像");
        List<ClientFileInfo> clientFileInfoList1 = gisClientFileMapper.getProductListByType(clientId,"普通光学影像");
        List<FolderItems> folderItemsList1 = new ArrayList<>();
        for(ClientFileInfo clientFileInfo :clientFileInfoList1){
            FolderItems folderItems1 = new FolderItems();
            folderItems1.setId(clientFileInfo.getLogicId());
            folderItems1.setParentId(1);
            folderItems1.setName(clientFileInfo.getProductName());
            folderItems1.setGeoJson(clientFileInfo.getGeoJson().toString());
            folderItems1.setDownloadUrl(clientFileInfo.getDownloadUrl());
            folderItems1.setThumbUrl(clientFileInfo.getThumbUrl());
            folderItems1.setLayerName(clientFileInfo.getLayerName());
            folderItems1.setProductId(clientFileInfo.getProductId());
            folderItemsList1.add(folderItems1);
        }
        folderItems0.setChildren(folderItemsList1);
        folderItemsList.add(folderItems0);

        FolderItems folderItems1 = new FolderItems();
        folderItems1.setId(2);
        folderItems1.setParentId(0);
        folderItems1.setName("夜光影像");
        List<ClientFileInfo> clientFileInfoList2 = gisClientFileMapper.getProductListByType(clientId,"夜光影像");
        List<FolderItems> folderItemsList2 = new ArrayList<>();
        for(ClientFileInfo clientFileInfo :clientFileInfoList2){
            FolderItems folderItems2 = new FolderItems();
            folderItems2.setId(clientFileInfo.getLogicId());
            folderItems2.setParentId(2);
            folderItems2.setName(clientFileInfo.getProductName());
            folderItems2.setGeoJson(clientFileInfo.getGeoJson().toString());
            folderItems2.setDownloadUrl(clientFileInfo.getDownloadUrl());
            folderItems2.setThumbUrl(clientFileInfo.getThumbUrl());
            folderItems2.setLayerName(clientFileInfo.getLayerName());
            folderItems2.setProductId(clientFileInfo.getProductId());
            folderItemsList2.add(folderItems2);
        }
        folderItems1.setChildren(folderItemsList2);
        folderItemsList.add(folderItems1);

        FolderItems folderItems2 = new FolderItems();
        folderItems2.setId(3);
        folderItems2.setParentId(0);
        folderItems2.setName("视频影像");
        List<ClientFileInfo> clientFileInfoList3 = gisClientFileMapper.getProductListByType(clientId,"视频影像");
        List<FolderItems> folderItemsList3 = new ArrayList<>();
        for(ClientFileInfo clientFileInfo :clientFileInfoList3){
            FolderItems folderItems3 = new FolderItems();
            folderItems3.setId(clientFileInfo.getLogicId());
            folderItems3.setParentId(3);
            folderItems3.setName(clientFileInfo.getProductName());
            folderItems3.setGeoJson(clientFileInfo.getGeoJson().toString());
            folderItems3.setDownloadUrl(clientFileInfo.getDownloadUrl());
            folderItems3.setThumbUrl(clientFileInfo.getThumbUrl());
            folderItems3.setLayerName(clientFileInfo.getLayerName());
            folderItems3.setProductId(clientFileInfo.getProductId());
            folderItemsList3.add(folderItems3);
        }
        folderItems2.setChildren(folderItemsList3);
        folderItemsList.add(folderItems2);

        FolderItems folderItems3 = new FolderItems();
        folderItems3.setId(4);
        folderItems3.setParentId(0);
        folderItems3.setName("多光谱光学影像");
        List<ClientFileInfo> clientFileInfoList4 = gisClientFileMapper.getProductListByType(clientId,"多光谱光学影像");
        List<FolderItems> folderItemsList4 = new ArrayList<>();
        for(ClientFileInfo clientFileInfo :clientFileInfoList4){
            FolderItems folderItems4 = new FolderItems();
            folderItems4.setId(clientFileInfo.getLogicId());
            folderItems4.setParentId(4);
            folderItems4.setName(clientFileInfo.getProductName());
            folderItems4.setGeoJson(clientFileInfo.getGeoJson().toString());
            folderItems4.setDownloadUrl(clientFileInfo.getDownloadUrl());
            folderItems4.setThumbUrl(clientFileInfo.getThumbUrl());
            folderItems4.setLayerName(clientFileInfo.getLayerName());
            folderItems4.setProductId(clientFileInfo.getProductId());
            folderItemsList4.add(folderItems4);
        }
        folderItems3.setChildren(folderItemsList4);
        folderItemsList.add(folderItems3);

        List<FolderItems> root =  new ArrayList<>();
        root=listToTree(folderItemsList);
        return root;
    }
    @Override
    public List<FolderItems> getClientFileByClass(long clientId){

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
            folderItems1.setLayerName(clientFileInfo.getLayerName());
            folderItems1.setProductId(clientFileInfo.getProductId());
            folderItemsList1.add(folderItems1);
        }
        folderItems0.setChildren(folderItemsList1);
        folderItemsList.add(folderItems0);

        FolderItems folderItems1 = new FolderItems();
        folderItems1.setId(2);
        folderItems1.setParentId(0);
        folderItems1.setName("高级产品");
        List<ClientFileInfo> clientFileInfoList2 = gisClientFileMapper.getProductListByClass(clientId,"高级产品");
        List<FolderItems> folderItemsList2 = new ArrayList<>();
        for(ClientFileInfo clientFileInfo :clientFileInfoList2){
            FolderItems folderItems2 = new FolderItems();
            folderItems2.setId(clientFileInfo.getLogicId());
            folderItems2.setParentId(2);
            folderItems2.setName(clientFileInfo.getProductName());
            folderItems2.setGeoJson(clientFileInfo.getGeoJson().toString());
            folderItems2.setDownloadUrl(clientFileInfo.getDownloadUrl());
            folderItems2.setThumbUrl(clientFileInfo.getThumbUrl());
            folderItems2.setLayerName(clientFileInfo.getLayerName());
            folderItems2.setProductId(clientFileInfo.getProductId());
            folderItemsList2.add(folderItems2);
        }
        folderItems1.setChildren(folderItemsList2);
        folderItemsList.add(folderItems1);

        FolderItems folderItems2 = new FolderItems();
        folderItems2.setId(3);
        folderItems2.setParentId(0);
        folderItems2.setName("专题产品");
        List<ClientFileInfo> clientFileInfoList3 = gisClientFileMapper.getProductListByClass(clientId,"专题产品");
        List<FolderItems> folderItemsList3 = new ArrayList<>();
        for(ClientFileInfo clientFileInfo :clientFileInfoList3){
            FolderItems folderItems3 = new FolderItems();
            folderItems3.setId(clientFileInfo.getLogicId());
            folderItems3.setParentId(3);
            folderItems3.setName(clientFileInfo.getProductName());
            folderItems3.setGeoJson(clientFileInfo.getGeoJson().toString());
            folderItems3.setDownloadUrl(clientFileInfo.getDownloadUrl());
            folderItems3.setThumbUrl(clientFileInfo.getThumbUrl());
            folderItems3.setLayerName(clientFileInfo.getLayerName());
            folderItems3.setProductId(clientFileInfo.getProductId());
            folderItemsList3.add(folderItems3);
        }
        folderItems2.setChildren(folderItemsList3);
        folderItemsList.add(folderItems2);

        List<FolderItems> root =  new ArrayList<>();
        root=listToTree(folderItemsList);
        return root;

    }
    @Override
    public boolean initFolder(Long clientId){
        GisClientFolder gisClientFolder=new GisClientFolder();
        gisClientFolder.setParentId(-1);
        gisClientFolder.setFolderName("我的数据");
        gisClientFolder.setFolderId(0);
        gisClientFolder.setClientId(clientId);
        gisClientFolderMapper.insert(gisClientFolder);
        return true;
    }
    @Override
    public boolean is_exist(Long clientId){
        int Num = gisClientFolderMapper.getclientId(clientId);
        if(Num!=0){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public List<ClientData> getClientData(long clientId,String description){
        List<ClientData> clientDataList = new ArrayList<>();
        clientDataList= gisClientFileMapper.getClientDataList(clientId,description);
        return clientDataList;
    }
    @Override
    public File downloadFile(String urlPath, String downloadDir) {
        File file = null;
        try {
            // 统一资源
            URL url = new URL(urlPath);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("GET");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();
            // 文件大小
            int fileLength = httpURLConnection.getContentLength();
            // 文件名
            String filePathUrl = httpURLConnection.getURL().getFile();
            File fileStart = new File(filePathUrl);
            String fileFullName=fileStart.getName();
            System.out.println("file length---->" + fileLength);
            URLConnection con = url.openConnection();
            InputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());
            String path = downloadDir +fileFullName ;//fileFullName
            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
                // 打印下载百分比
                // System.out.println("下载了-------> " + len * 100 / fileLength +
                // "%\n");
            }
            bin.close();
            out.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            return file;
        }

    }

}
