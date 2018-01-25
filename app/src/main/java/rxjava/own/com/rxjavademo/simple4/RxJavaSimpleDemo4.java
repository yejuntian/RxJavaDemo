package rxjava.own.com.rxjavademo.simple4;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rxjava.own.com.rxjavademo.R;

/**
 * Created by tianyejun on 2018/1/25.
 */

public class RxJavaSimpleDemo4 extends Activity implements View.OnClickListener {
    Observable<String> observable;
    private Button Button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple2);
        Button = findViewById(R.id.button);
        Button.setOnClickListener(this);

        //传统的Java函数，转变为一个Observable；然后依次发送数据
        observable = Observable.just("hello word","1232","32432");

        //使用该方法,那么观察者接口中只回调onComplete方法
        observable = Observable.empty();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                observable.subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        Log.e("test", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String integer) {
                        Log.e("test", String.valueOf(integer));
                    }
                });
                break;
        }
    }
}
