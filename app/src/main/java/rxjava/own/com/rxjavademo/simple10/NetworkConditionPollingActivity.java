package rxjava.own.com.rxjavademo.simple10;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rxjava.own.com.rxjavademo.R;
import rxjava.own.com.rxjavademo.service.Api;
import rxjava.own.com.rxjavademo.service.entity.Translation;

public class NetworkConditionPollingActivity extends Activity {
    private Api request;
    private Subscription subscribe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_polling);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 支持RxJava
                .build();
        request = retrofit.create(Api.class);


    }

    public void onClick(View view) {

        subscribe= Observable.interval(2, 1, TimeUnit.SECONDS)
                .doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.d("test", "第 " + aLong + " 次轮询" + " 当前线程 = " + Thread.currentThread().getName());
                        if(aLong == 5){
                            subscribe.unsubscribe();
                        }
                    }
                })
                .flatMap(new Func1<Long, Observable<Translation>>() {
                    @Override
                    public Observable<Translation> call(Long aLong) {
                        return request.getCall();
                    }
                })
                .compose(this.<Translation>schedulersTransformer())
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onCompleted() {
                        Log.e("test", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("test", "onError e = " + e);
                    }

                    @Override
                    public void onNext(Translation translation) {
                        Log.d("test", "translation = " + translation);

                    }
                });


    }

    public <T> Observable.Transformer<T, T> schedulersTransformer() {
        return new Observable.Transformer<T, T>() {

            @Override
            public Observable<T> call(Observable<T> rObservable) {
                return rObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


}
