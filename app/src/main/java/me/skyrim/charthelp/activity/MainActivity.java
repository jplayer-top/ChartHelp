package me.skyrim.charthelp.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.koma.greendao.gen.DepartmentBeanDao;
import com.koma.greendao.gen.WorkerBeanDao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import me.skyrim.charthelp.R;
import me.skyrim.charthelp.adapter.DepartmentAdapter;
import me.skyrim.charthelp.base.BaseActivity;
import me.skyrim.charthelp.base.BaseFunc;
import me.skyrim.charthelp.base.BeanDaoManager;
import me.skyrim.charthelp.dao.DepartmentBean;
import me.skyrim.charthelp.dao.WorkerBean;
import me.skyrim.charthelp.event.UpdateDepartmentEvent;
import me.skyrim.charthelp.ui.CreateDepartmentDialog;

public class MainActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tvTotalPrice)
    TextView tvTotalPrice;
    private CreateDepartmentDialog mCreateDepartmentDialog;
    private DepartmentBeanDao mDepartmentDao;
    private DepartmentAdapter mAdapter;

    @Override
    public int initLayout() {
        EventBus.getDefault().register(this);
        return R.layout.activity_main;
    }

    @Override
    public void bindData() {
        mIvToolBarLeft.setVisibility(View.GONE);
        mDepartmentDao = BeanDaoManager.Builder().getDepartmentDao();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<DepartmentBean> beanList = mDepartmentDao.queryBuilder().list();
        refreshTotal(beanList);
        mAdapter = new DepartmentAdapter(this, beanList);
        mRecyclerView.setAdapter(mAdapter);
        View footer = View.inflate(this, R.layout.adapter_footer_department, null);
        mAdapter.addFooterView(footer);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCreateDepartmentDialog != null && mCreateDepartmentDialog.isShowing()) {
                    return;
                }
                mCreateDepartmentDialog = new CreateDepartmentDialog(mBaseActivity);
                mCreateDepartmentDialog.show();
            }
        });

        mAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                if (mCreateDepartmentDialog != null && mCreateDepartmentDialog.isShowing()) {
                    return;
                }
                curPos = position;
                mCreateDepartmentDialog = new CreateDepartmentDialog(mBaseActivity, mAdapter.getData().get(position));
                mCreateDepartmentDialog.show();
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                DepartmentBean departmentBean = mDepartmentDao.queryBuilder().where(DepartmentBeanDao.Properties.Id.eq(mAdapter.getData().get(position)
                        .getId())).unique();
                switch (view.getId()) {
                    case R.id.tv_delete:
                        mDepartmentDao.delete(departmentBean);
                        mAdapter.remove(position);
                        WorkerBeanDao workerBeanDao = BeanDaoManager.Builder().getWorkerDao();
                        List<WorkerBean> list = workerBeanDao.queryBuilder().where(WorkerBeanDao.Properties.DepartmentId.eq
                                (departmentBean.getId())).list();
                        for (WorkerBean workerBean : list) {
                            workerBeanDao.delete(workerBean);
                        }
                        refreshTotal(mAdapter.getData());
                        break;
                    default:
                        Bundle bundle = new Bundle();
                        bundle.putString("departmentName", mAdapter.getData().get(position).getDepartmentName());
                        bundle.putLong("departmentId", mAdapter.getData().get(position).getId());
                        BaseFunc.startActivity(mBaseActivity, WorkerActivity.class, bundle);
                        break;
                }
                return false;
            }
        });
    }

    private void refreshTotal(List<DepartmentBean> beanList) {
        float curTotal = 0;
        for (DepartmentBean departmentBean : beanList) {
            curTotal += departmentBean.departmentTotalPrice;
        }
        tvTotalPrice.setText(String.format(Locale.CHINA, "当前公司总消费 ￥%.2f", curTotal));
    }

    private List<DepartmentBean> refreshData() {
        List<DepartmentBean> list = mDepartmentDao.queryBuilder().list();
        mAdapter.setNewData(list);
        refreshTotal(list);
        return list;
    }

    private int curPos;

    @Subscribe
    public void updataDepartmentMoney(UpdateDepartmentEvent event) {
        refreshData();
    }

    @Subscribe
    public void getDepartmentName(CreateDepartmentDialog.ConfirmEvent event) {
        DepartmentBean departmentBean;
        if (event.isCreate) {
            departmentBean = new DepartmentBean();
            List<DepartmentBean> beanList = mDepartmentDao.queryBuilder().list();
            departmentBean.setId(beanList.size() == 0 ? 0 : beanList.get(beanList.size() - 1).getId() + 1);
            departmentBean.setDepartmentName(event.content);
            departmentBean.setDepartmentTotalPrice(0);
            mDepartmentDao.insert(departmentBean);
            mAdapter.addData(departmentBean);
            for (DepartmentBean bean : beanList) {
                BaseFunc.loge(bean.getId());
            }
        } else {
            departmentBean = mDepartmentDao.queryBuilder().where(DepartmentBeanDao.Properties.Id.eq(event
                    .departmentId)).unique();
            departmentBean.setDepartmentName(event.content);
            mDepartmentDao.update(departmentBean);
            mAdapter.getData().get(curPos).setDepartmentName(event.content);
            mAdapter.notifyItemChanged(curPos);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
