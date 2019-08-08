package com.cgwx.data.dto;

import java.util.List;

public class AttributeValuesDto {

    private int valueCount;

    private List<String> valueList;

    public int getValueCount() {
        return valueCount;
    }

    public void setValueCount(int valueCount) {
        this.valueCount = valueCount;
    }

    public List<String> getValueList() {
        return valueList;
    }

    public void setValueList(List<String> valueList) {
        this.valueList = valueList;
    }
}
