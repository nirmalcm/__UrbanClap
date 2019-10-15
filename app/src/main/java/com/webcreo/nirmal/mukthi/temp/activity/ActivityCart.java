package com.webcreo.nirmal.mukthi.temp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.adapter.AdapterCart;
import com.webcreo.nirmal.mukthi.temp.model.ModelBase;
import com.webcreo.nirmal.mukthi.temp.utils.Tools;

import java.util.List;

public class ActivityCart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initComponent() {
        initFontStyle();
        initCartItems();
        initProceed();
    }

    private void initFontStyle() {
        Tools.applyFontStyle(this, findViewById(android.R.id.content), Tools.FontTypeFace.font_Quattrocento, Tools.FontStyle.normal);
        Tools.applyFontStyle(this, findViewById(R.id.service_cost), Tools.FontTypeFace.font_Quattrocento, Tools.FontStyle.bold);
    }

    private RecyclerView mCartItemRecycler;
    private AdapterCart mCartListAdapter;
    List<ModelBase> mCartItems;
    private void initCartItems() {

        mCartItemRecycler = (RecyclerView) findViewById(R.id.cart_items);

        mCartItems = new ModelBase().getContents(this, "3_real_estate.json","real_estate",true);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mCartItemRecycler.setLayoutManager(linearLayoutManager);

        mCartListAdapter = new AdapterCart(this, mCartItems);
        mCartListAdapter.notifyDataSetChanged();

        mCartItemRecycler.setAdapter(mCartListAdapter);
        mCartItemRecycler.setItemAnimator(new DefaultItemAnimator());
        mCartItemRecycler.setNestedScrollingEnabled(false);
    }

    private boolean proceed = true;
    private FrameLayout nextButton;
    private void initProceed(){
        nextButton = (FrameLayout) findViewById(R.id.proceed);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (proceed){
                    startActivity(new Intent(ActivityCart.this,ActivityAddress.class));
                }
            }
        });
    }
}