package xyz.ninesoft.hfsm.simple2;

import xyz.ninesoft.hfsm.HFSM_BASE;
import xyz.ninesoft.hfsm.IEVENT_BASE;
import xyz.ninesoft.hfsm.IOUT_BASE;

/**
 * Created by illiacDev on 2017-12-22.
 */

public class HFSM extends HFSM_BASE<IEVENT, IOUT_BASE> {
    abstract class StateImpl extends State implements IEVENT {
        @Override
        public IEVENT_BASE operate() {
            return getParent();
        }

        @Override
        public IEVENT_BASE showID() {
            subject.onNext(iout -> iout.onShowID(getID(this)));
            return null;
        }
    }

    @Override
    protected IEVENT obtainInitalState() {
        return s0;
    }

    private IEVENT s0 = new StateImpl() {
        @Override
        public IEVENT_BASE getParent() {
            return top;
        }

        @Override
        public IEVENT_BASE init() {
            current = off;
            return null;
        }


    };

    private IEVENT on = new StateImpl() {


        @Override
        public IEVENT_BASE getParent() {
            return s0;
        }

        @Override
        public IEVENT_BASE operate() {
            transit_neo(off);
            return null;
        }
    };

    private IEVENT off = new StateImpl() {


        @Override
        public IEVENT_BASE getParent() {
            return s0;
        }

        @Override
        public IEVENT_BASE operate() {
            transit_neo(on);
            return null;
        }
    };

}
