package com.lesw.tree_knowledge;

import android.content.Intent;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

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
            Employee employee = DummyDB.getInstance(getApplicationContext()).findEmployeeByEmail(email);

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
