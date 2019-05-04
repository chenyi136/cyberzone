package com.safecode.cyberzone.pojo;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Created by xuq on 2018/6/21.
 */

public class DicItem {

    private Long id;

    private Long sort;

    private String text;

    private String value;

    private Long dicTypeId;

    private Long parentId;

    private Long isLeaf;

    private Long state;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public Long getDicTypeId() {
        return dicTypeId;
    }

    public void setDicTypeId(Long dicTypeId) {
        this.dicTypeId = dicTypeId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Long isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
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
