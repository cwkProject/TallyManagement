package com.port.tally.management.bean;

/**
 * Created by song on 2015/10/1.
 */
public class EndWorkBean {
    private String takeNote;//记录
    private  String vehicleNum;//车号
    private String boatName; //船名
    private String forwarder; //货代

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    public String getBoatName() {
        return boatName;
    }

    public void setBoatName(String boatName) {
        this.boatName = boatName;
    }

    public String getForwarder() {
        return forwarder;
    }

    public void setForwarder(String forwarder) {
        this.forwarder = forwarder;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    private  String cargo;//货物
    private String place;//场地
    private String allocation;//货位
    private String setport;//集疏港
    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOverWork() {
        return overWork;
    }

    public void setOverWork(String overWork) {
        this.overWork = overWork;
    }

    public String getWorkTeam() {
        return workTeam;
    }

    public void setWorkTeam(String workTeam) {
        this.workTeam = workTeam;
    }

    public String getTakeNote() {
        return takeNote;
    }

    public void setTakeNote(String takeNote) {
        this.takeNote = takeNote;
    }

    private String workTeam; //班组
    private  String count; //件数
    private String endTime; //结束时间
    private String overWork;//完工
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
}
