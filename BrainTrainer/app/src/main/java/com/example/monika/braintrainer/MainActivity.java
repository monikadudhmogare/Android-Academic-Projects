package com.example.monika.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton, button0, button1, button2, button3, playAgain;
    RelativeLayout gameLayout;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int correctAnswerLocation;
    int wrongAnswer;
    int score;
    int numberOfQuestions = 0;
    TextView scoreTextView, sumTextView, resultTextView, timerTextView;

    public void onGoClick(View view)
    {
        goButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(RelativeLayout.VISIBLE);

        onPlayAgain(findViewById(R.id.playAgain));
    }

    public void generateNewQuestion()
    {
        Random random = new Random();

        int a = random.nextInt(21);
        int b = random.nextInt(21);

        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        // answer to be placed at any random location
        correctAnswerLocation = random.nextInt(4);
        answers.clear();

        for(int i = 0; i < 4; i++)
        {
            if(i == correctAnswerLocation)
            {
                answers.add(a + b);
            }
            else
            {
                wrongAnswer = random.nextInt(41);

                while(wrongAnswer == a + b)
                {
                    wrongAnswer = random.nextInt(41);
                }

                answers.add(wrongAnswer);
            }

        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public void onPlayAgain(final View view)
    {
        score =0;
        numberOfQuestions =0;

        timerTextView.setText("30s");
        scoreTextView.setText("0/0");
        resultTextView.setText("");
        playAgain.setVisibility(View.INVISIBLE);

        generateNewQuestion();

        new CountDownTimer(30100, 1000)
        {


            @Override
            public void onTick(long millisUntilFinished) {

                timerTextView.setText(String.valueOf(millisUntilFinished / 1000) + "s");

            }

            @Override
            public void onFinish() {

                playAgain.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                resultTextView.setText("Your Score: " + Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));

            }
        }.start();

    }


    public void onChooseAnswer(View view)
    {
        if(view.getTag().toString().equals(Integer.toString(correctAnswerLocation)))
        {
            score++;
            resultTextView.setText("Correct Answer!!");
        }
        else
        {
            resultTextView.setText("Wrong Answer!!");
        }

        numberOfQuestions++;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));

        generateNewQuestion();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = (Button) findViewById(R.id.goButton);

        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        playAgain = (Button)findViewById(R.id.playAgain);

        resultTextView = (TextView)findViewById(R.id.resultTextView);
        sumTextView = (TextView) findViewById(R.id.sumTextView);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        scoreTextView = (TextView)findViewById(R.id.scoreTextView);
        gameLayout = (RelativeLayout) findViewById(R.id.gameLayout);

    }
}
