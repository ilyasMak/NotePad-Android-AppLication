package com.example.sptests;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class NoteDataBase extends SQLiteOpenHelper {
    public NoteDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Notes (note_id INTEGER PRIMARY KEY AUTOINCREMENT,note_titre TEXT,note TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void InsererNote(String titre , String note){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO Notes (note_titre, note) VALUES (?, ?)", new String[]{titre, note});
        db.close();
    }
    public void SupprimerNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM Notes WHERE note_id = " + id + ";");
        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<Note> GetNotes(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM Notes ORDER BY note_id DESC", null);
        ArrayList<Note> nts =new ArrayList<>();
        cur.moveToFirst();
        while(cur.isAfterLast()==false){
            int id ;
            String titre , note ;
            id=cur.getInt(cur.getColumnIndex("note_id"));
            titre = cur.getString(cur.getColumnIndex("note_titre"));
            note = cur.getString(cur.getColumnIndex("note"));
            nts.add(new Note(id,titre,note));
            cur.moveToNext();
        }
        db.close();
        return nts ;
    }
}
