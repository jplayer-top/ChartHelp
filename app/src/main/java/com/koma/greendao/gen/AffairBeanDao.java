package com.koma.greendao.gen;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import me.skyrim.charthelp.dao.AffairBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "AFFAIR_BEAN".
*/
public class AffairBeanDao extends AbstractDao<AffairBean, Long> {

    public static final String TABLENAME = "AFFAIR_BEAN";

    /**
     * Properties of entity AffairBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Content = new Property(1, String.class, "content", false, "CONTENT");
        public final static Property Price = new Property(2, float.class, "price", false, "PRICE");
        public final static Property Date = new Property(3, String.class, "date", false, "DATE");
        public final static Property Count = new Property(4, int.class, "count", false, "COUNT");
        public final static Property CountPrice = new Property(5, float.class, "countPrice", false, "COUNT_PRICE");
        public final static Property WorkerId = new Property(6, long.class, "workerId", false, "WORKER_ID");
    }

    private Query<AffairBean> workerBean_AffairBeenQuery;

    public AffairBeanDao(DaoConfig config) {
        super(config);
    }
    
    public AffairBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"AFFAIR_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"CONTENT\" TEXT," + // 1: content
                "\"PRICE\" REAL NOT NULL ," + // 2: price
                "\"DATE\" TEXT," + // 3: date
                "\"COUNT\" INTEGER NOT NULL ," + // 4: count
                "\"COUNT_PRICE\" REAL NOT NULL ," + // 5: countPrice
                "\"WORKER_ID\" INTEGER NOT NULL );"); // 6: workerId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"AFFAIR_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AffairBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(2, content);
        }
        stmt.bindDouble(3, entity.getPrice());
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(4, date);
        }
        stmt.bindLong(5, entity.getCount());
        stmt.bindDouble(6, entity.getCountPrice());
        stmt.bindLong(7, entity.getWorkerId());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AffairBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(2, content);
        }
        stmt.bindDouble(3, entity.getPrice());
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(4, date);
        }
        stmt.bindLong(5, entity.getCount());
        stmt.bindDouble(6, entity.getCountPrice());
        stmt.bindLong(7, entity.getWorkerId());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AffairBean readEntity(Cursor cursor, int offset) {
        AffairBean entity = new AffairBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // content
            cursor.getFloat(offset + 2), // price
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // date
            cursor.getInt(offset + 4), // count
            cursor.getFloat(offset + 5), // countPrice
            cursor.getLong(offset + 6) // workerId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AffairBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setContent(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPrice(cursor.getFloat(offset + 2));
        entity.setDate(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCount(cursor.getInt(offset + 4));
        entity.setCountPrice(cursor.getFloat(offset + 5));
        entity.setWorkerId(cursor.getLong(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AffairBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AffairBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AffairBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "affairBeen" to-many relationship of WorkerBean. */
    public List<AffairBean> _queryWorkerBean_AffairBeen(long workerId) {
        synchronized (this) {
            if (workerBean_AffairBeenQuery == null) {
                QueryBuilder<AffairBean> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.WorkerId.eq(null));
                workerBean_AffairBeenQuery = queryBuilder.build();
            }
        }
        Query<AffairBean> query = workerBean_AffairBeenQuery.forCurrentThread();
        query.setParameter(0, workerId);
        return query.list();
    }

}