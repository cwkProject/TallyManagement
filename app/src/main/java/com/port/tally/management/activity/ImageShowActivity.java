package com.port.tally.management.activity;
/**
 * Created by 超悟空 on 2015/12/4.
 */

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.port.tally.management.R;
import com.port.tally.management.util.ImageUtil;

import java.io.File;

/**
 * 展示全屏图片的布局
 *
 * @author 超悟空
 * @version 1.0 2015/12/4
 * @since 1.0
 */
public class ImageShowActivity extends AppCompatActivity {

    /**
     * 要使用该布局显示的图片文件对象传递标签
     */
    public static final String IMAGE_FILE = "image_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);

        // 初始化
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {

        // 显示图片的控件
        ImageView imageView = (ImageView) findViewById(R.id.activity_image_show_imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Object object = getIntent().getSerializableExtra(IMAGE_FILE);

        if (object instanceof File) {
            imageView.setImageBitmap(ImageUtil.resolutionBitmap((File) object, 720, 1280));
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }
}
