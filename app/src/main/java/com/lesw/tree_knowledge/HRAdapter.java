package com.lesw.tree_knowledge;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class HRAdapter extends FragmentStatePagerAdapter {


    HRAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position % 5) {
            case 0:
                return "Competências";
            case 1:
                return "Certificações";
            case 2:
                return "Acompanhamento";
            case 3:
                return "Pesquisa";
            default:
                return "Cadastro";
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position % 5) {
            case 0:
                return new UserTreeFragment();
            case 1:
                return ApprovalsFragment.newInstance();
            case 2:
                return new HRTreeFragment();
            case 3:
                return SearchFragment.newInstance();
            default:
                return new HRSignupFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
