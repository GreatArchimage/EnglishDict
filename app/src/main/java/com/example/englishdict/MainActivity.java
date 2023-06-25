package com.example.englishdict;

import android.content.ClipData;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.englishdict.database.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.englishdict.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.noteFragment, R.id.accountFragment)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);


        BottomNavigationView navView2 =findViewById(R.id.nav_view2);
        NavigationUI.setupWithNavController(navView2, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        navController.navigate(R.id.nav_home);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.noteFragment:
                        navController.navigate(R.id.noteFragment);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.accountFragment:
                        navController.navigate(R.id.accountFragment);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.item_about:
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("关于")
                                .setMessage("这是一款英语词典app，主要功能：查单词、收藏单词。")
                                .setPositiveButton("确定", null)
                                .show();
                        break;
                    case R.id.item_quit:
                        finish();
                        break;
                }
                return false;
            }
        });

        //设置侧滑栏用户名和邮箱的显示
        View header = navigationView.getHeaderView(0);
        TextView tv_nav_username = header.findViewById(R.id.tv_nav_username);
        TextView tv_nav_email = header.findViewById(R.id.tv_nav_email);

        User user = MyApplication.getInstance().user;
        String username = "请先登录";;
        String email = "";
        if(user != null){
            username = "Welcome,"+user.userName;
            email = user.email;
            //显示头像
            ImageView img_portrait = header.findViewById(R.id.img_nav_portrait);
            byte[] photobytes = user.portrait;
            if (photobytes != null && photobytes.length > 0) {
                Glide.with(this).load(photobytes)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(img_portrait);
            }
        }
        //TextView tv_nav_username = findViewById(R.id.tv_nav_username);
        //TextView tv_nav_email = findViewById(R.id.tv_nav_email);
        tv_nav_username.setText(username);
        tv_nav_email.setText(email);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}