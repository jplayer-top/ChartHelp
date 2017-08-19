package me.skyrim.charthelp.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import java.util.Locale;

import me.skyrim.charthelp.R;
import me.skyrim.charthelp.base.BaseActivity;
import me.skyrim.charthelp.dao.AffairBean;
import me.skyrim.charthelp.ui.SlidingButtonView;
import me.skyrim.charthelp.uitls.DensityUtils;

/**
 * Created by Obl on 2017/8/19.
 * me.skyrim.charthelp.adapter
 */


public class AffairAdapter extends BaseQuickAdapter<AffairBean, BaseViewHolder> implements SlidingButtonView
        .IonSlidingButtonListener {
    private SlidingButtonView mMenu = null;
    private BaseActivity activity;

    public AffairAdapter(BaseActivity activity, List<AffairBean> data) {
        super(R.layout.adapter_affair, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, AffairBean item) {
        helper.itemView.findViewById(R.id.layout_content).getLayoutParams().width = DensityUtils.getWidthOfScreen
                (activity, 6, 1);
        ((SlidingButtonView) (helper.itemView.findViewById(R.id.slide))).setSlidingButtonListener(this);
        helper.setText(R.id.tvAffairContent, item.getContent())
                .setText(R.id.tvAffairCount, String.format(Locale.CHINA, "共%d件", item.getCount()))
                .setText(R.id.tvAffairPrice, String.format(Locale.CHINA, "单价：%.2f", item.getPrice()))
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
