package com.zhowin.basicframework.common.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.zhowin.basicframework.R;


/**
 * Created by : Z_B on 2019/8/19.
 * describe：通知帮助类
 */
public class NotificationHelper extends ContextWrapper {

    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
    }

    private NotificationManager getManager() {
        if (manager == null) {
            return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        } else {
            return manager;
        }
    }

    /**
     * 创建渠道
     * Android8.0以上必须添加 渠道 才能显示通知
     *
     * @param id 渠道id 和 渠道名字
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(String id) {
        NotificationChannel channel = new NotificationChannel(id, id, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    /**
     * 26以上的版本
     *
     * @param id      渠道id
     * @param title   标题
     * @param content 内容
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private Notification.Builder getChannelNotification(String id, String title, String content) {
        return new Notification.Builder(getApplicationContext(), id)
                .setContentTitle(title)
                .setContentText(content)
                .setChannelId(id)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(false)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);
    }

    /**
     * 26以下的版本
     *
     * @param title   标题
     * @param content 内容
     * @return
     */
    private NotificationCompat.Builder getNotification_25(String title, String content) {
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(false)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);
    }


    /**
     * 点击不跳转
     *
     * @param id      渠道id
     * @param title   标题
     * @param content 内容
     */
    public void sendNotification(String id, String title, String content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(id);
            manager.notify(2, getChannelNotification(id, title, content).build());
        } else {
            manager.notify(2, getNotification_25(title, content).build());
        }
    }

    /**
     * 点击跳转
     *
     * @param id            渠道id
     * @param title         标题
     * @param content       内容
     * @param pendingIntent 跳转activity的intent
     */
    public void sendNotification(String id, String title, String content, PendingIntent pendingIntent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(id);
            manager.notify(2, getChannelNotification(id, title, content).setContentIntent(pendingIntent).build());
        } else {
            manager.notify(2, getNotification_25(title, content).setContentIntent(pendingIntent).build());
        }
    }

    /**
     * 取消通知
     */
    public void cancelNotification() {
        manager.cancel(2);
//        manager.cancelAll();
    }

}
