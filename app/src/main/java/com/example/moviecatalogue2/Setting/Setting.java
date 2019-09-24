package com.example.moviecatalogue2.Setting;

import android.os.Bundle;
import android.support.annotation.Nullable;

public class Setting extends AppPref {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingPreferenceFragment()).commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
