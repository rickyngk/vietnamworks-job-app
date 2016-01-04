package vietnamworks.com.helper;

/**
 * Created by duynk on 12/29/15.
 */
public class BaseApplication  extends android.app.Application {

    public enum FontType {
        sDefaults("sDefaults"),
        DEFAULT("DEFAULT"),
        DEFAULT_BOLD("DEFAULT_BOLD"),
        MONOSPACE("MONOSPACE"),
        SERIF("SERIF"),
        SANS_SERIF("SANS_SERIF");

        private final String value;

        private FontType(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return getValue();
        }
    }
    static BaseApplication sInstance;

    public BaseApplication() {
        super();
        sInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LocalStorage.init(this);
    }

    public static void setDefaultFont(FontType type, String filename) {
        FontsOverride.setDefaultFont(sInstance, type.toString(), filename);
    }
}
