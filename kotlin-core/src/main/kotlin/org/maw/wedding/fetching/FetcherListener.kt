package org.maw.wedding.fetching

interface FetcherListener<T> {
    fun onSuccess(result: T)
    fun onFailure()
}
