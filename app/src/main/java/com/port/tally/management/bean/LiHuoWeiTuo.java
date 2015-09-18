package com.port.tally.management.bean;

public class LiHuoWeiTuo {

    /**
     * @param args
     */
    private String weituo1;

    public String getWeituo1() {
        return weituo1;
    }

    public void setWeituo1(String weituo1) {
        this.weituo1 = weituo1;
    }

    public String getWeituo2() {
        return weituo2;
    }

    public void setWeituo2(String weituo2) {
        this.weituo2 = weituo2;
    }

    private String weituo2;

    public LiHuoWeiTuo(String weituo1, String weituo2) {
        super();
        this.weituo1 = weituo1;
        this.weituo2 = weituo2;
    }

    //理货
    private String huoming;

    public String getHuoming() {
        return huoming;
    }

    public void setHuoming(String huoming) {
        this.huoming = huoming;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getGuocheng() {
        return guocheng;
    }

    public void setGuocheng(String guocheng) {
        this.guocheng = guocheng;
    }

    private String start;
    private String end;
    private String guocheng;

    public LiHuoWeiTuo(String huoming, String start, String end, String guocheng) {
        super();
        this.huoming = huoming;
        this.start = start;
        this.end = end;
        this.guocheng = guocheng;

    }


}
