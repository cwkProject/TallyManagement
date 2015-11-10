package com.port.tally.management.data;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.model.data.base.JsonDataModel;

import java.util.Map;

/**
 * Created by song on 2015/11/8.
 */
public class TallySaveData extends JsonDataModel {
    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "TallySaveData.";
    /**
     * 服务请求传入参数
     */
    //公司编码
    private  String CodeCompany= null;
    //部门编码
    private String CodeDepartment= null;
    //委托编码
    private String Cgno= null;
    //派工编码
    private String Pmno= null;
    //用户编码
    private String CodeTallyman= null;
    //用户姓名
    private String Tallyman= null;

    //源航次编码
    private String Vgno= null;
    //源仓别
    private String Cabin= null;
    //源车别代码
    private String CodeCarrier= null;
    //源车号
    private String CarrierNum= null;
    //源驳船船舶规范编码
    private String CodeNvessel= null;
    //源驳船属性
    private String Bargepro= null;
    //源场地编码
    private String CodeStorage= null;
    //源货位编码
    private String CodeBooth= null;
    //源桩角编码
    private String CodeAllocation= null;
    //源载体描述
    private String Carrier1= null;
    //源载体属性
    private String Carrier1Num= null;
    //目的航次编码
    private String VgnoLast= null;
    //目的仓别
    private String CabinLast= null;
    //目的车别代码
    private String CodeCarrierLast= null;
    //目的车号
    private String CarrierNumLast= null;
    //目的驳船船舶规范编码
    private String CodeNvesselLast= null;
    //目的驳船属性
    private String BargeproLast= null;
    //目的场地编码
    private String CodeStorageLast= null;
    //目的货位编码
    private String CodeBoothLast= null;
    //目的桩角编码
    private String CodeAllocationLast= null;
    //目的载体描述
    private String Carrier2= null;
    //目的载体属性
    private String Carrier2num= null;
    //票货编码
    private String CodeGoodsBill= null;
    //票货描述
    private String GoodsBillDisplay= null;
    //商务票货编码
    private String CodeGbBusiness= null;

    public void setGbBusinessDisplay(String gbBusinessDisplay) {
        GbBusinessDisplay = gbBusinessDisplay;
    }

    public void setCodeCompany(String codeCompany) {
        CodeCompany = codeCompany;
    }

    public void setCodeDepartment(String codeDepartment) {
        CodeDepartment = codeDepartment;
    }

    public void setCgno(String cgno) {
        Cgno = cgno;
    }

    public void setPmno(String pmno) {
        Pmno = pmno;
    }

    public void setCodeTallyman(String codeTallyman) {
        CodeTallyman = codeTallyman;
    }

    public void setTallyman(String tallyman) {
        Tallyman = tallyman;
    }

    public void setVgno(String vgno) {
        Vgno = vgno;
    }

    public void setCabin(String cabin) {
        Cabin = cabin;
    }

    public void setCodeCarrier(String codeCarrier) {
        CodeCarrier = codeCarrier;
    }

    public void setCarrierNum(String carrierNum) {
        CarrierNum = carrierNum;
    }

    public void setCodeNvessel(String codeNvessel) {
        CodeNvessel = codeNvessel;
    }

    public void setBargepro(String bargepro) {
        Bargepro = bargepro;
    }

    public void setCodeStorage(String codeStorage) {
        CodeStorage = codeStorage;
    }

    public void setCodeBooth(String codeBooth) {
        CodeBooth = codeBooth;
    }

    public void setCodeAllocation(String codeAllocation) {
        CodeAllocation = codeAllocation;
    }

    public void setCarrier1(String carrier1) {
        Carrier1 = carrier1;
    }

    public void setCarrier1Num(String carrier1Num) {
        Carrier1Num = carrier1Num;
    }

    public void setVgnoLast(String vgnoLast) {
        VgnoLast = vgnoLast;
    }

    public void setCabinLast(String cabinLast) {
        CabinLast = cabinLast;
    }

    public void setCodeCarrierLast(String codeCarrierLast) {
        CodeCarrierLast = codeCarrierLast;
    }

    public void setCarrierNumLast(String carrierNumLast) {
        CarrierNumLast = carrierNumLast;
    }

    public void setCodeNvesselLast(String codeNvesselLast) {
        CodeNvesselLast = codeNvesselLast;
    }

    public void setBargeproLast(String bargeproLast) {
        BargeproLast = bargeproLast;
    }

    public void setCodeStorageLast(String codeStorageLast) {
        CodeStorageLast = codeStorageLast;
    }

    public void setCodeBoothLast(String codeBoothLast) {
        CodeBoothLast = codeBoothLast;
    }

    public void setCodeAllocationLast(String codeAllocationLast) {
        CodeAllocationLast = codeAllocationLast;
    }

    public void setCarrier2(String carrier2) {
        Carrier2 = carrier2;
    }

    public void setCarrier2num(String carrier2num) {
        Carrier2num = carrier2num;
    }

    public void setCodeGoodsBill(String codeGoodsBill) {
        CodeGoodsBill = codeGoodsBill;
    }

    public void setGoodsBillDisplay(String goodsBillDisplay) {
        GoodsBillDisplay = goodsBillDisplay;
    }

    public void setCodeGbBusiness(String codeGbBusiness) {
        CodeGbBusiness = codeGbBusiness;
    }

    public void setCodeSpecialMark(String codeSpecialMark) {
        CodeSpecialMark = codeSpecialMark;
    }

    public void setCodeWorkingArea(String codeWorkingArea) {
        CodeWorkingArea = codeWorkingArea;
    }

    public void setCodeWorkingAreaLast(String codeWorkingAreaLast) {
        CodeWorkingAreaLast = codeWorkingAreaLast;
    }

    public void setQuality(String quality) {
        Quality = quality;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public void setCount(String count) {
        Count = count;
    }

    public void setAmount2(String amount2) {
        Amount2 = amount2;
    }

    //商务票货描述
    private String GbBusinessDisplay= null;
    //子过程特殊标志编码
    private String CodeSpecialMark= null;
    //源区域编码
    private String CodeWorkingArea= null;
    //目的区域编码
    private String CodeWorkingAreaLast= null;
    //质量
    private String Quality= null;
    //件数1
    private String Amount= null;
    //重量1
    private String Weight= null;
    //数量1
    private String Count= null;
    //件数2
    private String Amount2= null;

    private String Weight2= null;

    private String Count2= null;

    public void setWeight2(String weight2) {
        Weight2 = weight2;
    }

    public void setCount2(String count2) {
        Count2 = count2;
    }

    public void setMachine(String machine) {
        Machine = machine;
    }

    public void setWorkTeam(String workTeam) {
        WorkTeam = workTeam;
    }

    private String Machine= null;

    private String WorkTeam= null;
    @Override
    protected void onFillRequestParameters(Map<String, String> map) {
        // 传入请求参数
        map.put("CodeCompany",CodeCompany);
        map.put("CodeDepartment",CodeDepartment);
        map.put("Cgno", Cgno);
        map.put("Pmno",Pmno);
        map.put("CodeTallyman", CodeTallyman);
        map.put("Tallyman", Tallyman);
        map.put("Vgno",Vgno);
        map.put("Cabin",Cabin);
        map.put("CodeCarrier", CodeCarrier);
        map.put("CarrierNum",CarrierNum);
        map.put("CodeNvessel",CodeNvessel);
        map.put("Bargepro",Bargepro);
        map.put("CodeStorage",CodeStorage);
        map.put("CodeBooth", CodeBooth);
        map.put("CodeAllocation",CodeAllocation);
        map.put("Carrier1",Carrier1);
        map.put("Carrier1Num",Carrier1Num);
        map.put("VgnoLast",VgnoLast);
        map.put("CabinLast",CabinLast);
        map.put("CodeCarrierLast",CodeCarrierLast);
        map.put("CarrierNumLast",CarrierNumLast);
        map.put("CodeNvesselLast",CodeNvesselLast);
        map.put("BargeproLast",BargeproLast);
        map.put("CodeStorageLast",CodeStorageLast);
        map.put("CodeBoothLast", CodeBoothLast);
        map.put("CodeAllocationLast",CodeAllocationLast);
        map.put("Carrier2",Carrier2);
        map.put("Carrier2num",Carrier2num);
        map.put("CodeGoodsBill",CodeGoodsBill);
        map.put("GoodsBillDisplay",GoodsBillDisplay);
        map.put("CodeGbBusiness",CodeGbBusiness);
        map.put("GbBusinessDisplay",GbBusinessDisplay);
        map.put("CodeSpecialMark",CodeSpecialMark);
        map.put("CodeGoodsBill",CodeGoodsBill);
        map.put("CodeWorkingArea",CodeWorkingArea);
        map.put("CodeWorkingAreaLast",CodeWorkingAreaLast);
        map.put("Quality",Quality);
        map.put("Amount",Amount);
        map.put("Weight",Weight);
        map.put("Count",Count);
        map.put("Amount2",Amount2);
        map.put("Weight2",Weight2);
        map.put("Count2",Count2);
        map.put("Machine",Machine);
        map.put("WorkTeam",WorkTeam);
        Log.i("TallySaveData的map值",""+map);


    }

    /**
     * 提取服务执行结果
     *
     * @param jsonObject Json结果集
     *
     * @return 服务请求结果，true表示请求成功，false表示请求失败
     *
     * @throws JSONException 解析过程中出现错误
     */
    @Override
    protected boolean onRequestResult(JSONObject jsonObject) throws JSONException {
        // 得到执行结果
        String resultState = jsonObject.getString("IsSuccess");
        return resultState != null && "yes".equals(resultState.trim().toLowerCase());

    }
    /**
     * 提取服务返回的结果消息，
     * 在{@link #onRequestResult(JSONObject)}之后被调用
     *
     * @param b     服务请求执行结果，
     *                   即{@link #onRequestResult(JSONObject)}返回值
     * @param jsonObject Json结果集
     *
     * @return 消息字符串
     *
     * @throws JSONException 解析过程中出现错误
     */
    @Override
    protected String onRequestMessage(boolean b, JSONObject jsonObject) throws JSONException {
        return jsonObject.getString("Message");
    }
    /**
     * 提取服务反馈的结果数据，
     * 在服务请求成功后调用，
     * 即{@link #onRequestResult(JSONObject)}返回值为true时，
     * 在{@link #onRequestMessage(boolean , JSONObject)}之后被调用，
     * @param jsonResult Json结果集
     * @throws JSONException 解析过程中出现错误
     */
    @Override
    protected void onRequestSuccess(JSONObject jsonResult) throws JSONException {
        Object object=jsonResult.get("Data");

    }

    @Override
    protected void onRequestFailed(JSONObject jsonResult) throws JSONException {


    }
}

