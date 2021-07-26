package com.example.grabtutor.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.grabtutor.Fragment.Fragment1;
import com.example.grabtutor.Fragment.Fragment2;
import com.example.grabtutor.Fragment.Fragment3;
import com.example.grabtutor.R;

import org.jetbrains.annotations.NotNull;

public class SplashActivity extends AppCompatActivity {

    private ImageView logo;
    private static final int NUM_PAGES = 3;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;
    Animation anim;
    SharedPreferences onBoardingScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.imageView_splash_logo);
        logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        anim = AnimationUtils.loadAnimation(this, R.anim.o_b_anim);
        viewPager.setAnimation(anim);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
                boolean isFirstTime = onBoardingScreen.getBoolean("firstTime", true);

                if (isFirstTime) {
                    SharedPreferences.Editor editor = onBoardingScreen.edit();
                    editor.putBoolean("firstTime", false);
                    editor.commit();
                    //go to onboardingscreen
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1000);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(@NonNull @NotNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @NotNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return new Fragment1();
                case 1: return new Fragment2();
                case 2: return new Fragment3();
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}