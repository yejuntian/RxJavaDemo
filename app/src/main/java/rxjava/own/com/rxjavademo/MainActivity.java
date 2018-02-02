package rxjava.own.com.rxjavademo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import rxjava.own.com.rxjavademo.simple10.Rxjava_net;
import rxjava.own.com.rxjavademo.simple4.SubjectActivity;
import rxjava.own.com.rxjavademo.simple9.Rxjava_API;
import rxjava.own.com.rxjavademo.simple9.Rxjava_FilterAPI;
import rxjava.own.com.rxjavademo.simple9.Rxjava_Map;
import rxjava.own.com.rxjavademo.simple9.Rxjava_Merge;
import rxjava.own.com.rxjavademo.simple9.Rxjava_Scheduler;


public class MainActivity extends Activity {
    private ListView listView;
    private Class clazz[] = new Class[]{Rxjava_API.class, Rxjava_FilterAPI.class, Rxjava_Map.class, Rxjava_Merge.class, Rxjava_Scheduler.class, Rxjava_net.class, SubjectActivity.class};
    private String str[] = new String[]{"基本API", "过滤操作符", "转换操作", "组合操作", "线程调度", "Rxjava+Retrofit结合使用", "Subject主题"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adapter.addAll(str);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(position);
            }
        });
    }

    private void startActivity(int position) {
        Intent intent = new Intent(this, clazz[position]);
        startActivity(intent);
    }

}
