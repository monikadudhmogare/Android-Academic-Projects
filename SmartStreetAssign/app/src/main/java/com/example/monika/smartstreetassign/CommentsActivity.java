package com.example.monika.smartstreetassign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by monika on 9/18/17.
 */

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Comment;

public class CommentsActivity extends AppCompatActivity implements View.OnClickListener
{

        private RatingBar rating_Bar;
        private Button submit;
        private TextView textView;
        private  DatabaseConnector dbConnector= new DatabaseConnector(this);


        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_comments);

            //Adding the rating bar
            rating_Bar = (RatingBar) findViewById(R.id.ratingStar);

            //Adding up a listener to listen Rating Bar
            rating_Bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
            {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                }
            });

            submit = (Button) findViewById(R.id.submit);
            submit.setOnClickListener(this);

        }

        @Override
        public void onClick(View v)
        {
            if(v.getId() == R.id.submit)
            {

                EditText comment = (EditText) findViewById(R.id.comment);
                String feedback = comment.getText().toString();
                String rating = String.valueOf(rating_Bar.getRating());
                String username = getIntent().getStringExtra("username");

                Comments comment1 = new Comments();
                comment1.setComment(feedback);
                comment1.setRating(rating);
                comment1.setUsername(username);

                dbConnector.insertComment(comment1);

                Intent intent = new Intent(CommentsActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

                Toast.makeText(getApplicationContext(), "Successfully saved the Comment", Toast.LENGTH_LONG).show();
            }
        }

        public void onRetrieveClick(View view)
        {
            Button retrieve = (Button) findViewById(R.id.retrieve);

            EditText comment = (EditText) findViewById(R.id.comment);
            TextView textView=(TextView)findViewById(R.id.textView);
            textView.setText(comment.getText());

        }
    }
