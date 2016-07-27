package z.messagedeliverlibrary;

import android.os.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 2016/7/16.
 */
public class MessageDeliver {
    private static List<OnHandleListener> list = new ArrayList<>();
    private static android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            for (OnHandleListener listener : list)
                listener.onHandle(msg.what, msg.obj);
        }
    };

    public static void sendMessage(int type, Object msg, long delay) {
        Message message = new Message();
        message.obj = msg;
        message.what = type;
        mHandler.sendMessageDelayed(message, delay);
    }

    public interface OnHandleListener {
        void onHandle(int type, Object obj);
    }

    public static void addOnHandleListener(OnHandleListener listener) {
        list.add(listener);
    }

    public static void removeOnHandleListener(OnHandleListener listener) {
        if (listener != null && list.contains(listener))
            list.remove(listener);
    }
}
