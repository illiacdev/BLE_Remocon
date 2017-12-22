package xyz.ninesoft.mylibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by illiac on 2016-08-18.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class PrefHelperV2Test {

    static Context context;

    @BeforeClass
    public static void SetupClass() {
        context = InstrumentationRegistry.getTargetContext();

        clearPref();
    }

    @Before
    public void setUp() throws Exception {

    }

    static private void clearPref() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        for (String key : preferences.getAll().keySet())
            editor.remove(key);
        editor.commit();
    }

    @After
    public void tearDown() throws Exception {

    }

    enum T_TYPE implements IEnumExtend {
        T1(String.class), T2(Integer.class), T3(String.class);

        final public Class aCls;

        T_TYPE(Class aCls) {
            this.aCls = aCls;
        }

        @Override
        public Class getType() {
            return aCls;
        }
    }

    enum T_TYPE2 implements IEnumExtend {
        T1(String.class), T2(Integer.class), T3(Boolean.class);

        final public Class aCls;

        T_TYPE2(Class aCls) {
            this.aCls = aCls;
        }

        @Override
        public Class getType() {
            return aCls;
        }
    }

    @Test
    public void testFunc() {
        PrefHelperV2<T_TYPE> helperV2 = new PrefHelperV2<>(context);

        helperV2.putValue(T_TYPE.T1, "test");
        String test = helperV2.getValue(T_TYPE.T1, "");
        assertTrue(test.equals("test"));
    }

    @Test
    public void testFunc2() {
        assertTrue(PrefHelperV2.checkValueType(Boolean.class, true));
        assertTrue(PrefHelperV2.checkValueType(String.class, ""));

        System.out.println(T_TYPE.T1.toString());

        System.out.println(String.class.isAssignableFrom(Integer.class));
        System.out.println(Integer.class.isAssignableFrom(String.class));

    }

    @Test(expected = RuntimeException.class)
    public void testFunc3() {
        PrefHelperV2<T_TYPE> helperV2 = new PrefHelperV2<>(context);

        helperV2.putValue(T_TYPE.T1, true);
    }

    @Test
    public void testFunc4() {
        PrefHelperV2<T_TYPE> helperV2 = new PrefHelperV2<>(context);

        try {
            helperV2.putValue(T_TYPE.T1, true);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testFunc5() {
        PrefHelperV2<T_TYPE> helperV2 = new PrefHelperV2<>(context);

        assertThat(helperV2.getValue(T_TYPE.T3, "empty"), is("empty"));
        helperV2.putValue(T_TYPE.T3, "test1");
        assertThat(helperV2.getValue(T_TYPE.T3, "empty"), is("test1"));

    }

    @Test(expected = RuntimeException.class)
    public void testFunc6() {
        PrefHelperV2<T_TYPE2> helperV2 = new PrefHelperV2<>(context);
        assertThat(helperV2.getValue(T_TYPE2.T3, "empty"), is("empty"));
    }

    @Test
    public void testFunc7() {
        PrefHelperV2<T_TYPE2> helperV2 = new PrefHelperV2<>(context);

        assertThat(helperV2.getValue(T_TYPE2.T3, false), is(false));
        helperV2.putValue(T_TYPE2.T3, true);
        assertThat(helperV2.getValue(T_TYPE2.T3, false), is(true));

    }

    @Test
    public void testFunc8() {
        clearPref();
        PrefHelperV2<T_TYPE> helperV2 = new PrefHelperV2<>(context);
        helperV2.putValue(T_TYPE.T3, "test1");
        assertThat(helperV2.getValue(T_TYPE.T3, "empty"), is("test1"));

    }

    @Test
    public void testFunc9() {
        PrefHelperV2<T_TYPE2> helperV2 = new PrefHelperV2<>(context);
        helperV2.putValue(T_TYPE2.T3, true);
        assertThat(helperV2.getValue(T_TYPE2.T3, false), is(true));

    }
}