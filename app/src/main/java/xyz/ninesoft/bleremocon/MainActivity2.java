package xyz.ninesoft.bleremocon;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.ninesoft.bleremocon.databinding.MainBinding;
import xyz.ninesoft.mylibrary.BaseActivity;
import xyz.ninesoft.mylibrary.BaseFragment;

/**
 * Created by illiacDev on 2017-12-22.
 */

public class MainActivity2 extends BaseActivity {
    private static final String TAG = "BL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("BLE 리모콘 UI 데모");
        setLogo(R.drawable.ic_action_logo);
    }

    @Override
    protected BaseFragment getContentFragment() {
        return new Content();
    }

    static public class Content extends BaseFragment {
        MainBinding bind;
        MonitorView.Probe probe;
        MonitorView.Probe probe1;
        private Thread thread = null;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            bind = DataBindingUtil.inflate(inflater, R.layout.main, container,
                                           false);
            bind.button3.setOnClickListener(v -> runThread());



            probe = new MonitorView.Probe();
            probe.color = Color.YELLOW;

            probe1 = new MonitorView.Probe();
            probe1.color = Color.WHITE;

            bind.monitor.addProbe(probe);
            bind.monitor.addProbe(probe1);
//            bind.monitor.start(30);
            return bind.getRoot();
        }

        private void runThread() {
            if(thread != null){
                thread.interrupt();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                thread = null;
//                bind.button3.setText("START");
                bind.monitor.stop();
                bind.monitor.clear();
                return;
            }
            bind.monitor.start(30);

            bind.button3.setText("STOP");
            thread = new Thread(() -> {
                float deg = 0;
                int height = bind.monitor.getHeight();

                while (true) {
                    deg += Math.PI / 180 * 5;
                    deg %= 2 * Math.PI;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }

                    double sin = Math.sin(deg);
                    double fx = LinerInterpolate(sin);

                    probe.putData((float) fx);
                    bind.setData2(String.format("%5.2f", sin));

                    double cos = Math.cos(deg);
                    double fx2 = LinerInterpolate(cos);

                    probe1.putData((float) fx2);

                    bind.setData(String.format("%5.2f", cos));

                }
                bind.button3.setText("START");

            });
            thread.start();
        }

        private double LinerInterpolate(double sin) {
            double fx1 = 0;
            double fx2 = 1;
            double a = (1 - sin) / 2;
            double b = (2 - (1 - sin)) / 2;
            return b * fx1 + a * fx2;
        }
    }
}
