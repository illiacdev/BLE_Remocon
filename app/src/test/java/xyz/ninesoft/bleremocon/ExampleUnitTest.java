package xyz.ninesoft.bleremocon;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void function() throws InterruptedException {
        System.out.println("start!");
        Observable.timer(100, TimeUnit.MILLISECONDS).subscribe(aLong -> {
            System.out.println(aLong);
        });

        Observable<Long> interval = Observable.interval(500, TimeUnit.MILLISECONDS);
        interval.subscribe(aLong -> {
            System.out.println(aLong);
                           }
        );

        Thread.sleep(3000);
        System.out.println("end");
    }

    @Test
    public void function2()
    {
        Queue<Integer> integers = new ArrayDeque<>();
//        Deque<Integer> integers = new ArrayDeque<>();
        integers.add(1);
        integers.add(2);
        assertThat(integers.size(),is(2));
        Integer pop = integers.poll();
        assertThat(pop,is(1));
        assertThat(integers.size(),is(1));

    }
}