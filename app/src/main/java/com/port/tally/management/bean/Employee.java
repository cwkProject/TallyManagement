package com.port.tally.management.bean;
/**
 * Created by 超悟空 on 2015/12/28.
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 员工信息数据结构
 *
 * @author 超悟空
 * @version 1.0 2015/12/28
 * @since 1.0
 */
public class Employee implements Parcelable {

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
     * 公司编码
     */
    private String company = null;

    /**
     * 构造函数
     */
    public Employee() {
    }

    protected Employee(Parcel in) {
        id = in.readString();
        name = in.readString();
        shortCode = in.readString();
        company = in.readString();
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

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

    /**
     * 获取公司编码
     *
     * @return 公司编码
     */
    public String getCompany() {
        return company;
    }

    /**
     * 设置公司编码
     *
     * @param company 公司编码
     */
    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(shortCode);
        dest.writeString(company);
    }
}
