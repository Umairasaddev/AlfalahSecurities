package com.softech.bipldirect.Fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.softech.bipldirect.MainActivity;
import com.softech.bipldirect.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;


public class WatchlistPagerFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static int page = 0;
    private ViewPagerAdapter adapter;
    private ArrayList<String> userIdList=new ArrayList<>();
    View view;
    public SharedPreferences preferences;
    SharedPreferences.Editor prefsEditor;
    public WatchlistPagerFragment() {
        // Required empty public constructor
    }

    public static WatchlistPagerFragment newInstance(int page1) {
        page = page1;
        return new WatchlistPagerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.watch_list_fragment_pager, container, false);
        return view;
    }


    @Override
    public void onResume() {
        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (toolbar != null) {
            toolbar.setTitle("");
        }
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.viewpager);
        preferences =getContext().getSharedPreferences("appData", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = preferences.edit();

        setupViewPager();
        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//               NavigationDrawerActivity.getSwitchBtn(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int i) {
                if(i == 1){
//                    showAlertIfDataNotAvailable(PortfolioNewFragment.checkIfDataIsAvailable());
                } else {
                    // do nothing.
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) { }
        });
        viewPager.setCurrentItem(page);

        String userID= MainActivity.loginResponse.getResponse().getUserId();

        Set<String> set = preferences.getStringSet("key", null);
        if (set!=null) {
            userIdList.addAll(set);
        }
        else {
            userIdList.add(userID);
            Set<String> set1=new HashSet<>();
            set1.addAll(userIdList);
            prefsEditor.putStringSet("key", set1);
            prefsEditor.commit();
//            showCustomDialog(getContext());
        }
        boolean userId=false;
        for (int i=0;i<userIdList.size();i++) {
            if (userID.equals(userIdList.get(i))) {
                userId = true;
            }
        }
        if (!userId){
            userIdList.add(userID);
            set.addAll(userIdList);
            prefsEditor.putStringSet("key", set);
            prefsEditor.commit();
//            showCustomDialog(getContext());
        }




    }

//    private void showAlertIfDataNotAvailable(boolean isDataAvailable){
//        if(!isDataAvailable){
//            // change_02
//            // show alert dialog cause no chart data is available.
//            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
//            alert.setTitle("KTrade");
////            alert.setMessage("Data is not available to draw chart");
//            alert.setMessage("Portfolio is not available to draw chart");
//            alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            });
//            alert.show();
//        }
//    }

    private void setupViewPager() {
        adapter = new ViewPagerAdapter(this.getChildFragmentManager());
        adapter.addFragment(new ExchangeFragment(), "Market");
        adapter.addFragment(new MarketFragment(), "WatchList");
        adapter.addFragment(new PortfolioFragment(), "Portfolio");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
