package com.apress.gerber.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * Created by Colonel on 2017/9/24.
 */

public class Login_and_sign extends Activity {
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.link_ZC)
    Button linkZC;
    @BindView(R.id.link_DL)
    Button linkDL;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_and_signup);
        Bmob.initialize(this,getString(R.string.bomb_application_id));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        BmobUser bmobUser = BmobUser.getCurrentUser();
        if (bmobUser == null) {
        } else {
            startActivity(new Intent(Login_and_sign.this, MainActivity.class));
            finish();
        }
    }

    @OnClick({R.id.link_ZC, R.id.link_DL})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.link_ZC:
                checkZC();
                break;
            case R.id.link_DL:
                checkDL();
                break;
        }
    }
    public void checkZC() {
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }
    public void checkDL() {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }
}
