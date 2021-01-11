package com.zhowin.basicframework.common.model;

import com.blankj.utilcode.util.NetworkUtils;

/**
 * author Z_B
 * date :2019/11/26 9:32
 * description: 网络变化的model
 */
public class NetworkChangeEvent {

    public boolean isConnected; //是否链接
    public NetworkUtils.NetworkType networkType; //网络状态

    public NetworkChangeEvent(boolean isConnected, NetworkUtils.NetworkType networkType) {
        this.isConnected = isConnected;
        this.networkType = networkType;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public NetworkUtils.NetworkType getNetworkType() {
        return networkType;
    }

    public void setNetworkType(NetworkUtils.NetworkType networkType) {
        this.networkType = networkType;
    }
}
