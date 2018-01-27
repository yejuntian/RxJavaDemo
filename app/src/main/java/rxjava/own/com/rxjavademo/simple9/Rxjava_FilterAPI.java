package rxjava.own.com.rxjavademo.simple9;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rxjava.own.com.rxjavademo.R;


public class Rxjava_FilterAPI extends Activity implements View.OnClickListener {

    private Observable<String> observable;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button1);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
//                subscribeType();
//                filter();
//                take();
//                takeLast();
//                distinct1();
//                distinct2();
//                first();
//                last();
//                skip();
//                skipLast();
//                elementAt();
//                elementAtOrDefault();
//                sample();
                break;
        }

    }

    /**
     * 订阅消息方式
     * 1，完整回调
     * observable.subscribe(subscriber)
     * observable.subscribe(onNext,onError,onComplete)
     * 2，不完整回调
     * observable.subscribe(onNext)
     * observable.subscribe(onNext,onError)
     */
    public void subscribeType() {
        observable = Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("13");
                subscriber.onCompleted();

            }
        });

        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        });
    }

    /**
     * 过滤器
     */
    public void filter() {
        String str[] = new String[]{"hello", "123", "2343"};
        Observable.from(str).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return !s.equals("hello");
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("test", "s = " + s);
            }
        });
    }

    /**
     * 当我们不需要整个序列的时候，而是想取前开头或者结尾几个元素
     * 注意：take(count) count不能小于0
     */
    public void take() {
        String str[] = new String[]{"hello", "123", "2343", "32324", "fdafda"};
        Observable.from(str).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return !s.equals("123");
            }
        }).take(3).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("test", "s = " + s);
            }
        });
    }

    /**
     * 获取最后N条数据
     * 注意：takeLast(count) count不能小于0
     */
    public void takeLast() {
        String str[] = new String[]{"hello", "123", "2343", "32324", "fdafda"};
        Observable.from(str).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return !s.equals("fdafda");
            }
        }).takeLast(3).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("test", "s = " + s);
            }
        });
    }

    /**
     * 过滤重复数据
     * distinct只能区别基本类型的重复
     */
    public void distinct1() {
        String str2[] = new String[]{"hello", "123", "123", "32324", "32324"};
        Observable.from(str2).distinct().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("test", "s = " + s);
            }
        });
    }

    /**
     * 自定义过滤去除重复数据
     * distinct(Func1)参数中的Func1中的call方法会根据Observable发射的值生成一个Key，
     * 然后比较这个key来判断两个数据是不是相同
     */
    public void distinct2() {
        List<Person> list = new ArrayList<>();
        list.add(new Person("张三", 20));
        list.add(new Person("张三", 20));

        list.add(new Person("李四", 20));
        list.add(new Person("李四", 20));

        list.add(new Person("赵六", 20));
        list.add(new Person("赵六", 21));


        Observable.from(list).distinct(new Func1<Person, Object>() {
            @Override
            public Object call(Person person) {
                return person.getAge() + person.getName();
            }
        }).subscribe(new Action1<Person>() {
            @Override
            public void call(Person s) {
                Log.e("test", "s = " + s);
            }
        });
    }

    /**
     *只发送序列中的第一个数据项
     * 其实内部调用的take(1)方法
     */
    public void first(){
        Observable.from(new String[]{"hello","word"}).first().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("test","s = "+s);
            }
        });
    }

    /**
     *只发送序列中的最后一个数据项
     * 其实内部调用的takeLast方法
     */
    public void last(){
        Observable.from(new String[]{"hello","word"}).last().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("test","s = "+s);
            }
        });
    }

    /**
     * 从头开始，跳过多少个，然后再发送
     */
    public void skip(){
        Observable.from(new String[]{"hello","word","xiaohong"}).skip(1).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("test","s = "+s);
            }
        });
    }

    /**
     * 最后面的几个不需要，然后再发送
     */
    public void skipLast(){
        Observable.from(new String[]{"hello","word","xiaohong"}).skipLast(1).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("test","s = "+s);
            }
        });
    }

    /**
     * 发射的事件序列中的第n项数据,下标从0开始
     */
    public void elementAt(){
        Observable.from(new String[]{"hello","word","xiaohong"}).elementAt(1).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("test","s = "+s);
            }
        });
    }

    /**
     * 发送执行的消息，如果不存在发送默认值
     */
    public void elementAtOrDefault(){
        Observable.from(new String[]{"hello","word","xiaohong"}).elementAtOrDefault(9,"平凡的世界").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("test","s = "+s);
            }
        });
    }


    /**
     * 指定时间范围内，发送最新的一条数据
     */
    public void sample(){
        Observable.from(new String[]{"hello","word","xiaohong"}).sample(9,TimeUnit.SECONDS).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("test","s = "+s);
            }
        });
    }
}
