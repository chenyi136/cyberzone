package com.safecode.cyberzone.vo;

/**
 * Created by xuq on 2018/6/21.
 */
public class DicItemVo extends BaseCommonVo {
    private Integer dicTypeId;
    private String text;
    private Integer isLeaf;

    public Integer getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getDicTypeId() {
        return dicTypeId;
    }

    public void setDicTypeId(Integer dicTypeId) {
        this.dicTypeId = dicTypeId;
    }
}
