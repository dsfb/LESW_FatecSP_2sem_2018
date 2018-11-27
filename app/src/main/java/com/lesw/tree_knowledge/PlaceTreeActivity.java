package com.lesw.tree_knowledge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceTreeActivity extends AppCompatActivity {

    @BindView(R.id.materialViewPager)
    @Nullable
    MaterialViewPager mViewPager;
    FragmentStatePagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_place_tree);
        setTitle("");
        ButterKnife.bind(this);

        Intent intent = getIntent();

        if(intent != null) {
            String knowledgeName = intent.getStringExtra("KNOWLEDGE");
            String userName = intent.getStringExtra("USERNAME");
            String certification = intent.getStringExtra("CERTIFICATION");

            adapter = new PlaceTreeAdapter(getSupportFragmentManager());

            ((PlaceTreeAdapter) adapter).setUserName(userName);
            ((PlaceTreeAdapter) adapter).setKnowledgeName(knowledgeName);
            ((PlaceTreeAdapter) adapter).setCertification(certification);

            mViewPager.getViewPager().setAdapter(adapter);

            mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
                @Override
                public HeaderDesign getHeaderDesign(int page) {
                    switch (page) {
                        default:
                            return HeaderDesign.fromColorResAndDrawable(R.color.green, getResources().getDrawable( R.drawable.header));
                    }
                }
            });

            mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
            mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
        } else {
            Log.e("PlaceTreeActivity", "intent não tinha knowledgeName ou email");
        }
    }

}
