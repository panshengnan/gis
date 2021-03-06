package com.cgwx.service.impl;

import com.cgwx.aop.result.ResultUtil;
import com.cgwx.dao.*;
import com.cgwx.data.dto.*;
import com.cgwx.data.entity.*;
import com.cgwx.service.IClientProductService;
import com.cgwx.service.IMetadataService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class MetadataServiceImpl implements IMetadataService {
    @Autowired
    GisThemeticProductInfoMapper pdmThemeticProductInfoMapper;

    @Autowired
    GisThemeticProductDetailInfoMapper pdmThemeticProductDetailInfoMapper;
    @Autowired
    GisThemeticProductDetailIndustryInfoMapper pdmThemeticProductDetailIndustryInfoMapper;

    @Autowired
    GisOrthoProductInfoMapper pdmOrthoProductInfoMapper;

    @Autowired
    GisProductInfoMapper pdmProductInfoMapper;

    @Autowired
    GisInlayProductInfoMapper pdmInlayProductInfoMapper;

    @Autowired
    GisSubdivisionProductInfoMapper pdmSubdivisionProductInfoMapper;

    @Autowired
    GisProductTypeInfoMapper pdmProductTypeInfoMapper;

    @Autowired
    IProductDownloadServiceImpl iProductDownloadService;

    @Autowired
    GisProductStoreLinkInfoMapper pdmProductStoreLinkInfoMapper;

    @Autowired
    GisAdvancedProductShpInfoMapper pdmAdvancedProductShpInfoMapper;

    @Autowired
    GisProductLayerInfoMapper pdmProductLayerInfoMapper;

    @Autowired
    GisStandardProductInfoMapper gisStandardProductInfoMapper;

    @Autowired
    IClientProductService iClientProductService;

    @Autowired
    GisProvinceMapInfoMapper gisProvinceMapInfoMapper;

    @Autowired
    GisClientFileMapper gisClientFileMapper;

    @Value("${productStoreLinkHead}")
    private String productStoreLinkHead;//拼链接

    @Override
    //単期产品详情
    public SinglePeriodThemeticProductDetail getSinglePeriodThemeticProductDetail(String productId, String singlePeriodProductId) {  //方法在userRequirementService中定义
        GisThemeticProductDetailInfo themeticProductDetailPart2 = pdmThemeticProductDetailInfoMapper.selectThemeticProductDetailByProductIdPart2(singlePeriodProductId, productId);
        GisThemeticProductInfo themeticProductDetailPart1 = pdmThemeticProductInfoMapper.selectThemeticProductDetailPart1ByProductId(productId);

        SinglePeriodThemeticProductDetail singlePeriodThemeticProductDetail = new SinglePeriodThemeticProductDetail();
        singlePeriodThemeticProductDetail.setSinglePeriodProductId(singlePeriodProductId);
        //System.err.print(" "+themeticProductDetailPart2.getProducer());
        if (themeticProductDetailPart2.getImageGeo() == null) {
            singlePeriodThemeticProductDetail.setImageGeo(null);
        } else {
            singlePeriodThemeticProductDetail.setImageGeo(themeticProductDetailPart2.getImageGeo().toString());
        }

        singlePeriodThemeticProductDetail.setProducer(themeticProductDetailPart2.getProducer());
        singlePeriodThemeticProductDetail.setSatellite(themeticProductDetailPart2.getSatellite());
        singlePeriodThemeticProductDetail.setSensor(themeticProductDetailPart2.getSensor());
        singlePeriodThemeticProductDetail.setSinglePeriodProductName(themeticProductDetailPart2.getSinglePeriodProductName());
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (themeticProductDetailPart2.getImagingTime() == null) {
            singlePeriodThemeticProductDetail.setCenterImagingTime(null);
        } else {
            String setImagingtime = timeFormat.format(themeticProductDetailPart2.getImagingTime());
            singlePeriodThemeticProductDetail.setCenterImagingTime(setImagingtime);
        }

        if (themeticProductDetailPart2.getProduceTime() == null) {
            singlePeriodThemeticProductDetail.setProduceTime(null);
        } else {
            String setProducetime = timeFormat.format(themeticProductDetailPart2.getProduceTime());
            singlePeriodThemeticProductDetail.setProduceTime(setProducetime);
        }
        //singlePeriodThemeticProductDetail.setProduceTime(timeFormat.format(themeticProductDetailPart2.getProduceTime()));
        singlePeriodThemeticProductDetail.setSizeOfTif(themeticProductDetailPart2.getSizeOfTif());

        //String path =themeticProductDetailPart1.getParentDirectory();//ss0810

        //获取文件列表和对应的URL
//        List<FileUrl> themeticUrlList = getFileListAndUrl(productId,singlePeriodProductId);
//        singlePeriodThemeticProductDetail.setLayerName(pdmProductLayerInfoMapper.getThemeticProductLayerName(productId,singlePeriodProductId));
//        for(int a=themeticUrlList.size()-1;a>=0;a--) {
//            if(themeticUrlList.get(a).getFileName().contains("jpg"))
//            {
//                //System.out.println("removejpg"+themeticUrlList.get(a).getFileName());
//                singlePeriodThemeticProductDetail.setThumbnailUrl(productStoreLinkHead+themeticUrlList.get(a).getFileUrl());
//                //themeticUrlList.remove(a);
//            }
//            else
//            {
//                themeticUrlList.get(a).setFileUrl(productStoreLinkHead+themeticUrlList.get(a).getFileUrl());
//            }
//
//        }
//        singlePeriodThemeticProductDetail.setFileListAndUrl(themeticUrlList);

        return singlePeriodThemeticProductDetail;
    }

    @Override
    //获取文件列表和URL
    public List<FileUrl> getFileListAndUrl(String productId, String singlePeriodProductId) {
        List<GisProductStoreLinkInfo> productLinkList = iProductDownloadService.getProductLinkList(productId, singlePeriodProductId);

        List<FileUrl> urlList = new ArrayList<>();
        for (int a = 0; a < productLinkList.size(); a++) {
            FileUrl fileUrl = new FileUrl();
            fileUrl.setFileName(productLinkList.get(a).getFileName());
            fileUrl.setFileUrl(productLinkList.get(a).getStoreLink());
            urlList.add(fileUrl);
        }
        return urlList;
    }

    @Override
    //专题产品详情
    public ThemeticProductDetail getThemeticProductDetail(String productId, List<String> singlePeriodProductIdList) {
        // System.err.println("id"+productId);
        ThemeticProductDetail themeticProductDetail = new ThemeticProductDetail();
        GisProductInfo themeticProductDetailPart1 = pdmProductInfoMapper.selectProductDetailPart1ByProductId(productId);
        if (themeticProductDetailPart1 == null) {
            System.out.println("no product");
            return themeticProductDetail;
        }
        themeticProductDetail.setProductId(themeticProductDetailPart1.getProductId());
        themeticProductDetail.setThemeticProductName(themeticProductDetailPart1.getProductName());
        //themeticProductDetail.setIndustry(themeticProductDetailPart1.getIndustry());
        // System.err.println(themeticProductDetailPart1.getProductId()+" -"+themeticProductDetailPart1.getThemeticProductName()+" -"+themeticProductDetailPart1.getIndustry());
        themeticProductDetail.setCountOfPeriods(pdmThemeticProductDetailInfoMapper.countSinglePeriodThemeticProductId(productId).toString());
        // System.err.println("peri"+themeticProductDetail.getCountOfPeriods());
        themeticProductDetail.setProductDescription(pdmProductInfoMapper.selectProductDescriptionByProductId(productId));

        themeticProductDetail.setClientName(themeticProductDetailPart1.getClientName());
        themeticProductDetail.setDeliverName(themeticProductDetailPart1.getDeliverName());
        themeticProductDetail.setDeliverTime(themeticProductDetailPart1.getDeliverTime());
        themeticProductDetail.setIndustry(getIndustryByProductid(productId));
        //获取分析报告路径
        //String parentDirect=iProductDownloadService.getEntityFilePath(productId);
        // String path1="C:\\pdm_bak\\专题产品\\长春市201309热岛效应\\长春市201309热岛效应.pdf";
        themeticProductDetail.setAnalysisReportUrl(productStoreLinkHead + pdmProductStoreLinkInfoMapper.selectProductAnalysisReporturl(productId));
        String docpath = pdmProductStoreLinkInfoMapper.selectProductDocAnalysisReporturl(productId);
        //System.out.println("docpath:"+docpath);
        if (docpath == null) {
            themeticProductDetail.setDocAnalysisReportUrl(null);
        } else {
            themeticProductDetail.setDocAnalysisReportUrl(productStoreLinkHead + docpath);
        }
        //获取全部文件URL
        // String path4 ="C:\\pdm_bak\\专题产品\\长春市201309热岛效应";
        themeticProductDetail.setAllFileDownloadUrl(productStoreLinkHead + pdmProductStoreLinkInfoMapper.selectProductAllfileDownloadurl(productId));
        Integer size = pdmThemeticProductDetailInfoMapper.countSinglePeriodThemeticProductId(productId);
        // System.err.println("size"+size);
        List<SinglePeriodThemeticProductDetail> list = new ArrayList<>();
        for (int count = 0; count < size; count++) {
            list.add(getSinglePeriodThemeticProductDetail(productId, singlePeriodProductIdList.get(count)));
            //list.get(count).setLegendUrl(pdmProductLayerInfoMapper.getLayerLegend(productId,singlePeriodProductIdList.get(count)));
            list.get(count).setIsShp(pdmProductLayerInfoMapper.getIsShp(productId, singlePeriodProductIdList.get(count)));

            System.out.println(pdmProductStoreLinkInfoMapper.selectProductthumbnailUrl(productId, singlePeriodProductIdList.get(count)));
            list.get(count).setThumbnailUrl(productStoreLinkHead + pdmProductStoreLinkInfoMapper.selectProductthumbnailUrl(productId, singlePeriodProductIdList.get(count)));//productStoreLinkHead+pdmProductStoreLinkInfoMapper.selectProductthumbnailUrl(productId)
            list.get(count).setLegendUrl(productStoreLinkHead + pdmProductStoreLinkInfoMapper.selectProductLegendUrl(productId, singlePeriodProductIdList.get(count)));
            list.get(count).setLayerName(pdmProductLayerInfoMapper.getLayerName(productId, singlePeriodProductIdList.get(count)));
        }
        themeticProductDetail.setSinglePeriodThemeticProductDetail(list);


        return themeticProductDetail;
    }

    @Override
    //正射产品详情
    public OrthoProductDetail getOrthoProductDetail(String productId) {
        //      OrthoProductDetail pdmOrthoProductInfo = pdmOrthoProductInfoMapper.selectOrthoProductDetailByProductId(productId);
//       OrthoProductDetail orthoProductDetail=new OrthoProductDetail();
//       orthoProductDetail.setProductId(pdmOrthoProductInfo.getProductId());
//       orthoProductDetail.setProductName(pdmOrthoProductInfo.getProductName());
//       if(pdmOrthoProductInfo.getImageGeo()!=null)
//       {
//           orthoProductDetail.setImageGeo(pdmOrthoProductInfo.getImageGeo().toString());
//       }
//       else
//       {
//           orthoProductDetail.setImageGeo(null);
//       }
//        orthoProductDetail.setBands(pdmOrthoProductInfo.getBands());
//        orthoProductDetail.setReceiveStation(pdmOrthoProductInfo.getReceiveStation());
//        orthoProductDetail.setReceiveTime(pdmOrthoProductInfo.getReceiveTime());
//        orthoProductDetail.setSwingSatelliteAngle(pdmOrthoProductInfo.getSwingSatelliteAngle());
//        //if(pdmOrthoProductInfo.getCloudPercent()!=null)
//       // {
//            //orthoProductDetail.setCloudPercent(null);
//        orthoProductDetail.setCloudPercent(pdmOrthoProductInfo.getCloudPercent());
//       // }
//
//
//        orthoProductDetail.setCenterLatitude(pdmOrthoProductInfo.getCenterLatitude());
//        orthoProductDetail.setCenterLongitude(pdmOrthoProductInfo.getCenterLongitude());
//        orthoProductDetail.setCenterTime(pdmOrthoProductInfo.getCenterTime());
//        orthoProductDetail.setWidthInMeters(pdmOrthoProductInfo.getWidthInMeters());
//        orthoProductDetail.setHeightInMeters(pdmOrthoProductInfo.getHeightInMeters());
//        orthoProductDetail.setProductQuality(pdmOrthoProductInfo.getProductQuality());
//       orthoProductDetail.setProducer(pdmOrthoProductInfo.getProducer());
//       orthoProductDetail.setGeographicInfo(pdmOrthoProductInfo.getGeographicInfo());
//       orthoProductDetail.setSatellite(pdmOrthoProductInfo.getSatellite());
//       orthoProductDetail.setSensor(pdmOrthoProductInfo.getSensor());

        OrthoProductDetail orthoProductDetail = pdmOrthoProductInfoMapper.selectOrthoProductDetailByProductId(productId);

        AdvanceProductPart1Info productInfo = pdmProductInfoMapper.selectAdvanceProductPart1ByProductId(productId);

//        orthoProductDetail.setDelieverName(productInfo.getDeliverName());
//        orthoProductDetail.setDelieverTime(productInfo.getDeliverTime());
//        orthoProductDetail.setDeliverMethod(productInfo.getDeliverMethod());
//        orthoProductDetail.setProduceArea(productInfo.getProcucerArea());
//        orthoProductDetail.setProduceTime(productInfo.getProduceTime());
        orthoProductDetail.setLayerName(pdmProductLayerInfoMapper.getAdvanceProductLayerName(productId));
//        orthoProductDetail.setProductType("ortho");

        //获取路径，后续需要改
        List<FileUrl> orthoUrlList = getFileListAndUrl(productId, null);
        int fileUrlSize = orthoUrlList.size();
        for (int a = fileUrlSize - 1; a >= 0; a--) {
//            System.out.println(  "size:"+orthoUrlList.size());
            // System.out.println(  "url:"+orthoUrlList.get(a).getFileUrl());

            orthoUrlList.get(a).setFileUrl(productStoreLinkHead + orthoUrlList.get(a).getFileUrl());
            if (orthoUrlList.get(a).getFileName().contains("thumb.jpg")) {
                System.out.println("removejpg" + orthoUrlList.get(a).getFileName());
                orthoProductDetail.setThumbnailUrl(orthoUrlList.get(a).getFileUrl());
                //orthoUrlList.remove(a);

            } else if (orthoUrlList.get(a).getFileName().contains("zip")) {
                //System.out.println("removezip"+orthoUrlList.get(a).getFileName());
                orthoProductDetail.setAllFileDownloadUrl(orthoUrlList.get(a).getFileUrl());
                orthoUrlList.remove(a);
            }
        }
        orthoProductDetail.setFileListAndUrl(orthoUrlList);
        return orthoProductDetail;
    }

    //镶嵌产品
    @Override
    public InlayProductDetail getInlayProductDetail(String productId) {

        InlayProductDetail inlayProductDetail = new InlayProductDetail();
        GisInlayProductInfo pdmInlayProductInfo = pdmInlayProductInfoMapper.selectInlayProductDetailByProductId(productId);
        AdvanceProductPart1Info productInfo = pdmProductInfoMapper.selectAdvanceProductPart1ByProductId(productId);
        inlayProductDetail.setProductId(pdmInlayProductInfo.getProductId());
        inlayProductDetail.setProductName(pdmInlayProductInfo.getInlayProductName());
        if (pdmInlayProductInfo.getImageGeo() != null) {

            inlayProductDetail.setImageGeo(pdmInlayProductInfo.getImageGeo().toString());
        } else {
            //System.out.println("igeo-null"+pdmInlayProductInfo.getImageGeo());
            inlayProductDetail.setImageGeo(null);
        }
        inlayProductDetail.setProducer(pdmInlayProductInfo.getProducer());
        inlayProductDetail.setGeographicInfo(pdmInlayProductInfo.getGeographicInfo());
        inlayProductDetail.setDeliverMethod(productInfo.getDeliverMethod());
        inlayProductDetail.setProduceArea(productInfo.getProcucerArea());
        inlayProductDetail.setProduceTime(productInfo.getProduceTime());
        inlayProductDetail.setDelieverName(productInfo.getDeliverName());
        inlayProductDetail.setDelieverTime(productInfo.getDeliverTime());
        inlayProductDetail.setProductType(pdmInlayProductInfo.getInlayType());
        inlayProductDetail.setShpInfoList(pdmAdvancedProductShpInfoMapper.getshpInfoList(productId));
        inlayProductDetail.setLayerName(pdmProductLayerInfoMapper.getAdvanceProductLayerName(productId));
        //String path =pdmInlayProductInfo.getInlayProductDirectory();
        //获取分析报告路径，后续需要改
        List<FileUrl> inlayUrlList = getFileListAndUrl(productId, null);
        int fileUrlSize = inlayUrlList.size();
        for (int a = fileUrlSize - 1; a >= 0; a--) {
//           System.out.println(  "size:"+inlayUrlList.size());
            // System.out.println(  "url:"+inlayUrlList.get(a).getFileUrl());

            inlayUrlList.get(a).setFileUrl(productStoreLinkHead + inlayUrlList.get(a).getFileUrl());
            if (inlayUrlList.get(a).getFileName().contains("thumb.jpg")) {
                // System.out.println("removejpg"+inlayUrlList.get(a).getFileName());
                inlayProductDetail.setThumbnailUrl(inlayUrlList.get(a).getFileUrl());
                //inlayUrlList.remove(a);

            } else if (inlayUrlList.get(a).getFileName().contains(".zip")) {
                //System.out.println("removezip"+inlayUrlList.get(a).getFileName());
                inlayProductDetail.setAllFileDownloadUrl(inlayUrlList.get(a).getFileUrl());
                inlayUrlList.remove(a);
            }
        }

        inlayProductDetail.setFileListAndUrl(inlayUrlList);
        return inlayProductDetail;

    }

    //分幅产品
    @Override
    public SubdivisionProductDetail getSubdivisionProductDetail(String productId) {
        GisSubdivisionProductInfo pdmSubdivisionProductInfo = pdmSubdivisionProductInfoMapper.selectSubdivisionProductDetailByProductId(productId);
        AdvanceProductPart1Info productInfo = pdmProductInfoMapper.selectAdvanceProductPart1ByProductId(productId);
        SubdivisionProductDetail subdivisionProductDetail = new SubdivisionProductDetail();
        subdivisionProductDetail.setProductId(productId);
        subdivisionProductDetail.setProductName(pdmSubdivisionProductInfo.getSubdivisionProductName());
        if (pdmSubdivisionProductInfo.getImageGeo() != null) {
            // System.out.println("sgeo-null");
            subdivisionProductDetail.setImageGeo(pdmSubdivisionProductInfo.getImageGeo().toString());
        } else {
            // System.out.println("sgeo-null"+pdmSubdivisionProductInfo.getImageGeo());
            subdivisionProductDetail.setImageGeo(null);
        }
        // subdivisionProductDetail.setNumberOfTif(pdmSubdivisionProductInfo.getNumberOfTif().toString());
        // subdivisionProductDetail.setIndustry(pdmSubdivisionProductInfo.getIndustry());
        // PdmProductInfo productInfo=pdmProductInfoMapper.selectProductDetailPart1ByProductId(productId);
        subdivisionProductDetail.setProducer(pdmSubdivisionProductInfo.getProducer());
        subdivisionProductDetail.setDeliverMethod(productInfo.getDeliverMethod());
        subdivisionProductDetail.setProduceArea(productInfo.getProcucerArea());
        subdivisionProductDetail.setProduceTime(productInfo.getProduceTime());
        subdivisionProductDetail.setDelieverName(productInfo.getDeliverName());
        subdivisionProductDetail.setDelieverTime(productInfo.getDeliverTime());
        if (pdmSubdivisionProductInfo.getResolution() == null) {
            subdivisionProductDetail.setResolution(null);
        } else {
            subdivisionProductDetail.setResolution(pdmSubdivisionProductInfo.getResolution().toString());
        }

        subdivisionProductDetail.setGeographicInfo(pdmSubdivisionProductInfo.getGeographicInfo());
        subdivisionProductDetail.setProductType("subdivision");
        subdivisionProductDetail.setShpInfoList(pdmAdvancedProductShpInfoMapper.getshpInfoList(productId));
        subdivisionProductDetail.setLayerName(pdmProductLayerInfoMapper.getAdvanceProductLayerName(productId));
        String path = pdmSubdivisionProductInfo.getSubdivisionProductDirectory();
        //获取分析报告路径，后续需要改

        List<FileUrl> subdivisionUrlList = getFileListAndUrl(productId, null);
        int fileUrlSize = subdivisionUrlList.size();
        for (int a = fileUrlSize - 1; a >= 0; a--) {
            // System.out.println(  "size:"+subdivisionUrlList.size());
            //  System.out.println(  "url:"+subdivisionUrlList.get(a).getFileUrl());

            subdivisionUrlList.get(a).setFileUrl(productStoreLinkHead + subdivisionUrlList.get(a).getFileUrl());
            if (subdivisionUrlList.get(a).getFileName().contains("thumb.jpg")) {
                //  System.out.println("removejpg"+subdivisionUrlList.get(a).getFileName());
                subdivisionProductDetail.setThumbnailUrl(subdivisionUrlList.get(a).getFileUrl());
                //subdivisionUrlList.remove(a);

            } else if (subdivisionUrlList.get(a).getFileName().contains(".zip")) {
                //System.out.println("removezip"+subdivisionUrlList.get(a).getFileName());
                subdivisionProductDetail.setAllFileDownloadUrl(subdivisionUrlList.get(a).getFileUrl());
                subdivisionUrlList.remove(a);
            }
        }
        subdivisionProductDetail.setFileListAndUrl(subdivisionUrlList);
        return subdivisionProductDetail;

    }

    @Override
    public StandardProductDetail getStandardProductDetail(String productId) {

        StandardProductDetail standardProductDetail = gisStandardProductInfoMapper.selectStandardProductDetailByProductId(productId);
        return standardProductDetail;
    }

    //获取产品列表
    @Override
    public ProductQueryListResult getProductList(ProductQueryCri cri) {

        String productName = cri.getProductName();
        String productDescription = cri.getProductDescription();
        String orderby = cri.getOrderby();
        String type = cri.getProductType();
        List<ProductQueryList> list = new ArrayList<>();
        System.out.print("type" + cri.getProductType());
        if (type.equals("")) {
            List<ProductQueryList> listPart1 = pdmProductInfoMapper.selectThemeticProductByCondition(productName, productDescription, orderby);
            list.addAll(listPart1);
            List<ProductQueryList> listPart2 = pdmProductInfoMapper.selectOrthoProductByCondition(productName, productDescription, orderby);
            list.addAll(listPart2);
            List<ProductQueryList> listPart3 = pdmProductInfoMapper.selectInlayProductByCondition(productName, productDescription, orderby);
            list.addAll(listPart3);
            List<ProductQueryList> listPart4 = pdmProductInfoMapper.selectSubdivisionProductByCondition(productName, productDescription, orderby);
            list.addAll(listPart4);

        } else {
            if (type.equals("themetic")) {

                list = pdmProductInfoMapper.selectThemeticProductByCondition(productName, productDescription, orderby);

            } else if (type.equals("ortho")) {
                list = pdmProductInfoMapper.selectOrthoProductByCondition(productName, productDescription, orderby);

            } else if (type.equals("inlay")) {
                list = pdmProductInfoMapper.selectInlayProductByCondition(productName, productDescription, orderby);

            } else if (type.equals("subdivision")) {
                list = pdmProductInfoMapper.selectSubdivisionProductByCondition(productName, productDescription, orderby);

            } else {
                System.out.print("wrong product Type");
            }

        }

        PageInfo<ProductQueryList> pageInfo = new PageInfo<>(list);

        ProductQueryListResult productQueryListResult = new ProductQueryListResult();

        productQueryListResult.setTotalItems(pageInfo.getTotal());
        productQueryListResult.setTotalPageNum(pageInfo.getPages());
        productQueryListResult.setProductQueryList(list);
        return productQueryListResult;

    }

    public ThemeticProductListResult getThemeticProductList(ThemeticProductCri themeticProductCri) {

        String productName = themeticProductCri.getProductName();
        String orderby = themeticProductCri.getOrderby();
        String satellite = themeticProductCri.getSatellite();
        String industry = themeticProductCri.getIndustry();
        String sensor = themeticProductCri.getSensor();
        String clientName = themeticProductCri.getClientName();
        String delieverName = themeticProductCri.getDelieverName();
        List<ThemeticProductList> themeticProductDetailList = pdmThemeticProductInfoMapper.selectThemeticProductByCondition(productName, orderby, satellite, industry, sensor, clientName, delieverName);

        PageInfo<ThemeticProductList> pageInfo = new PageInfo<>(themeticProductDetailList);

        ThemeticProductListResult themeticProductListResult = new ThemeticProductListResult();

        themeticProductListResult.setTotalItems(pageInfo.getTotal());
        themeticProductListResult.setTotalPageNum(pageInfo.getPages());
        themeticProductListResult.setProductQueryList(themeticProductDetailList);
        return themeticProductListResult;

    }

    public OrthoProductListResult getOrthoProductList(OrthoProductCri orthoProductCri) {

        String productName = orthoProductCri.getProductName();
        String orderby = orthoProductCri.getOrderby();
        String satellite = orthoProductCri.getSatellite();
        String sensor = orthoProductCri.getSensor();
        String clientName = orthoProductCri.getClientName();
        String delieverName = orthoProductCri.getDelieverName();
        BigDecimal resolution = orthoProductCri.getResolution();
        String imageBreath = orthoProductCri.getImageBreath();
        List<OrthoProductList> orthoProductList = pdmOrthoProductInfoMapper.selectOrthoProductByCondition(productName, orderby, satellite, sensor, clientName, delieverName, resolution, imageBreath);

        PageInfo<OrthoProductList> pageInfo = new PageInfo<>(orthoProductList);

        OrthoProductListResult orthoProductListResult = new OrthoProductListResult();

        orthoProductListResult.setTotalItems(pageInfo.getTotal());
        orthoProductListResult.setTotalPageNum(pageInfo.getPages());
        orthoProductListResult.setProductQueryList(orthoProductList);
        return orthoProductListResult;
    }

    public InlayProductListResult getInlayProductList(InlayProductCri inlayProductCri) {

        String productName = inlayProductCri.getProductName();
        String orderby = inlayProductCri.getOrderby();
        String clientName = inlayProductCri.getClientName();
        String delieverName = inlayProductCri.getDelieverName();
        List<InlayProductList> inlayProductList = pdmInlayProductInfoMapper.selectInlayProductByCondition(productName, orderby, clientName, delieverName);

        PageInfo<InlayProductList> pageInfo = new PageInfo<>(inlayProductList);

        InlayProductListResult inlayProductListResult = new InlayProductListResult();

        inlayProductListResult.setTotalItems(pageInfo.getTotal());
        inlayProductListResult.setTotalPageNum(pageInfo.getPages());
        inlayProductListResult.setProductQueryList(inlayProductList);
        return inlayProductListResult;
    }

    public SubdivisionProductListResult getSubdivisionProductList(SubdivisionProductCri subdivisionProductCri) {

        String productName = subdivisionProductCri.getProductName();
        String orderby = subdivisionProductCri.getOrderby();
        String clientName = subdivisionProductCri.getClientName();
        String delieverName = subdivisionProductCri.getDelieverName();
        BigDecimal resolution = subdivisionProductCri.getResolution();
        String industry = subdivisionProductCri.getIndustry();

        List<SubdivisionProductList> subdivisionProductList = pdmSubdivisionProductInfoMapper.selectSubdivisionProductByCondition(productName, orderby, clientName, delieverName, resolution, industry);

        PageInfo<SubdivisionProductList> pageInfo = new PageInfo<>(subdivisionProductList);

        SubdivisionProductListResult subdivisionProductListResult = new SubdivisionProductListResult();

        subdivisionProductListResult.setTotalItems(pageInfo.getTotal());
        subdivisionProductListResult.setTotalPageNum(pageInfo.getPages());
        subdivisionProductListResult.setProductQueryList(subdivisionProductList);
        return subdivisionProductListResult;
    }

    @Override
    public List<ThemeticProductSimpleInfo> getThemeticSimpleProductlist(Object geo, String producer) {
        List<ThemeticProductSimpleInfo> themeticProductSimpleInfoList = new ArrayList<ThemeticProductSimpleInfo>();
        themeticProductSimpleInfoList = pdmThemeticProductDetailInfoMapper.selectSimpleinfoByProducerandGeo(producer, geo);
        // System.out.println(themeticProductSimpleInfoList.get(0).getImageGeo());
        return themeticProductSimpleInfoList;
    }

    @Override
    public List<String> getProductIdlist(String clientname, String description) {
        List<String> productIdlist = new ArrayList<String>();
        //System.out.println(clientname+" "+description+" ++");
        productIdlist = pdmProductInfoMapper.getProductIdlistByclientanddescription(clientname, description);
        return productIdlist;
    }

    @Override
    public List<String> getProductIdlistFromIndustry(int level1, int level2) {
        List<String> productIdList = new ArrayList<String>();
        productIdList = pdmThemeticProductDetailIndustryInfoMapper.selectThemeticidByIndustry(level1, level2);
        return productIdList;
    }


    @Override
    public List<ThemeticProductListByGeos> getThemeticProductListByConditions(String clientname, String description, Object geo, String producer, List<Industry> industryList) {
        List<ThemeticProductListByGeos> themeticProductListByGeosList = new ArrayList<ThemeticProductListByGeos>();
        String where = "";
        List<String> productIdlistFromIndustry = new ArrayList<String>();
        if (industryList == null) {
            where = "1=1";
        } else {
            for (int i = 0; i < industryList.size(); i++) {
                if (industryList.get(i).getLevel2() != 10000) {
                    where = where + " or (industry_level1=" + industryList.get(i).getLevel1() + " and industry_level2=" + industryList.get(i).getLevel2() + ")";
                } else {
                    where = where + " or (industry_level1=" + industryList.get(i).getLevel1() + ")";
                }
            }
            where = where.substring(4, where.length());
            System.out.println("where is" + where);
        }
        // List<String> industrytype=new ArrayList<String>();

        themeticProductListByGeosList = pdmProductInfoMapper.getThemeticInfoByConditions(clientname, description, where, geo, producer);
        return themeticProductListByGeosList;
    }

    @Override
    public void mergeThemeticSimpleInfoListByProductIdlist(List<ThemeticProductSimpleInfo> themeticProductSimpleInfoList, List<String> productIdlist) {
        if (productIdlist == null) {
            // System.out.println("productidnull");
            for (int i = themeticProductSimpleInfoList.size() - 1; i >= 0; i--) {
                themeticProductSimpleInfoList.remove(i);
            }

            return;
        }
        if (themeticProductSimpleInfoList.isEmpty()) {
            return;
        }
        List<Integer> forremove = new ArrayList<Integer>();                //记录需要移除的产品
        for (int i = 0; i < themeticProductSimpleInfoList.size(); i++) {
            //System.out.println("judge:"+themeticProductSimpleInfoList1.get(i).getProductId()+"I=:"+i);
            boolean flag = false;
            for (int j = 0; j < productIdlist.size(); j++) {
                if (themeticProductSimpleInfoList.get(i).getProductId().equals(productIdlist.get(j))) {
                    flag = true;
                    break;
                }
            }
            if (flag == false) {
                forremove.add(i);

            }
        }
        //System.out.println(forremove);
        for (int i = forremove.size() - 1; i >= 0; i--) {
            int rem = forremove.get(i);
            themeticProductSimpleInfoList.remove(rem);
        }
    }

    @Override
    public void printThemeticSimpleInfoList(List<ThemeticProductSimpleInfo> themeticProductSimpleInfoList) {
        for (int i = 0; i < themeticProductSimpleInfoList.size(); i++) {
            System.out.print(themeticProductSimpleInfoList.get(i).getSinglePeriodId() + " " + themeticProductSimpleInfoList.get(i).getProductId() + " ");
        }
        System.out.println();
    }

    @Override
    public List<String> getProductIdlistByIndustryList(List<Industry> industryList) {
        List<String> productIdlistFromIndustry = new ArrayList<String>();
        if (industryList == null) {
            List<String> stringListtemp = getProductIdlistFromIndustry(10000, 10000);
            return stringListtemp;
        }
        String where = "";
        // List<String> industrytype=new ArrayList<String>();
        for (int i = 0; i < industryList.size(); i++) {
            if (industryList.get(i).getLevel2() != 10000) {
                where = where + " or (industry_level1=" + industryList.get(i).getLevel1() + " and industry_level2=" + industryList.get(i).getLevel2() + ")";
            } else {
                where = where + " or (industry_level1=" + industryList.get(i).getLevel1() + ")";
            }
        }
        where = where.substring(4, where.length());
        System.out.println("where is" + where);
        productIdlistFromIndustry = pdmThemeticProductDetailIndustryInfoMapper.selectThemeticidByIndustrylist(where);
        // System.out.println("idlist is"+productIdlistFromIndustry);
        productIdlistFromIndustry = removeRepeat(productIdlistFromIndustry);
        //System.out.println("idlist is"+productIdlistFromIndustry);
//        for(int i=0;i<industryList.size();i++)
//        {
//            List<String> stringListtemp=getProductIdlistFromIndustry(industryList.get(i).getLevel1(),industryList.get(i).getLevel2());
//           // System.out.println("string"+stringListtemp);
//            if(stringListtemp.isEmpty())
//            {
//                return null;
//            }
//            if (productIdlistFromIndustry.size()==0)
//            {
//                productIdlistFromIndustry=stringListtemp;
//            }
//            else
//            {
//                for(int j=productIdlistFromIndustry.size()-1;j>=0;j--)
//                {
//                    boolean flag=false;
//                    for(int k=0;k<stringListtemp.size();k++)
//                    {
//                        if(productIdlistFromIndustry.get(j).equals(stringListtemp.get(k)))
//                        {
//                            flag=true;
//                            break;
//                        }
//                    }
//                    if(flag==false)
//                    {
//                        productIdlistFromIndustry.remove(j);
//                    }
//                }
//            }
//        }
        return productIdlistFromIndustry;
    }

    @Override
    public List<ThemeticProductListByGeos> packetSingleThemeticProductToThemeticProduct(List<ThemeticProductSimpleInfo> themeticProductSimpleInfoList) {

        List<ThemeticProductListByGeos> themeticProductListByGeosResultList = new ArrayList<ThemeticProductListByGeos>();
        if (themeticProductSimpleInfoList == null || themeticProductSimpleInfoList.isEmpty()) {
            return null;
        }
        while (themeticProductSimpleInfoList.isEmpty() == false) {
            ThemeticProductListByGeos themeticProductListByGeosResulttemp = new ThemeticProductListByGeos();
            List<ThemeticProductSimpleInfo> themeticProductListtemp = new ArrayList<ThemeticProductSimpleInfo>();
            String themeticProductIdtemp = themeticProductSimpleInfoList.get(0).getProductId();
            for (int i = themeticProductSimpleInfoList.size() - 1; i >= 0; i--) {
                if (themeticProductSimpleInfoList.get(i).getProductId().equals(themeticProductIdtemp)) {
                    themeticProductListtemp.add(themeticProductSimpleInfoList.get(i));
                    themeticProductSimpleInfoList.remove(i);

                }

            }
            themeticProductListByGeosResulttemp.setThemeticProductSimpleInfoList(themeticProductListtemp);
            themeticProductListByGeosResulttemp.setProductId(themeticProductIdtemp);
            themeticProductListByGeosResulttemp.setIndustryList(getIndustryByProductid(themeticProductIdtemp));
            themeticProductListByGeosResulttemp.setProductName(pdmProductInfoMapper.getProductNameById(themeticProductIdtemp));
            themeticProductListByGeosResultList.add(themeticProductListByGeosResulttemp);

        }

        return themeticProductListByGeosResultList;
    }

    @Override
    public List<Industry> getIndustryByProductid(String productId) {
        List<Industry> industryList = new ArrayList<Industry>();
        industryList = pdmThemeticProductDetailIndustryInfoMapper.selectIndustryByProductid(productId);
//        for(int i=0;i<industryList.size();i++)
//        {
//            System.out.println(industryList.get(i).getLevel1()+"  "+industryList.get(i).getLevel2());
//        }
        return industryList;
    }

    @Override
    public List<AdvanceProductSimpleInfo> getAdvanceProductSimpleInfoList(AdvanceProductCri advanceProductCri) {

        List<AdvanceProductSimpleInfo> advanceProductSimpleInfoList = new ArrayList<AdvanceProductSimpleInfo>();
        List<AdvanceProductSimpleInfo> advanceProductSimpleInfoListtemp1 = new ArrayList<AdvanceProductSimpleInfo>();
        List<AdvanceProductSimpleInfo> advanceProductSimpleInfoListtemp2 = new ArrayList<AdvanceProductSimpleInfo>();

        if (advanceProductCri.isOrtho()) {
            advanceProductSimpleInfoList = pdmOrthoProductInfoMapper.selectSimpleinfoByconditions(advanceProductCri.getProducer(), advanceProductCri.getImage_geo(), advanceProductCri.getDeliverName(), advanceProductCri.getProduceArea(), advanceProductCri.getDeliverMethod(), advanceProductCri.getProduceStartTime(), advanceProductCri.getProduceEndTime(), advanceProductCri.getDeliverStartTime(), advanceProductCri.getDeliverEndTime(), advanceProductCri.getProductName());
            for (int i = 0; i < advanceProductSimpleInfoList.size(); i++) {
                advanceProductSimpleInfoList.get(i).setProductType("正射产品");
            }
            //System.out.println(advanceProductSimpleInfoList);
        }
        if (advanceProductCri.isInlay()) {
            advanceProductSimpleInfoListtemp1 = pdmInlayProductInfoMapper.selectSimpleinfoByconditions(advanceProductCri.getProducer(), advanceProductCri.getImage_geo(), advanceProductCri.getDeliverName(), advanceProductCri.getProduceArea(), advanceProductCri.getDeliverMethod(), advanceProductCri.getProduceStartTime(), advanceProductCri.getProduceEndTime(), advanceProductCri.getDeliverStartTime(), advanceProductCri.getDeliverEndTime(), advanceProductCri.getProductName());
            for (int i = 0; i < advanceProductSimpleInfoListtemp1.size(); i++) {
                advanceProductSimpleInfoListtemp1.get(i).setProductType("镶嵌产品");
                advanceProductSimpleInfoList.add(advanceProductSimpleInfoListtemp1.get(i));
            }
        }
        if (advanceProductCri.isSubdivision()) {
            advanceProductSimpleInfoListtemp2 = pdmSubdivisionProductInfoMapper.selectSimpleinfoByconditions(advanceProductCri.getProducer(), advanceProductCri.getImage_geo(), advanceProductCri.getDeliverName(), advanceProductCri.getProduceArea(), advanceProductCri.getDeliverMethod(), advanceProductCri.getProduceStartTime(), advanceProductCri.getProduceEndTime(), advanceProductCri.getDeliverStartTime(), advanceProductCri.getDeliverEndTime(), advanceProductCri.getProductName());
            for (int i = 0; i < advanceProductSimpleInfoListtemp2.size(); i++) {
                advanceProductSimpleInfoListtemp2.get(i).setProductType("分幅产品");
                advanceProductSimpleInfoList.add(advanceProductSimpleInfoListtemp2.get(i));
            }
        }

        for (int i = 0; i < advanceProductSimpleInfoList.size(); i++) {
            //System.out.println("id:"+advanceProductSimpleInfoList.get(i).getProductId());
            List<String> urls = pdmProductStoreLinkInfoMapper.selectProductthumbnailUrlurl(advanceProductSimpleInfoList.get(i).getProductId());
            //System.out.println("urllist:"+urls);
            if (urls.isEmpty()) {
                advanceProductSimpleInfoList.get(i).setThumbUrl(productStoreLinkHead + "null");
                advanceProductSimpleInfoList.get(i).setThumbnailUrl(productStoreLinkHead + "null");
            } else if (urls.size() == 2) {
                if (urls.get(0).contains("thumb.jpg")) {
                    advanceProductSimpleInfoList.get(i).setThumbUrl(productStoreLinkHead + urls.get(0));
                    advanceProductSimpleInfoList.get(i).setThumbnailUrl(productStoreLinkHead + urls.get(1));
                } else {
                    advanceProductSimpleInfoList.get(i).setThumbUrl(productStoreLinkHead + urls.get(1));
                    advanceProductSimpleInfoList.get(i).setThumbnailUrl(productStoreLinkHead + urls.get(0));
                }
            } else if (urls.size() == 1) {
                advanceProductSimpleInfoList.get(i).setThumbUrl(productStoreLinkHead + urls.get(0));
                advanceProductSimpleInfoList.get(i).setThumbnailUrl(productStoreLinkHead + urls.get(0));
            }

            advanceProductSimpleInfoList.get(i).setDownloadurl(productStoreLinkHead + pdmProductStoreLinkInfoMapper.selectProductAllfileDownloadurl(advanceProductSimpleInfoList.get(i).getProductId()));
        }

        return advanceProductSimpleInfoList;
    }

    @Override
    public AdvanceProductSimpleInfoResult getAdvanceProductSimpleInfoListByConditions(AdvanceProductCri advanceProductCri) {
        List<AdvanceProductSimpleInfo> advanceProductSimpleInfoList = new ArrayList<AdvanceProductSimpleInfo>();
        PageHelper.startPage(advanceProductCri.getCurPageNum(), advanceProductCri.getMaxResultNum());
        System.out.println(advanceProductCri.getImage_geo());
        advanceProductSimpleInfoList = pdmOrthoProductInfoMapper.selectSimpleinfoByAllconditions(advanceProductCri.getProducer(), advanceProductCri.getImage_geo(), advanceProductCri.getDeliverName(), advanceProductCri.getProduceArea(), advanceProductCri.getDeliverMethod(), advanceProductCri.getProduceStartTime(), advanceProductCri.getProduceEndTime(), advanceProductCri.getDeliverStartTime(), advanceProductCri.getDeliverEndTime(), advanceProductCri.getProductName(), advanceProductCri.isOrtho(), advanceProductCri.isInlay(), advanceProductCri.isSubdivision());
        for (int i = 0; i < advanceProductSimpleInfoList.size(); i++) {
            int productType = pdmProductInfoMapper.selectProductTypeByProductId(advanceProductSimpleInfoList.get(i).getProductId());
            //System.out.print(productType);
            String advancedProductType = pdmProductTypeInfoMapper.selectProductTypeDescriptionByProductType(productType);
            advanceProductSimpleInfoList.get(i).setProductType(advancedProductType);
            //System.out.println("id:"+advanceProductSimpleInfoList.get(i).getProductId());
            List<String> urls = pdmProductStoreLinkInfoMapper.selectProductthumbnailUrlurl(advanceProductSimpleInfoList.get(i).getProductId());
            //System.out.println("urllist:"+urls);
            if (urls.isEmpty()) {
                advanceProductSimpleInfoList.get(i).setThumbUrl(productStoreLinkHead + "null");
                advanceProductSimpleInfoList.get(i).setThumbnailUrl(productStoreLinkHead + "null");
            } else if (urls.size() == 2) {
                if (urls.get(0).contains("thumb.jpg")) {
                    advanceProductSimpleInfoList.get(i).setThumbUrl(productStoreLinkHead + urls.get(0));
                    advanceProductSimpleInfoList.get(i).setThumbnailUrl(productStoreLinkHead + urls.get(1));
                } else {
                    advanceProductSimpleInfoList.get(i).setThumbUrl(productStoreLinkHead + urls.get(1));
                    advanceProductSimpleInfoList.get(i).setThumbnailUrl(productStoreLinkHead + urls.get(0));
                }
            } else if (urls.size() == 1) {
                advanceProductSimpleInfoList.get(i).setThumbUrl(productStoreLinkHead + urls.get(0));
                advanceProductSimpleInfoList.get(i).setThumbnailUrl(productStoreLinkHead + urls.get(0));
            }

            advanceProductSimpleInfoList.get(i).setDownloadurl(productStoreLinkHead + pdmProductStoreLinkInfoMapper.selectProductAllfileDownloadurl(advanceProductSimpleInfoList.get(i).getProductId()));
        }

        PageInfo<AdvanceProductSimpleInfo> pageInfo = new PageInfo<>(advanceProductSimpleInfoList);
        System.out.println("总条目数：" + pageInfo.getTotal());
        System.out.println("总页数：" + pageInfo.getPages());
        AdvanceProductSimpleInfoResult advanceProductSimpleInfoResult = new AdvanceProductSimpleInfoResult();
        advanceProductSimpleInfoResult.setProductQueryList(advanceProductSimpleInfoList);
        advanceProductSimpleInfoResult.setTotalItems(pageInfo.getTotal());
        advanceProductSimpleInfoResult.setTotalPageNum(pageInfo.getPages());
        return advanceProductSimpleInfoResult;

    }


    public List<String> removeRepeat(List<String> stringList) {
        List<String> returnstring = new ArrayList<String>();
        while (stringList.isEmpty() == false) {
            String stringtemp = new String(stringList.get(0));
            for (int i = stringList.size() - 1; i >= 0; i--) {
                if (stringtemp.equals(stringList.get(i))) {
                    stringList.remove(i);
                }
            }
            returnstring.add(stringtemp);
        }
        return returnstring;
    }

    @Override
    public List<String> getDeliverMethodList() {
        List<String> deliverMethodList = pdmProductInfoMapper.getDeliverMethodList();
        return deliverMethodList;
    }

    @Override
    public List<String> getProduceAreaList() {
        List<String> produceAreaList = pdmProductInfoMapper.getProduceAreaList();
        return produceAreaList;
    }

    @Override
    public List<ExampleData> getExampleDataList(List<String> productIdList) {

        List<ExampleData> exampleDataList = new ArrayList<>();
        System.out.println(productIdList);
        if (productIdList != null) {
            for (int i = 0; i < productIdList.size(); i++) {
                int productType = pdmProductInfoMapper.selectProductTypeByProductId(productIdList.get(i));
                String TypeName = pdmProductTypeInfoMapper.selectProductTypeDescriptionByProductType(productType);
                ExampleData exampleDatatemp = new ExampleData();
                exampleDatatemp.setProductId(productIdList.get(i));
                switch (TypeName) {
                    case "正射产品":
                        OrthoProductDetail orthoProductDetail = getOrthoProductDetail(productIdList.get(i));
                        exampleDatatemp.setProductName(orthoProductDetail.getProductName());
                        exampleDatatemp.setThumbnailUrl(orthoProductDetail.getThumbnailUrl());

                    case "镶嵌产品":
                        InlayProductDetail inlayProductDetail = getInlayProductDetail(productIdList.get(i));
                        exampleDatatemp.setProductName(inlayProductDetail.getProductName());
                        exampleDatatemp.setThumbnailUrl(inlayProductDetail.getThumbnailUrl());
                    case "分幅产品":
                        SubdivisionProductDetail subdivisionProductDetail = getSubdivisionProductDetail(productIdList.get(i));
                        exampleDatatemp.setProductName(subdivisionProductDetail.getProductName());
                        exampleDatatemp.setThumbnailUrl(subdivisionProductDetail.getThumbnailUrl());

                    case "专题产品":
                        List<String> singlePeriodProductIdList = pdmThemeticProductDetailInfoMapper.selecSinglePeriodThemeticProductList(productIdList.get(i));
                        ThemeticProductDetail multiPeriodThemeticProductDetail = getThemeticProductDetail(productIdList.get(i), singlePeriodProductIdList);
                        exampleDatatemp.setProductName(multiPeriodThemeticProductDetail.getThemeticProductName());
                        exampleDatatemp.setCountOfPeriods(multiPeriodThemeticProductDetail.getCountOfPeriods());
                        List<ExampleSingleData> exampleSingleDataList = new ArrayList<>();
                        List<SinglePeriodThemeticProductDetail> singlePeriodThemeticProductDetail = multiPeriodThemeticProductDetail.getSinglePeriodThemeticProductDetail();
                        for (int j = 0; j < singlePeriodThemeticProductDetail.size(); j++) {
                            ExampleSingleData exampleSingleData = new ExampleSingleData();
                            exampleSingleData.setSingleId(singlePeriodThemeticProductDetail.get(j).getSinglePeriodProductId());
                            exampleSingleData.setSinglePeriodProductName(singlePeriodThemeticProductDetail.get(j).getSinglePeriodProductName());
                            exampleSingleData.setThumbnailUrl(singlePeriodThemeticProductDetail.get(j).getThumbnailUrl());
                            exampleSingleDataList.add(exampleSingleData);
                        }
                        exampleDatatemp.setExampleSingleDataList(exampleSingleDataList);
                    default:
                }
                exampleDataList.add(exampleDatatemp);

            }
        } else {
            return null;
        }
        return exampleDataList;
    }

    @Override
    public List<FolderItems> buildTree() {
        List<FolderItems> folderItemsList = new ArrayList<>();

        FolderItems folderItems0 = new FolderItems();
        folderItems0.setId(0);
        folderItems0.setParentId(-1);
        folderItems0.setName("”吉林一号“样例数据");
        folderItemsList.add(folderItems0);

        FolderItems folderItems1 = new FolderItems();
        folderItems1.setId(1);
        folderItems1.setParentId(0);
        folderItems1.setName("标准产品");
        folderItemsList.add(folderItems1);


        FolderItems folderItems10 = new FolderItems();
        folderItems10.setId(10);
        folderItems10.setParentId(1);
        folderItems10.setName("普通光学推扫");
        folderItemsList.add(folderItems10);
        FolderItems folderItems11 = new FolderItems();
        folderItems11.setId(11);
        folderItems11.setParentId(1);
        folderItems11.setName("夜光凝视");
        folderItemsList.add(folderItems11);
        FolderItems folderItems12 = new FolderItems();
        folderItems12.setId(12);
        folderItems12.setParentId(1);
        folderItems12.setName("视频凝视");
        folderItemsList.add(folderItems12);
        FolderItems folderItems13 = new FolderItems();
        folderItems13.setId(13);
        folderItems13.setParentId(1);
        folderItems13.setName("多光谱推扫");
        folderItemsList.add(folderItems13);
        List<ClientFileInfo> clientFileInfoList = gisClientFileMapper.getExampleStandardProduct();
        for (int i = 0; i < clientFileInfoList.size(); i++) {
            FolderItems folderItemsTem = new FolderItems();
            folderItemsTem.setId(300 + i);
            String ProductType = clientFileInfoList.get(i).getProductType();
            switch (ProductType) {
                case "普通光学影像":
                    folderItemsTem.setParentId(10);
                    break;
                case "夜光影像":
                    folderItemsTem.setParentId(11);
                    break;
                case "视频影像":
                    folderItemsTem.setParentId(12);
                    break;
                case "多光谱光学影像":
                    folderItemsTem.setParentId(13);
                    break;
                default:
                    break;
            }
            folderItemsTem.setProductId(clientFileInfoList.get(i).getProductId());
            folderItemsTem.setName(clientFileInfoList.get(i).getProductName());
            folderItemsTem.setGeoJson(clientFileInfoList.get(i).getGeoJson().toString());
            folderItemsTem.setDownloadUrl(clientFileInfoList.get(i).getDownloadUrl());

            folderItemsList.add(folderItemsTem);
        }


        FolderItems folderItems2 = new FolderItems();
        folderItems2.setId(2);
        folderItems2.setParentId(0);
        folderItems2.setName("融合镶嵌产品");
        folderItemsList.add(folderItems2);
        FolderItems folderItems20 = new FolderItems();
        folderItems20.setId(20);
        folderItems20.setParentId(2);
        folderItems20.setName("普通光学推扫");
        folderItemsList.add(folderItems20);
        FolderItems folderItems21 = new FolderItems();
        folderItems21.setId(21);
        folderItems21.setParentId(2);
        folderItems21.setName("夜光凝视");
        folderItemsList.add(folderItems21);
        FolderItems folderItems22 = new FolderItems();
        folderItems22.setId(22);
        folderItems22.setParentId(2);
        folderItems22.setName("多光谱推扫");
        folderItemsList.add(folderItems22);

        FolderItems folderItems200 = new FolderItems();
        folderItems200.setId(200);
        folderItems200.setParentId(0);
        folderItems200.setName("专题产品");
        folderItemsList.add(folderItems200);
        List<String> productIdList = pdmProductInfoMapper.getProductIdList("");
        if (productIdList != null) {
            for (int i = 0; i < productIdList.size(); i++) {
                FolderItems folderItemsTem = new FolderItems();
                folderItemsTem.setId(400 + i);
                int productType = pdmProductInfoMapper.selectProductTypeByProductId(productIdList.get(i));
                String TypeName = pdmProductTypeInfoMapper.selectProductTypeDescriptionByProductType(productType);
                folderItemsTem.setProductId(productIdList.get(i));
                switch (TypeName) {
                    case "正射产品":
                        OrthoProductDetail orthoProductDetail = getOrthoProductDetail(productIdList.get(i));
                        folderItemsTem.setName(orthoProductDetail.getProductName());
                        String Type = orthoProductDetail.getProductType();
                        switch(Type){
                            case "普通光学推扫":
                                folderItemsTem.setParentId(20);
                                break;
                            case "夜光凝视":
                                folderItemsTem.setParentId(21);
                                break;
                            case "多光谱推扫":
                                folderItemsTem.setParentId(22);
                                break;
                                }
                        folderItemsTem.setName(orthoProductDetail.getProductName());
                        folderItemsTem.setDownloadUrl(orthoProductDetail.getAllFileDownloadUrl());
                        folderItemsTem.setThumbUrl(orthoProductDetail.getThumbnailUrl());
                        folderItemsTem.setLayerName(orthoProductDetail.getLayerName());
                        break;

                    case "镶嵌产品":
                        InlayProductDetail inlayProductDetail = getInlayProductDetail(productIdList.get(i));
                        folderItemsTem.setName(inlayProductDetail.getProductName());
                        String Type2 = inlayProductDetail.getProductType();
                        switch(Type2){
                            case "普通光学推扫":
                                folderItemsTem.setParentId(20);
                                break;
                            case "夜光凝视":
                                folderItemsTem.setParentId(21);
                                break;
                            case "多光谱推扫":
                                folderItemsTem.setParentId(22);
                                break;
                        }
                        folderItemsTem.setName(inlayProductDetail.getProductName());
                        folderItemsTem.setDownloadUrl(inlayProductDetail.getAllFileDownloadUrl());
                        folderItemsTem.setThumbUrl(inlayProductDetail.getThumbnailUrl());
                        folderItemsTem.setLayerName(inlayProductDetail.getLayerName());
                        break;

                    case "专题产品":
                        List<String> singlePeriodProductIdList = pdmThemeticProductDetailInfoMapper.selecSinglePeriodThemeticProductList(productIdList.get(i));
                        ThemeticProductDetail multiPeriodThemeticProductDetail = getThemeticProductDetail(productIdList.get(i), singlePeriodProductIdList);
                        folderItemsTem.setName(multiPeriodThemeticProductDetail.getThemeticProductName());
                        folderItemsTem.setDownloadUrl(multiPeriodThemeticProductDetail.getAllFileDownloadUrl());

                        List<SinglePeriodThemeticProductDetail> singlePeriodThemeticProductDetail = multiPeriodThemeticProductDetail.getSinglePeriodThemeticProductDetail();//专题只展示第一期
                        folderItemsTem.setLayerName(singlePeriodThemeticProductDetail.get(0).getLayerName());
                        folderItemsTem.setThumbUrl(singlePeriodThemeticProductDetail.get(0).getThumbnailUrl());
                        break;
                    default:
                        break;
                }
                folderItemsList.add(folderItemsTem);
            }
        }


                FolderItems folderItems3 = new FolderItems();
                folderItems3.setId(3);
                folderItems3.setParentId(0);
                folderItems3.setName("光谱星5米一张图");
                folderItemsList.add(folderItems3);

                FolderItems folderItems4 = new FolderItems();
                folderItems4.setId(4);
                folderItems4.setParentId(3);
                folderItems4.setName("华北");
                folderItemsList.add(folderItems4);
                FolderItems folderItems5 = new FolderItems();
                folderItems5.setId(5);
                folderItems5.setParentId(3);
                folderItems5.setName("东北");
                folderItemsList.add(folderItems5);
                FolderItems folderItems6 = new FolderItems();
                folderItems6.setId(6);
                folderItems6.setParentId(3);
                folderItems6.setName("华中");
                folderItemsList.add(folderItems6);
                FolderItems folderItems7 = new FolderItems();
                folderItems7.setId(7);
                folderItems7.setParentId(3);
                folderItems7.setName("华东");
                folderItemsList.add(folderItems7);
                FolderItems folderItems8 = new FolderItems();
                folderItems8.setId(8);
                folderItems8.setParentId(3);
                folderItems8.setName("华南");
                folderItemsList.add(folderItems8);
                FolderItems folderItems9 = new FolderItems();
                folderItems9.setId(9);
                folderItems9.setParentId(3);
                folderItems9.setName("西南");
                folderItemsList.add(folderItems9);
                FolderItems folderItems9a = new FolderItems();
                folderItems9a.setId(91);
                folderItems9a.setParentId(3);
                folderItems9a.setName("西北");
                folderItemsList.add(folderItems9a);

                List<GisProvinceMapInfo> gisProvinceMapInfoList = gisProvinceMapInfoMapper.getAllProvinceMap();
                for (int j = 0; j < gisProvinceMapInfoList.size(); j++) {
                    FolderItems folderItemsTem11 = new FolderItems();
                    folderItemsTem11.setId(30 + j);
                    String location = gisProvinceMapInfoList.get(j).getLocation();
                    switch (location) {
                        case "华北":
                            folderItemsTem11.setParentId(4);
                            break;
                        case "东北":
                            folderItemsTem11.setParentId(5);
                            break;
                        case "华中":
                            folderItemsTem11.setParentId(6);
                            break;
                        case "华东":
                            folderItemsTem11.setParentId(7);
                            break;
                        case "华南":
                            folderItemsTem11.setParentId(8);
                            break;
                        case "西南":
                            folderItemsTem11.setParentId(9);
                            break;
                        case "西北":
                            folderItemsTem11.setParentId(91);
                            break;
                        default:
                            break;
                    }
                    folderItemsTem11.setName(gisProvinceMapInfoList.get(j).getAreaName());
                    folderItemsTem11.setGeoJson(gisProvinceMapInfoList.get(j).getImageGeo().toString());
                    folderItemsTem11.setDownloadUrl(gisProvinceMapInfoList.get(j).getDownloadUrl());
                    folderItemsTem11.setLayerName(gisProvinceMapInfoList.get(j).getLayerName());
                    folderItemsList.add(folderItemsTem11);
                }

                List<FolderItems> root = new ArrayList<>();
                root = iClientProductService.listToTree(folderItemsList);
                return root;
            }


}

