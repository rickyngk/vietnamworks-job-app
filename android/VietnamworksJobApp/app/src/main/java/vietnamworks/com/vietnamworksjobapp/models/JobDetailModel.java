package vietnamworks.com.vietnamworksjobapp.models;

import android.content.Context;

import R.helper.Callback;
import vietnamworks.com.vnwcore.entities.Job;

/**
 * Created by duynk on 1/13/16.
 */
public class JobDetailModel {
    public static void load(Context ctx, String id, final Callback callback) {
        Job.loadById(ctx, id, callback);
    }
}
