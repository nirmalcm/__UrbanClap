package com.webcreo.nirmal.mukthi.temp.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.adapter.AdapterMore;
import com.webcreo.nirmal.mukthi.temp.model.ModelBase;
import com.webcreo.nirmal.mukthi.temp.utils.Tools;

import java.util.List;

public class ActivityMore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        recieveIntent();
        initToolbar();
        initComponent();
    }

    private void recieveIntent() {
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Services");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initComponent() {
        initHomeServices();
        initConstructionServices();

        Tools.applyFontStyle(this, findViewById(android.R.id.content), Tools.FontTypeFace.font_Quattrocento, Tools.FontStyle.normal);
        Tools.applyFontStyle(this, findViewById(R.id.construction_services), Tools.FontTypeFace.font_Quattrocento, Tools.FontStyle.bold);
        Tools.applyFontStyle(this, findViewById(R.id.home_services), Tools.FontTypeFace.font_Quattrocento, Tools.FontStyle.bold);
    }

    private NestedScrollView mNestedScrollView;
    private void initNestedScroll() {
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nested_scroll_view);
        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Rect scrollBounds = new Rect();
                mNestedScrollView.getHitRect(scrollBounds);

                /*if (mHeaderName.getLocalVisibleRect(scrollBounds)) {
                    hideToolbar();
                    // Any portion of the searchView, even a single pixel, is within the visible window
                } else {
                    showToolbar();
                    // NONE of the searchView is within the visible window
                }*/
            }
        });
    }

    private RecyclerView mHomeServicesRecycler;
    private AdapterMore mHomeServicesAdapter;
    private List<ModelBase> mHomeServices;
    private void initHomeServices()
    {
        mHomeServicesRecycler = (RecyclerView) findViewById(R.id.homeservices);

        mHomeServices = new ModelBase().getContents(this, "1_home_services.json","home_services",false);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        mHomeServicesRecycler.setLayoutManager(gridLayoutManager);

        mHomeServicesAdapter = new AdapterMore(this, mHomeServices);
        mHomeServicesAdapter.notifyDataSetChanged();

        mHomeServicesRecycler.setAdapter(mHomeServicesAdapter);
        mHomeServicesRecycler.setItemAnimator(new DefaultItemAnimator());
        mHomeServicesRecycler.setNestedScrollingEnabled(false);
    }

    private RecyclerView mConstructionServicesRecycler;
    private AdapterMore mConstructionServicesAdapter;
    private List<ModelBase> mConstructionServices;
    private void initConstructionServices()
    {
        mConstructionServicesRecycler = (RecyclerView) findViewById(R.id.constructionservices);

        mConstructionServices = new ModelBase().getContents(this, "2_construction_services.json","construction_services",false);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        mConstructionServicesRecycler.setLayoutManager(gridLayoutManager);

        mConstructionServicesAdapter = new AdapterMore(this, mConstructionServices);
        mConstructionServicesAdapter.notifyDataSetChanged();

        mConstructionServicesRecycler.setAdapter(mConstructionServicesAdapter);
        mConstructionServicesRecycler.setItemAnimator(new DefaultItemAnimator());
        mConstructionServicesRecycler.setNestedScrollingEnabled(false);
    }
}