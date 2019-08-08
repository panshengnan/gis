package com.cgwx.service;

import com.cgwx.aop.result.Result;
import com.cgwx.data.dto.AttributeInfoDto;
import com.cgwx.data.dto.AttributeValuesDto;
import com.cgwx.data.dto.SldPathAndNameDto;
import com.linuxense.javadbf.DBFException;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IlayerPublishService {


    boolean publishStyle(File sldFile, String styleName);
    boolean updateStyle(File sldFile, String styleName);
    boolean updateStyle(String sldBody, String styleName);
    boolean publishShp(String workSpace,String dataStore,String layerName,File zipFileSource,String srs,String style);
    boolean publishShp(String workSpace,String dataStore,String layerName,String filePath,File sldFile);
    boolean publishShpForArchive(String productId,String singleId,String dataStore,String layerName,String filePath,String sldPath,String legendUrl);
    void rename(String path, String newName);
    List<String> getShpFileList(String path);
    String getShpPathWithoutCutline(String parentPath);
    void exportZip(List<String> fileNames, File zip) throws FileNotFoundException;
    String getShpEPSG(String path) throws FactoryException, TransformException;
    SimpleFeatureCollection readShp(String path);
    String xml2jsonString(String path) throws JSONException, IOException;
    void writeFile(String fileEntityPath,String content);
    String json2xml(String jsonString);
    String generateSingleRuleXml(String attributeName,String attributeValue,String titleName, String fillColor, String fillOpacity, String strokeColor, String strokeOpacity);
    String generateRuleXml(String attributeName, int valueCount, List<AttributeInfoDto> attributeInfoDtoList);
    String generateSldBody(String targetXml,String ruleXml);
    SldPathAndNameDto getSldPathAndName(String productId,String singleId);
    List<String> getAttributeList(String productId,String singleId);
    AttributeValuesDto getAttributeValueInfo(String productId,String singleId,String attributeName);
    List<JSONObject> getAdvancedProductShpInfo(String path);
    String transformMultiPolygonToPolygon(JSONObject jsonObject);
    JSONObject getGj(String wkt);
    List<String> readDBF(String path)throws Exception ;
    void writeDBF(String path,List<String> attributeList) throws Exception;
    void testWriteAndReadAgain() throws DBFException, IOException;
    List<String> getAttributeList(List<String> attributeList);
    List<String> getAttributeListForArchive(List<String> attributeList,String productId,String singleId);
    String getAttributeFlag(String productId,String singleId,String attributeName);
    String getAttributeOtherValue(String productId,String singleId,String attributeName,String attributeValue);
    String getAttributeOtherName(String productId,String singleId,String attributeName);
    String getDbfPath(String path);
    Result publishTifToGeoserver(JSONObject jsonObject) throws ParserConfigurationException, IOException;
    JSONObject publishTif(String filePath, String nameSpace, String borderColor) throws IOException, FactoryException, TransformException;
    String getUUId();
    String transformPOLYGONToPolygon(JSONObject jsonObject);
}
