package com.port.tally.management.bean;

/**
 * Created by song on 2015/10/10.
 */
public class WorkPlanDetailBean {
    private String cargoowner;
    private String cargo;
    private String vgdisplay;
    private String client;
    private String storage;
    private String weight;
    private String first_indate;

    public String getInout() {
        return inout;
    }

    public void setInout(String inout) {
        this.inout = inout;
    }

    public String getCargoowner() {
        return cargoowner;
    }

    public void setCargoowner(String cargoowner) {
        this.cargoowner = cargoowner;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getVgdisplay() {
        return vgdisplay;
    }

    public void setVgdisplay(String vgdisplay) {
        this.vgdisplay = vgdisplay;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFirst_indate() {
        return first_indate;
    }

    public void setFirst_indate(String first_indate) {
        this.first_indate = first_indate;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getBooth() {
        return booth;
    }

    public void setBooth(String booth) {
        this.booth = booth;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPieceweight() {
        return pieceweight;
    }

    public void setPieceweight(String pieceweight) {
        this.pieceweight = pieceweight;
    }

    private String inout;
    private String trade;
    private String mark;
    private String booth;
    private String pack;
    private String amount;
    private String pieceweight;
}
