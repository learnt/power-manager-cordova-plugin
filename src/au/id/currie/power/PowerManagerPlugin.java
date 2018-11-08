package au.id.currie.power;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

public class PowerManagerPlugin extends CordovaPlugin {

    public static final String TAG = "CordovaPowerManager";
    private PowerManager.WakeLock lock;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        PowerManager powerService = (PowerManager) cordova.getActivity().getSystemService(Context.POWER_SERVICE);
        lock = powerService.newWakeLock(PowerManager.FULL_WAKE_LOCK, "cordova plugin lock");
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("acquire") && !lock.isHeld()) {
            Log.d(TAG, "acquire");
            lock.acquire();
            return true;
        }
        if (action.equals("release")) {
            release();
        }
        return false;
    }

    @Override
    public void onPause(boolean multitasking) {
        // don't want to release on when screen turned off manually
        // perhaps want to release when switching to another app
//        release();
    }

    @Override
    public void onDestroy() {
        release();
    }

    private void release() {
        Log.d(TAG, "release");
        if (lock.isHeld()) {
            lock.release();
        }
    }
}
