package com.unicorn.taskscan.user;

import android.support.annotation.StringDef;

import com.unicorn.taskscan.SimpleApplication;

import java.util.ArrayList;
import java.util.List;

public class UserHelper {

    public static void initUsersIfNeed() {
        UserDao userDao = SimpleApplication.getDaoSession().getUserDao();
        long count = userDao.count();
        if (count == 0) {
            userDao.insertInTx(getUsers());
        }
    }

    private static List<User> getUsers() {
        List<User> users = new ArrayList<>();

        users.add(createUser("zuobiao01", "1018"));
        users.add(createUser("zuobiao02", "1007"));
        users.add(createUser("zuobiao03", "1076"));
        users.add(createUser("zuobiao04", "1087"));
        users.add(createUser("zuobiao05", "1038"));
        users.add(createUser("zuobiao06", "1903"));

        users.add(createUser("chufa01", "111111"));
        users.add(createUser("chufa02", "111111"));
        users.add(createUser("daoda01", "111111"));
        users.add(createUser("daoda02", "111111"));
        users.add(createUser("admin", "111111"));

        return users;
    }

    private static User createUser(final String account, final String pwd) {
        User user = new User();
        user.setAccount(account);
        user.setPwd(pwd);
        return user;
    }

    public static
    @UserType
    String getUserType(String account) {
        if (account.startsWith(CHU_FA)) {
            return CHU_FA;
        }
        if (account.startsWith(DAO_DA)) {
            return DAO_DA;
        }
        if (account.startsWith(ADMIN)) {
            return ADMIN;
        }
        return OTHER;
    }

    public static final String CHU_FA = "chufa";
    public static final String DAO_DA = "daoda01";
    public static final String ADMIN = "admin";
    public static final String OTHER = "other";

    @StringDef({CHU_FA, DAO_DA, ADMIN, OTHER})
    public @interface UserType {
    }

}
