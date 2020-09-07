package sample.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.time.LocalDate;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Manager.class, name = "manager"),
        @JsonSubTypes.Type(value = Engineer.class, name = "engineer"),
})

public abstract class Employee implements Serializable {
    protected String name;
    protected String startDate;
    protected AdditionalInfo additionalInfo;

    public Employee(String name, String startDate, String publishingCompany, boolean takeawayPermission) {
        this.name = name;
        this.startDate = startDate;
        this.additionalInfo = new AdditionalInfo(publishingCompany, takeawayPermission);
    }

    public Employee(){

    }

    public String getName() {
        return name;
    }

    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", email='" + additionalInfo.email+ '\'' +
                ", remote_work=" + additionalInfo.remote_work +
                '}';
    }

    public String writeData(){
        return "";
    }
}
