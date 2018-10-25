package com.lesw.tree_knowledge;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ManagerAdapter extends FragmentStatePagerAdapter {


    public ManagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position % 3) {
            case 0:
                return "Competências";
            case 1:
                return "Acompanhamento";
            case 2:
                return "Pesquisa";
        }
        return "";
    }

    @Override
    public Fragment getItem(int position) {
        switch (position % 3) {
            case 0:
                return new UserTreeFragment();
            case 1:
                return new ManagerTreeFragment();
            default:
                return SearchFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
