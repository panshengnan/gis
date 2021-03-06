package com.cgwx.service.impl;

import com.cgwx.dao.*;
import com.cgwx.data.dto.DirectoryInfo;
import com.cgwx.data.dto.SecondaryFileStructure;
import com.cgwx.data.entity.GisProductStoreLinkInfo;
import com.cgwx.service.IProductArchiveService;
import com.cgwx.service.IProductDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class IProductDownloadServiceImpl implements IProductDownloadService {

    @Autowired
    GisThemeticProductInfoMapper pdmThemeticProductInfoMapper;

    @Autowired
    GisOrthoProductInfoMapper pdmOrthoProductInfoMapper;

    @Autowired
    GisInlayProductInfoMapper pdmInlayProductInfoMapper;

    @Autowired
    GisSubdivisionProductInfoMapper pdmSubdivisionProductInfoMapper;

    @Autowired
    GisProductStoreLinkInfoMapper pdmProductStoreLinkInfoMapper;

    @Autowired
    IProductArchiveService iProductArchiveService;

    @Value("${productStoreLinkHead}")
    private String productStoreLinkHead;//拼链接再用吧

    @Override/*根据文件名和路径下载文件*/
    public String downloadFile(HttpServletRequest request, HttpServletResponse response, String fileName, String filePath) {

        if (fileName != null) {
            String realPath = filePath;
            File file = new File(realPath, fileName);
            if (file.exists()) {
                try {
                    response.setContentType("multipart/form-data;charset=UTF-8");
                    response.setHeader("Content-Disposition", "attachment; fileName=" + fileName + ";filename*=utf-8''" + URLEncoder.encode(fileName, "UTF-8"));
                } catch (Exception e) {
                }
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("文件下载完成！");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "success";
    }

    @Override /*根据产品id获取产品完整父路径*/
    public String getEntityFilePath(String productId) {

        String path = "";
        String productType = productId.substring(0, 3);
        switch (productType) {
            case "THE":
                path = pdmThemeticProductInfoMapper.selectFilePathByProductId(productId);
                break;
            case "ORT":
                path = pdmOrthoProductInfoMapper.selectFilePathByProductId(productId);
                break;
            case "INL":
                path = pdmInlayProductInfoMapper.selectFilePathByProductId(productId);
                break;
            case "SUB":
                path = pdmSubdivisionProductInfoMapper.selectFilePathByProductId(productId);
                break;
            default:
                break;
        }
        return path;
    }

    @Override /*根据产品id以及单期产品id获取产品存储连接*/
    public List<GisProductStoreLinkInfo> getProductLinkList(String productId, String singlePeriodProductId) {

        List<GisProductStoreLinkInfo> productLinkList = new ArrayList<GisProductStoreLinkInfo>();
        if (singlePeriodProductId == null || singlePeriodProductId.equals("")) {
            productLinkList = pdmProductStoreLinkInfoMapper.selectProductStoreLinksByProductId(productId);
        } else {
            productLinkList = pdmProductStoreLinkInfoMapper.selectProductStoreLinksByProductIdAndsingleId(productId, singlePeriodProductId);
        }
        return productLinkList;
    }

    @Override/*高级产品heihei*/
    public int generateProductLink(int productType, String productId, String productName) {

        String productPathVar = "";
        switch (productType) {

            case 1://专题
                productPathVar = "专题产品/";
                break;
            case 2://正射
                productPathVar = "正射产品/";
                break;
            case 3://镶嵌
                productPathVar = "镶嵌产品/";
                break;
            case 4://分幅
                productPathVar = "分幅产品/";
                break;
            default:
                break;
        }

        String path = getEntityFilePath(productId);
        System.out.println("产品路径为："+path);
        SecondaryFileStructure secondaryFileStructure = iProductArchiveService.getSecondaryFileStructure(path);
        List<DirectoryInfo> directoryInfoList = secondaryFileStructure.getDirectory();
        List<String> files = secondaryFileStructure.getFile();
        for (String file : files) {
            GisProductStoreLinkInfo pdmProductStoreLinkInfo = new GisProductStoreLinkInfo();
            pdmProductStoreLinkInfo.setProductId(productId);
            pdmProductStoreLinkInfo.setSinglePeriodProductId("");
            pdmProductStoreLinkInfo.setFileName(file);
            pdmProductStoreLinkInfo.setStoreLink(productPathVar + productName + '/' + file);
            System.out.println("生成的连接为："+pdmProductStoreLinkInfo.getStoreLink());
            pdmProductStoreLinkInfoMapper.insert(pdmProductStoreLinkInfo);
        }
        for (DirectoryInfo directoryInfo : directoryInfoList) {
            List<String> fileList = directoryInfo.getFileListInDirectory();
            for (String fileName : fileList) {
                GisProductStoreLinkInfo pdmProductStoreLinkInfo = new GisProductStoreLinkInfo();
                pdmProductStoreLinkInfo.setProductId(productId);
                pdmProductStoreLinkInfo.setSinglePeriodProductId(directoryInfo.getSingleTempId());
                pdmProductStoreLinkInfo.setFileName(fileName);
                pdmProductStoreLinkInfo.setStoreLink(productPathVar + productName + '/' + directoryInfo.getDirectoryName() + '/' + fileName);
                pdmProductStoreLinkInfoMapper.insert(pdmProductStoreLinkInfo);
                System.out.println("生成的连接2为："+pdmProductStoreLinkInfo.getStoreLink());
            }
        }

        return 1;
    }

}
