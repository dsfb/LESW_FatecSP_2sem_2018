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
        switch (position % this.getCount()) {
            case 0:
                return "Competências";
            case 1:
            default:
                return "Certificações";
            case 2:
                return "Acompanhamento";
            case 3:
                return "Pesquisa";
            case 4:
                return "Adicionar Competência";
            case 5:
                return "Nova Competência";
            case 6:
                return "Mudança de Nível";
            case 7:
                return "Ver por Nível";

        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position % this.getCount()) {
            case 0:
                return new UserTreeFragment();
            case 1:
            default:
                return ApprovalsFragment.newInstance();
            case 2:
                return new HRTreeFragment();
            case 3:
                return SearchFragment.newInstance();
            case 4:
                return new UploadFragment();
            case 5:
                return new InsertNewKnowledgeFragment();
            case 6:
                return new ChangeLevelFragment();
            case 7:
                return new LevelKnowledgeFragment();
        }
    }

    @Override
    public int getCount() {
        return 8;
    }
}
