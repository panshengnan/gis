package com.cgwx.service.impl;

import com.cgwx.dao.*;
import com.cgwx.data.dto.DirectoryInfo;
import com.cgwx.data.dto.SecondaryFileStructure;
import com.cgwx.data.dto.UploadFileReturn;
import com.cgwx.data.entity.*;
import com.cgwx.service.IProductArchiveService;
import com.cgwx.service.IProductDownloadService;
import com.cgwx.service.IlayerPublishService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.JPEGEncodeParam;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import net.sf.json.JSONException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.annotations.Param;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import sun.nio.cs.StreamDecoder;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by PanSN on 2018/9/5.
 */

@Service
public class IProductArchiveServiceImpl implements IProductArchiveService {

    @Autowired
    IProductDownloadService iProductDownloadService;

    @Autowired
    IlayerPublishService ilayerPublishService;//ss0726

    @Autowired
    GisThemeticProductInfoMapper pdmThemeticProductInfoMapper;

    @Autowired
    GisOrthoProductInfoMapper pdmOrthoProductInfoMapper;

    @Autowired
    GisInlayProductInfoMapper pdmInlayProductInfoMapper;

    @Autowired
    GisSubdivisionProductInfoMapper pdmSubdivisionProductInfoMapper;

    @Autowired
    GisArchiveCheckInfoMapper pdmArchiveCheckInfoMapper;

    @Autowired
    GisProductInfoMapper pdmProductInfoMapper;

    @Autowired
    GisThemeticProductDetailIndustryInfoMapper pdmThemeticProductDetailIndustryInfoMapper;

    @Autowired
    GisThemeticProductDetailInfoMapper pdmThemeticProductDetailInfoMapper;

    @Autowired
    GisProducerInfoMapper pdmProducerInfoMapper;

    @Autowired
    GisArchiveRecordsInfoMapper pdmArchiveRecordsInfoMapper;

    @Autowired
    GisUserInfoMapper pdmUserInfoMapper;

    @Autowired
    GisAdvancedProductShpInfoMapper pdmAdvancedProductShpInfoMapper;

    @Autowired
    GisSatelliteInfoMapper pdmSatelliteInfoMapper;

    @Autowired
    GisSensorInfoMapper pdmSensorInfoMapper;

    @Autowired
    GisProductStoreLinkInfoMapper gisProductStoreLinkInfoMapper;

    StreamDecoder sd;

    @Override/*上传文件*/
    public UploadFileReturn uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        String fileName = file.getOriginalFilename();
        UploadFileReturn uploadFileReturn = new UploadFileReturn();

        String path = System.getProperty("user.dir") + "/临时存储区";
//        String path = "F:/pdmWindows測試/pdm-master/临时存储区";

        File dest = new File(path + "/" + fileName);
        System.out.println("文件保存路径为：" + dest.toString());
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {

            file.transferTo(dest);
            uploadFileReturn.setFileName(dest.toString());
            uploadFileReturn.setFilePath(path);
            return uploadFileReturn;

        } catch (IllegalStateException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


//    public String detectCodeType(String path) throws IOException {
//
//        File file = new File(path);
//        InputStream in = new java.io.FileInputStream(file);
//        byte[] b = new byte[50];
//        in.read(b);
//        in.close();
//        System.out.println("前三个字节是："+b);
//        if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
//
//            System.out.println(file.getName() + "：编码为UTF-8");
//        }
//        else
//            System.out.println(file.getName() + "：可能是GBK，也可能是其他编码");
//
//        return "";
//    }

    @Override
    public void unzipTest(String path) {



        System.out.println(path);
//        try {
            File file = new File(path);
//            InputStream in = new FileInputStream(file);
//            InputStreamReader reader = new InputStreamReader(in, "UTF8");
//
//            System.out.println(reader);
//
////            super(in);
//
//            sd = StreamDecoder.forInputStreamReader(in, this, (String)null);
//            System.out.println(reader.read());
//            CharsetDecoder cd2 = charset.newDecoder();
//            InputStreamReader reader = new InputStreamReader(in, cd2);
//
//        }catch (Exception pe)
//        {
//            System.out.println("error");
//        }


            int BUFFER = 2048;

            try {
                BufferedOutputStream dest = null;
                FileInputStream fis = new FileInputStream(file);
                ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
                ZipEntry entry;
                while((entry = zis.getNextEntry()) != null) {
                    System.out.println("Extracting: " +entry);
                    int count;
                    byte data[] = new byte[BUFFER];
                    // write the files to the disk
                    FileOutputStream fos = new
                            FileOutputStream(entry.getName());
                    dest = new
                            BufferedOutputStream(fos, BUFFER);
                    while ((count = zis.read(data, 0, BUFFER))
                            != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                }
                zis.close();
            } catch(Exception e) {
                e.printStackTrace();
            }

    }

    @Override/*解压zip文件*/
    public String getZipCodeType(String source) throws IOException {

        String codeType = "GBK";
        String zipName =source.substring(source.lastIndexOf('\\')+1,source.lastIndexOf('.'));
        System.out.println(zipName);
        try {
            int count =0;
            File zipFile = new File(source);
            ZipFile zFile = new ZipFile(zipFile);
            zFile.setFileNameCharset("GBK");
            List<FileHeader> headerList = zFile.getFileHeaders();
            for (FileHeader fileHeader : headerList) {
                if (fileHeader.isDirectory()) {
                    if(count++==0) {
                        String tmp = fileHeader.getFileName();
                        tmp = tmp.substring(0,tmp.indexOf('/'));
                        System.out.println(tmp);
                        if(!zipName.equals(tmp))
                            codeType = "UTF-8";
                    }
                }
            }
        } catch (ZipException e) {

        }
        return codeType;
    }

    @Override/*解压zip文件*/
    public String unZip(String source, String dest,String codeType) throws IOException {

        String unzipPath = "";
        String password = "password";
        System.out.println("解压中……");
        try {
            File zipFile = new File(source);
            ZipFile zFile = new ZipFile(zipFile);
            zFile.setFileNameCharset(codeType);//UTF-8
            File destDir = new File(dest);
            if (zFile.isEncrypted()) {
                zFile.setPassword(password.toCharArray());
            }
            zFile.extractAll(dest);
            List<FileHeader> headerList = zFile.getFileHeaders();
            List<File> extractedFileList = new ArrayList<File>();
            for (FileHeader fileHeader : headerList) {
                if (!fileHeader.isDirectory()) {
                    extractedFileList.add(new File(destDir, fileHeader.getFileName()));
                }
                else {
                    unzipPath = fileHeader.getFileName();
                    System.out.println(unzipPath);
                    unzipPath = unzipPath.substring(0,unzipPath.indexOf('/'));
                    System.out.println("解压后的文件夹名字是："+unzipPath);

                }
            }
            File[] extractedFiles = new File[extractedFileList.size()];
            extractedFileList.toArray(extractedFiles);
            for (File f : extractedFileList) {
                System.out.println(f.getAbsolutePath() + "....");
            }
        } catch (ZipException e) {
            System.out.println("解压失败！");
        }

        String tmpPath = source;
        tmpPath = tmpPath.substring(0, tmpPath.lastIndexOf('\\')+1);
        unzipPath = tmpPath+unzipPath;
        System.out.println("实际路径是："+unzipPath);
        return unzipPath;
    }

    @Override/*压缩zip文件*/
    public void zip(String srcFile, String dest, String passwd) {
        File srcfile = new File(srcFile);
        String destname = buildDestFileName(srcfile, dest);
        ZipParameters par = new ZipParameters();
        par.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        par.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        if (passwd != null) {
            par.setEncryptFiles(true);
            par.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            par.setPassword(passwd.toCharArray());
        }
        try {
            ZipFile zipfile = new ZipFile(destname);
            if (srcfile.isDirectory()) {
                zipfile.addFolder(srcfile, par);
            } else {
                zipfile.addFile(srcfile, par);
            }
        } catch (ZipException e) {
        }
    }

    public static String buildDestFileName(File srcfile, String dest) {
        if (dest == null) {
            if (srcfile.isDirectory()) {
                dest = srcfile.getParent() + File.separator + srcfile.getName() + ".zip";
            } else {
                String filename = srcfile.getName().substring(0, srcfile.getName().lastIndexOf("."));
                dest = srcfile.getParent() + File.separator + filename + ".zip";
            }
        } else {
            createPath(dest);
            if (dest.endsWith(File.separator)) {
                String filename = "";
                if (srcfile.isDirectory()) {
                    filename = srcfile.getName();
                } else {
                    filename = srcfile.getName().substring(0, srcfile.getName().lastIndexOf("."));
                }
                dest += filename + ".zip";
            }
        }
        return dest;
    }

    public static void createPath(String dest) {
        File destDir = null;
        if (dest.endsWith(File.separator)) {
            destDir = new File(dest);
        } else {
            destDir = new File(dest.substring(0, dest.lastIndexOf(File.separator)));
        }

        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }

    @Override
    public void updateXml(Document document, GisThemeticProductInfo pdmThemeticProductInfo) {

        Element element = document.createElement("productData");
        element.setAttribute("productId", "id1");
        Element element_name = document.createElement("name");
        element_name.setTextContent("2B");
//         Element element_nianling = doc.createElement("nianling");
//         element_nianling.setTextContent("23");
//         Element element_jieshao = doc.createElement("jieshao");
//         element_jieshao.setTextContent("gi sh");
        element.appendChild(element_name);
//         element.appendChild(element_nianling);
//         element.appendChild(element_jieshao);
        document.getDocumentElement().appendChild(element);
    }

    @Override/*更新xml*/
    public void update(Document document, String fileName) {
        try {

            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            tf.transform(new DOMSource(document), new StreamResult(fileName));
            System.out.println("写入完成！");
        } catch (Exception e) {
        }
    }

    @Override/*生成缩略图*/
    public String changeTiftoJpg(String fileName) {

        String jpgName = fileName.replace(".tif", ".jpg");
        try {
            RenderedOp src = JAI.create("fileLoad", fileName);
            OutputStream os = new FileOutputStream(jpgName);
            JPEGEncodeParam param = new JPEGEncodeParam();
            ImageEncoder enc = ImageCodec.createImageEncoder("JPEG", os, param);
            enc.encode(src);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jpgName;
    }

    @Override/*缩放img*/
    public String ZoomImg(String fileName) {

        String thumbnailImg = fileName.replace(".jpg", "Thumbnail.jpg");
        try {
            FileInputStream fis = new FileInputStream(fileName);
            BufferedImage bfimg = ImageIO.read(fis);
            int width = new Double(bfimg.getWidth()).intValue();
            int height = new Double(bfimg.getHeight()).intValue();
            if (width < 500) return fileName;
            int newHeight = height / 5;
            int newWidth = width / 5;
            BufferedImage bufferImgOut = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_3BYTE_BGR);
            Graphics graphics = bufferImgOut.createGraphics();
            graphics.drawImage(bfimg.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
            ImageIO.write(bufferImgOut, "jpg", new File(thumbnailImg));
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File f = new File(fileName);
        f.delete();
        return thumbnailImg;
    }

    @Override
    public String getUUId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    @Override /*分别获取每种高级产品的id*/
    public String getNextProductId(int productType) {
        Date currentDate = new Date();
        SimpleDateFormat formatyyyyMMddHS = new SimpleDateFormat("yyyy-MM-dd 00:00:01");
        SimpleDateFormat formatyyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        String currentDateStr = formatyyyyMMddHS.format(currentDate);
        int count = 0;
        String productId = "";
        String timestamp = "";
        switch (productType) {
            case 1://专题
                count = pdmThemeticProductInfoMapper.selectThemeticProductCountByDate(currentDateStr);
                timestamp = formatyyyyMMdd.format(new Date());
                productId = "THEME_PRODUCT_" + timestamp + "_" + (int) (Math.random() * 10000) + "_" + (new String(10001 + count + "").substring(1, 5));
                break;
            case 2://正射
                count = pdmOrthoProductInfoMapper.selectOrthoProductCountByDate(currentDateStr);
                timestamp = formatyyyyMMdd.format(new Date());
                productId = "ORTHO_PRODUCT_" + timestamp + "_" + (int) (Math.random() * 10000) + "_" + (new String(10001 + count + "").substring(1, 5));
                break;
            case 3://镶嵌
                count = pdmInlayProductInfoMapper.selectInlayProductCountByDate(currentDateStr);
                timestamp = formatyyyyMMdd.format(new Date());
                productId = "INLAY_PRODUCT_" + timestamp + "_" + (int) (Math.random() * 10000) + "_" + (new String(10001 + count + "").substring(1, 5));
                break;
            case 4://分幅
                count = pdmSubdivisionProductInfoMapper.selectSubdivisionProductCountByDate(currentDateStr);
                timestamp = formatyyyyMMdd.format(new Date());
                productId = "SUBDIVISION_PRODUCT_" + timestamp + "_" + (int) (Math.random() * 10000) + "_" + (new String(10001 + count + "").substring(1, 5));
                break;
            default:
                break;
        }

        return productId;
    }

    @Override /*获取一个文件夹中的所有文件*/
    public List<String> getFileNameList(String productId) {

        String path = iProductDownloadService.getEntityFilePath(productId);
        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                String tmp = tempList[i].toString();
                tmp = tmp.substring(tmp.lastIndexOf('\\') + 1);
                System.out.println(tmp);
                files.add(tmp);
            }
            if (tempList[i].isDirectory()) {
            }
        }
        return files;
    }

    @Override/*获取专题多期产品中二级目录中的目录结构，并且写入归档缓存表以及归档记录*/
    public SecondaryFileStructure getSecondaryFileStructureAndWriteCheckTable(String path, String archivePersonnel, int type) {

        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        SecondaryFileStructure secondaryFileStructure = new SecondaryFileStructure();
        String tempId = getUUId();
        secondaryFileStructure.setTempId(tempId);
        List<DirectoryInfo> directoryInfoList = new ArrayList<DirectoryInfo>();
        int count = 0;
        if (tempList != null)
            for (int i = 0; i < tempList.length; i++) {
                if (tempList[i].isFile()) {
                    String tmp = tempList[i].toString();
                    tmp = tmp.substring(tmp.lastIndexOf('\\') + 1);
                    System.out.println(tmp);
                    files.add(tmp);
                }
                if (tempList[i].isDirectory()) {
                    DirectoryInfo directoryInfo = new DirectoryInfo();
                    directoryInfo.setSingleTempId((count++) + "");
                    List<String> files2 = new ArrayList<String>();
                    String tmp = tempList[i].toString();
                    tmp = tmp.substring(tmp.lastIndexOf('\\') + 1);
                    directoryInfo.setDirectoryName(tmp);
                    System.out.println(tmp);
                    File[] tempList2 = tempList[i].listFiles();
                    for (int j = 0; j < tempList2.length; j++) {
                        String tmp2 = tempList2[j].toString();
                        tmp2 = tmp2.substring(tmp2.lastIndexOf('\\') + 1);
                        System.out.println(tmp2);
                        files2.add(tmp2);
                    }
                    directoryInfo.setFileListInDirectory(files2);
                    directoryInfoList.add(directoryInfo);
                }
            }
        secondaryFileStructure.setFile(files);
        secondaryFileStructure.setDirectory(directoryInfoList);
        GisArchiveCheckInfo pdmArchiveCheckInfo = new GisArchiveCheckInfo();
        pdmArchiveCheckInfo.setTemporaryPath(path);
        pdmArchiveCheckInfo.setProductId(tempId);
        pdmArchiveCheckInfo.setFileName(path.substring(path.lastIndexOf('\\') + 1));
        pdmArchiveCheckInfo.setStatus(0);
        pdmArchiveCheckInfoMapper.insert(pdmArchiveCheckInfo);
        GisArchiveRecordsInfo pdmArchiveRecordsInfo = new GisArchiveRecordsInfo();
        pdmArchiveRecordsInfo.setArchiveResult(2);
        pdmArchiveRecordsInfo.setArchivePersonnel(archivePersonnel);
        pdmArchiveRecordsInfo.setProductName(path.substring(path.lastIndexOf('\\') + 1));
        pdmArchiveRecordsInfo.setArchiveType(type);
        pdmArchiveRecordsInfo.setProductId(tempId);
        pdmArchiveRecordsInfo.setArchiveTime(new Date());
        pdmArchiveRecordsInfoMapper.insert(pdmArchiveRecordsInfo);
        System.out.println("路径是：" + path.substring(path.lastIndexOf('\\') + 1));
        return secondaryFileStructure;
    }


    @Override
    public String writeArchiveRecordAndWriteArchiveCheckInfo(String path, String archivePersonnel, int type) {

        String tempId = getUUId();
        GisArchiveCheckInfo pdmArchiveCheckInfo = new GisArchiveCheckInfo();
        pdmArchiveCheckInfo.setTemporaryPath(path);
        pdmArchiveCheckInfo.setProductId(tempId);
        pdmArchiveCheckInfo.setFileName(path.substring(path.lastIndexOf('\\') + 1));
        pdmArchiveCheckInfo.setStatus(0);
        pdmArchiveCheckInfoMapper.insert(pdmArchiveCheckInfo);
        GisArchiveRecordsInfo pdmArchiveRecordsInfo = new GisArchiveRecordsInfo();
        pdmArchiveRecordsInfo.setArchiveResult(0);
        pdmArchiveRecordsInfo.setArchivePersonnel(archivePersonnel);
        pdmArchiveRecordsInfo.setProductName(path.substring(path.lastIndexOf('\\') + 1));
        pdmArchiveRecordsInfo.setArchiveType(type);
        pdmArchiveRecordsInfo.setProductId(tempId);
        pdmArchiveRecordsInfo.setArchiveTime(new Date());
        pdmArchiveRecordsInfoMapper.insert(pdmArchiveRecordsInfo);
        System.out.println("路径是：" + path.substring(path.lastIndexOf('\\') + 1));
        return tempId;
    }

    @Override/*获取专题多期产品中二级目录中的目录结构*/
    public SecondaryFileStructure getSecondaryFileStructure(String path) {

        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        SecondaryFileStructure secondaryFileStructure = new SecondaryFileStructure();
        String tempId = getUUId();
        secondaryFileStructure.setTempId(tempId);
        List<DirectoryInfo> directoryInfoList = new ArrayList<DirectoryInfo>();
        int count = 0;
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                String tmp = tempList[i].toString();
                tmp = tmp.substring(tmp.lastIndexOf('\\') + 1);
                System.out.println(tmp);
                files.add(tmp);
            }
            if (tempList[i].isDirectory()) {
                DirectoryInfo directoryInfo = new DirectoryInfo();
                directoryInfo.setSingleTempId((count++) + "");
                List<String> files2 = new ArrayList<String>();
                String tmp = tempList[i].toString();
                tmp = tmp.substring(tmp.lastIndexOf('\\') + 1);
                directoryInfo.setDirectoryName(tmp);
                System.out.println(tmp);
                File[] tempList2 = tempList[i].listFiles();
                for (int j = 0; j < tempList2.length; j++) {
                    String tmp2 = tempList2[j].toString();
                    tmp2 = tmp2.substring(tmp2.lastIndexOf('\\') + 1);
                    System.out.println(tmp2);
                    files2.add(tmp2);

                }
                directoryInfo.setFileListInDirectory(files2);
                directoryInfoList.add(directoryInfo);
            }
        }
        secondaryFileStructure.setFile(files);
        secondaryFileStructure.setDirectory(directoryInfoList);

        return secondaryFileStructure;
    }

    /* 复制文件内容 */
    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }
        System.out.println("归档完成！");

    }

    @Override /* 复制文件内容 */
    public void copyFile(File source, File dest) {
        try {
            FileUtils.copyFile(source, dest);
        } catch (Exception e) {
        }
    }

    @Override/*复制整个文件夹内容*/
    public void copyFolder(String oldPath, String newPath) {

        try {
            (new File(newPath)).mkdirs();
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }

    }

    @Override/**/
    public List<String> getClientNameList(String clientName) {

        return pdmProductInfoMapper.selectClientNameList(clientName);
    }

    @Override/**/
    public List<String> getDeliverNameList(String deliverName) {

        return pdmProductInfoMapper.selectDeliverNameList(deliverName);
    }

    @Override/**/
    public List<String> getProducerList(String producer) {

        return pdmProducerInfoMapper.selectProducerList(producer);
    }

    @Override/**/
    public int updateProductInfo(GisProductInfo pdmProductInfo) {
        return pdmProductInfoMapper.insert(pdmProductInfo);
    }

    @Override/**/
    public String getProductName(String tempId) {

        return pdmArchiveCheckInfoMapper.selectFileNameById(tempId);
    }

    @Override/**/
    public String getThemeticProductTemporaryPath(String tempId) {

        return pdmArchiveCheckInfoMapper.selectTemporaryPathById(tempId);
    }

    @Override/**/
    public int updateThemeticProductDetailIndustry(GisThemeticProductDetailIndustryInfo pdmThemeticProductDetailIndustryInfo) {

        return pdmThemeticProductDetailIndustryInfoMapper.insert(pdmThemeticProductDetailIndustryInfo);
    }

    @Override/**/
    public int updateThemeticProductDetail(GisThemeticProductDetailInfo pdmThemeticProductDetailInfo) {

        return pdmThemeticProductDetailInfoMapper.insert(pdmThemeticProductDetailInfo);
    }

    @Override/**/
    public int updateThemeticProduct(GisThemeticProductInfo pdmThemeticProductInfo) {

        return pdmThemeticProductInfoMapper.insert(pdmThemeticProductInfo);
    }

    @Override
    public int insertPdmProducerInfo(String producerName) {

        GisProducerInfo pdmProducerInfo = new GisProducerInfo();
        pdmProducerInfo.setProducer(producerName);
        return pdmProducerInfoMapper.insert(pdmProducerInfo);
    }

    @Override
    public int selectCountByProducerName(String producerName) {

        return pdmProducerInfoMapper.selectCountByProducerName(producerName);
    }

    @Override
    public int updatePdmProducerInfo(String producerName) {

        int count = selectCountByProducerName(producerName);
        if (count == 0) {
            insertPdmProducerInfo(producerName);
        }
        return 1;
    }

    @Override
    public PageInfo<GisArchiveRecordsInfo> getArchiveRecordList(String archivePersonnel, int curPageNum, int maxResult, String productName, int archiveType, int archiveStatus) {

        PageHelper.startPage(curPageNum, maxResult);
        List<GisArchiveRecordsInfo> pdmArchiveRecords = pdmArchiveRecordsInfoMapper.selectArchiveRecordsByArchivePersonnel(archivePersonnel, productName, archiveType, archiveStatus);
        PageInfo<GisArchiveRecordsInfo> pageInfo = new PageInfo<>(pdmArchiveRecords);
        return pageInfo;
    }

    @Override
    public int updateArchiveRecordsInfo(GisArchiveRecordsInfo pdmArchiveRecordsInfo, String tempId, int productType) {

        pdmArchiveRecordsInfoMapper.updateArchiveRecordsInfo(pdmArchiveRecordsInfo.getProductId(), pdmArchiveRecordsInfo.getArchiveResult(), tempId, productType);
        return 1;
    }

    @Override
    public String getArchivePersonnelName(String archivePersonnel) {

        return pdmUserInfoMapper.selectUserNameByUserId(archivePersonnel);
    }

    @Override
    public int updateOrthoProduct(GisOrthoProductInfo pdmOrthoProductInfo) {

        return pdmOrthoProductInfoMapper.insert(pdmOrthoProductInfo);
    }

    @Override
    public int updateInlayProduct(GisInlayProductInfo pdmInlayProductInfo) {

        return pdmInlayProductInfoMapper.insert(pdmInlayProductInfo);
    }

    @Override
    public int updateSubdivisionProduct(GisSubdivisionProductInfo pdmSubdivisionProductInfo) {

        return pdmSubdivisionProductInfoMapper.insert(pdmSubdivisionProductInfo);
    }


    @Override
    public int updateAdvancedProductShpInfo(GisAdvancedProductShpInfo pdmAdvancedProductShpInfo) {

        return pdmAdvancedProductShpInfoMapper.insert(pdmAdvancedProductShpInfo);
    }

    @Override
    public String xml2jsonString(String path) throws JSONException, IOException {
        File file = new File(path);
        InputStream in = new FileInputStream(file);
        String xml = IOUtils.toString(in);
        return XML.toJSONObject(xml).toString();
    }

    @Override
    public List<String> getSatelliteList() {
        return pdmSatelliteInfoMapper.selectSatelliteInfo();
    }

    @Override
    public List<String> getSensorList() {
        return pdmSensorInfoMapper.selectSensorInfo();
    }

    @Override
    public String getXmlFilePath(String parentPath){

        String xmlPath = "";
        File file = new File(parentPath);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                String tmp = tempList[i].toString();
                String postfix = tmp.substring(tmp.lastIndexOf('.') + 1);
                if (postfix.equals("xml") || postfix.equals("XML") && !tmp.contains("shp.xml"))
                    xmlPath = tmp;
            }
        }
        return xmlPath;
    }

    ////////////////////////////////////////////
    @Override
    public String productCheck(int productType){

        switch (productType){
            case 1://专题
                break;
        }
        return "";
    }

    @Override
    public boolean isExistTif(String path){

        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                String tmp = tempList[i].toString();
                String postfix = tmp.substring(tmp.lastIndexOf('.') + 1);
                if (postfix.equals("tif") || postfix.equals("TIF"))
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean isExistShp(String path){

        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                String tmp = tempList[i].toString();
                String postfix = tmp.substring(tmp.lastIndexOf('.') + 1);
                if (postfix.equals("shp") || postfix.equals("SHP"))
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean isExistJpg(String path){

        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                String tmp = tempList[i].toString();
                String postfix = tmp.substring(tmp.lastIndexOf('.') + 1);
                if ((postfix.equals("jpg") || postfix.equals("JPG")) && !tmp.contains("legend"))
                    return true;
            }
        }
        return false;
    }
    @Override
    public String  getSldName(String path){
        File file = new File(path);
        File[] subFiles = file.listFiles();
        //获取e盘下所有的文件或文件夹对象
        if (null!=subFiles){
            for (File subFile : subFiles) {
                if(subFile.isFile() && subFile.getName().endsWith(".sld"))
                    return subFile.getName();
            }
        }
        return "";
    }
    @Override
    public String getLegendUrl(String productId){
        List<GisProductStoreLinkInfo> StoreLinkList = new ArrayList<>();
        StoreLinkList =gisProductStoreLinkInfoMapper.selectProductStoreLinksByProductId(productId);
        String legendPath = "";

        for (int i = 0; i < StoreLinkList.size(); i++) {
                String tmp = StoreLinkList.get(i).getStoreLink();
                String postfix = tmp.substring(0,tmp.lastIndexOf('.'));
                if (postfix.contains("legend"))
                    legendPath = tmp;
        }
        return legendPath;
    }

    ///////////////////////////////////////////////////
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
    public List<String> getShpFileList(String path){
        List<String> fileList = new ArrayList<>();
        String fileName = ilayerPublishService.getShpPathWithoutCutline(path);//ss0726
        fileList.add(fileName);
        System.out.println(fileName);
        fileName = fileName.substring(0,fileName.indexOf('.')-1);
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                String tmp = tempList[i].toString();
                if (tmp.contains(fileName))
                   fileList.add(tmp);
            }
        }
        return  fileList;
    }

    @Override
    public void rename(String path,String newName){//有时间改成只检测后缀名

        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
               File tmpFile = tempList[i];
               if(tmpFile.toString().contains(".shp") && !tmpFile.toString().contains(".xml")) {
                   File newFile = new File(path+"\\"+newName+".shp");
                   tmpFile.renameTo(newFile);
               }
               else if(tmpFile.toString().contains(".shx")) {
                   File newFile = new File(path+"\\"+newName+".shx");
                   tmpFile.renameTo(newFile);
               }
               else if(tmpFile.toString().contains(".dbf")) {
                   File newFile = new File(path+"\\"+newName+".dbf");
                   tmpFile.renameTo(newFile);
               }
               else if(tmpFile.toString().contains(".sbx")) {
                   File newFile = new File(path+"\\"+newName+".sbx");
                   tmpFile.renameTo(newFile);
               }
               else if(tmpFile.toString().contains(".sbn")) {
                   File newFile = new File(path+"\\"+newName+".sbn");
                   tmpFile.renameTo(newFile);
               }
               else if(tmpFile.toString().contains(".prj")) {
                   File newFile = new File(path+"\\"+newName+".prj");
                   tmpFile.renameTo(newFile);
               }
            }
        }
        System.out.println("重命名完成！");
    }
    @Override
    public String combination(String key,String value,boolean isEnd){

        String data = key+"="+value;
        if(!isEnd)
            data+='&';
        return data;

    }
}
