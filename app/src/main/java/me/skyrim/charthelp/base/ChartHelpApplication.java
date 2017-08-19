package me.skyrim.charthelp.base;

import android.app.Application;
import android.content.Context;

import com.koma.greendao.gen.DaoSession;

/**
 * Created by Obl on 2017/8/18.
 * me.skyrim.charthelp
 */

public class ChartHelpApplication extends Application {
    public GreenDaoManager greenDaoManager;
    public static DaoSession daoSession;
    public static Context baseContent;

    @Override
    public void onCreate() {
        super.onCreate();
        greenDaoManager = GreenDaoManager.getInstance(this);
        daoSession = greenDaoManager.getSession();
        baseContent = getBaseContext();
    }
}
