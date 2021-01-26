package com.keycodemon.moneymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {

    ImageView logo,appName,splashImg;
    LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        logo = findViewById(R.id.SplashLogo);
        appName = findViewById(R.id.appNameSplash);
        splashImg = findViewById(R.id.splashImg);
        lottieAnimationView = findViewById(R.id.splashAnimation);

//        splashImg.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
//        logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
//        appName.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
//        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);

        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }
}