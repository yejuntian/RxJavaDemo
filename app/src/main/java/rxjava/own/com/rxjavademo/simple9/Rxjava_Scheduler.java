package rxjava.own.com.rxjavademo.simple9;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rxjava.own.com.rxjavademo.R;


public class Rxjava_Scheduler extends Activity implements View.OnClickListener {

    private Observable<String> observable;
    private ImageView image;
    private Button button1;
    private String downLoadUrl = "http://pic4.nipic.com/20091217/3885730_124701000519_2.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);
        image = findViewById(R.id.image);
        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
//                downLoadImage();
                rxJavaDownLoader();
                break;
        }

    }

    /**
     * 默认加载图片
     */
    private void downLoadImage() {
        new DownLoadImage().execute();
    }

    /**
     * subscribeOn和observeOn的区别
     * 区别：一、
     * subscribeOn:指定生产事件所在的线程
     * observeOn：指定消费事件所在的线程
     * 区别：二
     * subscribeOn:按照顺序执行序列，作用于他前后的序列直到遇到了observeOn才切换新的线程
     * observeOn：按照顺序执行序列，只能作用于它之后的序列
     * <p>
     * 注意：先指定生产线程再指定消费线程，否则会报错。
     */
    public void rxJavaDownLoader() {
        Observable.just(downLoadUrl)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String url) {
                        return downLoadUrl(url);
                    }
                })
                .map(new Func1<Bitmap, Bitmap>() {
                    @Override
                    public Bitmap call(Bitmap bitmap) {
                        //
                        return bitmap;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //在执行生产事件之前，我们需要做一些初始化工作，而这个工作可以在子线程，也可以在主线程
                        //更新UI必须在主线程
                        image.setVisibility(View.VISIBLE);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        image.setImageBitmap(bitmap);
                    }
                });
    }

    class DownLoadImage extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... voids) {
            return downLoadUrl(downLoadUrl);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            image.setImageBitmap(bitmap);
        }

    }

    private Bitmap downLoadUrl(String downLoadUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(downLoadUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedInputStream buf = new BufferedInputStream(inputStream);
            bitmap = BitmapFactory.decodeStream(buf);
            inputStream.close();
            buf.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
