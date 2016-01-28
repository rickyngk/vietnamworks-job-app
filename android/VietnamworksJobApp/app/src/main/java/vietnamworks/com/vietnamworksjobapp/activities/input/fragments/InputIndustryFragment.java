package vietnamworks.com.vietnamworksjobapp.activities.input.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import butterknife.Bind;
import butterknife.ButterKnife;
import R.helper.BaseFragment;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vietnamworksjobapp.activities.input.InputInfoActivity;
import vietnamworks.com.vietnamworksjobapp.models.UserLocalSearchDataModel;

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
                String industry = "";
                String industry_code = "";
                int index = industrySpinner.getSelectedItemPosition();
                if (index > 0) {
                    industry = industrySpinner.getSelectedItem().toString();
                    industry_code = getResources().getStringArray(R.array.industry_code)[index];
                }
                UserLocalSearchDataModel.getEntity().setIndustry(industry);
                UserLocalSearchDataModel.getEntity().setIndustryCode(industry_code);
                getActivityRef(InputInfoActivity.class).onSkip(null);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.industry_array, R.layout.cv_dropdown_simple);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        industrySpinner.setAdapter(adapter);


        return rootView;
    }
}
