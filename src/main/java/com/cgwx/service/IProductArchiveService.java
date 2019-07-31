package com.cgwx.service;

import com.cgwx.data.dto.SecondaryFileStructure;
import com.cgwx.data.dto.UploadFileReturn;
import com.cgwx.data.entity.*;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONException;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by PanSN on 2018/9/5.
 */

public interface IProductArchiveService {

    void updateXml(Document document, GisThemeticProductInfo pdmThemeticProductInfo);
    void update(Document document, String fileName);
    String changeTiftoJpg(String fileName);
    String ZoomImg(String fileName);
    String getUUId();
    String getNextProductId(int productType);
    List<String> getFileNameList(String productId);
    SecondaryFileStructure getSecondaryFileStructure(String path);
    SecondaryFileStructure getSecondaryFileStructureAndWriteCheckTable(String path, String archivePersonnel, int type);
    void copyFolder(String oldPath, String newPath);
    void copyFile(File source, File dest);
    UploadFileReturn uploadFile(MultipartFile file);
    String unZip(String fileName, String filePath, String codeType) throws IOException;
    void zip(String srcFile, String dest, String passwd);
    List<String> getClientNameList(String clientName);
    List<String> getDeliverNameList(String deliverName);
    List<String> getProducerList(String producer);
    int updateProductInfo(GisProductInfo pdmProductInfo);
    String getProductName(String tempId);
    String getThemeticProductTemporaryPath(String tempId);
    int updateThemeticProductDetailIndustry(GisThemeticProductDetailIndustryInfo pdmThemeticProductDetailIndustryInfo);
    int updateThemeticProductDetail(GisThemeticProductDetailInfo pdmThemeticProductDetailInfo);
    int updateThemeticProduct(GisThemeticProductInfo pdmThemeticProductInfo);
    int insertPdmProducerInfo(String producerName);
    int selectCountByProducerName(String producerName);
    int updatePdmProducerInfo(String producerName);
    PageInfo<GisArchiveRecordsInfo> getArchiveRecordList(String archivePersonnel, int curPageNum, int maxResult, String productName, int archiveType, int archiveStatus);
    int updateArchiveRecordsInfo(GisArchiveRecordsInfo pdmArchiveRecordsInfo, String tempId, int productType);
    String getArchivePersonnelName(String archivePersonnel);
    String writeArchiveRecordAndWriteArchiveCheckInfo(String path, String archivePersonnel, int type);
    int updateOrthoProduct(GisOrthoProductInfo pdmOrthoProductInfo);
    int updateInlayProduct(GisInlayProductInfo pdmInlayProductInfo);
    int updateSubdivisionProduct(GisSubdivisionProductInfo pdmSubdivisionProductInfo);
    int updateAdvancedProductShpInfo(GisAdvancedProductShpInfo pdmAdvancedProductShpInfo);
    String xml2jsonString(String path)throws JSONException, IOException;
    List<String> getSatelliteList();
    List<String> getSensorList();
    String getXmlFilePath(String parentPath);
    void unzipTest(String path);
    String getZipCodeType(String source) throws IOException;
    String productCheck(int productType);
    boolean isExistTif(String path);
    boolean isExistShp(String path);
    boolean isExistJpg(String path);
    void exportZip(List<String> fileNames, File zip) throws FileNotFoundException;
    List<String> getShpFileList(String path);
    void rename(String path, String newName);
    String combination(String key, String value, boolean isEnd);
}
