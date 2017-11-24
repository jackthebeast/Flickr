package it.jgrassi.flickr.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.List;

import it.jgrassi.flickr.R;
import it.jgrassi.flickr.model.Post;
import it.jgrassi.flickr.viewmodel.GalleryViewModel;

/**
 * Created by j.grassi on 24/11/2017.
 */

public class GalleryActivity extends AppCompatActivity {
    private GalleryViewModel viewModel;
    private RecyclerView postList;
    private ProgressBar progress;
    private TextView error;
    private EditText search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        viewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);

        viewModel.setErrorVisibility(View.GONE);
        viewModel.setImageListVisibility(View.GONE);
        viewModel.setProgressVisibility(View.GONE);

        postList = (RecyclerView) findViewById(R.id.gallery_list);
        postList.setLayoutManager(new LinearLayoutManager(this));

        progress = (ProgressBar) findViewById(R.id.gallery_progress);
        error = (TextView) findViewById(R.id.gallery_error);
        search = (EditText) findViewById(R.id.search_text);

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setTags(s.toString());
            }
        });

        viewModel.getPostList().observeForever(new Observer<List<Post>>() {
            @Override
            public void onChanged(@Nullable List<Post> posts) {
                PostAdapter adapter = new PostAdapter();
                adapter.setList(posts);
                postList.setAdapter(adapter);
                postList.getAdapter().notifyDataSetChanged();
            }
        });

        viewModel.getProgressVisibility().observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer visibility) {
                progress.setVisibility(visibility);
            }
        });

        viewModel.getImageListVisibility().observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer visibility) {
                postList.setVisibility(visibility);
            }
        });

        viewModel.getErrorVisibility().observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer visibility) {
                error.setVisibility(visibility);
            }
        });

        viewModel.getError().observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String text) {
                error.setText(text);
            }
        });

        viewModel.loadFeed();
    }
}
