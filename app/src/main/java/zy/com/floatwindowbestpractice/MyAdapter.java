package zy.com.floatwindowbestpractice;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * ================================================
 * 作    者：Luffy（张阳）
 * 版    本：1.0
 * 创建日期：2018/4/16
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class MyAdapter extends BaseQuickAdapter<EventBean, BaseViewHolder> {
    public MyAdapter(int layoutResId, @Nullable List<EventBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventBean item) {

    }
}
