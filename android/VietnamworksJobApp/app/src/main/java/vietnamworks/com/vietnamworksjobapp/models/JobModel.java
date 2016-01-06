package vietnamworks.com.vietnamworksjobapp.models;

import android.content.Context;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

import vietnamworks.com.helper.BaseActivity;
import vietnamworks.com.helper.Callback;
import vietnamworks.com.helper.CallbackSuccess;
import vietnamworks.com.vietnamworksjobapp.entities.Job;

/**
 * Created by duynk on 1/5/16.
 */
public class JobModel {
    static JobModel instance =  new JobModel();
    ArrayList<Job> data = new ArrayList<>();

    public static void load(final Context ctx, final Callback callback) {
        BaseActivity.timeout(new Runnable() {
            @Override
            public void run() {
                int n = 5;
                instance.data.clear();
                for (int i = 0; i < n; i++) {
                    Job job = new Job();
                    job.setJobTitle("JOB " + (i + 1));
                    instance.data.add(job);

                }
                callback.onCompleted(ctx, new CallbackSuccess());
            }
        }, 3000);
    }

    public static JobModel getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        ArrayList<HashMap> a = new ArrayList<>();
        for (Job j:data) {
            a.add(j.exportToHashMap());
        }
        JSONArray array = new JSONArray(a);
        return array.toString();
    }

    public static int count() {
        return instance.data.size();
    }

    public static Job get(int index) {
        return instance.data.get(index);
    }
}
