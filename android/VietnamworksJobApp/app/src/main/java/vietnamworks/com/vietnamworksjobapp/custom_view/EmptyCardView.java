package vietnamworks.com.vietnamworksjobapp.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import vietnamworks.com.vietnamworksjobapp.R;

/**
 * Created by duynk on 1/11/16.
 */
public class EmptyCardView extends FrameLayout {
    public EmptyCardView(Context context) {
        super(context);
        initializeViews(context);
    }

    public EmptyCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public EmptyCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    public void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cv_empty_card, this);
    }
}
