package com.lesw.tree_knowledge.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class UserAdapter extends FragmentStatePagerAdapter {


    public UserAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position % this.getCount()) {
            case 0:
                return "Competências";
            case 1:
                return "Adicionar Competência";
            case 2:
                return "Nova Competência";
        }
        return "";
    }

    @Override
    public Fragment getItem(int position) {
        switch (position % this.getCount()) {
            case 0:
            default:
                return new UserTreeFragment();
            case 1:
                return new UploadFragment();
            case 2:
                return new InsertNewKnowledgeFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
