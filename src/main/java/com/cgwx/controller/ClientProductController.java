package com.cgwx.controller;

import com.cgwx.aop.result.Result;
import com.cgwx.aop.result.ResultUtil;
import com.cgwx.data.dto.ClientFileByType;
import com.cgwx.data.dto.ClientFileInfo;
import com.cgwx.data.dto.FolderItems;
import com.cgwx.data.dto.folderDto;
import com.cgwx.data.entity.GisClientFile;
import com.cgwx.service.IClientProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
public class ClientProductController {

    @Autowired
    IClientProductService iClientProductService;

    @RequestMapping(value = "/getClientProductList")  //客户产品列表
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result ThemeticProductDetail(@RequestParam(value = "clientId", required = true) int clientId,//1 按文件夹 2 按类别 3 按级别
                                        @RequestParam(value = "style", required = true) int style) {
        switch (style){
            case 1 :
                folderDto folderDto = new folderDto();
                folderDto.setItemsTree(iClientProductService.buildFolderTree(clientId));
                return ResultUtil.success(folderDto);
            case 2 :
                List<ClientFileByType> clientFileByTypeList = new ArrayList<>();
                clientFileByTypeList =iClientProductService.getClientFileByType(clientId);
                return ResultUtil.success(clientFileByTypeList);
            case 3 :

            default :
                return ResultUtil.success("获取用户产品列表失败");
        }

    }
    @RequestMapping(value = "/MoveFolder")  //客户产品列表
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result MoveFolder(@RequestParam(value = "sourceId", required = true) int sourceId,
                           @RequestParam(value = "descId", required = true) int descId,
                           @RequestParam(value = "clientId", required = true) int clientId) { //int sourceId,int descId,int clientId


        if(iClientProductService.moveFolder(sourceId,descId,clientId)){
            System.out.println("移动文件夹成功" );
            List<FolderItems> item = iClientProductService.buildFolderTree(clientId);
            folderDto folderDto = new folderDto();
            folderDto.setItemsTree(item);
            return ResultUtil.success(folderDto);
            }
        return ResultUtil.success("新建文件夹失败");

    }

    @RequestMapping(value = "/AddFolder")  //客户产品列表 int folderId,String folderName,int parentId,int clientId
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result AddFolder(@RequestParam(value = "folderId", required = true) int folderId,
                           @RequestParam(value = "folderName", required = true) String folderName,
                           @RequestParam(value = "parentId", required = true) int parentId,
                          @RequestParam(value = "clientId", required = true)int clientId) { //int sourceId,int descId,int clientId

//        List<FolderItems> item = iClientProductService.buildFolderTree(clientId);
//        folderDto folderDto = new folderDto();
//        folderDto.setItemsTree(item);
        if(iClientProductService.addFolder(folderId,folderName,parentId,clientId)){
            System.out.println("新建文件夹成功" );
            List<FolderItems> item = iClientProductService.buildFolderTree(clientId);
            folderDto folderDto = new folderDto();
            folderDto.setItemsTree(item);
            return ResultUtil.success(folderDto);
        }
            return ResultUtil.success("新建文件夹失败");
    }

    @RequestMapping(value = "/DeleteFolder")  //客户产品列表 int folderId,String folderName,int parentId,int clientId
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result DeleteFolder(@RequestParam(value = "folderId", required = true) int folderId,
                          @RequestParam(value = "clientId", required = true)int clientId) { //int sourceId,int descId,int clientId

//        List<FolderItems> item = iClientProductService.buildFolderTree(clientId);
//        folderDto folderDto = new folderDto();
//        folderDto.setItemsTree(item);
        if(iClientProductService.deleteFolder(folderId,clientId)){
            System.out.println("删除文件夹成功" );
            List<FolderItems> item = iClientProductService.buildFolderTree(clientId);
            folderDto folderDto = new folderDto();
            folderDto.setItemsTree(item);
            return ResultUtil.success(folderDto);
        }
            return ResultUtil.success("删除文件夹失败");
    }

    @RequestMapping(value = "/RenameFolder")  //客户产品列表 int folderId,String folderName,int parentId,int clientId
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result RenameFolder(@RequestParam(value = "folderId", required = true) int folderId,
                          @RequestParam(value = "newName", required = true)String newName,
                             @RequestParam(value = "clientId", required = true)int clientId) { //int sourceId,int descId,int clientId

//        List<FolderItems> item = iClientProductService.buildFolderTree(clientId);
//        folderDto folderDto = new folderDto();
//        folderDto.setItemsTree(item);
        if(iClientProductService.RenameFolder(folderId,newName,clientId)){
            System.out.println("修改文件夹名称成功" );
            List<FolderItems> item = iClientProductService.buildFolderTree(clientId);
            folderDto folderDto = new folderDto();
            folderDto.setItemsTree(item);
            return ResultUtil.success(folderDto);
        }
            return ResultUtil.success("修改文件夹名称失败");
    }

    @RequestMapping(value = "/Movefile")  //客户产品列表 int folderId,String folderName,int parentId,int clientId
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result Movefile(@RequestParam(value = "productId", required = true) int productId,
                             @RequestParam(value = "descId", required = true)int descId,
                             @RequestParam(value = "clientId", required = true)int clientId) { //int sourceId,int descId,int clientId

//        List<FolderItems> item = iClientProductService.buildFolderTree(clientId);
//        folderDto folderDto = new folderDto();
//        folderDto.setItemsTree(item);
        if(iClientProductService.moveFlie(productId,descId,clientId)){
            System.out.println("移动文件成功" );
            List<FolderItems> item = iClientProductService.buildFolderTree(clientId);
            folderDto folderDto = new folderDto();
            folderDto.setItemsTree(item);
            return ResultUtil.success(folderDto);
        }
            return ResultUtil.success("移动文件失败");
    }

    @RequestMapping(value = "/test2")  //客户产品列表
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result test2() {

        List<FolderItems> folderItemsList = new ArrayList<>();
        folderItemsList.add(new FolderItems(0,-1,"全部数据"));
        folderItemsList.add(new FolderItems(1,0,"我的数据"));
        folderItemsList.add(new FolderItems(2,0,"我的数据2"));
        folderItemsList.add(new FolderItems(3,1,"我的数据3"));
        folderItemsList.add(new FolderItems(4,3,"我的数据4"));
        folderItemsList.add(new FolderItems(5,4,"我的数据5"));
        folderItemsList.add(new FolderItems(6,2,"我的数据6"));
        folderItemsList.add(new FolderItems(7,2,"我的数据7"));
        List<FolderItems> item = iClientProductService.listToStree(folderItemsList);
        folderDto folderDto = new folderDto();
        folderDto.setItemsTree(item);
        return ResultUtil.success(folderDto);
//        return ResultUtil.success("");
    }
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
