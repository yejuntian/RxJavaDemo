package rxjava.own.com.rxjavademo.service.entity;

import android.util.Log;


//    {
//        "status": 1,
//        "content": {
//        "from": "en-EU",
//        "to": "zh-CN",
//        "out": "示例",
//        "vendor": "ciba",
//        "err_no": 0
//        }
//    }

public class Translation {
    public int status;

    private content content;

    private static class content {
        public String from;
        public String to;
        public String vendor;
        public String out;
        public int errNo;

        @Override
        public String toString() {
            return "content{" +
                    "from='" + from + '\'' +
                    ", to='" + to + '\'' +
                    ", vendor='" + vendor + '\'' +
                    ", out='" + out + '\'' +
                    ", errNo=" + errNo +
                    '}';
        }
    }

    //定义 输出返回数据 的方法
    public void show() {
        Log.d("RxJava", content.out);
    }

    @Override
    public String toString() {
        return "Translation{" +
                "status=" + status +
                ", content=" + content +
                '}';
    }
}
