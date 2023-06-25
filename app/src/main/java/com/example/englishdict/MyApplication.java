package com.example.englishdict;

import android.app.Application;

import com.example.englishdict.database.EnglishWord;
import com.example.englishdict.database.User;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private static MyApplication mApp;
    public User user;

    public static MyApplication getInstance(){
        if(mApp == null){
            mApp = new MyApplication();
        }
        return mApp;
    }
}
