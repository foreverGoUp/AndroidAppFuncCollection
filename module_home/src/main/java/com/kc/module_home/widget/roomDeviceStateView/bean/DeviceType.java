package com.kc.module_home.widget.roomDeviceStateView.bean;

/**
 * Created by ckc on 2017/8/2.
 *
 * @param value 可作为回调值传到view外部
 * @param name  可作为view显示的设备类型名称
 */

public class DeviceType {

    public static final String DEVICE_NATHER = "nather";//新风
    public static final String DEVICE_VEIGA = "veiga";//地暖
    public static final String DEVICE_HOPE = "hope";//播放器
    public static final String DEVICE_LAMP = "lamp";//灯
    public static final String DEVICE_WINDOW = "curtain";//窗帘

    private String value;
    private String name;
    private int clientDeviceType;

    public DeviceType(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public DeviceType(String name, int clientDeviceType) {
        this.name = name;
        this.clientDeviceType = clientDeviceType;
    }

    public int getClientDeviceType() {
        return clientDeviceType;
    }

    public void setClientDeviceType(int clientDeviceType) {
        this.clientDeviceType = clientDeviceType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
