package com.port.tally.management.util;
/**
 * Created by 超悟空 on 2015/9/7.
 */

/**
 * 存放应用使用的全局静态常量
 *
 * @author 超悟空
 * @version 1.0 2015/9/7
 * @since 1.0
 */
public interface StaticValue {
    /**
     * 本应用编号
     */
    String APP_CODE = "LHGL";

    /**
     * 意图数据传递标签
     */
    interface IntentTag {
        /**
         * 委托编码取值标签
         */
        String ENTRUST_ID_TAG = "entrust_id_tag";
    }

    /**
     * 数据列表工具标签
     */
    interface CodeListTag {
        /**
         * 货物类别
         */
        String CARGO_TYPE_LIST = "cargo_type_list";

        /**
         * 货主
         */
        String CARGO_OWNER_LIST = "cargo_owner_list";

        /**
         * 航次
         */
        String VOYAGE_LIST = "voyage_list";

        /**
         * 作业过程
         */
        String OPERATION_LIST = "operation_list";

    }
    String HTTP_GET_SAMPLE_URL = "http://168.100.1.218/wlkg/Service/";
    /**
     * 派工计划模块ip
     */
    //派工计划第一页IP
    String HTTP_GET_TASKONE_URL = "http://218.92.115.55/M_Lhgl/Service/Slip/GetMissionPlan.aspx";
    //详细派工页
    String HTTP_GET_TASKDETAIL_URL = "http://218.92.115.55/M_Lhgl/Service/Slip/GetMissionPlanDetail.aspx";
    //派工获取委托查询数据
    String HTTP_GET_ENTRUST_URL = "http://218.92.115.55/M_Lhgl/Service/Slip/GetConsign.aspx";
    //获取子过程标志列表数据
    String HTTP_GET_SUBPROCESS_URL = "http://218.92.115.55/M_Lhgl/Service/Base/GetSpecialMark.aspx";
    //获取票货数据
    String HTTP_GET_SHIPMENTDATA_URL = "http://218.92.115.55/M_Lhgl/Service/Slip/GetGoogsBill.aspx";
    //获取作业数据列表数据
    String HTTP_GET_OPERATDATA_URL = "http://218.92.115.55/M_Lhgl/Service/Base/GetWorkingArea.aspx";
    //汽运查询地址
    String HTTP_GET_TRUNKQUERY_URL ="http://218.92.115.55/M_Lhgl/Service/Vehicle/GetVehicleTransport.aspx";
    //汽运作业地址
    String HTTP_GET_TRUNKWORK_URL ="http://218.92.115.55/M_Lhgl/Service/Vehicle/GetStartWork.aspx";
    /**
     * 获取货主列表请求地址
     */
    String CARGO_OWNER_LIST_URL = "http://218.92.115.55/M_Lhgl/Service/Base/GetCargoOwner.aspx";

    /**
     * 获取航次列表请求地址
     */
    String VOYAGE_LIST_URL = "http://218.92.115.55/M_Lhgl/Service/Base/GetVoyage.aspx";

    /**
     * 获取作业过程请求地址
     */
    String OPERATION_LIST_URL = "http://218.92.115.55/M_Lhgl/Service/Base/GetOperation.aspx";

    /**
     * 获取货物种类列表的请求地址
     */
    String CARGO_TYPE_LIST_URL = "http://218.92.115.55/M_Lhgl/Service/Base/GetCargo.aspx";

    /**
     * 查询委托列表的请求地址
     */
    String ENTRUST_QUERY_LIST_URL = "http://218.92.115.55/M_Lhgl/Service/Consign/GetConsign.aspx";

    /**
     * 获取委托详情的请求地址
     */
    String ENTRUST_CONTENT_URL = "http://218.92.115.55/M_Lhgl/Service/Consign/GetConsignDetail" +
            ".aspx";
    //汽运作业班组请求地址
    String HTTP_GET_TRUNKWORKTEAM_URL ="http://218.92.115.55/M_Lhgl/Service/Base/GetWorkTeam.aspx";
    //地图数据请求地址
    String HTTP_GET_MAP_URL ="http://218.92.115.55/M_Lhgl/Service/Map/GetMassCoord.aspx";
    //   开工
    String HTTP_GET_STARTWORK_URL ="http://218.92.115.55/M_Lhgl/Service/Vehicle/GetWork.aspx";
    //   完工
    String HTTP_GET_ENDWORK_URL ="http://218.92.115.55/M_Lhgl/Service/Vehicle/GetWork.aspx";
    //提交开工时间
    String HTTP_GET_UPDATSTART_URL ="http://218.92.115.55/M_Lhgl/Service/Vehicle/GetStartWork.aspx";
    //提交完工时间
    String HTTP_GET_UPDATEND_URL ="http://218.92.115.55/M_Lhgl/Service/Vehicle/GetStartWork.aspx";
}
