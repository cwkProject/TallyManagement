package com.port.tally.management.bean;

/**
 * Created by song on 2015/9/24.
 */
public class TrunkQueryBean {
    private String vehicleNum =null;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    public String getCagro() {
        return cagro;
    }

    public void setCagro(String cagro) {
        this.cagro = cagro;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getGaterecordid() {
        return gaterecordid;
    }

    public void setGaterecordid(String gaterecordid) {
        this.gaterecordid = gaterecordid;
    }

    private String client =null;
    private String cagro = null;

    public String getWorkteam() {
        return workteam;
    }

    public void setWorkteam(String workteam) {
        this.workteam = workteam;
    }

    private String workteam = null;
    private String amount = null;
    private String gaterecordid = null;
    private String s=null;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
