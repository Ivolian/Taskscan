package com.unicorn.taskscan.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.unicorn.taskscan.R;
import com.unicorn.taskscan.TeamNoInputAct;
import com.unicorn.taskscan.base.BaseAct;
import com.unicorn.taskscan.print.WoyouPrinter;
import com.unicorn.taskscan.record.RecordArriver;
import com.unicorn.taskscan.record.RecordCleaner;
import com.unicorn.taskscan.record.RecordDeparter;
import com.unicorn.taskscan.record.RecordDownloader;
import com.unicorn.taskscan.record.RecordQueryAct;
import com.unicorn.taskscan.record.RecordStatAct;
import com.unicorn.taskscan.record.RecordSyncer;
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
        setOnItemClickListener();
    }

    private List<Menu> menus() {
        String userType = UserHelper.getUserType(ConfigUtils.getAccount());
        return MenuProvider.provide(userType);
    }

    private void setOnItemClickListener() {
        menuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Menu menu = menuAdapter.getItem(position);
                switch (menu.getText()) {
                    case Menu.DEPART_SCAN:
                        isDepart = true;
                        scan();
                        break;
                    case Menu.DEPART_INPUT:
                        isDepart = true;
                        startTeamNoInputAct();
                        break;
                    case Menu.ARRIVE_SCAN:
                        isDepart = false;
                        scan();
                        break;
                    case Menu.ARRIVE_INPUT:
                        isDepart = false;
                        startTeamNoInputAct();
                        break;
                    case Menu.MATCH_DOWNLOAD:
                        new RecordDownloader(MenuAct.this).downloadRecord();
                        break;
                    case Menu.RECORD_SYNC:
                        new RecordSyncer().sync();
                        break;
                    case Menu.RECORD_QUERY:
                        startActivity(RecordQueryAct.class);
                        break;
                    case Menu.CLEAR_RECORD:
                        new RecordCleaner(MenuAct.this).showCleanConfirm();
                        break;
                    case Menu.RECORD_STAT:
                        startActivity(RecordStatAct.class);
                        break;
                }
            }
        });
    }


    // =================== startTeamNoInputAct ===================

    private void startTeamNoInputAct() {
        Intent intent = new Intent(this, TeamNoInputAct.class);
        intent.putExtra("isDepart", isDepart);
        startActivity(intent);
    }


    // =================== scan ===================

    private boolean isDepart;

    private void scan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            String teamNo = result.getContents();
            copeTeamNo(teamNo);
            scan();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void copeTeamNo(final String teamNo) {
        if (isDepart) {
            new RecordDeparter().depart(teamNo);
        } else {
            new RecordArriver().arrive(teamNo);
        }
    }


    // =================== onCreate & onDestroy ===================

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WoyouPrinter.init(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WoyouPrinter.destroy(this);
    }


}
