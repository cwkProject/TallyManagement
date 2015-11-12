package com.port.tally.management.work;

import android.util.Log;

import com.port.tally.management.bean.StartWorkBean;
import com.port.tally.management.data.EndWorkData;
import com.port.tally.management.data.TallySaveData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;
import org.mobile.library.network.factory.NetworkType;

/**
 * Created by song on 2015/11/8.
 */
public class TallySaveWork extends DefaultWorkModel<String, String,TallySaveData> {

    /**
     * 参数合法性检测，
     * 用于检测传入参数是否合法，
     * 需要子类重写检测规则，
     *
     * @param parameters 传入参数
     *
     * @return 检测结果，合法返回true，非法返回false
     */

    protected boolean onCheckParameters(String... parameters) {
        // 需要至少两个传入参数
        return !(parameters == null || parameters.length < 1);
    }

    /**
     * 设置任务请求地址
     *
     * @return 地址字符串
     */
    @Override
    protected String onTaskUri() {
        return StaticValue.HTTP_POST_TALLYSAVE_URL;
    }

    /**
     * 服务返回数据解析成功后，
     * 并且服务执行为成功即{@link org.mobile.library.model.data.IDefaultDataModel#isSuccess()}返回true时，
     * 设置任务返回数据，
     * 即设置{@link #setResult(Object)}的参数。
     * 该方法在{@link #onParseSuccess(org.mobile.library.model.data.IDefaultDataModel)}之后调用
     *
     * @param data 解析后的数据模型对象
     *
     * @return 任务返回数据
     */
    @Override
    protected String onRequestSuccessSetResult(TallySaveData data) {
        return data.getObjectdata();
    }

    /**
     * 服务返回数据解析成功后，
     * 但是服务执行为失败即{@link org.mobile.library.model.data.IDefaultDataModel#isSuccess()}返回false时，
     * 设置任务返回数据，
     * 即设置{@link #setResult(Object)}的参数。
     * 该方法在{@link #onParseSuccess(org.mobile.library.model.data.IDefaultDataModel)}之后调用
     *
     * @param data 解析后的数据模型对象
     *
     * @return 任务返回数据
     */
    @Override
    protected String onRequestFailedSetResult(TallySaveData data) {
        return data.getObjectdata();
    }

    /**
     * 创建数据模型对象并填充参数，
     * {@link #onCheckParameters(String...)}检测成功后调用
     *
     * @param parameters 传入参数
     *
     * @return 参数设置完毕后的数据模型对象
     */
    @Override
    protected TallySaveData onCreateDataModel(String... parameters) {
        TallySaveData tallySaveData = new TallySaveData();
        Log.i("TallySaveWork", "" + parameters.length);

        tallySaveData.setCodeCompany(parameters[0]);
        tallySaveData.setCodeDepartment(parameters[1]);
        tallySaveData.setCgno(parameters[2]);
        tallySaveData.setPmno(parameters[3]);
        tallySaveData.setCodeTallyman(parameters[4]);
        tallySaveData.setTallyman(parameters[5]);
        tallySaveData.setVgno(parameters[6]);
        tallySaveData.setCabin(parameters[7]);
        tallySaveData.setCodeCarrier(parameters[8]);
        tallySaveData.setCarrierNum(parameters[9]);
        tallySaveData.setCodeNvessel(parameters[10]);
        tallySaveData.setBargepro(parameters[11]);
        tallySaveData.setCodeStorage(parameters[12]);
        tallySaveData.setCodeBooth(parameters[13]);
        tallySaveData.setCodeAllocation(parameters[14]);
        tallySaveData.setCarrier1(parameters[15]);
        tallySaveData.setCarrier1Num(parameters[16]);
        tallySaveData.setVgnoLast(parameters[17]);
        tallySaveData.setCabinLast(parameters[18]);
        tallySaveData.setCodeCarrierLast(parameters[19]);
        tallySaveData.setCarrierNumLast(parameters[20]);
        tallySaveData.setCodeNvesselLast(parameters[21]);
        tallySaveData.setBargeproLast(parameters[22]);
        tallySaveData.setCodeStorageLast(parameters[23]);
        tallySaveData.setCodeBoothLast(parameters[24]);
        tallySaveData.setCodeAllocationLast(parameters[25]);
        tallySaveData.setCarrier2(parameters[26]);
        tallySaveData.setCarrier2num(parameters[27]);
        tallySaveData.setCodeGoodsBill(parameters[28]);
        tallySaveData.setGoodsBillDisplay(parameters[29]);
        tallySaveData.setCodeGbBusiness(parameters[30]);
        tallySaveData.setGbBusinessDisplay(parameters[31]);
        tallySaveData.setCodeSpecialMark(parameters[32]);
        tallySaveData.setCodeWorkingArea(parameters[33]);
        tallySaveData.setCodeWorkingAreaLast(parameters[34]);
        tallySaveData.setQuality(parameters[35]);
        tallySaveData.setAmount(parameters[36]);
        tallySaveData.setWeight(parameters[37]);
        tallySaveData.setCount(parameters[38]);
        tallySaveData.setAmount2(parameters[39]);
        tallySaveData.setWeight2(parameters[40]);
        tallySaveData.setCount2(parameters[41]);
        tallySaveData.setMachine(parameters[42]);
        tallySaveData.setWorkTeam(parameters[43]);
        tallySaveData.setTrainNum(parameters[44]);
        tallySaveData.setTbno(parameters[45]);
        tallySaveData.setMarkFinish(parameters[46]);
        Log.i("TallySaveWork", "" + tallySaveData.toString());
        Log.i("TallySaveWork", "" +tallySaveData);
        return tallySaveData;
    }

    @Override
    protected NetworkType onNetworkType() {
        return NetworkType.HTTP_POST;
    }
}


