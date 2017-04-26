package com.unicorn.taskscan.menu;

import com.unicorn.taskscan.R;
import com.unicorn.taskscan.user.UserHelper;

import java.util.Arrays;
import java.util.List;

class MenuProvider {

    private static List<Menu> menus = Arrays.asList(
            new Menu(Menu.DEPART_SCAN, R.drawable.icon1),
            new Menu(Menu.DEPART_INPUT, R.drawable.icon1),
            new Menu(Menu.ARRIVE_SCAN, R.drawable.icon2),
            new Menu(Menu.ARRIVE_INPUT, R.drawable.icon2),
            new Menu(Menu.MATCH_DOWNLOAD, R.drawable.icon5),
            new Menu(Menu.RECORD_SYNC, R.drawable.icon3),
            new Menu(Menu.RECORD_QUERY, R.drawable.icon4),
            new Menu(Menu.CLEAR_RECORD, R.drawable.icon6),
            new Menu(Menu.RECORD_STAT, R.drawable.icon4),
            new Menu(Menu.DATA_STAT, R.drawable.icon4)
    );

    static List<Menu> provide(@UserHelper.UserType String userType) {
        switch (userType) {
            case UserHelper.CHU_FA:
                return Arrays.asList(
                        new Menu(Menu.DEPART_SCAN, R.drawable.icon1),
                        new Menu(Menu.DEPART_INPUT, R.drawable.icon1),
                        new Menu(Menu.RECORD_SYNC, R.drawable.icon3),
                        new Menu(Menu.DATA_STAT, R.drawable.icon4)
                );
            case UserHelper.DAO_DA:
                return Arrays.asList(
                        new Menu(Menu.ARRIVE_SCAN, R.drawable.icon2),
                        new Menu(Menu.ARRIVE_INPUT, R.drawable.icon2),
                        new Menu(Menu.RECORD_SYNC, R.drawable.icon3),
                        new Menu(Menu.DATA_STAT, R.drawable.icon4)
                );
            case UserHelper.ADMIN:
                return Arrays.asList(
                        new Menu(Menu.MATCH_DOWNLOAD, R.drawable.icon5),
                        new Menu(Menu.RECORD_QUERY, R.drawable.icon4),
                        new Menu(Menu.CLEAR_RECORD, R.drawable.icon6),
                        new Menu(Menu.RECORD_STAT, R.drawable.icon4)
                );
            case UserHelper.OTHER:
                return menus;
            default:
                return null;
        }
    }

}
