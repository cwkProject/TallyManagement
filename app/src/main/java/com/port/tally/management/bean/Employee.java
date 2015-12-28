package com.port.tally.management.bean;
/**
 * Created by 超悟空 on 2015/12/28.
 */

/**
 * 员工信息数据结构
 *
 * @author 超悟空
 * @version 1.0 2015/12/28
 * @since 1.0
 */
public class Employee {

    /**
     * 编码
     */
    private String id = null;

    /**
     * 姓名
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
     * 获取姓名
     *
     * @return 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
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
