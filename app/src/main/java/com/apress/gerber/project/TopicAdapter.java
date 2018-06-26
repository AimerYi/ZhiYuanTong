package com.apress.gerber.project;

/**
 * Created by Colonel on 2017/9/19.
 */

import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.List;
import java.util.Map;


/**
 * 参考这位博主的源码http://blog.csdn.net/who0am0i/article/details/45200709 改进的地方如下：
 * 1.支持几行几列的radiobutton显示（不会随着手机分辨率而改变） 2.listview上下滑动radiobutton选中状态显示错乱的修复
 *
 * @author nihaoqiulinhe
 * @createTime 2017年1月3日 下午2:01:12
 */
public class TopicAdapter extends BaseAdapter {
    private static final String TAG = "TopicAdapter";
    String KEY = "list_topic_item";

    //四个listview的每个对应的getID状态
    private int optionID1, optionID2, optionID3, optionID4;

    private SparseIntArray checked = new SparseIntArray();//保存哪些radio被选中，相当于hashmap但效率更高

    private LayoutInflater mInflater;
    private Context context;

    private List<Map<String, TopicItem>> mData;//数据源

    public TopicAdapter(Context context, List<Map<String, TopicItem>> data) {
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        init();
    }

    private void init() {
        mData.clear();
    }

    @Override
    public int getCount() {
        int count = mData == null ? 0 : mData.size();
        return count;
    }

    @Override
    public Object getItem(int position) {
		/*
		 * Object obj = records != null && records.size() > position ?
		 * records.get(position) : null; return obj;
		 */
        return null;
    }

    @Override
    public long getItemId(int position) {
        //return position;
        return 0;
    }

    private Integer index = -1;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item, null);

            holder = new Holder();
            holder.question = (TextView) convertView.findViewById(R.id.row_text);
            holder.option = (RadioGroup) convertView.findViewById(R.id.radio_group);
            holder.option1 = (RadioButton) convertView.findViewById(R.id.item_rbt_y);
            optionID1 = holder.option1.getId();
            holder.option2 = (RadioButton) convertView.findViewById(R.id.item_rbt_n);
            optionID2 = holder.option2.getId();

            //为了能够在一行显示两个radiobutton:获取屏幕的宽度
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();//左侧设置的间距

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.option2.getLayoutParams();
            holder.option2.setLayoutParams(params);

            /*holder.option3 = (RadioButton) convertView.findViewById(R.id.topic_item_option3);
            optionID3 = holder.option3.getId();
            holder.option4 = (RadioButton) convertView.findViewById(R.id.topic_item_option4);
            optionID4 = holder.option4.getId();*/

            /*LinearLayout.LayoutParams paramsTwo = (LinearLayout.LayoutParams) holder.option4.getLayoutParams();
            holder.option4.setLayoutParams(paramsTwo);*/

            holder.option.setTag(position);

            holder.option.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        index = (Integer) v.getTag();
                    }
                    return false;
                }
            });

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
            holder.option.setTag(position);
        }

        //在显示RadioButton值之前，取消对其监听，否则会出现混乱的效果。
        holder.option.setOnCheckedChangeListener(null);
        holder.option.clearCheck();

        if (checked.indexOfKey(position) > -1) {
            holder.option.check(checked.get(position));
        } else {
            holder.option.clearCheck();
        }
        holder.option.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId > -1) {
                    checked.put(position, checkedId);

                    TopicItem item = mData.get(position).get(KEY);

                    int RadioButtonId = group.getCheckedRadioButtonId();

                    if (RadioButtonId == optionID1) {
                        item.setUserAnswerId(item.getOptionList().get(0).getAnswerOption().trim());
                    } else if (RadioButtonId == optionID2) {
                        item.setUserAnswerId(item.getOptionList().get(1).getAnswerOption().trim());
                    } /*else if (RadioButtonId == optionID3) {
                        item.setUserAnswerId(item.getOptionList().get(2).getAnswerOption().trim());
                    } else if (RadioButtonId == optionID4) {
                        item.setUserAnswerId(item.getOptionList().get(3).getAnswerOption().trim());
                    }*/
                    mData.get(position).put(KEY, item);
                } else {
                    if (checked.indexOfKey(position) > -1) {
                        checked.removeAt(checked.indexOfKey(position));
                    }
                }
            }
        });

        TopicItem item = mData.get(position).get(KEY);
        if (item != null) {
            //防止afterTextChanged自动执行
            holder.question.setText(item.getQuestion());
            holder.option1.setText(item.getOptionList().get(0).getAnswer().toString());
            holder.option2.setText(item.getOptionList().get(1).getAnswer().toString());
           /* holder.option3.setText(item.getOptionList().get(2).getAnswer().toString());
            holder.option4.setText(item.getOptionList().get(3).getAnswer().toString());*/

        }
        holder.option.clearFocus();
        if (index != -1 && index == position) {
            holder.option.requestFocus();
        }
        return convertView;
    }

    private class Holder {
        private TextView question;
        private RadioGroup option;
        private RadioButton option1;
        private RadioButton option2;
        /*private RadioButton option3;
        private RadioButton option4;*/
    }
}