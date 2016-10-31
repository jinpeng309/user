package com.capslock.rpc.service.user.util;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by alvin.
 */
public class RxDecorator {

    public static Observable<Void> toRx(final RunnableWithException runnable) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                try {
                    runnable.run();
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                }
            }
        }).observeOn(Schedulers.io());
    }
}
