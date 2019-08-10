package com.cgwx.service.impl;

import com.cgwx.aop.result.Result;
import com.cgwx.aop.result.ResultUtil;
import com.cgwx.common.constants.LayerInfo;
import com.cgwx.common.utils.GeoserverXml;
import com.cgwx.common.utils.PublishLayer;
import com.cgwx.dao.GisProductLayerInfoMapper;
import com.cgwx.dao.GisProductShpAttributeDetailInfoMapper;
import com.cgwx.dao.GisProductShpAttributeInfoMapper;
import com.cgwx.dao.GisThemeticProductDetailInfoMapper;
import com.cgwx.data.dto.AttributeInfoDto;
import com.cgwx.data.dto.AttributeValuesDto;
import com.cgwx.data.dto.SldPathAndNameDto;
import com.cgwx.data.entity.GisProductLayerInfo;
import com.cgwx.data.entity.GisProductShpAttributeDetailInfo;
import com.cgwx.data.entity.GisProductShpAttributeInfo;
import com.cgwx.service.IlayerPublishService;

import com.linuxense.javadbf.*;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.gce.geotiff.GeoTiffFormat;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.json.XML;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.Filter;
import org.opengis.geometry.Envelope;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.geotools.resources.Classes.CHARACTER;
import static org.geotools.resources.Classes.primitiveToWrapper;

@Service
public class LayerPublishServiceImpl implements IlayerPublishService {

    @Value("${geoserverPath}")
    private String RESTURL;

    @Value("${geoserverUsername}")
    private String RESTUSER;

    @Value("${geoserverPassword}")
    private String RESTPW;

    @Autowired
    private GisProductLayerInfoMapper gisProductLayerInfoMapper;

    @Autowired
    private GisProductShpAttributeInfoMapper gisProductShpAttributeInfoMapper;

    @Autowired
    private GisProductShpAttributeDetailInfoMapper gisProductShpAttributeDetailInfoMapper;

    @Autowired
    private GisThemeticProductDetailInfoMapper gisThemeticProductDetailInfoMapper;

    @Autowired
    LayerInfo layerInfo;


    public boolean publishStyle(File sldFile, String styleName) {

        try {
            GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(RESTURL, RESTUSER, RESTPW);
            publisher.publishStyle(sldFile, styleName);
        } catch (Exception mue) {
            mue.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateStyle(File sldFile, String styleName) {

        try {
            GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(RESTURL, RESTUSER, RESTPW);
            publisher.updateStyle(sldFile, styleName);
        } catch (Exception mue) {
            mue.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateStyle(String sldBody, String styleName) {

        try {
            GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(RESTURL, RESTUSER, RESTPW);
            publisher.updateStyle(sldBody, styleName);
        } catch (Exception mue) {
            mue.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean publishShp(String workSpace, String dataStore, String layerName, File zipFileSource, String srs, String style) {

        List<String> workspaces = null;
        String result = "";
        try {
            GeoServerRESTReader reader = new GeoServerRESTReader(RESTURL, RESTUSER, RESTPW);
            GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(RESTURL, RESTUSER, RESTPW);
            workspaces = reader.getWorkspaceNames();
            if (workspaces.contains(workSpace)) {
                publisher.createWorkspace(workSpace);
            }
            if (publisher.publishShp(workSpace, dataStore, layerName, zipFileSource, srs, style)) {
                result = "发布成功！";
            } else {
                result = "发布失败！";
            }
        } catch (Exception mue) {
            mue.printStackTrace();
            return false;
        }
        System.out.println(result);
        return true;
    }

    public boolean publishShp(String workSpace, String dataStore, String layerName, String filePath, File sldFile) {

        try {
            File zipFileSource = new File(filePath + "\\temp.zip");
            rename(filePath, layerName);
            List<String> fileList = getShpFileList(filePath);
            exportZip(fileList, zipFileSource);
            String srs = getShpEPSG(getShpPathWithoutCutline(filePath));
            String style = "generic";
            if (sldFile != null) {
                style = sldFile.getName();
                style = style.substring(0, style.indexOf("."));
                publishStyle(sldFile, style);
            }
            publishShp(workSpace, dataStore, layerName, zipFileSource, srs, style);
            zipFileSource.delete();
            rename(filePath, filePath.substring(filePath.lastIndexOf("\\") + 1));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void updateThemeticProductDetailImgGeo(String productId, String singleId, String geoJson) {

        gisThemeticProductDetailInfoMapper.updateThemeticProductDetailImgGeo(productId, singleId, geoJson);
    }

    @Override
    public String getShpLatLonBounding(String path) throws FactoryException, TransformException {

        SimpleFeatureCollection colls1 = this.readShp(path);
        Envelope envelope = colls1.getBounds();
        MathTransform transform = CRS.findMathTransform(colls1.getBounds().getCoordinateReferenceSystem(), DefaultGeographicCRS.WGS84);
        com.vividsolutions.jts.geom.Envelope envelope1 = new com.vividsolutions.jts.geom.Envelope(envelope.getMinimum(0), envelope.getMaximum(0), envelope.getMinimum(1), envelope.getMaximum(1));
        envelope1 = JTS.transform(envelope1, transform);
        com.vividsolutions.jts.geom.Polygon polygon = JTS.toGeometry(envelope1);
        System.out.println(polygon);
        return transformPOLYGONToPolygon(getGj(polygon.toString()));
    }

    @Async("taskExecutor")
    @Override
    public Object publishThemeShpForArchive(String productId, String singleId, String dataStore, String layerName, String filePath, String sldPath, String legendUrl) {

        File styleFile;
        String style = "generic";
        if (sldPath != null && !sldPath.equals("")) {
            styleFile = new File(sldPath);
            style = styleFile.getName();
            System.out.println("style:" + style);
            style = style.substring(0, style.indexOf("."));
        } else {
            styleFile = null;
        }
        try {
            String dbfPath = getDbfPath(filePath);
            String dbfName = dbfPath.substring(0, dbfPath.lastIndexOf("."));
            System.out.println("dbf的名字是：" + dbfName);
            String tmpDbfFilePath = filePath + "\\tmp.dbf";
            writeDBF(tmpDbfFilePath, getAttributeListForArchive(readDBF(dbfPath), productId, singleId));
            File tmpDbfFile = new File(filePath + "\\tmp.dbf");
            File dbfFile = new File(dbfPath);
            dbfFile.delete();
            tmpDbfFile.renameTo(new File(dbfPath));
        } catch (Exception e) {
            System.out.println("读写dbf文件出错！");
            return false;
        }
        try {
            String shpPath = getShpPathWithoutCutline(filePath);
            String geoJson = getShpLatLonBounding(shpPath);
            updateThemeticProductDetailImgGeo(productId, singleId, geoJson);
        } catch (Exception e) {
            System.out.println("获取边界出错！");
            return false;
        }
        if (publishShp("layerPublish", layerName, layerName, filePath, styleFile)) {
            GisProductLayerInfo gisProductLayerInfo = new GisProductLayerInfo();
            gisProductLayerInfo.setProductId(productId);
            gisProductLayerInfo.setLayerName("layerPublish:" + layerName);
            gisProductLayerInfo.setSingleId(singleId);
            gisProductLayerInfo.setSldPath(sldPath);
            gisProductLayerInfo.setStyleName(style);
            gisProductLayerInfo.setLegend(legendUrl);
            gisProductLayerInfo.setIsShp("true");
            gisProductLayerInfoMapper.insert(gisProductLayerInfo);
        } else {
            System.out.println("发布出错！");
            return false;
        }
        return true;
    }

    @Override
    public List<String> getAttributeListForArchive(List<String> attributeList, String productId, String singleId) {
        List<String> attributeListCopy = new ArrayList<>();
        String header = attributeList.get(0);
        String[] attribute = header.split(",");
        int length = attribute.length;
        System.out.println("长度是：" + length);
        List<Integer> positon = new ArrayList<>();

        String[] attributeValue = attributeList.get(2).replace("[", "").replace("]", "").split(",");//2!
        for (int j = 0; j < length; j++) {
            System.out.println("1111" + attributeValue[j]);
            if (checkcountname(attributeValue[j])) {
//                length++;
                System.out.println("222");
                positon.add(j);
            }
        }
        //写attribute表
        int otherNameCount = 0;
        for (int index = 0; index < length; index++) {
            GisProductShpAttributeInfo gisProductShpAttributeInfo = new GisProductShpAttributeInfo();
            gisProductShpAttributeInfo.setProductId(productId);
            gisProductShpAttributeInfo.setSingleId(singleId);
            gisProductShpAttributeInfo.setAttributeName(attribute[index]);
            for (int pos = 0; pos < positon.size(); pos++) {
                if (index == positon.get(pos)) {
                    gisProductShpAttributeInfo.setFlag("true");
                    gisProductShpAttributeInfo.setOtherName("rule" + otherNameCount++);
                }
            }
            gisProductShpAttributeInfoMapper.insert(gisProductShpAttributeInfo);
        }

        //定义value_count列表
        int[] valueCountList = new int[length];

        //写attributeDetail表
        int[] rowCount = new int[length];
        for (int rowNo = 0; rowNo < length; rowNo++)
            rowCount[rowNo] = 0;

        int[] valueCount = new int[length];///////////////////
        for (int valNo = 0; valNo < length; valNo++)
            valueCount[valNo] = 0;

        for (int j = 1; j < attributeList.size(); j++) {
            String tmp = attributeList.get(j).replace("[", "").replace("]", "");
            String[] attributeValues = tmp.split(",");
            for (int valueIndex = 0; valueIndex < attributeValues.length; valueIndex++) {
                int attributeId = gisProductShpAttributeInfoMapper.getId(productId, singleId, attribute[valueIndex]);
                GisProductShpAttributeDetailInfo gisProductShpAttributeDetailInfo = new GisProductShpAttributeDetailInfo();
                gisProductShpAttributeDetailInfo.setAttributeId(attributeId);
                gisProductShpAttributeDetailInfo.setAttributeValue(attributeValues[valueIndex]);
                for (int pos = 0; pos < positon.size(); pos++) {
                    if (valueIndex == positon.get(pos)) {
                        gisProductShpAttributeDetailInfo.setOtherValue(String.valueOf(rowCount[valueIndex]++));
                    }
                }
                gisProductShpAttributeDetailInfoMapper.insert(gisProductShpAttributeDetailInfo);
            }
        }

        //获得valueCount
        for (int index = 0; index < length; index++) {
            int attributeId = gisProductShpAttributeInfoMapper.getId(productId, singleId, attribute[index]);
            int valueNum = gisProductShpAttributeDetailInfoMapper.getAttributeValueCount(attributeId);
            gisProductShpAttributeInfoMapper.updateValueCount(productId, singleId, attribute[index], valueNum);
        }


        int countHead = 0;
        int countBody = 0;

        String tmpHead = attributeList.get(0);
        for (int i = 0; i < positon.size(); i++) {

            tmpHead = tmpHead + "," + "rule" + countHead++;
        }
        attributeListCopy.add(tmpHead);


        for (int j = 1; j < attributeList.size(); j++) {
            String tmp = attributeList.get(j).replace("[", "").replace("]", "");
            for (int i = 0; i < positon.size(); i++) {
                tmp = tmp + "," + countBody++;
                //这里记住position[i]
                tmp = "[" + tmp + "]";

            }
            attributeListCopy.add(tmp);
        }


        return attributeListCopy;

    }

    @Override
    public void rename(String path, String newName) {//改成只检测后缀名

        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                File tmpFile = tempList[i];
                if (tmpFile.toString().contains(".shp") && !tmpFile.toString().contains(".xml")) {
                    File newFile = new File(path + "\\" + newName + ".shp");
                    tmpFile.renameTo(newFile);
                } else if (tmpFile.toString().contains(".shx")) {
                    File newFile = new File(path + "\\" + newName + ".shx");
                    tmpFile.renameTo(newFile);
                } else if (tmpFile.toString().contains(".dbf")) {
                    File newFile = new File(path + "\\" + newName + ".dbf");
                    tmpFile.renameTo(newFile);
                } else if (tmpFile.toString().contains(".sbx")) {
                    File newFile = new File(path + "\\" + newName + ".sbx");
                    tmpFile.renameTo(newFile);
                } else if (tmpFile.toString().contains(".sbn")) {
                    File newFile = new File(path + "\\" + newName + ".sbn");
                    tmpFile.renameTo(newFile);
                } else if (tmpFile.toString().contains(".prj")) {
                    File newFile = new File(path + "\\" + newName + ".prj");
                    tmpFile.renameTo(newFile);
                }
            }
        }
        System.out.println("重命名完成！");
    }

    @Override
    public List<String> getShpFileList(String path) {
        List<String> fileList = new ArrayList<>();
        String fileName = getShpPathWithoutCutline(path);
        fileList.add(fileName);
        System.out.println(fileName);
        fileName = fileName.substring(0, fileName.indexOf('.') - 1);
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                String tmp = tempList[i].toString();
                if (tmp.contains(fileName))
                    fileList.add(tmp);
            }
        }
        return fileList;
    }

    @Override
    public String getShpPathWithoutCutline(String parentPath) {

        String shpPath = "";
        File file = new File(parentPath);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                String tmp = tempList[i].toString();
                String postfix = tmp.substring(tmp.lastIndexOf('.') + 1);

                if (postfix.equals("shp") || postfix.equals("SHP"))
                    shpPath = tmp;
            }
        }
        return shpPath;
    }

    @Override
    public void exportZip(List<String> fileNames, File zip) throws FileNotFoundException {
        File srcFile[] = new File[fileNames.size()];
        for (int i = 0; i < fileNames.size(); i++) {
            srcFile[i] = new File(fileNames.get(i));
        }
        byte[] byt = new byte[1024];
        ZipOutputStream out = null;
        FileInputStream in = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(zip), Charset.forName("UTF-8"));
            for (int i = 0; i < srcFile.length; i++) {
                try {
                    in = new FileInputStream(srcFile[i]);
                    out.putNextEntry(new ZipEntry(srcFile[i].getName()));
                    int length;
                    while ((length = in.read(byt)) > 0) {
                        out.write(byt, 0, length);
                    }
                    out.closeEntry();
                    in.close();
                } catch (Exception e) {
                    System.out.println("文件打包失败");
                } finally {
                    try {
                        in.close();
                    } catch (Exception e1) {
                        System.out.println("文件流关闭失败");
                    }
                }

            }
            out.close();
        } catch (Exception e) {
            System.out.println("文件打包失败2");
            throw new FileNotFoundException();
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                System.out.println("文件流关闭失败2");
            }
        }
    }

    @Override
    public String getShpEPSG(String path) throws FactoryException, TransformException {

        SimpleFeatureCollection colls1 = this.readShp(path);
        String crs = colls1.getBounds().getCoordinateReferenceSystem().toString();
//        System.out.println(crs);
        String utm = "";
        if (crs.contains("UTM")) {
            utm = crs.substring(crs.indexOf('"'), crs.indexOf(','));
            utm = utm.substring(utm.lastIndexOf('_') + 1, utm.lastIndexOf('N'));
            utm = "EPSG:326" + utm;
        } else
            utm = "EPSG:4326";
//        System.out.println("utm是：" + utm);
        return utm;
    }

    @Override
    public SimpleFeatureCollection readShp(String path) {
        return readShp(path, null);

    }

    SimpleFeatureCollection readShp(String path, Filter filter) {
        SimpleFeatureSource featureSource = readStoreByShp(path);
        if (featureSource == null) return null;
        try {
            return filter != null ? featureSource.getFeatures(filter) : featureSource.getFeatures();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    SimpleFeatureSource readStoreByShp(String path) {
        File file = new File(path);
        FileDataStore store;
        SimpleFeatureSource featureSource = null;
        try {
            store = FileDataStoreFinder.getDataStore(file);
            ((ShapefileDataStore) store).setStringCharset(Charset.forName("GBK"));
//            ((ShapefileDataStore) store).setStringCharset(Charset.forName("UTF-8"));
//            System.out.println(Charset.forName("UTF-8"));
            featureSource = store.getFeatureSource();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return featureSource;
    }

    @Override
    public String xml2jsonString(String path) throws JSONException, IOException {
        File file = new File(path);
        InputStream in = new FileInputStream(file);
        String xml = IOUtils.toString(in);
        String json = XML.toJSONObject(xml).toString();
//        System.out.println(xml);
//        return XML.toJSONObject(xml).toString();
        return xml;
    }

    @Override
    public void writeFile(String fileEntityPath, String content) {

        File tmpFile = new File(fileEntityPath);
        FileWriter fw = null;
        try {
            if (!tmpFile.exists()) {
                tmpFile.createNewFile();
            }
            fw = new FileWriter(tmpFile);
            BufferedWriter out = new BufferedWriter(fw);
            out.write(content, 0, content.length() - 1);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String json2xml(String jsonString) {
        XMLSerializer xmlSerializer = new XMLSerializer();
//        System.out.println("123:"+JSONSerializer.toJSON(jsonString));
        return xmlSerializer.write(JSONSerializer.toJSON(jsonString));
    }


    @Override
    public String generateSingleRuleXml(String attributeName, String attributeValue, String titleName, String fillColor, String fillOpacity, String strokeColor, String strokeOpacity) {

        String templateRule = "<sld:Title>0</sld:Title>\n" +
                "                    <ogc:Filter>\n" +
                "                        <ogc:PropertyIsEqualTo>\n" +
                "                            <ogc:PropertyName>Islands</ogc:PropertyName>\n" +
                "                            <ogc:Literal>0</ogc:Literal>\n" +
                "                        </ogc:PropertyIsEqualTo>\n" +
                "                    </ogc:Filter>\n" +
                "                    <sld:PolygonSymbolizer>\n" +
                "                        <sld:Fill>\n" +
                "                            <sld:CssParameter name=\"fill\">#100000</sld:CssParameter>\n" +
                "                            <sld:CssParameter name=\"fill-opacity\">0.5</sld:CssParameter>\n" +
                "                        </sld:Fill>\n" +
                "                        <sld:Stroke>\n" +
                "                            <sld:CssParameter name=\"stroke\">#FF0000</sld:CssParameter>\n" +
                "                            <sld:CssParameter name=\"stroke-opacity\">0.5</sld:CssParameter>\n" +
                "                        </sld:Stroke>\n" +
                "                    </sld:PolygonSymbolizer>";
        templateRule = templateRule.replace("<sld:Title>0</sld:Title>", "<sld:Title>" + titleName + "</sld:Title>");
        templateRule = templateRule.replace("<sld:CssParameter name=\"fill\">#100000</sld:CssParameter>", "<sld:CssParameter name=\"fill\">" + fillColor + "</sld:CssParameter>");
        templateRule = templateRule.replace("<sld:CssParameter name=\"fill-opacity\">0.5</sld:CssParameter>", "<sld:CssParameter name=\"fill-opacity\">" + fillOpacity + "</sld:CssParameter>");
        templateRule = templateRule.replace("<sld:CssParameter name=\"stroke\">#FF0000</sld:CssParameter>", "<sld:CssParameter name=\"stroke\">" + strokeColor + "</sld:CssParameter>");
        templateRule = templateRule.replace("<sld:CssParameter name=\"stroke-opacity\">#FF0000</sld:CssParameter>", "<sld:CssParameter name=\"stroke\">" + strokeOpacity + "</sld:CssParameter>");
        templateRule = templateRule.replace("<ogc:PropertyName>Islands</ogc:PropertyName>", "<ogc:PropertyName>" + attributeName + "</ogc:PropertyName>");
        templateRule = templateRule.replace("<ogc:Literal>0</ogc:Literal>", "<ogc:Literal>" + attributeValue + "</ogc:Literal>");
        return templateRule;
    }

    @Override
    public String generateRuleXml(String attributeName, int valueCount, List<AttributeInfoDto> attributeInfoDtoList) {


        String ruleXml = "";
        int count = 0;
        for (AttributeInfoDto attributeInfo : attributeInfoDtoList) {

            String ruleHeader = "<sld:Rule>\n" +
                    "                    <sld:Name>" + count + "</sld:Name>";
            String ruleBody = generateSingleRuleXml(attributeName, attributeInfo.getAttributeValue(), String.valueOf(count), attributeInfo.getFillColor(), attributeInfo.getFillOpacity(), attributeInfo.getStrokeColor(), attributeInfo.getStrokeOpacity());
            String ruleEnd = "</sld:Rule>";
            ruleXml += (ruleHeader + ruleBody + ruleEnd);
            count++;
        }
        return ruleXml;
    }

    @Override
    public String generateSldBody(String targetXml, String ruleXml) {

        return targetXml.replace(targetXml.substring(targetXml.indexOf("<sld:Rule>"), targetXml.lastIndexOf(" </sld:FeatureTypeStyle>")), ruleXml);
    }

    @Override
    public SldPathAndNameDto getSldPathAndName(String productId, String singleId) {

//        System.out.println("singleid是："+singleId);
        return gisProductLayerInfoMapper.getSldPathAndName(productId, singleId);
    }

    @Override
    public List<String> getAttributeList(String productId, String singleId) {


        return gisProductShpAttributeInfoMapper.getAttributeList(productId, singleId);
    }

    @Override
    public AttributeValuesDto getAttributeValueInfo(String productId, String singleId, String attributeName) {

        int id = gisProductShpAttributeInfoMapper.getId(productId, singleId, attributeName);
        List<String> values = gisProductShpAttributeDetailInfoMapper.getAttributeValues(id);
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) == null || values.get(i).equals(""))
                values.remove(i);
        }
        AttributeValuesDto attributeValuesDto = new AttributeValuesDto();
        attributeValuesDto.setValueCount(gisProductShpAttributeInfoMapper.getValueCount(productId, singleId, attributeName));
        attributeValuesDto.setValueList(values);
        return attributeValuesDto;
    }


    @Override
    public List<JSONObject> getAdvancedProductShpInfo(String path) {


        SimpleFeatureCollection colls1 = this.readShp(path);

        SimpleFeatureIterator iters = colls1.features();

        List<JSONObject> jSONObjects = new ArrayList<JSONObject>();
        while (iters.hasNext()) {
            SimpleFeature sf = iters.next();

            String[] strList = (sf.getProperties()).toString().split("SimpleFeatureImpl.Attribute");

            JSONObject jsonObject = new JSONObject();
            for (int i = 0; i < strList.length; i++) {
                String tmp = strList[i];
//                System.out.println("原串是：" + tmp);
                String key = "";
                String value = "";
                if (i > 0 && i < strList.length - 1) {
                    key = tmp.substring(2, tmp.indexOf('<'));

                    String comma = ",";

                    if (comma.equals((tmp.charAt(tmp.length() - 1))))
                        value = "";
                    else
                        value = tmp.substring(tmp.lastIndexOf('=') + 1, tmp.lastIndexOf(','));

                }
                if (i == strList.length - 1) {
                    {
                        key = tmp.substring(2, tmp.indexOf('<'));

                        value = tmp.substring(tmp.lastIndexOf('=') + 1, tmp.lastIndexOf(']'));

                    }
                }
                if (i != 0)
                    jsonObject.put(key, value);
            }
            jSONObjects.add(jsonObject);

        }
        List<JSONObject> jSONObjectList = new ArrayList<JSONObject>();
        for (JSONObject jsonObjectTmp : jSONObjects) {

            String wkt = jsonObjectTmp.getString("the_geom");
//            System.out.println("wkt为：" + wkt);
            String multiPolygon = getGj(wkt).toString();
//            System.out.println("multiPlygon是：" + multiPolygon);
            String geojson = transformMultiPolygonToPolygon(getGj(wkt));
//            System.out.println("geojson:" + geojson);
//            System.out.println(jsonObjectTmp.getString("the_geom"));

            jsonObjectTmp.put("the_geom", geojson);
            jSONObjectList.add(jsonObjectTmp);
        }

//        System.out.println("1111:" + jSONObjectList);
        return jSONObjectList;

    }

    @Override
    public String transformMultiPolygonToPolygon(JSONObject jsonObject) {

        String coordinates = jsonObject.getString("coordinates");
        coordinates = coordinates.substring(1, coordinates.length() - 1);
        jsonObject.put("coordinates", coordinates);
        String geojson = jsonObject.toString();
        geojson = geojson.replace("MULTIPOLYGON \"", "Polygon\"");
        return geojson;
    }

    @Override
    public JSONObject getGj(String wkt) {
        if (wkt == null) {
            return null;
        }
        if (wkt.contains("(")) {
            int index = wkt.indexOf("(");
            String type = wkt.substring(0, index);
            String value = wkt.substring(index);
            String t = value.replace("(", "[").replace(")", "]");
            Pattern compile = Pattern.compile("[-.0-9]+\\s+[-.0-9]+");
            Matcher matcher = compile.matcher(t);
            StringBuilder sb = new StringBuilder();
            sb.append("{\"type\":\"" + type + "\",\"coordinates\":");
            int end = 0;
            int start;
            while (matcher.find()) {
                String group = matcher.group();
                start = matcher.start();
                String[] split = group.split("\\s+");
                sb.append(t.substring(end, start)).append("[").append(split[0]).append(",").append
                        (split[1]).append("]");
                end = matcher.end();
            }
            sb.append(t.substring(end));
            sb.append("}");
            return JSONObject.fromObject(sb.toString());
        }
        return null;
    }

    @Override
    public List<String> readDBF(String path) throws Exception {
//        InputStream fis = null;
//        try {
//            // 读取文件的输入流
//            fis = new FileInputStream(path);
//            // 根据输入流初始化一个DBFReader实例，用来读取DBF文件信息
//            DBFReader reader = new DBFReader(fis);
//            // 解决乱码
//            reader.setCharactersetName("GBK");
//            // 调用DBFReader对实例方法得到path文件中字段的个数
//            int fieldsCount = reader.getFieldCount();
//            // 取出字段信息
//            for (int i = 0; i < fieldsCount; i++) {
//                DBFField field = reader.getField(i);
//                System.out.println("字段信息"+field.getName());
//            }
//            Object[] rowValues;
//            // 一条条取出path文件中记录
//            while ((rowValues = reader.nextRecord()) != null) {
//                for (int i = 0; i < rowValues.length; i++) {
//                    System.out.println("aa"+rowValues[i]+"aaaa"+String.valueOf(i));
//                    System.out.println("aaaaa");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fis.close();
//            } catch (Exception e) {
//            }
//        }
//        DBFWriter writer = null;

        List<String> attributeList = new ArrayList<>();
        DBFReader reader = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        File f = new File(path);
        // 开始读
        fis = new FileInputStream(f);
        reader = new DBFReader(fis);
        reader.setCharactersetName("GBK");
        Object[] objects = null;
        int fieldsCount = reader.getFieldCount();
        String attributeName = "";
        for (int i = 0; i < fieldsCount; i++) {
            DBFField field = reader.getField(i);
            System.out.println(reader.getField(i));
            attributeName += field.getName();
            if (i != fieldsCount - 1)
                attributeName += ",";
            else
                attributeList.add(attributeName);
        }

        for (; (objects = reader.nextRecord()) != null; ) {
            String tmpAttributeName = Arrays.toString(objects);
            System.out.println(Arrays.toString(objects));
            attributeList.add(tmpAttributeName);
        }
        DBFUtils.close(reader);
        System.out.println("获得的信息是：" + String.valueOf(attributeList));
        return attributeList;
    }

    public boolean checkcountname(String countname) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(countname);
        if (m.find()) {
            return true;
        }
        return false;
    }

    @Override
    public List<String> getAttributeList(List<String> attributeList) {

        List<String> attributeListCopy = new ArrayList<>();
        String header = attributeList.get(0);
        String[] attribute = header.split(",");
        int length = attribute.length;
        System.out.println("长度是：" + length);
        List<Integer> positon = new ArrayList<>();

        String[] attributeValue = attributeList.get(2).replace("[", "").replace("]", "").split(",");//2!
        for (int j = 0; j < length; j++) {
            System.out.println("1111" + attributeValue[j]);
            if (checkcountname(attributeValue[j])) {
//                length++;
                System.out.println("222");
                positon.add(j);
            }
        }
        int countHead = 0;
        int countBody = 0;

        String tmpHead = attributeList.get(0);
        for (int i = 0; i < positon.size(); i++) {

            tmpHead = tmpHead + "," + "rule" + countHead++;
        }
        attributeListCopy.add(tmpHead);


        for (int j = 1; j < attributeList.size(); j++) {
            String tmp = attributeList.get(j).replace("[", "").replace("]", "");
            for (int i = 0; i < positon.size(); i++) {
                tmp = tmp + "," + countBody++;
                //这里记住position[i]
                tmp = "[" + tmp + "]";

            }
            attributeListCopy.add(tmp);
        }

        return attributeListCopy;
    }

    @Override
    public void writeDBF(String path, List<String> attributeList) throws Exception {
//        OutputStream fos = null;
//        try {
//            // 定义DBF文件字段
//            DBFField[] fields = new DBFField[2];
//            // 分别定义各个字段信息，setFieldName和setName作用相同，
//            // 只是setFieldName已经不建议使用
//            fields[0] = new DBFField();
//            // fields[0].setFieldName("emp_code");
//            fields[0].setName("Class_name");
//            fields[0].setDataType(CHARACTER);
//            fields[0].setFieldLength(200);
//            fields[0].setDecimalCount(20);
//            fields[1] = new DBFField();
//            // fields[1].setFieldName("emp_name");
//            fields[1].setName("area");
//            fields[1].setDataType(DBFField.FIELD_TYPE_C);
//            fields[1].setFieldLength(200);
//            fields[1].setDecimalCount(20);
////            fields[2] = new DBFField();
////            // fields[2].setFieldName("salary");
////            fields[2].setName("emp_Test3");
////            fields[2].setDataType(DBFField.FIELD_TYPE_N);
////            fields[2].setFieldLength(12);
////            fields[2].setDecimalCount(2);
//            // DBFWriter writer = new DBFWriter(new File(path));
//            // 定义DBFWriter实例用来写DBF文件
//            DBFWriter writer = new DBFWriter();
//            writer.setCharactersetName("GBK");
//            // 把字段信息写入DBFWriter实例，即定义表结构
//            writer.setFields(fields);
//            // 一条条的写入记录
//            Object[] rowData = new Object[2];
//            rowData[0] = "建设用地";
//            System.out.println("row是："+rowData[0]);
//            rowData[1] = "dsaf";
////            rowData[2] = new Double(5000.00);
//            writer.addRecord(rowData);
//            rowData = new Object[2];
//            rowData[0] = "林地";
//            rowData[1] = "sdfa";
////            rowData[2] = new Double(3400.00);
//            writer.addRecord(rowData);
//            rowData = new Object[2];
//            rowData[0] = "棉花";
//            rowData[1] = "sadf";
////            rowData[2] = new Double(7350.00);
//            writer.addRecord(rowData);
//            // 定义输出流，并关联的一个文件
//            fos = new FileOutputStream(path);
//            // 写入数据
//            writer.write(fos);
//            // writer.write();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fos.close();
//            } catch (Exception e) {
//            }
//        }


        String header = attributeList.get(0);
        String[] attribute = header.split(",");
        int length = attribute.length;

//        for(int i=1;i<attributeList.size();i++) {
//
//            String[] attributeValue = attributeList.get(i).replace("[","").replace("]","").split(",");
//            for(int j = 0;j<length;j++)
//            {
//               if(checkcountname(attributeValue[j])){
//                   length++;
//               }
//            }
//        }

        DBFField fields[] = new DBFField[length];
        for (int i = 0; i < length; i++) {
            fields[i] = new DBFField();
            fields[i].setName(attribute[i]);
            fields[i].setType(DBFDataType.CHARACTER);
            fields[i].setLength(20);
        }
        DBFWriter writer = null;
        DBFReader reader = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        File f = new File(path);
        f.createNewFile();
        // 开始写
        fos = new FileOutputStream(f);
        writer = new DBFWriter(fos);
        writer.setFields(fields);
        writer.setCharactersetName("GBK");
        // now populate DBFWriter
        //
        for (int i = 1; i < attributeList.size(); i++) {
            String[] attributeValue = attributeList.get(i).replace("[", "").replace("]", "").split(",");
            Object rowData[] = new Object[length];
            for (int j = 0; j < length; j++) {
                rowData[j] = attributeValue[j];
            }
            writer.addRecord(rowData);
        }
        DBFUtils.close(writer);
        System.out.println("The dbf file product success!");
    }

    @Override
    public void testWriteAndReadAgain() throws DBFException, IOException {
        // let us create field definitions first
        // we will go for 3 fields
        //
        DBFField fields[] = new DBFField[3];

        fields[0] = new DBFField();
        fields[0].setName("emp_code");
        fields[0].setType(DBFDataType.CHARACTER);
        fields[0].setLength(10);

        fields[1] = new DBFField();
        fields[1].setName("emp_name");
        fields[1].setType(DBFDataType.CHARACTER);
        fields[1].setLength(20);

        fields[2] = new DBFField();
        fields[2].setName("salary");
        fields[2].setType(DBFDataType.NUMERIC);
        fields[2].setLength(12);
        fields[2].setDecimalCount(2);
        DBFWriter writer = null;
        DBFReader reader = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        File f = new File("d:/emp.dbf");
        f.createNewFile();
        try {
            // 开始写
            fos = new FileOutputStream(f);
            writer = new DBFWriter(fos);
            writer.setFields(fields);
            writer.setCharactersetName("GBK");
            // now populate DBFWriter
            //

            Object rowData[] = new Object[3];
            rowData[0] = "1000";
            rowData[1] = "中国";
            rowData[2] = new Double(5000.00);

            writer.addRecord(rowData);

            rowData = new Object[3];
            rowData[0] = "1001";
            rowData[1] = "Lalit";
            rowData[2] = new Double(3400.00);

            writer.addRecord(rowData);

            rowData = new Object[3];
            rowData[0] = "1002";
            rowData[1] = "Rohit";
            rowData[2] = new Double(7350.00);

            writer.addRecord(rowData);

            DBFUtils.close(writer);
            System.out.println("The dbf file product success!");

            // 开始读
            fis = new FileInputStream(f);
            reader = new DBFReader(fis);
            reader.setCharactersetName("GBK");
            Object[] objects = null;
            for (; (objects = reader.nextRecord()) != null; ) {
                System.out.println(Arrays.toString(objects));
            }
            DBFUtils.close(reader);

        } finally {
            DBFUtils.close(reader);
            DBFUtils.close(writer);
        }
    }

    @Override
    public String getAttributeFlag(String productId, String singleId, String attributeName) {


        return gisProductShpAttributeInfoMapper.getAttributeFlag(productId, singleId, attributeName);
    }

    @Override
    public String getAttributeOtherValue(String productId, String singleId, String attributeName, String attributeValue) {

        int id = gisProductShpAttributeInfoMapper.getId(productId, singleId, attributeName);
        return gisProductShpAttributeDetailInfoMapper.getAttributeOtherValue(id, attributeValue);
    }

    @Override
    public String getAttributeOtherName(String productId, String singleId, String attributeName) {

        return gisProductShpAttributeInfoMapper.getAttributeOtherName(productId, singleId, attributeName);
    }

    @Override
    public String getDbfPath(String path) {

        String dbfPath = "";
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                String tmp = tempList[i].toString();
                String postfix = tmp.substring(tmp.lastIndexOf('.') + 1);

                if (postfix.equals("dbf") && !tmp.contains("boundary"))
                    dbfPath = tmp;
            }
        }
        return dbfPath;

    }

    @Override
    public Result publishTifToGeoserver(JSONObject jsonObject) throws ParserConfigurationException, IOException {

        JSONObject resultObject = new JSONObject();
        HttpClient httpClient = new HttpClient();
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(layerInfo.getGeoserverUsername(), layerInfo.getGeoserverPassword());
        System.out.println(layerInfo.getGeoserverUsername());
        httpClient.getState().setCredentials(AuthScope.ANY, creds);
        httpClient.getParams().setContentCharset("UTF-8");
        String url = layerInfo.getGeoserverPath() + "/rest/workspaces/" + jsonObject.getString("namespace") + "/coveragestores";
        String data = GeoserverXml.GetCoveragestoreXml(jsonObject);
        PostMethod postMethod = new PostMethod(url);
        RequestEntity requestEntity = new StringRequestEntity(data, "application/xml", "UTF-8");
        postMethod.setRequestEntity(requestEntity);
        int result = httpClient.executeMethod(postMethod);
        if (result != 201) {
            if (result == 500) {
                System.out.println("存在同名图层");
                return ResultUtil.error(511, "存在同名图层");

            } else {
                System.out.println(postMethod.getResponseBodyAsString());
                System.out.println(result);
                System.out.println("发布存储失败");
                return ResultUtil.error(511, "发布存储失败");
            }
        }
        data = GeoserverXml.getCoverageXml(jsonObject);
        url = layerInfo.getGeoserverPath() + "/rest/workspaces/" + jsonObject.getString("namespace") + "/coveragestores/" + URLEncoder.encode(jsonObject.getString("storename"), "utf-8") + "/coverages";
        postMethod = new PostMethod(url);
        postMethod.setRequestHeader("Content-type", "text/xml");
        requestEntity = new StringRequestEntity(data, "application/xml", "UTF-8");
        postMethod.setRequestEntity(requestEntity);
        result = httpClient.executeMethod(postMethod);
        if (result != 201) {
            url = layerInfo.getGeoserverPath() + "/rest/workspaces/" + jsonObject.getString("namespace") + "/coveragestores/" + URLEncoder.encode(jsonObject.getString("storename"), "utf-8");
            DeleteMethod deleteMethod = new DeleteMethod(url);
            httpClient.executeMethod(deleteMethod);
            return ResultUtil.error(512, postMethod.getResponseBodyAsString());
        }

        return ResultUtil.success();
    }

    @Override
    public JSONObject publishTif(String filePath, String nameSpace, String borderColor) throws IOException, FactoryException, TransformException {
        JSONObject jsonObject = new JSONObject();
        File file = new File(filePath);
        String name = file.getName().split("\\.")[0];
        /////////////////////
        name = name.substring(0, 10) + getUUId();
        ///////////////////
        if (name.length() > 20) name = name.substring(0, 20);
        jsonObject.put("filename", name);
        GeoTiffReader reader;
        GeoTiffFormat format = new GeoTiffFormat();
        File file2 = new File(filePath);
        System.out.println("tif名字是：" + filePath);
        reader = format.getReader(file2.toURL());
        if (reader == null) {
            System.out.println("Tif文件无法打开");
        }
        GridCoverage2D coverage = reader.read(null);
        String crs = coverage.getCoordinateReferenceSystem().toString();
        if (crs == null) {
            System.out.println("文件缺少坐标系");
        }
        Envelope envelope = coverage.getEnvelope();
        AffineTransform art = (AffineTransform) coverage.getGridGeometry().getGridToCRS();
        jsonObject.put("namespace", nameSpace);
        jsonObject.put("storename", name);
        jsonObject.put("layername", name);
        jsonObject.put("srs", "EPSG" + ":" + PublishLayer.GetCrsEPSG(crs));
        jsonObject.put("nativeBoundingBox", "[" + envelope.getMinimum(0) + "," + envelope.getMaximum(0) + "," + envelope.getMinimum(1) + "," + envelope.getMaximum(1) + "]");
        MathTransform transform = CRS.findMathTransform(coverage.getCoordinateReferenceSystem(), DefaultGeographicCRS.WGS84);
        com.vividsolutions.jts.geom.Envelope envelope1 = new com.vividsolutions.jts.geom.Envelope(envelope.getMinimum(0), envelope.getMaximum(0), envelope.getMinimum(1), envelope.getMaximum(1));
        if (!PublishLayer.GetCrsEPSG(crs).equals("4326")) envelope1 = JTS.transform(envelope1, transform);
        jsonObject.put("latLonBoundingBox", "[" + envelope1.getMinX() + "," + envelope1.getMaxX() + "," + envelope1.getMinY() + "," + envelope1.getMaxY() + "]");

        com.vividsolutions.jts.geom.Polygon polygon = JTS.toGeometry(envelope1);
        System.out.println("Polygon是：" + polygon);
        // ！！GeometryJSON g = new GeometryJSON();
        jsonObject.put("nativeCRS", crs);
        jsonObject.put("filepath", filePath);
        jsonObject.put("rasterXSize", coverage.getRenderedImage().getWidth());
        jsonObject.put("rasterYSize", coverage.getRenderedImage().getHeight());
        jsonObject.put("scaleX", art.getScaleX());
        jsonObject.put("scaleY", art.getScaleY());
        jsonObject.put("shearX", art.getShearX());
        jsonObject.put("shearY", art.getShearY());

        jsonObject.put("translateX", art.getTranslateX());
        jsonObject.put("translateY", art.getTranslateY());
        jsonObject.put("borderColor", borderColor);
        jsonObject.put("data_type", "image");
        jsonObject.put("geoserverPath", layerInfo.getGeoserverPath());

        JSONObject jsonObjectReturn = new JSONObject();
        String geoJsonReturn = transformPOLYGONToPolygon(getGj(polygon.toString()));
        System.out.println("Polygon2是：" + geoJsonReturn);
        jsonObjectReturn.put("fileName", name);
        jsonObjectReturn.put("geoJson", geoJsonReturn);

        try {
            Result result = publishTifToGeoserver(jsonObject);
            if (result.getStatus() == 200) {
                JSONObject param = new JSONObject();

                param.put("layerName", nameSpace + ":" + name);
                if (coverage.getCoordinateReferenceSystem().toString().contains("UTM")) param.put("projection", "UTM");
                else param.put("projection", "WGS-84");
                return jsonObjectReturn;

            }

            return jsonObjectReturn;

        } catch (ParserConfigurationException e) {
            System.out.println("发布失败");
            return jsonObjectReturn;
        }

    }

    @Override
    public String getUUId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    @Override
    public String transformPOLYGONToPolygon(JSONObject jsonObject) {

        String geojson = jsonObject.toString();
        geojson = geojson.replace("POLYGON \"", "Polygon\"");
        return geojson;

    }

    @Override
    public boolean resetStyle(String styleName) {

        String defaultStyle = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<StyledLayerDescriptor version=\"1.0.0\" \n" +
                "                       xsi:schemaLocation=\"http://www.opengis.net/sld StyledLayerDescriptor.xsd\" \n" +
                "                       xmlns=\"http://www.opengis.net/sld\" \n" +
                "                       xmlns:ogc=\"http://www.opengis.net/ogc\" \n" +
                "                       xmlns:xlink=\"http://www.w3.org/1999/xlink\" \n" +
                "                       xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "  <NamedLayer>\n" +
                "    <Name>generic</Name>\n" +
                "    <UserStyle>\n" +
                "      <Title>Generic</Title>\n" +
                "      <Abstract>Generic style</Abstract>\n" +
                "      <FeatureTypeStyle>\n" +
                "        <Rule>\n" +
                "          <Name>raster</Name>\n" +
                "          <Title>raster</Title>\n" +
                "          <ogc:Filter>\n" +
                "            <ogc:PropertyIsEqualTo>\n" +
                "              <ogc:Function name=\"isCoverage\"/>\n" +
                "              <ogc:Literal>true</ogc:Literal>\n" +
                "            </ogc:PropertyIsEqualTo>\n" +
                "          </ogc:Filter>\n" +
                "          <RasterSymbolizer>\n" +
                "            <Opacity>1.0</Opacity>\n" +
                "          </RasterSymbolizer>\n" +
                "        </Rule>\n" +
                "        <Rule>\n" +
                "          <Name>Polygon</Name>\n" +
                "          <Title>Polygon</Title>\n" +
                "          <ogc:Filter>\n" +
                "            <ogc:PropertyIsEqualTo>\n" +
                "              <ogc:Function name=\"dimension\">\n" +
                "                <ogc:Function name=\"geometry\"/>\n" +
                "              </ogc:Function>\n" +
                "              <ogc:Literal>2</ogc:Literal>\n" +
                "            </ogc:PropertyIsEqualTo>\n" +
                "          </ogc:Filter>\n" +
                "          <PolygonSymbolizer>\n" +
                "            <Fill>\n" +
                "              <CssParameter name=\"fill\">#AAAAAA</CssParameter>\n" +
                "            </Fill>\n" +
                "            <Stroke>\n" +
                "              <CssParameter name=\"stroke\">#000000</CssParameter>\n" +
                "              <CssParameter name=\"stroke-width\">1</CssParameter>\n" +
                "            </Stroke>\n" +
                "          </PolygonSymbolizer>\n" +
                "        </Rule>\n" +
                "        <Rule>\n" +
                "          <Name>Line</Name>\n" +
                "          <Title>Line</Title>\n" +
                "          <ogc:Filter>\n" +
                "            <ogc:PropertyIsEqualTo>\n" +
                "              <ogc:Function name=\"dimension\">\n" +
                "                <ogc:Function name=\"geometry\"/>\n" +
                "              </ogc:Function>\n" +
                "              <ogc:Literal>1</ogc:Literal>\n" +
                "            </ogc:PropertyIsEqualTo>\n" +
                "          </ogc:Filter>\n" +
                "          <LineSymbolizer>\n" +
                "            <Stroke>\n" +
                "              <CssParameter name=\"stroke\">#0000FF</CssParameter>\n" +
                "              <CssParameter name=\"stroke-opacity\">1</CssParameter>\n" +
                "            </Stroke>\n" +
                "          </LineSymbolizer>\n" +
                "        </Rule>\n" +
                "        <Rule>\n" +
                "          <Name>point</Name>\n" +
                "          <Title>Point</Title>\n" +
                "          <ElseFilter/>\n" +
                "          <PointSymbolizer>\n" +
                "            <Graphic>\n" +
                "              <Mark>\n" +
                "                <WellKnownName>square</WellKnownName>\n" +
                "                <Fill>\n" +
                "                  <CssParameter name=\"fill\">#FF0000</CssParameter>\n" +
                "                </Fill>\n" +
                "              </Mark>\n" +
                "              <Size>6</Size>\n" +
                "            </Graphic>\n" +
                "          </PointSymbolizer>\n" +
                "        </Rule>\n" +
                "        <VendorOption name=\"ruleEvaluation\">first</VendorOption>\n" +
                "      </FeatureTypeStyle>\n" +
                "    </UserStyle>\n" +
                "  </NamedLayer>\n" +
                "</StyledLayerDescriptor>\n";
        updateStyle(defaultStyle, styleName);
        return true;
    }

    @Async("taskExecutor")
    @Override
    public Object publishThemeTifForArchive(JSONObject msg) throws IOException, FactoryException, TransformException {

        JSONObject jsonObject = publishTif(msg.getString("path"), "layerPublish", "#FFFFFF");
        updateThemeticProductDetailImgGeo(msg.getString("productId"), msg.getString("singleId"), jsonObject.getString("geoJson"));
        GisProductLayerInfo gisProductLayerInfo = new GisProductLayerInfo();
        gisProductLayerInfo.setProductId(msg.getString("productId"));
        gisProductLayerInfo.setLayerName("layerPublish:" + jsonObject.getString("fileName"));
        gisProductLayerInfo.setSingleId(msg.getString("singleId"));
        gisProductLayerInfo.setIsShp("false");
        gisProductLayerInfoMapper.insert(gisProductLayerInfo);
        if (Integer.parseInt(msg.getString("productType")) == 1) {
            updateThemeticProductDetailImgGeo(msg.getString("productId"), msg.getString("singleId"), jsonObject.getString("geoJson"));
        }
        return true;
    }

}
