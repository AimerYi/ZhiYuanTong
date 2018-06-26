package com.apress.gerber.project;

/**
 * Created by Colonel on 2017/9/15.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;



/**
 * Created by Air on 2016/9/29.
 */

public class MySettingActivity extends BaseActivity {


    private static final String[] SEX_CHOISE = {"男", "女"};
    private TextView tvSex;
    private TextView tvLocation;
    private EditText mUserPassword;
    private EditText mNewPassword;
    private EditText mEmail;
    public Spinner mSex;
    public String sex;
    private EditText mPhone;
    private Button mUpdate;
    private User mCurrentUser;
    public String mSexSetted;
    private ImageView TX_image;
    private String old_password;
    private TextView userId;
    private List<BmobObject> mTaskList = new ArrayList<>();
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String UserObjectId;
    private ArrayAdapter<String> adapter_sex;
    public static String TAG = "LoginTest";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting);
        Bmob.initialize(this,getString(R.string.bomb_application_id));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCurrentUser = BmobUser.getCurrentUser(User.class);
        UserObjectId = mCurrentUser.getObjectId();
        userId = (TextView) findViewById(R.id.user_id);
        String username = mCurrentUser.getUsername();
        userId.setText("" + mCurrentUser.getUsername());
        tvSex = (TextView) findViewById(R.id.tv_sex);
        mUserPassword = (EditText) findViewById(R.id.user_password);
        mNewPassword = (EditText) findViewById(R.id.user_newpassword);
        mEmail = (EditText) findViewById(R.id.email);
        mSex = (Spinner) findViewById(R.id.sex);
        mPhone = (EditText) findViewById(R.id.mobile_phone_number);
        mUpdate = (Button) findViewById(R.id.update);
        TX_image = (ImageView) findViewById(R.id.tx_image);

        mEmail.setText(mCurrentUser.getEmail());
        mPhone.setText(mCurrentUser.getMobilePhoneNumber());
        sp = getSharedPreferences(Constants.SP_USER_INFO, MODE_PRIVATE);
        editor = sp.edit();
        old_password = sp.getString(Constants.SP_USER_PASSWORD, "");
        adapter_sex = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SEX_CHOISE);
        adapter_sex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSex.setAdapter(adapter_sex);

        mSex.setOnItemSelectedListener(new SpinnerTypeSelectedListener());

        mSex.setVisibility(View.VISIBLE);

        String sex = mCurrentUser.getSex();

        for (int j = 0; j < 2; j++) {
            if (SEX_CHOISE[j].equals(sex)) {
                mSex.setSelection(j);
                break;
            }
        }

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mUserPassword.getText().toString())) {
                    Toast.makeText(MySettingActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(mNewPassword.getText().toString())) {
                    if (!(mNewPassword.getText().toString().length() >= 6 && mNewPassword.getText().toString().length() <= 20 && (!TextUtils.isEmpty(mNewPassword.getText().toString())))) {
                        Toast.makeText(MySettingActivity.this, "请输入的密码位数不小于6位，不超过20位", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (old_password.equals(mUserPassword.getText().toString())) {
                    updateSql();
                    finish();
                } else {
                    Toast.makeText(MySettingActivity.this, "原密码错误", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class SpinnerTypeSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            mSexSetted = SEX_CHOISE[arg2];
            sex = SEX_CHOISE[arg2];
            if (mSexSetted.equals("女")) {
                TX_image.setImageResource(R.drawable.girl);
            } else {
                TX_image.setImageResource(R.drawable.boy);
            }

        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }


    private void updateSql() {
        if (mNewPassword == null) {

        } else {
            User user = new User();
            user.setSex(mSexSetted);

            if (!TextUtils.isEmpty(mNewPassword.getText().toString())) {
                user.setPassword(mNewPassword.getText().toString());
            }
            user.setEmail(mEmail.getText().toString());
            user.setMobilePhoneNumber(mPhone.getText().toString());
            user.update(mCurrentUser.getObjectId(), new UpdateListener() {

                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        if (!TextUtils.isEmpty(mNewPassword.getText().toString())) {
                            editor.putString(Constants.SP_USER_PASSWORD, mNewPassword.getText().toString());
                        }
                        editor.putString(Constants.SP_USER_SEX, sex);
                        editor.putString(Constants.SP_USER_EMAIL, mEmail.getText().toString());
                        editor.putString(Constants.SP_USER_PHONENUMBER, mPhone.getText().toString());

                        editor.commit();
                        Toast.makeText(MySettingActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MySettingActivity.this, "更新失败，请重新填写信息", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "更新个人信息失败1：" + e.getMessage() + "," + e.getErrorCode());
                    }
                }
            });
        }
    }

}
