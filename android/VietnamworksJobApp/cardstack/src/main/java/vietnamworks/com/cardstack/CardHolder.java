package vietnamworks.com.cardstack;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import vietnamworks.com.helper.StateObject;

/**
 * Created by duynk on 1/5/16.
 */
public class CardHolder extends FrameLayout {
    public StateObject stateData = new StateObject();


    private CardStackView2 refStack;
    private ViewGroup cardView;

    public CardHolder(Context context) {
        super(context);
        initializeViews(context);
    }

    public CardHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public CardHolder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }


    public void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_card, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        cardView = (ViewGroup) this.findViewById(R.id.card_view);
    }

    public void setHolderRef(CardStackView2 ref) {
        this.refStack = ref;
    }
    public CardStackView2 getHolderRef() {
        return this.refStack;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.refStack != null) {
            this.refStack.onChildTouchEvent(this, event);
        }
        return true;
    }

    public ViewGroup getCardView() {
        return cardView;
    }
}