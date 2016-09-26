package android.com.group18_hw03;

import android.content.Context;
import android.os.Handler;

/**
 * Created by murali101002 on 9/22/2016.
 */
public interface AccessUI {
//    public Handler handler = new Handler();
    public void showProgressDialog();
    public Context getContext();
    public void dismissProgressDialog();
    public Handler getHandler();
}
