package rxjava.own.com.rxjavademo.simple5;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import rx.Observable;
import rx.Observer;
import rx.subjects.PublishSubject;
import rxjava.own.com.rxjavademo.R;

/**
 * PublishSubject
 */

public class RxJavaSimpleDemo5 extends Activity implements View.OnClickListener {
    PublishSubject<String> publishSubject;
    private Button Button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple2);
        Button = findViewById(R.id.button);
        Button.setOnClickListener(this);

        publishSubject = PublishSubject.create();
        publishSubject.subscribe(new Observer<String>() {
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
                publishSubject.onNext("123");
                break;
        }
    }
}
