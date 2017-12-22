package xyz.ninesoft.hfsm;

/**
 * Created by illiacDev on 2017-08-03.
 */

public interface IOUT_BASE {
    void onShowID(String stateName);
    void onEntry(String stateName);
    void onExit(String stateName);
    void onInit(String stateName);
}
