package com.unicorn.taskscan;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.unicorn.taskscan.base.ButterKnifeActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends ButterKnifeActivity {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        initAccount();
        initLoginButton();
    }

    @OnClick(R.id.btnLogin)
    public void btnLoginOnClick() {
        if (checkInput()) {
            login();
        }
    }

    private void login() {
        if (checkUser(getAccount(), getPwd())) {
            TinyDB tinyDB = TinyDB.getNewInstance();
            tinyDB.putString(Constant.K_ACCOUNT, getAccount());
            startActivityAndFinish(MainActivity.class);
        } else {
            ToastUtils.show("账户或密码错误");
        }
    }

    private boolean checkUser(final String account, final String pwd) {
        UserDao userDao = SimpleApplication.getDaoSession().getUserDao();
        User user = userDao.queryBuilder()
                .where(UserDao.Properties.Account.eq(account))
                .where(UserDao.Properties.Pwd.eq(pwd))
                .unique();
        return user != null;
    }


    // ==================== 基本无视 ====================

    @BindView(R.id.metAccount)
    MaterialEditText metAccount;

    @BindView(R.id.metPwd)
    MaterialEditText metPwd;

    @BindView(R.id.btnLogin)
    TextView btnLogin;

    private void initAccount() {
        TinyDB tinyDB = TinyDB.getNewInstance();
        String account = tinyDB.getString(Constant.K_ACCOUNT);
        metAccount.setText(account);
    }

    private void initLoginButton() {
        btnLogin.setBackground(getLoginButtonBackground());
    }

    private Drawable getLoginButtonBackground() {
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(Color.WHITE);
        bg.setCornerRadius(10);
        return bg;
    }

    private boolean checkInput() {
        if (getAccount().equals("")) {
            ToastUtils.show("账号不能为空");
            return false;
        }
        if (getPwd().equals("")) {
            ToastUtils.show("密码不能为空");
            return false;
        }
        return true;
    }

    private String getAccount() {
        return metAccount.getText().toString().trim();
    }

    private String getPwd() {
        return metPwd.getText().toString().trim();
    }

}
