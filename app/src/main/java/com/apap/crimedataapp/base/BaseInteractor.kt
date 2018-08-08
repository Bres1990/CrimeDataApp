package com.apap.crimedataapp.base

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseInteractor<D, P> {

    protected abstract fun buildObservable(p: P): Observable<D>

    fun execute(p: P): Observable<D> {
        return buildObservable(p)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}