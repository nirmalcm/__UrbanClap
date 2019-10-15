package com.webcreo.nirmal.mukthi.temp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.utils.Tools;

public class ActivityShedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shedule);
        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Shedule An Appoinment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initComponent() {
        initProceed();
        initFontStyle();
    }

    private void initFontStyle() {
        Tools.applyFontStyle(this, findViewById(android.R.id.content), Tools.FontTypeFace.font_Quattrocento, Tools.FontStyle.normal);
    }

    private boolean proceed = true;
    private FrameLayout nextButton;
    private void initProceed(){
        nextButton = (FrameLayout) findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (proceed){
                    startActivity(new Intent(ActivityShedule.this,ActivityOrderSummary.class));
                }
            }
        });
    }
}