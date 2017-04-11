package com.unicorn.taskscan.menu;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.unicorn.taskscan.R;
import com.unicorn.taskscan.base.BaseAct;
import com.unicorn.taskscan.user.UserHelper;
import com.unicorn.taskscan.utils.ConfigUtils;

import java.util.List;

import butterknife.BindView;

import static android.view.View.OVER_SCROLL_NEVER;

public class MenuAct extends BaseAct {

    @Override
    protected int getLayoutResID() {
        return R.layout.act_menu;
    }

    @Override
    protected void init() {
        initRecyclerView();
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    MenuAdapter menuAdapter;

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(menuAdapter = new MenuAdapter());
        recyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        menuAdapter.setNewData(menus());
        setOnItemChildClickListener();
    }

    private List<Menu> menus() {
        String userType = UserHelper.getUserType(ConfigUtils.getAccount());
        return MenuProvider.provide(userType);
    }

    private void setOnItemChildClickListener() {
        menuAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

}
