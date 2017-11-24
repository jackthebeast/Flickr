package it.jgrassi.flickr.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.text.Html;
import android.text.Spanned;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import it.jgrassi.flickr.model.Post;

/**
 * Created by jacop on 24/11/2017.
 */

public class DetailViewModel extends ViewModel {
    private Post post;

    @BindingAdapter({"detailSource"})
    public static void loadDetail(ImageView imageView, String url) {
        if(url != null)
            Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    public Spanned getDescription(){
        return Html.fromHtml(post.description);
    }

    public String getDetailUrl(){
        return post.media.m;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
