package com.example.asssignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.andreilisun.swipedismissdialog.SwipeDismissDialog;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    JSONPlaceholder jsonPlaceholder;
    SwipeDismissDialog swipeDismissDialog;
    Button button4;
    String p, q;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button4 = (Button)findViewById(R.id.button2);
        if(!isNetworkConnected()){
            Toast.makeText(this, "Start Internet Connection", Toast.LENGTH_LONG).show();
        }
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://backend-test-zypher.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceholder = retrofit.create(JSONPlaceholder.class);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPost();
            }
        });
        createPost();
    }
    private void createPost() {
        Post post = new Post("Title" , "https://thumbs.dreamstime.com/z/correct-right-answer-incorrect-sign-wrong-icon-botton-circle-accept-agree-disagree-170565857.jpg");
        Call<Post> call = jsonPlaceholder.createPost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                p = response.body().getTitle();
                q = response.body().getImage();
                btn_dialog();
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void btn_dialog(){
        View mview = getLayoutInflater().inflate(R.layout.customdialog , null);
        swipeDismissDialog = new SwipeDismissDialog.Builder(MainActivity.this).setView(mview).build().show();
        final TextView textView = (TextView)mview.findViewById(R.id.text);
        ImageView imageView = (ImageView)mview.findViewById(R.id.image);
        Button button = (Button)mview.findViewById(R.id.button);
        textView.setText(p);
        if(q!=null){
            try {
                URL url = new URL(q);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                imageView.setImageBitmap(bmp);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://github.com/Shivam10025");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
                finish();
            }
        });
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}