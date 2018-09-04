package com.ihc.tree_knowledge;

import android.content.Intent;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.materialViewPager)
    MaterialViewPager mViewPager;
    FragmentStatePagerAdapter adapter;

    RoleEnum userRole = RoleEnum.USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTitle("");
        ButterKnife.bind(this);

        final Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        Intent intent = getIntent();

        if(intent != null) {
            String email = intent.getStringExtra("EMAIL");
            Employee employee = DummyDB.getInstance().findEmployeeByEmail(email);

            if(employee != null) {
                userRole = employee.getFunction();
            }
        }

        switch (userRole){
            case MANAGER:
                adapter = new ManagerAdapter(getSupportFragmentManager());
                break;
            case HR:
                adapter = new HRAdapter(getSupportFragmentManager());
                break;
            case USER:
            default:
                adapter = new UserAdapter(getSupportFragmentManager());
        }

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
    }
}
