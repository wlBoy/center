package com.kl.ar.common.constant;

/**
 * 
 * @package:com.kl.ar.common.constant
 * @Description: 自评状态枚举类
 * @author: wanlei
 * @date: 2019年12月21日下午2:16:05
 */
public enum SelfAssessmentStatus {
    STATUS_2SIGN_2REVIEW(1011, "待评分"),
    STATUS_2SIGN_2REVIEW_OK(1010, "已评分");

    private Integer code;
    private String desc;

    SelfAssessmentStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(Integer code) {
        for (SelfAssessmentStatus status : SelfAssessmentStatus.values()) {
            if (status.getCode() != null) {
                if (status.getCode().intValue() == code.intValue()) {
                    return status.getDesc();
                }
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
