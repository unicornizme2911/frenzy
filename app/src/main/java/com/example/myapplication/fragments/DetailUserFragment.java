package com.example.myapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.activity.PaymentHistoryActivity;
import com.example.myapplication.authentication.LoginActivity;
import com.example.myapplication.entities.User;
import com.example.myapplication.models.UserModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class DetailUserFragment extends Fragment {
    private User user;
    private TextView name,logout;
    private CircleImageView avatar;
    private final UserModel userModel = new UserModel();
    public DetailUserFragment(User user){
        this.user = user;
    }
    private static final String TAG = "DetailUserFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_user2, container, false);
        init(view);
        Listener(view);
        name = view.findViewById(R.id.tv_name);
        fillData();
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new UpdateProfileFragment(user));
            }
        });
        avatar = view.findViewById(R.id.profile_image);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent , 1);
            }
        });
        return view;
    }
    private void fillData(){
        userModel.getUser(user.getUuid(), new UserModel.UserCallbacks() {
            @Override
            public void onSuccess(User user) {
                if(user.getAvatar() != null){
                    Glide.with(getContext())
                            .load(user.getAvatar())
                            .into(avatar);
                }
                name.setText(user.getFullname().toString());
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }

    private void Listener(View view){
        view.findViewById(R.id.iv_close).setOnClickListener(view1 -> changeFragment(new HomeFragment(user)));
        view.findViewById(R.id.iv_home).setOnClickListener(view1 -> changeFragment(new HomeFragment(user)));
        view.findViewById(R.id.LN_history_payment).setOnClickListener(view1 -> changeFragment(new PaymentHistoryFragment(user)));
        view.findViewById(R.id.LN2_movieFollow).setOnClickListener(view1 -> changeFragment(new MovieFollowFragment(user)));
        view.findViewById(R.id.LN_choose_theater).setOnClickListener(view1 -> changeFragment(new TheaterFragment(user)));
        view.findViewById(R.id.tv_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init(View view) {}

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap =(Bitmap) data.getExtras().get("data");
        avatar.setImageBitmap(bitmap);
        Uri tempUri = getImageUri(getContext(), bitmap);
        File finalFile = new File(getRealPathFromURI(tempUri));
        Log.e(TAG, "onActivityResult: "+tempUri.toString());
        userModel.changeAvatar(user.getUuid(), tempUri.toString(), new UserModel.UserCallbacks() {
            @Override
            public void onSuccess(User user) {
                Log.e(TAG, "onSuccess: ");
            }
            @Override
            public void onFailed(Exception e) {

            }
        });
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage,
                "Title", null);
        return Uri.parse(path);
    }
    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getActivity().getContentResolver() != null) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }
    private void changeFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}