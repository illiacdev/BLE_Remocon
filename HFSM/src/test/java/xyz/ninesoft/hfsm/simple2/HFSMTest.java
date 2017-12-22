package xyz.ninesoft.hfsm.simple2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import xyz.ninesoft.hfsm.IOUT_BASE;

/**
 * Created by illiacDev on 2017-12-22.
 */
public class HFSMTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    private final IOUT_BASE t1 = new IOUT_BASE() {

        @Override
        public void onShowID(String is) {
            System.out.println("id is :" + is);
        }

        @Override
        public void onEntry(String stateName) {
            System.out.println(stateName + "-ENTRY");
        }

        @Override
        public void onExit(String stateName) {
            System.out.println(stateName + "-EXIT");
        }

        @Override
        public void onInit(String stateName) {
            System.out.println(stateName + "-INIT");
        }

    };


    @Test
    public void function()
    {
        HFSM hfsm = new HFSM();
        hfsm.setMonitor();
        hfsm.observable.subscribe(iout_baseConsumer -> iout_baseConsumer.accept(t1));

        hfsm.run();
        hfsm.current.showID();

        hfsm.dispatch(IEVENT::operate);
        hfsm.dispatch(IEVENT::operate);
    }
}