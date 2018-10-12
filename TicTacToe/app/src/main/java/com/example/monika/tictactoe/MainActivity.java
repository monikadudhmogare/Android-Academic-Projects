package com.example.monika.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //yellow = 0 and red = 1
    int activePlayer = 0;

    boolean activeGame= true;

    // Unplayed chance = 2
    int[] state = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winner = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};


    public void placeIn(View view)
    {

        // When user clicks on empty space
        ImageView count = (ImageView) view;

        System.out.println(count.getTag().toString());

        int countTapped = Integer.parseInt(count.getTag().toString());

        if (state[countTapped] == 2 && activeGame)
        {
            state[countTapped] = activePlayer;

            // Set off the screen
            count.setTranslationY(-1000f);

            if (activePlayer == 0)
            {
                count.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                count.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            count.animate().translationYBy(1000f).rotation(360).setDuration(300);

            //Array of integers
            for (int[] winPosition : winner)
            {
                if (state[winPosition[0]] == state[winPosition[1]] &&
                        state[winPosition[1]] == state[winPosition[2]] &&
                        state[winPosition[0]] != 2)
                {

                    activeGame = false;
                    // If someone wins the game
                    String gameWinner = "Red";
                    if(state[winPosition[0]] ==0)
                    {
                        gameWinner = "Yellow";
                    }

                    TextView winnerText = (TextView) findViewById(R.id.winnerText);
                    winnerText.setText(gameWinner + " is the Winner.. Yay!!");

                    LinearLayout layout = (LinearLayout) findViewById(R.id.tryAgainLayout);

                    layout.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    public void tryAgain(View view)
    {
        activeGame = true;
        LinearLayout layout = (LinearLayout) findViewById(R.id.tryAgainLayout);
        layout.setVisibility(View.INVISIBLE);
        activePlayer = 0;

        for(int i = 0; i < state.length; i++)
        {
            state[i] = 2;
        }

        GridLayout grid = (GridLayout) findViewById(R.id.grid);
        for(int i = 0; i < grid.getChildCount(); i++ )
        {
            ((ImageView) grid.getChildAt(i)).setImageResource(0);
        }

    }

        @Override
        protected void onCreate (Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }
    }
