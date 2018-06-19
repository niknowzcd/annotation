package com.example.dly.annotation_api.finder;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public class ActivityFinder implements Finder {

    @Override
    public View findView(Object source, int id) {
        return ((Activity) source).findViewById(id);
    }
}
