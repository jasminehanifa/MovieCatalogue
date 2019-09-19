package com.example.moviecatalogue2.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.example.moviecatalogue2.widget.StackRemoteViewsFactory;

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
