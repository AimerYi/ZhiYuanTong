package com.apress.gerber.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private TextView tv_choose;
    private static final String[] SEX_CHOISE = {"男", "女"};
    private Spinner spinner=null;
    private Button bt1;
    private ImageView touxiang;
    public static String sInfo;
    private EditText edtext;
    public static String ed;
    private User mNewstUser;

    private MySettingActivity mySettingActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Bmob.initialize(this,getString(R.string.bomb_application_id));
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        tv_choose = (TextView) findViewById(R.id.TV_choose);
        spinner = (Spinner) findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(new ProvOnItemSelectedListener());
        bt1 = (Button) findViewById(R.id.button);
        bt1.setOnClickListener(onClick);
        edtext = (EditText) findViewById(R.id.ed_text);
       // queryUserInfo();


        /*getSupportActionBar().hide();*/
    }

    public void setShakeAnimation() {
        edtext.startAnimation(shakeAnimation(5));
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

    public String getsInfo() {
        return sInfo;
    }

    public void setsInfo(String sInfo) {
        this.sInfo = sInfo;
    }


    public View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            final String toMsg = edtext.getText().toString();
            if(TextUtils.isEmpty(toMsg)){
                setShakeAnimation();
                Toast.makeText(MainActivity.this,"输入不能为空",Toast.LENGTH_LONG).show();
                return;
            }else
            {
                ed=edtext.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("确认");//设置对话框的标题  
                builder.setMessage("您的"+ getsInfo() +"是"+ed);//设置对话框的内容  

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {//取消按钮  

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {//这个是设置确定按钮  
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                        startActivity(intent);
                    }
                });
                AlertDialog b=builder.create();
                b.show();//必须show一下才能看到对话框，跟Toast一样的道理  

            }

        }

    };


    // CycleTimes动画重复的次数
    public Animation shakeAnimation(int CycleTimes) {
        //设置偏移动画 其中new TranslateAnimation(0,10,0,10)四个值表示为 X坐标从0-->10,Y坐标从0-->10
        Animation translateAnimation = new TranslateAnimation(0, 5, 0, 5);
        //设置动画次数
        translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
        //设置动画间隔
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }


    /*   @Override
       public boolean onCreateOptionsMenu(Menu menu) {
           // Inflate the menu; this adds items to the action bar if it is present.
           getMenuInflater().inflate(R.menu.main, menu);
           return true;
       }
   */
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

        if (id == R.id.nav_camera) {
            Toast.makeText(getApplicationContext(), "敬请期待",Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(MainActivity.this, MySettingActivity.class));


        } else if (id == R.id.nav_slideshow) {
            Intent ll = new Intent(MainActivity.this, MyHistory.class);
            startActivity(ll);


        } else if (id == R.id.exit) {
            BmobUser.logOut();
            Intent ll = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(ll);
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class ProvOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapter,View view,int position,long id) {
            //获取选择的项的值 
            setsInfo(adapter.getItemAtPosition(position).toString());
            tv_choose.setText("请输入您的"+ getsInfo() +":");

        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }
   /* private void queryUserInfo() {
        BmobQuery<User> query = new BmobQuery<>();
        Log.d(TAG, "queryUserInfo: " + BmobUser.getCurrentUser().getObjectId());
        query.getObject(BmobUser.getCurrentUser().getObjectId(), new QueryListener<User>() {
            @Override
            public void done(final User user,final BmobException e) {
                if (e == null) {
                    mNewstUser = user;
                    if ("女".equals(mNewstUser.getSex())) {
                        touxiang.setImageDrawable(getDrawable(R.drawable.girl));
                    }
                } else {
                    Log.e(TAG, "done: " + e.getMessage() + "");
                }
            }
        });
    }*/
   private void queryUserInfo() {
        BmobQuery<User> query = new BmobQuery<>();
        Log.d(TAG, "queryUserInfo: " + BmobUser.getCurrentUser().getObjectId());
        query.getObject(BmobUser.getCurrentUser().getObjectId(), new QueryListener<User>() {
            @Override
            public void done(final User user,final BmobException e) {
               if (e == null) {
                    mNewstUser = user;
                   mNewstUser.getObjectId();

                    if ("女".equals(mNewstUser.getSex()))
                    {

                    }
                } else {
                   Log.e(TAG, "done: " + e.getMessage() + "");
                }
            }
        });
    }
}
