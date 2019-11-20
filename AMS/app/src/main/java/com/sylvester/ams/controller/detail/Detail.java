package com.sylvester.ams.controller.detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.sylvester.ams.R;
import com.sylvester.ams.model.Arthropod;
import com.sylvester.ams.service.ArthropodService;

public class Detail extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArthropodService service;
    private DetailMenuListener basicListener;
    private DetailMenuListener feedListener;
//    private DetailMenuListener basicListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        DetailContext.context = this;

        // Custom Toolbar 설정
        initToolbar();

        // TabLayout, ViewPager 설정
        initContentLayout();

        // Intent 값을 받아 넘겨지는 개체를 구한다
        Intent intent = getIntent();
        int id = intent.getIntExtra("arthropodId", -1);

        // 개체 이미지 설정
        ImageView iv_picture = (ImageView) findViewById(R.id.iv_picture);
        service = DetailContext.getService();

        if (id != -1)
            // 넘겨지는 개체의 사진으로 iv_picture 의 이미지를 설정
            DetailContext.arthropod = service.getArthropod(id);
        else
            // 넘겨지는 개체 고유 아이디가 없을 경우, 기본 사진으로 대체하여 설정
            DetailContext.arthropod = new Arthropod();

        iv_picture.setImageBitmap(service.getArthropodImg(DetailContext.arthropod));
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

        // tabLayout 리스너
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

        // viewPager 리스너
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

    // toolBar 에 추가된 항목의 select 이벤트를 처리하는 함수
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // save 버튼을 눌렀을 때 동작
            case R.id.action_save:
                    if (basicListener != null && feedListener != null) {
                        basicListener.onClickSave(DetailContext.arthropod);
                        feedListener.onClickSave(DetailContext.arthropod);

//                        if (DetailContext.scientificName != null && !DetailContext.scientificName.equals(""))
//                            service.insertArthropod();
//                        else {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                            builder.setMessage("학명을 입력해주세요.");
//                            builder.setPositiveButton("확인",
//                                    new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//                                        }
//                                    });
//                            AlertDialog alertDialog = builder.create();
//
//                            alertDialog.show();
//                        }
                    }
                return true;
            // back 키를 눌렀을 때 동작
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setBasicListener(DetailMenuListener listener) {
        this.basicListener = listener;
    }

    public void setFeedListener(DetailMenuListener listener) {
        this.feedListener = listener;
    }

//    public void setBasicListener(DetailMenuListener listener) {
//        this.basicListener = listener;
//    }
}
