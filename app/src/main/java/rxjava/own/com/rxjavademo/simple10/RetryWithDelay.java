package rxjava.own.com.rxjavademo.simple10;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by tianyejun on 2018/2/1.
 */


public class RetryWithDelay implements Func1<Observable<? extends Throwable>, Observable<?>> {

    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;

    public RetryWithDelay(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> attempts) {
        return attempts.flatMap(new Func1<Throwable, Observable<?>>() {
            @Override
            public Observable<?> call(Throwable throwable) {
                if (++retryCount <= maxRetries) {
                    Log.e("test", "将要在 " + retryDelayMillis+ " 秒后重试, 重试次数为： " + retryCount);
                    return Observable.timer(retryDelayMillis, TimeUnit.SECONDS);
                }
                return Observable.error(throwable);
            }
        });
    }
}
