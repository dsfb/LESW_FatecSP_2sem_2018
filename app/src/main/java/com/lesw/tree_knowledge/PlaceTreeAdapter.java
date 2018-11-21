package com.lesw.tree_knowledge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class PlaceTreeAdapter extends FragmentStatePagerAdapter {
    private String knowledgeName;
    private String userName;

    PlaceTreeAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setKnowledgeName(String knowledgeName) {
        this.knowledgeName = knowledgeName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position % this.getCount()) {
            case 0:
            default:
                return "Adição de Conhecimento";
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position % this.getCount()) {
            case 0:
            default:
                if (knowledgeName == null) {
                    Log.e("PlaceTreeAdapter", "getIem: knowledgeName é null!");
                }

                if (userName == null) {
                    Log.e("PlaceTreeAdapter", "getItem: userName é null!");
                }

                Bundle bundle = new Bundle();
                bundle.putString("userName", userName);
                bundle.putString("knowledgeName", knowledgeName);

                Fragment f = new PlaceTreeFragment();
                f.setArguments(bundle);
                return f;
        }
    }

    @Override
    public int getCount() {
        return 1;
    }
}
