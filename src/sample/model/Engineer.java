package sample.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;
import java.time.LocalDate;

@JsonTypeName("book")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Programmer.class, name = "programmer"),
        @JsonSubTypes.Type(value = Tester.class, name = "tester"),
        @JsonSubTypes.Type(value = Designer.class, name = "designer"),
})

public abstract class Engineer extends Employee implements Serializable {
    public enum Quality{
        Junior,
        Middle,
        Senior;
    }
    public Quality quality;
    protected int office;

    public Engineer(String name, String startDate, String publishingCompany, boolean takeawayPermission, int office, Quality quality) {
        super(name, startDate, publishingCompany, takeawayPermission);
        this.office = office;
        this.quality=quality;
    }

    public Engineer(){

    }


    public void setOffice(int office) {
        this.office = office;
    }

    public int getOffice() {
        return office;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public Quality getQuality() {
        return quality;
    }

    @Override
    public String toString() {
        return "Engineer{" +
                "office=" + office + '\'' +
                ", quality=" + quality +
                ", name='" + name +'\'' +
                ", startDate=" + startDate +
                ", email='" + additionalInfo.email+ '\'' +
                ", remote_work=" + additionalInfo.remote_work +
                '}';
    }

    @Override
    public String writeData(){
        return "";
    }
}
