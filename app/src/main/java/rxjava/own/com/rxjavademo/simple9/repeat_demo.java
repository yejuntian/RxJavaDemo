package rxjava.own.com.rxjavademo.simple9;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

import rx.Observable;
import rx.functions.Action1;
import rx.subjects.AsyncSubject;
import rxjava.own.com.rxjavademo.R;


public class repeat_demo extends Activity implements View.OnClickListener {
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


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:

                break;
            case R.id.button2:
                Observable.just("hello word",123).repeat(3).subscribe(new Action1<Serializable>() {
                    @Override
                    public void call(Serializable serializable) {
                        Log.e("test", String.valueOf(serializable));
                    }
                });
                break;
        }
    }
}
