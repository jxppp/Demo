
package com.example.myapplication.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.celink.bluetoothmanager.BleApp;
import com.celink.common.common.CrashHelper;
import com.googfit.App;
import com.googfit.R;
import com.googfit.activity.account.accountk3.AccountK3DesktopSetting;
import com.googfit.activity.history.gps.GpsActivity;
import com.googfit.activity.history.gps.map.MapFactory;
import com.googfit.activity.history.gps.tool.Place;
import com.googfit.api.base.OauthAPI;
import com.googfit.datamanager.control.historyproxy.HistoryProxyFactory;
import com.googfit.datamanager.entity.Options;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class SharedPreferenceUtils {

    private SharedPreferences globalSharedPref; // 用于全局的设置参数保存
    private SharedPreferences curUserSharedPref;// 当前用户的pref

    public static final String SYSTEM_GLOBAL_PARAM_SETTING = "SYSTEM_GLOBAL_PARAM_SETTING";
    public static final String CUR_USER_PARAM_SETTING = "CUR_USER_PARAM_SETTING";

    public static final String LAUG_STR = "LAUG_STR";

    private static SharedPreferenceUtils instance;

    public static final String OLDCLOCK = "OLDCLOCK";// 闹钟

    private SharedPreferenceUtils() {
        globalSharedPref = App.getInstance().getSharedPreferences(SYSTEM_GLOBAL_PARAM_SETTING, Context.MODE_PRIVATE);
        curUserSharedPref = App.getInstance().getSharedPreferences(CUR_USER_PARAM_SETTING, Context.MODE_PRIVATE);
    }

    public synchronized static SharedPreferenceUtils getInstance() {
        if (instance == null) {
            instance = new SharedPreferenceUtils();
        }
        return instance;
    }



}
