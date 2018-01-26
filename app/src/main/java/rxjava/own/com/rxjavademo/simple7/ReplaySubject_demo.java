package rxjava.own.com.rxjavademo.simple7;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import rx.Observer;
import rx.subjects.BehaviorSubject;
import rx.subjects.ReplaySubject;
import rxjava.own.com.rxjavademo.R;

/**
 * ReplaySubject
 * 1，缓存所有的消息，
 * 2，一旦有新的观察者订阅了，会立马将缓存的消息发送对应的观察者
 *
 * 好处：不会错过任何消息，保证所有订阅我的观察者都能够收到之前的消息
 * 注意：缓存的数据量可以指定 默认缓存16个
 *
 * 核心缓存数据原理：
 * 通过ArrayList进行缓存数据
 */

public class ReplaySubject_demo extends Activity implements View.OnClickListener {
    ReplaySubject<String> replaySubject;
    private Button Button1;
    private Button Button2;
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple2);
        Button1 = findViewById(R.id.button1);
        Button2 = findViewById(R.id.button2);
        editText = findViewById(R.id.edit_text);
        Button1.setOnClickListener(this);
        Button2.setOnClickListener(this);

        //1，缓存所有的消息，一旦有新的观察者订阅了，会立马将缓存的消息发送对应的观察者
        replaySubject = ReplaySubject.create();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                //先订阅消息
                replaySubject.subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        Log.e("test", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String object) {
                        Log.e("test", String.valueOf(object));
                    }
                });
                break;
            case R.id.button2:
                //需要自己手动发送（冷更新）
                replaySubject.onNext(editText.getText().toString());
                break;
        }
    }
}
