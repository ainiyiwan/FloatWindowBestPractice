package zy.com.floatwindowbestpractice;

import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends BaseActivity {

    private List<EventBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 1; i++) {
            list.add(new EventBean("主队", "客队"));
        }
    }

    /**
     * 跳转到上一个界面
     * @param view
     */
    public void onPre(View view) {
        finish();
    }

    /**
     * 发送消息
     * @param view
     */
    public void onSendMessage(View view) {
        EventBus.getDefault().post(new MessageEvent(list));
    }
}
