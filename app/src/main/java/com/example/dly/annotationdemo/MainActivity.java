package com.example.dly.annotationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dly.annotation_api.ButterKnife;
import com.example.dly.app_annotation.BindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.annotation_tv)
    public TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tv1.setText("annotation_demo");
    }
}
