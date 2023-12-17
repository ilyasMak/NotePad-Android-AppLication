package com.example.sptests;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class writeyournote extends AppCompatActivity {
       public static  EditText ETnote , ETtitre ;
       Button Bsave , Bannuler ;
        NoteDataBase ndb ;
    Intent send ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writeyournote);
        ETtitre = findViewById(R.id.ETtitreNote);
        ETnote = findViewById(R.id.ETnote);
        Bsave = findViewById(R.id.Bsave);
        send = new Intent(writeyournote.this , MainActivity.class);
        //-------------------------------
        Bundle b = getIntent().getExtras();

        if(MainActivity.AddisClicked==false){
            ETtitre.setText(b.getString("titre"));
            ETnote.setText(b.getString("note"));
        }
        //-------------------------------
        Bsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(  MainActivity.AddisClicked==true){

                    if (!ETtitre.getText().toString().equals("") && !ETnote.getText().toString().equals("")){
                   startActivity(send); }
                    else {
                        Toast.makeText(writeyournote.this, getResources().getString(R.string.messageToast), Toast.LENGTH_LONG).show(); }

                }

                    //----------------------------
                    ndb = new NoteDataBase(writeyournote.this,"Notepad",null,1);
                    ndb.InsererNote(ETtitre.getText().toString(),ETnote.getText().toString());

                     if(MainActivity.AddisClicked==false){

                         if(ETtitre.getText().toString().equals( b.getString("titre")) && ETnote.getText().toString().equals(b.getString("note") )){
                             Toast.makeText(writeyournote.this,"vous avez rien changer", Toast.LENGTH_SHORT).show();
                             ndb.SupprimerNote(b.getInt("id"));
                            startActivity(send);
                         }
                         else {


                        AlertDialog.Builder ab =new AlertDialog.Builder(writeyournote.this);
                        ab.setMessage(getResources().getString(R.string.alertMessage))
                                .setTitle(getResources().getString(R.string.alerttitle))
                                        .setIcon(R.drawable.taro)
                                                .setNegativeButton(getResources().getString(R.string.alersNon), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        ndb.SupprimerNote(b.getInt("id"));
                                                      startActivity(send);
                                                    }
                                                })
                        .setPositiveButton(getResources().getString(R.string.alersOui), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               startActivity(send);
                            }
                        }).show();

                    }}



            }
        });


    }
    public void Annuler(View view) {
        startActivity(send);
    }

    public static void afficherNote(String titre , String note){
        ETtitre.setText(titre);
        ETnote.setText(note);
    }
}