package com.example.parseinstagram.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parseinstagram.Post;
import com.example.parseinstagram.PostsAdapter;
import com.example.parseinstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends PostsFragment {
    private static final String TAG = "ProfileFragment";

    private TextView tvName;
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> mPosts;

    private CircleImageView civProfileImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        civProfileImage = view.findViewById(R.id.civProfileImage);
        tvName = view.findViewById(R.id.tvName);
        rvPosts = view.findViewById(R.id.rvPosts);
        //create data source
        mPosts = new ArrayList<>();
        //create adapter
        adapter = new PostsAdapter(getContext(), mPosts);
        //set the adapter on the recycler view
        rvPosts.setAdapter(adapter);
        //set the layout manager on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        ParseUser user = ParseUser.getCurrentUser();
        Post post = new Post();
        post.setKeyUser(user);
        tvName.setText(post.getKeyUser().getUsername());
        queryPosts();
    }

    @Override
    protected void queryPosts() {
       // super.queryPosts();
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        //View posts from recent to oldest
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null){
                    Log.d(TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                mPosts.addAll(posts);
                adapter.notifyDataSetChanged();
                for(int i=0; i< posts.size(); i++ ){
                    Post post = posts.get(i);
                    Log.d(TAG, "Post: " +post.getKeyDescription() + "Username: " +post.getKeyUser().getUsername());
                }

            }
        });
    }
}
