package com.kc.module_home.widget.roomDeviceStateView;

import android.util.Log;

import com.kc.module_home.widget.roomDeviceStateView.bean.DeviceList;
import com.kc.module_home.widget.roomDeviceStateView.bean.DeviceType;
import com.kc.module_home.widget.roomDeviceStateView.bean.RoomInfo;
import com.kc.module_home.widget.roomDeviceStateView.tool.DeviceTool;

import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */

public class ViewUtil {

    private static final String TAG = "ViewUtil";
    public static final int DEVICE_STATE_EMPTY = 0;
    public static final int DEVICE_STATE_ON = 1;
    public static final int DEVICE_STATE_OFF = 2;
    private static final String VALUE_ON = "1";

    public static int getDeviceOnNumber(int clientDType, List<RoomInfo> roomInfos, List<DeviceList> deviceLists) {
        int count = 0;
        int roomSize = roomInfos.size();
        for (int i = 0; i < roomSize; i++) {
            if (isDeviceExist(roomInfos.get(i).getRoomId(), clientDType, deviceLists)) {
                if (!isDeviceOff(roomInfos.get(i).getRoomId(), clientDType, deviceLists)) {
//                    Log.e(TAG, "isDeviceOff: clientDType=" + clientDType + ",RoomId=" + roomInfos.get(i).getRoomId());
                    count++;
                }
            }
        }
//        Log.e(TAG, "count=" + count);
        return count;
    }

    public static int getDeviceOnNumber(Integer roomId, List<DeviceType> deviceTypes, List<DeviceList> deviceLists) {
        int count = 0;
        int deviceTypeSize = deviceTypes.size();
        for (int i = 0; i < deviceTypeSize; i++) {
            if (isDeviceExist(roomId, deviceTypes.get(i).getClientDeviceType(), deviceLists)) {
                if (!isDeviceOff(roomId, deviceTypes.get(i).getClientDeviceType(), deviceLists)) {
//                    Log.e(TAG, "isDeviceOff: roomId=" + roomId + ",clientDType=" + deviceTypes.get(i).getClientDeviceType());
                    count++;
                }
            }
        }
//        Log.e(TAG, "count=" + count);
        return count;
    }

    public static void toggleSelectedBox(Integer roomId, int clientDType, List<DeviceList> deviceLists, List<String> selectedBox) {
        if (isDeviceExist(roomId, clientDType, deviceLists)) {
            StringBuffer stringBuffer = new StringBuffer();
            String selectTag = stringBuffer.append(roomId).append(",").append(clientDType).toString();
            if (selectedBox.contains(selectTag)) {
                selectedBox.remove(selectTag);
            } else {
                selectedBox.add(selectTag);
            }
        }
    }

    public static String getSelectedTag(Integer roomId, int clientDType) {
        StringBuffer stringBuffer = new StringBuffer();
        String selectTag = stringBuffer.append(roomId).append(",").append(clientDType).toString();
        return selectTag;
    }

    public static void toggleSelectedBoxForDeviceType(int clientDType, List<RoomInfo> roomInfos, List<String> selectedBoxs, List<DeviceList> deviceLists) {
        Log.e(TAG, "-----------toggleSelectedBoxForDeviceType-----------");
        int roomSize = roomInfos.size();
        boolean add = false;
        String selectedTag;
        for (int i = 0; i < roomSize; i++) {
            selectedTag = getSelectedTag(roomInfos.get(i).getRoomId(), clientDType);
            if (!selectedBoxs.contains(selectedTag) && isDeviceExist(roomInfos.get(i).getRoomId(), clientDType, deviceLists)) {
                add = true;
                break;
            }
        }
        if (add) {
            for (int i = 0; i < roomSize; i++) {
                selectedTag = getSelectedTag(roomInfos.get(i).getRoomId(), clientDType);
                if (!selectedBoxs.contains(selectedTag) && isDeviceExist(roomInfos.get(i).getRoomId(), clientDType, deviceLists)) {
                    selectedBoxs.add(selectedTag);
                    Log.e(TAG, "添加已选中房间的设备：" + selectedTag);
                }
            }
        } else {
            for (int i = 0; i < roomSize; i++) {
                selectedTag = getSelectedTag(roomInfos.get(i).getRoomId(), clientDType);
                if (selectedBoxs.contains(selectedTag)) {
                    selectedBoxs.remove(selectedTag);
                    Log.e(TAG, "移除已选中房间的设备：" + selectedTag);
                }
            }
        }
        Log.e(TAG, "操作后的selectedBoxs大小：" + selectedBoxs.size());
    }

    public static void toggleSelectedBoxForRoom(Integer roomId, List<DeviceType> deviceTypes, List<String> selectedBoxs, List<DeviceList> deviceLists) {
        Log.e(TAG, "-----------toggleSelectedBoxForRoom-----------");
        int deviceTypeSize = deviceTypes.size();
        boolean add = false;
        String selectedTag;
        for (int i = 0; i < deviceTypeSize; i++) {
            selectedTag = getSelectedTag(roomId, deviceTypes.get(i).getClientDeviceType());
            if (!selectedBoxs.contains(selectedTag) && isDeviceExist(roomId, deviceTypes.get(i).getClientDeviceType(), deviceLists)) {
                add = true;
                break;
            }
        }
        if (add) {
            for (int i = 0; i < deviceTypeSize; i++) {
                selectedTag = getSelectedTag(roomId, deviceTypes.get(i).getClientDeviceType());
                if (!selectedBoxs.contains(selectedTag) && isDeviceExist(roomId, deviceTypes.get(i).getClientDeviceType(), deviceLists)) {
                    selectedBoxs.add(selectedTag);
                    Log.e(TAG, "添加已选中房间的设备：" + selectedTag);
                }
            }
        } else {
            for (int i = 0; i < deviceTypeSize; i++) {
                selectedTag = getSelectedTag(roomId, deviceTypes.get(i).getClientDeviceType());
                if (selectedBoxs.contains(selectedTag)) {
                    selectedBoxs.remove(selectedTag);
                    Log.e(TAG, "移除已选中房间的设备：" + selectedTag);
                }
            }
        }
        Log.e(TAG, "操作后的selectedBoxs大小：" + selectedBoxs.size());
    }

    public static boolean isBoxSelected(Integer roomId, int clientDType, List<String> selectedBox) {
        if (roomId == null) {
            return false;
        }
        StringBuffer stringBuffer = new StringBuffer();
        String selectTag = stringBuffer.append(roomId).append(",").append(clientDType).toString();
//        Log.e(TAG, "selectTag=" + selectTag);
        if (selectedBox.contains(selectTag)) {
//            Log.e(TAG, "isBoxSelected: contains");
            return true;
        }
//        Log.e(TAG, "isBoxSelected: not contains");
        return false;
    }

    public static int getDeviceStateTag(Integer roomId, int clientDType, List<DeviceList> list) {
        if (roomId == null || list == null) {
            return DEVICE_STATE_EMPTY;
        }
        if (isDeviceExist(roomId, clientDType, list)) {
            if (isDeviceOff(roomId, clientDType, list)) {
                return DEVICE_STATE_OFF;
            } else {
                return DEVICE_STATE_ON;
            }
        } else {
            return DEVICE_STATE_EMPTY;
        }
    }

    private static boolean isDeviceExist(Integer roomId, int clientDType, List<DeviceList> list) {
        if (roomId == null || list == null) {
            return false;
        }
        int size = list.size();
        DeviceList deviceList;
        int cDType;
        for (int i = 0; i < size; i++) {
            deviceList = list.get(i);
            cDType = DeviceTool.getClientDeviceType(deviceList.getType(), deviceList.getDevType());
            if (deviceList.getRoomId().equals(roomId) && cDType == clientDType) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDeviceOff(Integer roomId, int clientDType, List<DeviceList> list) {
        if (roomId == null || list == null) {
            return true;
        }
        int size = list.size();
        DeviceList deviceList;
        int cDType;
        for (int i = 0; i < size; i++) {
            deviceList = list.get(i);
            cDType = DeviceTool.getClientDeviceType(deviceList.getType(), deviceList.getDevType());
            if (deviceList.getRoomId().equals(roomId) && cDType == clientDType) {
                if (deviceList.getState() != null && deviceList.getState().equals(VALUE_ON)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void convertData(List<RoomInfo> roomInfos, List<Integer> roomImages, List<DeviceType> deviceTypes, List<Integer> deviceTypeImages, List<DeviceList> deviceLists) {
        //是否每个房间都有该种设备
        int roomSize = roomInfos.size();
        int dTypeSize = deviceTypes.size();
        RoomInfo roomInfo;
        DeviceType deviceType;
        int removePos;
        Log.e(TAG, ">>>>>>>>>>>>>开始遍历移除房间");
        for (int i = 0; i < roomSize; i++) {
            removePos = -1;
            roomInfo = roomInfos.get(i);
            for (int j = 0; j < dTypeSize; j++) {
                deviceType = deviceTypes.get(j);
                if (isDeviceExist(roomInfo.getRoomId(), deviceType.getClientDeviceType(), deviceLists)) {
                    break;
                }
                if (j == dTypeSize - 1) {
                    removePos = i;
                    Log.e(TAG, "该房间不存在任何一种设备：" + roomInfo.getRoomName());
                }
            }
            if (removePos != -1) {
                roomInfos.remove(removePos);
                roomImages.remove(removePos);
                roomSize--;
                i--;
            }
        }
        Log.e(TAG, ">>>>>>>>>>>>>开始遍历移除设备类型");
        for (int i = 0; i < dTypeSize; i++) {
            removePos = -1;
            deviceType = deviceTypes.get(i);
            for (int j = 0; j < roomSize; j++) {
                roomInfo = roomInfos.get(j);
                if (isDeviceExist(roomInfo.getRoomId(), deviceType.getClientDeviceType(), deviceLists)) {
                    break;
                }
                if (j == roomSize - 1) {
                    removePos = i;
                    Log.e(TAG, "该种设备不在任何一个房间：" + deviceType.getName());
                }
            }
            if (removePos != -1) {
                deviceTypes.remove(removePos);
                deviceTypeImages.remove(removePos);
                dTypeSize--;
                i--;
            }
        }
    }

//    private static void removeImage(int removePos, int[] roomImages) {
//        int[] tempArr = new int[roomImages.length - 1];
//        int len = roomImages.length;
//        Log.e(TAG, "移除前图片数组长度:" + len);
//        for (int i = 0; i < len; i++) {
//            if (i == len - 1) {
//                break;
//            }
//            if (removePos == i || i > removePos) {
//                tempArr[i] = roomImages[i + 1];
//            } else {
//                tempArr[i] = roomImages[i];
//            }
//        }
//        roomImages = tempArr;
//        Log.e(TAG, "移除后图片数组长度:" + tempArr.length);
//    }

}
