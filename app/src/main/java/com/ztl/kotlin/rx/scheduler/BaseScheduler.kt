package com.cxz.wanandroid.rx.scheduler

import io.reactivex.*
import org.reactivestreams.Publisher

abstract class BaseScheduler<T> protected constructor(private val subscribeOnScheduler: Scheduler,
                                                      private val observeOnScheduler: Scheduler) : ObservableTransformer<T, T>,
        SingleTransformer<T, T>,
        MaybeTransformer<T, T>,
        CompletableTransformer,
        FlowableTransformer<T, T> {

    override fun apply(observable: Completable): CompletableSource {
        return observable.subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
    }

    override fun apply(observable: Flowable<T>): Publisher<T> {
        return observable.subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
    }

    override fun apply(observable: Maybe<T>): MaybeSource<T> {
        return observable.subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
    }

    override fun apply(observable: Observable<T>): ObservableSource<T> {
        return observable.subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
    }

    override fun apply(observable: Single<T>): SingleSource<T> {
        return observable.subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
    }
}
