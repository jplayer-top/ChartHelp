package me.skyrim.charthelp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.skyrim.charthelp.R;
import me.skyrim.charthelp.dao.DepartmentBean;

/**
 * Created by PEO on 2017/3/6.
 * 修改密码
 */

public class CreateDepartmentDialog extends BaseDialog {

    @BindView(R.id.tvGone)
    TextView mTvGone;
    @BindView(R.id.etPhotoName)
    EditText mEtPhotoName;
    @BindView(R.id.btnCancle)
    Button mBtnCancle;
    @BindView(R.id.btnSave)
    Button mBtnSave;
    @BindView(R.id.etPhotoNamePsw)
    EditText mEtPhotoNamePsw;
    public boolean isCreate;
    private String name = "";
    private long departmentId;

    public CreateDepartmentDialog(@NonNull Activity activity) {
        super(activity);
        isCreate = true;
        departmentId = -1;
    }

    public CreateDepartmentDialog(@NonNull Activity activity, DepartmentBean departmentBean) {
        super(activity);
        this.name = departmentBean.getDepartmentName();
        isCreate = false;
        departmentId = departmentBean.getId();
    }

    @Override
    View createView() {
        View view = View.inflate(mActivity, R.layout.dialog_add_department, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBtnSave.setText("确认");
        mEtPhotoNamePsw.setVisibility(View.VISIBLE);
        mEtPhotoName.setVisibility(View.GONE);
        mEtPhotoNamePsw.setText(name);
        mEtPhotoNamePsw.setSelection(name.length());
    }

    @Override
    public void show() {
        if (!isShowing()) {
            super.show();
        }
    }

    @OnClick({R.id.btnCancle, R.id.btnSave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancle:
                if (isShowing()) {
                    mEtPhotoNamePsw.setText("");
                    dismiss();
                }
                break;
            case R.id.btnSave:
                Editable content = mEtPhotoNamePsw.getText();
                if (content == null || "".equals(content.toString())) {
                    return;
                }
                EventBus.getDefault().post(new ConfirmEvent(content.toString(), isCreate, departmentId));
                dismiss();
                break;
        }
    }

    public class ConfirmEvent {
        public String content;
        public boolean isCreate;
        public long departmentId;

        public ConfirmEvent(String content, boolean isCreate, long departmentId) {
            this.content = content;
            this.isCreate = isCreate;
            this.departmentId = departmentId;
        }
    }
}
