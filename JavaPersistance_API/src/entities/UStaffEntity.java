package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name="staffSeq", initialValue=1, allocationSize=100)
@Table(name = "u_staff", schema = "main")
@NamedQueries({
    @NamedQuery(name="UStaffEntity.fetchAll", query="SELECT p FROM UStaffEntity p"),

//    @NamedQuery(name="UStaffEntity.byBirth", query="SELECT e FROM UStaffEntity e "
//            + "WHERE e.sBirthdate >= :dateFrom AND e.sBirthdate <= :dateTo")

        @NamedQuery(name="UStaffEntity.byBirth", query="SELECT e FROM UStaffEntity e WHERE e.sBirthdate <= :dateLimit")
})

public class UStaffEntity {

    @Id
    @Column(name = "s_id", nullable = true)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="staffSeq")
    private Integer sId;
    @Column(name = "s_svnr", nullable = true)
    private Short sSvnr;
    @Column(name = "s_address", nullable = true, length = -1)
    private String sAddress;
    @Column(name = "s_birthdate", nullable = true, length = -1)
    private String sBirthdate;
    @Column(name = "s_gender", nullable = true, length = -1)
    private String sGender;
    @Column(name = "s_firstname", nullable = true, length = -1)
    private String sFirstname;
    @Column(name = "s_lastname", nullable = true, length = -1)
    private String sLastname;
    @ManyToMany(mappedBy="examinationStaff")
    private List<UExaminationEntity> staffsExaminations;

    //@Id
    //@Column(name = "s_id", nullable = true)
    //@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="staffSeq")
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }


    public List<UExaminationEntity> getStaffsExaminations() {
        return staffsExaminations;
    }

    public void setStaffsExaminations(List<UExaminationEntity> staffsExaminations) {
        this.staffsExaminations = staffsExaminations;
    }

    @Basic
    public Short getsSvnr() {
        return sSvnr;
    }

    public void setsSvnr(Short sSvnr) {
        this.sSvnr = sSvnr;
    }

    @Basic
    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    @Basic
    public String getsBirthdate() {
        return sBirthdate;
    }

    public void setsBirthdate(String sBirthdate) {
        this.sBirthdate = sBirthdate;
    }

    @Basic
    public String getsGender() {
        return sGender;
    }

    public void setsGender(String sGender) {
        this.sGender = sGender;
    }

    @Basic
    public String getsFirstname() {
        return sFirstname;
    }

    public void setsFirstname(String sFirstname) {
        this.sFirstname = sFirstname;
    }

    @Basic
    public String getsLastname() {
        return sLastname;
    }

    public void setsLastname(String sLastname) {
        this.sLastname = sLastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UStaffEntity that = (UStaffEntity) o;
        return Objects.equals(sId, that.sId) &&
                Objects.equals(sSvnr, that.sSvnr) &&
                Objects.equals(sAddress, that.sAddress) &&
                Objects.equals(sBirthdate, that.sBirthdate) &&
                Objects.equals(sGender, that.sGender) &&
                Objects.equals(sFirstname, that.sFirstname) &&
                Objects.equals(sLastname, that.sLastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sId, sSvnr, sAddress, sBirthdate, sGender, sFirstname, sLastname);
    }

    @Override
    public String toString() {
        return "UStaffEntity{" +
                "sId=" + sId +
                ", sSvnr=" + sSvnr +
                ", sAddress='" + sAddress + '\'' +
                ", sBirthdate='" + sBirthdate + '\'' +
                ", sGender='" + sGender + '\'' +
                ", sFirstname='" + sFirstname + '\'' +
                ", sLastname='" + sLastname + '\'' +
                ", staffsExaminations=" + staffsExaminations +
                '}';
    }
}
