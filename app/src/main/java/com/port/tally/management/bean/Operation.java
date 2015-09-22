package com.port.tally.management.bean;
/**
 * Created by 超悟空 on 2015/9/21.
 */

/**
 * 作业过程数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/9/21
 * @since 1.0
 */
public class Operation {

    /**
     * 编码
     */
    private String id = null;

    /**
     * 名称
     */
    private String name = null;

    /**
     * 速记码
     */
    private String shortCode = null;

    /**
     * 获取编码
     *
     * @return 编码
     */
    public String getId() {
        return id;
    }

    /**
     * 设置编码
     *
     * @param id 编码
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取名称
     *
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取速记码
     *
     * @return 速记码
     */
    public String getShortCode() {
        return shortCode;
    }

    /**
     * 设置速记码
     *
     * @param shortCode 速记码
     */
    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }
}
