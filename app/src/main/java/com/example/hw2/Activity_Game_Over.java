    package com.example.hw2;

    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.TextView;

    import com.google.gson.Gson;

    import java.util.ArrayList;
    import java.util.HashSet;
    import java.util.Set;

    public class Activity_Game_Over extends AppCompatActivity {

    public static final String WINNER = "WINNER";
    public static final String NUM_OF_TURNS = "NUM_OF_TURNS";

    private TopTenGames topTenList;

    private TextView game_over_LBL_winner_details;
    private ImageView game_over_IV_winner;

    private Button game_over_BTN_new_game;
    private Button game_over_BTN_top_10;

    private MySP mySP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__game__over);

        setUpViews();
        mySP = new MySP(this);


        Gson gson = new Gson();
        String json = getIntent().getStringExtra("GAME_Details");
        GameDetails gameDetails = gson.fromJson(json, GameDetails.class);

        int player = gameDetails.getWinning_player();
        int num_of_turns = gameDetails.getNum_of_turns();
        if(player == Activity_Game.PLAYER_ONE){
            game_over_IV_winner.setImageResource(getResources().getIdentifier("alliance", "drawable", "com.example.hw2"));
        }
        else {
            game_over_IV_winner.setImageResource(getResources().getIdentifier("horde", "drawable", "com.example.hw2"));
        }
        game_over_LBL_winner_details.setText("The Winner Is Player #" + player + " With " + num_of_turns + " Turns");

    //        Set<String> jsonSet = mySP.getSetString(MySP.KEYS.LIST_OF_TOP_GAMES, new HashSet<String>);
    //
    //        for (String current :  jsonSet) {
    //
    //        }
        ArrayList<GameDetails> top_10 = new ArrayList<GameDetails>();
        ;

        json = mySP.getString(MySP.KEYS.LIST_OF_TOP_GAMES,"");

        if(json.equals("")) {

             top_10.add(gameDetails);
             topTenList = new TopTenGames(top_10);
             Log.d("NUUUUUUUUU BOYA", top_10.toString());
         }
         else {

             topTenList = gson.fromJson(json, TopTenGames.class);
             ArrayList<GameDetails> games = topTenList.getScores();

             if (games.size() < 10){
                 games.add(gameDetails);
             }
             else{
                         /*
            TODO - Check if need to Add to TopTenList
         */
             }
             topTenList.setScores(games);
         }
         Log.d("TopTenGames", topTenList.toString());
         mySP.putString(MySP.KEYS.LIST_OF_TOP_GAMES, gson.toJson(topTenList));

        game_over_BTN_new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Activity_Game.class));
                finish();
            }
        });

        game_over_BTN_top_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Activity_Top_10.class));
                finish();
            }
        });
    }

    private void setUpViews() {
        game_over_LBL_winner_details = findViewById(R.id.game_over_LBL_winner_details);
        game_over_IV_winner = findViewById(R.id.game_over_IV_winner);
        game_over_BTN_new_game =  findViewById(R.id.game_over_BTN_new_game);
        game_over_BTN_top_10 = findViewById(R.id.game_over_BTN_top_10);
    }
    }