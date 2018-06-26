package com.apress.gerber.project;

/**
 * Created by Colonel on 2017/9/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Colonel on 2017/9/13.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;




public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    @BindView(R.id.input_name)
    EditText _nameText;
    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_mobile)
    EditText _mobileText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;
    @BindView(R.id.sign_up_confirm)
    AppCompatButton _signupButton;
    @BindView(R.id.link_login)
    TextView mLinkLogin;
    @BindView(R.id.sex_spn)
    Spinner sexSpn;
    private String msex = "男";
    private String name;
    private String email;
    private String mobile;
    private String password;
    private User mCurrentUser = new User();
    private static final String[] SEX_CHOISE = {"男", "女"};

    private String mUserObjectID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        Bmob.initialize(this,getString(R.string.bomb_application_id));
        ButterKnife.bind(this);
        initView();
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        mLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition( R.anim.push_left_in,R.anim.push_left_out);
            }
        });
    }

    private void initView() {
        ArrayAdapter<String> sex_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SEX_CHOISE);
        sex_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpn.setAdapter(sex_adapter);
        sexSpn.setOnItemSelectedListener(new SpinnerTypeSelectedListener());
    }

    public void signup() {
        if (!validate()) {
            onSignupFailed();
            return;
        }
        _signupButton.setEnabled(false);
        name = _nameText.getText().toString();
        email = _emailText.getText().toString();
        mobile = _mobileText.getText().toString();
        password = _passwordText.getText().toString();
        mCurrentUser.setUsername(name);
        mCurrentUser.setPassword(password);
        mCurrentUser.setEmail(email);
        mCurrentUser.setMobilePhoneNumber(mobile);
        mCurrentUser.setSex(msex);

        try {
            userRegister(mCurrentUser);

        } catch (RuntimeException e) {
            _signupButton.setEnabled(true);
            Toast.makeText(SignupActivity.this, "注册失败，请稍后再试", Toast.LENGTH_SHORT).show();
        }

        // Log.d(TAG,mSex.getText().toString());
        /*mCurrentUser.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {

                    mUserObjectID = user.getObjectId();

                    //建立用户积分表
                    user.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                onSignupSuccess();
                            } else {
                                Toast.makeText(SignupActivity.this, "注册失败，请稍后再试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Log.d(TAG, "注册失败" + e.getMessage() + "," + e.getErrorCode());
                    if (e.getErrorCode() == 202)
                        Toast.makeText(SignupActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                    _signupButton.setEnabled(true);
                    if (e.getErrorCode() == 301)
                        Toast.makeText(SignupActivity.this, "请输入有效手机号码！", Toast.LENGTH_SHORT).show();
                    Toast.makeText(SignupActivity.this, "请检测信息是否填写正确！", Toast.LENGTH_SHORT).show();
                }

            }
        });*/


    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        Toast.makeText(SignupActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

        //清除缓存，重新回到登陆界面
        BmobUser.logOut();
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        saveinfo_location();
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "请检测信息是否填写正确！", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 1) {
            _nameText.setError(getString(R.string.nemalength));
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError(getString(R.string.login_not_match_email));
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() != 11) {
            _mobileText.setError(getString(R.string.signup_phonenumber_not_match));
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            _passwordText.setError(getString(R.string.login_not_match_password));
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 6 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError(getString(R.string.signup_password_not_match));
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }
        return valid;
    }


    class SpinnerTypeSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            ((TextView) arg0.getChildAt(0)).setTextColor(Color.BLACK);
            msex = SEX_CHOISE[arg2];
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }



    private void saveinfo_location() {
        SharedPreferences prf = getSharedPreferences(Constants.SP_USER_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = prf.edit();
        editor.putString(Constants.SP_USER_NAME, name);
        editor.putString(Constants.SP_USER_PASSWORD, password);
        editor.putString(Constants.SP_USER_SEX, msex);
        editor.putString(Constants.SP_USER_PHONENUMBER, mobile);
        editor.putString(Constants.SP_USER_EMAIL, email);
        editor.commit();
    }


    /**
     * 用户注册，失败则抛异常
     *
     * @param user
     * @throws RuntimeException
     */
    public void userRegister(User user) throws RuntimeException {
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    //注册成功
                    onSignupSuccess();

                } else {

                    Log.d(TAG, "注册失败" + e.getMessage() + "," + e.getErrorCode());
                    if (e.getErrorCode() == 202){
                        Toast.makeText(SignupActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                        _signupButton.setEnabled(true);
                    }

                    if (e.getErrorCode() == 203)
                    {
                        Toast.makeText(SignupActivity.this, "邮箱已经存在", Toast.LENGTH_SHORT).show();
                        _signupButton.setEnabled(true);
                    }
                    if (e.getErrorCode() == 209)
                    {
                        Toast.makeText(SignupActivity.this, "该手机号码已经存在", Toast.LENGTH_SHORT).show();
                        _signupButton.setEnabled(true);
                    }



//                    Toast.makeText(SignupActivity.this, "请检测信息是否填写正确！", Toast.LENGTH_SHORT).show();
                    /*if (e.getErrorCode() == 202)
                        throw new RuntimeException("用户名已存在");

                    if (e.getErrorCode() == 301)
                        throw new RuntimeException("请输入有效手机号码！");

                    throw new RuntimeException("注册失败，请稍后再试！");*/
                }
            }
        });
    }

    /**
     * 建立积分表
     *
     * @param userId
     */
    /*private void userRegisterForPoints(String userId) throws RuntimeException {
        Points points = new Points();
        points.setUserId(userId);
        points.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    //用户注册成功
                    onSignupSuccess();
                } else {
                    throw new RuntimeException("注册失败，请稍后再试！");
                }
            }
        });

    }*/
}