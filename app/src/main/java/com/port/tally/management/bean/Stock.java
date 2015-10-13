package com.port.tally.management.bean;
/**
 * Created by 超悟空 on 2015/9/19.
 */

/**
 * 堆存数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/9/19
 * @since 1.0
 */
public class Stock {

    /**
     * 票货编码
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
     * 货代
     */
    private String forwarder = null;

    /**
     * 货场
     */
    private String storage = null;

    /**
     * 重量
     */
    private String weight = null;

    /**
     * 件数
     */
    private String pieceAmount = null;

    /**
     * 件重
     */
    private String pieceWeight = null;

    /**
     * 进场日期
     */
    private String inDate = null;

    /**
     * 进出口
     */
    private String inout = null;

    /**
     * 内外贸
     */
    private String trade = null;

    /**
     * 包装
     */
    private String pack = null;

    /**
     * 唛头
     */
    private String mark = null;

    /**
     * 货位
     */
    private String position = null;

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
     * 获取重量
     *
     * @return 重量
     */
    public String getWeight() {
        return weight;
    }

    /**
     * 设置重量
     *
     * @param weight 重量
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

    /**
     * 获取件数
     *
     * @return 件数
     */
    public String getPieceAmount() {
        return pieceAmount;
    }

    /**
     * 设置件数
     *
     * @param pieceAmount 件数
     */
    public void setPieceAmount(String pieceAmount) {
        this.pieceAmount = pieceAmount;
    }

    /**
     * 获取件重
     *
     * @return 实际件重
     */
    public String getPieceWeight() {
        return pieceWeight;
    }

    /**
     * 设置件重
     *
     * @param pieceWeight 件重
     */
    public void setPieceWeight(String pieceWeight) {
        this.pieceWeight = pieceWeight;
    }

    /**
     * 获取进港日期
     *
     * @return 进港日期
     */
    public String getInDate() {
        return inDate;
    }

    /**
     * 设置进港日期
     *
     * @param inDate 进港日期
     */
    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    /**
     * 获取进出口
     *
     * @return 进出口
     */
    public String getInout() {
        return inout;
    }

    /**
     * 设置进出口
     *
     * @param inout 进出口
     */
    public void setInout(String inout) {
        this.inout = inout;
    }

    /**
     * 获取内外贸
     *
     * @return 内外贸
     */
    public String getTrade() {
        return trade;
    }

    /**
     * 设置内外贸
     *
     * @param trade 内外贸
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
     * 获取唛头
     *
     * @return 唛头
     */
    public String getMark() {
        return mark;
    }

    /**
     * 设置唛头
     *
     * @param mark 唛头
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

    /**
     * 获取货代
     *
     * @return 货代
     */
    public String getForwarder() {
        return forwarder;
    }

    /**
     * 设置货代
     *
     * @param forwarder 货代
     */
    public void setForwarder(String forwarder) {
        this.forwarder = forwarder;
    }

    /**
     * 获取货场
     *
     * @return 货场
     */
    public String getStorage() {
        return storage;
    }

    /**
     * 设置货场
     *
     * @param storage 货场
     */
    public void setStorage(String storage) {
        this.storage = storage;
    }

    /**
     * 获取货位
     *
     * @return 货位
     */
    public String getPosition() {
        return position;
    }

    /**
     * 设置货位
     *
     * @param position 货位
     */
    public void setPosition(String position) {
        this.position = position;
    }
}
