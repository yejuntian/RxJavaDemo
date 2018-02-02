package rxjava.own.com.rxjavademo.simple9;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rxjava.own.com.rxjavademo.R;


public class Rxjava_Merge extends Activity implements View.OnClickListener {

    private Observable<String> observable;
    private Button merge, mergeDelayError, mergeDelayErrorOk, zip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge);
        merge = findViewById(R.id.merge);
        mergeDelayError = findViewById(R.id.mergeDelayError);
        mergeDelayErrorOk = findViewById(R.id.mergeDelayErrorOk);
        zip = findViewById(R.id.zip);


        merge.setOnClickListener(this);
        mergeDelayError.setOnClickListener(this);
        mergeDelayErrorOk.setOnClickListener(this);
        zip.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.merge:
                merge();
                break;
            case R.id.mergeDelayError:
                mergeDelayError();
                break;
            case R.id.mergeDelayErrorOk:
                mergeDelayErrorOk();
                break;
            case R.id.zip:
                zip();
                break;
        }

    }



    /**
     * 多个输入一个输出
     */
    public void merge() {
        String str1[] = new String[]{"1", "12", "123"};
        String str2[] = new String[]{"hello", "word", "liming"};

        Observable.merge(Observable.from(str1), Observable.from(str2))
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("test", "s = " + s);
                    }
                });
    }

    /**
     * 错误的方式
     * 在Observable中，一旦某个事件抛出异常，后面的序列将会终止
     * 注意：mergeDelayError如果你希望在序列出错的时候，不影响后面的序列
     * 可以使用mergeDelayError方法
     */
    public void mergeDelayError() {
        String str2[] = new String[]{"hello", "word", "liming"};
        Observable.mergeDelayError(Observable.error(new Exception("错误")), Observable.from(str2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onCompleted() {
                        Log.e("test", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("test", "e = " + e.toString());
                    }

                    @Override
                    public void onNext(Object o) {
                        Log.e("test", "Object = " + (String) o);
                    }
                });
    }

    /**
     * 正确的方式
     * 发现这样先mergeDelayError再指定线程的话，
     * mergeDelayError没有起到延迟通知onError的作用，
     * 第一个observable出现错误的时候，整个合并的observable也onError了，第二个observable无法输出。
     * 但是如果改成每个observable单独subscribeOn和observeOn，然后再mergeDelayError，就正常进行了
     */
    public void mergeDelayErrorOk() {
        String str2[] = new String[]{"hello", "word", "liming"};
        Observable.mergeDelayError(Observable.error(new Exception("错误"))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                , Observable.from(str2)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
        ).subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {
                Log.e("test", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("test", "e = " + e.toString());
            }

            @Override
            public void onNext(Object o) {
                Log.e("test", "Object = " + (String) o);
            }
        });
    }

    /**
     * 第一点：合并算法自己决定
     * 第二点：序列长度由原始的最小数组长度决定
     * （例如数组1长度是4 数组2长度是3 最终新的序列长度是3）
     */
    public void zip() {
        String str1[] = new String[]{"1", "2", "3", "4"};
        String str2[] = new String[]{"hello", "word", "xiaohong"};

        Observable.zip(Observable.from(str1), Observable.from(str2), new Func2<String, String, String>() {
            @Override
            public String call(String s, String s2) {
                return s + "_" + s2;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("test", "s = " + s);
            }
        });
    }
}
