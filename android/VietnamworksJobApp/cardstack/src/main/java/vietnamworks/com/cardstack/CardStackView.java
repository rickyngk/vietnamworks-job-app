package vietnamworks.com.cardstack;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;

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


    public final static float CARD_TRIGGER_PERCENT = 0.25f;
    public final static float FIRST_CARD_MAX_ROTATE_ANGLE = 5.0f;

    private int state = -1;
    private int nextState = STATE_PRE_INIT;
    private int lastState = -1;
    private boolean isAlive = true;
    private long frameDt;
    private boolean lockState;
    private long lastUpdate;
    private Thread thread;

    //ui
    ProgressBar progressBar;
    ArrayList<ViewGroup> cards;
    View cardHolder;

    private float movingPercent;
    private float overMovingPercent;
    RelativeLayout.LayoutParams layoutParams;

    CardStackViewDelegate delegate = new CardStackViewDelegate() {
        @Override
        public void onStarted() {

        }

        @Override
        public View onLoadView(int index) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    };

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
        cardHolder = this.findViewById(R.id.cards);

        thread = new Thread() {
            @Override
            public void run() {
                lockState = false;
                try {
                    while(isAlive) {
                        update();
                        long dt = Math.min(Math.max((long) (1000.0f / 60.0f) - frameDt, 10), 100);
                        sleep(dt);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        switchState(STATE_PRE_INIT);

        thread.start();
    }

    public void setDelegate(CardStackViewDelegate delegate) {
        this.delegate = delegate;
    }

    private void switchState(int state) {
        nextState = state;
    }

    private void update() {
        if (lockState) {
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

        switch (state) {
            case STATE_PRE_INIT:
                showLoading(true);
                delegate.onStarted();
                break;
            case STATE_IDLE:
                int n = delegate.getCount();
                for (int i = 0; i < n && i < cards.size(); i++) {
                    View v = delegate.onLoadView(i);
                    cards.get(i).addView(v);
                    if (i >= n) {
                        v.setVisibility(INVISIBLE);
                    }
                }
                showLoading(false);
                updateLayout();
                if (cards.size() > 0) {
                    cards.get(0).setOnTouchListener(onTouchListener);
                }
                break;
        }
    }

    private void onUpdateState() {
        boolean updateLayout = false;
        switch (state) {
            case STATE_PRE_INIT:
                break;
            case STATE_DRAG:
            case STATE_DRAG_OUT:
                int targetX = targetScrollX;
                View front = cards.get(0);
                layoutParams = ((RelativeLayout.LayoutParams) front.getLayoutParams());
                int currentX = layoutParams.leftMargin;

                if (targetX != currentX) {
                    layoutParams.leftMargin = Common.lerp(layoutParams.leftMargin, targetScrollX, 0.8f);
                    updateLayout = true;
                }

                if (updateLayout) {
                    float p = Math.abs(layoutParams.leftMargin) / (front.getWidth()*CARD_TRIGGER_PERCENT);
                    movingPercent = Math.min(p, 1.0f);
                    overMovingPercent = Math.max(Math.min(p - 1.0f, 1.0f), 0f);
                    if (movingPercent >= 1.0f) {
                        switchState(STATE_DRAG_OUT);
                    } else if (movingPercent > 0 && state == STATE_DRAG_OUT) {
                        switchState(STATE_DRAG);
                    }
                }
                break;
        }

        if (updateLayout) {
            updateLayout();
        }
    }

    private void showLoading(final boolean val) {
        BaseActivity.timeout(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(val ? VISIBLE : GONE);
                cardHolder.setVisibility(val ? GONE : VISIBLE);
            }
        });
    }

    private void setLockState(boolean val) {
        lockState = val;
    }

    private void updateLayout() {
        setLockState(true);
        switch (state) {
            case STATE_IDLE:
                int n = Math.min(delegate.getCount(), cards.size());
                for (int i = 1; i < n; i++) {
                    final View v = cards.get(i);
                    float width = v.getWidth();
                    float widthTarget = width - Common.convertDpToPixel(5.0f * i);
                    final float scale = widthTarget/width;
                    final float translate = Common.convertDpToPixel(5.0f * i);
                    BaseActivity.timeout(new Runnable() {
                         @Override
                         public void run() {
                             v.setTranslationY(translate);
                             v.setScaleX(scale);
                             v.setScaleY(scale);
                             setLockState(false);
                         }
                     });
                }
                break;
            case STATE_DRAG:
            case STATE_DRAG_OUT:
                BaseActivity.timeout(new Runnable() {
                    @Override
                    public void run() {
                        View front = cards.get(0);
                        front.setLayoutParams(layoutParams);
                        front.setRotation(overMovingPercent * FIRST_CARD_MAX_ROTATE_ANGLE * Common.sign(layoutParams.leftMargin));
                    }
                });
                setLockState(false);
                break;
            default:
                setLockState(false);
                break;
        }
    }

    private float mDownX;
    private long lastTimeTouch = 0;
    int originLayoutMargin = 0;
    int targetScrollX = 0;

    private OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent ev) {
            final int action = MotionEventCompat.getActionMasked(ev);
            int distance = (int) (ev.getRawX() - mDownX);

            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    lastTimeTouch = System.currentTimeMillis();
                    mDownX = ev.getRawX();
                    originLayoutMargin = ((RelativeLayout.LayoutParams) v.getLayoutParams()).leftMargin;
                    if (state != STATE_DRAG && state != STATE_DRAG_OUT) {
                        switchState(STATE_DRAG);
                    }
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    targetScrollX = originLayoutMargin + (int) (ev.getRawX() - mDownX);
                    break;
                }
            }
            return true;
        }
    };


    public void ready() {
        showLoading(false);
        switchState(STATE_IDLE);
    }
}
