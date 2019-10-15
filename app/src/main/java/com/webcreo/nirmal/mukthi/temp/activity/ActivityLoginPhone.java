package com.webcreo.nirmal.mukthi.temp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.webcreo.nirmal.mukthi.R;

import java.util.concurrent.TimeUnit;

import static com.webcreo.nirmal.mukthi.temp.utils.Tools.disableViews;
import static com.webcreo.nirmal.mukthi.temp.utils.Tools.enableViews;
import static com.webcreo.nirmal.mukthi.temp.utils.Tools.makeGone;
import static com.webcreo.nirmal.mukthi.temp.utils.Tools.makeInvisible;
import static com.webcreo.nirmal.mukthi.temp.utils.Tools.makeVisible;

public class ActivityLoginPhone extends AppCompatActivity implements
        View.OnClickListener {

    private static final String TAG = "ActivityLoginPhone";

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;

    private static final String COUNTRY_CODE_INDIA = "+91";

    private FirebaseAuth mAuth;

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private FrameLayout mParentView;
    private EditText mPhoneNumber;
    private EditText mOTP;
    private TextInputLayout mOTPLayout;
    private Button mLogin;
    private TextView mResend;
    private FrameLayout mEmailLogin;
    private LinearLayout mProgress;

    private String mOTPCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        mPhoneNumber = findViewById(R.id.id_phone);
        mOTP = findViewById(R.id.id_OTP);
        mOTPLayout = findViewById(R.id.id_OTP_layout);

        mLogin = findViewById(R.id.id_login);
        mResend = findViewById(R.id.id_resend_otp);
        mEmailLogin = findViewById(R.id.id_email_login);

        mLogin.setOnClickListener(this);
        mResend.setOnClickListener(this);
        mEmailLogin.setOnClickListener(this);

        mParentView = findViewById(R.id.id_parent);

        mProgress = (LinearLayout) findViewById(R.id.id_progress);

        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);
                mVerificationInProgress = false;
                updateUI(STATE_VERIFY_SUCCESS, credential);
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                mVerificationInProgress = false;
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    mPhoneNumber.setError("Invalid phone number.");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                }
                updateUI(STATE_VERIFY_FAILED);
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                mResendToken = token;
                updateUI(STATE_CODE_SENT);
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(COUNTRY_CODE_INDIA + mPhoneNumber.getText().toString());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }


    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            updateUI(STATE_SIGNIN_SUCCESS, user);
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                mOTP.setError("Invalid code.");
                            }
                            updateUI(STATE_SIGNIN_FAILED);
                        }
                    }
                });
    }

    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user);
        } else {
            updateUI(STATE_INITIALIZED);
        }
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:
                // Initialized state, show only the phone number field and start button
                makeVisible(mLogin);
                makeInvisible(mProgress);
                mLogin.setText("GET OTP");
                enableViews(mPhoneNumber,mLogin,mEmailLogin);
                makeGone(mOTPLayout);
                makeInvisible(mResend);
                break;
            case STATE_CODE_SENT:
                // Code sent state, show the verification field, the
                makeVisible(mLogin);
                makeInvisible(mProgress);
                makeVisible(mParentView);
                mParentView.setAlpha(1f);
                mLogin.setText("LOGIN");
                disableViews(mPhoneNumber);
                enableViews(mOTPLayout,mLogin,mEmailLogin,mResend);
                break;
            case STATE_VERIFY_FAILED:
                // Verification has failed, show all options
                makeVisible(mLogin);
                makeInvisible(mProgress);
                enableViews(mPhoneNumber,mLogin,mEmailLogin);
                makeGone(mOTPLayout);
                makeVisible(mLogin);
                makeInvisible(mResend);
                break;
            case STATE_VERIFY_SUCCESS:
                // Set the verification text based on the credential
                makeVisible(mLogin);
                makeInvisible(mProgress);
                if (cred != null) {
                    if (cred.getSmsCode() != null) {
                        mOTP.setText(cred.getSmsCode());
                    } else {
                        mOTP.setText("instand validation");
                    }
                }

                break;
            case STATE_SIGNIN_FAILED:
                // No-op, handled by sign-in check
                makeVisible(mLogin);
                makeInvisible(mProgress);
                break;
            case STATE_SIGNIN_SUCCESS:
                // Verification has succeeded, proceed to firebase sign in
                startActivity(new Intent(ActivityLoginPhone.this,ActivityMain.class));
                break;
        }
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = mPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumber.setError("Invalid phone number.");
            return false;
        }

        return true;
    }

    private boolean validateOTP() {
        mOTPCode = mOTP.getText().toString();
        if (TextUtils.isEmpty(mOTPCode)) {
            mOTP.setError("Cannot be empty");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_login:
                if(validatePhoneNumber()) {
                    makeInvisible(mLogin);
                    makeVisible(mProgress);
                    startPhoneNumberVerification(COUNTRY_CODE_INDIA+mPhoneNumber.getText().toString());
                    if(validateOTP()) {
                        makeInvisible(mLogin);
                        makeVisible(mProgress);
                        verifyPhoneNumberWithCode(mVerificationId, mOTPCode);
                    }
                }
                break;
            case R.id.id_resend_otp:
                resendVerificationCode(COUNTRY_CODE_INDIA + mPhoneNumber.getText().toString(), mResendToken);
                break;
            case R.id.id_email_login:
                startActivity(new Intent(ActivityLoginPhone.this, ActivityLoginEmail.class));
                break;
        }
    }
}
