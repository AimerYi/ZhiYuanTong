package com.apress.gerber.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Colonel on 2017/8/11.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 参考这位博主的源码http://blog.csdn.net/who0am0i/article/details/45200709 改进的地方如下：
 * 1.支持几行几列的radiobutton显示（不会随着手机分辨率而改变） 2.listview上下滑动radiobutton选中状态显示错乱的修复
 *
 * @author nihaoqiulinhe
 * @createTime 2017年1月3日 下午2:01:12
 */
public class ThirdActivity extends Activity {
    private static final String TAG = "ThirdActivity";
    public Context context;
    private ListView topic_listview;
    private Button button_sent;
    private BaseAdapter mAdapter;
    public String a[];
    private List<Map<String, TopicItem>> data = new ArrayList<Map<String, TopicItem>>();
    JSONObject data_json;
    public int i;
    public String g;
    public String j;
    public int max1;
    private Button bt5;
    public int max2;
    public static String h;
    String KEY = "list_topic_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);

        this.context = this;
        topic_listview = (ListView) findViewById(R.id.list_view);
        mAdapter = new TopicAdapter(context, data);
        topic_listview.setAdapter(mAdapter);
        initData();
        getData();
        bt5 = (Button) findViewById(R.id.bt5);
        bt5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        button_sent = (Button) findViewById(R.id.bt4);
        button_sent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                zuida();
                chazhi();
                String t = "";
                int k=0;
                for (int i = 0; i < data.size(); i++) {
                    if(data.get(i).get(KEY).getUserAnswerId().trim() == "-1"){
                        t = t +(i+1)+"、";
                        k=-1;
                    }

                    /*t = t + "第 " + (i + 1) + " 题  -----"+h+"----->您选择了【选项" + data.get(i).get(KEY).getUserAnswerId().trim() + "】 \n";*/
                }

                if (k==-1){
                    t=t.substring(0,t.length()-1);
                    Toast.makeText(context, "您还有第"+t+"题未选", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(ThirdActivity.this,PutoutActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void initData() {
        try {
            data_json = new JSONObject();
            data_json.put("result", "success");
            JSONArray array = new JSONArray();


            for (int i = 0; i <45; i++) {

                String [] strArray = new String [45];
                strArray[0] = "当我一个人独处时，会感到更愉快。";
                strArray[1] = "在学习和生活中我喜欢独自筹划，不愿受别人干涉。";
                strArray[2] = "听别人谈“家中被盗”一类的事，很难引起我的同情。";
                strArray[3] = "和一群人在一起的时候，我总想不出恰当的话来说。";
                strArray[4] = "我经常不停地思考某一问题，直到想出正确的答案。";
                strArray[5] = "对别人借我的和我借别人的东西，我都能记得很清楚。";
                strArray[6] = "我喜欢抽象思维的工作，不喜欢动手的工作。";
                strArray[7] = "我喜欢在做事情前，对此事情做出细致的安排。";
                strArray[8] = "和别人谈判时，我总是很容易放弃自己的观点。";
                strArray[9] = "对于将来的职业，我愿意从事虽然工资少、但是比较稳定的职业。";
                strArray[10] = "我在开始一个计划前会花很多时间去计划。";
                strArray[11] = "我喜欢监督事情直至完工。";
                strArray[12] = "我办事很少思前想后。";
                strArray[13] = "我喜欢亲自动手制作一些小玩意儿，从中得到乐趣。";
                strArray[14] = "我喜欢为重大决策负责。";
                strArray[15] = "和不熟悉的人交谈对我来说毫不困难。";
                strArray[16] = "我很容易结识同性别的朋友。";
                strArray[17] = "我乐于按父母和老师的安排去做事。";
                strArray[18] = "如果掌握一门精湛的手艺并能以此赚到足够多的钱，我会感到满足。";
                strArray[19] = "我愿意冒一点险以求进步。";
                strArray[20] = "我小时候经常把玩具拆开，把里面看个究竟。";
                strArray[21] = "我有文艺方面的天赋。";
                strArray[22] = "我喜欢作一名教师。";
                strArray[23] = "假如将我单独一个人留着实验室做实验，我会感到非常无聊。";
                strArray[24] = "我喜欢协助老师做班级管理类的工作。";
                strArray[25] = "与言情小说相比，我更喜欢推理小说。";
                strArray[26] = "如果待遇相同，我宁愿当商品推销员，而不愿当图书管理员。";
                strArray[27] = "当我遵循成规时，我感到安全。";
                strArray[28] = "我擅长于检查细节。";
                strArray[29] = "我花钱时小心翼翼。";
                strArray[30] = "我很渴望参加探险活动。";
                strArray[31] = "我喜欢按部就班地完成要做的工作。";
                strArray[32] = "我喜欢竞争。";
                strArray[33] = "当接受新任务后，我喜欢以自己的独特方法去完成它。";
                strArray[34] = "我很难做那种需要持续集中注意力的工作。";
                strArray[35] = "我有很强的想象力。";
                strArray[36] = "我喜欢做戏剧、音乐、歌舞、新闻采访等方面的工作。";
                strArray[37] = "比起普通的游戏，我更喜欢需要动脑子的智力游戏。";
                strArray[38] = "集体讨论中，我往往保持沉默。";
                strArray[39] = "我喜欢成为人们注意的焦点。";
                strArray[40] = "我更喜欢自己下了赌注的比赛或游戏。";
                strArray[41] = "我喜欢参加各种各样的聚会。";
                strArray[42] = "我喜欢讨价还价。";
                strArray[43] = "我喜欢不时地夸耀一下自己取得的好成就。";
                strArray[44] = "我可以花很长的时间去想通事情的道理。";
                int j=i+1;
                JSONObject object = new JSONObject();
                object.put("questionId", "1");
                object.put("question",j+"."+strArray[i] );
                object.put("answerId", "");
                object.put("userAnswerId", "-1");

                JSONArray sarray = new JSONArray();
                JSONObject sobject1 = new JSONObject();
                sobject1.put("answer", " 是");
                sobject1.put("answerOption", "1");
                sarray.put(sobject1);

                JSONObject sobject2 = new JSONObject();
                sobject2.put("answer", "否");
                sobject2.put("answerOption", "2");
                sarray.put(sobject2);

                object.put("optionList", sarray);
                array.put(object);

            }
            data_json.put("list", array);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public  int zuida(){
        int x,y,max1x,max2x;
        int b[]=null;
        b=new int[6];
        b[0]=0;//A
        b[1]=0;//I
        b[2]=0;//R
        b[3]=0;//C
        b[4]=0;//E
        b[5]=0;//S
        for ( x = 1; x <=45;x++) {
            if(data.get(x-1).get(KEY).getUserAnswerId().trim() == "1"){
                if(x==7||x==13||x==34||x==36||x==37||x==44){//A+1
                    b[0]++;
                }
                if(x==8){//A-1
                    b[0]--;
                }
                if(x==22){//A+2
                    b[0]=b[0]+2;
                }
                if(x==1||x==5||x==7||x==26||x==36||x==38||x==45){//I+1
                    b[1]++;
                }
                if(x==24||x==35){//I--
                    b[1]--;
                }
                if(x==1||x==2||x==4||x==14||x==19||x==21){//R+1
                    b[2]++;
                }
                if(x==7||x==16||x==24){//R-1
                    b[2]--;
                }
                if(x==8||x==9||x==10||x==11||x==18||x==25||x==28||x==29||x==32){//c+1   没有C-1
                    b[3]++;
                }
                if(x==6||x==12||x==20||x==27||x==30||x==31||x==33||x==41||x==43) {//E+1
                    b[4]++;
                }
                if(x==10){//E-1
                    b[4]--;
                }
                if(x==16||x==17||x==40||x==42){//S+1
                    b[5]++;
                }
                if(x==3||x==4||x==39){//S-1
                    b[5]--;
                }
                if(x==23){//S+2
                    b[5]=b[5]+2;
                }
            }
            else if(data.get(x-1).get(KEY).getUserAnswerId().trim() == "2"){
                if(x==26){//A+1
                    b[0]++;
                }
                if(x==13){//A-1
                    b[0]--;
                }
                if(x==24||x==35){//I+1   没有I-1
                    b[1]++;
                }
                if(x==7||x==12||x==20||x==40||x==41){//R+1
                    b[2]++;
                }
                if(x==23||x==31||x==42){//R-1
                    b[2]--;
                }
                if(x==27||x==33||x==41){//C+1
                    b[3]++;
                }
                if(x==10||x==18||x==31||x==32||x==36){//C-1
                    b[3]--;
                }
                if(x==10){//E+1  没有E-1
                    b[4]++;
                }
                if(x==1||x==3||x==4){//S+1
                    b[5]++;
                }
            }
        }
        String P=b[0]+"";
        Log.i("smile", "A的值位  ："+P);
        String Q=b[1]+"";
        Log.i("smile", "I的值位  ："+Q);
        String R=b[2]+"";
        Log.i("smile", "R的值位  ："+R);
        String S=b[3]+"";
        Log.i("smile", "C的值位  ："+S);
        String Y=b[4]+"";
        Log.i("smile", "E的值位  ："+Y);
        String U=b[5]+"";
        Log.i("smile", "S的值位  ："+U);



        max1=b[0];
        max1x=0;
        for(y=1;y<6;y++){
            if(max1<b[y]){
                max1x=y;
                max1=b[y];
            }
        }
        b[max1x]=-10;
        max2=b[0];
        max2x=0;
        for(y=1;y<6;y++){
            if(max2<b[y]){
                max2x=y;
                max2=b[y];
            }
        }
        switch(max1x){
            case 0:j="A";break;
            case 1:j="I";break;
            case 2:j="R";break;
            case 3:j="C";break;
            case 4:j="E";break;
            case 5:j="S";break;

        }
        switch(max2x){
            case 0:g="A";break;
            case 1:g="I";break;
            case 2:g="R";break;
            case 3:g="C";break;
            case 4:g="E";break;
            case 5:g="S";break;

        }
        return  0;
    }

    public void chazhi(){
        if(max1-max2>3){
            h=j;
        }
        else{
            h=j+g;
        }
        Log.i("smile", "值位  ："+h);
    }


    private void getData() {
        System.out.println(data_json.toString());
        data.clear();
        if (data_json.optString("result").equals("success")) {

            if (data_json.optJSONArray("list") != null) {
                for (int i = 0; i < data_json.optJSONArray("list").length(); i++) {
                    JSONObject object = data_json.optJSONArray("list").optJSONObject(i);
                    TopicItem topic = new TopicItem();
                    topic.setAnswerId(object.optString("answerId"));
                    topic.setQuestionId(object.optString("questionId"));
                    topic.setQuestion(object.optString("question"));
                    topic.setUserAnswerId(object.optString("userAnswerId"));

                    List<OptionItem> optionList = new ArrayList<OptionItem>();
                    for (int j = 0; j < object.optJSONArray("optionList").length(); j++) {
                        JSONObject object_option = object.optJSONArray("optionList").optJSONObject(j);

                        OptionItem option = new OptionItem();
                        option.setAnswerOption(object_option.optString("answerOption"));
                        option.setAnswer(object_option.optString("answer"));

                        optionList.add(option);
                    }
                    topic.setOptionList(optionList);

                    Map<String, TopicItem> list_item = new HashMap<String, TopicItem>();
                    list_item.put(KEY, topic);
                    data.add(list_item);
                }
                mAdapter.notifyDataSetChanged();
            }
        }
    }

}

