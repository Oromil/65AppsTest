package com.oromil.a65appstest.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Oromil on 19.12.2017.
 */

public class Speciality implements Serializable{

    public Speciality(int id, String name){
        this.id = id;
        this.name = name;
    }

    @SerializedName("specialty_id")
    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this==object)
            return true;
        if (object==null)
            return false;
        if (getClass()!=object.getClass())
            return false;
        Speciality speciality = (Speciality) object;
        if (speciality.getId()==id&&speciality.getName().equals(name))
            return true;
        return false;
    }
}
