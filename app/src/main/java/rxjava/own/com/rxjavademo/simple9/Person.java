package rxjava.own.com.rxjavademo.simple9;

import java.util.List;

/**
 * Created by tianyejun on 2018/1/27.
 */

public class Person {
    private String name;
    private int age;
    private List<Order> orderList;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", orderList=" + orderList +
                '}';
    }
}
