package com.example.dly.annotation_api;

import android.app.Activity;
import android.view.View;

import com.example.dly.annotation_api.finder.ActivityFinder;
import com.example.dly.annotation_api.finder.Finder;
import com.example.dly.annotation_api.finder.ViewFinder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dly on 2018/6/19.
 */

public class ButterKnife {

    public ButterKnife() {
        throw new AssertionError("No .instances");
    }

    private static final ActivityFinder ACTIVITY_FINDER = new ActivityFinder();

    private static final ViewFinder VIEW_FINDER = new ViewFinder();

    private static Map<String, Injector> FINDER_MAP = new HashMap<>();

    public static void bind(Activity activity) {
        bind(activity, activity, ACTIVITY_FINDER);
    }

    public static void bind(View view) {
        bind(view, view);
    }

    public static void bind(Object host, View view) {
        bind(host, view, VIEW_FINDER);
    }

    /**
     * 获取目标类
     *
     * @param host
     * @param source
     * @param finder
     */
    public static void bind(Object host, Object source, Finder finder) {
        String className = host.getClass().getName();
        try {
            Injector injector = FINDER_MAP.get(className);
            if (injector == null) {
                Class<?> finderClass = Class.forName(className + "$$Injector");
                injector = (Injector) finderClass.newInstance();
                FINDER_MAP.put(className, injector);
            }
            injector.inject(host, source, finder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
