package com.port.tally.management.bean;
/**
 * Created by 超悟空 on 2015/12/18.
 */

/**
 * 作业计划数据结构
 *
 * @author 超悟空
 * @version 1.0 2015/12/18
 * @since 1.0
 */
public class WorkPlan {

    /**
     * 派工编码
     */
    private String dispatchCode = null;

    /**
     * 委托编码
     */
    private String entrustCode = null;

    /**
     * 票货编码
     */
    private String ticketCode = null;

    /**
     * 委托人
     */
    private String entrustName = null;

    /**
     * 任务号
     */
    private String taskNumber = null;

    /**
     * 货物
     */
    private String goods = null;

    /**
     * 作业过程
     */
    private String operation = null;

    /**
     * 计划量
     */
    private String amount = null;

    /**
     * 开始时间
     */
    private String beginTime = null;

    /**
     * 结束时间
     */
    private String endTime = null;

    /**
     * 源载体
     */
    private String sourceCarrier = null;

    /**
     * 目标载体
     */
    private String targetCarrier = null;

    /**
     * 获取派工编码
     *
     * @return 派工编码
     */
    public String getDispatchCode() {
        return dispatchCode;
    }

    /**
     * 设置派工编码
     *
     * @param dispatchCode 派工编码
     */
    public void setDispatchCode(String dispatchCode) {
        this.dispatchCode = dispatchCode;
    }

    /**
     * 获取委托编码
     *
     * @return 委托编码
     */
    public String getEntrustCode() {
        return entrustCode;
    }

    /**
     * 设置委托编码
     *
     * @param entrustCode 委托编码
     */
    public void setEntrustCode(String entrustCode) {
        this.entrustCode = entrustCode;
    }

    /**
     * 获取票货编码
     *
     * @return 票货编码
     */
    public String getTicketCode() {
        return ticketCode;
    }

    /**
     * 设置票货编码
     *
     * @param ticketCode 票货编码
     */
    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    /**
     * 获取委托人
     *
     * @return 委托人
     */
    public String getEntrustName() {
        return entrustName;
    }

    /**
     * 设置委托人
     *
     * @param entrustName 委托人
     */
    public void setEntrustName(String entrustName) {
        this.entrustName = entrustName;
    }

    /**
     * 获取任务号
     *
     * @return 任务号
     */
    public String getTaskNumber() {
        return taskNumber;
    }

    /**
     * 设置任务号
     *
     * @param taskNumber 任务号
     */
    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * 获取货物
     *
     * @return 货物
     */
    public String getGoods() {
        return goods;
    }

    /**
     * 设置货物
     *
     * @param goods 货物
     */
    public void setGoods(String goods) {
        this.goods = goods;
    }

    /**
     * 获取作业过程
     *
     * @return 作业过程
     */
    public String getOperation() {
        return operation;
    }

    /**
     * 设置作业过程
     *
     * @param operation 作业过程
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * 获取计划量
     *
     * @return 计划量
     */
    public String getAmount() {
        return amount;
    }

    /**
     * 设置计划量
     *
     * @param amount 计划量
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * 获取开始时间
     *
     * @return 开始时间
     */
    public String getBeginTime() {
        return beginTime;
    }

    /**
     * 设置开始时间
     *
     * @param beginTime 开始时间
     */
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取结束时间
     *
     * @return 结束时间
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * 设置结束时间
     *
     * @param endTime 结束时间
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取源载体
     *
     * @return 源载体
     */
    public String getSourceCarrier() {
        return sourceCarrier;
    }

    /**
     * 设置源载体
     *
     * @param sourceCarrier 源载体
     */
    public void setSourceCarrier(String sourceCarrier) {
        this.sourceCarrier = sourceCarrier;
    }

    /**
     * 获取目标载体
     *
     * @return 目标载体
     */
    public String getTargetCarrier() {
        return targetCarrier;
    }

    /**
     * 设置目标载体
     *
     * @param targetCarrier 目标载体
     */
    public void setTargetCarrier(String targetCarrier) {
        this.targetCarrier = targetCarrier;
    }
}
