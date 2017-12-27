package com.oromil.a65appstest.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Oromil on 19.12.2017.
 */

public class Worker implements Serializable {

    @SerializedName("f_name")
    private String name;

    @SerializedName("l_name")
    private String surname;

    private String birthday;

    @SerializedName("avatr_url")
    private String avatarLink;

    @SerializedName("specialty")
    private List<Speciality> specialities;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    private Set<Integer> specialityIdSet;

    public List<Speciality> getSpecialities() {
        return specialities;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public String getBirthday() {
        return birthday;
    }

    public static Builder newBuilder() {
        return new Worker().new Builder();
    }


    public Set<Integer> getSpecialityIdSet() {
        return specialityIdSet;
    }

    public void addSpecialityId(int specialityId) {
        if (specialityIdSet == null)
            specialityIdSet = new LinkedHashSet<>();
        specialityIdSet.add(specialityId);
    }


    public void addSpeciality(Speciality speciality) {
        if (Worker.this.specialities == null)
            Worker.this.specialities = new ArrayList<>();
        this.specialities.add(speciality);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj.getClass() != Worker.class)
            return false;
        if (this == obj)
            return true;
        Worker worker = (Worker) obj;
        if (worker.getName().equals(name) && worker.getSurname().equals(surname)
                && worker.getBirthday().equals(birthday))
            return true;

        return false;
    }

    public class Builder {


        public Builder() {
        }

        public Builder setName(String name) {
            Worker.this.name = name;
            return this;
        }

        public Builder setSurname(String surname) {
            Worker.this.surname = surname;
            return this;
        }

        public Builder setBirthday(String birthday) {
            Worker.this.birthday = birthday;
            return this;
        }

        public Builder setAvatarLink(String avatarLink) {
            Worker.this.avatarLink = avatarLink;
            return this;
        }

        public Builder addSpecialityId(int specialityId) {
            if (Worker.this.specialityIdSet == null)
                Worker.this.specialityIdSet = new LinkedHashSet<>();
            Worker.this.specialityIdSet.add(specialityId);
            return this;
        }

        public Builder addSpecialityId(Set<Integer> specialityId) {
            if (Worker.this.specialityIdSet == null)
                Worker.this.specialityIdSet = specialityId;
            Worker.this.specialityIdSet.addAll(specialityId);
            return this;
        }

        public Builder addSpecialities(List<Speciality> specialities) {
            if (Worker.this.specialities == null)
                Worker.this.specialities = specialities;
            else
                Worker.this.specialities.addAll(specialities);
            return this;
        }

        public Worker build() {
            return Worker.this;
        }
    }
}
