package vietnamworks.com.vietnamworksjobapp.models;

import android.content.Context;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    Job job1 = new Job();
                    job1.setJobTitle("aaa");

                    Job job2 = new Job();
                    job2.setJobTitle("bbb");

                    instance.data.clear();
                    instance.data.add(job1);
                    instance.data.add(job2);
                    callback.onCompleted(ctx, new CallbackSuccess());
                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        }).start();
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
}
