package xyz.ninesoft.fsm;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by illiacDev on 2017-12-25.
 */

public class FSM<T extends Enum<T>, TEVENT> {
    public FSM() {

        final int 파라메터화된_타입_T_STATE_ID의_순서 = 0; //Automata<0,1,2,3,4>
        Class<T> enumCls = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[파라메터화된_타입_T_STATE_ID의_순서];
        T[] result = null;
        try {
            Method method = enumCls.getDeclaredMethod("values", null);
            result = (T[]) method.invoke(null, null);

            for (T id : result) {
                String clsName = this.getClass().getName() + "$" + id.toString();
                Class cls = Class.forName(clsName);
                Object[] arg = new Object[2];
                arg[0] = this;
                arg[1] = id;
                Constructor[] cs = cls.getDeclaredConstructors();
                State newState = (State) cs[0].newInstance(arg);
                map.put(id, newState);
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    public abstract class State {
        final T id;

        public State(T id) {
            this.id = id;
        }

        public void dispatch(TEVENT eid) {

        }

        @Override
        public String toString() {
            return id.toString();
        }
    }

    State curr;

    public State getCurr() {
        return curr;
    }

    HashMap<T, State> map = new LinkedHashMap<>();

    public void dispatch(TEVENT eid) {

        curr.dispatch(eid);
    }

    public void transit(T target_id) {
        curr = map.get(target_id);
    }

    public void start(T id) {
        curr = map.get(id);
        System.out.println(curr.id.toString() + " ->id curr");
    }

}
