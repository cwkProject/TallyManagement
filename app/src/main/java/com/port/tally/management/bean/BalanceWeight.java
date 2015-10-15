package com.port.tally.management.bean;
/**
 * Created by 超悟空 on 2015/9/19.
 */

/**
 * 衡重数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/10/14
 * @since 1.0
 */
public class BalanceWeight {

    /**
     * 货物
     */
    private String cargo = null;

    /**
     * 船名
     */
    private String ship = null;

    /**
     * 委托号
     */
    private String entrustNumber = null;

    /**
     * 委托人
     */
    private String entrust = null;

    /**
     * 计划重量
     */
    private String planWeight = null;

    /**
     * 当班量
     */
    private String dutyWeight = null;

    /**
     * 公司编码
     */
    private String companyCode = null;

    /**
     * 白夜班
     */
    private String dayNight = null;

    /**
     * 班组日期
     */
    private String date = null;

    /**
     * 累计量
     */
    private String totalWeight = null;

    /**
     * 当班车次
     */
    private String dutyTruckNumber = null;

    /**
     * 累计车次
     */
    private String totalTruckNumber = null;

    /**
     * 提单号
     */
    private String billNumber = null;

    /**
     * 公司
     */
    private String company = null;

    /**
     * 获取货物
     *
     * @return 货物
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * 设置货物
     *
     * @param cargo 货物
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * 获取船名
     *
     * @return 船名
     */
    public String getShip() {
        return ship;
    }

    /**
     * 设置船名
     *
     * @param ship 船名
     */
    public void setShip(String ship) {
        this.ship = ship;
    }

    /**
     * 获取委托号
     *
     * @return 委托号
     */
    public String getEntrustNumber() {
        return entrustNumber;
    }

    /**
     * 设置委托号
     *
     * @param entrustNumber 委托号
     */
    public void setEntrustNumber(String entrustNumber) {
        this.entrustNumber = entrustNumber;
    }

    /**
     * 获取计划量
     *
     * @return 计划量
     */
    public String getPlanWeight() {
        return planWeight;
    }

    /**
     * 设置计划量
     *
     * @param planWeight 计划量
     */
    public void setPlanWeight(String planWeight) {
        this.planWeight = planWeight;
    }

    /**
     * 获取当班量
     *
     * @return 当班量
     */
    public String getDutyWeight() {
        return dutyWeight;
    }

    /**
     * 设置当班量
     *
     * @param dutyWeight 当班量
     */
    public void setDutyWeight(String dutyWeight) {
        this.dutyWeight = dutyWeight;
    }

    /**
     * 获取当班日期
     *
     * @return 当班日期
     */
    public String getDate() {
        return date;
    }

    /**
     * 设置当班日期
     *
     * @param date 当班日期
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 获取公司
     *
     * @return 公司
     */
    public String getCompany() {
        return company;
    }

    /**
     * 设置公司
     *
     * @param company 公司
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * 获取委托人
     *
     * @return 委托人
     */
    public String getEntrust() {
        return entrust;
    }

    /**
     * 设置委托人
     *
     * @param entrust 委托人
     */
    public void setEntrust(String entrust) {
        this.entrust = entrust;
    }

    /**
     * 获取公司编码
     *
     * @return 公司编码
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * 设置公司编码
     *
     * @param companyCode 公司编码
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    /**
     * 获取白夜班
     *
     * @return 白夜班
     */
    public String getDayNight() {
        return dayNight;
    }

    /**
     * 设置白夜班
     *
     * @param dayNight 白夜班
     */
    public void setDayNight(String dayNight) {
        this.dayNight = dayNight;
    }

    /**
     * 获取累计量
     *
     * @return 累计量
     */
    public String getTotalWeight() {
        return totalWeight;
    }

    /**
     * 设置累计量
     *
     * @param totalWeight 累计量
     */
    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    /**
     * 获取当班车次
     *
     * @return 当班车次
     */
    public String getDutyTruckNumber() {
        return dutyTruckNumber;
    }

    /**
     * 设置当班车次
     *
     * @param dutyTruckNumber 当班车次
     */
    public void setDutyTruckNumber(String dutyTruckNumber) {
        this.dutyTruckNumber = dutyTruckNumber;
    }

    /**
     * 获取累计车次
     *
     * @return 累计车次
     */
    public String getTotalTruckNumber() {
        return totalTruckNumber;
    }

    /**
     * 设置累计车次
     *
     * @param totalTruckNumber 累计车次
     */
    public void setTotalTruckNumber(String totalTruckNumber) {
        this.totalTruckNumber = totalTruckNumber;
    }

    /**
     * 获取提单号
     *
     * @return 提单号
     */
    public String getBillNumber() {
        return billNumber;
    }

    /**
     * 设置提单号
     *
     * @param billNumber 提单号
     */
    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }
}
