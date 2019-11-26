package com.zhowin.basicframework.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.zhowin.basicframework.common.model.NetworkChangeEvent;
import com.zhowin.basicframework.common.utils.NetworkUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * author Z_B
 * date :2019/11/26 9:26
 * description: 网络变化的receiver
 */
public class NetworkChangedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == ConnectivityManager.CONNECTIVITY_ACTION) {
            /*判断当前网络时候可用以及网络类型*/
            boolean isConnected = NetworkUtils.isConnected();
            NetworkUtils.NetworkType networkType = NetworkUtils.getNetworkType();
            EventBus.getDefault().post(new NetworkChangeEvent(isConnected, networkType));
        }

    }
}
