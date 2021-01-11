package com.study.webfluxtest.samples;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.SignalType;

/**
 * @version : 1.0
 * @description: java类作用描述
 * @author: tianwen
 * @create: 2021/1/10 15:13
 **/
public class SampleSubscriber<T> extends BaseSubscriber<T> {

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        subscription.request(1);
    }

    @Override
    protected void hookOnNext(T value) {
        request(1);
    }

    @Override
    protected void hookOnComplete() {
        System.out.println("订阅完成了");
    }

    @Override
    protected void hookOnError(Throwable throwable) {
        throwable.printStackTrace();
        System.out.println("订阅出错了");
    }

    @Override
    protected void hookOnCancel() {
        System.out.println("订阅 hookOnCancel ");
    }

    @Override
    protected void hookFinally(SignalType type) {
        System.out.println("订阅 hookFinally ");
    }
}
