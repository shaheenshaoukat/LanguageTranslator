package com.example.shah.translator.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.shah.translator.R;
import com.example.shah.translator.databinding.ActivityMainBinding;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Animation topanim, bottomanim;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        topanim = AnimationUtils.loadAnimation(this, R.anim.topanim);
        bottomanim = AnimationUtils.loadAnimation(this, R.anim.bottomanim);
        binding.logo.setAnimation(topanim);
        binding.text.setAnimation(bottomanim);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                startActivity(intent);
                finish();

            }
        }, 2500);



    }
}