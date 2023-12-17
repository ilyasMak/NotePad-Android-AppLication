package com.example.sptests;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

   Button Badd;
   ListView LVnotes ;
    ArrayList<Note> notes ;
    NoteDataBase ndb ;
    int elementSelctionner =-1;
    public static boolean AddisClicked = false ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Badd= findViewById(R.id.Badd);
        AddisClicked = false ;
        LVnotes = findViewById(R.id.LVnotes);


       //----------------------------------------------------------------------------------
     ndb = new NoteDataBase(MainActivity.this,"Notepad",null,1);

      notes=new ArrayList<>();
       notes = ndb.GetNotes();

       //-----------------------------------------------------------------------------------
       Badd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(MainActivity.this,writeyournote.class);
               AddisClicked = true ;
               startActivity(i);
           }
       });
        //-----------------------------------------------------------------------------------

        LVnotes.setAdapter(new Notes(notes));



    }
    class Notes extends BaseAdapter {
        ArrayList<Note> notes = new ArrayList<Note>();

        public Notes(ArrayList<Note> notes){
            this.notes=notes ;

        }

        @Override
        public int getCount() {
            return notes.size();
        }

        @Override
        public Note getItem(int i) {
            return notes.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            LayoutInflater LI = getLayoutInflater();
            View v = LI.inflate(R.layout.note,null);
            TextView TVtitre ,TVnote ;
            TVtitre = v.findViewById(R.id.TVtitre);
            TVnote = v.findViewById(R.id.TVnote) ;
            TVtitre.setText(notes.get(i).titre_note);
            TVnote.setText(getPremiersMots(notes.get(i).note));
            //-------------------------------
            Button Bsupprimer = v.findViewById(R.id.Bsupprimer);
            Button Bedit = v.findViewById(R.id.Bedit);
            //----------------------------------------------------------
            Bsupprimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = notes.get(i).id_note;
                    notes.remove(i);
                    notifyDataSetChanged();
                   ndb.SupprimerNote(id);

                }
            });
            //------------------------------------------------------------
            Bedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent (MainActivity.this , writeyournote.class);
                    Bundle b=new Bundle();
                    b.putString("titre",notes.get(i).titre_note);
                    b.putString("note",notes.get(i).note);
                    b.putInt("id",notes.get(i).id_note);
                    it.putExtras(b);
                    startActivity(it);
                   // System.out.println(notes.get(i).titre_note);

                    writeyournote.afficherNote(notes.get(i).titre_note,notes.get(i).note);

                }
            });
            //------------------------------------------------------------
            return v;
        }

    }
    //--------------------------------------------------
    public static String getPremiersMots(String phrase) {
       String[] mots = phrase.split("\\s+");
 if (mots.length > 4) {
            return mots[0] + " " + mots[1] + " " + mots[2] + " " + mots[3]+"...";
        } else {
          return phrase;
        }
    }

}