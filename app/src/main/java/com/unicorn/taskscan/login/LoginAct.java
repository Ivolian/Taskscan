package com.unicorn.taskscan.login;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.unicorn.taskscan.R;
import com.unicorn.taskscan.SimpleApplication;
import com.unicorn.taskscan.base.BaseAct;
import com.unicorn.taskscan.menu.MenuAct;
import com.unicorn.taskscan.user.User;
import com.unicorn.taskscan.user.UserDao;
import com.unicorn.taskscan.utils.ConfigUtils;
import com.unicorn.taskscan.utils.Constant;
import com.unicorn.taskscan.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginAct extends BaseAct {

    @Override
    protected int getLayoutResID() {
        return R.layout.act_login;
    }

    @Override
    protected void init() {
        initAccount();
        initLoginButton();
    }

    private void initAccount() {
        metAccount.setText(ConfigUtils.getAccount());
    }

    @OnClick(R.id.btnLogin)
    public void btnLoginOnClick() {
        if (isInputValid()) {
            login();
        }
    }

    private void login() {
        final String account = getAccount();
        final String pwd = getPwd();
        if (isUserValid(account, pwd)) {
            ConfigUtils.saveAccount(account);
            startActivity(MenuAct.class);
            finish();
        } else {
            ToastUtils.show("账户或密码错误");
        }
    }

    private boolean isUserValid(final String account, final String pwd) {
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

    private void initLoginButton() {
        btnLogin.setBackground(getLoginButtonBackground());
    }

    private Drawable getLoginButtonBackground() {
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(Color.WHITE);
        bg.setCornerRadius(10);
        return bg;
    }

    private boolean isInputValid() {
        if (getAccount().equals(Constant.EMPTY_STR)) {
            ToastUtils.show("账号不能为空");
            return false;
        }
        if (getPwd().equals(Constant.EMPTY_STR)) {
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
