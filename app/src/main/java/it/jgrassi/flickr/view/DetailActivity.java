package it.jgrassi.flickr.view;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import it.jgrassi.flickr.R;
import it.jgrassi.flickr.databinding.ActivityDetailBinding;
import it.jgrassi.flickr.model.Post;
import it.jgrassi.flickr.viewmodel.DetailViewModel;

/**
 * Created by jacop on 24/11/2017.
 */

public class DetailActivity extends AppCompatActivity implements Animation.AnimationListener {

    private static final String PARAM_POST = "PARAM_POST";
    private DetailViewModel viewModel;
    private ActivityDetailBinding binding;
    private View scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);


        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

        Post post = (Post) getIntent().getSerializableExtra(PARAM_POST);
        viewModel.setPost(post);

        binding.setViewModel(viewModel);

        scrollView = findViewById(R.id.detail_scroll);

        View mainView = findViewById(R.id.detail_main);
        mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(scrollView.getVisibility() == View.VISIBLE)
                    fadeOut();
                else
                    fadeIn();
            }
        });
    }

    private void fadeIn(){
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeInAnimation.setAnimationListener(this);
        scrollView.startAnimation(fadeInAnimation);
    }

    private void fadeOut(){
        Animation fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeOutAnimation.setAnimationListener(this);
        scrollView.startAnimation(fadeOutAnimation);
    }

    public static void launchDetail(Context context, Post post, Pair<View, String>... sharedElements) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(PARAM_POST, post);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)context, sharedElements );

        context.startActivity(intent, options.toBundle());
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(scrollView.getVisibility() == View.VISIBLE)
            scrollView.setVisibility(View.INVISIBLE);
        else
            scrollView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }



    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
