package sample.model;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;
import java.time.LocalDate;

@JsonTypeName("programmer")
public class Programmer extends Engineer implements Serializable {
    public enum ProgType {
        Backend,
        Frontend,
        FullStack,
        Web,
        Game_Developer,
        Android,
        iOS;
    }
    private ProgType progType;
    private boolean higher_education;

    public Programmer(String name, String startDate, String email, boolean remote_work, ProgType type, boolean higher_education, int office, Quality quality) {
        super(name, startDate, email, remote_work, office, quality);
        this.progType = type;
        this.higher_education = higher_education;
    }

    public Programmer() {

    }

    public void setType(ProgType type) {
        this.progType = type;
    }

    public void setColorful(boolean colorful) {
        higher_education = colorful;
    }

    public ProgType getProgType() {
        return progType;
    }

    public boolean isHigher_education() {
        return higher_education;
    }

    @Override
    public String toString() {
        return "Programmer{" +
                "progType=" + progType +
                ", higher_education=" + higher_education +
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
        String result = "Programmer;" + this.getProgType().toString() + ';'
                + this.isHigher_education() + ';'
                + this.getOffice() + ';'
                + this.getQuality().toString() + ';'
                + this.getName() + ';'
                + this.getStartDate().toString() + ';'
                + this.getAdditionalInfo().getEmail() + ';'
                + this.getAdditionalInfo().isremote_work();
        return result;
    }
}
