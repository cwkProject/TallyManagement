package com.port.tally.management.activity;

/**
 * Created by song on 2015/9/20.
 */
import android.app.Activity;
import android.os.Bundle;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.Geometry;
import com.baidu.mapapi.map.Graphic;
import com.baidu.mapapi.map.GraphicsOverlay;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Symbol;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.port.tally.management.R;

public class electronicMap extends Activity{
    // 地图相关
    private MapView mMapView = null;
    private BMapManager mBMapManager;
    private MapController mMapController = null;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBMapManager = new BMapManager(this);

        //第一个参数是API key,
        //第二个参数是常用事件监听，用来处理通常的网络错误，授权验证错误等，你也可以不添加这个回调接口PTMdf5ZfHZkhRwHvAtUwYpZX

        mBMapManager.init("cgTaWnGGDTuUzBXdCMSXTKqE", new MKGeneralListenerImpl());
        setContentView(R.layout.my_position);
        mMapView = (MapView) findViewById(R.id.bmapView); //获取百度地图控件实例
        mMapController = mMapView.getController(); //获取地图控制器
        mMapController.enableClick(true);   //设置地图是否响应点击事件
        double mLat = 34.740672;
        double mLon = 119.380729;
        int lat = (int) (mLat * 1E6);
        int lon = (int) (mLon * 1E6);
        GeoPoint pt1 = new GeoPoint(lat, lon);
        mMapController.setCenter(pt1);

        mMapController.setZoom(16);   //设置地图缩放级别
        mMapView.setBuiltInZoomControls(true);   //显示内置缩放控件
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay(mMapView);
        mMapView.getOverlays().add(graphicsOverlay);
        // 添加多边形
        graphicsOverlay.setData(drawPolygon());






        // 执行地图刷新使生效
        mMapView.refresh();
    }

    public Graphic drawPolygon() {

        double mLat = 34.740672;
        double mLon = 119.380729;
        int lat = (int) (mLat * 1E6);
        int lon = (int) (mLon * 1E6);
        GeoPoint pt1 = new GeoPoint(lat, lon);
        mLat = 34.741266;
        mLon = 119.381591;
        lat = (int) (mLat * 1E6);
        lon = (int) (mLon * 1E6);
        GeoPoint pt2 = new GeoPoint(lat, lon);
        mLat = 34.740576;
        mLon = 119.381183;
        lat = (int) (mLat * 1E6);
        lon = (int) (mLon * 1E6);
        GeoPoint pt3 = new GeoPoint(lat, lon);
        mLat = 34.740928;
        mLon = 119.380715;
        lat = (int) (mLat * 1E6);
        lon = (int) (mLon * 1E6);
        GeoPoint pt4 = new GeoPoint(lat, lon);
        mLat = 34.740728;
        mLon =  119.380715;
        lat = (int) (mLat * 1E6);
        lon = (int) (mLon * 1E6);


        // 构建多边形
        Geometry polygonGeometry = new Geometry();
        // 设置多边形坐标
        GeoPoint[] polygonPoints = new GeoPoint[5];
        polygonPoints[0] = pt1;
        polygonPoints[1] = pt2;
        polygonPoints[2] = pt3;
        polygonPoints[3] = pt4;

        polygonGeometry.setPolygon(polygonPoints);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMapView.onRestoreInstanceState(savedInstanceState);
    }
    /**
     * 常用事件监听，用来处理通常的网络错误，授权验证错误等
     *
     * @author xiaanming
     */
    private class MKGeneralListenerImpl implements MKGeneralListener {

        /**
         * 一些网络状态的错误处理回调函数
         */
        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {

            }
        }

        /**
         * 授权错误的时候调用的回调函数
         */
        @Override
        public void onGetPermissionState(int iError) {
            if (iError == MKEvent.ERROR_PERMISSION_DENIED) {

            }
        }

    }
}