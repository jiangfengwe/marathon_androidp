package com.tdin360.zjw.marathon.model;

/**
 * Created by jeffery on 2017/12/27.
 */

public class UpdateBean {

    /**
     * AppVersion : 0
     * AppUpdateLink : null
     * UpdateContent : <div style='word-wrap: break-word'><h3 style='text-align:center'>版本更新</h3><ul style='list-style:none;color:#333333;letter-spacing:1px;'><li style='margin-top:8px;'>1.测试一</li><li style='margin-top:8px;'>2.测试二</li><li style='margin-top:8px;'>3.测试三</li></ul></div>
     */

    private int AppVersion;
    private String AppUpdateLink;
    private String UpdateContent;

    public int getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(int AppVersion) {
        this.AppVersion = AppVersion;
    }

    public String getAppUpdateLink() {
        return AppUpdateLink;
    }

    public void setAppUpdateLink(String AppUpdateLink) {
        this.AppUpdateLink = AppUpdateLink;
    }

    public String getUpdateContent() {
        return UpdateContent;
    }

    public void setUpdateContent(String UpdateContent) {
        this.UpdateContent = UpdateContent;
    }
}
