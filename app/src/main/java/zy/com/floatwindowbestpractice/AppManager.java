package zy.com.floatwindowbestpractice;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * @createdate 2014年12月19日
 * @description App管理器，用于管理所有的activity 使用时注意堆栈后进先出原则
 */
public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后压入的），可能为null
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (activityStack != null && activityStack.size() > 0) {
            try {
                activity = activityStack.lastElement();
            } catch (NoSuchElementException e) {
                activity = null;
            }
        }
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后压入的）
     */
    public void finishActivity() {
        Activity activity = null;
        if (activityStack != null && activityStack.size() > 0) {
            try {
                activity = activityStack.lastElement();
            } catch (NoSuchElementException e) {
                activity = null;
            }
        }
        finishActivity(activity);
    }

    public void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            removeActivity(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        Activity removeActivity = null;
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                removeActivity = activity;
                break;
            }
        }
        if (removeActivity != null) {
            finishActivity(removeActivity);
        }
    }

    /**
     * 得到指定的Activity
     *
     * @param cls
     */
    public Activity getActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 结束所有的
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 除传入的类以外， 其余类finish
     */
    public void finishActivityExcept(Class clazz) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i) && !activityStack.get(i).getClass().equals(clazz)) {
                activityStack.get(i).finish();
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回app运行状态
     * 1:程序在前台运行
     * 2:程序在后台运行
     * 3:程序未启动
     * 注意：需要配置权限<uses-permission android:name="android.permission.GET_TASKS" />
     */
    public int getAppSatus(Context context, String pageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(20);
        //判断程序是否在栈顶
        if (list.get(0).topActivity.getPackageName().equals(pageName)) {
            return 1;
        } else {
            //判断程序是否在栈里
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.topActivity.getPackageName().equals(pageName)) {
                    return 2;
                }
            }
            return 3;//栈里找不到，返回3
        }
    }

    public static boolean isHomes(Context context) {
        try {
            ActivityManager mActivityManager = (ActivityManager)context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
            List<String> strs = getHomes(context);
            if(strs != null && strs.size() > 0 && rti != null && rti.size() > 0){
                return strs.contains(rti.get(0).topActivity.getPackageName());
            }else{
                return false;
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获得属于桌面的应用的应用包名称
     * @return 返回包含所有包名的字符串列表
     */
    public static List<String> getHomes(Context context) {
        List<String> names = new ArrayList<String>();
        try {
            PackageManager packageManager = context.getPackageManager();
            //属性
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                    PackageManager.MATCH_DEFAULT_ONLY);
            for(ResolveInfo ri : resolveInfo){
                names.add(ri.activityInfo.packageName);
            }
            return names;
        } catch (Exception e) {
            e.printStackTrace();
            return names;
        }
    }
}