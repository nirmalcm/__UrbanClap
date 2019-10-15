package com.webcreo.nirmal.mukthi.temp.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.fragment.FragmentMainHome;
import com.webcreo.nirmal.mukthi.temp.fragment.FragmentMainHelp;
import com.webcreo.nirmal.mukthi.temp.fragment.FragmentMainBookings;
import com.webcreo.nirmal.mukthi.temp.fragment.FragmentMainProfile;
import com.webcreo.nirmal.mukthi.temp.service.ServiceLocationTrack;
import com.webcreo.nirmal.mukthi.temp.utils.ManagerPermission;
import com.webcreo.nirmal.mukthi.temp.utils.ManagerSession;
import com.webcreo.nirmal.mukthi.temp.utils.Tools;
import com.webcreo.nirmal.mukthi.temp.helper.HelperAnimation;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.webcreo.nirmal.mukthi.temp.utils.ManagerPermission.PERMISSION_LOCATION;

public class ActivityMain extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.splashScreenTheme);
        setContentView(R.layout.activity_main);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        //Crashlytics.getInstance().crash(); // Force a crash
        initXmlIds();
        initSession();
    }

    private void initXmlIds() {

        mToolbar = (FrameLayout) findViewById(R.id.id_toolbar);

        mCurrentLocation = (TextView) findViewById(R.id.id_current_location);
        mToolbarHome = (FrameLayout) findViewById(R.id.id_toolbar_home);
        mToolbarMyBooking = (FrameLayout) findViewById(R.id.id_toolbar_mybookings);
        mToolbarHelpCenter = (FrameLayout) findViewById(R.id.id_toolbar_helpcenter);
        mToolbarProfile = (FrameLayout) findViewById(R.id.id_toolbar_profile);

        mBottomNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        mSearchButton = (ImageButton)findViewById(R.id.id_search_button);

        selectedFragments = new Stack<Fragment>();
        fragmentIdMap = new HashMap<Fragment, Integer>();
    }

    private TextView mCurrentLocation;
    private ManagerSession mManagerSession;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private void initSession() {
        mManagerSession = new ManagerSession(this);
        //mManagerSession.checkLogin();

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    //startActivity(new Intent(ActivityMain.this, ActivityLoginPhone.class));
                    //finish();

                    initToolbar();
                    initBottomNavigation();
                    initPermissions();
                    initComponent();
                }
                else {
                    //Crashlytics.setUserIdentifier(user.getPhoneNumber());
                    initToolbar();
                    initBottomNavigation();
                    initPermissions();
                    initComponent();
                }
            }
        };

        //mCurrentLocation.setText(mManagerSession.getLocation());
        //HelperAnimation.startFadeInOut(mCurrentLocation);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    public void logout(){
        auth.signOut();
        finish();
        startActivity(new Intent(ActivityMain.this, ActivityLoginPhone.class));
    }
    private ImageButton mSearchButton;
    private ImageButton mCart;
    private void initToolbar() {
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMain.this, ActivitySearch.class));
            }
        });

        mCart = (ImageButton)findViewById(R.id.cart);
        mCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMain.this, ActivityCart.class));
            }
        });
    }


    HashMap<Fragment,Integer> fragmentIdMap;
    Stack<Fragment> selectedFragments;
    private BottomNavigationView mBottomNavigation;
    private void initBottomNavigation() {
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_categories:
                        Fragment mainHomeFragment = new FragmentMainHome();
                        loadFragment(mainHomeFragment);
                        setToolbarHome();
                        selectedFragments.push(mainHomeFragment);
                        fragmentIdMap.put(mainHomeFragment,item.getItemId());
                        return true;
                    case R.id.navigation_whatsapp:
                        Fragment mainMyBookingsFragment = new FragmentMainBookings();
                        loadFragment(mainMyBookingsFragment);
                        setToolbarMyBooking();
                        selectedFragments.push(mainMyBookingsFragment);
                        fragmentIdMap.put(mainMyBookingsFragment,item.getItemId());
                        return true;
                    case R.id.navigation_deals:
                        Fragment mainHelpCenterFragment = new FragmentMainHelp();
                        loadFragment(mainHelpCenterFragment);
                        setToolbarHelpCenter();
                        selectedFragments.push(mainHelpCenterFragment);
                        fragmentIdMap.put(mainHelpCenterFragment,item.getItemId());
                        return true;
                    case R.id.navigation_profile:
                        Fragment mainProfileFragment = new FragmentMainProfile();
                        loadFragment(mainProfileFragment);
                        setToolbarProfile();
                        selectedFragments.push(mainProfileFragment);
                        fragmentIdMap.put(mainProfileFragment,item.getItemId());
                        return true;
                }
                return false;
            }
        });
        selectHomeFragment();
        Tools.removeShiftMode(mBottomNavigation);
    }

    private FrameLayout mToolbarProfile;
    private void setToolbarProfile() {
        insertToolbar();
        mToolbarProfile.setVisibility(View.VISIBLE);
        mToolbarHome.setVisibility(View.INVISIBLE);
        mToolbarMyBooking.setVisibility(View.INVISIBLE);
        mToolbarHelpCenter.setVisibility(View.INVISIBLE);
    }

    private FrameLayout mToolbarHelpCenter;
    private void setToolbarHelpCenter() {
        insertToolbar();
        mToolbarHelpCenter.setVisibility(View.VISIBLE);
        mToolbarProfile.setVisibility(View.INVISIBLE);
        mToolbarHome.setVisibility(View.INVISIBLE);
        mToolbarMyBooking.setVisibility(View.INVISIBLE);
    }

    private FrameLayout mToolbar;
    public void removeToolbar(){
        mToolbar.setVisibility(View.GONE);
    }
    public void insertToolbar(){
        mToolbar.setVisibility(View.VISIBLE);
    }

    private FrameLayout mToolbarMyBooking;
    private void setToolbarMyBooking() {
        insertToolbar();
        mToolbarMyBooking.setVisibility(View.VISIBLE);
        mToolbarProfile.setVisibility(View.INVISIBLE);
        mToolbarHome.setVisibility(View.INVISIBLE);
        mToolbarHelpCenter.setVisibility(View.INVISIBLE);
    }

    private FrameLayout mToolbarHome;
    private void setToolbarHome() {
        insertToolbar();
        mToolbarHome.setVisibility(View.VISIBLE);
        mToolbarProfile.setVisibility(View.INVISIBLE);
        mToolbarMyBooking.setVisibility(View.INVISIBLE);
        mToolbarHelpCenter.setVisibility(View.INVISIBLE);
    }

    private void initComponent() {

    }
    public void showSearchButton() {
        mSearchButton.setVisibility(View.VISIBLE);
    }

    public void hideSearchButton() {
        mSearchButton.setVisibility(View.INVISIBLE);
    }

    public void selectHomeFragment() {
        mBottomNavigation.setSelectedItemId(R.id.navigation_categories);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed()
    {
        if(selectedFragments.size()>=2)
        {
            Fragment currentFragment = selectedFragments.pop();
            Fragment requiredFragment = selectedFragments.pop();
            if (currentFragment!=null) {
                int selectedItemId = fragmentIdMap.get(requiredFragment);
                BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
                bottomNavigationView.setSelectedItemId(selectedItemId);
            }
        }
        else
        {
            if (exit)
            {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                startActivity(intent);
                finish();
                System.exit(0);
            }
            else
            {
                Toast.makeText(this, "Press Back again to Exit", Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);
            }
        }
    }

    ManagerPermission managerPermission;
    public void initPermissions() {
        managerPermission = new ManagerPermission(this);
        managerPermission.checkPermission(PERMISSION_LOCATION, ACCESS_FINE_LOCATION);
        /*if(managerPermission.hasPermissions(ACCESS_FINE_LOCATION))
            initLocationTrack();
        else
            managerPermission.checkPermission(PERMISSION_LOCATION, ACCESS_FINE_LOCATION);*/
    }

    private ServiceLocationTrack serviceLocationTrack;
    public void initLocationTrack() {
        serviceLocationTrack = new ServiceLocationTrack(ActivityMain.this);
        if (serviceLocationTrack.canGetLocation()) {
            getCompleteAddressString(serviceLocationTrack.getLatitude(), serviceLocationTrack.getLongitude());
        } else {
            serviceLocationTrack.showSettingsAlert();
        }
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                mManagerSession.setLocation(strAdd);
                mCurrentLocation.setText(strAdd);
                HelperAnimation.endFadeInOut(mCurrentLocation);
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
            mManagerSession.setHasLocation(false);
        }
        return strAdd;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initLocationTrack();
                } else {
                    //code for deny
                    managerPermission.checkPermission(PERMISSION_LOCATION, ACCESS_FINE_LOCATION);
                }
                break;
        }
    }
}