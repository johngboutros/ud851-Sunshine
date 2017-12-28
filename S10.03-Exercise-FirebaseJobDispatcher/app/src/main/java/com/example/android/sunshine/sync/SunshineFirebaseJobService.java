package com.example.android.sunshine.sync;/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// (2) Make sure you've imported the jobdispatcher.JobService, not job.JobService

import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

// (3) Add a class called SunshineFirebaseJobService that extends jobdispatcher.JobService
public class SunshineFirebaseJobService extends JobService {

    // (4) Declare an ASyncTask field called mFetchWeatherTask
    AsyncTask mFetchWeatherTask;


    // (5) Override onStartJob and within it, spawn off a separate ASyncTask to sync weather data
    /**
     * The entry point to your Job. Implementations should offload work to another thread of execution
     * as soon as possible because this runs on the main thread. If work was offloaded, call {@link
     * JobService#jobFinished(JobParameters, boolean)} to notify the scheduling service that the work
     * is completed.
     * <p>
     * <p>If a job with the same service and tag was rescheduled during execution {@link
     * JobService#onStopJob(JobParameters)} will be called and the wakelock will be released. Please
     * make sure that all reschedule requests happen at the end of the job.
     *
     * @param job
     * @return {@code true} if there is more work remaining in the worker thread, {@code false} if the
     * job was completed.
     */
    @Override
    public boolean onStartJob(final JobParameters job) {

        mFetchWeatherTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                SunshineSyncTask.syncWeather(SunshineFirebaseJobService.this);
                return null;
            }

            // (6) Once the weather data is sync'd, call jobFinished with the appropriate arguments
            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false);
            }
        };

        mFetchWeatherTask.execute();

        return true;
    }


    // (7) Override onStopJob, cancel the ASyncTask if it's not null and return true
    /**
     * Called when the scheduling engine has decided to interrupt the execution of a running job, most
     * likely because the runtime constraints associated with the job are no longer satisfied. The job
     * must stop execution.
     *
     * @param job
     * @return true if the job should be retried
     * @see JobInvocation.Builder#setRetryStrategy(RetryStrategy)
     * @see RetryStrategy
     */
    @Override
    public boolean onStopJob(JobParameters job) {
        if (mFetchWeatherTask != null) mFetchWeatherTask.cancel(true);
        return true;
    }




}