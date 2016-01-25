package vietnamworks.com.vietnamworksjobapp.services;

import android.content.Context;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import R.helper.Callback;
import R.helper.CallbackResult;
import R.helper.Common;
import vietnamworks.com.vietnamworksjobapp.R;

/**
 * Created by duynk on 1/21/16.
 */
public class CloudinaryService {
    static Cloudinary cloudinary;
    public static void init(Context ctx) {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", ctx.getString(R.string.cloudinary_cloud_name));
        config.put("api_key", ctx.getString(R.string.cloudinary_api_key));
        config.put("api_secret", Common.r13(ctx.getString(R.string.cloudinary_api_secret)));
        cloudinary = new Cloudinary(config);
    }

    public static void uploadRaw(final Context context, final String input, final String public_name, final Callback callback) {
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    File file = new File(input);
                    if (file.exists() && file.isFile()) {
                        String publicName = public_name;
                        if (input.indexOf(".") > 0) {
                            String filenameArray[] = input.split("\\.");
                            String ext = filenameArray[filenameArray.length-1];
                            publicName = public_name + "." + ext;
                        };

                        FileInputStream fileInputStream = new FileInputStream(file);
                        Map m = cloudinary.uploader().uploadLarge(fileInputStream, ObjectUtils.asMap("folder", "vnw_job_app_cv", "public_id", publicName, "resource_type", "raw", "chunk_size", 6000000));
                        callback.onCompleted(context, CallbackResult.success(m));
                    } else {
                        callback.onCompleted(context, CallbackResult.error("Invalid input file"));
                    }
                } catch (Exception e) {
                    callback.onCompleted(context, CallbackResult.error(e.getMessage()));
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
