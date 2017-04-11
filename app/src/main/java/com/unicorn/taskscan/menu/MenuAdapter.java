package com.unicorn.taskscan.menu;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.unicorn.taskscan.R;

class MenuAdapter extends BaseQuickAdapter<Menu, BaseViewHolder> {

    MenuAdapter() {
        super(R.layout.item_menu);
    }

    @Override
    protected void convert(BaseViewHolder helper, Menu menu) {
        helper.setText(R.id.tvText, menu.getText());
        helper.setImageResource(R.id.ivIcon, menu.getIcon());
    }

}
