package rxjava.own.com.rxjavademo.simple2;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rxjava.own.com.rxjavademo.R;

/**
 * Created by tianyejun on 2018/1/25.
 */

public class RxJavaSimpleDemo2 extends Activity implements View.OnClickListener {
    Observable<Integer> observableString;
    private Button Button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple2);
        Button = findViewById(R.id.button);
        Button.setOnClickListener(this);
        //创建一个被观察者
        //配置回调接口
        // 为什么要配置--> 监听观察者订阅，一旦有观察者订阅立即回调该接口
        observableString = Observable

                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> observer) {
                        Log.e("test", "回调了");
                        //所以我们在这个方法里可以干一些事情

                        //进行通信（说白了就是通知观察者）
                        for (int i = 0; i < 5; i++) {
                            observer.onNext(i);
                        }
                        //当数据完成传递完成
                        observer.onCompleted();
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                Subscription subscriptionPrint = observableString.subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Observable completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Oh,no! Something wrong happened！");
                    }

                    @Override
                    public void onNext(Integer item) {
                        System.out.println("Item is " + item);
                    }
                });
                break;
        }
    }
}
