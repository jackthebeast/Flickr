package it.jgrassi.flickr.view;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import it.jgrassi.flickr.R;
import it.jgrassi.flickr.databinding.ItemPostBinding;
import it.jgrassi.flickr.model.Post;

/**
 * Created by j.grassi on 24/11/2017.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>  {
    private List<Post> list;


    public static class PostViewHolder extends RecyclerView.ViewHolder{
        final ItemPostBinding binding;

        public PostViewHolder(ItemPostBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindPost(Post post){
            binding.setPost(post);
        }

        @BindingAdapter({"thumbnailSource"})
        public static void loadThumbnail(ImageView imageView, String url) {
            if(url != null)
                Glide.with(imageView.getContext()).load(url).into(imageView);
        }

    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPostBinding itemPostBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_post, parent, false);
        return new PostViewHolder(itemPostBinding);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.bindPost(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setList(List<Post> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
