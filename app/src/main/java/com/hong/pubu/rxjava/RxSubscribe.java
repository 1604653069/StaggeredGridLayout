package com.hong.pubu.rxjava;


import android.content.Context;
import android.util.Log;


import com.hong.pubu.BaseResponse;
import com.hong.pubu.ExceptionHandle;
import com.hong.pubu.JsonUtils;

import java.util.Locale;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 异步操作工具类
 * @param <T>
 */
public abstract class RxSubscribe<T> implements Observer<BaseResponse<T>> {

    public Disposable disposable;
    private String tip;
    private Context context;

    public RxSubscribe(Context context) {
        this.context = context;
    }

    public RxSubscribe(Context context, String tip) {
        this.context = context;
        this.tip = tip;
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
        Log.e("TAG","Start the subscribe");
    }

    @Override
    public void onNext(BaseResponse<T> t) {
        try {
            if(t.getStatus()==200)
               onSuccess(t.getData());
            else
                onHint("请求失败");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("TAG","这里被调用了");
        }
        Log.e("TAG",String.format(Locale.CANADA, "Respond data:%s%n",
                JsonUtils.objectToString(t)));
    }

    @Override
    public void onError(Throwable e) {
        ExceptionHandle.ResponseThrowable error;
        if (e instanceof Exception)
            error = ExceptionHandle.HandleException(e);
        else
            error = new ExceptionHandle.ResponseThrowable(e);
        onHint(error.getMessage() + error.getCode());
        Log.i("TAG","错误提示:"+error.getMessage() + error.getCode());
//        postError(error.getMessage() + error.getCode());
        Log.e("TAG",String.format(Locale.CANADA, "Error code:%s%nError message:%s",
                error.getCode(), error.getMessage()));
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onComplete() {
        Log.e("TAG","End the subscribe");
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }


    public abstract void onSuccess(T t) throws Exception;

    public abstract void onHint(String hint);
}
