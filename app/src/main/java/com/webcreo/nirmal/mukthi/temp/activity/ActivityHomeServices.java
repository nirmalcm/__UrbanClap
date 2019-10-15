package com.webcreo.nirmal.mukthi.temp.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.adapter.AdapterHSCategories;
import com.webcreo.nirmal.mukthi.temp.model.ModelBase;
import com.webcreo.nirmal.mukthi.temp.helper.HelperAnimation;
import com.webcreo.nirmal.mukthi.temp.utils.Tools;

import java.util.List;

public class ActivityHomeServices extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_services);
        recieveIntent();
        initCollapseToolbar();
        //initProgressBar();
        //initToolbar();
        initComponent();
    }

    private ModelBase selectedModelBase;
    private void recieveIntent() {
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            selectedModelBase = (ModelBase) getIntent().getSerializableExtra("data");
        }
    }

    private View mProgressBar;
    private void initProgressBar() {
        mProgressBar = findViewById(R.id.id_progress_bar);

        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setAlpha(1.0f);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HelperAnimation.fadeOut(mProgressBar);
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        }, 1000 + 400);
    }

    private void initCollapseToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ImageView headerBackground = (ImageView) findViewById(R.id.background);

        getSupportActionBar().setTitle(selectedModelBase.getName());
        if (selectedModelBase.isHasBackground()){
            //Tools.loadImageDrawable(this, headerBackground,selectedModelBase.getBackgroundDrawable());
            Tools.loadFirebaseImage(this,selectedModelBase.getBackground(),headerBackground);
        }
        else {
        }
    }

    private ImageButton mBack;
    private ImageButton mBackTwo;
    private TextView mTitle;
    private void initToolbar() {
        mToolbar = (FrameLayout) findViewById(R.id.id_toolbar);
        mTitle = (TextView) findViewById(R.id.id_title);

        mTitle.setText(selectedModelBase.getName());
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mBackTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private FrameLayout mToolbar;
    public void showToolbar(){
        mToolbar.setVisibility(View.VISIBLE);
    }
    public void hideToolbar(){
        mToolbar.setVisibility(View.INVISIBLE);
    }

    private void initComponent() {
        //initNestedScroll();
        //initSectionHeader();
        initCategories();
        initSectionWhyChooseUs();
        initSectionHappinessGuarantee();

        Tools.applyFontStyle(this,findViewById(android.R.id.content), Tools.FontTypeFace.font_Quattrocento, Tools.FontStyle.normal);
    }

    private void initSectionHappinessGuarantee() {
    }

    private void initSectionWhyChooseUs() {
    }

    private TextView mHeaderName;
    private void initSectionHeader() {
        mHeaderName = (TextView) findViewById(R.id.id_name);
        mHeaderName.setText(selectedModelBase.getName());
    }

    private NestedScrollView mNestedScrollView;
    private void initNestedScroll() {
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nested_scroll_view);
        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Rect scrollBounds = new Rect();
                mNestedScrollView.getHitRect(scrollBounds);

                if (mHeaderName.getLocalVisibleRect(scrollBounds)) {
                    hideToolbar();
                    // Any portion of the searchView, even a single pixel, is within the visible window
                } else {
                    showToolbar();
                    // NONE of the searchView is within the visible window
                }
            }
        });
    }

    private RecyclerView mCategoriesRecycler;
    private AdapterHSCategories mCategoriesAdapter;
    private List<ModelBase> mCategories;
    private void initCategories()
    {
        mCategoriesRecycler = (RecyclerView) findViewById(R.id.categories);

        mCategories = selectedModelBase.getSubCategories();

        //final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mCategoriesRecycler.setLayoutManager(linearLayoutManager);

        mCategoriesAdapter = new AdapterHSCategories(this, mCategories);
        mCategoriesAdapter.notifyDataSetChanged();

        mCategoriesRecycler.setAdapter(mCategoriesAdapter);
        mCategoriesRecycler.setItemAnimator(new DefaultItemAnimator());
        mCategoriesRecycler.setNestedScrollingEnabled(false);
    }
}