package sample.model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;
import java.time.LocalDate;

@JsonTypeName("designer")

public class Designer extends Engineer implements Serializable {
    public enum DesignType{
        Game,
        Web,
        Graphic;
    }
    private DesignType designType;

    public Designer(String name, String startDate, String email, boolean remote_work, int office, Quality quality, DesignType type) {
        super(name, startDate, email, remote_work, office, quality);
        this.designType = type;
    }

    public Designer(){


    }

    public DesignType getType() {
        return designType;
    }

    public void setType(DesignType type) {
        this.designType = type;
    }

    @Override
    public String toString() {
        return "Designer{" +
                "designType=" + designType +
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
        String result = "Designer;" + this.getType().toString() + ';'
                + this.getOffice() + ';'
                + this.getQuality().toString() + ';'
                + this.getName() + ';'
                + this.getStartDate().toString() + ';'
                + this.getAdditionalInfo().getEmail() + ';'
                + this.getAdditionalInfo().isremote_work();
        return result;
    }
}
