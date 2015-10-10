package com.port.tally.management.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Toast;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.port.tally.management.R;
import com.port.tally.management.bean.MapBean;
import com.port.tally.management.work.MapWork;

import org.mobile.library.model.work.WorkBack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DianZiMap extends Activity {
    private Handler mHandler;
    private static LatLng latLng1;
    private  List<MapBean> dataClick;
    private static int k;
    private Toast mToast;
    private MapView mMapView = null;
    BaiduMap mbaidumap;
    private  static String[][] mapData;
    private   Double[][] latData;
    private  Double[][] lngData;
    private double geo1[] ={34.735660,119.473205,34.735941,119.473343,34.735643,119.474368,34.735345,119.474213};
    private double geo2[]={34.732146,119.471118,34.731945,119.471786,34.731323,119.471458,34.731538,119.470801};
    private double geo[]={119.317425,34.719149,119.319976,34.716716,119.324504,34.713868,119.328349,34.710604,119.329427,34.710693 ,119.332517,34.715114,119.337835,34.712651,
            119.339667,34.717132,119.335535,34.719238,119.330038,34.722028, 119.327163,34.723481,119.317425,34.719149};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.my_position);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);

        mbaidumap = mMapView.getMap();
        //设定中心点坐标
        LatLng p = new LatLng(34.732560,119.474350);

        // 将GPS设备采集的原始GPS坐标转换成百度坐标
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        // sourceLatLng待转换坐标
        converter.coord(p);
        LatLng desLatLng = converter.convert();
//        // 定义坐标点
//        LatLng point = new LatLng(39.963175, 116.400244);
        // 构建mark图标
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(desLatLng)
                .zoom(18)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mbaidumap.setMapStatus(mMapStatusUpdate);
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.tip);
        // 构建markeroption,用于在地图上添加marker
        OverlayOptions options = new MarkerOptions().icon(bitmap).position(desLatLng);
        mbaidumap.addOverlay(options);
        onLoadFromNetWork();
        mHandler = new Handler();
        mbaidumap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                latLng1 =  latLng;
                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                for ( int i = 0;i <mapData.length-2; i++) {
                    Log.i("经度", mapData.length + "经度是" +mapData[i][3] + "经度是" + mapData[i][4] + "经度是" + mapData[i][6]);
                    LatLngBounds.Builder b = new LatLngBounds.Builder();
                    if (!mapData[i][2].equals("") && !mapData[i][3].equals("")) {
                    b.include(new LatLng(Double.parseDouble(mapData[i][2]),Double.parseDouble( mapData[i][3])));}
                    if (!mapData[i][4].equals("") && !mapData[i][5].equals("")) {
                    b.include(new LatLng(Double.parseDouble(mapData[i][4]),Double.parseDouble( mapData[i][5])));}
                    if (!mapData[i][6].equals("") && !mapData[i][7].equals("")) {
                b.include(new LatLng(Double.parseDouble(mapData[i][6]),Double.parseDouble( mapData[i][7])));}
                    if (!mapData[i][8].equals("") && !mapData[i][9].equals("")) {
                b.include(new LatLng(Double.parseDouble(mapData[i][8]),Double.parseDouble( mapData[i][9])));}
                      LatLngBounds build = b.build();
                    if(build.contains(latLng1)){ showToast("点中目标");
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
                    }
                }
                                    }
                }, 2000);

            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }
//        mbaidumap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                // 这是范围
//                for ( int i = 0;i <mapData.length; i++) {
//                LatLngBounds.Builder b = new LatLngBounds.Builder();
//                    b.include(new LatLng(Double.parseDouble(mapData[i][2]),Double.parseDouble( mapData[i][3])));
//                    b.include(new LatLng(Double.parseDouble(mapData[i][4]),Double.parseDouble( mapData[i][5])));
//                b.include(new LatLng(Double.parseDouble(mapData[i][6]),Double.parseDouble( mapData[i][7])));
//                b.include(new LatLng(Double.parseDouble(mapData[i][8]),Double.parseDouble( mapData[i][9])));
//                LatLngBounds build = b.build();
//                if(build.contains(latLng)) showToast("点中目标");
//            }
//
//        }
//                    public boolean onMapPoiClick(MapPoi mapPoi) {
//                        return false;
//                    }
//         }
//        mHandler = new Handler();
//        mbaidumap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
//           public void onMapClick(LatLng latLng) {
//          //此处理点击事件
//          latLng1=latLng;
////            mHandler.postDelayed(new Runnable() {
////            @Override
////            public void run() {
//
//
////                Log.i("经度", latmin1 + "经度是" + latmax1 + "经度是" + lngmin1 + "经度是" + lngmax1);
////                for (int j = 0; j < latData.length - 2; j++) {
////                    Log.i("经度", latData[j][0] + "经度是" + latData[j][1] + "经度是" + lngData[j][0] + "经度是" + lngData[j][1]);
//          mHandler.postDelayed(new
//
//          Runnable() {
//              @Override
//              public void run () {
//                  if (rangeInDefined(latLng1.latitude, latData[j][0], latData[j][1]) && rangeInDefined(latLng1.latitude, lngData[j][0], lngData[j][1])) {
//                      showToast("你好");
//                      //在此处理item点击事件
//                      LayoutInflater factory = LayoutInflater.from(DianZiMap.this);// 创建LayoutInflater对象
//                      final View myView = factory.inflate(R.layout.mapdialog, null); // 将布局文件转换为View
//                      AlertDialog.Builder builder = new AlertDialog.Builder(DianZiMap.this);
//                      builder.setView(myView);
//                      builder.setPositiveButton("关闭",                    // 设置确定按钮
//                              new DialogInterface.OnClickListener() {
//
//                                  public void onClick(DialogInterface dialog, int whichButton) {
//                                      dialog.dismiss();
//
//                                  }
//                              });
//
//                      Dialog dialog = builder.create();
//                      dialog.show();
//                  } else {
//                      showToast("不在范围内");
//                  }
//              }
//
//          }
//
//          ,1000);
//
//
////
////            }
////        }, 1000);
//
////
//
//
////
//      }
//            public boolean onMapPoiClick(MapPoi mapPoi) {
//                return false;
//            }
//        });
//    }
//
//    );

//        mbaidumap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
//            public void onMapClick(LatLng latLng) {
//                //此处理点击事件
//                latLng1=latLng;
//
//                        Log.i("经度", latmin1 + "经度是" + latmax1 + "经度是" + lngmin1 + "经度是" + lngmax1);
//                        for (int j = 0; j < latData.length - 2; j++) {
//                            k = j;
//                            Log.i("j的值", "j的值" + j);
//                            Log.i("k的值", "k的值" + k + "最小经度latData[k][0]是" + latData[k][0] + "最大经度latData[k][1]是" + latData[k][1] + "最小纬度lngData[k][0]是" + lngData[k][0] + "最大纬度lngData[k][1]是" + lngData[k][1]);
//
//                            Log.i("坐标点击事件", "k的值" + k + "当前经度" + latLng1.latitude + "当前纬度" + latLng1.latitude + "最小经度是" + latData[k][0] + "最大经度是" + latData[k][1] + "最小纬度是" + lngData[k][0] + "最大纬度是" + lngData[k][1]);
////
//                            if (rangeInDefined(latLng1.latitude, latData[j][0], latData[j][1]) && rangeInDefined(latLng1.latitude, lngData[j][0], lngData[j][1])) {
//                                mHandler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        showToast("你好");
//                                        //在此处理item点击事件
//                                        LayoutInflater factory = LayoutInflater.from(DianZiMap.this);// 创建LayoutInflater对象
//                                        final View myView = factory.inflate(R.layout.mapdialog, null); // 将布局文件转换为View
//                                        AlertDialog.Builder builder = new AlertDialog.Builder(DianZiMap.this);
//                                        builder.setView(myView);
//                                        builder.setPositiveButton("关闭",                    // 设置确定按钮
//                                                new DialogInterface.OnClickListener() {
//
//                                                    public void onClick(DialogInterface dialog, int whichButton) {
//                                                        dialog.dismiss();
//
//                                                    }
//                                                });
//
//                                        Dialog dialog = builder.create();
//                                        dialog.show();
//                                    }
//                                }, 10000);
////                                             } else {
////                                                 showToast("不在范围内");
//                                             }
//
//
//                            }
//
//                        }
//
//
//
//            public boolean onMapPoiClick(MapPoi mapPoi) {
//                return false;
//            }
//        });

//
//    }

    public void onLoadFromNetWork() {
        MapWork mapWork = new MapWork();

        mapWork.setWorkBackListener(new WorkBack<List<MapBean>>() {

            public void doEndWork(boolean state, List<MapBean> data) {
                //经度集合
                List<Double> lat=new ArrayList<Double>();
                //纬度集合
                List<Double> lng=new ArrayList<Double>();


                mapData = new String[data.size()+1][15];
                latData = new Double[data.size()+1][2];
                lngData = new Double[data.size()+1][2];
                List<LatLng> pts = new ArrayList<LatLng>();
                LatLng pt1 = null;
                LatLng pt2 = null;
                LatLng pt3 = null;
                LatLng pt4 = null;
                LatLng pttext = null;
                int i = 0;
                for ( i = 0;i < data.size(); i++) {
                    if(!pts.isEmpty())pts.clear();
                    if(!lat.isEmpty())lat.clear();
                    if(!lng.isEmpty())lng.clear();

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
                    mapData[i][13] = data.get(i).getLongitude6(); //定义多边形的五个顶点
                    if (!data.get(i).getLatitude1().equals("") && !data.get(i).getLongitude1().equals("")) {
                        pt1 = new LatLng(Double.parseDouble(data.get(i).getLatitude1()),Double.parseDouble(data.get(i).getLongitude1()));
//                        drawText(data.get(i).getMassNum(),pt1);
                        pts.add(pt1);

                        lat.add(Double.parseDouble(data.get(i).getLatitude1()));
                        lng.add(Double.parseDouble(data.get(i).getLongitude1()));

                    }
                    if (!data.get(i).getLatitude2().equals("") && !data.get(i).getLongitude2().equals(""))
                    {
                         pt2 = new LatLng(Double.parseDouble(data.get(i).getLatitude2()),Double.parseDouble(data.get(i).getLongitude2()));
                        pts.add(pt2);

                        lat.add(Double.parseDouble(data.get(i).getLatitude2()));
                        lng.add(Double.parseDouble(data.get(i).getLongitude2()));
                    }
                    if (!data.get(i).getLatitude3().equals("") && !data.get(i).getLongitude3().equals(""))
                    {
                         pt3 = new LatLng(Double.parseDouble(data.get(i).getLatitude3()),Double.parseDouble(data.get(i).getLongitude3()));
                        pts.add(pt3);

                        lat.add(Double.parseDouble(data.get(i).getLatitude3()));
                        lng.add(Double.parseDouble(data.get(i).getLongitude3()));
                    }
                    if (!data.get(i).getLatitude4().equals("") && !data.get(i).getLongitude4().equals(""))
                    {
                         pt4 = new LatLng(Double.parseDouble(data.get(i).getLatitude4()),Double.parseDouble(data.get(i).getLongitude4()));
                        pts.add(pt4);

                        lat.add(Double.parseDouble(data.get(i).getLatitude4()));
                        lng.add(Double.parseDouble(data.get(i).getLongitude4()));
                    }
//                    System.out.println("max的值为: " + Collections.max(lat) + "min的值为: " + Collections.min(lat));

                   if(!pts.isEmpty()) {
                       Double textlat =  (Collections.min(lat)+Collections.max(lat))/2;
                       Double textlng =  (Collections.min(lng)+Collections.max(lng))/2;
                       pttext =new LatLng(textlat,textlng);
                       Log.i("+ \"文本\" +", textlat + "纬度是" + textlng);
                       drawText(data.get(i).getMassNum(), pttext);

                       drawPolygon(pts);
                       Log.i("+ \"纬度是\" +", Collections.min(lat) + "纬度是" + Collections.max(lat) + "纬度是" + Collections.min(lng) + "纬度是" + Collections.max(lng));
                       latData[i][0] = Collections.min(lat);
                       latData[i][1] = Collections.max(lat);
                       lngData[i][0] = Collections.min(lng);
                       lngData[i][1] = Collections.max(lng);
                       Log.i("+ \"纬度是1\" +", latData[i][0] + "纬度是1" +latData[i][1] + "纬度是1" + lngData[i][0] + "纬度是1" +  lngData[i][1]);
                       //所点击的坐标是否在图层的坐标中，在的话则显示对话框，不在的话就不显示，
                       //得到坐标，得到经纬度，判断经度是否在这些图层中的某个图层中，判断纬度是否在这些图层中的某个图层中
                   }
                }

            }
        });

        mapWork.beginExecute();

    }
    private void drawText(String str,LatLng pt1){
        OverlayOptions textOptions = new TextOptions()
                .bgColor(0xAAFFFF00)
                .fontSize(12)
                .fontColor(0xFFFF00FF)
                .text(str)
                .rotate(-30)
                .position(pt1);
        //(2)在地图上添加该文字对象并显示
        mbaidumap.addOverlay(textOptions);
    }
     private void drawPolygon( List<LatLng> pts){

         //纬度集合
         List<Double> lng=new ArrayList<Double>();
         OverlayOptions polygonOption = new PolygonOptions()
                 .points(pts)
                 .stroke(new Stroke(4, 0xAA00FF00))
                 .fillColor(0xAAFFFF00);
         Log.i("onMapClick", pts.toString());
         //在地图上添加多边形Option，用于显示
         mbaidumap.addOverlay(polygonOption);

     }
    private boolean rangeInDefined(Double current, Double min, Double max)
    {
        Log.i("rangeInDefined","当前值" + current + "最小值" + min + "最大值" + max );
        return Math.max(min, current) == Math.min(current, max);
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
        mMapView.onDestroy();
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


}
