package com.example.paola.opendoor;


public  class User {
    public String name;
    public String fecha;


    User( ){
    }

    User( String pcode,String pname){

        name=pname;
        fecha=pcode;

    }
    String getName(){
        return name;
    }
    String getFecha(){
        return fecha;
    }
}
