package com.sylvester.ams.controller.detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.sylvester.ams.R;
import com.sylvester.ams.model.Arthropod;
import com.sylvester.ams.model.ScientificName;
import com.sylvester.ams.service.ArthropodService;
import com.sylvester.ams.service.realm.RealmArthropodInfoService;
import com.sylvester.ams.service.realm.RealmArthropodService;

public class Detail extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArthropodService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Intent 값 받기
        Intent intent = getIntent();
        DetailContext.id = intent.getIntExtra("arthropodId", -1);

        // Custom Toolbar 설정
        initToolbar();

        // TabLayout, ViewPager 설정
        initContentLayout();

        addListener();

        service = DetailContext.getInstance().getService();
        ImageView iv_picture = (ImageView) findViewById(R.id.iv_picture);

        if (DetailContext.id != -1)
            iv_picture.setImageBitmap(service.getArthropodImg(DetailContext.id));
        else
            iv_picture.setImageBitmap(service.getArthropodImg());
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initContentLayout() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        // tab 정렬 방식에 대한 옵션
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (CustomViewPager) findViewById(R.id.pager);

        // pagerAdapter 객체 생성
        ContentsPagerAdapter pagerAdapter = new ContentsPagerAdapter(
                getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        // disable viewPager horizontal scroll
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    private void addListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                viewPager.getParent().requestDisallowInterceptTouchEvent(false);
            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }
        });
    }

    // ToolBar 에 menu.xml 을 인플레이트함
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    // ToolBar에 추가된 항목의 select 이벤트를 처리하는 함수
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_save) {
            // 저장하기를 눌렀을 때
            if (DetailContext.ScientificName != null && !DetailContext.ScientificName.equals("")) {
                service.insertArthropod();
            } else {
            }

            return true;
        } else if (id == android.R.id.home) {
            //toolbar의 back키 눌렀을 때 동작
            finish();
            //Intent intent = new Intent(getApplicationContext(), ArthropodList.class);  // 다음 화면으로 넘어갈 클래스 지정
            //startActivity(intent);  // 다음 화면으로 넘어간다.
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
