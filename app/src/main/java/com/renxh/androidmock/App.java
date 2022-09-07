package com.renxh.androidmock;

import android.app.Application;

import com.renxh.mock.MockSdk;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MockSdk.INSTANCE.init(this);
    }
}
