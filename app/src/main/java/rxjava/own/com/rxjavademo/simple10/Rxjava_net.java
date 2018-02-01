package rxjava.own.com.rxjavademo.simple10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import rxjava.own.com.rxjavademo.R;


public class Rxjava_net extends Activity {

    private ListView listView;
    private Class[] clazz = new Class[]{NetworkPollingActivity.class,NetworkConditionPollingActivity.class, InterfaceNestingActivity.class, interfaceRetryActivity.class};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        listView = findViewById(R.id.listview);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adapter.addAll(new String[]{"（无条件）网络请求轮询", "（有条件）网络请求轮询", "网络请求嵌套回调", "接口重试"});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(position);
            }

        });
    }

    private void startActivity(int position) {
        Intent intent = new Intent(this,clazz[position]);
        startActivity(intent);
    }


}
