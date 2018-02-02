package rxjava.own.com.rxjavademo.simple9;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rxjava.own.com.rxjavademo.R;


public class Rxjava_Map extends Activity implements View.OnClickListener {

    private Observable<String> observable;
    private Button flatMap, map, flatMapIterable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        map = findViewById(R.id.map);
        flatMap = findViewById(R.id.flatMap);
        flatMapIterable = findViewById(R.id.flatMapIterable);

        map.setOnClickListener(this);
        flatMap.setOnClickListener(this);
        flatMapIterable.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map:
                map();
                break;
            case R.id.flatMap:
                //map1();
                flatMap();
                break;
            case R.id.flatMapIterable:
                flatMapIterable();
                break;
        }

    }

    /**
     * Func1构造函数中的两个参数
     * 参数一：Observable发射值当前的类型
     * 参数二：map转换后的类型
     */
    public void map() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("张三", 20));
        personList.add(new Person("张三1", 20));
        personList.add(new Person("张三2", 20));

        Observable.from(personList).map(new Func1<Person, String>() {
            @Override
            public String call(Person person) {
                return person.getName();
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("test", "姓名：" + s);
            }
        });
    }

    /**
     * Func1构造函数中的两个参数
     * 参数一：Observable发射值当前的类型
     * 参数二：map转换后的类型
     */
    public void map1() {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Order> orderList = new ArrayList<>();
            Person person = new Person("张三", 20);
            for (int j = 0; j < 2; j++) {
                Order order = new Order("张三" + i + " 订单号 :" + j, " 商品名称 :" + j);
                orderList.add(order);
            }
            person.setOrderList(orderList);
            personList.add(person);
        }


        Observable.from(personList).map(new Func1<Person, List<Order>>() {
            @Override
            public List<Order> call(Person person) {
                return person.getOrderList();
            }
        }).subscribe(new Action1<List<Order>>() {
            @Override
            public void call(List<Order> orderList) {
                for (Order order : orderList) {
                    Log.e("test", order.getOrderId() + order.getGoodName());

                }
            }
        });
    }

    /**
     * 应用场景:解决接口嵌套问题（例如授权成功之后，登录场景）
     */
    public void flatMap() {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Order> orderList = new ArrayList<>();
            Person person = new Person("张三", 20);
            for (int j = 0; j < 2; j++) {
                Order order = new Order("张三" + i + " 订单号 :" + j, " 商品名称 :" + j);
                orderList.add(order);
            }
            person.setOrderList(orderList);
            personList.add(person);
        }

        Observable.from(personList).flatMap(new Func1<Person, Observable<Order>>() {
            @Override
            public Observable<Order> call(Person person) {
                return Observable.from(person.getOrderList());
            }
        }).subscribe(new Action1<Order>() {
            @Override
            public void call(Order order) {
                Log.e("test", order.getOrderId() + order.getGoodName());
            }
        });
    }

    public void flatMapIterable() {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Order> orderList = new ArrayList<>();
            Person person = new Person("张三", 20);
            for (int j = 0; j < 2; j++) {
                Order order = new Order("张三" + i + " 订单号 :" + j, " 商品名称 :" + j);
                orderList.add(order);
            }
            person.setOrderList(orderList);
            personList.add(person);
        }

        Observable.from(personList).flatMapIterable(new Func1<Person, Iterable<Order>>() {
            @Override
            public Iterable<Order> call(Person person) {
                return person.getOrderList();
            }
        }).subscribe(new Action1<Order>() {
            @Override
            public void call(Order order) {
                Log.e("test", order.getOrderId() + order.getGoodName());
            }
        });
    }


}
