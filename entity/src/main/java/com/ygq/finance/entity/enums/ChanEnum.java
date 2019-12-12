package com.ygq.finance.entity.enums;

/**
 * @author ythawed
 * @date 2019/12/10 0010
 * <p>
 * 渠道配置信息
 */
public enum ChanEnum {

    /**
     * 渠道编号
     * 渠道名称
     * 渠道配置
     */
    A("001", "渠道1", "F:/javaPlugins/verification/data/aaa"),
    B("002", "渠道2", "F:/javaPlugins/verification/data/bbb"),
    ;

    private String chanId;
    private String chanNum;
    private String ftpPath;
    private String ftpUser;
    private String ftpPwd;
    private String rootDir;

    ChanEnum(String chanId, String chanNum, String rootDir) {
        this.chanId = chanId;
        this.chanNum = chanNum;
        this.rootDir = rootDir;
    }

    /**
     * 根据渠道id获取渠道配置
     */
    public static ChanEnum getByChanId(String chanId) {
        for (ChanEnum value : ChanEnum.values()) {
            if (value.getChanId().equals(chanId)) {
                return value;
            }
        }
        return null;
    }


    public String getChanId() {
        return chanId;
    }

    public void setChanId(String chanId) {
        this.chanId = chanId;
    }

    public String getChanNum() {
        return chanNum;
    }

    public void setChanNum(String chanNum) {
        this.chanNum = chanNum;
    }

    public String getFtpPath() {
        return ftpPath;
    }

    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
    }

    public String getFtpUser() {
        return ftpUser;
    }


    public String getFtpPwd() {
        return ftpPwd;
    }


    public String getRootDir() {
        return rootDir;
    }

}
