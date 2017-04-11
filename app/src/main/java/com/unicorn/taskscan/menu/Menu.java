package com.unicorn.taskscan.menu;

class Menu {

    private String text;

    private int icon;

    Menu(String text, int icon) {
        this.text = text;
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public int getIcon() {
        return icon;
    }

}
