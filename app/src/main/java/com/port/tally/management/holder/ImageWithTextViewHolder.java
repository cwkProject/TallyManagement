package com.port.tally.management.holder;
/**
 * Created by 超悟空 on 2015/12/12.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.port.tally.management.R;

/**
 * 带有图片和文本框的布局管理器
 *
 * @author 超悟空
 * @version 1.0 2015/12/12
 * @since 1.0
 */
public class ImageWithTextViewHolder {

    /**
     * 根布局
     */
    public View rootItem = null;

    /**
     * 图片空控件
     */
    public ImageView imageView = null;

    /**
     * 文本控件
     */
    public TextView textView = null;

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public ImageWithTextViewHolder(Context context) {

        rootItem = LayoutInflater.from(context).inflate(R.layout.image_with_text_view, null);

        imageView = (ImageView) rootItem.findViewById(R.id.image_with_text_view_imageView);

        textView = (TextView) rootItem.findViewById(R.id.image_with_text_view_textView);
    }
}
