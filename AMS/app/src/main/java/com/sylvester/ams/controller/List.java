package com.sylvester.ams.controller;

import android.os.Bundle;
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
import com.sylvester.ams.controller.adapters.RealmRecyclerViewAdapter;
import com.sylvester.ams.controller.service.realm.ArthropodInfoService;
import com.sylvester.ams.controller.service.realm.ArthropodService;
import com.sylvester.ams.controller.service.realm.RealmContext;
import com.sylvester.ams.model.Arthropod;
import com.sylvester.ams.model.ArthropodInfo;

import java.util.ArrayList;

import io.realm.Realm;

public class List extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Realm realm;
    private RealmContext realmContext;
    private ArrayList<Arthropod> arthropodArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Toolbar를 생성한다.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        // Drawer layout의 동작을 설정한다.
        initDrawerLayout(toolbar);

        bindModel();
    }

    // Drawer layout의 동작을 설정하는 함수
    private void initDrawerLayout(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void bindModel() {
        realmContext = RealmContext.getInstance();
        realm = realmContext.getRealm();

        mRecyclerView = findViewById(R.id.rv_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // 세로로 아이템들을 보여주기 위해 Linear layout manager를 이용한다.
        mLayoutManager = new LinearLayoutManager(this);

        // RecyclerView의 리스트에 아무것도 들어있지 않는다면, 샘플을 생성한다.
        ArthropodService service = new ArthropodService();
        ArthropodInfoService infoService = new ArthropodInfoService();
        ArrayList<ArthropodInfo> s = new ArrayList<>();
        s = infoService.getArthropodInfos();
        ArthropodInfo a = infoService.getArthropodInfo("Acanthoscurria geniculata");

        if (service.getArthropods().isEmpty() == true) {
            Arthropod sample = new Arthropod(R.drawable.ic_tarantula
                    , infoService.getArthropodInfo("Acanthoscurria geniculata"));
            sample.setName("따따라니");
            service.setArthropod(sample);
        }

        // Realm에서 읽어오기
        arthropodArrayList = service.getArthropods();

        // Adapter에 지정한다.
        mRecyclerView.setLayoutManager(mLayoutManager);
        RealmRecyclerViewAdapter myAdapter = new RealmRecyclerViewAdapter(arthropodArrayList);
        mRecyclerView.setAdapter(myAdapter);
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
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

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
