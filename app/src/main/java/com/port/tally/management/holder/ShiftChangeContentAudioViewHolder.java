package com.port.tally.management.holder;
/**
 * Created by 超悟空 on 2015/12/23.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.port.tally.management.R;

/**
 * 交接班消息正文的音频布局管理器
 *
 * @author 超悟空
 * @version 1.0 2015/12/23
 * @since 1.0
 */
public class ShiftChangeContentAudioViewHolder {

    /**
     * 根布局
     */
    public View rootItem = null;

    /**
     * 图片控件
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
    public ShiftChangeContentAudioViewHolder(Context context) {

        rootItem = LayoutInflater.from(context).inflate(R.layout.shift_change_content_audio, null);

        // 绑定自身引用
        rootItem.setTag(R.id.view_holder_tag, this);

        imageView = (ImageView) rootItem.findViewById(R.id.shift_change_content_audio_imageView);

        textView = (TextView) rootItem.findViewById(R.id.shift_change_content_audio_textView);
    }
}
