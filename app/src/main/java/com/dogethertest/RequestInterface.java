package com.dogethertest;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by HP on 04/03/18.
 */

public interface RequestInterface {

    @GET("repos/{user}/{repo}/issues")
    Observable<List<RepoIssues>> register(@Path("user") String user,@Path("repo") String repo);
}
