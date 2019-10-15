package com.webcreo.nirmal.mukthi.temp.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.adapter.AdapterRECollections;
import com.webcreo.nirmal.mukthi.temp.adapter.AdapterRELocation;
import com.webcreo.nirmal.mukthi.temp.adapter.AdapterRESponsoredProjects;
import com.webcreo.nirmal.mukthi.temp.model.ModelBase;
import com.webcreo.nirmal.mukthi.temp.utils.Tools;

import java.util.List;

public class ActivityRealEstate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_estate);
        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Real Estate Services");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initComponent() {
        ImageView realEstate = (ImageView) findViewById(R.id.real_estate);
        Tools.loadFirebaseImage(this,"real_estate_1.png",realEstate);
        realEstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(ActivityRealEstate.this, ActivityRealEstate.class));
            }
        });

        initBestLocalities();
        initSponsoredProjects();
        initCollections();
    }

    private NestedScrollView mNestedScrollView;
    private void initNestedScroll() {
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nested_scroll_view);
        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Rect scrollBounds = new Rect();
                mNestedScrollView.getHitRect(scrollBounds);
            }
        });
    }

    private RecyclerView mBestLocalitiesRecycler;
    private AdapterRELocation mBestLocalitiesAdapter;
    private List<ModelBase> mBestLocalities;
    private void initBestLocalities()
    {
        mBestLocalitiesRecycler = (RecyclerView) findViewById(R.id.best_localities);

        mBestLocalities = new ModelBase().getContents(this,"3_real_estate.json","real_estate",false);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mBestLocalitiesRecycler.setLayoutManager(linearLayoutManager);

        mBestLocalitiesAdapter = new AdapterRELocation(this, mBestLocalities);
        mBestLocalitiesAdapter.notifyDataSetChanged();

        mBestLocalitiesRecycler.setAdapter(mBestLocalitiesAdapter);
        mBestLocalitiesRecycler.setItemAnimator(new DefaultItemAnimator());
        mBestLocalitiesRecycler.setNestedScrollingEnabled(false);
    }

    private RecyclerView mSponsoredProjectsRecycler;
    private AdapterRESponsoredProjects mSponsoredProjectsAdapter;
    private List<ModelBase> mSponsoredProjects;
    private void initSponsoredProjects()
    {
        mSponsoredProjectsRecycler = (RecyclerView) findViewById(R.id.sponsored_projects);

        mSponsoredProjects = new ModelBase().getContents(this,"3_real_estate.json","real_estate",false);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mSponsoredProjectsRecycler.setLayoutManager(linearLayoutManager);

        mSponsoredProjectsAdapter = new AdapterRESponsoredProjects(this, mSponsoredProjects);
        mSponsoredProjectsAdapter.notifyDataSetChanged();

        mSponsoredProjectsRecycler.setAdapter(mSponsoredProjectsAdapter);
        mSponsoredProjectsRecycler.setItemAnimator(new DefaultItemAnimator());
        mSponsoredProjectsRecycler.setNestedScrollingEnabled(false);
    }

    private RecyclerView mCollectionsRecycler;
    private AdapterRECollections mCollectionsAdapter;
    private List<ModelBase> mCollections;
    private void initCollections()
    {
        mCollectionsRecycler = (RecyclerView) findViewById(R.id.collections);

        mCollections = new ModelBase().getContents(this,"3_real_estate.json","real_estate",false);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mCollectionsRecycler.setLayoutManager(gridLayoutManager);

        mCollectionsAdapter = new AdapterRECollections(this, mCollections);
        mCollectionsAdapter.notifyDataSetChanged();

        mCollectionsRecycler.setAdapter(mCollectionsAdapter);
        mCollectionsRecycler.setItemAnimator(new DefaultItemAnimator());
        mCollectionsRecycler.setNestedScrollingEnabled(false);

        mCollectionsRecycler.setHasFixedSize(true);
    }
}