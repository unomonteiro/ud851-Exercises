package com.example.android.asynctaskloader;

import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.view.View;

import com.example.android.asynctaskloader.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public static class GitHubLoader extends AsyncTaskLoader<String> {
    // COMPLETED (1) Create a String member variable called mGithubJson that will store the raw JSON
    /* This String will contain the raw JSON from the results of our Github search */
    String mGithubJson;

    @Override
    protected void onStartLoading() {

        /* If no arguments were passed, we don't have a query to perform. Simply return. */
        if (args == null) {
            return;
        }

        /*
         * When we initially begin loading in the background, we want to display the
         * loading indicator to the user
         */
        mLoadingIndicator.setVisibility(View.VISIBLE);

        // COMPLETED (2) If mGithubJson is not null, deliver that result. Otherwise, force a load
        /*
         * If we already have cached results, just deliver them now. If we don't have any
         * cached results, force a load.
         */
        if (mGithubJson != null) {
            deliverResult(mGithubJson);
        } else {
            forceLoad();
        }
    }

    @Override
    public String loadInBackground() {

        /* Extract the search query from the args using our constant */
        String searchQueryUrlString = args.getString(MainActivity.SEARCH_QUERY_URL_EXTRA);

        /* If the user didn't enter anything, there's nothing to search for */
        if (searchQueryUrlString == null || TextUtils.isEmpty(searchQueryUrlString)) {
            return null;
        }

        /* Parse the URL from the passed in String and perform the search */
        try {
            URL githubUrl = new URL(searchQueryUrlString);
            String githubSearchResults = NetworkUtils.getResponseFromHttpUrl(githubUrl);
            return githubSearchResults;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // COMPLETED (3) Override deliverResult and store the data in mGithubJson
    // COMPLETED (4) Call super.deliverResult after storing the data
    @Override
    public void deliverResult(String githubJson) {
        mGithubJson = githubJson;
        deliverResult(githubJson);
    }
}
