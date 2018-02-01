package rxjava.own.com.rxjavademo.simple4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import rxjava.own.com.rxjavademo.R;
import rxjava.own.com.rxjavademo.simple5.PublishSubject_demo;
import rxjava.own.com.rxjavademo.simple6.BehaviorSubject_demo;
import rxjava.own.com.rxjavademo.simple7.ReplaySubject_demo;
import rxjava.own.com.rxjavademo.simple8.AsyncSubject_demo;

/**
 * Created by tianyejun on 2018/2/1.
 */

public class SubjectActivity extends Activity {
    private Class clazz[]=new Class[]{PublishSubject_demo.class, BehaviorSubject_demo.class, ReplaySubject_demo.class, AsyncSubject_demo.class};
    private ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_net);
        listView = findViewById(R.id.listview);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adapter.addAll(new String[]{"PublishSubject", "BehaviorSubject", "ReplaySubject", "AsyncSubject"});
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
