package com.webcreo.nirmal.mukthi.temp.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.model.ModelBase;
import com.webcreo.nirmal.mukthi.temp.utils.ManagerSQLite;
import com.webcreo.nirmal.mukthi.temp.utils.Tools;

public class ActivityHomeService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_service);
        recieveIntent();
        initToolbar();
        initComponent();
    }

    private ModelBase selectedModelBase;
    private void recieveIntent() {
        selectedModelBase = (ModelBase) getIntent().getSerializableExtra("data");
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initComponent() {
        TextView serviceName = (TextView) findViewById(R.id.service_name);
        serviceName.setText(selectedModelBase.getName());
        ImageView serviceImage = (ImageView) findViewById(R.id.service_image);
        Tools.loadFirebaseImage(this, selectedModelBase.getImage(),serviceImage);

        initNestedScroll();
        initPremiumBrands();
        initCartFunctioning();

        Tools.applyFontStyle(this,findViewById(android.R.id.content), Tools.FontTypeFace.font_Quattrocento, Tools.FontStyle.normal);
        Tools.applyFontStyle(this, findViewById(R.id.service_name), Tools.FontTypeFace.font_Quattrocento, Tools.FontStyle.bold);
        Tools.applyFontStyle(this, findViewById(R.id.cost), Tools.FontTypeFace.font_Quattrocento, Tools.FontStyle.bold);
    }

    private NestedScrollView mNestedScrollView;
    private CardView mHeader;
    private void initNestedScroll() {
        mHeader = (CardView) findViewById(R.id.header);
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nested_scroll_view);
        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Rect scrollBounds = new Rect();
                mNestedScrollView.getHitRect(scrollBounds);

                if (mHeader.getLocalVisibleRect(scrollBounds)) {
                    // Any portion of the searchView, even a single pixel, is within the visible window
                    getSupportActionBar().setTitle("");
                } else {
                    // NONE of the searchView is within the visible window
                    getSupportActionBar().setTitle(selectedModelBase.getName());
                }
            }
        });
    }

    private ManagerSQLite managerSQLite;
    private CardView addToCart;
    private CardView removeFromCart;
    private void initCartFunctioning() {
        managerSQLite = new ManagerSQLite(this);
        addToCart = (CardView) findViewById(R.id.add_to_cart);
        removeFromCart = (CardView) findViewById(R.id.remove_from_cart);

        if (managerSQLite.isInCart(selectedModelBase.getId())){
            showRemoveFromCart();
        } else {
            showAddToCart();
        }

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!managerSQLite.isInCart(selectedModelBase.getId())){
                    managerSQLite.addToCart(selectedModelBase.getId());
                    showRemoveFromCart();
                }
            }
        });

        removeFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (managerSQLite.isInCart(selectedModelBase.getId())){
                    managerSQLite.removeFromCart(selectedModelBase.getId());
                    showAddToCart();
                }
            }
        });
    }

    private void showRemoveFromCart() {
        removeFromCart.setVisibility(View.VISIBLE);
        addToCart.setVisibility(View.INVISIBLE);
    }

    private void showAddToCart() {
        addToCart.setVisibility(View.VISIBLE);
        removeFromCart.setVisibility(View.INVISIBLE);
    }

    private CardView mSectionPremiumBrands;
    private ImageView mPremiumBrands;
    private ImageView mPremiumBrandsIcon1;
    private ImageView mPremiumBrandsIcon2;
    private ImageView mPremiumBrandsIcon3;
    private void initPremiumBrands() {

        mSectionPremiumBrands = (CardView) findViewById(R.id.section_premium_brands);
        mPremiumBrands = (ImageView) findViewById(R.id.premium_brands_logo);
        mPremiumBrandsIcon1 = (ImageView) findViewById(R.id.premium_brands_ic_1);
        mPremiumBrandsIcon2 = (ImageView) findViewById(R.id.premium_brands_ic_2);
        mPremiumBrandsIcon3 = (ImageView) findViewById(R.id.premium_brands_ic_3);

        mSectionPremiumBrands.setVisibility(View.VISIBLE);
        Tools.loadImageDrawable(this, mPremiumBrandsIcon1,R.drawable.ic_1);
        Tools.loadImageDrawable(this, mPremiumBrandsIcon2,R.drawable.ic_2);
        Tools.loadImageDrawable(this, mPremiumBrandsIcon3,R.drawable.ic_3);
        Tools.loadImageDrawable(this, mPremiumBrands,R.drawable.ic_premium_text);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}