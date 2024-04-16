package com.example.shah.translator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.shah.translator.databinding.ActivityDashboardBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

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
        Intent intent=new Intent(Dashboard.this,Favourite.class);
        startActivity(intent);
    }
}