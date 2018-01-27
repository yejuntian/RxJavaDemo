package rxjava.own.com.rxjavademo.simple9;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rxjava.own.com.rxjavademo.R;


public class Rxjava_method extends Activity implements View.OnClickListener {
    private Button Button1;
    private Button Button2;
    private EditText editText;
    private Observable<String> observable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple2);
        Button1 = findViewById(R.id.button1);
        Button2 = findViewById(R.id.button2);
        editText = findViewById(R.id.edit_text);
        Button1.setOnClickListener(this);
        Button2.setOnClickListener(this);

        observable = Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Observable.create(new OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        if (subscriber.isUnsubscribed()) {
                            return;
                        }
                        Log.e("test", "执行代码");
                        subscriber.onNext("hello word");
                        subscriber.onCompleted();
                    }
                });
            }
        });

        Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:

                break;
            case R.id.button2:
//                repeat();
//                defer();
                range();
                break;
        }
    }

    /**
     * repeat方法
     * 重复发射几次数据
     */
    public void repeat() {
        Observable.just("hello word", 123).repeat(3).subscribe(new Action1<Serializable>() {
            @Override
            public void call(Serializable serializable) {
                Log.e("test", String.valueOf(serializable));
            }
        });
    }

    /**
     * 使用懒加载的机制
     * 直到观察者被订阅的时候才会被回调
     */
    public void defer() {
        observable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.e("test", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e("test", "s = " + s);
            }
        });
    }

    /**
     * 从指定位置开始向后发送多少个数据
     * 第一个参数为起始点
     * 第二个参数为发射数字的个数
     */
    public void range() {
        Observable.range(1, 5).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer Integer) {
                Log.e("test", "range  :" + Integer);
            }
        });
    }




}
