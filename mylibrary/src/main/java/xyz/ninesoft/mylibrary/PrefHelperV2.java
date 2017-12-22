package xyz.ninesoft.mylibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by illiac on 2016-08-18.
 */
public class PrefHelperV2<T_FIELD extends Enum<T_FIELD> & IEnumExtend> {
    final SharedPreferences preferences;

    public PrefHelperV2(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public <T_VALUE> void putValue(T_FIELD filed, T_VALUE value) {
        매개변수검사(filed, value);

        putValueImpl(filed, value);
    }

    private <T_VALUE> void 매개변수검사(T_FIELD filed, T_VALUE value) {
        필드타입_임의값타입검사_N_예외(filed, value);
        필드타입_저장된타입_검사_N_삭제(filed);
    }

    private <T_VALUE> void 필드타입_임의값타입검사_N_예외(T_FIELD filed, T_VALUE value) {
        if (!checkValueType(filed.getType(), value))
            throw new RuntimeException(String.format("매개변수의 타입(%s)과 필드타입(%s)이 다릅니다.", value.getClass().getSimpleName(), filed.getType().getSimpleName()));
    }

    private  void 필드타입_저장된타입_검사_N_삭제(T_FIELD filed) {
        if (isExistKey(filed) &&
                !filed.getType().isAssignableFrom(preferences.getAll().get(filed.name()).getClass())) {
            removeKey(filed);
        }
    }

    public void removeKey(T_FIELD filed) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(filed.name());
        editor.commit();
    }


    public <T_VALUE> void putValueImpl(T_FIELD filed, T_VALUE value) {
        SharedPreferences.Editor editor = preferences.edit();
        Class type = filed.getType();

        if (type == String.class)
            editor.putString(filed.toString(), (String) value);
        else if (type == Boolean.class)
            editor.putBoolean(filed.toString(), (Boolean) value);
        else if (type == Integer.class)
            editor.putInt(filed.toString(), (Integer) value);
        else if (type == Long.class)
            editor.putLong(filed.toString(), (Long) value);
        else
            throw new RuntimeException(String.format("타입(%s)이 정의되어있지 않습니다.", type.getSimpleName()));

        editor.commit();
    }

    public static <T_VALUE> boolean checkValueType(Class<T_VALUE> cls, T_VALUE value) {
        return cls == value.getClass();
    }

    public <T_VALUE> T_VALUE getValue(T_FIELD filed, T_VALUE defaultValue) {
        매개변수검사(filed, defaultValue);

        return getValueImpl(filed, defaultValue);
    }

    public <T_VALUE> T_VALUE getValueImpl(T_FIELD filed, T_VALUE defaultValue) {
        Class type = filed.getType();

        if (type == Boolean.class) {
            return (T_VALUE) type.cast(preferences.getBoolean(filed.name(), (Boolean) defaultValue));
        } else if (type == String.class) {
            return (T_VALUE) type.cast(preferences.getString(filed.name(), (String) defaultValue));
        } else if (type == Integer.class) {
            return (T_VALUE) type.cast(preferences.getInt(filed.name(), (Integer) defaultValue));
        } else if (type == Long.class) {
            return (T_VALUE) type.cast(preferences.getLong(filed.name(), (Long) defaultValue));
        }

        throw new RuntimeException(String.format("타입(%s)이 정의되어있지 않습니다.", type.getSimpleName()));
    }

    public boolean isExistKey(T_FIELD item) {
        return preferences.getAll().containsKey(item.toString());
    }
}
