package com.mkenlo.activefit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mkenlo.activefit.db.ActiveFitRepository;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    public @BindView(R.id.btn_app_start)
    Button mAppStart;
    ActiveFitRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        mRepository = ((BaseApp)getApplication()).getRepository();
        mAppStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startApp();
            }
        });

    }

    void startApp(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                //ActiveFitRepository repository = ((BaseApp)getApplication()).getRepository();
                if(mRepository.isProfileSetup())
                    intent = new Intent(getBaseContext(), MainActivity.class);
                else
                    intent = new Intent(getBaseContext(), ProfileSetupActivity.class);

                startActivity(intent);
            }
        });
    }

}
