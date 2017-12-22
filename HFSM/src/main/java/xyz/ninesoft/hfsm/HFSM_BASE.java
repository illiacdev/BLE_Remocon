package xyz.ninesoft.hfsm;

import java.lang.reflect.Field;
import java.util.Stack;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by illiacDev on 2017-08-03.
 */

public abstract class HFSM_BASE<IEVENT extends IEVENT_BASE, IOUT extends IOUT_BASE> {
    public abstract class State implements IEVENT_BASE {

        protected String getID(Object obj) {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    if (field.get(thisPtr) == obj)
                        return field.getName();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        public void emit_entry() {
            if (bMonitor)
                subject.onNext(iout -> iout.onEntry(getID(this)));

        }

        public void emit_exit() {
            if (bMonitor)
                subject.onNext(iout -> iout.onExit(getID(this)));
        }

        public void emit_init() {
            if (bMonitor)
                subject.onNext(iout -> iout.onInit(getID(this)));
        }

        @Override
        public IEVENT_BASE entry() {
            return getParent();
        }

        @Override
        public IEVENT_BASE exit() {
            return getParent();
        }

        @Override
        public IEVENT_BASE doAction() {
            return getParent();
        }

        @Override
        public IEVENT_BASE showID() {
            return getParent();
        }

        @Override
        public IEVENT_BASE init() {
            return getParent();
        }

        @Override
        public IEVENT_BASE empty() {
            return getParent();
        }

    }

    protected IEVENT_BASE pseudoInital = new State() {

        @Override
        public IEVENT_BASE init() {
            System.out.println("top-INIT;");
            current = obtainInitalState();
            return null;
        }

        @Override
        public IEVENT_BASE getParent() {
            return null;
        }

    };

    public void setMonitor() {
        this.bMonitor = true;
    }

    protected boolean bMonitor = false;

    Class cls;
    HFSM_BASE thisPtr;

    public HFSM_BASE() {
        thisPtr = this;
        cls = getClass();
    }

    protected PublishSubject<Consumer<IOUT>> subject = PublishSubject.create();
    public Observable<Consumer<IOUT>> observable = subject;
    public IEVENT current = null;
    protected IEVENT source = null;

    /*public void transit(IEVENT target) {
        transit(target, () -> {
            //NOP
        });
    }

    public void transit(IEVENT target, Action1<IOUT> action0) {
        transit(target, () -> subject.onNext(action0));
    }

    public void transit(IEVENT target, Action0 action0) {
        current.exit();
        action0.call();
        current = target;
        current.entry();
        current.doAction();
    }*/

    public void transit_neo(IEVENT target) {
        target.showID();
//        IEVENT s;
        IEVENT_BASE s, t, p, q;
        IEVENT_BASE[] entry = new IEVENT_BASE[7];

        //curr -> source 까지 exit (source 제외)
        for (s = current; s != source; ) {
            t = s.exit();
            if (t != null)
                s = t; //해당상태의 exit handler 가 정상적으로 null을 리턴하면 관행적으로 처리
            else
                s = s.empty(); //해당상태의 exit handler 가 없거나 기타의 경우 명시적으로 상위상태 획득.
        }

        Stack<IEVENT> e = new Stack<>();
        e.push(target);

        // (a) check mySource == target (transition to self)
        if (source == target) {
            source.exit();
            //TRIGGER(mySource, Q_EXIT_SIG);                   // exit source
//      goto inLCA;
            inLCA(e, target); //lca의 직접 하위상태부터 target 까지의 상태스택.
            return;
        }
        // (b) check mySource == target->super
        p = target.empty();
        if (source == p) {
//      goto inLCA;
            inLCA(e, target);
            return;
        }

        // (c) check mySource->super == target->super (most common)
        q = source.empty();
        if (q == p) {
            source.exit();
//      goto inLCA;
            inLCA(e, target);
            return;
        }

        // (d) check mySource->super == target
        if (q == target) {
            source.exit();                   // exit source
            e.pop();                                    // do not enter the LCA
            inLCA(e, target);
            return;
        }

        // (e) check rest of mySource == target->super->super... hierarchy
        e.push((IEVENT) p);
        for (s = p.empty(); s != null;
             s = s.empty()) {
            if (source == s) {
//         goto inLCA;
                inLCA(e, target);
                return;
            }
            e.push((IEVENT) s);
        }
        source.exit();                      // exit source

        // (f) check rest of mySource->super == target->super->super...
        Stack<IEVENT> lca = (Stack<IEVENT>) e.clone();
        for (; !lca.empty(); lca.pop()) {
            if (q == lca.peek()) {
                lca.pop();                         // do not enter the LCA
                e = (Stack<IEVENT>) lca.clone();
//         goto inLCA;
                inLCA(e, target);
                return;
            }

        }

        // (g) check each mySource->super->super..for each target...
        for (s = q; s != null; s = s.empty()) {
            for (lca = (Stack<IEVENT>) e.clone(); !lca.empty();
                 lca.pop()) {
                if (s == lca.peek()) {
                    lca.pop();
                    e = (Stack<IEVENT>) lca.clone();

//            goto inLCA;
                    inLCA(e, target);
                    return;
                }
            }
            s.exit();                               // exit s
        }
    }

    private void inLCA(Stack<IEVENT> e, IEVENT target) {
        while (!e.empty())
            e.pop().entry(); //lca 하위상태부터 target 까지 entry
        current = target;
        while (target.init() == null) { //target 부터 init 가능한 하위상태까지 init;
            target = current;
            target.entry();
        }
    }

    public void dispatch(Function<IEVENT, IEVENT_BASE> event) {
        try {
            for (source = current;
                 source != null;
                 source = (IEVENT) event.apply(source))
                ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String getStateName() {
        StackTraceElement stack = new Throwable().getStackTrace()[5];
        return stack.getMethodName();
    }

    protected IEVENT top = null;

    protected abstract IEVENT obtainInitalState();

    public void run() {
        current = top;
        source = (IEVENT) pseudoInital;
        source.init();
        current.entry();

        while (current.init() == null)
            current.entry();

    }
}
