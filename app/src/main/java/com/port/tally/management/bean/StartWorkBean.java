package com.port.tally.management.bean;

/**
 * Created by song on 2015/10/1.
 */
public class StartWorkBean {
    private  String vehicleNum;//车号
    private String boatName; //船名
    private String forwarder; //货代
    private  String cargo;//货物

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private  String id;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private  String message;
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getForwarder() {
        return forwarder;
    }

    public void setForwarder(String forwarder) {
        this.forwarder = forwarder;
    }

    public String getBoatName() {
        return boatName;
    }

    public void setBoatName(String boatName) {
        this.boatName = boatName;
    }

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    public String getAllocation() {
        return allocation;
    }

    public void setAllocation(String allocation) {
        this.allocation = allocation;
    }

    public String getSetport() {
        return setport;
    }

    public void setSetport(String setport) {
        this.setport = setport;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getNotePerson() {
        return notePerson;
    }

    public void setNotePerson(String notePerson) {
        this.notePerson = notePerson;
    }

    private String place;//场地
    private String allocation;//货位
    private String setport;//集疏港
    private String loader;//装卸车

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getLoader() {
        return loader;
    }

    public void setLoader(String loader) {
        this.loader = loader;
    }

    private String task;//任务号
    private String startTime;//开始时间
    private String notePerson;//记录人
    private String cardNo;//通行证号

    public String getStrSubmittime() {
        return strSubmittime;
    }

    public void setStrSubmittime(String strSubmittime) {
        this.strSubmittime = strSubmittime;
    }

    public String getStrWeight() {
        return strWeight;
    }

    public void setStrWeight(String strWeight) {
        this.strWeight = strWeight;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    private String strSubmittime;//申报时间
    private String strWeight;//衡重
}
