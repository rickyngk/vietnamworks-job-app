package vietnamworks.com.vietnamworksjobapp.activities.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nononsenseapps.filepicker.FilePickerActivity;

import java.io.File;

import R.helper.BaseActivity;
import R.helper.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vietnamworksjobapp.activities.main.MainActivity;
import vietnamworks.com.vietnamworksjobapp.services.ShareContext;
import vietnamworks.com.vnwcore.Auth;
import vietnamworks.com.vnwcore.entities.JobApplyForm;

/**
 * Created by duynk on 1/21/16.
 */
public class UploadCVFragment extends BaseFragment {

    @Bind(R.id.btn_cancel_upload_cv)
    Button btnCancelUploadCV;

    @Bind(R.id.btn_confirm_cv)
    Button btnConfirmCV;

    @Bind(R.id.btn_upload_from_dropbox)
    Button btnUploadFromDropBox;

    @Bind(R.id.btn_upload_from_sdcard)
    Button btnUploadFromSDCard;

    @Bind(R.id.upload_options)
    RadioGroup uploadOptions;

    @Bind(R.id.opt_use_current_cv)
    RadioButton optUseCurrentCV;

    @Bind(R.id.opt_use_new_cv)
    RadioButton optUseNewCV;

    @Bind(R.id.file_name)
    TextView fileName;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_upload_cv, container, false);
        ButterKnife.bind(this, rootView);
        BaseActivity.hideActionBar();

        btnCancelUploadCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.popFragment();
                BaseActivity.hideKeyboard();
            }
        });

        btnConfirmCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.hideKeyboard();
                JobApplyForm jf = (JobApplyForm) ShareContext.get(ShareContext.SELECTED_JOB);
                if (optUseNewCV.isChecked()) {
                    jf.setResumeAttachId(null);
                } else {
                    jf.setFileContents(null);
                    jf.setResumeAttachId(Auth.getAuthData().getAttachmentResumeId());
                }

                boolean hasUploadFile = jf.getFileContents() != null && !jf.getFileContents().isEmpty();
                boolean useOld = jf.getResumeAttachId() != null;
                if (!hasUploadFile && ! useOld) {
                    BaseActivity.toast(R.string.cv_is_required);
                } else {
                    BaseActivity.replaceFragment(new CoverLetterFragment(), R.id.fragment_holder);
                }
            }
        });


        if (Auth.getAuthData().getAttachmentResumeId() == null || Auth.getAuthData().getAttachmentResumeId().isEmpty()) {
            btnUploadFromDropBox.setEnabled(true);
            btnUploadFromSDCard.setEnabled(true);
            optUseCurrentCV.setEnabled(false);
            optUseNewCV.setChecked(true);
        } else {
            btnUploadFromDropBox.setEnabled(false);
            btnUploadFromSDCard.setEnabled(false);
            optUseCurrentCV.setChecked(true);
        }

        uploadOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.opt_use_current_cv) {
                    btnUploadFromDropBox.setEnabled(false);
                    btnUploadFromSDCard.setEnabled(false);
                } else {
                    btnUploadFromDropBox.setEnabled(true);
                    btnUploadFromSDCard.setEnabled(true);
                }
            }
        });

        btnUploadFromSDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), FilePickerActivity.class);
                // This works if you defined the intent filter
                // Intent i = new Intent(Intent.ACTION_GET_CONTENT);

                // Set these depending on your use case. These are the defaults.
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
                i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

                // Configure initial directory by specifying a String.
                // You could specify a String like "/storage/emulated/0/", but that can
                // dangerous. Always use Android's API calls to get paths to the SD-card or
                // internal memory.
                i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

                getActivity().startActivityForResult(i, MainActivity.PICK_FILE_REQUEST_CODE);
            }
        });

        fileName.setText("");

        return rootView;
    }

    public void onPickedFile(String path) {
        if (path == null || path.isEmpty()) {
            //TODO: user cancel uploadRaw
        } else {
            File f = new File(path);
            if (f.exists() && f.isFile()) {
                if (f.length() > 512*1024) {
                    BaseActivity.toast(String.format(getString(R.string.file_size_too_big), "512KB"));
                    onFail();
                } else {
                    String ext = "";
                    if (path.indexOf(".") > 0) {
                        String filenameArray[] = path.split("\\.");
                        ext = filenameArray[filenameArray.length-1];
                    };
                    if (!ext.equalsIgnoreCase("doc") && !ext.equalsIgnoreCase("docx") && !ext.equalsIgnoreCase("pdf")) {
                        BaseActivity.toast(String.format(getString(R.string.file_type_is_not_support), "pdf, doc, docx"));
                        onFail();
                    } else {
                        JobApplyForm jf = (JobApplyForm) ShareContext.get(ShareContext.SELECTED_JOB);
                        jf.setFileContents(path);
                        fileName.setText(f.getName());
                        /*
                        String public_name = "cv_" + Auth.getAuthData().getProfile().getUserId() + "_" + Common.getMillis();
                        CloudinaryService.uploadRaw(getContext(), path, public_name, new Callback() {
                            @Override
                            public void onCompleted(Context context, CallbackResult result) {
                                if (result.hasError()) {
                                    BaseActivity.toast(R.string.fail_to_upload_file);
                                } else {
                                    Map m = (Map) result.getData();
                                    String url = (String) m.get("secure_url");
                                    String public_id = (String) m.get("public_id");
                                    System.out.println(m);
                                    //TODO: apply job
                                }
                            }
                        });
                        */
                    }
                }
            } else {
                BaseActivity.toast(R.string.invalid_file);
                onFail();
            }
        }
    }

    private void onFail() {
        fileName.setText("");
    }
}
