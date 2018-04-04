package com.dogethertest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Issues extends AppCompatActivity {

        public static final String BASE_URL = "https://api.github.com/";

        private RecyclerView mRecyclerView;

        private CompositeDisposable mCompositeDisposable;

        private DataAdapter mAdapter;

        private ArrayList<RepoIssues> mAndroidArrayList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_issues);

            mCompositeDisposable = new CompositeDisposable();
            initRecyclerView();
            loadJSON();
        }

        private void initRecyclerView() {

            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            mRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(layoutManager);
        }

        private void loadJSON() {
            MyDialog_Progress.open(Issues.this);
            RequestInterface requestInterface = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(RequestInterface.class);
            Intent intent = getIntent();
            String user=intent.getStringExtra("user");
            String repo=intent.getStringExtra("repo");


            mCompositeDisposable.add(requestInterface.register(user,repo)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse,this::handleError));

        }

        private void handleResponse(List<RepoIssues> androidList) {

            mAndroidArrayList = new ArrayList<>(androidList);
            mAdapter = new DataAdapter(mAndroidArrayList);
            mRecyclerView.setAdapter(mAdapter);
            MyDialog_Progress.close(Issues.this);
        }

        private void handleError(Throwable error) {
            MyDialog_Progress.close(Issues.this);
            Toast.makeText(this, "Enter a valid user & repository", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mCompositeDisposable.clear();
        }
    }

