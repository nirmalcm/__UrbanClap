package com.webcreo.nirmal.mukthi.temp.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.webcreo.nirmal.mukthi.R;
import com.webcreo.nirmal.mukthi.temp.helper.GlideApp;
import com.webcreo.nirmal.mukthi.temp.model.ModelCalendar;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

    public enum FontTypeFace {
        font_circular,
        font_maven,
        font_Quattrocento
    }

    public enum FontStyle {
        bold,
        italic,
        normal
    }

    public static ArrayList<ModelCalendar> getNext30Days() {
        try{
            ArrayList<ModelCalendar> next30Days = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE,30);
            return next30Days;
        }
        catch (Exception e){
            return null;
        }
    }

    public static Typeface getFontTypeFace(Context context, FontTypeFace fontTypeFace) {
        try{
            switch (fontTypeFace){
                case font_maven:
                    Typeface font_maven = Typeface.createFromAsset(context.getAssets(),"fonts/maven_pro_medium.ttf");
                    return font_maven;
                case font_circular:
                    Typeface font_circular = Typeface.createFromAsset(context.getAssets(),"fonts/circular_std_medium.otf");
                    return font_circular;
                case font_Quattrocento:
                    Typeface font_Quattrocento = Typeface.createFromAsset(context.getAssets(),"fonts/quattrocento_sans_regular.ttf");
                    return font_Quattrocento;
            }
        }
        catch (Exception e){
            return null;
        }
        return null;
    }

    public static int getFontStyle(FontStyle fontTypeFace) {
        try{
            switch (fontTypeFace){
                case bold:
                    return Typeface.BOLD;
                case italic:
                    return Typeface.ITALIC;
                case normal:
                    return Typeface.NORMAL;
            }
        }
        catch (Exception e){
            return -1;
        }
        return -1;
    }

    public static void applyFontStyle(Context context, View view, FontTypeFace fontTypeFace, FontStyle fontStyle) {
        try{
            switch (fontTypeFace){
                case font_maven:
                    Typeface font_maven = Typeface.createFromAsset(context.getAssets(),"fonts/maven_pro_medium.ttf");
                    applyFontStyle(view,font_maven,getFontStyle(fontStyle));
                    break;
                case font_circular:
                    Typeface font_circular = Typeface.createFromAsset(context.getAssets(),"fonts/circular_std_medium.otf");
                    applyFontStyle(view,font_circular,getFontStyle(fontStyle));
                    break;
                case font_Quattrocento:
                    Typeface font_Quattrocento = Typeface.createFromAsset(context.getAssets(),"fonts/quattrocento_sans_regular.ttf");
                    applyFontStyle(view,font_Quattrocento,getFontStyle(fontStyle));

                    break;
            }
        }
        catch (Exception e){
        }
    }

    public static void applyFontStyle(final View v, Typeface typeFace, int fontStyle)
    {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    applyFontStyle(child,typeFace,fontStyle);
                }
            } else if (v instanceof TextView) {
                //((TextView)v).setTypeface(typeFace);
                applyFontStyle((TextView)v,typeFace,fontStyle);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // ignore
        }
    }

    public static void applyFontStyle(TextView textView, Typeface typeFace, int fontStyle)
    {
        textView.setTypeface(Typeface.create(typeFace,fontStyle));
    }

    public static void applyFontSize(final View v, Float fontSize)
    {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    applyFontSize(child, fontSize);
                }
            } else if (v instanceof TextView) {
                ((TextView)v).setTextSize(TypedValue.COMPLEX_UNIT_DIP,fontSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // ignore
        }
    }

    public static String removeFileExtension(String string){
        String convertedString = string;
        if (string.indexOf(".") > 0)
            convertedString = string.substring(0, string.lastIndexOf('.'));
        return convertedString;
    }
    public static String loadJSONFromAsset(String fileName, Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public static int getImageFromDrawable(String imageName, Context context){
        return context.getResources().getIdentifier(imageName,"drawable",context.getPackageName());
    }

    public static void setSystemBarColor(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public static void setSystemBarColor(Activity act, @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(color));
        }
    }

    public static void setSystemBarColorDialog(Context act, Dialog dialog, @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = dialog.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(color));
        }
    }

    public static void setSystemBarLight(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View view = act.findViewById(android.R.id.content);
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }

    public static void setSystemBarLightDialog(Dialog dialog) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View view = dialog.findViewById(android.R.id.content);
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }

    public static void clearSystemBarLight(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = act.getWindow();
            window.setStatusBarColor(ContextCompat.getColor(act, R.color.colorPrimaryDark));
        }
    }

    /**
     * Making notification bar transparent
     */
    public static void setSystemBarTransparent(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void loadImageDrawable(Context ctx, ImageView img, @DrawableRes int drawable) {
        try {
            Glide.with(ctx)
                    .load(drawable)
                    .apply(new RequestOptions()
                            //.crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.NONE))
                    .into(img);
        } catch (Exception e) {
        }
    }

    /*public static void displayImageRound(final Context ctx, final ImageView img, @DrawableRes int drawable) {
        try {
            Glide.with(ctx).load(drawable).asBitmap().centerCrop().into(new BitmapImageViewTarget(img) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(ctx.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    img.setImageDrawable(circularBitmapDrawable);
                }
            });
        } catch (Exception e) {
        }
    }*/

    public static void loadFirebaseImage(final Context context, String imagePath, String imageName, final ImageView imageView) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(imagePath+ imageName);
        GlideApp.with(context)
                .load(storageReference)
                .into(imageView);
    }
    public static void loadFirebaseImage(final Context context, String imageName, final ImageView imageView) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Resources/Images/"+imageName);
        GlideApp.with(context)
                .load(storageReference)
                .into(imageView);

        /*storageReference.child("Resources/Images/"+imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                try {
                    Glide.with(context)
                            .load(uri.toString())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageView);

                    /*Picasso.with(ActivityHomeServices.this)
                            .load(uri.toString())
                            .into(headerBackground);
                } catch (Exception e) {
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });*/
    }

    public static void loadImageDrawable(Context ctx, ImageView img, String url) {
        try {
            Glide.with(ctx).load(url)
                    //.crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(img);
        } catch (Exception e) {
        }
    }

    public static String getFormattedDateShort(Long dateTime) {
        SimpleDateFormat newFormat = new SimpleDateFormat("MMM dd, yyyy");
        return newFormat.format(new Date(dateTime));
    }

    public static String getFormattedDateSimple(Long dateTime) {
        SimpleDateFormat newFormat = new SimpleDateFormat("MMMM dd, yyyy");
        return newFormat.format(new Date(dateTime));
    }

    public static String getFormattedDateEvent(Long dateTime) {
        SimpleDateFormat newFormat = new SimpleDateFormat("EEE, MMM dd yyyy");
        return newFormat.format(new Date(dateTime));
    }

    public static String getFormattedTimeEvent(Long time) {
        SimpleDateFormat newFormat = new SimpleDateFormat("h:mm a");
        return newFormat.format(new Date(time));
    }

    public static String getEmailFromName(String name) {
        if (name != null && !name.equals("")) {
            String email = name.replaceAll(" ", ".").toLowerCase().concat("@mail.com");
            return email;
        }
        return name;
    }

    public static int dpToPx(Context c, int dp) {
        Resources r = c.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static GoogleMap configActivityMaps(GoogleMap googleMap) {
        // set map type
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Enable / Disable zooming controls
        googleMap.getUiSettings().setZoomControlsEnabled(false);

        // Enable / Disable Compass icon
        googleMap.getUiSettings().setCompassEnabled(true);
        // Enable / Disable Rotate gesture
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        // Enable / Disable zooming functionality
        googleMap.getUiSettings().setZoomGesturesEnabled(true);

        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(true);

        return googleMap;
    }

    public static void copyToClipboard(Context context, String data) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("clipboard", data);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    public static void nestedScrollTo(final NestedScrollView nested, final View targetView) {
        nested.post(new Runnable() {
            @Override
            public void run() {
                nested.scrollTo(500, targetView.getBottom());
            }
        });
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
            view.animate().setDuration(200).rotation(180);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
    }

    public static boolean toggleArrow(boolean show, View view) {
        return toggleArrow(show, view, true);
    }

    public static boolean toggleArrow(boolean show, View view, boolean delay) {
        if (show) {
            view.animate().setDuration(delay ? 200 : 0).rotation(180);
            return true;
        } else {
            view.animate().setDuration(delay ? 200 : 0).rotation(0);
            return false;
        }
    }

    public static void changeNavigateionIconColor(Toolbar toolbar, @ColorInt int color) {
        Drawable drawable = toolbar.getNavigationIcon();
        drawable.mutate();
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    public static void changeMenuIconColor(Menu menu, @ColorInt int color) {
        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();
            if (drawable == null) continue;
            drawable.mutate();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    public static void changeOverflowMenuIconColor(Toolbar toolbar, @ColorInt int color) {
        try {
            Drawable drawable = toolbar.getOverflowIcon();
            drawable.mutate();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        } catch (Exception e) {
        }
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static String toCamelCase(String input) {
        input = input.toLowerCase();
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }

    public static String insertPeriodically(String text, String insert, int period) {
        StringBuilder builder = new StringBuilder(text.length() + insert.length() * (text.length() / period) + 1);
        int index = 0;
        String prefix = "";
        while (index < text.length()) {
            builder.append(prefix);
            prefix = insert;
            builder.append(text.substring(index, Math.min(index + period, text.length())));
            index += period;
        }
        return builder.toString();
    }


    public static void rateAction(Activity activity) {
        Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            activity.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        }
    }

    /**
     * For device info parameters
     */
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        } else {
            return manufacturer + " " + model;
        }
    }

    public static String getAndroidVersion() {
        return Build.VERSION.RELEASE + "";
    }

    public static int getVersionCode(Context ctx) {
        try {
            PackageManager manager = ctx.getPackageManager();
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }


    public static String getVersionName(Context ctx) {
        try {
            PackageManager manager = ctx.getPackageManager();
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            return ctx.getString(R.string.app_version) + " " + info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return ctx.getString(R.string.version_unknown);
        }
    }

    public static String getVersionNamePlain(Context ctx) {
        try {
            PackageManager manager = ctx.getPackageManager();
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return ctx.getString(R.string.version_unknown);
        }
    }

    /*public static DeviceInfo getDeviceInfo(Context context) {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.device = Tools.getDeviceName();
        deviceInfo.os_version = Tools.getAndroidVersion();
        deviceInfo.app_version = Tools.getVersionCode(context) + " (" + Tools.getVersionNamePlain(context) + ")";
        deviceInfo.serial = Tools.getDeviceID(context);
        return deviceInfo;
    }*/

    public static String getDeviceID(Context context) {
        String deviceID = Build.SERIAL;
        if (deviceID == null || deviceID.trim().isEmpty() || deviceID.equals("unknown")) {
            try {
                deviceID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            } catch (Exception e) {
            }
        }
        return deviceID;
    }

    public static String getFormattedDateOnly(Long dateTime) {
        SimpleDateFormat newFormat = new SimpleDateFormat("dd MMM yy");
        return newFormat.format(new Date(dateTime));
    }

    public static void directLinkToBrowser(Activity activity, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            activity.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(activity, "Ops, Cannot open url", Toast.LENGTH_LONG).show();
        }
    }

    /*public static void openInAppBrowser(Activity activity, String url, boolean from_notif) {
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        intentBuilder.setToolbarColor(activity.getResources().getColor(R.color.grey_95));
        intentBuilder.setSecondaryToolbarColor(activity.getResources().getColor(R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = intentBuilder.build();

        String packageName = HelperCustomTabs.getPackageNameToUse(activity);

        //If we cant find a package name, it means theres no browser that supports
        //Chrome Custom Tabs installed. So, we fallback to the webview
        if (packageName == null) {
            ActivityWebView.navigate(activity, url, from_notif);
        } else {
            if (!URLUtil.isValidUrl(url)) {
                Toast.makeText(activity, "Ops, Cannot open url", Toast.LENGTH_LONG).show();
                return;
            }
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.intent.setData(Uri.parse(url));
            activity.startActivityForResult(customTabsIntent.intent, 509);
        }
    }*/

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    public static void enableViews(View... views) {
        for (View v : views) {
            v.setEnabled(true);
            v.setVisibility(View.VISIBLE);
        }
    }

    public static void disableViews(View... views) {
        for (View v : views) {
            v.setEnabled(false);
        }
    }

    public static void makeVisible(View... views) {
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }
    }

    public static void makeInvisible(View... views) {
        for (View v : views) {
            v.setVisibility(View.INVISIBLE);
        }
    }

    public static void makeGone(View... views) {
        for (View v : views) {
            v.setVisibility(View.GONE);
        }
    }
    public static final String DATE_TIME_FORMAT_STAMP = "ddMMyyyyHHmmss";
    public static final String DATE_TIME_FORMAT_TIMER = "dd.MM.yyyy, HH:mm:ss";
    public static final String DATE_TIME_FORMAT_CLASSIC = "dd/MM/yyyy HH:mm:ss";
    public static final String DATE_TIME_FORMAT_MULTILINE = "dd/MM/yyyy\nHH:mm:ss";
    public static void utilTest()
    {
        String partialEmail = hideEmailPartially("nirmalcm.sg@gmail.com");

        String dateNow = convertTimeStampStringToSimpleDateTimeFormatString(String.valueOf(getCurrentTimeStamp()),DATE_TIME_FORMAT_STAMP);

        String timeStampNow = convertSimpleDateTimeFormatStringToTimeStampString(dateNow,DATE_TIME_FORMAT_STAMP);

        String dateNowFromTimeStampNow = convertTimeStampStringToSimpleDateTimeFormatString(timeStampNow,DATE_TIME_FORMAT_STAMP);

        String calendarNow = convertCalendarToTimeStampString(Calendar.getInstance(),DATE_TIME_FORMAT_CLASSIC);
    }

    public static final int getStringArraySize(final String stringArray) {
        String[] membersArray = stringArray.split(",");
        return membersArray.length;
    }

    private void requestFocus(View view, Context context) {
        if (view.requestFocus()) {
            //(Activity)context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String hideEmailPartially(String email)
    {
        return email.replaceAll("(?<=..).(?=...*@)", "*");
    }

    public static void disableEnableControls(boolean enable, ViewGroup vg){
        for (int i = 0; i < vg.getChildCount(); i++){
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup){
                disableEnableControls(enable, (ViewGroup)child);
            }
        }
    }

    public static void setVisibility(int visibility, ViewGroup vg){
        for (int i = 0; i < vg.getChildCount(); i++){
            View child = vg.getChildAt(i);
            child.setVisibility(visibility);
            if (child instanceof ViewGroup){
                setVisibility(visibility, (ViewGroup)child);
            }
        }
    }
    public static void disableView(LinearLayout linearLayout)
    {
        for (int k = 0; k < linearLayout.getChildCount(); k++) {
            View child = linearLayout.getChildAt(k);
            child.setEnabled(false);
        }
    }

    public static void enableView(LinearLayout linearLayout)
    {
        for (int k = 0; k < linearLayout.getChildCount(); k++) {
            View child = linearLayout.getChildAt(k);
            child.setEnabled(true);
        }
    }
    public static long getCurrentTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    public static String convertTimeStampStringToSimpleDateTimeFormatString(String timestamp, String format)
    {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.valueOf(timestamp)* 1000L);
        String dateAndTime = DateFormat.format(format, cal).toString();
        return dateAndTime;
    }

    public static String convertSimpleDateTimeFormatStringToTimeStampString(String simpledateTimeFormat, String format)
    {
        long ts = 0L;
        Timestamp timestamp;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Date parsedDate = dateFormat.parse(simpledateTimeFormat);
            timestamp = new Timestamp(parsedDate.getTime());
            ts = timestamp.getTime()/1000L;
        } catch(Exception e) { //this generic but you can control another types of exception
            // look the origin of excption
        }
        return String.valueOf(ts);
    }

    public static String convertCalendarToTimeStampString(Calendar calendar, String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String currentDateandTime = sdf.format(calendar.getTime());

        return currentDateandTime;

    }

    public static String getCurrentDateTimeInString(String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String currentDateandTime = sdf.format(new Date());

        return currentDateandTime;

    }

    public static long getDifferenceInDateAndTimeInTimeStamp(long startDateAndTimeInTimeStamp, long endDateAndTimeInTimeStamp)
    {
        String startDateAndTime =convertTimeStampStringToSimpleDateTimeFormatString(String.valueOf(startDateAndTimeInTimeStamp),"dd.MM.yyyy, HH:mm:ss");
        String endDateAndTime =convertTimeStampStringToSimpleDateTimeFormatString(String.valueOf(endDateAndTimeInTimeStamp),"dd.MM.yyyy, HH:mm:ss");

        long remainingTime = 0;

        Date startDate;
        Date endDate;

        long startLong;
        long endLong;

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        try {
            startDate = formatter.parse(startDateAndTime);
            endDate = formatter.parse(endDateAndTime);
            startLong = startDate.getTime();
            endLong = endDate.getTime();
            remainingTime = endLong - startLong;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return remainingTime;
    }

    public static long getDifferenceInDateAndTimeInString(String startDateAndTime, String endDateAndTime)
    {
        long remainingTime = 0;

        Date startDate;
        Date endDate;

        long startLong;
        long endLong;

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        try {
            startDate = formatter.parse(startDateAndTime);
            endDate = formatter.parse(endDateAndTime);
            startLong = startDate.getTime();
            endLong = endDate.getTime();
            remainingTime = endLong - startLong;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return remainingTime;
    }

    public static String timeCalculate(double ttime) {

        long days, hours, minutes, seconds;
        String daysT = "", restT = "";

        days = (Math.round(ttime) / 86400);
        hours = (Math.round(ttime) / 3600) - (days * 24);
        minutes = (Math.round(ttime) / 60) - (days * 1440) - (hours * 60);
        seconds = Math.round(ttime) % 60;

        if(days==1) daysT = String.format("%d day ", days);
        if(days>1) daysT = String.format("%d days ", days);

        restT = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        return daysT + restT;
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public static String getRealPathFromURI2(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public final static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);

        /*if (bm != null && !bm.isRecycled()) {
            bm.recycle();
            bm = null;
        }*/
        return resizedBitmap;
    }

    @SuppressLint("RestrictedApi")
    public static void removeShiftMode(BottomNavigationView view) {
        //this will remove shift mode for bottom navigation view
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShifting(false);
                //item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }

        } catch (NoSuchFieldException e) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }
    }

    /*public void accountPopUp(View v, Context context) {
        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.mygroups_activity_my_groups_popup_add_group,(ViewGroup)v.findViewById(R.id.popup));
            // create a 300px width and 470px height PopupWindow
            final PopupWindow popupWindow = new PopupWindow(layout, 700,
                    900, true);

            // display the popup in the center
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);


            TextView userEmail = (TextView) layout.findViewById(R.id.userEmail);
            userEmail.setText(new ManagerSession(context).getUserEmail());
            Button cancelButton = (Button) layout.findViewById(R.id.cancelPopup);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
