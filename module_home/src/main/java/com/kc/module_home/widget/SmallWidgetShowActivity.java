package com.kc.module_home.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dovar.router_annotation.Route;
import com.kc.module_home.R;

import ckc.android.develophelp.lib.util.ToastUtils;
import ckc.android.develophelp.lib.widget.SquareVerifyCodeView;

@Route(path = "widget/small")
public class SmallWidgetShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_widget_show);
        SquareVerifyCodeView verifyCodeView = findViewById(R.id.square);
        verifyCodeView.setOnVerifyCodeListener(new SquareVerifyCodeView.OnVerifyCodeListener() {
            @Override
            public void onVerifyCodeInputComplete(final String code) {
                ToastUtils.showToast(SmallWidgetShowActivity.this, code);
            }
        });
    }
}
