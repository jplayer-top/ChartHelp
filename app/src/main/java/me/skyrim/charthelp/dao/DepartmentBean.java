package me.skyrim.charthelp.dao;

import com.koma.greendao.gen.DaoSession;
import com.koma.greendao.gen.DepartmentBeanDao;
import com.koma.greendao.gen.WorkerBeanDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

/**
 * Created by Obl on 2017/8/18.
 * me.skyrim.charthelp.dao
 */
@Entity
public class DepartmentBean {
    @Id(autoincrement = true)
    public long id;
    @Property(nameInDb = "DEPARTMENT_NAME")
    public String departmentName;
    public float departmentTotalPrice;
    @ToMany(referencedJoinProperty = "departmentId")
    public List<WorkerBean> workerBeen;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 2067062791)
    private transient DepartmentBeanDao myDao;
    @Generated(hash = 2093996453)
    public DepartmentBean(long id, String departmentName,
            float departmentTotalPrice) {
        this.id = id;
        this.departmentName = departmentName;
        this.departmentTotalPrice = departmentTotalPrice;
    }
    @Generated(hash = 1119420877)
    public DepartmentBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getDepartmentName() {
        return this.departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public float getDepartmentTotalPrice() {
        return this.departmentTotalPrice;
    }
    public void setDepartmentTotalPrice(float departmentTotalPrice) {
        this.departmentTotalPrice = departmentTotalPrice;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 174578920)
    public List<WorkerBean> getWorkerBeen() {
        if (workerBeen == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            WorkerBeanDao targetDao = daoSession.getWorkerBeanDao();
            List<WorkerBean> workerBeenNew = targetDao
                    ._queryDepartmentBean_WorkerBeen(id);
            synchronized (this) {
                if (workerBeen == null) {
                    workerBeen = workerBeenNew;
                }
            }
        }
        return workerBeen;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 282403624)
    public synchronized void resetWorkerBeen() {
        workerBeen = null;
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
    @Generated(hash = 1590690829)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDepartmentBeanDao() : null;
    }
}
