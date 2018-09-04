package com.ihc.tree_knowledge;

public class IconTreeItem {

    int icon;
    String text;
    ColorEnum color;
    Integer counter;
    Integer warningCounter;
    Knowledge knowledge;

    IconTreeItem(int icon, String text, ColorEnum color, Integer counter) {
        this.icon = icon;
        this.text = text;
        this.color = color;
        this.counter = counter;
    }

    public IconTreeItem(int icon, ColorEnum color, Knowledge knowledge) {
        this.icon = icon;
        this.knowledge = knowledge;
        this.text = knowledge.getName();
        this.counter = knowledge.getCount();
        this.warningCounter = knowledge.getWarningCount();
        this.color = color;
    }
}
