package vietnamworks.com.vietnamworksjobapp.activities.onboarding.input.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import R.helper.BaseFragment;
import R.helper.Callback;
import R.helper.CallbackResult;
import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vietnamworksjobapp.activities.onboarding.input.InputInfoActivity;
import vietnamworks.com.vietnamworksjobapp.models.UserLocalSearchDataModel;
import vietnamworks.com.vnwcore.VNWAPI;

/**
 * Created by duynk on 1/4/16.
 */
public class InputTitleFragment extends BaseFragment {
    @Bind(R.id.btn_next)
    Button btnNext;

    @Bind(R.id.job_title)
    AutoCompleteTextView jobTitle;

    ArrayAdapter<String> adapter;

    private boolean preventTextChangeEvent = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_input_title, container, false);
        ButterKnife.bind(this, rootView);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNext();
            }
        });

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line);

        jobTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == getResources().getInteger(R.integer.ime_job_title) || actionId == EditorInfo.IME_NULL) && event == null) {
                    onNext();
                    return true;
                }
                return false;
            }
        });
        jobTitle.setAdapter(adapter);
        jobTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (preventTextChangeEvent) {
                    preventTextChangeEvent = false;
                    return;
                }
                VNWAPI.jobTitleSuggestion(getContext(), jobTitle.getText().toString(), new Callback<ArrayList<String>>() {
                    @Override
                    public void onCompleted(Context context, CallbackResult<ArrayList<String>> result) {
                        adapter.clear();
                        if (!result.hasError()) {
                            try {
                                ArrayList<String> data = result.getData();
                                if (data != null) {
                                    for (int i = 0; i < data.size(); i++) {
                                        adapter.add(data.get(i));
                                    }
                                }
                                System.out.println(data);
                                adapter.getFilter().filter(jobTitle.getText(), null);
                            } catch (Exception E) {
                                E.printStackTrace();
                            }
                        }
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return rootView;
    }

    private void onNext() {
        UserLocalSearchDataModel.getEntity().setJobTitle(jobTitle.getText().toString());
        getActivityRef(InputInfoActivity.class).setPageIndex(1);
    }
}
