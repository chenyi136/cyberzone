package com.safecode.cyberzone.global.consts;

/**
 * Created by xuq on 2018/7/16.
 */
public class DetectionApplConsts {

    //申请状态:未提交
    public static final String NO_COMMIT ="0";
    //申请状态:审核中
    public static final String CHECKING ="1";
    //申请状态:检测中
    public static final String DETECTIONING ="2";
    //申请状态:未通过
    public static final String NO_PASS ="3";
    //申请状态:通过
    public static final String YES_PASS ="4";
    //申请状态:检测完成
    public static final String END_CHECK="5";

    //执行安全性检测safecheck在数据表中的分割符
    public static final String SEPARATOR_SAFECHECK = "、";

    //页面来源
    //来源于cyberzone
    public static final String SOURCE_PAGE ="1";
}
