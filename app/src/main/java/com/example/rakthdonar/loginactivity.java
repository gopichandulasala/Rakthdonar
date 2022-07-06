package com.example.rakthdonar;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class loginactivity extends AppCompatActivity{
    TabLayout tbl;
    ViewPager vg;
    float v = 0;
    FloatingActionButton fb;
    Button login;
    ProgressBar pb;
    private TextView mail,passwd,fp;
    private FirebaseAuth mauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        mauth = FirebaseAuth.getInstance();
        login=findViewById(R.id.loginb);
        mail=findViewById(R.id.mailid);
        passwd=findViewById(R.id.password);
        fp=findViewById(R.id.forgotpass);
        tbl = findViewById(R.id.tablelayout);
        vg = findViewById(R.id.viewpager);
        tbl.addTab(tbl.newTab().setText("Login"));
        tbl.addTab(tbl.newTab().setText("signup"));
        tbl.setTabGravity(TabLayout.GRAVITY_FILL);
        final LoginAdapter ld = new LoginAdapter(getSupportFragmentManager(), this, tbl.getTabCount());
        vg.setAdapter(ld);
        vg.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tbl));
        tbl.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vg.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mauth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser==null){
            return;

        }
        Intent in=new Intent(this,DonarActivity.class);
        startActivity(in);
        finish();
    }


}