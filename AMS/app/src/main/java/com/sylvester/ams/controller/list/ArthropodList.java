package com.sylvester.ams.controller.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sylvester.ams.R;
import com.sylvester.ams.service.ArthropodService;
import com.sylvester.ams.service.realm.RealmArthropodService;
import com.sylvester.ams.model.Arthropod;

import java.util.ArrayList;
import java.util.List;

public class ArthropodList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<Arthropod> arthropodArrayList = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ArthropodListContext.context = this;

        // Toolbar를 생성한다.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        // Drawer layout의 동작을 설정한다.
        initDrawerLayout(toolbar);

        initRecycleView();
    }

    private void initDrawerLayout(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.rv_list);

        // RecycleView 의 크기가 변경되지 않을 때, 최적화를 위해 사용한다.
        recyclerView.setHasFixedSize(true);

        // 세로로 아이템들을 보여주기 위해 Linear layout manager 를 이용한다.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // RecyclerView의 리스트에 아무것도 들어있지 않는다면, 샘플을 생성한다.
        ArthropodService service = new RealmArthropodService();

        if (service.getArthropods().isEmpty()) {
            service.insertSample();
        }

        // Realm에서 읽어오기
        arthropodArrayList = service.getArthropods();

        // Adapter에 지정한다.
        RealmRecyclerViewAdapter myAdapter = new RealmRecyclerViewAdapter((ArrayList<Arthropod>) arthropodArrayList);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // ToolBar에 menu.xml을 전개(inflate)함
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }

    // Drawer에 추가된 항목의 select 이벤트를 처리하는 함수
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_add) {
            // Handle the camera action
        } else if (id == R.id.nav_notifications) {

        } else if (id == R.id.nav_delete) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_help) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
