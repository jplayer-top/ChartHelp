package me.skyrim.charthelp.activity;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.koma.greendao.gen.WorkerBeanDao;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import me.skyrim.charthelp.R;
import me.skyrim.charthelp.base.BaseActivity;
import me.skyrim.charthelp.base.BaseFunc;
import me.skyrim.charthelp.base.BeanDaoManager;
import me.skyrim.charthelp.dao.WorkerBean;

/**
 * Created by Obl on 2017/8/19.
 * me.skyrim.charthelp.activity
 */

public class UserActivity extends BaseActivity {
    @BindView(R.id.editName)
    EditText mEditName;
    @BindView(R.id.editId)
    EditText mEditId;
    @BindView(R.id.editNowMoney)
    EditText mEditNowMoney;
    private WorkerBeanDao mWorkerBeanDao;

    @Override
    public void bindData() {
        mWorkerBeanDao = BeanDaoManager.Builder().getWorkerDao();
        mTvToolBarRight.setVisibility(View.VISIBLE);
    }

    @Override
    public int initLayout() {
        mBundle = getIntent().getBundleExtra("bundle");
        mTvTitle.setText("添加员工");
        return R.layout.activity_user;
    }

    @Override
    public void rightButtonClick() {
        if (mEditName.getText() == null || mEditName.getText().toString().equals("")) {
            BaseFunc.showToast(mBaseActivity, "请输入关键信息");
            return;
        }
        if (mEditId.getText() == null || mEditId.getText().toString().equals("")) {
            BaseFunc.showToast(mBaseActivity, "请输入关键信息");
            return;
        }
        WorkerBean workerBean = new WorkerBean();
        List<WorkerBean> beanList = mWorkerBeanDao.queryBuilder().list();
        workerBean.setId(beanList.size() == 0 ? 0 : beanList.get(beanList.size() - 1).getId() + 1);
        workerBean.setWorkerName(mEditName.getText().toString());
        workerBean.setWorkerId(Long.parseLong(mEditId.getText().toString()));
        Editable nowMoney = mEditNowMoney.getText();
        workerBean.setTotalPrice(((nowMoney == null || nowMoney.toString().equals("")) ? 0 : Float.parseFloat(nowMoney.toString
                ())));
        workerBean.setDepartmentId(mBundle.getLong("departmentId"));
        mWorkerBeanDao.insert(workerBean);
        EventBus.getDefault().post(new InsertWorkerEvent());

        finish();
    }


    public class InsertWorkerEvent {
    }
}
