package vietnamworks.com.vietnamworksjobapp.activities.onboarding.input.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.helper.BaseFragment;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vietnamworksjobapp.activities.onboarding.input.InputInfoActivity;

/**
 * Created by duynk on 1/4/16.
 */
public class InputIndustryFragment extends BaseFragment {
    @Bind(R.id.btn_done)
    Button btnDone;

    @Bind(R.id.industry_spinner)
    Spinner industrySpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_input_industry, container, false);
        ButterKnife.bind(this, rootView);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivityRef(InputInfoActivity.class).onSkip(null);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.industry_array, R.layout.cv_dropdown_simple);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        industrySpinner.setAdapter(adapter);


        return rootView;
    }
}
