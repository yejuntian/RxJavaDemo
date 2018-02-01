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
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rxjava.own.com.rxjavademo.R;
import rxjava.own.com.rxjavademo.service.Api;
import rxjava.own.com.rxjavademo.service.entity.Translation;

public class InterfaceNestingActivity extends Activity {
    private Api request;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inter_nest);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 支持RxJava
                .build();
        request = retrofit.create(Api.class);


    }

    public void onClick(View view) {
        request.getRegisterCall()
                .flatMap(new Func1<Translation, Observable<Translation>>() {
                    @Override
                    public Observable<Translation> call(Translation translation) {
                        if (translation.status == 1) {
                            return request.getLoginCall();
                        } else {
                            return Observable.error(new Exception("访问异常"));
                        }
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
                        Log.e("test", "e = " + e);
                    }

                    @Override
                    public void onNext(Translation translation) {
                        Log.e("test", "translation = " + translation);
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
