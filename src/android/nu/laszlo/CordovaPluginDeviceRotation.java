/**
 */
package nu.laszlo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

//import android.support.v7.app.AppCompatActivity;
import android.content.Context;

public class CordovaPluginDeviceRotation extends CordovaPlugin {
    private static final String TAG = "CordovaPluginDeviceRotation";
    SensorManager sensorManager;
    Sensor accelerometerSensor;
    boolean accelerometerPresent;
    String orientation;
    List<CallbackContext> orientationChangeListeners;

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        Log.d(TAG, "Initializing CordovaPluginDeviceRotation");

        this.orientation = "unknown";
        Context context = this.cordova.getActivity().getApplicationContext();

        orientationChangeListeners = new ArrayList();

        sensorManager = (SensorManager)context.getSystemService(context.SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(sensorList.size() > 0){
            accelerometerPresent = true;
            accelerometerSensor = sensorList.get(0);
        }
        else{
            accelerometerPresent = false;
        }

        if(accelerometerPresent){
            sensorManager.registerListener(accelerometerListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if(action.equals("getOrientation")) {
            //String phrase = args.getString(0);
            callbackContext.success(orientation);
        } else if (action.equals("onOrientationChange")) {
            callbackContext.sendPluginResult(result(orientation)); // Always send the current orientation
            this.orientationChangeListeners.add(callbackContext);
        }
        return true;
    }

    private void notifyListeners(String newOrientation) {
        for (CallbackContext callbackContext: this.orientationChangeListeners) {
            callbackContext.sendPluginResult(result(orientation));
        }
    }

    private PluginResult result(String msg) {
        PluginResult res = new PluginResult(PluginResult.Status.OK, msg);
        res.setKeepCallback(true);
        return res;
    }

    private SensorEventListener accelerometerListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            String oldOrientation = orientation;
            float z_value = sensorEvent.values[2];
            if (z_value >= 0) {
                orientation = "up";
            } else {
                orientation = "down";
            }
            if (!oldOrientation.equals(orientation)) {
                // Only notify when orientation changes
                notifyListeners(orientation);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {
            // TODO: use this?
        }

    };

}
