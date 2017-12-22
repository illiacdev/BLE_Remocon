package xyz.ninesoft.bleremocon;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import xyz.ninesoft.bleremocon.databinding.ActivityMainBinding;
import xyz.ninesoft.mylibrary.BaseFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolBar);
        binding.toolBar.setLogo(R.drawable.ic_action_logo);


//        getSupportActionBar().setLogo(R.drawable.logo);

//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.logo);

//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.logo);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle("test");
    }




}
