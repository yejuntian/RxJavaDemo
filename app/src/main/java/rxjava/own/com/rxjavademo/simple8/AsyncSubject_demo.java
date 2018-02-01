package rxjava.own.com.rxjavademo.simple8;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import rx.Observer;
import rx.subjects.AsyncSubject;
import rx.subjects.ReplaySubject;
import rxjava.own.com.rxjavademo.R;

/**
 * AsyncSubject
 * 总结：
 * 特点一：我们在发送消息的时候，调用了onNext()方法，必须还调用onCompleted()方法，
 * 才会将消息发送出去，否则观察者收不到任何消息
 * 特点二：采用了AsyncSubject创建并且注册了观察者，那么所有观察者，有且只能收到最新的一条消息
 *
 */

public class AsyncSubject_demo extends Activity implements View.OnClickListener {
    AsyncSubject<String> asyncSubject;
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
        asyncSubject = AsyncSubject.create();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                //先订阅消息
                asyncSubject.subscribe(new Observer<String>() {
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
                asyncSubject.onNext("hello");
                asyncSubject.onNext("word");
                asyncSubject.onNext("welcoming");
                asyncSubject.onNext("北京");
                //必须调用onCompleted方法 消息才会被回调响应
                asyncSubject.onCompleted();
                break;
        }
    }
}
