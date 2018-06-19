package com.example.dly.annotation_api;


import com.example.dly.annotation_api.finder.Finder;

public interface Injector<T> {

    void inject(T host, Object source, Finder finder);
}
