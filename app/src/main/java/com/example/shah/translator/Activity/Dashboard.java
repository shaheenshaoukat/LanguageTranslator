package com.example.shah.translator.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.shah.translator.Fragment.ChatFragment;
import com.example.shah.translator.Fragment.HomeFragment;
import com.example.shah.translator.Fragment.SettingFragment;
import com.example.shah.translator.R;
import com.example.shah.translator.databinding.ActivityDashboardBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {
    ActivityDashboardBinding binding;
    HomeFragment homefrgmnt = new HomeFragment();
    SettingFragment settingFragment = new SettingFragment();
    ChatFragment chatFragment = new ChatFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setCurrentFragment(homefrgmnt);

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    setCurrentFragment(homefrgmnt);
                } else if (item.getItemId() == R.id.chat) {
                    setCurrentFragment(chatFragment);
                } else if (item.getItemId() == R.id.setting) {
                    setCurrentFragment(settingFragment);
                }
                return true;
            }
        });

    }

    private void setCurrentFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout, fragment)
                .commit();
    }

    public void Favourite(View view) {
        Intent intent=new Intent(Dashboard.this, Favourite.class);
        startActivity(intent);
    }
}