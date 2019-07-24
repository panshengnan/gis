package com.cgwx.service;

import java.io.File;

public interface IlayerPublishService {


    boolean publishStyle(File sldFile, String styleName);
    boolean updateStyle(File sldFile, String styleName);
    boolean publishShp(String workSpace,String dataStore,String layerName,File zipFileSource,String srs,String style);
//    boolean publishShp(String workSpace,String dataStore,String layerName,String filePath,String srs,String style);
}
