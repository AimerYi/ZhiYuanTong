package com.apress.gerber.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Colonel on 2017/9/19.
 */

public class PutoutActivity extends AppCompatActivity  {
    private static final int REQUEST_SIGNUP = 0;
    private static final String TAG = "PutoutActivity";
    public MainActivity mainActivity;
    private Button BtB;
    public  String A;
    public String kk ="";
    public String SS ="";
    public String ZZ ="";
    public String QQ ="";
    private ListView listView;
    public TextView PTtext3;
    public ArrayList<String>li=new ArrayList<String>();
    BmobBatch batch =new BmobBatch();
    List<BmobObject> historys = new ArrayList<BmobObject>();
    private User mNewstUser;
    public  String C;

    List<Recommend> studentList1 = new ArrayList<Recommend>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.putout_activity);

        Bmob.initialize(this,getString(R.string.bomb_application_id));
        PTtext3 = (TextView) findViewById(R.id.PTtext3);
        listView = (ListView)findViewById(R.id.lv);
        GetName xuan=new GetName();
        xuan.getlei(ThirdActivity.h);
        PTtext3.setText("    "+MainActivity.sInfo);


        BtB = (Button) findViewById(R.id.btb);
        BtB.setOnClickListener(onClick);

//        Bt2.setOnClickListener(onClick);
        /*String s="1,1,1,1,2,3,";
        List<String > list = new ArrayList<String>();
        String d[]=s.split(",");
        for (int i=0;i<d.length;i++)
        {
            list.add(d[i]);
        }
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,clear_repeat(list)));*/

    }
    public String getThirdActivity() {
        return ThirdActivity.h;

    }
    public String geted() {
        return MainActivity.ed;

    }

    public String getsInfo() {
        return MainActivity.sInfo;

    }

    public View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            queryUserInfo();
            Intent intent = new Intent(getApplicationContext(),SecondActivity .class);
            startActivity(intent);
            finish();
            /*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(intent, REQUEST_SIGNUP);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/

        }
    };

    class GetName
    {


        public  void getlei(String akind )
        {
            String bql ="select * from majoykind where kind ='"+akind+"'";
            new BmobQuery<Majoykind>().doSQLQuery(bql,new SQLQueryListener<Majoykind>()
            {

                public void done(BmobQueryResult<Majoykind> result, BmobException e)
                {
                    if(e ==null)
                    {
                        List<Majoykind> list = (List<Majoykind>) result.getResults();
                        if(list!=null && list.size()>0){
                            for(Majoykind majoykind:list)
                            {
                               //String B= majoykind.getnum();//通过kind获取num
                                GetName fei=new GetName();
                                fei.getname(majoykind.getnum());
                                //Toast.makeText(getApplicationContext(),B,Toast.LENGTH_LONG).show();
                            }

                        }
                        else
                        {
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


    public  void getname(String anum)
    {
        String bql=null;
        if(MainActivity.sInfo.equals("分数"))
        {
            String C = ""+(Integer.parseInt(MainActivity.ed)-15);
            String D = ""+(Integer.parseInt(MainActivity.ed)+6);
             bql ="select * from 2017 where num='"+anum+"' and score > "+C+" and score < "+D+"";
        }else {
            String C = ""+(Integer.parseInt(MainActivity.ed)-5000);
            String D = ""+(Integer.parseInt(MainActivity.ed)+15000);
             bql ="select * from 2017 where num='"+anum+"' and rank > "+C+" and rank < "+D+"";
        }

        new BmobQuery<School>().doSQLQuery(bql,new SQLQueryListener<School>()
        {

            @Override
            public void done(BmobQueryResult<School> result, BmobException e)
            {
                if(e ==null)
                {
                    List<School> list = (List<School>) result.getResults();
                    if(list!=null && list.size()>0){

                        for(School school:list)
                        {
                            kk=kk+school.gets_name()+",";
                            SS=SS+school.getscore()+",";
                            ZZ=ZZ+school.getmajors_name()+",";
                            QQ=QQ+school.getrank()+",";

                        }
                        List<Recommend> studentList = new ArrayList<Recommend>();

                        PutoutActivity cc=new PutoutActivity();
                        if(MainActivity.sInfo.equals("分数"))
                        {
                            cc.a(kk,SS,ZZ,studentList);
                        }
                        else
                        {
                            cc.a(kk,QQ,ZZ,studentList);

                        }
                        Collections.sort(studentList);

                        listSort(studentList);
                        studentList1=studentList;
                        RecommendAdapter adapter = new RecommendAdapter(getApplicationContext(),R.layout.putout_item,studentList);
                        listView.setAdapter(adapter);
//
//                        Toast.makeText(getApplicationContext(),kk,Toast.LENGTH_LONG).show();
                        /*Toast.makeText(getApplicationContext(), kk, Toast.LENGTH_LONG).show();*/

                    }
                    else
                    {
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
    public void listSort(List<Recommend>ii1)
    {


        Iterator<Recommend>ii=ii1.iterator();
        int count=0;
        while(ii.hasNext())
        {
            count++;

            Recommend s=(Recommend)ii.next();

            s.setName(count+"."+s.getName());
        }

    }
    public List<String> stringToList(String strs){
        String str[] = strs.split(",");
        return Arrays.asList(str);
    }
 }
    public   static List<String> clear_repeat(List<String>list)
    {
        Set set = new HashSet<String>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }
    public void strit(String k1){
        String d[]=k1.split(",");

        List<String > list = new ArrayList<String>();

        for (int i=0;i<d.length;i++)
        {
            list.add(d[i]);
        }

//        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list));
    }

    public void a(String a1,String a2,String a3,List<Recommend> studentList)
    {
        String s1=a1;
        String s2=a2;
        String s3=a3;

        String d1[]=s1.split(",");
        String d2[]=s2.split(",");
        String d3[]=s3.split(",");
        for (int i=0;i<d1.length;i++)
        {
            Recommend rec = new Recommend();
            /*History history = new History();*/
            rec.setName(d1[i]);
            rec.setAge(d2[i]);
            rec.setGender(d3[i]);

            studentList.add(rec);

        }
    }
    public void queryUserInfo() {

        BmobQuery<User> query = new BmobQuery<>();
        Log.d(TAG, "queryUserInfo: " + BmobUser.getCurrentUser().getObjectId());
        query.getObject(BmobUser.getCurrentUser().getObjectId(), new QueryListener<User>() {
            @Override
            public void done(final User user,final BmobException e) {
                if (e == null) {
                    mNewstUser = user;
                    mNewstUser.getObjectId();
                    int count=0;
                    Iterator<Recommend>itr=studentList1.iterator();
                    Recommend rec;
                    while(count<30)
                    {
                        History history = new History();
                        count++;
                        if(itr.hasNext())
                        {
                            rec=(Recommend)itr.next();
                            String age=rec.getAge();
                            String gender=rec.getGender();
                            String name=rec.getName();
                            if(MainActivity.sInfo.equals("分数")){
                                history.setrecord("1");
                            }
                            else
                            {
                            history.setrecord("2");
                            }
                            history.setid(mNewstUser.getObjectId());
                            history.setmajoy(gender);
                            history.sets_name(name);
                            history.setsocoure(age);
                            history.settime();
                            historys.add(history);

                        }
                        else
                        {

                            String age="0";
                            String gender="0";
                            String name="0";

                            if("分数".equals(MainActivity.sInfo)){
                                history.setrecord("1");
                            }
                            else
                            {
                                history.setrecord("2");
                            }
                            history.setid(mNewstUser.getObjectId());
                            history.setmajoy(gender);
                            history.sets_name(name);
                            history.setsocoure(age);
                            history.settime();
                            historys.add(history);

                        }
                    }
                    new BmobBatch().insertBatch(historys).doBatch(new QueryListListener<BatchResult>() {

                        @Override
                        public void done(List<BatchResult> o, BmobException e) {
                            if(e==null){
                                for(int i=0;i<o.size();i++){
                                    BatchResult result = o.get(i);
                                    BmobException ex =result.getError();
                                    Log.d("i","a"+i);
                                    if(ex==null){
                                        studentList1.clear();
                                    }else{

                                    }
                                }
                            }else{
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });
                }
                else {
                    Log.e(TAG, "done: " + e.getMessage() + "");
                }

            }

        });

    }


}

