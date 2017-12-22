package xyz.ninesoft.hfsm.simple;

import xyz.ninesoft.hfsm.HFSM_BASE;
import xyz.ninesoft.hfsm.IEVENT_BASE;

/**
 * Created by illiacDev on 2017-08-03.
 */

public class Simple_HFSM extends HFSM_BASE<IEVENT, IOUT> {

    abstract class StateImpl extends State implements IEVENT {

        public IEVENT_BASE a() { return getParent(); }

        public IEVENT_BASE b() {
            return getParent();
        }

        public IEVENT_BASE c() {
            return getParent();
        }

        public IEVENT_BASE d() {
            return getParent();
        }

        public IEVENT_BASE e() {
            return getParent();
        }

        public IEVENT_BASE f() {
            return getParent();
        }

        public IEVENT_BASE g() {
            return getParent();
        }

        @Override
        public IEVENT_BASE entry() {
            emit_entry();
            return null; //DC
        }

        @Override
        public IEVENT_BASE exit() {
            emit_exit();
            return null; //DC
        }



        @Override
        public State showID() {
            subject.onNext(iout -> iout.onShowID(getID(this)));
            return null;
        }

        @Override
        public IEVENT_BASE empty() {
            return getParent();
        }

    }

    private IEVENT s0 = new StateImpl() {

        @Override
        public IEVENT init() {
            emit_init();
            current = s1;
            return null;
        }

        @Override
        public IEVENT_BASE getParent() {
            return top;
        }

        @Override
        public State e() {
            transit_neo(s211);
            return null;
        }
    };

    private IEVENT s1 = new StateImpl() {

        @Override
        public IEVENT_BASE init() {
            emit_init();
            current = s11;
            return null;
        }

        @Override
        public IEVENT_BASE getParent() {
            return s0;
        }

        @Override
        public IEVENT_BASE a() {
            transit_neo(s1);
            return null;
        }

        @Override
        public IEVENT_BASE b() {
            transit_neo(s11);
            return null;
        }

        @Override
        public IEVENT_BASE c() {
            transit_neo(s2);
            return null;
        }

        @Override
        public IEVENT_BASE d() {
            transit_neo(s0);
            return null;
        }

        @Override
        public IEVENT_BASE f() {
            transit_neo(s211);
            return null;
        }
    };

    private IEVENT s11 = new StateImpl() {

        @Override
        public IEVENT_BASE getParent() {
            return s1;
        }

        @Override
        public IEVENT_BASE g() {
            transit_neo(s211);
            return null;
        }
    };

    private IEVENT s2 = new StateImpl() {
        @Override
        public IEVENT_BASE getParent() {
            return s0;
        }

        @Override
        public IEVENT_BASE init() {
            emit_init();
            current = s21;
            return null;
        }

        @Override
        public IEVENT_BASE c() {
            transit_neo(s1);
            return null;
        }
    };

    private IEVENT s21 = new StateImpl() {
        @Override
        public IEVENT_BASE getParent() {
            return s2;
        }

        @Override
        public IEVENT_BASE init() {
            emit_init();
            current = s211;
            return null;
        }
    };

    private IEVENT s211 = new StateImpl() {
        @Override
        public IEVENT_BASE getParent() {
            return s21;
        }
    };

    @Override
    protected IEVENT obtainInitalState() {
        return s0;
    }

}
