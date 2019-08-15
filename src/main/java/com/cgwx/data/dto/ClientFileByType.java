package com.cgwx.data.dto;

import java.util.List;

public class ClientFileByType {
    String Filetype;
    List<ClientFileInfo> ProductList;

    public String getFiletype() {
        return Filetype;
    }

    public void setFiletype(String filetype) {
        Filetype = filetype;
    }


    public List<ClientFileInfo> getProductList() {
        return ProductList;
    }

    public void setProductList(List<ClientFileInfo> productList) {
        ProductList = productList;
    }
}
