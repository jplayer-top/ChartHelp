package me.skyrim.charthelp.base;

import com.koma.greendao.gen.AffairBeanDao;
import com.koma.greendao.gen.DaoSession;
import com.koma.greendao.gen.DepartmentBeanDao;
import com.koma.greendao.gen.WorkerBeanDao;

/**
 * Created by Obl on 2017/8/18.
 * me.skyrim.charthelp.base
 */

public class BeanDaoManager {
    private DaoSession session;
    private static BeanDaoManager mBeanDaoManager;

    public static BeanDaoManager Builder() {
        if (mBeanDaoManager == null) {
            mBeanDaoManager = new BeanDaoManager();
        }
        return mBeanDaoManager;
    }

    public BeanDaoManager() {
        getSession();
    }

    public void getSession() {
        session = ChartHelpApplication.daoSession;
    }

    public DepartmentBeanDao getDepartmentDao() {
        return session.getDepartmentBeanDao();
    }

    public WorkerBeanDao getWorkerDao() {
        return session.getWorkerBeanDao();
    }

    public AffairBeanDao getAffairDao() {
        return session.getAffairBeanDao();
    }
}
