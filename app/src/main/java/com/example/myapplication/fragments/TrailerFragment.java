package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.entities.Movie;

public class TrailerFragment extends Fragment {
    private Movie movie;
    private WebView webView;
    private String userId;
    public TrailerFragment(Movie movie, String id){
        this.userId = id;
        this.movie = movie;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trailer, container, false);
        view.findViewById(R.id.iv_back).setOnClickListener(view1 -> changeFragment(new MovieDetailFragment(movie,userId)));
        webView = view.findViewById(R.id.Wv_trailer);
        String video = movie.getTrailer();
        webView.loadData(video,"text/html","utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        return view;
    }
    private void changeFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
