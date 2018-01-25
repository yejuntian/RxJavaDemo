package rxjava.own.com.rxjavademo.simple1;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by tianyejun on 2018/1/25.
 */

public class RxJavaSimpleDemo1 {
    /**
     * Aciton含义
     *
     *Action0 --> void call();
     * Action1<T> -->void call(T t);
     * Action2<T1, T2>-->void call(T1 t1, T2 t2);
     * Action3<T1, T2, T3>-->void call(T1 t1, T2 t2, T3 t3);
     * ActionN --> void call(Object... args);
     * 总结：Action 有什么作用？---- 回调接口
     * 第一点：规定了返回的参数列表
     * 第二点：框架只负责回调，具体的参数列表由用户决定
     */

    /**
     * Function含义
     * Func0<R> -->R call();
     * Func1<T, R> --> R call(T t);
     * Func2<T1, T2, R> --> R call(T1 t1, T2 t2);
     * Func3<T1, T2, T3, R> --> R call(T1 t1, T2 t2, T3 t3);
     * FuncN<R> --> R call(Object... args);
     * <p>
     * 总结：Action 和 Fuction的区别
     * Action:是没有返回值的回调接口
     * Fuction:是有返回值的回调接口
     */
    public static void method1() {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {

            }
        });
    }
}
