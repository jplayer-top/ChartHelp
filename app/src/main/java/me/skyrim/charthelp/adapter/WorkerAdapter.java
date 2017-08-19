package me.skyrim.charthelp.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import java.util.Locale;

import me.skyrim.charthelp.R;
import me.skyrim.charthelp.base.BaseActivity;
import me.skyrim.charthelp.dao.WorkerBean;
import me.skyrim.charthelp.ui.SlidingButtonView;
import me.skyrim.charthelp.uitls.DensityUtils;

/**
 * Created by Obl on 2017/8/18.
 * me.skyrim.charthelp.adapter
 */

public class WorkerAdapter extends BaseQuickAdapter<WorkerBean, BaseViewHolder> implements SlidingButtonView
        .IonSlidingButtonListener {
    private SlidingButtonView mMenu = null;
    private BaseActivity activity;

    public WorkerAdapter(BaseActivity activity, List<WorkerBean> data) {
        super(R.layout.adapter_worker, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkerBean item) {
        helper.itemView.findViewById(R.id.layout_content).getLayoutParams().width = DensityUtils.getWidthOfScreen
                (activity, 6, 1);
        ((SlidingButtonView) (helper.itemView.findViewById(R.id.slide))).setSlidingButtonListener(this);
        helper.setText(R.id.tvWorkerName, item.getWorkerName())
                .setText(R.id.tvWorkerName, String.format(Locale.CHINA, "%s     ID:%d", item.getWorkerName(), item
                        .getWorkerId()))
                .setText(R.id.tvWorkerTotalPrice, String.format(Locale.CHINA, "共花费 %.2f", item.getTotalPrice()))
                .addOnLongClickListener(R.id.layout_content)
                .addOnClickListener(R.id.layout_content)
                .addOnClickListener(R.id.tv_delete);
    }

    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }

    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if (menuIsOpen()) {
            if (mMenu != slidingButtonView) {
                closeMenu();
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }

    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if (mMenu != null) {
            return true;
        }
        return false;
    }
}
