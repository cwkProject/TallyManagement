package com.port.tally.management.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.port.tally.management.R;
import com.port.tally.management.bean.FormPicFile;
import com.port.tally.management.util.FloatTextToast;
import com.port.tally.management.util.HttpFormPicUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by song on 2015/9/21.
 */

public class ShiftDuty extends Activity{
    private ImageView imgLeft,collect_photo;
    private TextView title,text_btn,tv_state;
    private Button btn_takephoto,btn_picture,btnRecord,btnRecordStop,btnRecordPlay,btnRecordDelet;
    public static final int NONE = 0;
    public static final int PHOTOHRAPH = 1;// 拍照
    public static final int PHOTOZOOM = 2; // 缩放
    public static final int PHOTORESOULT = 3;// 结果
    public static final String IMAGE_UNSPECIFIED = "image/*";//路径
    InputStream isBm;
    //录音变量
    private String TAG = "session";
    private static final int RECORDER_BPP = 16;
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
    private static final String AUDIO_RECORDER_TEMP_FILE = "record_temp.raw";
    private static int frequency = 44100;
    private static int channelConfiguration = AudioFormat.CHANNEL_IN_STEREO;
    private static int EncodingBitRate = AudioFormat.ENCODING_PCM_16BIT;
    private AudioRecord audioRecord = null;
    private AudioTrack audioTrack = null;
    private int recBufSize = 0;
    private int playBufSize = 0;
    private Thread recordingThread = null;
    private boolean isRecording = false;
    private boolean isTracking = false;
    private boolean m_keep_running;
    protected PCMAudioTrack m_player;
    //录音变量
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.jiaojiemain);
        Init();
        //拍照
        btn_takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                        Environment.getExternalStorageDirectory(), "temp.jpg")));
                System.out.println("=============" + Environment.getExternalStorageDirectory());
                startActivityForResult(intent, PHOTOHRAPH);
            }
        });
        //从相册获取
        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_UNSPECIFIED);
                startActivityForResult(intent, PHOTOZOOM);
            }
        });

    }
    private void Init() {
        // TODO Auto-generated method stub
        title = (TextView) findViewById(R.id.title);
        imgLeft= (ImageView)findViewById(R.id.left);
        text_btn=(TextView) findViewById(R.id.text_btn);
//        btnRecord =(Button)findViewById(R.id.btn_record);
//        btnRecordPlay=(Button)findViewById(R.id.btn_play);
//        btnRecordStop = (Button)findViewById(R.id.btn_stop);
//        btnRecordDelet = (Button)findViewById(R.id.btn_delet);
        tv_state = (TextView)findViewById(R.id.tv_state);
        collect_photo =(ImageView)findViewById(R.id.collect_im_photo);
        btn_picture =(Button)findViewById(R.id.btn_picture);
        btn_takephoto= (Button)findViewById(R.id.btn_takephoto);
        title.setText("交接班");
        text_btn.setText(" 交接记录");
        title.setVisibility(View.VISIBLE);
        imgLeft.setVisibility(View.VISIBLE);
        text_btn.setVisibility(View.VISIBLE);
        imgLeft.setOnClickListener(new View.OnClickListener() {

            //	@Override
            public void onClick(View arg0) {
                finish();
            }
        });
        text_btn.setOnClickListener(new View.OnClickListener() {

            //@Override
            public void onClick(View arg0) {
                finish();
            }
        });
        setButtonHandlers();
    }
    private String uploadFile(InputStream fStream) {

        String urlStr = "http://218.92.115.55/M_Lhgl/Service/Handover/UploadPicture.aspx";
        String result = "0";
        try {

            byte[] buffer = new byte[fStream.available()];
            fStream.read(buffer);
            FormPicFile ff = new FormPicFile(buffer,"picFile","image/gif");
            Map<String, String> params = new HashMap<String, String>();
            Date date = new Date();
            String dateStr = new SimpleDateFormat("yy-MM-dd").format(date);
            params.put("uploadtime", dateStr);
            result = HttpFormPicUtil.post(urlStr, params, new FormPicFile[]{ff});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    private boolean validate(){
        return true;
    }
//    private boolean submit(){
//        String url = HttpUtil.BASE_URL+"servlet/PeopleInfoCollectServlet";
//
//        HttpPost request = HttpUtil.getHttpPost(url);
//        request.setEntity(makeEntity());
//
//        String result= HttpUtil.queryStringForPost(request);
//        if(result!=null&&result.equals("1"))
//            return true;
//
//        return false;
//    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == NONE)
            return;
        // 拍照
        if (requestCode == PHOTOHRAPH) {
            // 设置文件保存路径这里放在跟目录下
            File picture = new File(Environment.getExternalStorageDirectory()
                    + "/temp.jpg");
            System.out.println("------------------------" + picture.getPath());
            startPhotoZoom(Uri.fromFile(picture));
        }

        if (data == null)
            return;

        // 读取相册缩放图片
        if (requestCode == PHOTOZOOM) {
            startPhotoZoom(data.getData());
        }
        // 处理结果
        if (requestCode == PHOTORESOULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -
                isBm = new ByteArrayInputStream(
                        stream.toByteArray());			// 100)压缩文件
                collect_photo.setImageBitmap(photo);
                collect_photo.setVisibility(View.VISIBLE);

            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 64);
        intent.putExtra("outputY", 64);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTORESOULT);
    }
    //录音
    private void setButtonHandlers() {
        ((Button)findViewById(R.id.btn_record)).setOnClickListener(btnClick);
        ((Button)findViewById(R.id.btn_stop)).setOnClickListener(btnClick);
        ((Button)findViewById(R.id.btn_play)).setOnClickListener(btnClick);
        ((Button)findViewById(R.id.btn_delet)).setOnClickListener(btnClick);
        //((Button)findViewById(R.id.btnExit)).setOnClickListener(btnClick);

    }

    private void enableButton(int id,boolean isEnable){
        ((Button)findViewById(id)).setEnabled(isEnable);
    }

    private void enableButton0(boolean isRecording) {
        enableButton(R.id.btn_record,!isRecording);
        enableButton(R.id.btn_play,!isRecording);
        enableButton(R.id.btn_delet,!isRecording);
        enableButton(R.id.btn_stop,isRecording);
    }

    private void enableButton1(boolean isRecording) {
        enableButton(R.id.btn_record,!isRecording);
        enableButton(R.id.btn_play,isRecording);
        enableButton(R.id.btn_delet,isRecording);
        enableButton(R.id.btn_stop,isRecording);
    }

    private void enableButton2(boolean isRecording) {
        enableButton(R.id.btn_record,!isRecording);
        enableButton(R.id.btn_play,!isRecording);
        enableButton(R.id.btn_delet,isRecording);
        enableButton(R.id.btn_stop,isRecording);
    }

    private void enableButton3(boolean isTracking) {
        enableButton(R.id.btn_record,!isTracking);
        enableButton(R.id.btn_stop,!isTracking);
        enableButton(R.id.btn_play,!isTracking);
        enableButton(R.id.btn_delet,isTracking);
    }

    private void enableButton4(boolean isTracking) {
        enableButton(R.id.btn_record,!isTracking);
        enableButton(R.id.btn_stop,isTracking);
        enableButton(R.id.btn_play,!isTracking);
        enableButton(R.id.btn_delet,isTracking);
    }

    private String getFilename(){
        String filepath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(filepath,AUDIO_RECORDER_FOLDER);

        if(file.exists()){
            file.delete();
        }

        return (file.getAbsolutePath() + "/session.wav" );
    }

    private String getTempFilename(){
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath,AUDIO_RECORDER_FOLDER);

        if(!file.exists()){
            file.mkdirs();
        }

        File tempFile = new File(filepath,AUDIO_RECORDER_TEMP_FILE);

        if(tempFile.exists())
            tempFile.delete();

        return (file.getAbsolutePath() + "/" + AUDIO_RECORDER_TEMP_FILE);
    }

    private void startRecording(){

        createAudioRecord();

        audioRecord.startRecording();

        isRecording = true;

        recordingThread = new Thread(new Runnable() {

            @Override
            public void run() {
                writeAudioDataToFile();
            }
        },"AudioRecorder Thread");

        recordingThread.start();
    }

    private void writeAudioDataToFile(){
        byte data[] = new byte[recBufSize];
        String filename = getTempFilename();
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int read = 0;

        if(null != os){
            while(isRecording){
                read = audioRecord.read(data, 0, recBufSize);

                if(AudioRecord.ERROR_INVALID_OPERATION != read){
                    try {
                        os.write(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopRecording(){
        if(null != audioRecord){
            isRecording = false;

            audioRecord.stop();
            audioRecord.release();

            audioRecord = null;
            recordingThread = null;
        }

        copyWaveFile(getTempFilename(),getFilename());
        deleteTempFile();
    }

    private void deleteTempFile() {
        File file = new File(getTempFilename());

        file.delete();
    }

    private void copyWaveFile(String inFilename,String outFilename){
        FileInputStream in = null;
        FileOutputStream out = null;
        long totalAudioLen = 0;
        long totalDataLen = totalAudioLen + 36;
        long longSampleRate = frequency;
        int channels = 2;
        long byteRate = RECORDER_BPP * frequency * channels/8;

        byte[] data = new byte[recBufSize];

        try {
            in = new FileInputStream(inFilename);
            out = new FileOutputStream(outFilename);
            totalAudioLen = in.getChannel().size();
            totalDataLen = totalAudioLen + 36;

//            AppLog.logString("File size: " + totalDataLen);

            WriteWaveFileHeader(out, totalAudioLen, totalDataLen,
                    longSampleRate, channels, byteRate);

            while(in.read(data) != -1){
                out.write(data);
            }

            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void WriteWaveFileHeader(
            FileOutputStream out, long totalAudioLen,
            long totalDataLen, long longSampleRate, int channels,
            long byteRate) throws IOException {

        byte[] header = new byte[44];

        header[0] = 'R';  // RIFF/WAVE header
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f';  // 'fmt ' chunk
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = 16;  // 4 bytes: size of 'fmt ' chunk
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1;  // format = 1
        header[21] = 0;
        header[22] = (byte) channels;
        header[23] = 0;
        header[24] = (byte) (longSampleRate & 0xff);
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        header[32] = (byte) (2 * 16 / 8);  // block align
        header[33] = 0;
        header[34] = RECORDER_BPP;  // bits per sample
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);

        out.write(header, 0, 44);
    }

    private View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_record:{
//                    AppLog.logString("Start Recording");
                    tv_state.setText("btn_record");
                    enableButton0(true);
                    startRecording();

                    break;
                }
                case R.id.btn_stop:{
//                    AppLog.logString("Start Recording");
                    tv_state.setText("btn_stop");
                    enableButton2(false);
                    stopRecording();

                    break;
                }
                case R.id.btn_play:{
//                    AppLog.logString("Start Tracking");
                    tv_state.setText("btn_play");
                    enableButton3(true);
                    m_player = new PCMAudioTrack();
                    Log.i(TAG, "xxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                    m_player.init();
                    m_player.start();

                    break;
                }
                case R.id.btn_delet:{
                    tv_state.setText("btn_delet");
//                    AppLog.logString("Stop Tracking");
                    enableButton4(false);
                    if(m_player!=null){
                        m_player.free();
                    }
                    else{
                         FloatTextToast.makeText(ShiftDuty.this, tv_state, "结果不存在", FloatTextToast.LENGTH_SHORT).show();
                    }

                    m_player = null;

                    break;
                }


            }
        }
    };

    public void createAudioRecord(){
        recBufSize = AudioRecord.getMinBufferSize(frequency,
                channelConfiguration, EncodingBitRate);


        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency,
                channelConfiguration, EncodingBitRate, recBufSize);
    }

    public void createAudioTrack(){
        playBufSize=AudioTrack.getMinBufferSize(frequency,
                channelConfiguration, EncodingBitRate);


        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, frequency,
                channelConfiguration, EncodingBitRate,
                playBufSize, AudioTrack.MODE_STREAM);
    }

    class PCMAudioTrack extends Thread {

        protected byte[] m_out_bytes;


        final String FILE_PATH = "/sdcard/AudioRecorder/";
        final String FILE_NAME = "session.wav";

        File file;
        FileInputStream in;

        public void init() {
            try {
                file = new File(FILE_PATH , FILE_NAME);
                file.createNewFile();
                in = new FileInputStream(file);

//        			in.read(temp, 0, length);

                m_keep_running = true;

                createAudioTrack();

                m_out_bytes = new byte[playBufSize];

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void free() {
            m_keep_running = false;
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                Log.d("sleep exceptions...\n", "");
            }
        }

        public void run() {
            byte[] bytes_pkg = null;
            audioTrack.play();
            while (m_keep_running) {
                try {
                    if(m_out_bytes!=null){
                    in.read(m_out_bytes);
                    bytes_pkg = m_out_bytes.clone();
                    audioTrack.write(bytes_pkg, 0, bytes_pkg.length);}
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            audioTrack.stop();
            audioTrack = null;
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if( audioTrack != null){
            audioTrack.stop();
            audioTrack = null;
        }
        if(m_player!=null){
            m_player.free();
        }
        else{
//            FloatTextToast.makeText(ShiftDuty.this, tv_state, "结果不存在", FloatTextToast.LENGTH_SHORT).show();
        }

        m_player = null;
        stopRecording();
//         android.os.Process.killProcess(android.os.Process.myPid());
    }
}
