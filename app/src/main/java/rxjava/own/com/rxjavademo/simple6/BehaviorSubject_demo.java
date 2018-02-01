package rxjava.own.com.rxjavademo.simple6;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import rx.Observer;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rxjava.own.com.rxjavademo.R;

/**
 * BehaviorSubject
 */

public class BehaviorSubject_demo extends Activity implements View.OnClickListener {
    BehaviorSubject<String> behaviorSubject;
    private Button Button;
    private Button Button2;
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple2);
        Button = findViewById(R.id.button1);
        Button2 = findViewById(R.id.button2);
        editText = findViewById(R.id.edit_text);
        Button.setOnClickListener(this);
        Button2.setOnClickListener(this);

        //只要订阅就会默认发送一条消息，以后都是正常的
        behaviorSubject = BehaviorSubject.create("发送默认消息");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
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
                break;
            case R.id.button2:
                //需要自己手动发送（冷更新）
                behaviorSubject.onNext(editText.getText().toString());
                break;
        }
    }
}
