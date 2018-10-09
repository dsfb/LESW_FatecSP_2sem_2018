package com.lesw.tree_knowledge;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class UserAdapter extends FragmentStatePagerAdapter {


    public UserAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position % 2) {
            case 0:
                return "Competências";
            case 1:
                return "Adicionar";
        }
        return "";
    }

    @Override
    public Fragment getItem(int position) {
        switch (position % 2) {
            case 0:
                return new UserTreeFragment();
            default:
                return new UploadFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
