package me.skyrim.charthelp.ui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import me.skyrim.charthelp.R;

/**
 * Created by Obl on 2017/4/14.
 * com.ilanchuang.xiaoi.views
 */

public abstract class BaseDialog extends Dialog {
    public Activity mActivity;

    public BaseDialog(@NonNull Activity activity) {
        super(activity, R.style.dialog_custom);
        mActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = createView();
        setContentView(view);
        WindowManager windowManager = mActivity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = display.getWidth() * 4 / 5; // 设置dialog宽度为屏幕的4/5
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lp.dimAmount = 0.5f;
        window.setAttributes(lp);
        setCanceledOnTouchOutside(true);// 点击Dialog外部消失
    }

    abstract View createView();
}
