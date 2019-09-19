package com.example.moviecatalogue2;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FavFragment extends Fragment {
    private FragmentActivity mContext;


    public FavFragment(){

  }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_fav,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewPager viewPager = (ViewPager) getView().findViewById(R.id.viewpager);
        FavFragmentAdapter adapter = new FavFragmentAdapter(getChildFragmentManager(), mContext);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) getView().findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onAttach(Activity activity) {
        mContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
}
