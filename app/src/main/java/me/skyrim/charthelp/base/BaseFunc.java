package me.skyrim.charthelp.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by Obl on 2017/8/18.
 * me.skyrim.charthelp.base
 */

public class BaseFunc {
    public static void loge(Object msg) {
        Log.e("Obl", msg.toString());
    }

    public static void startActivity(BaseActivity baseActivity, Class clazz, Bundle bundle) {
        Intent intent = new Intent(baseActivity, clazz);
        intent.putExtra("bundle", bundle);
        baseActivity.startActivity(intent);
    }

    public static void startActivity(BaseActivity baseActivity, Class clazz) {
        Bundle bundle = new Bundle();
        startActivity(baseActivity, clazz, bundle);
    }

    public static void showToast(String msg) {
        showToast(ChartHelpApplication.baseContent, msg);
    }

    private static Toast toast;

    public static void showToast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.show();
    }

    public static boolean isSHowKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.hideSoftInputFromWindow(v.getWindowToken(), 0)) {
            imm.showSoftInput(v, 0);
            return true;
            //软键盘已弹出
        } else {
            return false;
            //软键盘未弹出
        }
    }

    public static void closeInput(Context context, View view) {
        if (isSHowKeyboard(context, view)) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
