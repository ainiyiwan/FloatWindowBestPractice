package zy.com.floatwindowbestpractice;

import java.util.List;

/**
 * ================================================
 * 作    者：Luffy（张阳）
 * 版    本：1.0
 * 创建日期：2018/4/16
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class MessageEvent {

    private List<EventBean> list;

    public MessageEvent(List<EventBean> list) {
        this.list = list;
    }

    public List<EventBean> getList() {
        return list;
    }

    public void setList(List<EventBean> list) {
        this.list = list;
    }
}
