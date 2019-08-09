package com.cgwx.controller;


import com.cgwx.aop.result.Result;
import com.cgwx.aop.result.ResultUtil;
import com.cgwx.data.dto.AttributeInfoDto;
import com.cgwx.data.dto.AttributeValuesDto;
import com.cgwx.data.dto.SldPathAndNameDto;
import com.cgwx.service.IlayerPublishService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
public class LayerPublishController {

    @Autowired
    IlayerPublishService ilayerPublishService;

    @RequestMapping(value = "/publishShpWithSld")
    @CrossOrigin()
    @ResponseBody
    public Result publishShpWithSld() {
//        File styleFile = new File("D:\\geoserver\\data_dir\\data\\tifPublishTest\\181222_6247_0000\\classes2.sld");
//        ilayerPublishService.publishStyle(styleFile,"classes");
//        ilayerPublishService.publishShp("cite", "test7272", "test7272", "D:\\geoserver\\data_dir\\data\\tifPublishTest\\181222_6247_0000", styleFile);

        ilayerPublishService.publishThemeShpForArchive("test7293","","test7293","test7293","D:\\test","D:\\geoserver\\data_dir\\data\\cite\\test7272\\class2.sld","");
        return ResultUtil.success("");
    }

    @RequestMapping(value = "/testPublish")
    @CrossOrigin()
    @ResponseBody
    public Result test() throws Exception {

//        String xmlJson = ilayerPublishService.xml2jsonString("F:\\pdmTest2\\pdm-master\\officialStorage\\镶嵌产品\\MP_广东省_广州市_白天\\2.sld");
////        System.out.println("以前的xml是："+xmlJson);
////        xmlJson=xmlJson.replace("#7FC97F","#BEAED4");
//
//        System.out.println("xml是：" + xmlJson);
//        System.out.println(XML.toJSONObject(xmlJson).toString());
//        JSONObject xmlJsonObject = JSONObject.fromObject(XML.toJSONObject(xmlJson).toString());
//        String Json = XML.toJSONObject(xmlJson).toString();
//        xmlJsonObject = xmlJsonObject.getJSONObject("sld:StyledLayerDescriptor");
//        xmlJsonObject = xmlJsonObject.getJSONObject("sld:UserLayer");
//        xmlJsonObject = xmlJsonObject.getJSONObject("sld:UserStyle");
//        xmlJsonObject = xmlJsonObject.getJSONObject("sld:FeatureTypeStyle");
//        JSONArray xmlJsonArray = xmlJsonObject.getJSONArray("sld:Rule");
//
//        JSONObject jsonObject = JSONObject.fromObject(xmlJsonArray.get(0).toString());
//        jsonObject = jsonObject.getJSONObject("sld:PolygonSymbolizer");
//        System.out.println("第一个颜色：" + jsonObject);
////        jsonObject = jsonObject.getJSONObject("sld:CssParameter");
//        jsonObject = jsonObject.getJSONObject("sld:Fill");
//        String tmp = jsonObject.toString();
//        System.out.println("第一个颜色：" + jsonObject);
//        tmp = tmp.replace(tmp.substring(tmp.indexOf("content") + 10, tmp.indexOf("content") + 17), "#FF0000");
//        System.out.println("颜色：" + tmp);
//
//        System.out.println("xml" + ilayerPublishService.json2xml(tmp));
////        String outxml = ilayerPublishService.json2xml(Json);
//
////        xmlJson = xmlJson.replace(xmlJson.substring(xmlJson.indexOf("<sld:Name>rule01</sld:Name>")+27,xmlJson.indexOf("</sld:Rule>")),"<sld:Title>0</sld:Title>\n" +
////                "                    <ogc:Filter>\n" +
////                "                        <ogc:PropertyIsEqualTo>\n" +
////                "                            <ogc:PropertyName>Islands</ogc:PropertyName>\n" +
////                "                            <ogc:Literal>0</ogc:Literal>\n" +
////                "                        </ogc:PropertyIsEqualTo>\n" +
////                "                    </ogc:Filter>\n" +
////                "                    <sld:PolygonSymbolizer>\n" +
////                "                        <sld:Fill>\n" +
////                "                            <sld:CssParameter name=\"fill\">#100000</sld:CssParameter>\n" +
////                "                            <sld:CssParameter name=\"fill-opacity\">0.5</sld:CssParameter>\n" +
////                "                        </sld:Fill>\n" +
////                "                        <sld:Stroke>\n" +
////                "                            <sld:CssParameter name=\"stroke\">#FF0000</sld:CssParameter>\n" +
////                "                        </sld:Stroke>\n" +
////                "                    </sld:PolygonSymbolizer>");
////        xmlJson = xmlJson.replace(xmlJson.substring(xmlJson.indexOf("<sld:Name>rule01</sld:Name>")+27,xmlJson.indexOf("</sld:Rule>")),ilayerPublishService.generateSingleRuleXml("0","#1F00FF","0.5","#FFFFFF","0.5"));
//        System.out.println("456" + xmlJson);
//        ilayerPublishService.updateStyle(xmlJson, "manycolors4");
        List<JSONObject> jsonObjects = ilayerPublishService.getAdvancedProductShpInfo("D:\\geoserver\\data_dir\\data\\tifPublishTest\\181222_6247_0000\\181222_6247_0000.shp");

//        ilayerPublishService.writeDBF("D:\\geoserver\\data_dir\\data\\tifPublishTest\\181222_6247_0000\\181222_6247_0001.dbf");

        ilayerPublishService.writeDBF("d:/emp.dbf", ilayerPublishService.getAttributeList(ilayerPublishService.readDBF("D:\\test\\test729.dbf")));
//        ilayerPublishService.writeDBF("d:/emp.dbf", ilayerPublishService.getAttributeList(ilayerPublishService.readDBF("D:\\geoserver\\data_dir\\data\\tifPublishTest\\181222_6247_0000\\181222_6247_0000.dbf")));
        System.out.println("///////////////////////////////////////////");

//        ilayerPublishService.writeDBF("d:/emp.dbf",ilayerPublishService.readDBF("D:\\geoserver\\data_dir\\data\\tifPublishTest\\181222_6247_0000\\181222_6247_0000.dbf"));
//        System.out.println("///////////////////////////////////////////");
        ilayerPublishService.readDBF("d:/emp.dbf");

        //        ilayerPublishService.testWriteAndReadAgain();
        //       String xmlJson =
//               "<sld:StyledLayerDescriptor xmlns=\"http://www.opengis.net/sld\" xmlns:sld=\"http://www.opengis.net/sld\" xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:gml=\"http://www.opengis.net/gml\" version=\"1.0.0\">\n" +
//               "    <sld:UserLayer>\n" +
//               "        <sld:LayerFeatureConstraints>\n" +
//               "            <sld:FeatureTypeConstraint/>\n" +
//               "        </sld:LayerFeatureConstraints>\n" +
//               "        <sld:UserStyle>\n" +
//               "            <sld:Name>181222 6247 0000</sld:Name>\n" +
//               "            <sld:FeatureTypeStyle>\n" +
//               "                <sld:Name>group 0</sld:Name>\n" +
//               "                <sld:FeatureTypeName>Feature</sld:FeatureTypeName>\n" +
//               "                <sld:SemanticTypeIdentifier>generic:geometry</sld:SemanticTypeIdentifier>\n" +
//               "                <sld:SemanticTypeIdentifier>colorbrewer:unique:accents</sld:SemanticTypeIdentifier>\n" +
//               "                <sld:Rule>\n" +
//               "                    <sld:Name>rule01</sld:Name>\n" +
//               "                    <sld:Title>unclassified</sld:Title>\n" +
//               "                    <ogc:Filter>\n" +
//               "                        <ogc:PropertyIsEqualTo>\n" +
//               "                            <ogc:PropertyName>Class_name</ogc:PropertyName>\n" +
//               "                            <ogc:Literal>unclassified</ogc:Literal>\n" +
//               "                        </ogc:PropertyIsEqualTo>\n" +
//               "                    </ogc:Filter>\n" +
//               "                    <sld:PolygonSymbolizer>\n" +
//               "                        <sld:Fill>\n" +
//               "                            <sld:CssParameter name=\"fill\">#7FC97F</sld:CssParameter>\n" +
//               "                            <sld:CssParameter name=\"fill-opacity\">0.5</sld:CssParameter>\n" +
//               "                        </sld:Fill>\n" +
//               "                    </sld:PolygonSymbolizer>\n" +
//               "                </sld:Rule>\n" +
//               "                <sld:Rule>\n" +
//               "                    <sld:Name>rule02</sld:Name>\n" +
//               "                    <sld:Title>小麦</sld:Title>\n" +
//               "                    <ogc:Filter>\n" +
//               "                        <ogc:PropertyIsEqualTo>\n" +
//               "                            <ogc:PropertyName>Class_name</ogc:PropertyName>\n" +
//               "                            <ogc:Literal>小麦</ogc:Literal>\n" +
//               "                        </ogc:PropertyIsEqualTo>\n" +
//               "                    </ogc:Filter>\n" +
//               "                    <sld:PolygonSymbolizer>\n" +
//               "                        <sld:Fill>\n" +
//               "                            <sld:CssParameter name=\"fill\">#BEAED4</sld:CssParameter>\n" +
//               "                            <sld:CssParameter name=\"fill-opacity\">0.5</sld:CssParameter>\n" +
//               "                        </sld:Fill>\n" +
//               "                    </sld:PolygonSymbolizer>\n" +
//               "                </sld:Rule>\n" +
//               "                <sld:Rule>\n" +
//               "                    <sld:Name>rule03</sld:Name>\n" +
//               "                    <sld:Title>建设用地</sld:Title>\n" +
//               "                    <ogc:Filter>\n" +
//               "                        <ogc:PropertyIsEqualTo>\n" +
//               "                            <ogc:PropertyName>Class_name</ogc:PropertyName>\n" +
//               "                            <ogc:Literal>建设用地</ogc:Literal>\n" +
//               "                        </ogc:PropertyIsEqualTo>\n" +
//               "                    </ogc:Filter>\n" +
//               "                    <sld:PolygonSymbolizer>\n" +
//               "                        <sld:Fill>\n" +
//               "                            <sld:CssParameter name=\"fill\">#FDC086</sld:CssParameter>\n" +
//               "                            <sld:CssParameter name=\"fill-opacity\">0.5</sld:CssParameter>\n" +
//               "                        </sld:Fill>\n" +
//               "                    </sld:PolygonSymbolizer>\n" +
//               "                </sld:Rule>\n" +
//               "                <sld:Rule>\n" +
//               "                    <sld:Name>rule04</sld:Name>\n" +
//               "                    <sld:Title>林地</sld:Title>\n" +
//               "                    <ogc:Filter>\n" +
//               "                        <ogc:PropertyIsEqualTo>\n" +
//               "                            <ogc:PropertyName>Class_name</ogc:PropertyName>\n" +
//               "                            <ogc:Literal>林地</ogc:Literal>\n" +
//               "                        </ogc:PropertyIsEqualTo>\n" +
//               "                    </ogc:Filter>\n" +
//               "                    <sld:PolygonSymbolizer>\n" +
//               "                        <sld:Fill>\n" +
//               "                            <sld:CssParameter name=\"fill\">#FFFF99</sld:CssParameter>\n" +
//               "                            <sld:CssParameter name=\"fill-opacity\">0.5</sld:CssParameter>\n" +
//               "                        </sld:Fill>\n" +
//               "                    </sld:PolygonSymbolizer>\n" +
//               "                </sld:Rule>\n" +
//               "                <sld:Rule>\n" +
//               "                    <sld:Name>rule05</sld:Name>\n" +
//               "                    <sld:Title>棉花</sld:Title>\n" +
//               "                    <ogc:Filter>\n" +
//               "                        <ogc:PropertyIsEqualTo>\n" +
//               "                            <ogc:PropertyName>Class_name</ogc:PropertyName>\n" +
//               "                            <ogc:Literal>棉花</ogc:Literal>\n" +
//               "                        </ogc:PropertyIsEqualTo>\n" +
//               "                    </ogc:Filter>\n" +
//               "                    <sld:PolygonSymbolizer>\n" +
//               "                        <sld:Fill>\n" +
//               "                            <sld:CssParameter name=\"fill\">#386CB0</sld:CssParameter>\n" +
//               "                            <sld:CssParameter name=\"fill-opacity\">0.5</sld:CssParameter>\n" +
//               "                        </sld:Fill>\n" +
//               "                    </sld:PolygonSymbolizer>\n" +
//               "                </sld:Rule>\n" +
//               "                <sld:Rule>\n" +
//               "                    <sld:Name>rule06</sld:Name>\n" +
//               "                    <sld:Title>水域</sld:Title>\n" +
//               "                    <ogc:Filter>\n" +
//               "                        <ogc:PropertyIsEqualTo>\n" +
//               "                            <ogc:PropertyName>Class_name</ogc:PropertyName>\n" +
//               "                            <ogc:Literal>水域</ogc:Literal>\n" +
//               "                        </ogc:PropertyIsEqualTo>\n" +
//               "                    </ogc:Filter>\n" +
//               "                    <sld:PolygonSymbolizer>\n" +
//               "                        <sld:Fill>\n" +
//               "                            <sld:CssParameter name=\"fill\">#F0027F</sld:CssParameter>\n" +
//               "                            <sld:CssParameter name=\"fill-opacity\">0.5</sld:CssParameter>\n" +
//               "                        </sld:Fill>\n" +
//               "                    </sld:PolygonSymbolizer>\n" +
//               "                </sld:Rule>\n" +
//               "                <sld:Rule>\n" +
//               "                    <sld:Name>rule07</sld:Name>\n" +
//               "                    <sld:Title>葡萄</sld:Title>\n" +
//               "                    <ogc:Filter>\n" +
//               "                        <ogc:PropertyIsEqualTo>\n" +
//               "                            <ogc:PropertyName>Class_name</ogc:PropertyName>\n" +
//               "                            <ogc:Literal>葡萄</ogc:Literal>\n" +
//               "                        </ogc:PropertyIsEqualTo>\n" +
//               "                    </ogc:Filter>\n" +
//               "                    <sld:PolygonSymbolizer>\n" +
//               "                        <sld:Fill>\n" +
//               "                            <sld:CssParameter name=\"fill\">#BF5B17</sld:CssParameter>\n" +
//               "                            <sld:CssParameter name=\"fill-opacity\">0.5</sld:CssParameter>\n" +
//               "                        </sld:Fill>\n" +
//               "                    </sld:PolygonSymbolizer>\n" +
//               "                </sld:Rule>\n" +
//               "            </sld:FeatureTypeStyle>\n" +
//               "        </sld:UserStyle>\n" +
//               "    </sld:UserLayer>\n" +
//               "</sld:StyledLayerDescriptor>\n" +
//               "\n";
////        File styleFile = new File("D:\\geoserver\\data_dir\\data\\tifPublishTest\\181222_6247_0000\\classes.sld");
//        ilayerPublishService.updateStyle(xmlJson, "classes");

        return ResultUtil.success(jsonObjects);
    }

    @RequestMapping(value = "/test726")
    @CrossOrigin()
    @ResponseBody
    public Result test726() throws Exception {

        List<AttributeInfoDto> attributeInfoDtoList = new ArrayList<>();
        AttributeInfoDto temp = new AttributeInfoDto();
        temp.setAttributeValue("0");
        temp.setFillColor("#1B9E77");
        temp.setFillOpacity("0.5");
        temp.setStrokeColor("#FF0000");
        temp.setStrokeOpacity("0.5");
        attributeInfoDtoList.add(temp);
        AttributeInfoDto temp1 = new AttributeInfoDto();
        temp1.setAttributeValue("1");
        temp1.setFillColor("#D95F02");
        temp1.setFillOpacity("0.5");
        temp1.setStrokeColor("#FF0000");
        temp1.setStrokeOpacity("0.5");
        attributeInfoDtoList.add(temp1);
        String xml = ilayerPublishService.generateRuleXml("Islands", 2, attributeInfoDtoList);
        System.out.println(xml);

        String xmlJson = ilayerPublishService.xml2jsonString("F:\\pdmTest2\\pdm-master\\officialStorage\\镶嵌产品\\MP_广东省_广州市_白天\\2.sld");
        xmlJson = xmlJson.replace(xmlJson.substring(xmlJson.indexOf("<sld:Rule>"), xmlJson.lastIndexOf(" </sld:FeatureTypeStyle>")), xml);
        System.out.println("xmljson:    " + xmlJson);
        ilayerPublishService.updateStyle(xmlJson, "manycolors4");

        return ResultUtil.success("");
    }


    @RequestMapping(value = "/changeStyle")
    @CrossOrigin()
    @ResponseBody
    public Result changeStyle(@RequestParam(value = "styleJson", required = true) String styleJson) throws Exception {

        JSONObject styleJsonObj = JSONObject.fromObject(styleJson);
        List<AttributeInfoDto> attributeInfoDtoList = new ArrayList<>();
        JSONArray xmlJsonArray = styleJsonObj.getJSONArray("attributeInfoDtoList");
        String flag = ilayerPublishService.getAttributeFlag(styleJsonObj.getString("productId"), styleJsonObj.getString("singleId"), styleJsonObj.getString("attributeName"));
        String attributeName = styleJsonObj.getString("attributeName");
            if (flag.equals("true")) {
             styleJsonObj.put("attributeName",ilayerPublishService.getAttributeOtherName(styleJsonObj.getString("productId"), styleJsonObj.getString("singleId"), styleJsonObj.getString("attributeName")));
            }
            for (int i = 0; i < xmlJsonArray.size(); i++) {
            AttributeInfoDto attributeInfoDto = new AttributeInfoDto();
            JSONObject attributeJsonObj = JSONObject.fromObject(xmlJsonArray.get(i));
            if(flag.equals("true")){
                String otherValue = ilayerPublishService.getAttributeOtherValue(styleJsonObj.getString("productId"), styleJsonObj.getString("singleId"), attributeName,attributeJsonObj.getString("attributeValue"));
                attributeInfoDto.setAttributeValue(otherValue);
             }
             else {
                attributeInfoDto.setAttributeValue(attributeJsonObj.getString("attributeValue"));
            }
            attributeInfoDto.setFillColor(attributeJsonObj.getString("fillColor"));
            attributeInfoDto.setFillOpacity(attributeJsonObj.getString("fillOpacity"));
            attributeInfoDto.setStrokeColor(attributeJsonObj.getString("strokeColor"));
            attributeInfoDto.setStrokeOpacity(attributeJsonObj.getString("strokeOpacity"));
            attributeInfoDtoList.add(attributeInfoDto);
            }
        String ruleXml = ilayerPublishService.generateRuleXml(styleJsonObj.getString("attributeName"), Integer.parseInt(styleJsonObj.getString("valueCount")), attributeInfoDtoList);
        SldPathAndNameDto sldPathAndNameDto = ilayerPublishService.getSldPathAndName(styleJsonObj.getString("productId"), styleJsonObj.getString("singleId"));
        String xmlJson = ilayerPublishService.xml2jsonString(sldPathAndNameDto.getSldPath());
        xmlJson = ilayerPublishService.generateSldBody(xmlJson, ruleXml);
        ilayerPublishService.updateStyle(xmlJson, sldPathAndNameDto.getSldName());//之后再还原回去
        return ResultUtil.success("提交成功！");
    }

    @RequestMapping(value = "/getAttributeList")
    @CrossOrigin()
    @ResponseBody
    public Result getAttributeList(@RequestParam(value = "productId", required = true) String productId, @RequestParam(value = "singleId", required = true) String singleId) {

        List<String> attributeList = ilayerPublishService.getAttributeList(productId, singleId);
        return ResultUtil.success(attributeList);
    }

    @RequestMapping(value = "/getAttributeValueInfo")
    @CrossOrigin()
    @ResponseBody
    public Result getAttributeValueInfo(@RequestParam(value = "productId", required = true) String productId, @RequestParam(value = "singleId", required = true) String singleId, @RequestParam(value = "attributeName", required = true) String attributeName) {

        AttributeValuesDto attributeValuesDto = ilayerPublishService.getAttributeValueInfo(productId, singleId, attributeName);
        return ResultUtil.success(attributeValuesDto);
    }

    @RequestMapping(value = "/resetLayer")
    @CrossOrigin()
    @ResponseBody
    public Result resetLayer(@RequestParam(value = "productId", required = true) String productId, @RequestParam(value = "singleId", required = true) String singleId) {

//        AttributeValuesDto attributeValuesDto = ilayerPublishService.getAttributeValueInfo(productId, singleId, attributeName);
        SldPathAndNameDto sldPathAndNameDto = ilayerPublishService.getSldPathAndName(productId, singleId);
        ilayerPublishService.resetStyle(sldPathAndNameDto.getSldName());
        return ResultUtil.success("提交成功！");
    }

}
