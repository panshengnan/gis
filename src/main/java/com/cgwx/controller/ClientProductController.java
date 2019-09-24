package com.cgwx.controller;

import com.cgwx.aop.Permission;
import com.cgwx.aop.result.Result;
import com.cgwx.aop.result.ResultUtil;
import com.cgwx.dao.GisClientFileMapper;
import com.cgwx.dao.GisProductInfoMapper;
import com.cgwx.data.dto.*;
import com.cgwx.data.entity.GisProductInfo;
import com.cgwx.data.entity.GisStandardProductInfo;
import com.cgwx.service.IClientProductService;
import com.cgwx.service.IProductArchiveService;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



@CrossOrigin
@Controller
public class ClientProductController {

    @Autowired
    IClientProductService iClientProductService;
    @Autowired
    IProductArchiveService iProductArchiveService;
    @Autowired
    GisClientFileMapper gisClientFileMapper;
    @Autowired
    GisProductInfoMapper pdmProductInfoMapper;
    @Value("${MetaQueryHead}")
    private String MetaQueryHead;//拼链接

    @RequestMapping(value = "/getProductDetail")
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public JSONObject getProductDetail(@RequestParam(value = "productId", required = true) String productId) {
        try {
            String url = "http://10.10.105.100:18037/metadataapi/metadataQuery/getProductInfoByProductId";
            System.out.println("对端link:" + url);
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset("UTF-8");
            PostMethod postMethod = new PostMethod(url);
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

    @RequestMapping(value = "/getAccountId")
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result getAccountId() {
        Long clientId = iClientProductService.getClientId();
//        UserManagement userManagement = new UserManagement();
//        return Integer.parseInt(userManagement.getUserId().toString());
        System.out.println(clientId);
        return ResultUtil.success(clientId.toString());
    }

    @RequestMapping(value = "/UpdateClientProductList")
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result UpdateClientProductList(@RequestParam(value = "json", required = true) String json) {//引接更新用户标准产品
        System.out.println("更新用户产品列表");
        System.out.println(json);
        JSONObject jsonObject1 = JSONObject.fromObject(json);
        long clientId = Long.parseLong(jsonObject1.getString( "clientId"));
        if(!iClientProductService.is_exist(clientId)){
           System.out.println("初始化当前登录用户树结构文件夹！");
           iClientProductService.initFolder(clientId);
        }
        String  fname = jsonObject1.getString( "downloadUrl").trim();
        String productId =fname.substring(fname.lastIndexOf('/')+1);

        //String productId =jsonObject1.getString( "productId"); //jerry 没给，暂时通过下载链接确定iproductId 2019/09/23
        JSONObject jsonObject2 = getProductDetail(productId).getJSONObject("data"); //调用77元数据查询

        //产品总表归档
        GisProductInfo gisProductInfo = new GisProductInfo();
        gisProductInfo.setProductId(jsonObject2.getString("productId"));
        gisProductInfo.setProductName(jsonObject2.getString("productId"));
        gisProductInfo.setProductType(5);
        int total_prod = iProductArchiveService.updateProductInfo(gisProductInfo);
        if(total_prod==0)
            System.out.println("总表已存在当前产品");
        //标准产品归档
        GisStandardProductInfo gisStandardProductInfo = new GisStandardProductInfo();
        gisStandardProductInfo.setProductId(jsonObject2.getString("productId"));
        gisStandardProductInfo.setSatelliteId(jsonObject2.getString("satelliteId"));
        gisStandardProductInfo.setSensor(jsonObject2.getString("sensor"));
        gisStandardProductInfo.setImagingTimeStr(jsonObject2.getString("imagingTime"));
        gisStandardProductInfo.setImageGsd(new BigDecimal(jsonObject2.getString("imageGsd")));
        gisStandardProductInfo.setCloudPercent(new BigDecimal(jsonObject2.getString("cloudPercent")));
        gisStandardProductInfo.setProductQuality(jsonObject2.getString("productQuality"));
        gisStandardProductInfo.setBandAmount(jsonObject2.getInt("bandAmount"));
        if(jsonObject2.containsKey("bandString")){
            gisStandardProductInfo.setBandString(jsonObject2.getString("bandString"));//当前字段不一定存在
        }
        gisStandardProductInfo.setThumbnail(MetaQueryHead+jsonObject2.getString("thumbnail"));//MetaQueryHead="http://10.10.105.100:18037/metadataapi"
        gisStandardProductInfo.setProductType(jsonObject2.getInt("productType"));
        gisStandardProductInfo.setProductBand(jsonObject2.getString("productBand"));
        gisStandardProductInfo.setRollSatelliteAngle(new BigDecimal(jsonObject2.getString("rollSatelliteAngle")));
        gisStandardProductInfo.setSolarElevation(Float.valueOf(jsonObject2.getString("solarElevation")));
        gisStandardProductInfo.setCenterLatitude(Float.valueOf(jsonObject2.getString("centerLatitude")));
        gisStandardProductInfo.setCenterLongitude(Float.valueOf(jsonObject2.getString("centerLongitude")));

        String geojson = jsonObject2.getString("imageGeo");
        int stand_prod = iProductArchiveService.updateStandardProduct(gisStandardProductInfo,geojson);
        if(stand_prod==0)
            System.out.println("标准数据表已存在当前产品");
        boolean success = iClientProductService.UpdateClientProduct(jsonObject1,jsonObject2);

        if(success){
            return ResultUtil.success("更新用户产品列表成功");
        }else{
            return ResultUtil.success("更新用户产品列表失败");
        }
    }
    @RequestMapping(value = "/UpdateStandardProductList")
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result UpdateStandardProductList(@RequestParam(value = "json", required = true) String json) {//标准产品
        System.out.println("更新用户产品列表");
        System.out.println(json);
        JSONObject jsonObject1 = JSONObject.fromObject(json);
        long clientId = Long.parseLong(jsonObject1.getString( "clientId"));
        String productId =jsonObject1.getString( "productId"); //jerry 没给，暂时通过下载链接确定iproductId 2019/09/23
        JSONObject jsonObject2 = getProductDetail(productId).getJSONObject("data"); //调用77元数据查询

        //产品总表归档
        GisProductInfo gisProductInfo = new GisProductInfo();
        gisProductInfo.setProductId(jsonObject2.getString("productId"));
        gisProductInfo.setProductName(jsonObject2.getString("productId"));
        gisProductInfo.setProductType(5);
        int total_prod = iProductArchiveService.updateProductInfo(gisProductInfo);
        if(total_prod==0)
            System.out.println("总表已存在当前产品");
        //标准产品归档
        GisStandardProductInfo gisStandardProductInfo = new GisStandardProductInfo();
        gisStandardProductInfo.setProductId(jsonObject2.getString("productId"));
        gisStandardProductInfo.setSatelliteId(jsonObject2.getString("satelliteId"));
        gisStandardProductInfo.setSensor(jsonObject2.getString("sensor"));
        gisStandardProductInfo.setImagingTimeStr(jsonObject2.getString("imagingTime"));
        gisStandardProductInfo.setImageGsd(new BigDecimal(jsonObject2.getString("imageGsd")));
        gisStandardProductInfo.setCloudPercent(new BigDecimal(jsonObject2.getString("cloudPercent")));
        gisStandardProductInfo.setProductQuality(jsonObject2.getString("productQuality"));
        gisStandardProductInfo.setBandAmount(jsonObject2.getInt("bandAmount"));
        if(jsonObject2.containsKey("bandString")){
            gisStandardProductInfo.setBandString(jsonObject2.getString("bandString"));//当前字段不一定存在
        }
        gisStandardProductInfo.setThumbnail(MetaQueryHead+jsonObject2.getString("thumbnail"));//MetaQueryHead="http://10.10.105.100:18037/metadataapi"
        gisStandardProductInfo.setProductType(jsonObject2.getInt("productType"));
        gisStandardProductInfo.setProductBand(jsonObject2.getString("productBand"));
        gisStandardProductInfo.setRollSatelliteAngle(new BigDecimal(jsonObject2.getString("rollSatelliteAngle")));
        gisStandardProductInfo.setSolarElevation(Float.valueOf(jsonObject2.getString("solarElevation")));
        gisStandardProductInfo.setCenterLatitude(Float.valueOf(jsonObject2.getString("centerLatitude")));
        gisStandardProductInfo.setCenterLongitude(Float.valueOf(jsonObject2.getString("centerLongitude")));

        String geojson = jsonObject2.getString("imageGeo");
        int stand_prod = iProductArchiveService.updateStandardProduct(gisStandardProductInfo,geojson);
        if(stand_prod==0)
            System.out.println("标准数据表已存在当前产品");
        boolean success = iClientProductService.UpdateClientProduct(jsonObject1,jsonObject2);

        if(success){
            return ResultUtil.success("更新用户产品列表成功");
        }else{
            return ResultUtil.success("更新用户产品列表失败");
        }
    }

    @RequestMapping(value = "/getClientData")//创建项目——我的数据列表
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result getClientData(@RequestParam(value = "description", required = true) String description) {
        long clientId = iClientProductService.getClientId();
        List<ClientData> clientDataList = new ArrayList<>();
        clientDataList= iClientProductService.getClientData(clientId,description);
        return ResultUtil.success(clientDataList);
    }



    @RequestMapping(value = "/getClientProductList")  //我的数据——三种展示方式
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result getClientProductList(@RequestParam(value = "style", required = true) int style) {//1 按文件夹 2 按类别 3 按级别
        long clientId = iClientProductService.getClientId();
        if(!iClientProductService.is_exist(clientId)){
            System.out.println("初始化当前登录用户树结构文件夹！");
            iClientProductService.initFolder(clientId);
        }
        switch (style){
            case 1 :
                folderDto folderDto1 = new folderDto();
                folderDto1.setItemsTree(iClientProductService.buildFolderTree(clientId));
                return ResultUtil.success(folderDto1);
            case 2 :
                folderDto folderDto2 = new folderDto();
                folderDto2.setItemsTree(iClientProductService.getClientFileByType(clientId));
                return ResultUtil.success(folderDto2);
            case 3 :
                folderDto folderDto3 = new folderDto();
                folderDto3.setItemsTree(iClientProductService.getClientFileByClass(clientId));
                return ResultUtil.success(folderDto3);
            default :
                return ResultUtil.success("获取用户产品列表失败");
        }

    }

//    @RequestMapping(value = "/test2")  //客户产品列表
//    @CrossOrigin(methods = RequestMethod.GET)
//    @ResponseBody
//    public Result test2() {
//        folderDto folderDto1 = new folderDto();
//        long clientId = Long.parseLong("1169500391481835522");
//        folderDto1.setItemsTree(iClientProductService.buildFolderTree(clientId));
//        return ResultUtil.success(folderDto1);
//
//    }


    @RequestMapping(value = "/getClientProductDetail")  //我的数据详情
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result getClientProductDetail(@RequestParam(value = "productId", required = true) int productId) {//1 按文件夹 2 按类别 3 按级别
        long clientId = iClientProductService.getClientId();
        String realId = gisClientFileMapper.getProductIdbylogicid(clientId,productId);
        return ResultUtil.success("移动文件夹失败");

    }

    @RequestMapping(value = "/MoveFolder")  //移动
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result MoveFolder(@RequestParam(value = "sourceId", required = true) int sourceId,
                           @RequestParam(value = "descId", required = true) int descId) { //,@RequestParam(value = "clientId", required = true) int clientId
        long clientId = iClientProductService.getClientId();
        if(iClientProductService.moveFolder(sourceId,descId,clientId)){
            System.out.println("移动文件夹成功" );
            List<FolderItems> item = iClientProductService.buildFolderTree(clientId);
            folderDto folderDto = new folderDto();
            folderDto.setItemsTree(item);
            return ResultUtil.success(folderDto);
            }
        return ResultUtil.success("移动文件夹失败");

    }

    @RequestMapping(value = "/AddFolder")  //新建
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result AddFolder(@RequestParam(value = "folderName", required = true) String folderName,
                           @RequestParam(value = "parentId", required = true) int parentId) { // ,@RequestParam(value = "clientId", required = true)int clientId
        long clientId = iClientProductService.getClientId();
        if(iClientProductService.addFolder(folderName,parentId,clientId)){
            System.out.println("新建文件夹成功" );
            List<FolderItems> item = iClientProductService.buildFolderTree(clientId);
            folderDto folderDto = new folderDto();
            folderDto.setItemsTree(item);
            return ResultUtil.success(folderDto);
        }
            return ResultUtil.success("新建文件夹失败");
    }

    @RequestMapping(value = "/DeleteFolder")  //删除
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result DeleteFolder(@RequestParam(value = "folderId", required = true) int folderId) { //,@RequestParam(value = "clientId", required = true)int clientId
        long clientId = iClientProductService.getClientId();
        if(iClientProductService.deleteFolder(folderId,clientId)){
            System.out.println("删除文件夹成功" );
            List<FolderItems> item = iClientProductService.buildFolderTree(clientId);
            folderDto folderDto = new folderDto();
            folderDto.setItemsTree(item);
            return ResultUtil.success(folderDto);
        }
            return ResultUtil.success("删除文件夹失败");
    }

    @RequestMapping(value = "/RenameFolder")  //重命名
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result RenameFolder(@RequestParam(value = "folderId", required = true) int folderId,
                          @RequestParam(value = "newName", required = true)String newName) { // ,@RequestParam(value = "clientId", required = true)int clientId
        long clientId = iClientProductService.getClientId();
        if(iClientProductService.RenameFolder(folderId,newName,clientId)){
            System.out.println("修改文件夹名称成功" );
            List<FolderItems> item = iClientProductService.buildFolderTree(clientId);
            folderDto folderDto = new folderDto();
            folderDto.setItemsTree(item);
            return ResultUtil.success(folderDto);
        }
            return ResultUtil.success("修改文件夹名称失败");
    }

    @RequestMapping(value = "/Movefile")  //移动文件
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result Movefile(@RequestParam(value = "productId", required = true) int productId,
                             @RequestParam(value = "descId", required = true)int descId) { //, @RequestParam(value = "clientId", required = true)int clientId
        long clientId = iClientProductService.getClientId();
        if(iClientProductService.moveFlie(productId,descId,clientId)){
            System.out.println("移动文件成功" );
            List<FolderItems> item = iClientProductService.buildFolderTree(clientId);
            folderDto folderDto = new folderDto();
            folderDto.setItemsTree(item);
            return ResultUtil.success(folderDto);
        }
            return ResultUtil.success("移动文件失败");
    }

//    @RequestMapping(value = "/test2")  //客户产品列表
//    @CrossOrigin(methods = RequestMethod.GET)
//    @ResponseBody
//    public Result test2() {
//
//        List<FolderItems> folderItemsList = new ArrayList<>();
//        folderItemsList.add(new FolderItems(0,-1,"全部数据"));
//        folderItemsList.add(new FolderItems(1,0,"我的数据"));
//        folderItemsList.add(new FolderItems(2,0,"我的数据2"));
//        folderItemsList.add(new FolderItems(3,1,"我的数据3"));
//        folderItemsList.add(new FolderItems(4,3,"我的数据4"));
//        folderItemsList.add(new FolderItems(5,4,"我的数据5"));
//        folderItemsList.add(new FolderItems(6,2,"我的数据6"));
//        folderItemsList.add(new FolderItems(7,2,"我的数据7"));
//        List<FolderItems> item = iClientProductService.listToTree(folderItemsList);
//        folderDto folderDto = new folderDto();
//        folderDto.setItemsTree(item);
//        return ResultUtil.success(folderDto);
//    }
    @RequestMapping(value = "/testTree")  //客户产品列表
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result testTree(@RequestParam(value = "clientId", required = true) int clientId) {

        List<FolderItems> item = iClientProductService.buildFolderTree(clientId);
        folderDto folderDto = new folderDto();
        folderDto.setItemsTree(item);
        return ResultUtil.success(folderDto);
    }
}
