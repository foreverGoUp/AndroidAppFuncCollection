package com.kc.module_home.widget.roomDeviceStateView.tool;

import static com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant.DEVICE_TYPE_WULIAN_ALARM_01;
import static com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant.DEVICE_TYPE_WULIAN_CURTAIN_80;
import static com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant.DEVICE_TYPE_WULIAN_CURTAIN_81;
import static com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant.DEVICE_TYPE_WULIAN_GAS_VALVE_25;
import static com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant.DEVICE_TYPE_WULIAN_LAMP_61;
import static com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant.DEVICE_TYPE_WULIAN_LAMP_62;
import static com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant.DEVICE_TYPE_WULIAN_LAMP_63;
import static com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant.DEVICE_TYPE_WULIAN_LAMP_64;
import static com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant.DEVICE_TYPE_WULIAN_SOCKET_50;
import static com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant.DEVICE_TYPE_WULIAN_SOCKET_51;
import static com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant.DEVICE_TYPE_WULIAN_S_CO2_42;
import static com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant.DEVICE_TYPE_WULIAN_S_DOOR_MAGNETISM_03;
import static com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant.DEVICE_TYPE_WULIAN_S_GAS_09;
import static com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant.DEVICE_TYPE_WULIAN_S_INFRARED_02;
import static com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant.DEVICE_TYPE_WULIAN_S_LIGHT_19;
import static com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant.DEVICE_TYPE_WULIAN_S_PM_44;
import static com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant.DEVICE_TYPE_WULIAN_S_SMOKE_43;
import static com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant.DEVICE_TYPE_WULIAN_S_TEMPER_17;
import static com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant.DEVICE_TYPE_WULIAN_WINDOW_65;
import static com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant.DEVICE_TYPE_WULIAN_WINDOW_66;

public class DeviceTool {

    /**
     * 获得设备在客户端中定义的类型
     *
     * @param type       服务端返回的设备的type字段
     * @param deviceType 服务端返回的设备的deviceType字段
     */
    public static int getClientDeviceType(String type, String deviceType) {
        if (type == null || deviceType == null) {
            return AppConstant.CLIENT_DEVICE_TYPE_UNKNOWN;
        }
        Integer clientDeviceType = null;
        switch (type) {
            case API.DEVICE_CURTAIN:
                clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_CURTAIN;
                break;
            case API.DEVICE_RELAY:
                clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_RELAY;
                break;
            case API.DEVICE_SHP://shp
                switch (deviceType) {
                    case API.SHP_DEVICE_TYPE_AIR_PURIFIER:
                        clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_AIR_PURIFIER;
                        break;
                    case API.SHP_DEVICE_TYPE_WASHER:
                        clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_WASHER;
                        break;
                    case API.SHP_DEVICE_TYPE_ROBOT_CLEANER:
                        clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_ROBOT_CLEANER;
                        break;
                    case API.SHP_DEVICE_TYPE_REFRIGERATOR:
                        clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_REFRIGERATOR;
                        break;
                }
                break;
            case API.DEVICE_WULIAN://物联
                switch (deviceType) {
                    case DEVICE_TYPE_WULIAN_SOCKET_50:
                    case DEVICE_TYPE_WULIAN_SOCKET_51:
                    case API.DEVICE_OUTLET:
                        clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_SOCKET;
                        break;
//                    case DEVICE_TYPE_WULIAN_INFRARED_HELPER_22:
//                        clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_;
//                        break;
                    case DEVICE_TYPE_WULIAN_GAS_VALVE_25:
                        clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_GAS_VALVE;
                        break;
                    case DEVICE_TYPE_WULIAN_LAMP_61:
                    case DEVICE_TYPE_WULIAN_LAMP_62:
                    case DEVICE_TYPE_WULIAN_LAMP_63:
                    case DEVICE_TYPE_WULIAN_LAMP_64:
                    case API.DEVICE_LAMP:
                        clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_LAMP;
                        break;
                    case DEVICE_TYPE_WULIAN_WINDOW_65:
                    case DEVICE_TYPE_WULIAN_WINDOW_66:
                    case API.DEVICE_WINDOW:
                        clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_WINDOW;
                        break;
//                    case DEVICE_TYPE_WULIAN_SOCKET_CALCULATE_77:
//                        clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_SOCKET;
//                        break;
                    case DEVICE_TYPE_WULIAN_CURTAIN_80:
                    case DEVICE_TYPE_WULIAN_CURTAIN_81:
                    case API.DEVICE_CURTAIN:
                        clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_CURTAIN;
                        break;
                    //传感器
                    case DEVICE_TYPE_WULIAN_ALARM_01:
                        clientDeviceType = AppConstant.CLIENT_SENSOR_TYPE_ALARM;
                        break;
                    case DEVICE_TYPE_WULIAN_S_INFRARED_02:
                        clientDeviceType = AppConstant.CLIENT_SENSOR_TYPE_INFRARED;
                        break;
                    case DEVICE_TYPE_WULIAN_S_DOOR_MAGNETISM_03:
                        clientDeviceType = AppConstant.CLIENT_SENSOR_TYPE_DOOR_MAGNETISM;
                        break;
                    case DEVICE_TYPE_WULIAN_S_GAS_09:
                        clientDeviceType = AppConstant.CLIENT_SENSOR_TYPE_GAS;
                        break;
                    case DEVICE_TYPE_WULIAN_S_SMOKE_43:
                        clientDeviceType = AppConstant.CLIENT_SENSOR_TYPE_SMOKE;
                        break;
                    case DEVICE_TYPE_WULIAN_S_TEMPER_17:
                        clientDeviceType = AppConstant.CLIENT_SENSOR_TYPE_TEMPER;
                        break;
                    case DEVICE_TYPE_WULIAN_S_LIGHT_19:
                        clientDeviceType = AppConstant.CLIENT_SENSOR_TYPE_LIGHT;
                        break;
                    case DEVICE_TYPE_WULIAN_S_CO2_42:
                        clientDeviceType = AppConstant.CLIENT_SENSOR_TYPE_CO2;
                        break;
                    case DEVICE_TYPE_WULIAN_S_PM_44:
                        clientDeviceType = AppConstant.CLIENT_SENSOR_TYPE_PM;
                        break;
                    //物联强制转为加湿器类型
                    case API.DEVICE_VALVE:
                        clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_HUMIDIFIER;
                        break;
                    case API.DEVICE_VALVE_WIND:
                        clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_FRESH_AIR;
                        break;
                }
                break;
            case API.DEVICE_AIR_BOX://空调
                clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_AIR;
                break;
            case API.DEVICE_TV://电视
                clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_TV;
                break;
            case API.DEVICE_HOPE://向往
                clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_MUSIC_PLAYER;
                break;
            case API.DEVICE_NATHER://新风
                clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_FRESH_AIR;
                break;
            case API.DEVICE_VEIGA://地暖
                clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_FLOOR_HEATING;
                break;
            case API.DEVICE_HUMIDIFIER://加湿
                clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_HUMIDIFIER;
                break;
            //传感器
            case API.DEVICE_B3://b3
//                clientDeviceType = AppConstant.CLIENT_SENSOR_TYPE_B3;
                switch (deviceType) {
                    case API.DEVICE_B3_CO2:
                        clientDeviceType = AppConstant.CLIENT_SENSOR_TYPE_CO2;
                        break;
                    case API.DEVICE_B3_H:
                        clientDeviceType = AppConstant.CLIENT_SENSOR_TYPE_WET;
                        break;
                    case API.DEVICE_B3_PM:
                        clientDeviceType = AppConstant.CLIENT_SENSOR_TYPE_PM;
                        break;
                    case API.DEVICE_B3_T:
                        clientDeviceType = AppConstant.CLIENT_SENSOR_TYPE_TEMPER;
                        break;
                    case API.DEVICE_B3_TVOC:
                        clientDeviceType = AppConstant.CLIENT_SENSOR_TYPE_TVOC;
                        break;
                }
                break;
        }
        if (clientDeviceType == null) {
            clientDeviceType = AppConstant.CLIENT_DEVICE_TYPE_UNKNOWN;
        }
        return clientDeviceType;
    }
}
