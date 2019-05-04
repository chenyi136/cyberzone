package com.safecode.cyberzone.vo;

/**
 * Created by xuq on 2018/6/21.
 */
public abstract class BaseCommonVo {
    private Integer startIndex;
    private Integer rows;

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }
}
