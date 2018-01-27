package rxjava.own.com.rxjavademo.simple9;

/**
 * Created by tianyejun on 2018/1/27.
 */

public class Order {
    private String orderId;
    private String goodName;

    public Order(String orderId, String goodName) {
        this.orderId = orderId;
        this.goodName = goodName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", goodName='" + goodName + '\'' +
                '}';
    }
}
