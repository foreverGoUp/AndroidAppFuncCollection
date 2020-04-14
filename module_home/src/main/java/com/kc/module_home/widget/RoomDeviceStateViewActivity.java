package com.kc.module_home.widget;

import android.os.Bundle;

import com.dovar.router_annotation.Route;
import com.kc.common_service.base.AppBaseActivity;
import com.kc.module_home.R;
import com.kc.module_home.databinding.ActivityRoomDeviceStateViewBinding;
import com.kc.module_home.widget.roomDeviceStateView.bean.DeviceList;
import com.kc.module_home.widget.roomDeviceStateView.bean.DeviceType;
import com.kc.module_home.widget.roomDeviceStateView.bean.RoomInfo;
import com.kc.module_home.widget.roomDeviceStateView.tool.API;
import com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant;

import java.util.ArrayList;
import java.util.List;

import ckc.android.develophelp.lib.base.mvvm.BaseViewModel;

@Route(path = "widget/roomDeviceState")
public class RoomDeviceStateViewActivity extends AppBaseActivity<ActivityRoomDeviceStateViewBinding, BaseViewModel> {

    private List<RoomInfo> mRoomInfos;
    private List<Integer> mRoomImages = new ArrayList<>();
    private List<DeviceType> mDeviceTypes = new ArrayList<>();
    private List<Integer> mDeviceTypeImages = new ArrayList<>();
    private List<DeviceList> mDeviceLists;

    @Override
    protected int onConfigContentViewLayout() {
        return R.layout.activity_room_device_state_view;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        mDataBinding.zoomView.setColumnNum(4);

        //设备类型
        genDeviceTypes(false);
        //设备图片
        genDeviceTypeImages(false);
        //所有房间
        mRoomInfos = getRoomInfos();
        //房间图片
        genRoomImages(mRoomInfos);
        //所有设备
        mDeviceLists = getDevices();
        mDataBinding.zoomView.setData(mRoomInfos, mRoomImages, mDeviceTypes, mDeviceTypeImages, mDeviceLists);
    }

    private List<DeviceList> getDevices() {
        List<DeviceList> ret = new ArrayList<>();
        ret.add(new DeviceList("0", "0", 0, AppConstant.DEVICE_TYPE_WULIAN_CURTAIN_80, API.DEVICE_WULIAN));
        ret.add(new DeviceList("1", "1", 0, API.DEVICE_AIR_BOX, API.DEVICE_AIR_BOX));
        ret.add(new DeviceList("2", "0", 1, AppConstant.DEVICE_TYPE_WULIAN_LAMP_61, API.DEVICE_WULIAN));
        ret.add(new DeviceList("3", "1", 2, AppConstant.DEVICE_TYPE_WULIAN_WINDOW_65, API.DEVICE_WULIAN));
        ret.add(new DeviceList("4", "0", 3, AppConstant.DEVICE_TYPE_WULIAN_SOCKET_50, API.DEVICE_WULIAN));
        ret.add(new DeviceList("5", "1", 4, API.DEVICE_TV, API.DEVICE_TV));
        ret.add(new DeviceList("6", "0", 5, API.DEVICE_HOPE, API.DEVICE_HOPE));
        return ret;
    }

    private List<RoomInfo> getRoomInfos() {
        List<RoomInfo> ret = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ret.add(new RoomInfo(i, "房间" + i));
        }
        return ret;
    }

    @Override
    protected void onConfigViewModelAndBindDataBinding() {

    }

    private void genRoomImages(List<RoomInfo> roomInfos) {
        mRoomImages.clear();
        int size = roomInfos.size();
        for (int i = 0; i < size; i++) {
            mRoomImages.add(R.drawable.fast_ctrl_img_1);
        }
    }


    /*
     * 从上而下 顺序如下：灯光，空调，窗帘，窗户，插座，新风，地暖，加湿器，扫地机，空气净化器，音乐播放器
     * */
    private void genDeviceTypes(boolean normalSort) {
        mDeviceTypes.clear();
        DeviceType deviceType;
        if (normalSort) {
            deviceType = new DeviceType("灯", AppConstant.CLIENT_DEVICE_TYPE_LAMP);
            mDeviceTypes.add(deviceType);
            deviceType = new DeviceType("空调", AppConstant.CLIENT_DEVICE_TYPE_AIR);
            mDeviceTypes.add(deviceType);
            deviceType = new DeviceType("窗帘", AppConstant.CLIENT_DEVICE_TYPE_CURTAIN);
            mDeviceTypes.add(deviceType);
            deviceType = new DeviceType("窗户", AppConstant.CLIENT_DEVICE_TYPE_WINDOW);
            mDeviceTypes.add(deviceType);
            deviceType = new DeviceType("插座", AppConstant.CLIENT_DEVICE_TYPE_SOCKET);
            mDeviceTypes.add(deviceType);
            deviceType = new DeviceType("新风", AppConstant.CLIENT_DEVICE_TYPE_FRESH_AIR);
            mDeviceTypes.add(deviceType);
            deviceType = new DeviceType("地暖", AppConstant.CLIENT_DEVICE_TYPE_FLOOR_HEATING);
            mDeviceTypes.add(deviceType);
            deviceType = new DeviceType("加湿", AppConstant.CLIENT_DEVICE_TYPE_HUMIDIFIER);
            mDeviceTypes.add(deviceType);
            deviceType = new DeviceType("扫地机", AppConstant.CLIENT_DEVICE_TYPE_ROBOT_CLEANER);
            mDeviceTypes.add(deviceType);
            deviceType = new DeviceType("净化器", AppConstant.CLIENT_DEVICE_TYPE_AIR_PURIFIER);
            mDeviceTypes.add(deviceType);
            deviceType = new DeviceType("播放器", AppConstant.CLIENT_DEVICE_TYPE_MUSIC_PLAYER);
            mDeviceTypes.add(deviceType);
        } else {
            deviceType = new DeviceType("播放器", AppConstant.CLIENT_DEVICE_TYPE_MUSIC_PLAYER);
            mDeviceTypes.add(deviceType);
            deviceType = new DeviceType("净化器", AppConstant.CLIENT_DEVICE_TYPE_AIR_PURIFIER);
            mDeviceTypes.add(deviceType);
            deviceType = new DeviceType("扫地机", AppConstant.CLIENT_DEVICE_TYPE_ROBOT_CLEANER);
            mDeviceTypes.add(deviceType);
            deviceType = new DeviceType("加湿", AppConstant.CLIENT_DEVICE_TYPE_HUMIDIFIER);
            mDeviceTypes.add(deviceType);
            deviceType = new DeviceType("地暖", AppConstant.CLIENT_DEVICE_TYPE_FLOOR_HEATING);
            mDeviceTypes.add(deviceType);
            deviceType = new DeviceType("新风", AppConstant.CLIENT_DEVICE_TYPE_FRESH_AIR);
            mDeviceTypes.add(deviceType);
            deviceType = new DeviceType("插座", AppConstant.CLIENT_DEVICE_TYPE_SOCKET);
            mDeviceTypes.add(deviceType);
            deviceType = new DeviceType("窗户", AppConstant.CLIENT_DEVICE_TYPE_WINDOW);
            mDeviceTypes.add(deviceType);
            deviceType = new DeviceType("窗帘", AppConstant.CLIENT_DEVICE_TYPE_CURTAIN);
            mDeviceTypes.add(deviceType);
            deviceType = new DeviceType("空调", AppConstant.CLIENT_DEVICE_TYPE_AIR);
            mDeviceTypes.add(deviceType);
            deviceType = new DeviceType("灯", AppConstant.CLIENT_DEVICE_TYPE_LAMP);
            mDeviceTypes.add(deviceType);
        }
    }

    private void genDeviceTypeImages(boolean normalSort) {
        mDeviceTypeImages.clear();
        if (normalSort) {
            mDeviceTypeImages.add(R.drawable.my_zone_device_lamp);
            mDeviceTypeImages.add(R.drawable.my_zone_device_air);
            mDeviceTypeImages.add(R.drawable.my_zone_device_curtain);
            mDeviceTypeImages.add(R.drawable.my_zone_device_window);
            mDeviceTypeImages.add(R.drawable.my_zone_device_socket);
            mDeviceTypeImages.add(R.drawable.my_zone_device_new_wind);
            mDeviceTypeImages.add(R.drawable.my_zone_device_floor_h);
            mDeviceTypeImages.add(R.drawable.my_zone_device_humidifier);
            mDeviceTypeImages.add(R.drawable.my_zone_device_robot_cleaner);
            mDeviceTypeImages.add(R.drawable.my_zone_device_air_purifier);
            mDeviceTypeImages.add(R.drawable.my_zone_device_music);
        } else {
            mDeviceTypeImages.add(R.drawable.my_zone_device_music);
            mDeviceTypeImages.add(R.drawable.my_zone_device_air_purifier);
            mDeviceTypeImages.add(R.drawable.my_zone_device_robot_cleaner);
            mDeviceTypeImages.add(R.drawable.my_zone_device_humidifier);
            mDeviceTypeImages.add(R.drawable.my_zone_device_floor_h);
            mDeviceTypeImages.add(R.drawable.my_zone_device_new_wind);
            mDeviceTypeImages.add(R.drawable.my_zone_device_socket);
            mDeviceTypeImages.add(R.drawable.my_zone_device_window);
            mDeviceTypeImages.add(R.drawable.my_zone_device_curtain);
            mDeviceTypeImages.add(R.drawable.my_zone_device_air);
            mDeviceTypeImages.add(R.drawable.my_zone_device_lamp);
        }
    }
}
