package com.lesw.tree_knowledge;

import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;

public class ManagerAdapter extends FragmentStatePagerAdapter {


    public ManagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position % 2) {
            case 0:
                return "Acompanhamento";
            case 1:
                return "Pesquisa";
        }
        return "";
    }

    @Override
    public Fragment getItem(int position) {
        switch (position % 2) {
            case 0:
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
