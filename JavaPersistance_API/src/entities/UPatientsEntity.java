package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name="patientSeq", initialValue=1, allocationSize=100)
@Table(name = "u_patients", schema = "main")
@NamedQuery(name="UPatientsEntity.fetchAll", query="SELECT p FROM UPatientsEntity p")
public class UPatientsEntity {

    @Id
    @Column(name = "p_id", nullable = true)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="patientSeq")
    private Integer pId;
    @Column(name = "p_svnr", nullable = true)
    private Short pSvnr;
    @Column(name = "p_address", nullable = true, length = -1)
    private String pAddress;
    @Column(name = "p_birthdate", nullable = true, length = -1)
    private String pBirthdate;
    @Column(name = "p_gender", nullable = true, length = -1)
    private String pGender;
    @Column(name = "p_firstname", nullable = true, length = -1)
    private String pFirstname;
    @Column(name = "p_lastname", nullable = true, length = -1)
    private String pLastname;
    @Column(name = "p_insurance", nullable = true, length = -1)
    private String pInsurance;
    @OneToMany(mappedBy = "patient")
    private List<UExaminationEntity> examinations = new ArrayList<>();

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }


    public List<UExaminationEntity> getExaminations() {
        return examinations;
    }

    public void setExaminations(List<UExaminationEntity> examinations) {
        this.examinations = examinations;
    }

    @Basic
    public Short getpSvnr() {
        return pSvnr;
    }

    public void setpSvnr(Short pSvnr) {
        this.pSvnr = pSvnr;
    }

    @Basic
    public String getpAddress() {
        return pAddress;
    }

    public void setpAddress(String pAddress) {
        this.pAddress = pAddress;
    }

    @Basic
    public String getpBirthdate() {
        return pBirthdate;
    }

    public void setpBirthdate(String pBirthdate) {
        this.pBirthdate = pBirthdate;
    }

    @Basic
    public String getpGender() {
        return pGender;
    }

    public void setpGender(String pGender) {
        this.pGender = pGender;
    }

    @Basic
    public String getpFirstname() {
        return pFirstname;
    }

    public void setpFirstname(String pFirstname) {
        this.pFirstname = pFirstname;
    }

    @Basic
    public String getpLastname() {
        return pLastname;
    }

    public void setpLastname(String pLastname) {
        this.pLastname = pLastname;
    }

    @Basic
    public String getpInsurance() {
        return pInsurance;
    }

    public void setpInsurance(String pInsurance) {
        this.pInsurance = pInsurance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UPatientsEntity that = (UPatientsEntity) o;
        return Objects.equals(pId, that.pId) &&
                Objects.equals(pSvnr, that.pSvnr) &&
                Objects.equals(pAddress, that.pAddress) &&
                Objects.equals(pBirthdate, that.pBirthdate) &&
                Objects.equals(pGender, that.pGender) &&
                Objects.equals(pFirstname, that.pFirstname) &&
                Objects.equals(pLastname, that.pLastname) &&
                Objects.equals(pInsurance, that.pInsurance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pId, pSvnr, pAddress, pBirthdate, pGender, pFirstname, pLastname, pInsurance);
    }

    @Override
    public String toString() {
        return "UPatientsEntity{" +
                "pId=" + pId +
                ", pSvnr=" + pSvnr +
                ", pAddress='" + pAddress + '\'' +
                ", pBirthdate='" + pBirthdate + '\'' +
                ", pGender='" + pGender + '\'' +
                ", pFirstname='" + pFirstname + '\'' +
                ", pLastname='" + pLastname + '\'' +
                ", pInsurance='" + pInsurance + '\'' +
                ", examinations=" + examinations +
                '}';
    }
}
