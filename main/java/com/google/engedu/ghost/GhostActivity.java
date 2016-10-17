package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean user = false;
    private Random random = new Random();
    private String wordfragment = "";
    private String s1="qwertyuiopasdfghjklzxcvbnm";
    private char[] ch=s1.toCharArray();
    private char[] vowels={'a','e','i','o','u'};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new SimpleDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }

        onStart(null);
        TextView label = (TextView)findViewById(R.id.gameStatus);
        TextView wordTextView = (TextView)findViewById(R.id.ghostText);
        Button BT_Challenge = (Button)findViewById(R.id.BT_Challenge);
        Button BT_Reset = (Button)findViewById(R.id.BT_Reset);
        BT_Challenge.setOnClickListener(generalClickListner);
        BT_Reset.setOnClickListener(generalClickListner);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        wordfragment="";
        user = random.nextBoolean();
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView)findViewById(R.id.gameStatus);   //here nullPointer exception is thrown
        TextView wordTextView = (TextView)findViewById(R.id.ghostText);
        Toast.makeText(this, "Onstart method", Toast.LENGTH_SHORT).show();
        if (user) {
            label.setText(USER_TURN);
            userTurn();
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;

    }



        private void computerTurn() {
        if (!user) {
            Log.i("computerTurn method","starting method ");
            TextView label = (TextView) findViewById(R.id.gameStatus);   //here nullPointer exception is thrown
            TextView wordTextView = (TextView) findViewById(R.id.ghostText);
            if (wordfragment.trim() == "" || wordfragment == null || wordfragment.trim().length() <= 4) {
                //string length <=4 then continue playing
                Log.i("computerTurn method","null or lessthan equal to 4 if");
                label.setText(COMPUTER_TURN);
                // Do computer turn stuff then make it the user's turn again
                computerPlay();
                user = true;

                userTurn();
            } else {
                if (dictionary.isWord(wordfragment.toLowerCase())) {
                    Log.i("computerTurn method","isWord if computer win");
                    //check if player has lossed?
                    Toast.makeText(this, "You Lost ...", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "Game Resetted", Toast.LENGTH_SHORT).show();
                    onStart(null);
                } else {
                    Log.i("computerTurn method","else most part");
                    label.setText(COMPUTER_TURN);
                    // Do computer turn stuff then make it the user's turn again
                    computerPlay();
                    user = true;

                    userTurn();
                }
            }
        }
    }


    private void userTurn(){
        Log.i("userTurn method","starting method ");
        TextView label = (TextView)findViewById(R.id.gameStatus);
        TextView wordTextView = (TextView)findViewById(R.id.ghostText);
        if(wordfragment.trim()=="" || wordfragment == null || wordfragment.trim().length()<=4){
            //string length <=4 then continue playing
            Log.i("userTurn method","less than 4 if ");

            //do user turn stuff then make it the computer's turn again
            label.setText(USER_TURN);


            computerTurn();
        }else{
            if (dictionary.isWord(wordfragment.toLowerCase())){
                //check if computer has lossed?
                Log.i("userTurn method","else if isWord method ");
                Toast.makeText(this, "You have Won...Hurray!", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Game Resetted", Toast.LENGTH_SHORT).show();
                onStart(null);
            }else{
                Log.i("userTurn method","else most part, compulsory part ");
                label.setText(USER_TURN);
                //do user turn stuff then make it the computer's turn again



                computerTurn();
            }
        }


    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.i("onKeyUp method","start method");
        TextView label = (TextView)findViewById(R.id.gameStatus);   //here nullPointer exception is thrown
        TextView wordTextView = (TextView)findViewById(R.id.ghostText);
        if(keyCode >= 29 && keyCode <= 54){

            wordfragment = wordfragment.concat(event.getDisplayLabel() + "");
            wordfragment = wordfragment.toLowerCase();
            wordTextView.setText(wordfragment);

            label.setText(USER_TURN);
            user=false;
            computerTurn();
        }
        return super.onKeyUp(keyCode, event);
    }

    private void computerPlay(){

        Log.i("computerPlay method","starting");
        TextView label = (TextView)findViewById(R.id.gameStatus);   //here nullPointer exception is thrown
        TextView wordTextView = (TextView)findViewById(R.id.ghostText);
            String nextWord = dictionary.getAnyWordStartingWith(wordfragment.toLowerCase());
            if(nextWord==null) {
                if (wordfragment.trim().length() > 4) {
                    Log.i("computerPlay method","if null and length >4");
                    Toast.makeText(this, "Computer challenged you. Computer wins", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "Game Resetted", Toast.LENGTH_SHORT).show();

                    onStart(null);
                } else {
                    Log.i("computerPlay method","if nextWord is null and length <=4");
                    wordfragment = (wordfragment + ch[random.nextInt(ch.length)]);
                    wordTextView.setText(wordfragment);
                }
            }else{
                    if ((wordfragment.length()+1)>=nextWord.length()){
                        //then computer will bluff with putting a vowel
                        Log.i("computerPlay method","computer bluff with vowel");
                        wordfragment = (wordfragment + vowels[random.nextInt(vowels.length)]);
                        wordTextView.setText(wordfragment);
                    }else{
                        Log.i("computerPlay method","putting next char from word");
                        wordfragment = nextWord.substring(0,wordfragment.length()+1);   //Index out of bound excception
                        wordTextView.setText(wordfragment);
                    }


            }


    }

    private void computerPlayChallenge(){
        if (dictionary.isWord(wordfragment)){
            Toast.makeText(GhostActivity.this, "You lost the challenge, computer win", Toast.LENGTH_SHORT).show();
            Toast.makeText(GhostActivity.this, "Game Resetted", Toast.LENGTH_SHORT).show();
            onStart(null);
        }else{
            Toast.makeText(GhostActivity.this, "You win the challenge!!", Toast.LENGTH_SHORT).show();
            Toast.makeText(GhostActivity.this, "Game Resetted", Toast.LENGTH_SHORT).show();
            onStart(null);
        }
    }

    View.OnClickListener generalClickListner = new View.OnClickListener() {
        @Override
        public void onClick(final View v){
            switch (v.getId())
            {
                case R.id.BT_Challenge:
                    Log.i("onClick Listener","challenge  button");
                    if(wordfragment.trim().length()<=4){
                            TextView label = (TextView)findViewById(R.id.gameStatus);
                            label.setText(USER_TURN);
                            user=false;
                            Toast.makeText(GhostActivity.this, "You cannot challenge right now", Toast.LENGTH_SHORT).show();
                    }else{
                            TextView label = (TextView)findViewById(R.id.gameStatus);
                            label.setText(USER_TURN);
                            user=false;
                            Toast.makeText(GhostActivity.this, "You challenged computer", Toast.LENGTH_SHORT).show();
                            computerPlayChallenge();
                    }
                    break;
                case R.id.BT_Reset:
                    Log.i("OnClick Listener","reset button");
                    TextView label = (TextView)findViewById(R.id.gameStatus);
                    label.setText(USER_TURN);
                    Toast.makeText(GhostActivity.this, "Game Resetted", Toast.LENGTH_SHORT).show();
                    onStart(null);
                    break;
            }
        }

    };


}
