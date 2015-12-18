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
     * 意图数据传递标签
     */
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

        /**
         * 公司编码取值标签
         */
        String COMPANY_CODE_TAG = "company_code_tag";

        /**
         * 日期取值标签
         */
        String DATE_TAG = "date_tag";

        /**
         * 白夜班取值标签
         */
        String DAY_NIGHT_TAG = "day_night_tag";

        /**
         * 堆存编码取值标签
         */
        String STOCK_ID_TAG = "stock_id_tag";

        /**
         * 货场编码取值标签
         */
        String STORAGE_CODE_TAG = "storage_code_tag";

        /**
         * 货位编码取值标签
         */
        String POSITION_CODE_TAG = "position_code_tag";
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

        /**
         * 货代
         */
        String FORWARDER_LIST = "forwarder_list";

        /**
         * 货场
         */
        String STORAGE_LIST = "storage_list";

        /**
         * 公司
         */
        String COMPANY_LIST = "company_list";

    }

    String HTTP_GET_SAMPLE_URL = "http://168.100.1.218/wlkg/Service/";
    /**
     * 派工计划模块ip
     */
    //派工货物提示框IP
    String HTTP_GET_CagoAuto_URL = "http://218.92.115.55/M_Lhgl/Service/Base/GetCargo.aspx";
    //派工计划第一页IP
    String HTTP_GET_TASKONE_URL = "http://218.92.115.55/M_Lhgl/Service/Slip/GetMissionCommand.aspx";
    //详细派工页
    String HTTP_GET_TASKDETAIL_URL = "http://218.92.115" + "" +
            ".55/M_Lhgl/Service/Slip/GetMissionCommandSummary.aspx";
    //派工获取委托查询数据
    String HTTP_GET_ENTRUST_URL = "http://218.92.115.55/M_Lhgl/Service/Slip/GetConsign.aspx";
    //获取子过程标志列表数据
    String HTTP_GET_SUBPROCESS_URL = "http://218.92.115.55/M_Lhgl/Service/Base/GetSpecialMark.aspx";
    //获取票货数据
    String HTTP_GET_SHIPMENTDATA_URL = "http://218.92.115.55/M_Lhgl/Service/Slip/GetGoogsBill.aspx";
    //  获取载货工具编码（源、目的）
    String HTTP_GET_CodeCarry_URL = "http://218.92.115.55/M_Lhgl/Service/Slip/GetCodeCarries.aspx";
    //  获取载货工具编码（源、目的）
    String HTTP_GET_AllCarry_URL = "http://218.92.115.55/M_Lhgl/Service/Slip/GetAllCarries.aspx";
    //获取所有载体数据
    String HTTP_GET_TRUSTSHIPMENTDATA_URL = "http://218.92.115" + "" +
            ".55/M_Lhgl/Service/Slip/GetGoodsBill.aspx";
    //获取作业数据列表数据
    String HTTP_GET_OPERATDATA_URL = "http://218.92.115.55/M_Lhgl/Service/Base/GetWorkingArea.aspx";
    //汽运查询地址
    String HTTP_GET_TRUNKQUERY_URL = "http://218.92.115" + "" +
            ".55/M_Lhgl/Service/Vehicle/GetVehicleTransport.aspx";
    //汽运作业地址
    String HTTP_GET_TRUNKWORK_URL = "http://218.92.115.55/M_Lhgl/Service/Vehicle/GetStartWork.aspx";
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

    /**
     * 获取货代列表的请求地址
     */
    String FORWARDER_LIST_URL = "http://218.92.115.55/M_Lhgl/Service/Base/GetClient.aspx";

    /**
     * 获取货场列表的请求地址
     */
    String STORAGE_LIST_URL = "http://218.92.115.55/M_Lhgl/Service/Base/GetStorage.aspx";

    /**
     * 查询堆存列表的请求地址
     */
    String STOCK_LIST_URL = "http://218.92.115.55/M_Lhgl/Service/Stock/GetStock.aspx";

    /**
     * 获取堆存详情的请求地址
     */
    String STOCK_CONTENT_URL = "http://218.92.115.55/M_Lhgl/Service/Stock/GetStockDetail.aspx";

    /**
     * 获取公司列表的请求地址
     */
    String COMPANY_LIST_URL = "http://218.92.115.55/M_Lhgl/Service/Base/GetDepartment.aspx";

    /**
     * 获取衡重列表的请求地址
     */
    String BALANCE_WEIGHT_LIST_URL = "http://218.92.115.55/M_Lhgl/Service/Weighing/GetWeighting"
            + ".aspx";
    /**
     * 获取衡重详情的请求地址
     */
    String BALANCE_WEIGHT_CONTENT_URL = "http://218.92.115" + "" +
            ".55/M_Lhgl/Service/Weighing/GetWeightingDetail.aspx";

    /**
     * 文件上传接口地址
     */
    String UPLOAD_FILE_URL = "http://218.92.115.55/M_Lhgl/Service/Handover/UploadFile.aspx";

    /**
     * 交接班提交接口地址
     */
    String SHIFT_CHANGE_COMMIT_URL = "http://218.92.115.55/M_Lhgl/Service/Handover/WorkHandover"
            + ".aspx";

    /**
     * 交接班消息获取接口地址
     */
    String SHIFT_CHANGE_CONTENT_URL = "http://218.92.115" + "" +
            ".55/M_Lhgl/Service/Handover/GetHandoverRecord.aspx";

    /**
     * 工作计划获取接口地址
     */
    String WORK_PLAN_URL = "http://218.92.115.55/M_Lhgl/Service/Slip/GetUncommitedMachineCommand"
            + ".aspx";

    //汽运作业班组请求地址
    String HTTP_GET_TRUNKWORKTEAM_URL = "http://218.92.115.55/M_Lhgl/Service/Base/GetWorkTeam.aspx";
    //地图数据请求地址
    String HTTP_GET_MAP_URL = "http://218.92.115.55/M_Lhgl/Service/Map/GetMassCoord.aspx";
    //    校验车辆是否黑名单
    String HTTP_GET_VERIFYVEHICLE_URL = "http://218.92.115" + "" +
            ".55/M_Lhgl/Service/Vehicle/VerifyVehicleBlackList.aspx";
    //    校验开工状态
    String HTTP_GET_VERIFYSTART_URL = "http://218.92.115" + "" +
            ".55/M_Lhgl/Service/Vehicle/VerifyStartWork.aspx";
    //    校验完工状态
    String HTTP_GET_VERIFYEND_URL = "http://218.92.115.55/M_Lhgl/Service/Vehicle/VerifyEndWork" +
            ".aspx";
    //   开工
    String HTTP_GET_STARTWORK_URL = "http://218.92.115.55/M_Lhgl/Service/Vehicle/GetStartWork.aspx";
    //   完工
    String HTTP_GET_ENDWORK_URL = "http://218.92.115.55/M_Lhgl/Service/Vehicle/GetEndWork.aspx";
    //提交开工时间
    String HTTP_GET_UPDATSTART_URL = "http://218.92.115.55/M_Lhgl/Service/Vehicle/StartWork" + "" +
            ".aspx";
    String HTTP_GET_Team_URL = "http://218.92.115.55/M_Lhgl/Service/Slip/GetTeamWorkerAndMark.aspx";
    String HTTP_GET_Machine_URL = "http://218.92.115.55/M_Lhgl/Service/Slip/GetMachineAndMark.aspx";
    String HTTP_GET_NewTeam_URL = "http://218.92.115.55/M_Lhgl/Service/Slip/GetTeamWorker.aspx";
    String HTTP_GET_NewMachine_URL = "http://218.92.115.55/M_Lhgl/Service/Slip/GetMachine.aspx";
    //提交完工时间
    String HTTP_GET_UPDATEND_URL = "http://218.92.115.55/M_Lhgl/Service/Vehicle/EndWork.aspx";
    //删除暂存数据
    String HTTP_GET_TALLYDETAILDELET_URL = "http://218.92.115" + "" +
            ".55/M_Lhgl/Service/Slip/DeleteSavedTallyBill.aspx";
    // 作业计划第一页
    String HTTP_GET_WORKPLAN_URL = "http://218.92.115.55/M_Lhgl/Service/Plan/GetOperationPlan.aspx";
    // 作业计划详情页
    String HTTP_GET_WORKPLANDETAIL_URL = "http://218.92.115" + "" +
            ".55/M_Lhgl/Service/Plan/GetOperationPlanDetail.aspx";
    //    获取配工机械数据接口
    String HTTP_GET_DetailMachine_URL = "http://218.92.115" + "" +
            ".55/M_Lhgl/Service/Slip/GetMissionMachine.aspx";
    //   获取配工司机数据
    String HTTP_GET_DetailName_URL = "http://218.92.115.55/M_Lhgl/Service/Slip/GetMissionDriver"
            + ".aspx";
    //获取配工班组班别数据
    String HTTP_GET_DetailTeam_URL = "http://218.92.115" +
            ".55/M_Lhgl/Service/Slip/GetMissionWorkTeam" + ".aspx";
    //获取配工班组工人姓名数据
    String HTTP_GET_DetailTeamName_URL = "http://218.92.115" + "" +
            ".55/M_Lhgl/Service/Slip/GetMissionWorkerName.aspx";
    //获取区域数据（理货作业票模块）
    String HTTP_GET_DetailArea_URL = "http://218.92.115.55/M_Lhgl/Service/Slip/GetArea.aspx";
    //    理货跳转的第二个listview
    String HTTP_GET_TALLYTWO_URL = "http://218.92.115.55/M_Lhgl/Service/Slip/GetAllTallyBill.aspx";
    //    保存接口
    // String HTTP_POST_TALLYSAVE_URL= "http://218.92.115.55/M_Lhgl/Service/Slip/SaveTallyBill
    // .aspx";
    String HTTP_POST_TALLYSAVE_URL = "http://10.199.10.220:8080/Service/Slip/SaveTallyBill.aspx";
    //    新子过程标志
    String HTTP_GET_SUBPROCESSNEW_URL = "http://218.92.115" + "" +
            ".55/M_Lhgl/Service/Slip/GetSavedSpecialMark.aspx";
    //    新区域
    String HTTP_GET_DetailAreaNEW_URL = "http://218.92.115.55/M_Lhgl/Service/Slip/GetSavedArea" +
            ".aspx";
    //   //获取暂存质量（理货作业票模块）
    String HTTP_GetSavedQuality_URL = "http://218.92.115.55/M_Lhgl/Service/Slip/GetSavedQuality"
            + ".aspx";
    //获取暂存数量数据
    String HTTP_GetSavedQuantityData_URL = "http://218.92.115" + "" +
            ".55/M_Lhgl/Service/Slip/GetSavedQuantityData.aspx";
    //获取暂存票货数据
    String HTTP_GetGoodsBill_URL = "http://218.92.115.55/M_Lhgl/Service/Slip/GetSavedGoodsBill" +
            ".aspx";
    //    获取货位数据
    String HTTP_GetALLOCATIONDATA_URL = "http://218.92.115.55/M_Lhgl/Service/Base/GetAllocation"
            + ".aspx";
    //    获取桩角数据
    String HTTP_GetCornerPile_URL = "http://218.92.115.55/M_Lhgl/Service/Base/GetBooth.aspx";

    //    获取桩角和货位数据
    String HTTP_GetCornerPileAndAllocation_URL = "http://218.92.115" + "" +
            ".55/M_Lhgl/Service/Base/GetBooth.aspx";
}
