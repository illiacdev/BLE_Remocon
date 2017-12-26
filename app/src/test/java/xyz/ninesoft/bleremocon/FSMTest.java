package xyz.ninesoft.bleremocon;

import org.junit.Test;

import xyz.ninesoft.fsm.FSM;

/**
 * Created by illiacDev on 2017-12-25.
 */
public class FSMTest {
    public enum Type {ON, OFF}

    public enum Event {OPER}

    class MyFSM extends FSM<Type, Event> {
        public class MyState extends State {

            public MyState(Type id) {
                super(id);
                System.out.println(id.toString());
            }
        }

        public class ON extends MyState {

            public ON(Type id) {
                super(id);
            }

            @Override
            public void dispatch(Event eid) {
                switch (eid) {
                    case OPER:
                        transit(Type.OFF);
                        break;
                    default:
                        break;
                }
            }
        }

        public class OFF extends MyState {

            public OFF(Type id) {
                super(id);
            }

            @Override
            public void dispatch(Event eid) {
                switch (eid) {
                    case OPER:
                        transit(Type.ON);
                        break;
                    default:
                        break;
                }
            }
        }

    }

    @Test
    public void function() {

        MyFSM fsm = new MyFSM();
        fsm.start(Type.ON);

        System.out.println(fsm.getCurr());
        fsm.dispatch(Event.OPER);
        System.out.println(fsm.getCurr());

    }

}