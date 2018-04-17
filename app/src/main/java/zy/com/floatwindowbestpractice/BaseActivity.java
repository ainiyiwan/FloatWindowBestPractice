package zy.com.floatwindowbestpractice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import zy.com.floatwindowbestpractice.util.RomUtil;

/**
 * ================================================
 * 作    者：Luffy（张阳）
 * 版    本：1.0
 * 创建日期：2018/4/16
 * 描    述：
 * 修订历史：
 * ================================================
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String TAG = "Float";
    private SuperCustomToast toast;
    private MySuperCustomToast customToast;
    private MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toast = SuperCustomToast.getInstance(getApplicationContext());
        customToast = MySuperCustomToast.getInstance(getApplicationContext());
        addToAppManager();
    }

    private void addToAppManager() {
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Will remove the activity from the Activity List when the current activity was destroy.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        List<EventBean> list = event.getList();
        if (RomUtil.isMIMU()) {
            Log.e(TAG, "小米设备");
            for (EventBean bean : list) {
                toast.showGoal(bean, getApplicationContext());
            }
        } else {
            Log.e(TAG, "不是小米设备");
//            View view = getLayoutInflater().inflate(R.layout.item_tips_recycler, null);
//            RecyclerView recyclerView = view.findViewById(R.id.recycler);
//            adapter = new MyAdapter(R.layout.item_tips_goal, list);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            recyclerView.setHasFixedSize(false);
//            recyclerView.setAdapter(adapter);
//            FloatWindow
//                    .with(getApplicationContext())
//                    .setView(view)
//                    .setWidth(Screen.width,1.0f)
//                    .setHeight(Screen.width,0.2f)
//                    .setX(Screen.width,0.0f)
//                    .setY(Screen.height,0.8f)
//                    .setTag("second")
//                    .setMoveType(MoveType.inactive)
//                    .setFilter(true, MainActivity.class, SecondActivity.class)
//                    .setDesktopShow(false)
//                    .build();
//            FloatWindow.get("second").show();

//            MiExToast miToast = new MiExToast(getApplicationContext());
//            miToast.setDuration(MiExToast.LENGTH_ALWAYS);
//            miToast.setAnimations(R.style.anim_view);
//            miToast.show();

            customToast.setOnItemClick(new MySuperCustomToast.OnItemClick() {
                @Override
                public void onCLick() {
                    boolean ishome= AppManager.isHomes(getApplicationContext());
                    Log.e("Filter", "ishome = " + ishome);
                    if (!ishome) {
                        Activity activity = AppManager.getAppManager().currentActivity();
                        String string = activity.getLocalClassName();
                        if (!string.equals("MainActivity")){
                            Intent intent  = new Intent(activity, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        Log.e("Filter", string);
                    }
                }
            });
            customToast.showGoal(list, getApplicationContext(), AppManager.getAppManager().currentActivity());

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_HOME == keyCode) {
            toast.hideToast();
            toast.mView.removeAllViews();
            toast.initView();

            customToast.hideToast();
            customToast.mView.removeAllViews();
            customToast.initView();
            return false;//同理
        }
        return super.onKeyDown(keyCode, event);
    }
}
