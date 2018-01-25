package rxjava.own.com.rxjavademo.simple6;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import rx.Observer;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rxjava.own.com.rxjavademo.R;

/**
 * PublishSubject
 */

public class RxJavaSimpleDemo6 extends Activity implements View.OnClickListener {
    BehaviorSubject<String> behaviorSubject;
    private Button Button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple2);
        Button = findViewById(R.id.button);
        Button.setOnClickListener(this);

        //只要订阅就会默认发送一条消息，以后都是正常的
        behaviorSubject = BehaviorSubject.create("发送默认消息");
        behaviorSubject.subscribe(new Observer<String>() {
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                //需要自己手动发送（冷更新）
                behaviorSubject.onNext("123");
                break;
        }
    }
}
