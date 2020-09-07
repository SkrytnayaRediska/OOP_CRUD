package sample.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

@JsonTypeName("manager")
public class Manager extends Employee implements Serializable {
    public enum ManageType {
        AccountManager,
        SalesManager,
        FinanceManager,
        AdvertasingManager,
        ProjectManager;
    }
    private ManageType manageType;
    protected int office;

    public Manager(String name, String startDate, String email, boolean remote_work, ManageType manageType, int office) {
        super(name, startDate, email, remote_work);
        this.manageType = manageType;
        this.office = office;
    }

    public  Manager(){

    }

    public ManageType getManageType() {
        return manageType;
    }
    public void setManageType(ManageType manageType) {
        this.manageType = manageType;
    }
    public void setOffice(int rage) {
        this.office = office;
    }

    public int getOffice() {
        return office;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "manageType="+ manageType +
                ", office=" + office +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", email='" + additionalInfo.email+ '\'' +
                ", remote_work=" + additionalInfo.remote_work +
                '}';
    }

    @Override
    public String writeData(){
        String result = "Manager;" + this.getManageType().toString() + ';'
                + this.getOffice() + ';'
                + this.getName() + ';'
                + this.getStartDate().toString() + ';'
                + this.getAdditionalInfo().getEmail() + ';'
                + this.getAdditionalInfo().isremote_work();
        return result;
    }
}
