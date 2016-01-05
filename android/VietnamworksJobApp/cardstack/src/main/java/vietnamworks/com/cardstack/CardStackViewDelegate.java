package vietnamworks.com.cardstack;

/**
 * Created by duynk on 1/5/16.
 */
public interface CardStackViewDelegate {
    void onLaunched(CardStackView obj);
    void onChangedActiveItem(int front_index, int mid_index, int back_index, CardStackView obj);
    void onBeforeChangedActiveItem(int front_index, int mid_index, int back_index, CardStackView obj);
    int getTotalRecords();
    void onBeforeSelectItem(int index,CardStackView ccsv);
    void onSelectItem(int index,CardStackView ccsv);
    void onDeselectItem(int index, CardStackView ccsv);
}
