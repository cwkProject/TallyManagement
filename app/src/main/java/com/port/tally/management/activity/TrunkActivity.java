package com.port.tally.management.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.port.tally.management.R;

/**
 * Created by song on 2015/9/29.
 */

public class TrunkActivity extends Activity {
    private Button startBtn,endBtn;
    private TextView title;
    private ImageView imgLeft;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.trunkwork_layout);
         startBtn =(Button)findViewById(R.id.start_btn);
        title = (TextView) findViewById(R.id.title);
         endBtn =(Button)findViewById(R.id.end_btn);
        imgLeft = (ImageView) findViewById(R.id.left);
        title.setText("汽运作业");
        imgLeft.setVisibility(View.VISIBLE);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrunkActivity.this,StartWork.class);
                startActivity(intent);

            }
        });
        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(TrunkActivity.this,EndWork.class);
                 startActivity(intent);

            }
        });
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
