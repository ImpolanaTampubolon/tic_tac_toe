package com.example.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();
        global = new Global(getApplicationContext(),this);
        global.drawBoard();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.start_game){
            global.startGame();
        }
        else if(id == R.id.versus_player){
            global.setVersusPlayer();
        }
        else if(id == R.id.versus_ai){
            global.setVersusAi();
        }

        return super.onOptionsItemSelected(item);
    }
}
