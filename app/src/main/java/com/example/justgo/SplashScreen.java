package com.example.justgo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN= 4000;


    Animation TopAnime, BottomAnime;
    ImageView image;
    TextView logo, slogan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        TopAnime= AnimationUtils.loadAnimation(this,R.anim.top_anima);
        BottomAnime= AnimationUtils.loadAnimation(this,R.anim.botton_anima);

        image= findViewById(R.id.imageView1);
        logo= findViewById(R.id.logo);
        slogan= findViewById(R.id.slogan);

        image.setAnimation(TopAnime);
        logo.setAnimation(BottomAnime);
        slogan.setAnimation(BottomAnime);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent  = new Intent(SplashScreen.this,LogIn.class);

                Pair[] pairs= new Pair[2];
                pairs[0]= new Pair<View,String>(image,"logo_image");
                pairs[1]= new Pair<View,String>(logo,"logo_text");

                ActivityOptions options= null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this,pairs);
                }

                startActivity(intent,options.toBundle());



            }
        },SPLASH_SCREEN);


    }
}