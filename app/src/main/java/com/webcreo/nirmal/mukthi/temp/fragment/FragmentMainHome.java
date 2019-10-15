package com.webcreo.nirmal.mukthi.temp.fragment;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.activity.ActivityMain;
import com.webcreo.nirmal.mukthi.temp.activity.ActivityRealEstate;
import com.webcreo.nirmal.mukthi.temp.activity.ActivitySearch;
import com.webcreo.nirmal.mukthi.temp.adapter.AdapterMainSlider;
import com.webcreo.nirmal.mukthi.temp.adapter.AdapterMainBanner;
import com.webcreo.nirmal.mukthi.temp.adapter.AdapterMainGrid;
import com.webcreo.nirmal.mukthi.temp.adapter.AdapterMainList;
import com.webcreo.nirmal.mukthi.temp.helper.CircularViewPagerHandler;
import com.webcreo.nirmal.mukthi.temp.model.ModelBase;
import com.webcreo.nirmal.mukthi.temp.utils.Tools;

import java.util.List;

public class FragmentMainHome extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);
        initXmlIds(view);
        initComponent(view);
        //initShimmer();
        return view;
    }

    private void initXmlIds(View view) {
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        mNestedScrollView = (NestedScrollView) view.findViewById(R.id.nested_scroll_view);
        mSearchframe = (FrameLayout) view.findViewById(R.id.id_search_frame);
        mSearchView = (CardView) view.findViewById(R.id.id_search_bar);
        mSearchText = (TextView) view.findViewById(R.id.id_search_text);
        mBannerRecycler = (RecyclerView) view.findViewById(R.id.id_banners);
        mGridRecycler = (RecyclerView) view.findViewById(R.id.id_service_grids);
        mListRecycler = (RecyclerView) view.findViewById(R.id.id_service_list);
        mSectionInsured = (CardView) view.findViewById(R.id.section_insured);
        mInsuredLogo = (ImageView) view.findViewById(R.id.insured_logo);
    }

    private void initComponent(View view) {
        //((ActivityMain) getActivity()).initPermissions();
        ((ActivityMain) getActivity()).initLocationTrack();

        ImageView homeHeader = (ImageView) view.findViewById(R.id.header_image);
        Tools.loadImageDrawable(getContext(),homeHeader,R.drawable.home_header);
        initNestedScroll();
        initSearchControl();
        initBanners();
        initAutoSlider(view);
        initServiceGrid();
        initServiceList();
        initInsuredCard();

        ImageView realEstate = (ImageView) view.findViewById(R.id.real_estate);
        Tools.loadFirebaseImage(getContext(),"real_estate_1.png",realEstate);
        realEstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), ActivityRealEstate.class));
            }
        });

        Tools.applyFontStyle(getContext(), view, Tools.FontTypeFace.font_Quattrocento, Tools.FontStyle.normal);
        Tools.applyFontStyle(getContext(), view.findViewById(R.id.offers), Tools.FontTypeFace.font_Quattrocento, Tools.FontStyle.bold);
        Tools.applyFontStyle(getContext(), view.findViewById(R.id.spot_lights), Tools.FontTypeFace.font_Quattrocento, Tools.FontStyle.bold);
        Tools.applyFontStyle(getContext(), view.findViewById(R.id.subscriptions), Tools.FontTypeFace.font_Quattrocento, Tools.FontStyle.bold);
    }

    private ShimmerFrameLayout mShimmerViewContainer;
    private void initShimmer() {
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmerAnimation();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                mShimmerViewContainer.setVisibility(View.VISIBLE);
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                //initComponent();
            }
        }, 1000);
    }

    private FrameLayout mSearchframe;
    private CardView mSearchView;
    private TextView mSearchText;
    private void initSearchControl() {
        ((ActivityMain) getActivity()).hideSearchButton();
        mSearchframe.setVisibility(View.VISIBLE);
        mSearchText.setInputType(InputType.TYPE_NULL);
        mSearchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivitySearch.class));
            }
        });
    }

    private CardView mSectionInsured;
    private ImageView mInsuredLogo;
    private void initInsuredCard() {
        mSectionInsured.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StateListAnimator stateListAnimator = AnimatorInflater.loadStateListAnimator(getContext(),
                    R.drawable.lift_outward
            );
            mSectionInsured.setStateListAnimator(stateListAnimator);
        }
        Tools.loadImageDrawable(getContext(), mInsuredLogo,R.drawable.ic__insured);
    }

    private NestedScrollView mNestedScrollView;
    private void initNestedScroll() {
        mNestedScrollView.setVisibility(View.VISIBLE);
        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Rect scrollBounds = new Rect();
                mNestedScrollView.getHitRect(scrollBounds);

                if (mSearchView.getLocalVisibleRect(scrollBounds)) {
                    ((ActivityMain) getActivity()).hideSearchButton();
                    // Any portion of the searchView, even a single pixel, is within the visible window
                } else {
                    ((ActivityMain) getActivity()).showSearchButton();
                    // NONE of the searchView is within the visible window
                }
            }
        });
    }

    private RecyclerView mBannerRecycler;
    private AdapterMainBanner mBannerAdapter;
    private List<ModelBase> mBanners;
    private void initBanners() {
        mBanners = new ModelBase().getContents(getContext(),"contents.json","banners",false);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mBannerRecycler.setLayoutManager(linearLayoutManager);

        mBannerAdapter = new AdapterMainBanner(getContext(), mBanners);
        mBannerAdapter.notifyDataSetChanged();

        mBannerRecycler.setAdapter(mBannerAdapter);
        mBannerRecycler.setItemAnimator(new DefaultItemAnimator());
        mBannerRecycler.setNestedScrollingEnabled(false);

        mBannerRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int Xdx = 0;
            boolean proceed = true;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                System.out.println("Proceed = "+ proceed);
                if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    proceed = true;
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int position = linearLayoutManager.findFirstVisibleItemPosition();
                    if(!recyclerView.canScrollHorizontally(1)){
                        position = linearLayoutManager.findLastVisibleItemPosition();
                    }
                    recyclerView.smoothScrollToPosition(position);
                    proceed = false;
                }
                else {
                    proceed = false;
                }
                System.out.println("state -- "+newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int offset = dx - Xdx;
                Xdx = dx;

                System.out.println("dx -- "+ dx);
                System.out.println("offset -- "+ offset);

                System.out.println("inside On Scrolled -- " + recyclerView.getScrollState());
                if(recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_SETTLING) {

                    System.out.println("Proceeded");
                    int position = linearLayoutManager.findFirstVisibleItemPosition();
                    System.out.println("Position = " + position);

                    View child = recyclerView.getChildAt(position);
                    Rect scrollBounds = new Rect();
                    mBannerRecycler.getHitRect(scrollBounds);
                    if (child!=null) {
                        scrollBounds.left = scrollBounds.right- child.getWidth()/2;
                        if (child.getLocalVisibleRect(scrollBounds)) {
                            // Any portion of the child, even a single pixel, is within the visible window
                        } else {
                            // NONE of the child is within the visible window
                            //recyclerView.smoothScrollToPosition(position+1);
                        }

                        if (isViewPassedHalf(child)){
                            //recyclerView.smoothScrollToPosition(position+1);
                            System.out.println("Reached half");
                        }
                    }

                    if(!recyclerView.canScrollHorizontally(1)){
                        position = linearLayoutManager.findLastVisibleItemPosition();
                    }
                    recyclerView.smoothScrollToPosition(position);

                    proceed = true;
                }
            }
        });
    }

    private boolean isViewPassedHalf(View view) {
        Rect scrollBounds = new Rect();
        mNestedScrollView.getDrawingRect(scrollBounds);

        float top = view.getY();
        float bottom = top + view.getHeight();

        float left  = view.getX();
        float right = left + view.getWidth();

        System.out.println("Scrollbounds left -- "+scrollBounds.left);
        System.out.println("View right -- "+right);
        if (right < scrollBounds.left+(view.getWidth()/2+view.getWidth()/4)) {
            return true;
        } else {
            return false;
        }
    }

    private RecyclerView mGridRecycler;
    private AdapterMainGrid mGridAdapter;
    List<ModelBase> mGrids;
    private void initServiceGrid() {

        mGrids = new ModelBase().getContents(getContext(), "1_home_services.json","home_services",true);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        mGridRecycler.setLayoutManager(gridLayoutManager);

        mGridAdapter = new AdapterMainGrid(getContext(), mGrids);
        mGridAdapter.notifyDataSetChanged();

        mGridRecycler.setAdapter(mGridAdapter);
        mGridRecycler.setItemAnimator(new DefaultItemAnimator());
        mGridRecycler.setNestedScrollingEnabled(false);
    }

    private RecyclerView mListRecycler;
    private AdapterMainList mListAdapter;
    List<ModelBase> mList;
    private void initServiceList() {

        mList = new ModelBase().getContents(getContext(), "3_real_estate.json","real_estate",true);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
             mListRecycler.setLayoutManager(linearLayoutManager);

        mListAdapter = new AdapterMainList(getContext(), mList);
        mListAdapter.notifyDataSetChanged();

        mListRecycler.setAdapter(mListAdapter);
        mListRecycler.setItemAnimator(new DefaultItemAnimator());
        mListRecycler.setNestedScrollingEnabled(false);
    }

    private AdapterMainSlider myViewPagerAdapter;
    private ViewPager mViewPager;
    private void initAutoSlider(final View view) {

        mViewPager = (ViewPager) view.findViewById(R.id.view_pager_banner);

        myViewPagerAdapter = new AdapterMainSlider(getContext(), new ModelBase().getContents(getContext(),"contents.json","auto_sliders",false));
        mViewPager.setAdapter(myViewPagerAdapter);
        /*mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(final int position) {
                bottomProgressDots(position,view);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });*/

        mViewPager.setOnPageChangeListener(new CircularViewPagerHandler(mViewPager));
        mViewPager.setPageMargin(-100);
        mViewPager.setOffscreenPageLimit(new ModelBase().getContents(getContext(),"contents.json","auto_sliders",false).size());

        // adding bottom dots
        //bottomProgressDots(0,view);
        startAutoSlider(new ModelBase().getContents(getContext(),"contents.json","auto_sliders",false).size());
    }

    private void bottomProgressDots(int current_index, View view) {
        LinearLayout dotsLayout = (LinearLayout) view.findViewById(R.id.layoutDots);
        ImageView[] dots = new ImageView[new ModelBase().getContents(getContext(),"contents.json","auto_sliders",false).size()];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(getContext());
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_IN);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current_index].setImageResource(R.drawable.shape_circle);
            dots[current_index].setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        }
    }

    private Runnable mRunnable = null;
    private Handler mHandler = new Handler();

    private void startAutoSlider(final int count) {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                int pos = mViewPager.getCurrentItem();
                pos = pos + 1;
                if (pos >= count) pos = 0;
                mViewPager.setCurrentItem(pos);
                mHandler.postDelayed(mRunnable, 3000);
            }
        };
        mHandler.postDelayed(mRunnable, 3000);
    }
}