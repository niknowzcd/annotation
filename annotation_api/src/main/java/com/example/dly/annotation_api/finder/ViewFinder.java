package com.example.dly.annotation_api.finder;

import android.content.Context;
import android.view.View;


public class ViewFinder implements Finder {

    @Override
    public View findView(Object source, int id) {
        return ((View) source).findViewById(id);
    }
}