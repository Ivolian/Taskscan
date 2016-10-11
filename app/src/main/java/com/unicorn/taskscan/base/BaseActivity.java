package com.unicorn.taskscan.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {

    protected void startActivity(Class activityClazz) {
        Intent intent = new Intent(this, activityClazz);
        startActivity(intent);
    }

    protected void startActivityAndFinish(Class activityClazz) {
        startActivity(activityClazz);
        finish();
    }

}
