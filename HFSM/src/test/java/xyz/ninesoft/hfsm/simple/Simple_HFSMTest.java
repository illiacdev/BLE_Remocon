package xyz.ninesoft.hfsm.simple;

import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.inOrder;

/**
 * Created by illiacDev on 2017-08-04.
 */
public class Simple_HFSMTest {

    private final IOUT t1 = new IOUT() {

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
    public void init_s0tos1() throws Exception {
        Simple_HFSM simple_hfsm = new Simple_HFSM();
        simple_hfsm.setMonitor();

        IOUT mock = mock(IOUT.class);
        InOrder inOrder = inOrder(mock);

        simple_hfsm.observable.subscribe(ioutAction1 -> ioutAction1.accept((t1)));

        simple_hfsm.observable.subscribe(ioutAction1 -> ioutAction1.accept(mock));

        simple_hfsm.run();

        inOrder.verify(mock).onEntry("s0");
        inOrder.verify(mock).onInit("s0");
        inOrder.verify(mock).onEntry("s1");
        inOrder.verify(mock).onInit("s1");
        inOrder.verify(mock).onEntry("s11");

//        inOrder.verify(mock).onInit("s11");

        simple_hfsm.current.showID();

    }

    @Test
    public void event_a() throws Exception {

        Simple_HFSM simple_hfsm = new Simple_HFSM();
        simple_hfsm.setMonitor();

        IOUT mock = mock(IOUT.class);
        InOrder inOrder = inOrder(mock);

        simple_hfsm.observable.subscribe(ioutAction1 -> ioutAction1.accept(t1));

        simple_hfsm.observable.subscribe(ioutAction1 -> ioutAction1.accept(mock));

        simple_hfsm.run();

        simple_hfsm.dispatch(IEVENT::a);

        inOrder.verify(mock).onExit("s11");
        inOrder.verify(mock).onExit("s1");
        inOrder.verify(mock).onEntry("s1");
        inOrder.verify(mock).onInit("s1");
        inOrder.verify(mock).onEntry("s11");

        simple_hfsm.current.showID();

    }

    @Test
    public void event_b() throws Exception {

        Simple_HFSM simple_hfsm = new Simple_HFSM();
        simple_hfsm.setMonitor();

        IOUT mock = mock(IOUT.class);
        InOrder inOrder = inOrder(mock);

        simple_hfsm.observable.subscribe(ioutAction1 -> ioutAction1.accept(t1));

        simple_hfsm.observable.subscribe(ioutAction1 -> ioutAction1.accept(mock));

        simple_hfsm.run();

        System.out.println("-------------------------------------------");
        simple_hfsm.dispatch(IEVENT::b);

        inOrder.verify(mock).onExit("s11");
        inOrder.verify(mock).onEntry("s11");

//        simple_hfsm.current.showID();
//
    }

    @Test
    public void event_c() throws Exception {
        Simple_HFSM simple_hfsm = new Simple_HFSM();
        simple_hfsm.setMonitor();

        IOUT mock = mock(IOUT.class);
        InOrder inOrder = inOrder(mock);

        simple_hfsm.observable.subscribe(ioutAction1 -> ioutAction1.accept(t1));

        simple_hfsm.observable.subscribe(ioutAction1 -> ioutAction1.accept(mock));

        simple_hfsm.run();

        simple_hfsm.dispatch(IEVENT::c);

        inOrder.verify(mock).onExit("s11");
        inOrder.verify(mock).onExit("s1");
        inOrder.verify(mock).onEntry("s2");
        inOrder.verify(mock).onInit("s2");
        inOrder.verify(mock).onEntry("s21");
        inOrder.verify(mock).onInit("s21");
        inOrder.verify(mock).onEntry("s211");

        simple_hfsm.current.showID();

    }

    @Test
    public void event_d() throws Exception {
        Simple_HFSM simple_hfsm = new Simple_HFSM();
        simple_hfsm.setMonitor();

        IOUT mock = mock(IOUT.class);
        InOrder inOrder = inOrder(mock);

        simple_hfsm.observable.subscribe(ioutAction1 -> ioutAction1.accept(t1));

        simple_hfsm.observable.subscribe(ioutAction1 -> ioutAction1.accept(mock));

        simple_hfsm.run();
        System.out.println("-------------------------------------------");
        simple_hfsm.dispatch(IEVENT::d);

        inOrder.verify(mock).onExit("s11");
        inOrder.verify(mock).onExit("s1");
        inOrder.verify(mock).onInit("s0");
        inOrder.verify(mock).onEntry("s1");
        inOrder.verify(mock).onEntry("s11");

        simple_hfsm.current.showID();

    }

    @Test
    public void event_e() throws Exception {
        Simple_HFSM simple_hfsm = new Simple_HFSM();
        simple_hfsm.setMonitor();

        IOUT mock = mock(IOUT.class);
        InOrder inOrder = inOrder(mock);

        simple_hfsm.observable.subscribe(ioutAction1 -> ioutAction1.accept(t1));

        simple_hfsm.observable.subscribe(ioutAction1 -> ioutAction1.accept(mock));

        simple_hfsm.run();
        System.out.println("-------------------------------------------");
        simple_hfsm.dispatch(IEVENT::e);

        inOrder.verify(mock).onExit("s11");
        inOrder.verify(mock).onExit("s1");
        inOrder.verify(mock).onEntry("s2");
        inOrder.verify(mock).onEntry("s21");
        inOrder.verify(mock).onEntry("s211");

        simple_hfsm.current.showID();

    }

    @Test
    public void event_f() throws Exception {
        Simple_HFSM simple_hfsm = new Simple_HFSM();
        simple_hfsm.setMonitor();

        IOUT mock = mock(IOUT.class);
        InOrder inOrder = inOrder(mock);

        simple_hfsm.observable.subscribe(ioutAction1 -> ioutAction1.accept(t1));

        simple_hfsm.observable.subscribe(ioutAction1 -> ioutAction1.accept(mock));

        simple_hfsm.run();
        System.out.println("-------------------------------------------");
        simple_hfsm.dispatch(IEVENT::f);

        inOrder.verify(mock).onExit("s11");
        inOrder.verify(mock).onExit("s1");
        inOrder.verify(mock).onEntry("s2");
        inOrder.verify(mock).onEntry("s21");
        inOrder.verify(mock).onEntry("s211");
        simple_hfsm.current.showID();

    }

    @Test
    public void event_g() throws Exception {
        Simple_HFSM simple_hfsm = new Simple_HFSM();
        simple_hfsm.setMonitor();

        IOUT mock = mock(IOUT.class);
        InOrder inOrder = inOrder(mock);

        simple_hfsm.observable.subscribe(ioutAction1 -> ioutAction1.accept(t1));

        simple_hfsm.observable.subscribe(ioutAction1 -> ioutAction1.accept(mock));

        simple_hfsm.run();
        System.out.println("-------------------------------------------");
        simple_hfsm.dispatch(IEVENT::g);

        inOrder.verify(mock).onExit("s11");
        inOrder.verify(mock).onExit("s1");
        inOrder.verify(mock).onEntry("s2");
        inOrder.verify(mock).onEntry("s21");
        inOrder.verify(mock).onEntry("s211");

        simple_hfsm.current.showID();

    }

}