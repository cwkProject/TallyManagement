package com.port.tally.management.adapter;
/**
 * Created by 超悟空 on 2015/12/9.
 */

import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.port.tally.management.R;
import com.port.tally.management.holder.ShiftChangeAudioViewHolder;

import org.mobile.library.cache.util.CacheTool;
import org.mobile.library.model.operate.OnItemClickListenerForRecyclerViewItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 交接班待发送音频列表适配器
 *
 * @author 超悟空
 * @version 1.0 2015/12/9
 * @since 1.0
 */
public class ShiftChangeAudioRecyclerViewAdapter extends RecyclerView
        .Adapter<ShiftChangeAudioViewHolder> {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "ShiftChangeAudioRecyclerViewAdapter.";

    /**
     * 存有音频文件的缓存工具
     */
    private CacheTool cacheTool = null;

    /**
     * 音频播放器
     */
    private MediaPlayer mediaPlayer = new MediaPlayer();

    /**
     * 显示的音频缓存key集合
     */
    private List<String> dataList = null;

    /**
     * item点击事件监听器
     */
    private OnItemClickListenerForRecyclerViewItem<List<String>, ShiftChangeAudioViewHolder>
            onItemClickListener = null;

    /**
     * 构造函数
     *
     * @param cacheTool 图片缩略图缓存工具
     */
    public ShiftChangeAudioRecyclerViewAdapter(CacheTool cacheTool) {
        this.cacheTool = cacheTool;
        dataList = new ArrayList<>();
    }

    /**
     * 设置点击事件
     *
     * @param onItemClickListener 点击事件监听器
     */
    public void setOnItemClickListener(OnItemClickListenerForRecyclerViewItem<List<String>,
            ShiftChangeAudioViewHolder> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 添加一条数据
     *
     * @param position 插入位置
     * @param data     添加的值
     */
    public void addData(int position, String data) {
        this.dataList.add(position, data);
        notifyItemInserted(position);
    }

    /**
     * 添加一组数据
     *
     * @param position 插入位置
     * @param data     添加的值集合
     */
    public void addData(int position, List<String> data) {
        this.dataList.addAll(position, data);
        notifyItemRangeInserted(position, data.size());
    }

    @Override
    public ShiftChangeAudioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 创建Item根布局
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .shift_change_audio_item, parent, false);

        return new ShiftChangeAudioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ShiftChangeAudioViewHolder holder, int position) {

        try {
            File file = cacheTool.getForFile(dataList.get(position));
            if (file != null) {
                mediaPlayer.setDataSource(file.getPath());
                mediaPlayer.prepare();
                // 音频长度
                int length = mediaPlayer.getDuration();
                mediaPlayer.reset();

                String lengthString = "";

                if (length / 60000 > 0) {
                    lengthString += length / 60000 + "'";
                }
                lengthString += (length / 1000) % 60 + "\"";

                lengthString += length % 1000;

                holder.textView.setText(lengthString);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG + "onBindViewHolder", "IOException is " + e.getMessage());
        }

        // 绑定监听事件
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(dataList, holder);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * 释放资源
     */
    public void release() {
        mediaPlayer.release();
    }
}
