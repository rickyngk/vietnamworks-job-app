package vietnamworks.com.vietnamworksjobapp.activities.onboarding.input.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import R.helper.BaseFragment;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vietnamworksjobapp.activities.onboarding.input.InputInfoActivity;
import vietnamworks.com.vietnamworksjobapp.entities.WorkingLocation;
import vietnamworks.com.vietnamworksjobapp.models.UserLocalProfileModel;

/**
 * Created by duynk on 1/4/16.
 */
public class InputLocationFragment extends BaseFragment {
    @Bind(R.id.btn_next)
    Button btnNext;

    @Bind(R.id.cb_hcm)
    CheckBox cb_hcm;

    @Bind(R.id.cb_hn)
    CheckBox cb_hn;

    @Bind(R.id.cb_danang)
    CheckBox cb_danang;

    @Bind(R.id.cb_other_location)
    CheckBox cb_other_location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_input_location, container, false);
        ButterKnife.bind(this, rootView);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<WorkingLocation> locations = new ArrayList<>();
                if (cb_hcm.isChecked()) {
                    locations.add(WorkingLocation.HoChiMinh);
                }
                if (cb_hn.isChecked()) {
                    locations.add(WorkingLocation.HaNoi);
                }
                if (cb_danang.isChecked()) {
                    locations.add(WorkingLocation.DaNang);
                }
                if (cb_other_location.isChecked()) {
                    locations.add(WorkingLocation.Other);
                }
                UserLocalProfileModel.getEntity().setWorkingLocation(locations);
                getActivityRef(InputInfoActivity.class).setPageIndex(2);
            }
        });

        return rootView;
    }
}
