package com.cgwx.service.impl;

import com.cgwx.service.IlayerPublishService;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class LayerPublishServiceImpl implements IlayerPublishService {

    @Value("${geoserverPath}")
    private String RESTURL;

    @Value("${geoserverUsername}")
    private String RESTUSER;

    @Value("${geoserverPassword}")
    private String RESTPW;


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
            if (publisher.publishShp(workSpace, dataStore, layerName, zipFileSource,srs,style)) {
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
}
