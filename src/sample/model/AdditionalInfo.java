package sample.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;


public class AdditionalInfo implements Serializable{
    public String email;
    public boolean remote_work;

    public AdditionalInfo(String publishingCompany, boolean remote_work) {
        this.email = publishingCompany;
        this.remote_work = remote_work;
    }

    public AdditionalInfo(){

    }

    public String getEmail() {
        return email;
    }

    public boolean isremote_work() {
        return remote_work;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRemote_work(boolean remote_work) {
        this.remote_work = remote_work;
    }

    @Override
    public String toString() {
        return "AdditionalInfo{" +
                "email='" + email +'\''+
                ", remote_work=" + remote_work +
                '}';
    }
}
