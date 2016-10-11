package com.unicorn.taskscan.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;


public abstract class ButterKnifeActivity extends BaseActivity {

    @LayoutRes
    abstract protected int getLayoutResID();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        ButterKnife.bind(this);
        init();
    }

    protected void init() {
        // do some init work
    }

}
