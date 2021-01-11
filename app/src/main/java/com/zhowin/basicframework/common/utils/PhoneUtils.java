package com.zhowin.basicframework.common.utils;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.RequiresPermission;
import android.telephony.SmsManager;
import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;
import com.zhowin.basicframework.common.base.BaseApplication;

import java.util.List;

import static android.Manifest.permission.SEND_SMS;

/**
 * author      : Z_B
 * date       : 2018/11/16
 * function  : 手机号码的检测
 */
public final class PhoneUtils {


    /**
     * 隐藏电话号码中间的四位数
     */
    public static String mobilNumber(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 手机号验证
     *
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {

        return checkPhone(phone, false);
    }


    /**
     * 手机号验证
     * 已经匹配到最新的号码段
     *
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone, boolean toast) {
        if (TextUtils.isEmpty(phone)) {
            if (toast) ToastUtils.showLong("手机号为空");
            return false;
        }
        String PHONE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
        if (phone.length() != 11 || !phone
                .matches(PHONE_NUMBER_REG)) {
            if (toast) ToastUtils.showLong("手机号格式不对");
            return false;
        }
        return true;
    }
    /**
     * 打电话
     */
    public static void callPhone(final String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        BaseApplication.getInstance().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * 发信息
     */
    public static void sendSms(final String phoneNumber, final String content) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", content);
        BaseApplication.getInstance().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @RequiresPermission(SEND_SMS)
    public static void sendSmsSilent(final String phoneNumber, final String content) {
        if (TextUtils.isEmpty(content)) return;
        PendingIntent sentIntent = PendingIntent.getBroadcast(BaseApplication.getInstance(), 0, new Intent("send"), 0);
        SmsManager smsManager = SmsManager.getDefault();
        if (content.length() >= 70) {
            List<String> ms = smsManager.divideMessage(content);
            for (String str : ms) {
                smsManager.sendTextMessage(phoneNumber, null, str, sentIntent, null);
            }
        } else {
            smsManager.sendTextMessage(phoneNumber, null, content, sentIntent, null);
        }
    }


}
