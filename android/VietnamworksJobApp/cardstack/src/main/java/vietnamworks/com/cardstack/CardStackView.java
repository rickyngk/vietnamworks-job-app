package vietnamworks.com.cardstack;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

/**
 * Created by duynk on 1/5/16.
 */
public class CardStackView extends FrameLayout {
    public final static int STATE_PRE_INIT = 0;
    public final static int STATE_IDLE = 1;


    private int state = -1;
    private int nextState = STATE_PRE_INIT;
    private int lastState = -1;
    private boolean isAlive = true;
    private long frameDt;
    private boolean lockState;
    private long lastUpdate;
    private Thread thread;
    private android.os.Handler handler = new android.os.Handler();

    //ui
    ProgressBar progressBar;
    ArrayList<ViewGroup> cards;
    View cardHolder;

    private void setTimeout(Runnable r) {
        handler.post(r);
    }

    private void setTimeout(Runnable r, long dt) {
        handler.postDelayed(r, dt);
    }

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
        cards.add((ViewGroup) this.findViewById(R.id.card_back));
        cards.add((ViewGroup) this.findViewById(R.id.card_mid));
        cards.add((ViewGroup) this.findViewById(R.id.card_front));
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
                break;
        }
    }

    private void onUpdateState() {
        switch (state) {
            case STATE_PRE_INIT:
                break;
        }
    }

    private void showLoading(final boolean val) {
        setTimeout(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(val?VISIBLE:GONE);
                cardHolder.setVisibility(val?GONE:VISIBLE);
            }
        });
    }

    public void ready() {
        showLoading(false);
        switchState(STATE_IDLE);
    }
}
