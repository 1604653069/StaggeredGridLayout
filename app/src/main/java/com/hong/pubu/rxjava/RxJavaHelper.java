package com.hong.pubu.rxjava;



import com.hong.pubu.ExceptionHandle;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * 异步请求工具类
 */
public class RxJavaHelper {
    /**
     * 切换线程操作
     *
     * @return Observable转换器
     */
    public static <T> ObservableTransformer<T, T> observeOnMainThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())//启动订阅线程
                        .observeOn(AndroidSchedulers.mainThread());//切换至主线程
            }
        };
    }

    /**
     * 指定错误重新发送订阅
     */
    public static class observeOnPredicate implements Predicate<Throwable> {
        @Override
        public boolean test(Throwable throwable) throws Exception {
            if (throwable instanceof Exception) {
                ExceptionHandle.ResponseThrowable error = ExceptionHandle.HandleException(throwable);
                if (error.getCode() == ExceptionHandle.HTTP_408 ||
                        error.getCode() == ExceptionHandle.HTTP_1003)
                    return true;
            }
            return false;
        }
    }

    /**
     * 被观察者的错误数据处理
     */
    public abstract static class observeOnConsumerThrowable implements Consumer<Throwable> {
        @Override
        public void accept(Throwable throwable) throws Exception {
            if (throwable instanceof Exception) {
                this.accept(ExceptionHandle.HandleException(throwable));
            } else {
                this.accept(new ExceptionHandle.ResponseThrowable(throwable));
            }
        }

        public abstract void accept(ExceptionHandle.ResponseThrowable error);
    }

    /**
     * 遇到错误重新发送一个订阅（观察者）
     */
    public abstract static class observeOnErrorReturn<R> implements Function<Throwable, R> {

        @Override
        public R apply(Throwable throwable) throws Exception {
            if (throwable instanceof Exception) {
                return this.accept(ExceptionHandle.HandleException(throwable));
            } else {
                return this.accept(new ExceptionHandle.ResponseThrowable(throwable));
            }
        }

        public abstract R accept(ExceptionHandle.ResponseThrowable error);
    }
}
