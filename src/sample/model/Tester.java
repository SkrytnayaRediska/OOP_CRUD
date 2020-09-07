package sample.model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;
import java.time.LocalDate;

@JsonTypeName("tester")

public class Tester extends Engineer implements Serializable{
    public enum TestType{
        Automated,
        Manual;
    }
    private TestType testType;

    public Tester(String name, String startDate, String email, boolean remote_work, int office, Quality quality, TestType type) {
        super(name, startDate, email, remote_work, office, quality);
        this.testType = type;
    }

    public Tester(){

    }

    public TestType getType() {
        return testType;
    }

    public void setType(TestType type) {
        this.testType = type;
    }

    @Override
    public String toString() {
        return "Tester{" +
                "testType=" + testType +
                ", office=" + office + '\'' +
                ", quality=" + quality +
                ", name='" + name +'\'' +
                ", startDate=" + startDate +
                ", email='" + additionalInfo.email+ '\'' +
                ", remote_work=" + additionalInfo.remote_work +
                '}';
    }

    @Override
    public String writeData(){
        String result = "Tester;" + this.getType().toString() + ';'
                + this.getOffice() + ';'
                + this.getQuality().toString() + ';'
                + this.getName() + ';'
                + this.getStartDate().toString() + ';'
                + this.getAdditionalInfo().getEmail() + ';'
                + this.getAdditionalInfo().isremote_work();
        return result;
    }
}