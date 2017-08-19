package me.skyrim.charthelp.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.koma.greendao.gen.AffairBeanDao;
import com.koma.greendao.gen.DepartmentBeanDao;
import com.koma.greendao.gen.WorkerBeanDao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import me.skyrim.charthelp.R;
import me.skyrim.charthelp.adapter.WorkerAdapter;
import me.skyrim.charthelp.base.BaseActivity;
import me.skyrim.charthelp.base.BaseFunc;
import me.skyrim.charthelp.base.BeanDaoManager;
import me.skyrim.charthelp.dao.AffairBean;
import me.skyrim.charthelp.dao.DepartmentBean;
import me.skyrim.charthelp.dao.WorkerBean;
import me.skyrim.charthelp.event.UpdateDepartmentEvent;
import me.skyrim.charthelp.event.WorkerTotalMoneyEvent;

/**
 * Created by Obl on 2017/8/18.
 * me.skyrim.charthelp.activity
 */

public class WorkerActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tvDepartmentTotalPrice)
    TextView mTvDepartmentTotalPrice;
    private WorkerBeanDao mWorkerBeanDao;
    private WorkerAdapter mAdapter;
    private long mDepartmentId;
    private List<WorkerBean> mBeanList;

    @Override
    public void bindData() {
        mDepartmentId = mBundle.getLong("departmentId");
        mTvTitle.setText(mBundle.getString("departmentName"));
        mWorkerBeanDao = BeanDaoManager.Builder().getWorkerDao();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        refreshData();
        mAdapter = new WorkerAdapter(this, mBeanList);
        mRecyclerView.setAdapter(mAdapter);
        View footer = View.inflate(this, R.layout.adapter_footer_worker, null);
        mAdapter.addFooterView(footer);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("departmentId", mDepartmentId);
                BaseFunc.startActivity(mBaseActivity, UserActivity.class, bundle);
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WorkerBean workerBean = mWorkerBeanDao.queryBuilder().where(WorkerBeanDao.Properties.Id.eq
                        (mAdapter.getData().get(position)
                                .getId())).unique();
                switch (view.getId()) {
                    case R.id.tv_delete:
                        mWorkerBeanDao.delete(workerBean);
                        mAdapter.remove(position);
                        AffairBeanDao affairDao = BeanDaoManager.Builder().getAffairDao();
                        List<AffairBean> list = affairDao.queryBuilder().where(AffairBeanDao.Properties.WorkerId.eq
                                (workerBean.getId())).list();
                        for (AffairBean affairBean : list) {
                            affairDao.delete(affairBean);
                        }
                        refreshData();
                        break;
                    default:
                        Bundle bundle = new Bundle();
                        bundle.putLong("workerId", workerBean.getId());
                        bundle.putFloat("totalMoney", workerBean.getTotalPrice());
                        bundle.putString("departmentName", mBundle.getString("departmentName"));
                        bundle.putString("workerName", workerBean.getWorkerName());
                        BaseFunc.startActivity(mBaseActivity, AffairActivity.class, bundle);
                        break;
                }
                return false;
            }
        });
    }

    private void refreshData() {
        mBeanList = mWorkerBeanDao.queryBuilder().where(WorkerBeanDao.Properties.DepartmentId.eq(mDepartmentId))
                .list();
        float curPrice = 0.00f;
        for (WorkerBean workerBean : mBeanList) {
            curPrice += workerBean.getTotalPrice();
        }
        mTvDepartmentTotalPrice.setText(String.format(Locale.CHINA, "该部门当前花费 %.2f", curPrice));
        DepartmentBeanDao departmentDao = BeanDaoManager.Builder().getDepartmentDao();
        DepartmentBean departmentBean = departmentDao.queryBuilder().where(DepartmentBeanDao.Properties.Id.eq
                (mDepartmentId)).unique();
        departmentBean.setDepartmentTotalPrice(curPrice);
        departmentDao.update(departmentBean);
        EventBus.getDefault().post(new UpdateDepartmentEvent());
    }

    @Subscribe
    public void insertWorker(UserActivity.InsertWorkerEvent event) {
        refreshData();
        mAdapter.setNewData(mBeanList);
    }

    @Subscribe
    public void updateEvent(WorkerTotalMoneyEvent event) {
        refreshData();
        mAdapter.setNewData(mBeanList);
    }

    @Override
    public int initLayout() {
        EventBus.getDefault().register(this);
        mBundle = getIntent().getBundleExtra("bundle");
        return R.layout.activity_worker;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
