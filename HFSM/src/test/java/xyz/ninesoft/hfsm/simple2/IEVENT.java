package xyz.ninesoft.hfsm.simple2;

import xyz.ninesoft.hfsm.IEVENT_BASE;

/**
 * Created by illiacDev on 2017-12-22.
 */

public interface IEVENT extends IEVENT_BASE {
    IEVENT_BASE operate();
}
