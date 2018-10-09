package com.lesw.tree_knowledge;

import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;

public class HRAdapter extends FragmentStatePagerAdapter {


    HRAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position % 4) {
            case 0:
                return "Certificações";
            case 1:
                return "Acompanhamento";
            case 2:
                return "Pesquisa";
            default:
                return "Cadastro";
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position % 4) {
            case 0:
                return ApprovalsFragment.newInstance();
            case 1:
                return new HRTreeFragment();
            case 2:
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
