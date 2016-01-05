package vietnamworks.com.helper;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by duynk on 1/5/16.
 */
public class BaseEntity implements IExportable {

    protected List<String> fields;
    protected HashMap<String, Object> data = new HashMap<>();

    public BaseEntity(@NonNull List<String> fieldsList) {
        fields = fieldsList;
    }

    public HashMap exportToHashMap() {
        return data;
    }

    @CallSuper
    public void importFromHashMap(@NonNull HashMap m) {
        for (String field:fields) {
            if (m.containsKey(field)) {
                data.put(field, m.get(field));
            }
        }
    }

    public void set(String key, Object value) {
        if (fields.contains(key)) {
            data.put(key, value);
        }
    }

    public Object get(@NonNull String key, Object defaultValue) {
        if (fields.contains(key)) {
            if (data.containsKey(key)) {
                Object obj = data.get(key);
                return obj == null ? defaultValue : obj;
            } else {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public String getString(@NonNull String key, String defaultValue) {
        return (String) get(key, defaultValue);
    }

    public int getInt(@NonNull String key, int defaultValue) {
        Object obj = get(key, null);
        if (obj == null) {
            return defaultValue;
        } else {
            try {
                return (Integer)obj;
            } catch (Exception E) {
                return defaultValue;
            }
        }
    }

    public long getLong(@NonNull String key, long defaultValue) {
        Object obj = get(key, null);
        if (obj == null) {
            return defaultValue;
        } else {
            try {
                return (Long)obj;
            } catch (Exception E) {
                return defaultValue;
            }
        }
    }

    public boolean getBoolean(@NonNull String key, boolean defaultValue) {
        Object obj = get(key, null);
        if (obj == null) {
            return defaultValue;
        } else {
            try {
                return (Boolean)obj;
            } catch (Exception E) {
                return defaultValue;
            }
        }
    }

    public <T> ArrayList<T> getArray(@NonNull String key) {
        Object obj = get(key, null);
        ArrayList<T> list = new ArrayList<T>();
        if (obj != null) {
            if (obj instanceof Iterator) {
                Iterator itr = (Iterator) obj;
                while (itr.hasNext()) {
                    Object element = itr.next();
                    if (element != null) {
                        list.add((T) element);
                    }
                }
            }
        }
        return list;
    }
}
