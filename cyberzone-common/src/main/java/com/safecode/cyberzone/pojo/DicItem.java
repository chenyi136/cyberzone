package com.safecode.cyberzone.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Objects;

/**
 * Created by xuq on 2018/6/21.
 */

@ApiModel
public class DicItem {

    @ApiModelProperty(value = "条目id")
    private Integer id;

    @ApiModelProperty(value = "排序序号")
    private Integer sort;

    @ApiModelProperty(value = "条目名称")
    private String text;

    @ApiModelProperty(value = "条目值")
    private String value;

    @ApiModelProperty(value = "字典类型id")
    private Integer dicTypeId;

    @ApiModelProperty(value = "父节点Id")
    private Integer parentId;

    @ApiModelProperty(value = "是否为叶子节点")
    private Integer isLeaf;

    @ApiModelProperty(value = "状态")
    private Integer state;

    @ApiModelProperty(value = "类型")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "DicItem{" +
                "id=" + id +
                ", sort=" + sort +
                ", text='" + text + '\'' +
                ", value='" + value + '\'' +
                ", dicTypeId=" + dicTypeId +
                ", parentId=" + parentId +
                ", isLeaf=" + isLeaf +
                ", state=" + state +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DicItem dicItem = (DicItem) o;
        return Objects.equals(id, dicItem.id) &&
                Objects.equals(sort, dicItem.sort) &&
                Objects.equals(text, dicItem.text) &&
                Objects.equals(value, dicItem.value) &&
                Objects.equals(dicTypeId, dicItem.dicTypeId) &&
                Objects.equals(parentId, dicItem.parentId) &&
                Objects.equals(isLeaf, dicItem.isLeaf) &&
                Objects.equals(state, dicItem.state) &&
                Objects.equals(type, dicItem.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sort, text, value, dicTypeId, parentId, isLeaf, state, type);
    }
}
