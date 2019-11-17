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

        ArthropodService service = DetailContext.getService();
        ImageView iv_picture = (ImageView) findViewById(R.id.iv_picture);
        iv_picture.setImageBitmap(service.getArthropodImg(DetailContext.id));
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

//    private void bindTarantulaObj() {
//        // xml에 있는 gui와 바인드한다.
//        TextView tv_late_fed = findViewById(R.id.tv_late_fed);                      // 마지막 피딩일
//        TextView tv_hungry = findViewById(R.id.tv_hungry);                          // 굶은 기간
//        Button ib_fed = findViewById(R.id.ib_fed);                                  // 피딩 버튼
//        CheckBox cb_postpone_fed = findViewById(R.id.cb_postpone_fed);              // 피딩을 중지
//        EditText et_fed_cycle = findViewById(R.id.et_fed_cycle);                    // 피딩주기
//        EditText et_postpone_fed_date = findViewById(R.id.et_postpone_fed_date);    // 탈피 후 피딩 연기일
//        EditText et_temperature_low = findViewById(R.id.et_temperature_low);        // 적정온도
//        EditText et_temperature_high = findViewById(R.id.et_temperature_high);
//        EditText et_humidity_low = findViewById(R.id.et_humidity_low);              // 적정습도
//        EditText et_humidity_high = findViewById(R.id.et_humidity_high);
//
//        // realm에 저장되어있는 상태와 동기화한다.
//        if (arthropod.getLastFeedDate() != null)
//            tv_late_fed.setText(arthropod.getLastFeedDate());
//
//        if(arthropod.getHungry() != -1)
//            tv_hungry.setText(Integer.toString(arthropod.getHungry()));
//
//        cb_postpone_fed.setChecked(arthropod.isPostponeFeed());
//
//        // genus ArrayList
//        genus.add("리스트에 Genus가 없으면 새로 추가");
//
//        // tarantulaInfoList에서 genus 중복제거
//        int j = 1;
//        for (int i = 1; i < arthropodInfoList.size(); i++) {
//            String temp = arthropodInfoList.get(i - 1).getScientificName();
//            temp = temp.split(" ")[0];
//
//            if (!temp.equals(genus.get(j - 1))) {
//                genus.add(temp);
//                j++;
//            }
//        }
//    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("AlertDialog Title");
        builder.setMessage("AlertDialog Content");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    // ToolBar에 menu.xml을 인플레이트함
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
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
