package com.port.tally.management.bean;
/**
 * Created by 超悟空 on 2015/9/19.
 */

/**
 * 委托数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/9/19
 * @since 1.0
 */
public class Entrust {

    /**
     * 委托编码
     */
    private String id = null;

    /**
     * 货主
     */
    private String cargoOwner = null;

    /**
     * 货物
     */
    private String cargo = null;

    /**
     * 航次
     */
    private String voyage = null;

    /**
     * 作业过程
     */
    private String work = null;

    /**
     * 委托号
     */
    private String entrustNumber = null;

    /**
     * 计划数量
     */
    private String planAmount = null;

    /**
     * 计划重量
     */
    private String planWeight = null;

    /**
     * 实际数量
     */
    private String factAmount = null;

    /**
     * 实际重量
     */
    private String factWeight = null;

    /**
     * 日期
     */
    private String date = null;

    /**
     * 卸船航次
     */
    private String unloadVoyage = null;

    /**
     * 装船航次
     */
    private String loadVoyage = null;

    /**
     * 进出
     */
    private String inout = null;

    /**
     * 贸易类别
     */
    private String trade = null;

    /**
     * 包装
     */
    private String pack = null;

    /**
     * 标识
     */
    private String mark = null;

    /**
     * 公司
     */
    private String company = null;

    /**
     * 获取唯一委托编码
     *
     * @return 委托编码
     */
    public String getId() {
        return id;
    }

    /**
     * 设置唯一委托编码
     *
     * @param id 委托编码
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取货主
     *
     * @return 货主
     */
    public String getCargoOwner() {
        return cargoOwner;
    }

    /**
     * 设置货主
     *
     * @param cargoOwner 货主
     */
    public void setCargoOwner(String cargoOwner) {
        this.cargoOwner = cargoOwner;
    }

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
     * 获取航次
     *
     * @return 航次
     */
    public String getVoyage() {
        return voyage;
    }

    /**
     * 设置航次
     *
     * @param voyage 航次
     */
    public void setVoyage(String voyage) {
        this.voyage = voyage;
    }

    /**
     * 获取作业过程
     *
     * @return 作业过程
     */
    public String getWork() {
        return work;
    }

    /**
     * 设置作业过程
     *
     * @param work 作业过程
     */
    public void setWork(String work) {
        this.work = work;
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
     * 获取计划数量
     *
     * @return 计划数量
     */
    public String getPlanAmount() {
        return planAmount;
    }

    /**
     * 设置计划数量
     *
     * @param planAmount 计划数量
     */
    public void setPlanAmount(String planAmount) {
        this.planAmount = planAmount;
    }

    /**
     * 获取计划重量
     *
     * @return 计划重量
     */
    public String getPlanWeight() {
        return planWeight;
    }

    /**
     * 设置计划重量
     *
     * @param planWeight 计划重量
     */
    public void setPlanWeight(String planWeight) {
        this.planWeight = planWeight;
    }

    /**
     * 获取实际数量
     *
     * @return 实际数量
     */
    public String getFactAmount() {
        return factAmount;
    }

    /**
     * 设置实际数量
     *
     * @param factAmount 实际数量
     */
    public void setFactAmount(String factAmount) {
        this.factAmount = factAmount;
    }

    /**
     * 获取实际重量
     *
     * @return 实际重量
     */
    public String getFactWeight() {
        return factWeight;
    }

    /**
     * 设置实际重量
     *
     * @param factWeight 实际重量
     */
    public void setFactWeight(String factWeight) {
        this.factWeight = factWeight;
    }

    /**
     * 获取日期
     *
     * @return 日期
     */
    public String getDate() {
        return date;
    }

    /**
     * 设置日期
     *
     * @param date 日期
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 获取卸船航次
     *
     * @return 卸船航次
     */
    public String getUnloadVoyage() {
        return unloadVoyage;
    }

    /**
     * 设置卸船航次
     *
     * @param unloadVoyage 卸船航次
     */
    public void setUnloadVoyage(String unloadVoyage) {
        this.unloadVoyage = unloadVoyage;
    }

    /**
     * 获取装船航次
     *
     * @return 装船航次
     */
    public String getLoadVoyage() {
        return loadVoyage;
    }

    /**
     * 设置装船航次
     *
     * @param loadVoyage 装船航次
     */
    public void setLoadVoyage(String loadVoyage) {
        this.loadVoyage = loadVoyage;
    }

    /**
     * 获取进出
     *
     * @return 进出
     */
    public String getInout() {
        return inout;
    }

    /**
     * 设置进出
     *
     * @param inout 进出
     */
    public void setInout(String inout) {
        this.inout = inout;
    }

    /**
     * 获取贸易类别
     *
     * @return 贸易类别
     */
    public String getTrade() {
        return trade;
    }

    /**
     * 设置贸易类别
     *
     * @param trade 贸易类别
     */
    public void setTrade(String trade) {
        this.trade = trade;
    }

    /**
     * 获取包装
     *
     * @return 包装
     */
    public String getPack() {
        return pack;
    }

    /**
     * 设置包装
     *
     * @param pack 包装
     */
    public void setPack(String pack) {
        this.pack = pack;
    }

    /**
     * 获取标识
     *
     * @return 标识
     */
    public String getMark() {
        return mark;
    }

    /**
     * 设置标识
     *
     * @param mark 标识
     */
    public void setMark(String mark) {
        this.mark = mark;
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
}
