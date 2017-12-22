package xyz.ninesoft.bleremocon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        setTitle("test");
        setLogo(R.drawable.ic_action_logo);
    }

    @Override
    protected BaseFragment getContentFragment() {
        return new Content();
    }

    static public class Content extends BaseFragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
//            return super.onCreateView(inflater, container, savedInstanceState);
            View inflate = inflater.inflate(R.layout.main, container, false);
//            TextView view = new TextView(getActivity());
//            view.setText("Content!!");
            return inflate;
        }
    }
}
