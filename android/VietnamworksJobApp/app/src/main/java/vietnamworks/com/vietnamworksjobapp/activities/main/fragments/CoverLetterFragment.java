package vietnamworks.com.vietnamworksjobapp.activities.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import R.helper.BaseActivity;
import R.helper.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vnwcore.Auth;

/**
 * Created by duynk on 1/20/16.
 */
public class CoverLetterFragment extends BaseFragment {
    @Bind(R.id.cover_letter)
    EditText coverLetter;

    @Bind(R.id.cover_letter_scroll_view)
    ViewGroup coverLetterScrollView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_input_cover_letter, container, false);
        ButterKnife.bind(this, rootView);
        BaseActivity.hideActionBar();

        vietnamworks.com.vnwcore.entities.Auth auth = Auth.getAuthData();
        coverLetter.setText(auth.getCoverLetter());


        coverLetterScrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coverLetter.requestFocus();
                BaseActivity.showKeyboard();
            }
        });
        return rootView;
    }
}
