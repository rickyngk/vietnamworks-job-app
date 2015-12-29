package vietnamworks.com.helper;

/**
 * Created by duynk on 12/29/15.
 */
public class CallbackResult {
    public class CallbackError {
        int errorCode;
        String message;
        public CallbackError(int errorCode, String message) {
            this.errorCode = errorCode;
            this.message = message;
        }
    }
    CallbackError error;
    Object data;

    public boolean hasError() {
        return error != null;
    }

    public CallbackError getError() {
        return this.error;
    }

    public CallbackResult(CallbackError error, Object data) {
        this.error = error;
        this.data = data;
    }
}
