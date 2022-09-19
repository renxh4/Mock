package com.renxh.mock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * Activity跳转封装类，把OnActivityResult方式改为Callback方式
 *
 * Created by XiaoFeng on 2018/9/5.
 */
public class ActivityLauncher {

    private static final String TAG = "ActivityLauncher";
    private Context mContext;
    /** 标准SDK下的Fragment */
    private RouterFragment mRouterFragment;

    public static ActivityLauncher init(Fragment fragment) {
        return init(fragment.getActivity());
    }

    public static ActivityLauncher init(FragmentActivity activity) {
        return new ActivityLauncher(activity);
    }

    public static ActivityLauncher init(Activity activity) {
        return new ActivityLauncher(activity);
    }

    private ActivityLauncher(FragmentActivity activity) {
        mContext = activity;
        mRouterFragment = getRouterFragment(activity);
    }

    private ActivityLauncher(Activity activity) {
        mContext = activity;
        mRouterFragment = getRouterFragment(activity);
    }




    private RouterFragment getRouterFragment(Activity activity) {
        RouterFragment routerFragment = findRouterFragment(activity);
        if (routerFragment == null) {
            routerFragment = RouterFragment.newInstance();
            android.app.FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(routerFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return routerFragment;
    }

    private RouterFragment findRouterFragment(Activity activity) {
        return (RouterFragment) activity.getFragmentManager().findFragmentByTag(TAG);
    }

    public void startActivityForResult(Class<?> clazz, Callback callback) {
        Intent intent = new Intent(mContext, clazz);
        startActivityForResult(intent, callback);
    }

    public void startActivityForResult(Intent intent, Callback callback) {
        if (mRouterFragment != null) {
            mRouterFragment.startActivityForResult(intent, callback);
        } else {
            throw new RuntimeException("please do init first!");
        }
    }

    public interface Callback {
        void onActivityResult(int resultCode, Intent data);
    }
}