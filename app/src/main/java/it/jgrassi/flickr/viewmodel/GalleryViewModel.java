package it.jgrassi.flickr.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;

import java.util.List;

import it.jgrassi.flickr.data.PostFactory;
import it.jgrassi.flickr.data.PostService;
import it.jgrassi.flickr.model.FeedResponse;
import it.jgrassi.flickr.model.Post;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by j.grassi on 24/11/2017.
 */

public class GalleryViewModel extends ViewModel implements Callback<FeedResponse> {
    private MutableLiveData<List<Post>> postList;
    private MutableLiveData<Integer> progressVisibility = new MutableLiveData<>();
    private MutableLiveData<Integer> imageListVisibility = new MutableLiveData<>();
    private MutableLiveData<Integer> errorVisibility = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<String> tags = new MutableLiveData<>();


    public void loadFeed(){
        setProgressVisibility(View.VISIBLE);
        setImageListVisibility(View.GONE);
        setErrorVisibility(View.GONE);

        Retrofit retrofit = PostFactory.create();

        PostService service = retrofit.create(PostService.class);

        Call<FeedResponse> call = service.fetchFeed("json", tags.getValue());

        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
        setPostList(response.body().items);
        setProgressVisibility(View.GONE);
        if(response.body() == null || response.body().items.size() == 0){
            setImageListVisibility(View.GONE);
            setErrorVisibility(View.VISIBLE);
            setError("No post available for the selected tags");
        }else {
            setErrorVisibility(View.GONE);
            setImageListVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailure(Call<FeedResponse> call, Throwable t) {
        setProgressVisibility(View.GONE);
        setImageListVisibility(View.GONE);
        setErrorVisibility(View.VISIBLE);
        setError(t.getMessage());
    }


    public MutableLiveData<List<Post>> getPostList() {
        if(postList == null)
            postList = new MutableLiveData<>();
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList.postValue(postList);
    }

    public MutableLiveData<Integer> getProgressVisibility() {
        return progressVisibility;
    }

    public void setProgressVisibility(Integer progressVisibility) {
        this.progressVisibility.postValue(progressVisibility);
    }

    public MutableLiveData<Integer> getImageListVisibility() {
        return imageListVisibility;
    }

    public void setImageListVisibility(Integer imageListVisibility) {
        this.imageListVisibility.postValue(imageListVisibility);
    }

    public MutableLiveData<Integer> getErrorVisibility() {
        return errorVisibility;
    }

    public void setErrorVisibility(Integer errorVisibility) {
        this.errorVisibility.postValue(errorVisibility);
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public void setError(String error) {
        this.error.postValue(error);
    }

    public MutableLiveData<String> getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags.setValue(tags.replaceAll(" ", ","));
        loadFeed();
    }
}
