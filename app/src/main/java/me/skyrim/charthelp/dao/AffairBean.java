package me.skyrim.charthelp.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Obl on 2017/8/18.
 * me.skyrim.charthelp.dao
 */

@Entity
public class AffairBean {
    @Id
    private Long id;
    private String content;
    private float price;
    private String date;
    private int count;
    private float countPrice;
    private long workerId;
    @Generated(hash = 1295961461)
    public AffairBean(Long id, String content, float price, String date, int count,
            float countPrice, long workerId) {
        this.id = id;
        this.content = content;
        this.price = price;
        this.date = date;
        this.count = count;
        this.countPrice = countPrice;
        this.workerId = workerId;
    }
    @Generated(hash = 2091469756)
    public AffairBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public float getPrice() {
        return this.price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getCount() {
        return this.count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public float getCountPrice() {
        return this.countPrice;
    }
    public void setCountPrice(float countPrice) {
        this.countPrice = countPrice;
    }
    public long getWorkerId() {
        return this.workerId;
    }
    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }
}
