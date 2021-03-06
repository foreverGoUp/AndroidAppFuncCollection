package com.kc.common_service.widget.roomDeviceStateView.bean;


/**
 * @param devType 设备类型，值在DeviceType类中。
 * @param state   设备状态，值null：设备离线，"0"：关，其他：开。
 * @param roomId  所属房间id。
 * @ModifiedBy 修改人
 * @Project DeviceList.java
 * @Desciption 设备列表
 * @Author SuFH
 * @Data 2017年3月15日上午8:50:34
 * <p>
 * 自定义我的地盘用到的参数
 */
public class DeviceList {
    private String id;// 设备ID
    private String state; // 设备状态 可能为空
    private Integer roomId;// 房间ID
    private String devType;// 设备类型
    private String type;// 设备类型

    //客户端
    private String detailDevType;// 详细设备类型

    @Override
    public String toString() {
        return "DeviceList{" +
                "id='" + id + '\'' +
                ", state='" + state + '\'' +
                ", roomId=" + roomId +
                ", devType='" + devType + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public DeviceList(String id, String state, Integer roomId, String devType, String type) {
        this.id = id;
        this.state = state;
        this.roomId = roomId;
        this.devType = devType;
        this.type = type;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}
