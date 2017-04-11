package com.unicorn.taskscan.menu;

import com.unicorn.taskscan.R;
import com.unicorn.taskscan.user.UserHelper;

import java.util.Arrays;
import java.util.List;

class MenuProvider {

    private static List<Menu> menus = Arrays.asList(
            new Menu("出发扫码", R.drawable.icon1),
            new Menu("出发输入", R.drawable.icon1),
            new Menu("到达扫码", R.drawable.icon2),
            new Menu("到达输入", R.drawable.icon2),
            new Menu("比赛下载", R.drawable.icon5),
            new Menu("数据同步", R.drawable.icon3),
            new Menu("成绩查询", R.drawable.icon4),
            new Menu("清除数据", R.drawable.icon6),
            new Menu("成绩统计", R.drawable.icon4)
    );

    public static List<Menu> provide(@UserHelper.UserType String userType) {
        switch (userType) {
            case UserHelper.CHU_FA:
                return menus.subList(0, 2);
            case UserHelper.DAO_DA:
                return menus.subList(2, 4);
            case UserHelper.ADMIN:
                return menus.subList(4, 9);
            case UserHelper.OTHER:
                return menus;
            default:
                return null;
        }
    }

}
