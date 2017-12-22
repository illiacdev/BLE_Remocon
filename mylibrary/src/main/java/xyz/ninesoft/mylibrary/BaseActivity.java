package xyz.ninesoft.mylibrary;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import xyz.ninesoft.mylibrary.databinding.ActivityBaseBinding;

public abstract class BaseActivity extends AppCompatActivity {

    private ActivityBaseBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_base);
        setSupportActionBar(bind.toolBar);

        bindContentFragment();

    }

    private static String getTag() {
        return BaseFragment.class.getSimpleName();
    }

    private void bindContentFragment() {
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(
                getTag());
        if (fragment == null) {
            fragment = getContentFragment();

            getSupportFragmentManager().beginTransaction().add(
                    R.id.place_holder, fragment, getTag())
                    .commit();
        }
    }

    protected abstract BaseFragment getContentFragment();

    protected void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    protected void setLogo(int resID) {
        bind.toolBar.setLogo(resID);
    }
}
