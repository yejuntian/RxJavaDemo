package rxjava.own.com.rxjavademo.simple3;

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

public class RxJavaSimpleDemo3 extends Activity implements View.OnClickListener {
    Observable<Integer> observable;
    private Button Button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple2);
        Button = findViewById(R.id.button1);
        Button.setOnClickListener(this);

        List<Integer> items = new ArrayList<Integer>();
        items.add(1);
        items.add(10);
        items.add(100);
        items.add(200);
        //框架本身提供了API
        //from:一旦当你有观察者注册，立马发送消息队列
        observable = Observable.from(items);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                observable.subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e("test", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e("test", String.valueOf(integer));
                    }
                });
                break;
        }
    }
}
