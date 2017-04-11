package com.unicorn.taskscan.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.f2prateek.dart.Dart;

import butterknife.ButterKnife;


public abstract class BaseAct extends AppCompatActivity {

    @LayoutRes
    abstract protected int getLayoutResID();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        ButterKnife.bind(this);
        Dart.inject(this);
        init();
    }

    protected void init() {
        // do some init work
    }

    protected void startActivity(Class activityClazz) {
        Intent intent = new Intent(this, activityClazz);
        startActivity(intent);
    }

}
