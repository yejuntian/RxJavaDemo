package rxjava.own.com.rxjavademo.simple9;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rxjava.own.com.rxjavademo.R;


public class Rxjava_API extends Activity implements View.OnClickListener {
    private Button repeat, repeatWhen, range, interval, timer, delay, toList;
    private Observable<String> observable;
    private Subscription subscribe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);
        repeat = findViewById(R.id.repeat);
        repeatWhen = findViewById(R.id.repeatWhen);
        range = findViewById(R.id.range);
        interval = findViewById(R.id.interval);
        timer = findViewById(R.id.timer);
        delay = findViewById(R.id.delay);
        toList = findViewById(R.id.toList);


        repeat.setOnClickListener(this);
        repeatWhen.setOnClickListener(this);
        range.setOnClickListener(this);
        interval.setOnClickListener(this);
        timer.setOnClickListener(this);
        delay.setOnClickListener(this);
        toList.setOnClickListener(this);

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
            case R.id.repeat:
                repeat();
                break;
            case R.id.repeatWhen:
                repeatWhen();
                break;
            case R.id.range:
                range();
                break;
            case R.id.interval:
                interval();
                break;
            case R.id.timer:
                timer();
                break;
            case R.id.delay:
                delay();
                break;
            case R.id.toList:
                toListTest();
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

    public void repeatWhen() {
        Observable.just("hello word", 123).repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Void> observable) {
                return observable.timer(3000, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Action1<Serializable>() {
            @Override
            public void call(Serializable serializable) {
                Log.e("test", String.valueOf(serializable));
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

    /**
     * interval()无限轮询：
     * 第一个参数：开始发送消息推迟的时间
     * 第二个参数：两个消息之间发送的间隔时间（即：轮询时间）
     * 第三个参数：时间单位（毫秒、秒、分钟....）；
     */
    public void interval() {
        Observable<Long> observable = Observable.interval(2, 3, TimeUnit.SECONDS);
        subscribe = observable.subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.e("test", "interval = " + String.valueOf(aLong));
            }
        });
    }

    /**
     * timer只允许执行一次
     */
    public void timer() {
        Observable.timer(3, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.e("test", "timer = " + String.valueOf(aLong));
            }
        });
    }

    /**
     * 整体向后延迟5秒
     */
    public void delay() {
        Integer[] items = {1, 2, 3, 4, 5};
        Observable<Integer> observable = Observable.from(items).delay(5, TimeUnit.SECONDS);
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {

            @Override
            public void onNext(Integer v) {
                Log.e("test", "onNext................." + v);
            }

            @Override
            public void onCompleted() {
                Log.e("test", "onCompleted.................");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("test", "onError.....................");
            }
        };

        observable.subscribe(subscriber);
    }


    /**
     * 测试toList操作符住
     */
    private void toListTest() {
        String[] strs = new String[]{"jack", "ann", "one", "two", "three", "four"};
        Observable.from(strs)
                .toList()
                .map(new Func1<List<String>, String>() {
                    @Override
                    public String call(List<String> strings) {
                        return strings.get(0);
                    }
                }).subscribe(new Action1<String>() {
            @Override
            public void call(String strings) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.unsubscribe();
        }
    }
}
