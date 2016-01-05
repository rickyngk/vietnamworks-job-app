package vietnamworks.com.cardstack;

import android.view.View;

/**
 * Created by duynk on 1/5/16.
 */
public interface CardStackViewDelegate2 {
    void onLaunched(CardStackView2 obj);
    void onChangedActiveItem(int front_index, int mid_index, int back_index, CardStackView2 obj);
    void onBeforeChangedActiveItem(int front_index, int mid_index, int back_index, CardStackView2 obj);
    int getTotalRecords();
    void onBeforeSelectItem(int index, CardStackView2 ccsv);
    void onSelectItem(int index, CardStackView2 ccsv);
    void onDeselectItem(int index, CardStackView2 ccsv);

    View onLoadView(int index);
}
