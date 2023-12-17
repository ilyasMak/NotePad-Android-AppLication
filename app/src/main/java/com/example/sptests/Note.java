package com.example.sptests;

import java.util.Date;

public class Note {
     String titre_note  ;
     String note ;
    //Date date_note ;
    int id_note ;
    //----------------------------------------------------------
    public Note(int id ,String titre_note , String note /*, Date date_note*/){
        this.titre_note=titre_note ;
        this.note = note ;
        this.id_note=id ;
        //this.date_note = date_note ;
    }
}
