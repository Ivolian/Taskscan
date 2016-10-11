package com.unicorn.taskscan.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


public abstract class ButterKnifeFragment extends Fragment {

    @LayoutRes
    abstract protected int getLayoutResID();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResID(), container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    protected void init() {
        // do some init work
    }

}
