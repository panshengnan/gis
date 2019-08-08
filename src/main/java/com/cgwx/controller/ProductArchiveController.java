package com.cgwx.controller;

import com.cgwx.aop.result.Result;
import com.cgwx.aop.result.ResultUtil;
import com.cgwx.data.dto.PdmArchiveRecordsInfoStr;
import com.cgwx.data.dto.UploadFileReturn;
import com.cgwx.data.entity.*;
import com.cgwx.service.IProductArchiveService;
import com.cgwx.service.IProductDownloadService;
import com.cgwx.service.IlayerPublishService;
import com.github.pagehelper.PageInfo;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin
@Controller
public class ProductArchiveController {


    @Autowired
    IProductArchiveService iProductArchiveService;

    @Autowired
    IProductDownloadService iProductDownloadService;

    @Autowired
    IlayerPublishService ilayerPublishService;//ss0726

    @Autowired
    private AmqpTemplate amqpTemplate;

    @RequestMapping(value = "/test")
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result test() throws IOException, FactoryException, TransformException {
//       System.out.println(iProductArchiveService.getNextProductId(4));
//        System.out.println(iProductDownloadService.getEntityFilePath("123"));
//              layerPublishService.publishTif("D:\\Program Files (x86)\\GeoServer 2.13.2\\data_dir\\data\\长春热岛201607.tif","sf","#000000");
        //File file = new File("D:\\Program Files (x86)\\GeoServer 2.13.2\\data_dir\\data\\长春热岛201607.tif");
        //System.out.println(file.toURL());

//       return ResultUtil.success(iProductArchiveService.getSecondaryFileStructure("C:\\Users\\37753\\Desktop\\tmpPic\\哈哈"));
//        iProductArchiveService.copyFolder("C:\\Users\\37753\\Desktop\\产品管理后台\\pdm\\专题产品","C:\\Users\\37753\\Desktop\\产品管理后台\\pdm\\高级产品");
//        JSONObject content = new JSONObject();
//        content.put("path","gou星");
//        amqpTemplate.convertAndSend("publishOrder",content);
//        System.out.println("到这了");
//        String heeh = "";
//        heeh =   iProductArchiveService.xml2jsonString("D:\\样例数据-高级产品管理系统\\分幅产品\\title_K51E007021\\title_K51E007021_cutline.xml");
//        JSONObject content = new JSONObject();
//        content.put("path", "gou星");
//        amqpTemplate.convertAndSend("archive2OfficialStorage", content);

//        int count =10;
//        for(int i=0;i<count;i++)
//        {
//            amqpTemplate.convertAndSend("publishOrder123",Integer.toString(i));
//            System.out.println("1111111111111111111111");
//        }

//        String path = "F:\\pdmTest2\\pdm-master\\临时存储区\\珲春市老龙口水深_库容反演.zip";
//        System.out.println(iProductArchiveService.getZipCodeType(path));
//        iProductArchiveService.unZip(path,"F:\\pdmTest2\\pdm-master\\临时存储区",iProductArchiveService.getZipCodeType(path));

//        String path = "F:\\pdmTest2\\pdm-master\\officialStorage\\正射产品\\GTC_JL101A_PMS_20161122151023_000016007_202_0011_001_0XX\\GTC_JL101A_PMS_20161122151023_000016007_202_0011_001_0XX.tif";
//        layerPublishService.publishTif(path, "tiffTest", "#000000");

//        String path = "D:\\Program Files (x86)\\GeoServer 2.13.2\\data_dir\\data\\hunchun\\hunchun.shp";
//        layerPublishService.getShpLatLonBounding(path);

//        String path = "F:\\pdmTest2\\pdm-master\\临时存储区\\吉林珲春20140928生态监测与评价";
//        String filePath = "F:\\pdmTest2\\pdm-master\\临时存储区\\松原市洪涝灾害风险评估\\松原市洪涝灾害风险评估.pdf";
//        String filePath2 = "F:\\pdmTest2\\pdm-master\\临时存储区\\松原市洪涝灾害风险评估\\松原市洪涝灾害风险评估.docx";
//        File zipFile = new File("F:\\pdmTest2\\pdm-master\\临时存储区\\吉林珲春20140928生态监测与评价\\1.zip");
//        iProductArchiveService.rename(path,"test");
//        List<String> fileList = iProductArchiveService.getShpFileList(path);
//        System.out.println(fileList);
//        iProductArchiveService.exportZip(fileList,zipFile);

//        String path = "F:\\pdmTest2\\pdm-master\\临时存储区\\吉林辽源20161001森林资源调查\\吉林辽源20161001森林资源调查\\吉林辽源20161001森林资源调查.shp";
//        layerPublishService.getShpLatLonBounding(path);

//        String tifPath = "F:\\中俄边境\\1\\给防火板-第一期\\中俄边境影像图.jpg";
//        iProductArchiveService.ZoomImg(tifPath);
////////////////////////////////////////给大数据加的

//        System.out.println("daozhele");
//        String url = "http://localhost:8044/test128";
//        Map<String, String> param = new HashMap<>();
//        param.put("name","李正阳");
//        // 创建Httpclient对象
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        CloseableHttpResponse response = null;
//        String resultString = "";
//        try {
//            // 创建Http Post请求
//            HttpPost httpPost = new HttpPost(url);
//            // 创建参数列表
//            if (param != null) {
//                List<NameValuePair> paramList = new ArrayList<>();
//                for (String key : param.keySet()) {
//                    paramList.add(new BasicNameValuePair(key, param.get(key)));
//                }
//                // 模拟表单
//                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
//                httpPost.setEntity(entity);
//            }
//            // 执行http请求
//            response = httpClient.execute(httpPost);
//            resultString = EntityUtils.toString(response.getEntity(), "utf-8");System.out.println("有错误2！");
//
//        } catch (Exception e) {
//            System.out.println("有错误！");
//            e.printStackTrace();
//        } finally {
//            try {
//                response.close();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        System.out.println(resultString);



//        HttpClient httpClient = new HttpClient();
//        httpClient.getParams().setContentCharset("UTF-8");
//        String url = "http://localhost:8078/test128";
//        PostMethod postMethod = new PostMethod(url);
//        String data = "{\"name\":\"123\"}";
//        String data2="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
//                "\t<name>123</name>";
//        JSONObject jsO = new JSONObject();
//
//        RequestEntity requestEntity = new StringRequestEntity(data2, "application/xml", "UTF-8");
//        postMethod.setRequestEntity(requestEntity);
//        postMethod.setParameter("name","123");
//        int result = httpClient.executeMethod(postMethod);
//
//
//        ////////////////////////////////////////////
//        return ResultUtil.success(postMethod.getResponseBodyAsString());
        return ResultUtil.success();
    }

    @RequestMapping("/test128")
    @CrossOrigin(methods = RequestMethod.POST)
    @ResponseBody
    public Result test128(String name)
    {
        return ResultUtil.success(name+"is axing");
    }

    @RequestMapping("/downL")
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public void downL(HttpServletRequest request1, HttpServletResponse response1, HttpServletRequest request2, HttpServletResponse response2, String productId, String fileName1, String fileName2) {

        System.out.println("收到文件下载请求！");
        downloadFile1(request1, response1, productId, fileName1);
        try {
            Thread.currentThread().sleep(10000);//毫秒
        } catch (Exception e) {
        }
        downloadFile2(request2, response2, productId, fileName2);

    }

    @RequestMapping("/downloadFile1")
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public void downloadFile1(HttpServletRequest request, HttpServletResponse response, String productId, String fileName) {

        System.out.println("收到文件下载请求1！" + fileName);
        String filePath = iProductDownloadService.getEntityFilePath(productId);
        iProductDownloadService.downloadFile(request, response, fileName, filePath);

    }

    @RequestMapping("/downloadFile2")
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public void downloadFile2(HttpServletRequest request, HttpServletResponse response, String productId, String fileName) {

        System.out.println("收到文件下载请求2！" + fileName);
        String filePath = iProductDownloadService.getEntityFilePath(productId);
        iProductDownloadService.downloadFile(request, response, fileName, filePath);
    }


    @RequestMapping(value = "/uploadThemeticProduct")
    @CrossOrigin(methods = RequestMethod.POST)
    @ResponseBody
    public Result uploadThemeticProduct(@RequestParam(value = "file", required = true) MultipartFile file, String archivePersonnel) throws Exception {

        System.out.print("收到专题产品上传请求！");
        System.out.println("归档人Id:" + archivePersonnel);
        UploadFileReturn uploadFileReturn = iProductArchiveService.uploadFile(file);
        System.out.println(uploadFileReturn.getFileName());
        System.out.println(uploadFileReturn.getFilePath());
        String path = iProductArchiveService.unZip(uploadFileReturn.getFileName(), uploadFileReturn.getFilePath(), iProductArchiveService.getZipCodeType(uploadFileReturn.getFileName()));
        System.out.println("解压后的路径是：" + path);
        return ResultUtil.success(iProductArchiveService.getSecondaryFileStructureAndWriteCheckTable(path, archivePersonnel, 1));
    }

    @RequestMapping(value = "/uploadAdvancedProduct")
    @CrossOrigin(methods = RequestMethod.POST)
    @ResponseBody
    public Result uploadAdvancedProduct(@RequestParam(value = "file", required = true) MultipartFile file, String archivePersonnel) throws Exception {

        System.out.print("收到高级产品上传请求！");
        System.out.println("归档人Id:" + archivePersonnel);
        UploadFileReturn uploadFileReturn = iProductArchiveService.uploadFile(file);
        String path = iProductArchiveService.unZip(uploadFileReturn.getFileName(), uploadFileReturn.getFilePath(), iProductArchiveService.getZipCodeType(uploadFileReturn.getFileName()));
        System.out.println("解压后的路径是：" + path);
        String tempId = iProductArchiveService.writeArchiveRecordAndWriteArchiveCheckInfo(path, archivePersonnel, 6);
        System.out.println("生成的临时id是：" + tempId);
        return ResultUtil.success(tempId);
    }

    @RequestMapping(value = "/commitAdvancedProductInfo")
    @CrossOrigin(methods = RequestMethod.POST)
    @ResponseBody
    public Result commitAdvancedProductInfo(@RequestParam(value = "json", required = true) String json) {

        System.out.println(json);
        //更新产品大表信息
        System.out.println("更新产品大表信息！！！！！");
        JSONObject jsonObject = JSONObject.fromObject(json);
        int productType = Integer.parseInt(jsonObject.getString("productType"));
        String productId = iProductArchiveService.getNextProductId(productType);
        String productName = iProductArchiveService.getProductName(jsonObject.getString("tempId"));
        GisProductInfo pdmProductInfo = new GisProductInfo();
        pdmProductInfo.setProductId(productId);
        pdmProductInfo.setProductName(productName);
        pdmProductInfo.setProductType(productType);
        pdmProductInfo.setProduceArea(jsonObject.getString("produceArea"));
        pdmProductInfo.setProducer(jsonObject.getString("producer"));
        //更新producer
        System.out.println("更新producer！！！！！");
        iProductArchiveService.insertPdmProducerInfo(jsonObject.getString("producer"));
        //更新producer
        pdmProductInfo.setDeliverName(jsonObject.getString("deliverName"));
        pdmProductInfo.setDeliverMethod(jsonObject.getString("deliverMethod"));
        pdmProductInfo.setDataProcessStep(jsonObject.getString("dataProcessStep"));
        String deliverTime = jsonObject.getString("deliverTime").replace("T", " ");
        String produceTime = jsonObject.getString("produceTime").replace("T", " ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            pdmProductInfo.setDeliverTime(dateFormat.parse(deliverTime));
            pdmProductInfo.setProduceTime(dateFormat.parse(produceTime));
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
        }
        iProductArchiveService.updateProductInfo(pdmProductInfo);
        //更新产品大表信息

        //正式将数据移入归档区
        String advancedProductPathVar = "";
        String tempPath = iProductArchiveService.getThemeticProductTemporaryPath(jsonObject.getString("tempId"));
        String officialPath = "";
        switch (productType) {
            case 2://正射
                advancedProductPathVar = "\\正射产品\\" + productName;
                break;
            case 3://镶嵌
                advancedProductPathVar = "\\镶嵌产品\\" + productName;
                break;
            case 4://分幅
                advancedProductPathVar = "\\分幅产品\\" + productName;
                break;
            default:
                break;
        }
        officialPath = System.getProperty("user.dir") + "\\officialStorage" + advancedProductPathVar;
        String zipFilePath = tempPath + ".zip";
        String zipFileTargetPath = officialPath + "\\" + productName + ".zip";
        System.out.println("zip文件路径为：" + zipFilePath);
        System.out.println("zip文件目的路径为：" + zipFileTargetPath);
        File file = new File(officialPath);
        if (!file.exists())
            file.mkdir();
        JSONObject archiveContent = new JSONObject();
        archiveContent.put("officialPath", officialPath);
        archiveContent.put("tempPath", tempPath);
        archiveContent.put("zipFilePath", zipFilePath);
        archiveContent.put("zipFileTargetPath", zipFileTargetPath);
//        iProductArchiveService.copyFolder(tempPath,officialPath);
//        File zipFile = new File(zipFilePath);
//        File zipTargetFile = new File(zipFileTargetPath);
//        iProductArchiveService.copyFile(zipFile, zipTargetFile);
        //开始发布tif
        archiveContent.put("path", "");
        archiveContent.put("productId", productId);
        archiveContent.put("singleId", "");
        archiveContent.put("productType", productType);
        archiveContent.put("productName", productName);
        archiveContent.put("producer", jsonObject.getString("producer"));
        amqpTemplate.convertAndSend("test1232", archiveContent);
        //开始发布tif
        //正式将数据移入归档区

        //更新各自的分表
        System.out.println("更新各自的分表！！！！！");

        //生成文件发布链接
//        iProductDownloadService.generateProductLink(productType, productId, productName);
        //生成文件发布链接

        //操作一波归档记录表
        GisArchiveRecordsInfo pdmArchiveRecordsInfo = new GisArchiveRecordsInfo();
        pdmArchiveRecordsInfo.setArchiveResult(2);//0：失败；1：成功；2：归档中
        pdmArchiveRecordsInfo.setProductId(productId);
        pdmArchiveRecordsInfo.setArchiveType(productType);
        iProductArchiveService.updateArchiveRecordsInfo(pdmArchiveRecordsInfo, jsonObject.getString("tempId"), productType);
        //操作一波归档记录表
        return ResultUtil.success(productId);
    }

    @RequestMapping(value = "/commitThemeticProductInfo")
    @CrossOrigin(methods = RequestMethod.POST)
    @ResponseBody
    public Result commitThemeticProductInfo(@RequestParam(value = "json", required = true) String json) throws IOException, FactoryException, TransformException {

        System.out.println(json);
        JSONObject jsonObject = JSONObject.fromObject(json);
        String productId = iProductArchiveService.getNextProductId(1);//1:专题
        String productName = iProductArchiveService.getProductName(jsonObject.getString("tempId"));
        GisProductInfo pdmProductInfo = new GisProductInfo();
        pdmProductInfo.setProductId(productId);
        pdmProductInfo.setProductName(productName);
        pdmProductInfo.setProductType(1);
        pdmProductInfo.setProductDescription(jsonObject.getString("productDescription"));
        pdmProductInfo.setClientName(jsonObject.getString("clientName"));
        pdmProductInfo.setDeliverName(jsonObject.getString("deliverName"));
        String deliverTime = jsonObject.getString("deliverTime").replace("T", " ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            pdmProductInfo.setDeliverTime(dateFormat.parse(deliverTime));
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
        }
        System.out.println(pdmProductInfo.getDeliverTime());
        iProductArchiveService.updateProductInfo(pdmProductInfo);
        System.out.println(jsonObject.getJSONArray("industry"));
        JSONArray industryJsonArray = jsonObject.getJSONArray("industry");
        for (int i = 0; i < industryJsonArray.size(); i++) {
            JSONObject jsonObjectTmp = industryJsonArray.getJSONObject(i);
            GisThemeticProductDetailIndustryInfo pdmTheProDetIndInfo = new GisThemeticProductDetailIndustryInfo();
            pdmTheProDetIndInfo.setIndustryLevel1(jsonObjectTmp.getInt("level1"));
            pdmTheProDetIndInfo.setIndustryLevel2(jsonObjectTmp.getInt("level2"));
            pdmTheProDetIndInfo.setProductId(productId);
            iProductArchiveService.updateThemeticProductDetailIndustry(pdmTheProDetIndInfo);
        }
        String tempPath = iProductArchiveService.getThemeticProductTemporaryPath(jsonObject.getString("tempId"));
        String officialPath = System.getProperty("user.dir") + "\\officialStorage\\专题产品\\" + productName;
        String zipFilePath = tempPath + ".zip";
        String zipFileTargetPath = officialPath + "\\" + productName + ".zip";
        System.out.println("zip文件路径为：" + zipFilePath);
        System.out.println("zip文件目的路径为：" + zipFileTargetPath);
        File file = new File(officialPath);
        if (!file.exists())
            file.mkdir();
        iProductArchiveService.copyFolder(tempPath, officialPath);
        File zipFile = new File(zipFilePath);
        File zipTargetFile = new File(zipFileTargetPath);
        iProductArchiveService.copyFile(zipFile, zipTargetFile);
        JSONArray singlePeriodProductInfoJsonArray = jsonObject.getJSONArray("singlePeriodProductInfo");
        GisThemeticProductDetailInfo pdmThemeticProductDetailInfo = new GisThemeticProductDetailInfo();
        for (int i = 0; i < singlePeriodProductInfoJsonArray.size(); i++) {
            JSONObject jsonObjectTmp = singlePeriodProductInfoJsonArray.getJSONObject(i);
            String imagingTime = jsonObjectTmp.getString("imagingTime").replace("T", " ");
            try {
                pdmThemeticProductDetailInfo.setImagingTime(dateFormat.parse(imagingTime));
            } catch (ParseException pe) {
                System.out.println(pe.getMessage());
            }
            pdmThemeticProductDetailInfo.setProducer(jsonObjectTmp.getString("producer"));
            //更新producer
            iProductArchiveService.insertPdmProducerInfo(jsonObjectTmp.getString("producer"));
            String produceTime = jsonObjectTmp.getString("produceTime").replace("T", " ");
            try {
                pdmThemeticProductDetailInfo.setProduceTime(dateFormat.parse(produceTime));
            } catch (ParseException pe) {
                System.out.println(pe.getMessage());
            }
            pdmThemeticProductDetailInfo.setProductId(productId);
            pdmThemeticProductDetailInfo.setSatellite(jsonObjectTmp.getString("satellite"));
            pdmThemeticProductDetailInfo.setSensor(jsonObjectTmp.getString("sensor"));
            pdmThemeticProductDetailInfo.setSinglePeriodProductDirectory(officialPath + '\\' + jsonObjectTmp.getString("singleName"));//注意以后改了
            pdmThemeticProductDetailInfo.setSinglePeriodProductId(jsonObjectTmp.getString("singleTempId"));
            pdmThemeticProductDetailInfo.setSinglePeriodProductName(jsonObjectTmp.getString("singleName"));
            iProductArchiveService.updateThemeticProductDetail(pdmThemeticProductDetailInfo);
            //下面这段是新加的
            String singlePath = officialPath + '\\' + jsonObjectTmp.getString("singleName");
            if (iProductArchiveService.isExistTif(singlePath)) {
                //开始发布tif
                JSONObject content = new JSONObject();
                System.out.println("tif文件夹路径：" + officialPath + '\\' + jsonObjectTmp.getString("singleName"));
//                String tifPath = ilayerPublishService.getTifFilePath(singlePath);//ss0801
//                content.put("path", tifPath);//注意以后改了
//                System.out.println("tif路径是：" + content.getString("path"));
//                //////////////////////////
//                if (!iProductArchiveService.isExistJpg(singlePath)) {
//                    iProductArchiveService.ZoomImg(iProductArchiveService.changeTiftoJpg(tifPath));
//                }
                //////////////////////////
                content.put("productId", productId);
                content.put("singleId", jsonObjectTmp.getString("singleTempId"));
                content.put("productType", 1);//专题
                content.put("count", Integer.toString(i));
                System.out.println("CONTENT!!!!!!" + content);
                amqpTemplate.convertAndSend("publishOrder123", content);
                System.out.println("正在发布tif");
                //开始发布tif
            } else {
                if (iProductArchiveService.isExistShp(singlePath)) {

                    File zipFileSource = new File(singlePath + "\\" + jsonObjectTmp.getString("singleName") + ".zip");
                    String layerName = productId.substring(16, productId.length() - 1) + jsonObjectTmp.getString("singleTempId");
                    iProductArchiveService.rename(singlePath, layerName);
                    List<String> fileList = iProductArchiveService.getShpFileList(singlePath);
                    iProductArchiveService.exportZip(fileList, zipFileSource);
//                    iProductArchiveService.rename(singlePath, jsonObjectTmp.getString("singleName"));
                    String RESTURL = "http://192.168.20.7:8086/geoserver";
                    String RESTUSER = "admin";
                    String RESTPW = "geoserver";
                    List<String> workspaces = null;
                    String result = "";
                    try {
                        GeoServerRESTReader reader = new GeoServerRESTReader(RESTURL, RESTUSER, RESTPW);
                        GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(RESTURL, RESTUSER, RESTPW);
//
//
                        workspaces = reader.getWorkspaceNames();
                        if (workspaces.contains("tifPublishTest")) {
                            if (publisher.publishShp("tifPublishTest", layerName, layerName, zipFileSource, ilayerPublishService.getShpEPSG(ilayerPublishService.getShpPathWithoutCutline(singlePath)))) {//ss0726
                                result = "发布成功！";
                            } else {
                                result = "发布失败！";
                            }
                        }
                        System.out.println(result);
                    } catch (Exception mue) {
                        mue.printStackTrace();
                    }
                    zipFileSource.delete();
                    String shpPath = ilayerPublishService.getShpPathWithoutCutline(singlePath);
                    System.out.println("shp的路径是：" + shpPath);
//                    String geoJson = ilayerPublishService.getShpLatLonBounding(shpPath);//ss0731
//                    System.out.println("geoJson是：" + geoJson);
                    iProductArchiveService.rename(singlePath, jsonObjectTmp.getString("singleName"));
//                    ilayerPublishService.updateThemeticProductDetailImgGeo(productId, jsonObjectTmp.getString("singleTempId"), geoJson);//ss0731
                    //////
                    GisProductLayerInfo pdmProductLayerInfo = new GisProductLayerInfo();
                    pdmProductLayerInfo.setProductId(productId);
                    pdmProductLayerInfo.setSingleId(jsonObjectTmp.getString("singleTempId"));
                    pdmProductLayerInfo.setLayerName("tifPublishTest:" + layerName);
                    System.out.println("更新图层中");
//                    ilayerPublishService.updateProductLayerInfo(pdmProductLayerInfo);//ss0731
                    System.out.println("更新图层完毕！图层名字：" + pdmProductLayerInfo.getLayerName());

                }
            }
        }
        //上面这段是新加的

        GisThemeticProductInfo pdmThemeticProductInfo = new GisThemeticProductInfo();
        pdmThemeticProductInfo.setProductId(productId);
        pdmThemeticProductInfo.setParentDirectory(officialPath);
        pdmThemeticProductInfo.setThemeticProductName(productName);
        iProductArchiveService.updateThemeticProduct(pdmThemeticProductInfo);
        iProductDownloadService.generateProductLink(1, productId, productName);
        //操作一波归档记录表
        GisArchiveRecordsInfo pdmArchiveRecordsInfo = new GisArchiveRecordsInfo();
        pdmArchiveRecordsInfo.setArchiveResult(1);
        pdmArchiveRecordsInfo.setProductId(productId);
        iProductArchiveService.updateArchiveRecordsInfo(pdmArchiveRecordsInfo, jsonObject.getString("tempId"), 1);
        //操作一波归档记录表
        System.out.println("专题归档结束，不包括异步的事！");
        return ResultUtil.success(productId);
    }

    @RequestMapping(value = "/getArchiveRecords")
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result getArchiveRecords(@RequestParam(value = "archivePersonnel", required = true) String archivePersonnel,
                                    @RequestParam(value = "curPageNum", required = true) int curPageNum,
                                    @RequestParam(value = "maxResult", required = true) int maxResult,
                                    @RequestParam(value = "condition", required = true) String condition) {

        System.out.println("归档人id为:" + archivePersonnel);
        System.out.println("条件为:" + condition);
        String productName = "";
        int archiveStatus = -1;//状态码
        int archiveType = -1;//状态码
        if (condition != null && !condition.equals("")) {
            productName = condition;
            if ("专题产品".contains(condition))
                archiveType = 1;
            else if ("正射产品".contains(condition))
                archiveType = 2;
            else if ("镶嵌产品".contains(condition))
                archiveType = 3;
            else if ("分幅产品".contains(condition))
                archiveType = 4;
        }
        System.out.println("产品名称：" + productName);
        if (condition.equals("成功"))
            archiveStatus = 1;
        else if (condition.equals("失败"))
            archiveStatus = 0;
        System.out.println("产品类型：" + archiveType);
        System.out.println("产品状态：" + archiveStatus);
        PageInfo<GisArchiveRecordsInfo> pageInfo = iProductArchiveService.getArchiveRecordList(archivePersonnel, curPageNum, maxResult, productName, archiveType, archiveStatus);
        PageInfo<PdmArchiveRecordsInfoStr> pageInfoForStr = new PageInfo<>();
        pageInfoForStr.setTotal(pageInfo.getTotal());
        pageInfoForStr.setPages(pageInfo.getPages());
        List<GisArchiveRecordsInfo> archiveRecordList = pageInfo.getList();
        List<PdmArchiveRecordsInfoStr> archiveRecordListWithName = new ArrayList<PdmArchiveRecordsInfoStr>();
        for (GisArchiveRecordsInfo pdmArchiveRecordsInfo : archiveRecordList) {
            PdmArchiveRecordsInfoStr pdmArchiveRecordsInfoStr = new PdmArchiveRecordsInfoStr();
            String archiverName = pdmArchiveRecordsInfo.getArchivePersonnel();
            pdmArchiveRecordsInfoStr.setArchivePersonnel(iProductArchiveService.getArchivePersonnelName(archiverName));
            pdmArchiveRecordsInfoStr.setArchiveResult(pdmArchiveRecordsInfo.getArchiveResult());
            pdmArchiveRecordsInfoStr.setArchiveType(pdmArchiveRecordsInfo.getArchiveType());
            pdmArchiveRecordsInfoStr.setProductId(pdmArchiveRecordsInfo.getProductId());
            pdmArchiveRecordsInfoStr.setProductName(pdmArchiveRecordsInfo.getProductName());
            SimpleDateFormat formatyyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            pdmArchiveRecordsInfoStr.setArchiveTime(formatyyyy_MM_dd.format(pdmArchiveRecordsInfo.getArchiveTime()));
            archiveRecordListWithName.add(pdmArchiveRecordsInfoStr);
        }
        pageInfoForStr.setList(archiveRecordListWithName);
        return ResultUtil.success(pageInfoForStr);
    }

    @RequestMapping(value = "/getDeliverNameList")
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result getDeliverNameList(String deliverName) {
        return ResultUtil.success(iProductArchiveService.getDeliverNameList(deliverName));
    }

    @RequestMapping(value = "/getClientNameList")
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result getClientNameList(String clientName) {
        return ResultUtil.success(iProductArchiveService.getClientNameList(clientName));
    }

    @RequestMapping(value = "/getProducerList")
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result getProducerList(String producer) {
        return ResultUtil.success(iProductArchiveService.getProducerList(producer));
    }

    @RequestMapping(value = "/getSatelliteList")
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result getSatelliteList() {
        return ResultUtil.success(iProductArchiveService.getSatelliteList());
    }

    @RequestMapping(value = "/getSensorList")
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result getSensorList() {
        return ResultUtil.success(iProductArchiveService.getSensorList());
    }

//    @RequestMapping(value = "/readShpTest")
//    @CrossOrigin(methods = RequestMethod.GET)
//    @ResponseBody
//    public Result readShpTest() {
//        String path = "C:\\Users\\37753\\Desktop\\na\\农安.shp";
//        List<JSONObject> jsonObjects = layerPublishService.getAdvancedProductShpInfo(path);
//        System.out.println("信息为：" + jsonObjects);
////        String path1 = "D:\\样例数据-高级产品管理系统\\镶嵌产品\\MP_吉林_长春_白天";
////        System.out.println("路径是：" + layerPublishService.getShpFilePath(path1));
//        return ResultUtil.success(jsonObjects);
//    }
//
//    @RequestMapping(value = "/test20190404")//给伯文的测试
//    @CrossOrigin(methods = RequestMethod.GET)
//    @ResponseBody
//    public Result test20190404()
//    {
//        List<JSONObject> jsonObjects = layerPublishService.getAdvancedProductShpInfo("C:\\Users\\37753\\Desktop\\宇航shp\\wcj_rubbish\\rubbishcutline.shp");
//
//        for(int i =0; i<jsonObjects.size();i++)
//        {
//            System.out.println(jsonObjects.get(i).toString());
//        }
//        return ResultUtil.success("");
//    }


}
