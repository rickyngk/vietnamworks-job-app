package vietnamworks.com.cardstack;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashSet;

import vietnamworks.com.helper.BaseActivity;
import vietnamworks.com.helper.Common;

/**
 * Created by duynk on 1/5/16.
 */
public class CardStackView extends FrameLayout {
    public final static int STATE_PRE_INIT = 0;
    public final static int STATE_IDLE = STATE_PRE_INIT + 1;
    public final static int STATE_DRAG = STATE_IDLE + 1;
    public final static int STATE_DRAG_OUT = STATE_DRAG + 1;
    public final static int STATE_SCROLL_BACK = STATE_DRAG_OUT + 1;
    public final static int STATE_FLY_OUT = STATE_SCROLL_BACK + 1;
    public final static int STATE_REORDER = STATE_FLY_OUT + 1;


    public final static float CARD_STEP_SIZE = 10f; //card reductive size for each level in stack
    public final static float CARD_TRIGGER_PERCENT = 0.25f; //distance of dragging that trigger opening next card, based of percent of card width
    public final static float FIRST_CARD_MAX_ROTATE_ANGLE = 5.0f; //card's rotate angle while dragging
    public final static float SWIPE_MIN_DT = 500; //delta time to detect swipe
    public final static float SWIPE_MIN_DISTANCE = 50f; //min distance to detect swipe
    public final static float MOVE_MIN_DISTANCE = 3f; //min distance to recognize touch or drag
    public final static float LERP_FACTOR = 0.2f; //lerp factor for transition animation

    private int state = -1;
    private int nextState = -1;
    private int lastState = -1;
    private boolean isAlive = true;
    private long frameDt;
    private boolean waitForUIThead;
    private long lastUpdate;
    private Thread thread;

    private int frontIndex = 0;

    //ui
    ProgressBar progressBar;
    ArrayList<ViewGroup> cards;
    View cardHolder;
    float density;

    private class IntPair {
        int i;
        int j;
        public IntPair(int i, int j) {this.i = i; this.j = j;}
    };

    private class ViewModel {
        //first card control
        float frontMovingPercent;
        float frontOverMovingPercent;
        int frontTranslateX;
        int frontTargetX;

        HashSet<IntPair> requestedView = new HashSet<>();

        boolean showLoading;

        boolean hasChanged;

        public void setFrontMovingPercent(float frontMovingPercent) {
            this.frontMovingPercent = frontMovingPercent;
            this.hasChanged = true;
        }

        public void setFrontOverMovingPercent(float frontOverMovingPercent) {
            this.frontOverMovingPercent = frontOverMovingPercent;
            this.hasChanged = true;
        }

        public void setFrontTranslateX(int frontTranslateX) {
            this.frontTranslateX = frontTranslateX;
            this.hasChanged = true;
        }

        public void setShowLoading(boolean showLoading) {
            this.showLoading = showLoading;
            this.hasChanged = true;
        }

        public void setFrontTargetX(int x) {
            frontTargetX = x;
            this.hasChanged = true;
        }

        public void update() {
            this.hasChanged = false;
        }

        public void requestView(HashSet views) {
            requestedView = views;
        }

        public void copyTo(@NonNull ViewModel model) {
            model.frontMovingPercent = this.frontMovingPercent;
            model.frontOverMovingPercent = this.frontOverMovingPercent;
            model.frontTranslateX = this.frontTranslateX;
            model.showLoading = this.showLoading;
            model.hasChanged = this.hasChanged;
            model.requestedView = (HashSet)this.requestedView.clone();
        }
    }


    private ViewModel viewModel = new ViewModel();
    private ViewModel stateViewModel = new ViewModel();

    CardStackViewDelegate delegate;

    public CardStackView(Context context) {
        super(context);
        initializeViews(context);
    }

    public CardStackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public CardStackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    public void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.card_stack, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        progressBar = (ProgressBar)this.findViewById(R.id.progressBar);

        cards = new ArrayList<ViewGroup>();
        cards.add((ViewGroup) this.findViewById(R.id.card_front));
        cards.add((ViewGroup) this.findViewById(R.id.card_mid));
        cards.add((ViewGroup) this.findViewById(R.id.card_back));
        cards.add((ViewGroup) this.findViewById(R.id.card_reserve));
        cardHolder = this.findViewById(R.id.cards);

        density = this.getResources().getDisplayMetrics().density;

        thread = new Thread() {
            @Override
            public void run() {
                waitForUIThead = false;
                try {
                    while(isAlive) {
                        update();
                        long dt = Math.min(Math.max((long) (1000.0f / 30.0f) - frameDt, 10), 100);
                        sleep(dt);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public void setDelegate(@NonNull CardStackViewDelegate delegate) {
        this.delegate = delegate;
        if (this.state < 0) {
            switchState(STATE_PRE_INIT);
        }
    }

    private void switchState(int state) {
        nextState = state;
    }

    private void update() {
        if (waitForUIThead) {
            return;
        }
        frameDt = Math.min(System.currentTimeMillis() - lastUpdate, 500);
        lastUpdate = System.currentTimeMillis();

        if (state != nextState) {
            onEnterState(nextState, state);
        }
        onUpdateState();
    }

    private void onEnterState(int state, int lastState) {
        this.lastState = lastState;
        this.state = state;
        this.nextState = state;
        int n = Math.min(delegate.getCount(), cards.size());

        System.out.println("onEnterState " + lastState + " -> " + state);
        switch (state) {
            case STATE_PRE_INIT:
                showLoading(true);
                delegate.onStarted(this);
                this.frontIndex = 0;
                break;
            case STATE_IDLE:
                if (lastState == STATE_PRE_INIT) {
                    HashSet<IntPair> requestView = new HashSet<>();
                    for (int i = 0; i < n; i++) {
                        requestView.add(new IntPair(i, i));
                    }
                    viewModel.requestView(requestView);
                    if (cards.size() > 0) {
                        cards.get(0).setOnTouchListener(onTouchListener);
                    }
                    showLoading(false);
                };
                break;
            case STATE_SCROLL_BACK:
                viewModel.setFrontTargetX(0);
                break;
            case STATE_FLY_OUT:
                viewModel.setFrontTargetX(Common.sign(viewModel.frontTranslateX) * BaseActivity.getScreenWidth());
                break;
            case STATE_REORDER:
                viewModel.setFrontTargetX(0);
                viewModel.setFrontTranslateX(0);
                HashSet<IntPair> requestView2 = new HashSet<>();
                this.frontIndex ++;
                for (int i = 0; i < n; i++) {
                    if (this.frontIndex + i < delegate.getCount()) {
                        requestView2.add(new IntPair(i, this.frontIndex + i));
                    }
                }
                viewModel.requestView(requestView2);
                switchState(STATE_IDLE);
                if (this.frontIndex < delegate.getCount()) {
                    delegate.onActive(this, this.frontIndex);
                } else {
                    delegate.onEndOfStack(this);
                }
                break;
        }
    }

    private void onUpdateState() {
        int currentX;
        View front;
        switch (state) {
            case STATE_DRAG:
            case STATE_DRAG_OUT:
            case STATE_SCROLL_BACK:
            case STATE_FLY_OUT:
                front = cards.get(0);
                currentX = (int)front.getTranslationX();

                if (viewModel.frontTargetX != currentX) {
                    viewModel.setFrontTranslateX(Common.lerp(currentX, viewModel.frontTargetX, LERP_FACTOR));

                    float p = Math.abs(viewModel.frontTranslateX) / (front.getWidth()*CARD_TRIGGER_PERCENT);
                    viewModel.setFrontMovingPercent(Math.min(p, 1.0f));
                    viewModel.setFrontOverMovingPercent(Math.max(Math.min(p - 1.0f, 1.0f), 0f));

                    if (state == STATE_DRAG && viewModel.frontMovingPercent >= 1.0f) {
                        switchState(STATE_DRAG_OUT);
                    } else if (state == STATE_DRAG_OUT && viewModel.frontMovingPercent < 1) {
                        switchState(STATE_DRAG);
                    }
                } else {
                    if (state == STATE_SCROLL_BACK) {
                        switchState(STATE_IDLE);
                    } else if (state == STATE_FLY_OUT) {
                        switchState(STATE_REORDER);
                    }
                }
                break;
        }

        viewModel.copyTo(stateViewModel);
        viewModel.update();
        if (stateViewModel.hasChanged) {
            beginUpdateLayout();
            BaseActivity.timeout(new Runnable() {
                @Override
                public void run() {
                    updateLayout();
                    finishUpdateLayout();
                }
            });
        }
    }

    private void showLoading(final boolean val) {
        viewModel.setShowLoading(val);
    }

    private void beginUpdateLayout() {
        waitForUIThead = true;
    }

    private void finishUpdateLayout() {
        waitForUIThead = false;
        stateViewModel.update();
    }

    private void updateLayout() {
        boolean showLoading = stateViewModel.showLoading;

        progressBar.setVisibility(showLoading ? VISIBLE : GONE);
        cardHolder.setVisibility(showLoading ? GONE : VISIBLE);

        if (!showLoading) { //update card view state
            int total_record = delegate.getCount();
            int total_cards = cards.size();

            for (IntPair pair : stateViewModel.requestedView) {
                View v = delegate.onLoadView(this, pair.j);
                cards.get(pair.i).removeAllViews();
                cards.get(pair.i).addView(v);
            }

            int total_available_records = total_record - frontIndex;
            int total_available_cards = Math.max(total_cards - 1, 0); //don't show reserved cards
            if (state == STATE_DRAG_OUT || state == STATE_FLY_OUT) {
                total_available_cards = total_cards;
            }
            int total_shown_cards = Math.min(total_available_records, total_available_cards);

            for (int i = 0; i < total_cards; i++) {
                cards.get(i).setVisibility(i >= total_shown_cards?INVISIBLE:VISIBLE);
            }

            float movingDistance = CARD_STEP_SIZE;
            int n = Math.min(total_record, total_cards);
            for (int i = 1; i < n; i++) {
                final View v = cards.get(i);
                float width = v.getWidth();
                float delta_i = i;
                if (state == STATE_DRAG_OUT || state == STATE_FLY_OUT) {
                    delta_i -= stateViewModel.frontOverMovingPercent;
                    if (i == n-1) {
                        delta_i = n-2;
                    }
                }

                float widthTarget = width - Common.convertDpToPixel(movingDistance * delta_i);
                final float scale = widthTarget/width;
                final float translate = Common.convertDpToPixel(movingDistance * delta_i);
                v.setTranslationY(translate);
                v.setScaleX(scale);
                v.setScaleY(scale);
            }

            View front = cards.get(0);
            if (front != null) {
                front.setTranslationX(stateViewModel.frontTranslateX);
                front.setRotation(stateViewModel.frontOverMovingPercent * FIRST_CARD_MAX_ROTATE_ANGLE * Common.sign(stateViewModel.frontTranslateX));
                if (state == STATE_DRAG || state == STATE_DRAG_OUT) {
                    delegate.onDrag(this, Common.sign(stateViewModel.frontTranslateX) * stateViewModel.frontMovingPercent);
                }
            }
        }
    }

    private float mDownX;
    private long lastTimeTouch = 0;

    private OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent ev) {
            if (state == STATE_SCROLL_BACK || state == STATE_FLY_OUT) {
                return false;
            }
            final int action = MotionEventCompat.getActionMasked(ev);
            float distanceX = (ev.getRawX() - mDownX);
            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    lastTimeTouch = System.currentTimeMillis();
                    mDownX = ev.getRawX();
                    if (state != STATE_DRAG && state != STATE_DRAG_OUT) {
                        switchState(STATE_DRAG);
                    }
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    viewModel.setFrontTargetX( (int)distanceX);
                    break;
                }
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (state == STATE_DRAG_OUT) {
                        switchState(STATE_FLY_OUT);
                    } else {
                        long dt = System.currentTimeMillis() - lastTimeTouch;
                        if (dt < SWIPE_MIN_DT && Math.abs(distanceX) > density * SWIPE_MIN_DISTANCE) {
                            //is swipe
                            //TODO handle swipe action
                            switchState(STATE_FLY_OUT);
                        } else {
                            if (Math.abs(distanceX) < MOVE_MIN_DISTANCE * density) {
                                delegate.onOpen(CardStackView.this, CardStackView.this.frontIndex);
                                switchState(STATE_IDLE);
                            } else {
                                switchState(STATE_SCROLL_BACK);
                            }
                        }
                    }
                    break;
            }
            return true;
        }
    };

    public void reset() {
        switchState(STATE_PRE_INIT);
    }


    public void ready() {
        switchState(STATE_IDLE);
    }
}
