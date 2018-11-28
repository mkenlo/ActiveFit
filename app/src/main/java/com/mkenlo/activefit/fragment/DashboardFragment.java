package com.mkenlo.activefit.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.HistoryClient;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.mkenlo.activefit.AppExecutors;
import com.mkenlo.activefit.BaseApp;
import com.mkenlo.activefit.R;
import com.mkenlo.activefit.RecordWorkoutActivity;
import com.mkenlo.activefit.db.ActiveFitRepository;
import com.mkenlo.activefit.db.model.DailySteps;
import com.mkenlo.activefit.db.model.UserProfile;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DashboardFragment extends Fragment {
    private static final int REQUEST_OAUTH_REQUEST_CODE = 0x1001;
    public @BindView(R.id.fab_start_workout)  FloatingActionButton mStartWorkout;
    public @BindView(R.id.tv_today_steps)     TextView mTodaySteps;
    public @BindView(R.id.tv_today_calories_burned)  TextView mTodayCalories;
    public @BindView(R.id.tv_today_distance)  TextView mTodayDistance;



    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, rootView);
        mStartWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),
                        RecordWorkoutActivity.class);
                startActivity(intent);
            }
        });


        FitnessOptions fitnessOptions =
                FitnessOptions.builder()
                        .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                        .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
                        .addDataType(DataType.TYPE_DISTANCE_CUMULATIVE)
                        .addDataType(DataType.TYPE_DISTANCE_DELTA)
                        .addDataType(DataType.AGGREGATE_ACTIVITY_SUMMARY)
                        .build();

        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(getContext()), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this,
                    REQUEST_OAUTH_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(getContext()),
                    fitnessOptions);
        } else {
            subscribe();
        }


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_OAUTH_REQUEST_CODE) {
                subscribe();
            }
        }
    }

    /** Records step data by requesting a subscription to background step data. */
    public void subscribe() {
        // To create a subscription, invoke the Recording API. As soon as the subscription is
        // active, fitness data will start recording.
        Fitness.getRecordingClient(getActivity(), GoogleSignIn.getLastSignedInAccount(getContext()))
                .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(
                                            getContext(),
                                            "Thank you for you subscription",
                                            Toast.LENGTH_SHORT);
                                } else {
                                    Toast.makeText(
                                            getContext(),
                                            "Authorization Failed",
                                            Toast.LENGTH_SHORT);
                                    Log.d(
                                            "USER AUTHORIZATION",
                                            "There was a problem subscribing.",
                                            task.getException());
                                }
                            }
                        });
        getTodayStepsCount();
        getTodayCalories();
        getTodayDistance();
    }


    private void getTodayStepsCount() {
        Fitness.getHistoryClient(
                getActivity(), GoogleSignIn.getLastSignedInAccount(getContext()))
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                long total =
                                        dataSet.isEmpty()
                                                ? 0
                                                : dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
                                mTodaySteps.setText(String.valueOf(total));
                                saveStepInDB(total);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(
                                        getContext(),
                                        "Authorization Failed. Unable to get step Count",
                                        Toast.LENGTH_SHORT);
                                mTodaySteps.setText(0);

                            }
                        });


    }

    private void getTodayCalories(){
        Fitness.getHistoryClient(
                getActivity(), GoogleSignIn.getLastSignedInAccount(getContext()))
                .readDailyTotal(DataType.TYPE_CALORIES_EXPENDED)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                float total = dataSet.isEmpty()
                                        ? 0 : dataSet.getDataPoints().get(0).getValue(
                                                Field.FIELD_CALORIES).asFloat();
                                int roundTotal = Math.round(total);
                                mTodayCalories.setText(String.valueOf(roundTotal)+" Cal");
                            }
                        }
                )
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(
                                        getContext(),
                                        "Authorization Failed. Unable to get step Count",
                                        Toast.LENGTH_SHORT);
                                mTodayCalories.setText(0+" Cal");

                            }
                        });
    }

    private void getTodayDistance(){
        Fitness.getHistoryClient(
                getActivity(), GoogleSignIn.getLastSignedInAccount(getContext()))
                .readDailyTotal(DataType.TYPE_DISTANCE_DELTA)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                Log.d("Distance:", dataSet.getDataPoints().toString());
                                float total = dataSet.isEmpty()
                                        ? 0 : dataSet.getDataPoints().get(0).getValue(
                                        Field.FIELD_DISTANCE).asFloat();
                                int roundTotal = Math.round(total);
                                mTodayDistance.setText(String.valueOf(roundTotal)+" m");
                            }
                        }
                )
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(
                                        getContext(),
                                        "Authorization Failed. Unable to get step Count",
                                        Toast.LENGTH_SHORT);
                                Log.d("DISTANCE", "Failed" );
                                mTodayDistance.setText(0+" m");



                            }
                        });
    }

    void saveStepInDB(final long steps){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        final String today = sdf.format(new Date());
        //System.out.println(today); //15/10/2013
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ActiveFitRepository repo = ((BaseApp) getActivity().getApplication()).getRepository();
                DailySteps todayActivity = repo.getTodayActivity();
                if(todayActivity == null){
                    todayActivity = new DailySteps();
                    todayActivity.setCount((int)steps);
                    todayActivity.setDate(today);
                    repo.insertTodayActivity(todayActivity);
                    Log.d("SAVE_IN_DATABASE", "this is a new day");
                }else if(steps > todayActivity.getCount()){
                    todayActivity.setCount((int) steps);
                    repo.updateTodayActivity(todayActivity);
                    Log.d("SAVE_IN_DATABASE", "this is update the day");
                }

            }
        });
    }
}
