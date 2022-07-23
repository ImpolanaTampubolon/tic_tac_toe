package com.example.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Global extends Application {
    Context context;
    Activity activity;

    public  Global(){
    }

    public Global(Context getcontext,Activity activity){
        this.context = getcontext;
        this.activity = activity;
    }

    public int numColumns = 3;

    public boolean player_1_turn = false;
    public String player_1_sign = "O";
    public String player_2_sign = "X";
    public boolean player_2_turn = false;
    public boolean is_draw = false;
    public boolean is_finish = false;
    public boolean is_start = false;
    public boolean win_diagonal_right = false;
    public boolean win_diagonal_left = false;
    public boolean win_vertical = false;
    public boolean win_horizontal = false;
    public boolean versus_ai = false;
    

    public String [] board_position = new String[numColumns*numColumns];


    public void drawBoard(){

        setEmptyPosition();

        GridView gridView = (GridView)this.activity.findViewById(R.id.gridView);
        String [] arr = new String[numColumns*numColumns];

        for(int i = 0 ; i<arr.length;i++){
            arr[i] = "";
        }

        CustomAdapter customAdapter = new CustomAdapter(context,arr);
        gridView.setAdapter(customAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt = (TextView) view.findViewById(R.id.text);

                if(versus_ai == false){
                    if(board_position[position].equals("") && is_finish == false){
                        txt.setText(setPosition(position));
                    }
                }else{
                    if(player_1_turn == true){
                        if(board_position[position].equals("") && is_finish == false){
                            txt.setText(setPosition(position));
                        }
                    }
                }


                System.out.println(is_finish);
            }
        });


    }

   public void setEmptyPosition(){
        for(int i=0;i<numColumns*numColumns;i++){
            board_position[i]="";
        }
        //ketika menggunakan theread gunakan apply untuk synchrounus
   }

    public void setVersusAi(){
        versus_ai = true;
    }

    public void setVersusPlayer(){
        versus_ai = false;
    }



   public void startGame(){
        is_start = true;
        is_finish = false;
        this.player_1_turn = true;
        this.player_2_turn = false;
        drawBoard();
   }

   public boolean checkDiagonal(int position){
       String sign = board_position[position];

       //diagonal right

        if(position%4==0){
            boolean is_win = true;
            for(int i=0;i<board_position.length;i+=4){
                if(board_position[i] == sign){
                    is_win = true;
                }
                else{
                    is_win = false;
                    break;
                }
            }

            win_diagonal_right = is_win;
            return is_win;
        }

        if(position%2 == 0 && position < board_position.length-2 && position > 0 ){
            boolean is_win = true;
            for(int i=2;i<board_position.length-2;i+=2){
                if(board_position[i] == sign){
                    is_win = true;

                }
                else{
                    is_win = false;
                    break;
                }
            }

            win_diagonal_left=is_win;
            return is_win;
        }

        return false;

   }


   public boolean checkVertical(int position){
        String sign = board_position[position];

        boolean is_win = false;

        for(int a = 0 ; a<numColumns ; a++){
            if(sign == board_position[a] && sign==board_position[a+numColumns] && sign == board_position[a+2*numColumns]){
                is_win = true;

            }
        }

        win_vertical = is_win;
       return is_win;
   }

    public boolean checkHorizontal(int position){
        String sign = board_position[position];
        boolean is_win = false;

        int no = 0;

        for(int i = 0 ;i<board_position.length;i++){
            no = no + 1;

            if(no%numColumns==0 && sign == board_position[i]){
                no=0;
                if(board_position[i]==board_position[i-1] && board_position[i]==board_position[i-2]){
                    return true;
                }
            }

        }

        win_horizontal = is_win;
        return is_win;
    }

   public void winGame(int position){
        String used_char = (this.player_1_turn ? this.player_1_sign : this.player_2_sign);
        String winning_note = (this.player_1_turn ? "Player Two Win" : "Player One Win");

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

       if(checkDiagonal(position) || checkVertical(position) || checkHorizontal(position)){
            is_finish = true;
           builder.setMessage(winning_note);
           builder.setTitle("IMpolana");
           AlertDialog alertDialog = builder.create();
           alertDialog.setTitle("oke");
           alertDialog.show();
       }

       if(checkDraw()){
           builder.setMessage("Draw");
           builder.setTitle("IMpolana");
           AlertDialog alertDialog = builder.create();
           alertDialog.setTitle("Draw");
            is_finish = true;
           alertDialog.show();
       }

   }

   public boolean checkDraw(){
        boolean is_draw = true;

        for(int i=0;i<board_position.length;i++){
            if(board_position[i] == ""){
                is_draw=false;
                break;
            }
        }

        return is_draw;
   }


   public String setPosition(int position){
        String return_value;

        if(player_2_turn){

            if(versus_ai == false){
                board_position[position] = player_2_sign;
                player_1_turn = true;
                player_2_turn = false;
                if(is_finish == false){
                    Toast.makeText(context, "Player's 1 Turn", Toast.LENGTH_SHORT).show();
                }
            }

            return_value = player_2_sign;
        }
        else{
            board_position[position] = player_1_sign;
            player_1_turn = false;
            player_2_turn = true;
            return_value = player_1_sign;

            if(is_finish == false){
                Toast.makeText(context, "Player's 2 Turn", Toast.LENGTH_SHORT).show();
            }

        }
        winGame(position);
        return return_value;
   }

   public void showBoardPosition(){
        String board = "";
        for(int i=0;i<board_position.length;i++){

            if(i>0){
                board+= ","+board_position[i];
            }
            else{
                board += ""+board_position[i];
            }

        }
        System.out.println(board);
    }

}
