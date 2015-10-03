package com.port.tally.management.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.Geometry;
import com.baidu.mapapi.map.Graphic;
import com.baidu.mapapi.map.GraphicsOverlay;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.Symbol;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.port.tally.management.R;
import com.port.tally.management.bean.MapBean;
import com.port.tally.management.work.MapWork;

import org.mobile.library.model.work.WorkBack;

import java.util.List;

public class DianZiMap extends Activity {

    private Toast mToast;
    private BMapManager mBMapManager;
    private MapView mMapView = null;
    private MapController mMapController = null;
    private GeoPoint p[];
    private GeoPoint p2[];

    private  String[][] mapData;
    private double geo1[] ={34.735660,119.473205,34.735941,119.473343,34.735643,119.474368,34.735345,119.474213};
    private double geo2[]={34.732146,119.471118,34.731945,119.471786,34.731323,119.471458,34.731538,119.470801};
    private double geo[]={119.317425,34.719149,119.319976,34.716716,119.324504,34.713868,119.328349,34.710604,119.329427,34.710693 ,119.332517,34.715114,119.337835,34.712651,
            119.339667,34.717132,119.335535,34.719238,119.330038,34.722028, 119.327163,34.723481,119.317425,34.719149};
//    private double geo1[]={119.317462,34.71909,119.319431,34.717025,119.324461,34.713939,119.327408,34.717619,119.323814,34.719547,119.322162,34.721268,119.317462,34.71909};
//    private double geo2[]={119.329096,34.719755,119.334019,34.717203,119.335492,34.719251,119.330534,34.721772,119.329096,34.719755};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用地图sdk前需先初始化BMapManager，这个必须在setContentView()先初始化
        mBMapManager = new BMapManager(this);
        // 将GPS设备采集的原始GPS坐标转换成百度坐标
//        CoordinateConverter converter  = new CoordinateConverter();
//        converter.from(CoordType.GPS);
//        // sourceLatLng待转换坐标
//        converter.coord(sourceLatLng);
//        LatLng desLatLng = converter.convert();
        //第一个参数是API key,
        //第二个参数是常用事件监听，用来处理通常的网络错误，授权验证错误等，你也可以不添加这个回调接口PTMdf5ZfHZkhRwHvAtUwYpZX
        //		mBMapManager.init("7ae13368159d6a513eaa7a17b9413b4b", new MKGeneralListenerImpl());
        mBMapManager.init("cgTaWnGGDTuUzBXdCMSXTKqE", new MKGeneralListenerImpl());
        setContentView(R.layout.my_position);
        mMapView = (MapView) findViewById(R.id.bmapView); //获取百度地图控件实例
        mMapController = mMapView.getController(); //获取地图控制器
        mMapController.enableClick(true);   //设置地图是否响应点击事件
         onLoadFromNetWork();

//        double mLat = Double.parseDouble(mapData[0][2]);
//        double mLon = Double.parseDouble(mapData[0][3]);
//
////        double mLat = 34.719149;
////        double mLon = 119.317425;
//        int lat = (int) (mLat * 1E6);
//        int lon = (int) (mLon * 1E6);
//        GeoPoint pt1 = new GeoPoint(lat, lon);
//        mMapController.setCenter(pt1);
//        mMapController.setZoom(17);   //设置地图缩放级别
//        mMapView.setBuiltInZoomControls(true);   //显示内置缩放控件
        //
//        Drawable mark= getResources().getDrawable(R.drawable.ic_launcher);//准备overlay图像数据，根据实情情况修复
//        //用OverlayItem准备Overlay数据
//        p2=new GeoPoint[geo.length/2];
//        for(int i=0;i<p2.length;i++){
//            p2[i] = new GeoPoint((int)(geo[2*i+1] * 1E6),(int)(geo[2*i] * 1E6));
//        }
//        OverlayItem[] item=new OverlayItem[p2.length];
//        for(int k=0;k<p2.length;k++){
//            item[k]=new OverlayItem(p2[k],"item","item");
//            //使用setMarker()方法设置overlay图片,如果不设置则使用构建ItemizedOverlay时的默认设置
////            item[k].setMarker(m[k]);
//        }

        //创建IteminizedOverlay
//        OverlayTest itemOverlay = new OverlayTest(mark, mMapView);
//        //将IteminizedOverlay添加到MapView中
////        mMapView.getOverlays().clear();
//        //现在所有准备工作已准备好，使用以下方法管理overlay.
//        //添加overlay, 当批量添加Overlay时使用addItem(List<OverlayItem>)效率更高
//        //将IteminizedOverlay添加到MapView中
//        mMapView.getOverlays().add(itemOverlay);
//        for(int n=0;n<item.length;n++){
//            itemOverlay.addItem(item[n]);
//        }

        // 界面加载时添加绘制图层
        addCustomElementsDemo();
        //修改定位数据后刷新图层生效
//        mMapView.refresh();

    }
    public void onLoadFromNetWork() {
        MapWork mapWork = new MapWork();

        mapWork.setWorkBackListener(new WorkBack<List<MapBean>>() {
            @Override
            public void doEndWork(boolean state, List<MapBean> data) {

                mapData = new String[data.size()+1][15];


                for (int i = 0; i < data.size(); i++) {

                    mapData[i][0] = data.get(i).getMassNum();
                    mapData[i][1] = data.get(i).getVertexCount();
                    mapData[i][2] = data.get(i).getLatitude1();
                    mapData[i][3] = data.get(i).getLongitude1();
                    mapData[i][4] = data.get(i).getLatitude2();
                    mapData[i][5] = data.get(i).getLongitude2();
                    mapData[i][6] = data.get(i).getLatitude3();
                    mapData[i][7] = data.get(i).getLongitude3();
                    mapData[i][8] = data.get(i).getLatitude4();
                    mapData[i][9] = data.get(i).getLongitude4();
                    mapData[i][10] = data.get(i).getLatitude5();
                    mapData[i][11] = data.get(i).getLongitude5();
                    mapData[i][12] = data.get(i).getLatitude6();
                    mapData[i][13] = data.get(i).getLongitude6();


                }
                double mLat = Double.parseDouble(mapData[0][2]);
                double mLon = Double.parseDouble(mapData[0][3]);

//        double mLat = 34.719149;
//        double mLon = 119.317425;
                int lat = (int) (mLat * 1E6);
                int lon = (int) (mLon * 1E6);
                GeoPoint pt1 = new GeoPoint(lat, lon);
                mMapController.setCenter(pt1);
                mMapController.setZoom(17);   //设置地图缩放级别
                mMapView.setBuiltInZoomControls(true);   //显示内置缩放控件

            }
        });

        mapWork.beginExecute();

    }
    public void addCustomElementsDemo() {
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay(mMapView);

        mMapView.getOverlays().add(graphicsOverlay);
//        // 添加多边形
//        graphicsOverlay.setData(drawPolygon());
        // 添加折线
        graphicsOverlay.setData(drawLine(geo));
        graphicsOverlay.setData(drawLine1(geo1));
        graphicsOverlay.setData(drawLine1(geo2));
        mMapView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("item onTap: ");

            }
        });
//        // 绘制文字
//        TextOverlay textOverlay = new TextOverlay(mMapView);
//        mMapView.getOverlays().add(textOverlay);
//        textOverlay.addText(drawText());
        // 执行地图刷新使生效
        mMapView.refresh();
    }
    public Graphic drawPolygon() {


        p=new GeoPoint[geo.length/2];
        for(int i=0;i<p.length;i++){
            p[i] = new GeoPoint((int)(geo[2*i] * 1E6),(int)(geo[2*i] * 1E6));
        }

        // 构建多边形
        Geometry polygonGeometry = new Geometry();
        // 设置多边形坐标
        polygonGeometry.setPolygon(p);
        // 设置多边形样式
        Symbol polygonSymbol = new Symbol();
        Symbol.Color polygonColor = polygonSymbol.new Color();
        polygonColor.red = 0;
        polygonColor.green = 0;
        polygonColor.blue = 255;
        polygonColor.alpha = 126;
        polygonSymbol.setSurface(polygonColor, 1, 5);
        // 生成Graphic对象
        Graphic polygonGraphic = new Graphic(polygonGeometry, polygonSymbol);
        return polygonGraphic;

    }
    /**
     * 绘制折线，该折线状态随地图状态变化
     *
     * @return 折线对象
     */
    public Graphic drawLine(double geo[]) {

        p=new GeoPoint[geo.length/2];
        for(int i=0;i<p.length;i++){
            p[i] = new GeoPoint((int)(geo[2*i+1] * 1E6),(int)(geo[2*i] * 1E6));
        }
        // 构建线
        Geometry lineGeometry = new Geometry();

        lineGeometry.setPolyLine(p);
        // 设定样式
        Symbol lineSymbol = new Symbol();

        Symbol.Color lineColor = lineSymbol.new Color();
        lineColor.red = 0;
        lineColor.green = 255;
        lineColor.blue = 0;
        lineColor.alpha = 255;
        lineSymbol.setLineSymbol(lineColor, 3);
        // 生成Graphic对象
        Graphic lineGraphic = new Graphic(lineGeometry, lineSymbol);
        return lineGraphic;
    }
    public Graphic drawLine1(double geo[]) {

        p=new GeoPoint[geo.length/2];
        for(int i=0;i<p.length;i++){
            p[i] = new GeoPoint((int)(geo[2*i] * 1E6),(int)(geo[2*i+1] * 1E6));
        }
        // 构建线
        Geometry lineGeometry = new Geometry();

        lineGeometry.setPolyLine(p);
        // 设定样式
        Symbol lineSymbol = new Symbol();

        Symbol.Color lineColor = lineSymbol.new Color();
        lineColor.red = 255;
        lineColor.green = 0;
        lineColor.blue = 0;
        lineColor.alpha = 255;
        lineSymbol.setLineSymbol(lineColor, 3);
        // 生成Graphic对象
        Graphic lineGraphic = new Graphic(lineGeometry, lineSymbol);
        return lineGraphic;
    }


    /**
     * 常用事件监听，用来处理通常的网络错误，授权验证错误等
     *
     *
     */
    public class MKGeneralListenerImpl implements MKGeneralListener {

        /**
         * 一些网络状态的错误处理回调函数
         */
        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
                showToast("您的网络出错啦！");
            }
        }

        /**
         * 授权错误的时候调用的回调函数
         */
        @Override
        public void onGetPermissionState(int iError) {
            if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
                showToast("API KEY错误, 请检查！");
            }
        }

    }


    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mMapView.destroy();
        super.onDestroy();
    }
    /**
     * 显示Toast消息
     *
     * @param msg
     */
    private void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /*
     * 要处理overlay点击事件时需要继承ItemizedOverlay
     * 不处理点击事件时可直接生成ItemizedOverlay.
     */
    class OverlayTest extends ItemizedOverlay<OverlayItem> {
        //用MapView构造ItemizedOverlay
//        public OverlayTest(Drawable mark,MapView mapView){
//            super(mark,mapView);
//        }
        public OverlayTest(Drawable mark,MapView mapView){
            super(mark,mapView);
        }
        protected boolean onTap(int index) {
            //在此处理item点击事件
            LayoutInflater factory = LayoutInflater.from(DianZiMap.this);// 创建LayoutInflater对象
			final View myView = factory.inflate(R.layout.mapdialog, null); // 将布局文件转换为View

            AlertDialog.Builder builder = new AlertDialog.Builder(DianZiMap.this);
            builder.setView(myView);
            builder.setPositiveButton("关闭",                    // 设置确定按钮
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();

                        }
                    });

            Dialog dialog = builder.create();
            dialog.show();
            showToast("item onTap: "+index);
            return true;
        }
        public boolean onTap(GeoPoint pt, MapView mapView){
            //在此处理MapView的点击事件，当返回 true时
            super.onTap(pt,mapView);
            return false;
        }

    }

}
