package rxjava.own.com.rxjavademo.simple5;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rxjava.own.com.rxjavademo.R;

/**
 * PublishSubject
 */

public class PublishSubject_demo extends Activity implements View.OnClickListener {
    PublishSubject<String> publishSubject;
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

        publishSubject = PublishSubject.create();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                //需要自己手动发送（冷更新）
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
                break;
            case R.id.button2:
                publishSubject.onNext(editText.getText().toString());
                //调用onCompleted（）事件函数后，以后只回调onCompleted（）方法
//                publishSubject.onCompleted();
                break;
        }
    }
}
