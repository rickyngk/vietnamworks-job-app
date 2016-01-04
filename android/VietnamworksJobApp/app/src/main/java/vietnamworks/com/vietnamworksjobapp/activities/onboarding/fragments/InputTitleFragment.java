package vietnamworks.com.vietnamworksjobapp.activities.onboarding.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import vietnamworks.com.helper.BaseFragment;
import vietnamworks.com.vietnamworksjobapp.R;

/**
 * Created by duynk on 1/4/16.
 */
public class InputTitleFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_input_title, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }
}
