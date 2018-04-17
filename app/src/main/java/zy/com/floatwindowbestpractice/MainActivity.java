package zy.com.floatwindowbestpractice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private List<EventBean> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
    }

    private void initData() {
        for (int i = 0; i < 1; i++) {
            list.add(new EventBean("主队", "客队"));
        }
    }


    /**
     * 跳转
     * @param view
     */
    public void onNext(View view) {
        startActivity(new Intent(MainActivity.this, SecondActivity.class));
    }

    /**
     * 发送消息
     * @param view
     */
    public void onSendMessage(View view) {
        EventBus.getDefault().post(new MessageEvent(list));
    }

}
