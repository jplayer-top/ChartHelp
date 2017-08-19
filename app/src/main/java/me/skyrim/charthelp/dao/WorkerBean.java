package me.skyrim.charthelp.dao;

import com.koma.greendao.gen.AffairBeanDao;
import com.koma.greendao.gen.DaoSession;
import com.koma.greendao.gen.WorkerBeanDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

/**
 * Created by Obl on 2017/8/18.
 * me.skyrim.charthelp.dao
 */

@Entity
public class WorkerBean {
    @Id
    private long id;
    public long workerId;
    public long departmentId;
    public String workerName;
    public float totalPrice;
    @ToMany(referencedJoinProperty = "workerId")
    public List<AffairBean> affairBeen;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1219112274)
    private transient WorkerBeanDao myDao;
    @Generated(hash = 574210118)
    public WorkerBean(long id, long workerId, long departmentId, String workerName,
            float totalPrice) {
        this.id = id;
        this.workerId = workerId;
        this.departmentId = departmentId;
        this.workerName = workerName;
        this.totalPrice = totalPrice;
    }
    @Generated(hash = 1128361111)
    public WorkerBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getWorkerId() {
        return this.workerId;
    }
    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }
    public long getDepartmentId() {
        return this.departmentId;
    }
    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }
    public String getWorkerName() {
        return this.workerName;
    }
    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }
    public float getTotalPrice() {
        return this.totalPrice;
    }
    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1972164266)
    public List<AffairBean> getAffairBeen() {
        if (affairBeen == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AffairBeanDao targetDao = daoSession.getAffairBeanDao();
            List<AffairBean> affairBeenNew = targetDao
                    ._queryWorkerBean_AffairBeen(id);
            synchronized (this) {
                if (affairBeen == null) {
                    affairBeen = affairBeenNew;
                }
            }
        }
        return affairBeen;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 375109667)
    public synchronized void resetAffairBeen() {
        affairBeen = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 377744530)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getWorkerBeanDao() : null;
    }
}
