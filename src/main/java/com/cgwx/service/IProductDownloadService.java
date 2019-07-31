package com.cgwx.service;

import com.cgwx.data.entity.GisProductStoreLinkInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IProductDownloadService {

    String getEntityFilePath(String productId);
    String downloadFile(HttpServletRequest request, HttpServletResponse response, String fileName, String filePath);
    List<GisProductStoreLinkInfo> getProductLinkList(String productId, String singlePeriodProductId);
    int generateProductLink(int productType, String productId, String productName);
}
