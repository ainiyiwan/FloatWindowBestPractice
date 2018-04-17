package zy.com.floatwindowbestpractice.util;

import android.os.Build;

/**
 * ================================================
 * 作    者：Luffy（张阳）
 * 版    本：1.0
 * 创建日期：2018/4/16
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class RomUtil {
    public static boolean isMIMU() {
        return Build.MANUFACTURER.equals("Xiaomi");
    }
}
