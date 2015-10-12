package com.port.tally.management.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.port.tally.management.R;
import com.port.tally.management.adapter.LiHuoWeiTuoAdapter;
import com.port.tally.management.bean.LiHuoWeiTuo;
import com.port.tally.management.bean.WorkPlanDetailBean;
import com.port.tally.management.work.ToallyDetailWork;
import com.port.tally.management.work.WorkPlanDetailWork;

import org.mobile.library.model.work.WorkBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 2015/10/10.
 */
public class WorkPlanDetail extends Activity {

    /**
     * @param args
     */
    private ImageView imgLeft;
    private TextView title,tv_cargo_owner,tv_cargo,tv_voyage,tv_forwarder,tv_goodsyard,tv_weight,tv_enter,tv_out,tv_trade,tv_maitou,tv_allocation,
            tv_pack,tv_count,tv_countweight;

    private Toast mToast;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workplandetail);
        init();
        initData();

    }

    private void initData(){
        WorkPlanDetailWork workPlanDetailWork = new WorkPlanDetailWork();
        workPlanDetailWork.setWorkBackListener(new WorkBack<WorkPlanDetailBean>() {
            @Override
            public void doEndWork(boolean b, WorkPlanDetailBean workPlanDetailBean) {
                if(b){
                    tv_cargo_owner.setText(workPlanDetailBean.getCargoowner());
                    tv_cargo.setText(workPlanDetailBean.getCargo());
                    tv_voyage.setText(workPlanDetailBean.getVgdisplay());
                    tv_forwarder.setText(workPlanDetailBean.getClient());
                    tv_goodsyard.setText(workPlanDetailBean.getStorage());
                    tv_weight.setText(workPlanDetailBean.getWeight());
                    tv_enter.setText(workPlanDetailBean.getFirst_indate());
                    tv_out.setText(workPlanDetailBean.getInout());
                    tv_trade.setText(workPlanDetailBean.getTrade());
                    tv_maitou.setText(workPlanDetailBean.getMark());
                    tv_allocation.setText(workPlanDetailBean.getBooth());
                    tv_pack.setText(workPlanDetailBean.getPack());
                    tv_count.setText(workPlanDetailBean.getAmount());
                    tv_countweight.setText(workPlanDetailBean.getPieceweight());
                }else {
                    showToast("数据不存在");
                }
            }
        });
        workPlanDetailWork.beginExecute("14");
    }
    private void init() {
        title = (TextView) findViewById(R.id.title);
        imgLeft = (ImageView) findViewById(R.id.left);
        tv_cargo_owner = (TextView) findViewById(R.id.tv_cargo_owner);
        tv_cargo = (TextView) findViewById(R.id.tv_cargo);
        tv_voyage = (TextView) findViewById(R.id.tv_voyage);
        tv_forwarder = (TextView) findViewById(R.id.tv_forwarder);
        tv_goodsyard = (TextView) findViewById(R.id.tv_goodsyard);
        tv_weight = (TextView) findViewById(R.id.tv_weight);
        tv_enter = (TextView) findViewById(R.id.tv_enter);
        tv_out = (TextView) findViewById(R.id.tv_out);
        tv_trade = (TextView) findViewById(R.id.tv_trade);
        tv_maitou = (TextView) findViewById(R.id.tv_maitou);
        tv_allocation = (TextView) findViewById(R.id.tv_allocation);
        tv_pack = (TextView) findViewById(R.id.tv_pack);
        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_countweight = (TextView) findViewById(R.id.tv_countweight);
        title.setText("作业计划详情");
        title.setVisibility(View.VISIBLE);
        imgLeft.setVisibility(View.VISIBLE);

        imgLeft.setOnClickListener(new View.OnClickListener() {

            //			@Override
            public void onClick(View arg0) {
                finish();
            }
        });

    }
    private void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
}
