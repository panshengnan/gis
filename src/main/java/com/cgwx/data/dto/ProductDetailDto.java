package com.cgwx.data.dto;

public class ProductDetailDto {

    private String ProductClass;

    private OrthoProductDetail orthoProductDetail;

    private InlayProductDetail inlayProductDetail;

    private SubdivisionProductDetail subdivisionProductDetail;

    private ThemeticProductDetail ThemeticProductDetail;

    private StandardProductDetail standardProductDetail;

    public String getProductClass() {
        return ProductClass;
    }

    public void setProductClass(String productClass) {
        ProductClass = productClass;
    }

    public OrthoProductDetail getOrthoProductDetail() {
        return orthoProductDetail;
    }

    public void setOrthoProductDetail(OrthoProductDetail orthoProductDetail) {
        this.orthoProductDetail = orthoProductDetail;
    }

    public InlayProductDetail getInlayProductDetail() {
        return inlayProductDetail;
    }

    public void setInlayProductDetail(InlayProductDetail inlayProductDetail) {
        this.inlayProductDetail = inlayProductDetail;
    }

    public SubdivisionProductDetail getSubdivisionProductDetail() {
        return subdivisionProductDetail;
    }

    public void setSubdivisionProductDetail(SubdivisionProductDetail subdivisionProductDetail) {
        this.subdivisionProductDetail = subdivisionProductDetail;
    }

    public com.cgwx.data.dto.ThemeticProductDetail getThemeticProductDetail() {
        return ThemeticProductDetail;
    }

    public void setThemeticProductDetail(com.cgwx.data.dto.ThemeticProductDetail themeticProductDetail) {
        ThemeticProductDetail = themeticProductDetail;
    }

    public StandardProductDetail getStandardProductDetail() {
        return standardProductDetail;
    }

    public void setStandardProductDetail(StandardProductDetail standardProductDetail) {
        this.standardProductDetail = standardProductDetail;
    }
}
