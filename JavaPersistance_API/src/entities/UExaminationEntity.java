package entities;

import fachlogik.Personal;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name="examinationSeq", initialValue=1, allocationSize=100)
@Table(name = "u_examination", schema = "main")
@NamedQueries({
        @NamedQuery(name="UExaminationEntity.fetchAll", query="SELECT e FROM UExaminationEntity e"),

        @NamedQuery(name="UExaminationEntity.fetchAllActual", query="SELECT e FROM UExaminationEntity e where e.eStart LIKE :actualYear")
})
public class UExaminationEntity {

    @Id
    @Column(name = "e_id", nullable = true)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="examinationSeq")
    private Integer eId;
    @Column(name = "e_start", nullable = true, length = -1)
    private String eStart;
    @Column(name = "e_end", nullable = true, length = -1)
    private String eEnd;
    @Column(name = "e_contrastMedium", nullable = true, length = -1)
    private String eContrastMedium;
    @Column(name = "e_cMquantity", nullable = true, precision = 0)
    private Double eCMquantity;
//    @Column(name = "e_p_patient", nullable = true)
//    private Integer ePPatient;
    @Column(name = "e_type", nullable = true, length = -1)
    private String eType;
    @ManyToMany
    @JoinTable(name="u_examination_staff",
            joinColumns= {@JoinColumn(name="es_s_id")},
            inverseJoinColumns= {@JoinColumn(name="es_e_id")})
    private List<UStaffEntity> examinationStaff;


    public Integer geteId() {
        return eId;
    }

    public void seteId(Integer eId) {
        this.eId = eId;
    }

    public List<UStaffEntity> getExaminationStaff() {
        return examinationStaff;
    }

    public void setExaminationStaff(List<UStaffEntity> examinationStaff) {
        this.examinationStaff = examinationStaff;
    }

//    @ManyToOne(fetch = FetchType.LAZY)
//    private UPatientsEntity patient;


    @ManyToOne
    @JoinColumn(name="e_p_patient")
    private UPatientsEntity patient;

    public UPatientsEntity getPatient() {
        return patient;
    }

    public void setPatient(UPatientsEntity patient) {
        this.patient = patient;
    }

    @Basic
    public String geteStart() {
        return eStart;
    }

    public void seteStart(String eStart) {
        this.eStart = eStart;
    }

    @Basic
    public String geteEnd() {
        return eEnd;
    }

    public void seteEnd(String eEnd) {
        this.eEnd = eEnd;
    }

    @Basic
    public String geteContrastMedium() {
        return eContrastMedium;
    }

    public void seteContrastMedium(String eContrastMedium) {
        this.eContrastMedium = eContrastMedium;
    }

    @Basic
    public Double geteCMquantity() {
        return eCMquantity;
    }

    public void seteCMquantity(Double eCMquantity) {
        this.eCMquantity = eCMquantity;
    }

//    @Basic
//    public Integer getePPatient() {
//        return ePPatient;
//    }
//
//    public void setePPatient(Integer ePPatient) {
//        this.ePPatient = ePPatient;
//    }

    @Basic
    public String geteType() {
        return eType;
    }

    public void seteType(String eType) {
        this.eType = eType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UExaminationEntity that = (UExaminationEntity) o;
        return Objects.equals(eId, that.eId) &&
                Objects.equals(eStart, that.eStart) &&
                Objects.equals(eEnd, that.eEnd) &&
                Objects.equals(eContrastMedium, that.eContrastMedium) &&
                Objects.equals(eCMquantity, that.eCMquantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eId, eStart, eEnd, eContrastMedium, eCMquantity);
    }

    @Override
    public String toString() {
        return "UExaminationEntity{" +
                "eId=" + eId +
                ", eStart='" + eStart + '\'' +
                ", eEnd='" + eEnd + '\'' +
                ", eContrastMedium='" + eContrastMedium + '\'' +
                ", eCMquantity=" + eCMquantity +
                '}';
    }

}
