package com.vigorous.cache;

public interface Cache<T> {
    void put(String key,T value);
    T get(String key);
}
