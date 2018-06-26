package com.apress.gerber.project;

/**
 * Created by Colonel on 2017/9/26.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SQLQueryListener;

import static android.R.attr.id;
import static android.R.attr.inflatedId;
import static android.R.attr.minResizeWidth;
import static cn.bmob.v3.Bmob.getApplicationContext;

public class MyHistory extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = "MyHistory";
    private TabLayout tab_title;
    private ViewPager vp_pager;
    private ListView listView_one;
    private ListView listView_two;
    private ListView listView_three;
    private TextView F_one_tv1,F_one_tv2,F_one_tv3;
    private TextView F_two_tv1,F_two_tv2,F_two_tv3;
    private TextView F_three_tv1,F_three_tv2,F_three_tv3;
    private List<String> list_title;                                      //tab名称列表
    private List<View> listViews;
    private List<Fragment> list_fragment;
    private View newsView;                                                //定义新闻页面
    private View sportView;                                               //定义体育页面
    private View funView;                                                 //定义娱乐页面
    private viewAdapter vAdapter;                                         //定义以view为切换的adapter
    private fragmentAdapter fAdapter;                                     //定义以fragment为切换的adapte
    public History_Fragment_one nFragment;
    private History_Fragment_two sFragment;
    private History_Fragment_three fFragment;
    private int[] tabImg;
    List<History>listMH1=new ArrayList<History>();//存储最近一次存储时间的list集合，只存十条
    List<History>listMH2=new ArrayList<History>();
    List<History>listMH3=new ArrayList<History>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initControls();
        Bmob.initialize(this,getString(R.string.bomb_application_id));
        viewChanage();
        //fragmentChange();

        rem_history text=new rem_history();
        text.input();
        text.input1();
        text.input2();
         /*listView = (ListView)(inflate.inflate(R.layout.fragment_one, null)).findViewById(R.id.fragment_one);*/



        final Handler handler = new Handler();
        Timer timer2 = new Timer();
        TimerTask testing = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        HistoryAdapter adapter_1= new HistoryAdapter(getApplicationContext(),R.layout.putout_item,listMH1);
                        listView_one.setAdapter(adapter_1);
                        HistoryAdapter adapter_2 = new HistoryAdapter(getApplicationContext(),R.layout.putout_item,listMH2);
                        listView_two.setAdapter(adapter_2);
                        HistoryAdapter adapter_3= new HistoryAdapter(getApplicationContext(),R.layout.putout_item,listMH3);
                        listView_three.setAdapter(adapter_3);
                    }

                });


            }
        };
        timer2.schedule(testing, 2000);

    }
    /**
     * 初始化控件
     */
    public View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private void initControls()
    {
        tab_title = (TabLayout)findViewById(R.id.tab_title);
        vp_pager = (ViewPager)findViewById(R.id.vp_pager);
        //为tabLayout上的图标赋值
        tabImg = new int[]{R.drawable.account,R.drawable.boy,R.drawable.girl};
    }

    /**
     * 采用在viewpager中切换 view 的方式,并添加了icon的功能
     */
    private void viewChanage()
    {
        listViews = new ArrayList<>();
        LayoutInflater mInflater = getLayoutInflater();

        newsView = mInflater.inflate(R.layout.fragment_one, null);
        sportView = mInflater.inflate(R.layout.fragment_two, null);
        funView = mInflater.inflate(R.layout.fragment_three, null);
        listView_one = (ListView) newsView.findViewById(R.id.fragment_one);
        listView_two = (ListView) sportView.findViewById(R.id.fragment_two);
        listView_three = (ListView) funView.findViewById(R.id.fragment_three);
        F_one_tv1= (TextView) newsView.findViewById(R.id.F_one_text1);
        F_one_tv2= (TextView) newsView.findViewById(R.id.F_one_text2);
        F_one_tv3= (TextView) newsView.findViewById(R.id.F_one_text3);
        F_two_tv1= (TextView) sportView.findViewById(R.id.F_two_text1);
        F_two_tv2= (TextView) sportView.findViewById(R.id.F_two_text2);
        F_two_tv3= (TextView) sportView.findViewById(R.id.F_two_text3);
        F_three_tv1= (TextView) funView.findViewById(R.id.F_three_text1);
        F_three_tv2= (TextView) funView.findViewById(R.id.F_three_text2);
        F_three_tv3= (TextView) funView.findViewById(R.id.F_three_text3);

        listViews.add(newsView);
        listViews.add(sportView);
        listViews.add(funView);

        list_title = new ArrayList<>();
        list_title.add("历史记录1");
        list_title.add("历史记录2");
        list_title.add("历史记录3");

        //设置TabLayout的模式,这里主要是用来显示tab展示的情况的
        //TabLayout.MODE_FIXED          各tab平分整个工具栏,如果不设置，则默认就是这个值
        //TabLayout.MODE_SCROLLABLE     适用于多tab的，也就是有滚动条的，一行显示不下这些tab可以用这个
        //                              当然了，你要是想做点特别的，像知乎里就使用的这种效果
        tab_title.setTabMode(TabLayout.MODE_FIXED);

        //设置tablayout距离上下左右的距离
        //tab_title.setPadding(20,20,20,20);

        //为TabLayout添加tab名称
        /*tab_title.addTab(tab_title.newTab().setText(list_title.get(0)));
        tab_title.addTab(tab_title.newTab().setText(list_title.get(1)));
        tab_title.addTab(tab_title.newTab().setText(list_title.get(2)));*/


        vAdapter = new viewAdapter(this,listViews,list_title,tabImg);
        vp_pager.setAdapter(vAdapter);
        //将tabLayout与viewpager连起来
        tab_title.setupWithViewPager(vp_pager);
    }

    /**
     * 采用viewpager中切换fragment
     */
    private void fragmentChange()
    {
        list_fragment = new ArrayList<>();

        nFragment = new History_Fragment_one();
        sFragment = new History_Fragment_two();
        fFragment = new History_Fragment_three();


        list_fragment.add(nFragment);
        list_fragment.add(sFragment);
        list_fragment.add(fFragment);

        list_title = new ArrayList<>();
        list_title.add("历史记录1");
        list_title.add("历史记录2");
        list_title.add("历史记录3");

        fAdapter = new fragmentAdapter(getSupportFragmentManager(),list_fragment,list_title);
        vp_pager.setAdapter(fAdapter);

        //将tabLayout与viewpager连起来
        tab_title.setupWithViewPager(vp_pager);
    }

    @Override
    public void onClick(View view) {

    }


    class rem_history
    {

        void input()
        {
            BmobQuery<User> query = new BmobQuery<>();
            Log.d(TAG, "queryUserInfo: " + BmobUser.getCurrentUser().getObjectId());
            query.getObject(BmobUser.getCurrentUser().getObjectId(), new QueryListener<User>() {
                @Override
                public void done(final User user, final BmobException e) {
                    if (e == null) {

                        String bql ="select * from History where id ='"+user.getObjectId()+"'limit 0,30 order by createdAt desc";
                        new BmobQuery<History>().doSQLQuery(bql,new SQLQueryListener<History>()
                        {

                            public void done(BmobQueryResult<History> result, BmobException e)
                            {
                                if(e ==null)
                                {
                                    List<History> list = (List<History>) result.getResults();
                                    if(list!=null && list.size()>0){
                                        String reo=new String();
                                        for(History history:list)
                                        {
                                            if(!history.getsocoure().equals("0"))
                                            {   history.sets_name(history.gets_name());

                                                listMH1.add(history);
                                            }
                                            reo=history.getrecord();
                                        }
                                        Collections.reverse(listMH1);
                                        if("1".equals(reo))
                                            F_one_tv3.setText("分数");
                                        else
                                            F_one_tv3.setText("次位号");
                                        F_one_tv1.setText("学校");
                                        F_one_tv2.setText("专业");


                                    }
                                    else
                                    {
                                        F_one_tv2.setText("没有历史记录1，请先添加");
                                        Log.i("smile", "查询成功，无数据返回");
                                    }
                                }
                                else
                                {
                                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                                }
                            }
                        });
                    }
                }
            });


        }

        void input1()
        {
            BmobQuery<User> query = new BmobQuery<>();
            Log.d(TAG, "queryUserInfo: " + BmobUser.getCurrentUser().getObjectId());
            query.getObject(BmobUser.getCurrentUser().getObjectId(), new QueryListener<User>() {
                @Override
                public void done(final User user, final BmobException e) {
                    if (e == null) {

                        String bql ="select * from History where id ='"+user.getObjectId()+"'limit 30,30 order by createdAt desc";
                        new BmobQuery<History>().doSQLQuery(bql,new SQLQueryListener<History>()
                        {

                            public void done(BmobQueryResult<History> result, BmobException e)
                            {
                                if(e ==null)
                                {
                                    List<History> list = (List<History>) result.getResults();
                                    if(list!=null && list.size()>0){
                                        String reo=new String();
                                        for(History history:list)
                                        {
                                            if(!history.getsocoure().equals("0"))
                                            {   history.sets_name(history.gets_name());

                                                listMH2.add(history);
                                            }
                                        }
                                        Collections.reverse(listMH2);
                                        if("1".equals(reo))
                                            F_two_tv3.setText("分数");
                                        else
                                            F_two_tv3.setText("次位号");
                                        F_two_tv1.setText("学校");
                                        F_two_tv2.setText("专业");
                                    }
                                    else
                                    {
                                        F_two_tv2.setText("没有历史记录2，请先添加");
                                        Log.i("smile", "查询成功，无数据返回");
                                    }
                                }
                                else
                                {
                                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                                }
                            }
                        });
                    }
                }
            });


        }


        void input2()
        {
            BmobQuery<User> query = new BmobQuery<>();
            Log.d(TAG, "queryUserInfo: " + BmobUser.getCurrentUser().getObjectId());
            query.getObject(BmobUser.getCurrentUser().getObjectId(), new QueryListener<User>() {
                @Override
                public void done(final User user, final BmobException e) {
                    if (e == null) {

                        String bql ="select * from History where id ='"+user.getObjectId()+"'limit 60,30 order by createdAt desc";
                        new BmobQuery<History>().doSQLQuery(bql,new SQLQueryListener<History>()
                        {

                            public void done(BmobQueryResult<History> result, BmobException e)
                            {
                                if(e ==null)
                                {
                                    List<History> list = (List<History>) result.getResults();
                                    if(list!=null && list.size()>0){
                                        String reo=new String();
                                        for(History history:list)
                                        {
                                            if(!history.getsocoure().equals("0"))
                                            {   history.sets_name(history.gets_name());

                                                listMH3.add(history);
                                            }
                                        }
                                        Collections.reverse(listMH3);
                                        if("1".equals(reo))
                                            F_three_tv3.setText("分数");
                                        else
                                            F_three_tv3.setText("次位号");
                                        F_three_tv1.setText("学校");
                                        F_three_tv2.setText("专业");
                                    }
                                    else
                                    {
                                        F_three_tv2.setText("没有历史记录3，请先添加");
                                        Log.i("smile", "查询成功，无数据返回");
                                    }
                                }
                                else
                                {
                                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                                }
                            }
                        });
                    }
                }
            });


        }




    }
    private void enterMainActivity() {

    }



}



















