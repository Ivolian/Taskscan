package com.unicorn.taskscan.user;

import com.unicorn.taskscan.SimpleApplication;

import java.util.ArrayList;
import java.util.List;

public class UserHelper {

    public static void initIfNeed() {
        UserDao userDao = SimpleApplication.getDaoSession().getUserDao();
        long count = userDao.count();
        if (count == 0) {
            userDao.insertInTx(getUserList());
        }
    }

    private static List<User> getUserList() {
        List<User> users = new ArrayList<>();
        users.add(createUser("zuobiao01", "1018"));
        users.add(createUser("zuobiao02", "1007"));
        users.add(createUser("zuobiao03", "1076"));
        users.add(createUser("zuobiao04", "1087"));
        users.add(createUser("zuobiao05", "1038"));
        users.add(createUser("zuobiao06", "1903"));
        return users;
    }

    private static User createUser(final String account, final String pwd) {
        User user = new User();
        user.setAccount(account);
        user.setPwd(pwd);
        return user;
    }

}
