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
        switch (position % this.getCount()) {
            case 0:
                return "Competências";
            case 1:
                return "Acompanhamento";
            case 2:
                return "Pesquisa";
            case 3:
                return "Adicionar";
            case 4:
                return "Ver por nível";
        }
        return "";
    }

    @Override
    public Fragment getItem(int position) {
        switch (position % this.getCount()) {
            case 0:
                return new UserTreeFragment();
            case 1:
            default:
                return new ManagerTreeFragment();
            case 2:
                return SearchFragment.newInstance();
            case 3:
                return new UploadFragment();
            case 4:
                return new LevelKnowledgeFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
