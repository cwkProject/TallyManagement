package com.port.tally.management.activity;
/**
 * Created by 超悟空 on 2015/9/7.
 */

import android.content.Intent;

import com.port.tally.management.R;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.activity.BaseLoginActivity;


/**
 * 登录Activity
 *
 * @author 超悟空
 * @version 1.0 2015/9/7
 * @since 1.0
 */
public class LoginActivity extends BaseLoginActivity {
    @Override
    protected int onActivityLoginLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected int onUserNameEditTextID() {
        return R.id.activity_login_username_edit;
    }

    @Override
    protected int onPasswordEditTextID() {
        return R.id.activity_login_password_edit;
    }

    @Override
    protected String onAppName() {
        return StaticValue.APP_CODE;
    }

    @Override
    protected void onLoginCheckID() {
        setLoginSaveCheckID(R.id.loginSave);
        setLoginAutoCheckID(R.id.loginAuto);
    }

    @Override
    protected void onPostClickLoginButton() {
        // 跳转到主Activity
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
