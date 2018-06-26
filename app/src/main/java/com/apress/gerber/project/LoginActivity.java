package com.apress.gerber.project;

/**
 * Created by Colonel on 2017/9/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.apress.gerber.project.Constants;


public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.custom_signin_button)
    Button _loginButton;
    @BindView(R.id.link_signup)
    TextView _signupLink;

    private User mCurrentUser;
    private String muser_name;
    private String muser_sex;
    private String muser_num;
    private String mw_password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        Bmob.initialize(this,getString(R.string.bomb_application_id));
        //判断本地是否存在用户缓存
        BmobUser bmobUser = BmobUser.getCurrentUser();
        if (bmobUser == null) {
        } else {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");
        if (!validate()) {
            onLoginFailed();
            return;
        }
        _loginButton.setEnabled(false);
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        mw_password = _passwordText.getText().toString();
        mCurrentUser.loginByAccount(email, password, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (user != null) {
                    onLoginSuccess();
                } else {
                    onLoginFailed();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        queryuserinfo();
        Toast.makeText(LoginActivity.this,R.string.login_success, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError(getString(R.string.login_not_match_email));
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError(getString(R.string.login_not_match_password));
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    private void queryuserinfo() {

        BmobQuery<User> query = new BmobQuery<User>();
        if (_emailText.getText().toString().contains("@")) {
            query.addWhereEqualTo("email", _emailText.getText().toString());
        }
//        } else {
//            query.addWhereEqualTo("username", _emailText.getText().toString());
//        }

        query.setLimit(1);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    muser_name = object.get(0).getUsername();
                    muser_sex = object.get(0).getSex();
                    muser_num = object.get(0).getMobilePhoneNumber();
                    updatalocalinfo();
                }
            }
        });
    }

    private void updatalocalinfo() {
        SharedPreferences prf = getSharedPreferences(Constants.SP_USER_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = prf.edit();
        editor.putString(Constants.SP_USER_NAME, muser_name);
        editor.putString(Constants.SP_USER_PASSWORD, mw_password);
        editor.putString(Constants.SP_USER_SEX, muser_sex);
        editor.putString(Constants.SP_USER_PHONENUMBER, muser_num);
        editor.apply();
//        Log.d(TAG, muser_name + ":" + muser_sex + ":" + muser_num + ":" + muser_location + ":" + mw_password);
    }

}
