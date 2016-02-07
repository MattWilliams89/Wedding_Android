package org.maw.wedding.fetching;

public interface FetcherListener<T> {
    void onSuccess(T result);
    void onFailure();
}
