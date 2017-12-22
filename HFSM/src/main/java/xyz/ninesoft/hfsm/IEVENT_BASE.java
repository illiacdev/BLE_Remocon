package xyz.ninesoft.hfsm;

/**
 * Created by illiacDev on 2017-08-03.
 */

public interface IEVENT_BASE {
    IEVENT_BASE entry();
    IEVENT_BASE exit();
    IEVENT_BASE doAction();
    IEVENT_BASE showID();
    IEVENT_BASE init();
    IEVENT_BASE empty();
    IEVENT_BASE getParent();
}
