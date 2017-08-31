package com.example.yd.sockettest1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yd.sockettest1.R;
import com.example.yd.sockettest1.common.CommonRequest;
import com.example.yd.sockettest1.common.CommonResponse;
import com.example.yd.sockettest1.http.ResponseHandler;
import com.example.yd.sockettest1.ui.RoundedImageView;

import static com.example.yd.sockettest1.Urls.CommonUrl.trans_url;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RoundedImageView home_head;
    TextView home_name;
    private ListView pet_list;
    private static String user_id="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        home_head=(RoundedImageView)findViewById(R.id.head);
        home_name=(TextView)findViewById(R.id.home_name);
        pet_list=(ListView)findViewById(R.id.pet_list) ;

            setInfo();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setInfo()  {
        CommonRequest request = new CommonRequest();

        request.addRequestParam("user_id",user_id);

        sendHttpPostRequest(trans_url, request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                if (response.getDataList().size() >= 0) {


                } else {
                }
            }

            @Override
            public void fail(String failCode, String failMsg) {
            }
        }, true);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.dog_house) {
            // Handle the camera action
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.dog) {

        } else if (id == R.id.news) {

        } else if (id == R.id.order) {

        }else if(id==R.id.person){

        }else if(id==R.id.cancel){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
