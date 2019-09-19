package com.example.moviecatalogue2;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.moviecatalogue2.Movie.FavMovieFragment;
import com.example.moviecatalogue2.Tv.FavTvFragment;

public class FavFragmentAdapter extends FragmentPagerAdapter {
    private Context context;

    public FavFragmentAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        context = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new FavMovieFragment();
        }
        else{
            return new FavTvFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return context.getResources().getString(R.string.fragment_fav_movie);
            case 1:
                return context.getResources().getString(R.string.fragment_fav_tv);
            default:
                return null;
        }
    }
}
