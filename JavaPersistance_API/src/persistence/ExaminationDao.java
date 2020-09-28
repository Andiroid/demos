package persistence;

import entities.UExaminationEntity;
import entities.UPatientsEntity;
import entities.UStaffEntity;
import fachlogik.*;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static main.MainApp.exType;

public class ExaminationDao implements IExaminationDao {

    private EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("jpa");
    private EntityManager em = emfactory.createEntityManager();

    public ExaminationDao(){ }


    @Override
    public List<Untersuchung> allExaminations() {
        TypedQuery<UExaminationEntity> query = em.createNamedQuery("UExaminationEntity.fetchAll", UExaminationEntity.class);
        List<UExaminationEntity> dptList = query.getResultList();
        List<Untersuchung> out = new ArrayList<>();
        for (UExaminationEntity queryRow : dptList) {
            Untersuchung u = new Untersuchung();
            u.setBeginn(queryRow.geteStart());
            u.setEnde(queryRow.geteEnd());
            u.setBezeichnung(queryRow.geteType());
            u.setKm(queryRow.geteContrastMedium());
            u.setMengeKM(queryRow.geteCMquantity().toString());
            u.setId(queryRow.geteId());
            UPatientsEntity thisP = queryRow.getPatient();
            Patient newP = new Patient();
            newP.setId(thisP.getpId());
            newP.setAdresse(thisP.getpAddress());
            newP.setGeburtsdatum(LocalDate.parse(thisP.getpBirthdate()));
            newP.setGeschlecht(Geschlecht.valueOf(thisP.getpGender()));
            newP.setSvnr(thisP.getpSvnr());
            newP.setVorname(thisP.getpFirstname());
            newP.setNachname(thisP.getpLastname());
            u.setPatient(newP);
            List<Personal> thisExStaff = new ArrayList<>();
            for (UStaffEntity thisStaff : queryRow.getExaminationStaff()) {
                Personal newStaff = new Personal();
                newStaff.setId(thisStaff.getsId());
                newStaff.setAdresse(thisStaff.getsAddress());
                newStaff.setGeburtsdatum(LocalDate.parse(thisStaff.getsBirthdate()));
                newStaff.setGeschlecht(Geschlecht.valueOf(thisStaff.getsGender()));
                newStaff.setSvnr(thisStaff.getsSvnr());
                newStaff.setVorname(thisStaff.getsFirstname());
                newStaff.setNachname(thisStaff.getsLastname());
                thisExStaff.add(newStaff);
            }
            u.setPersonal(thisExStaff);
            out.add(u);
        }
        return out;
    }

    @Override
    public List<Untersuchung> allActualExaminations() {
        TypedQuery<UExaminationEntity> query = em.createNamedQuery("UExaminationEntity.fetchAllActual", UExaminationEntity.class);
        String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        query.setParameter("actualYear",year+"%");
        List<UExaminationEntity> dptList = query.getResultList();
        List<Untersuchung> out = new ArrayList<>();
        for (UExaminationEntity queryRow : dptList) {
            Untersuchung u = new Untersuchung();
            u.setBeginn(queryRow.geteStart());
            u.setEnde(queryRow.geteEnd());
            u.setBezeichnung(queryRow.geteType());
            u.setKm(queryRow.geteContrastMedium());
            u.setMengeKM(queryRow.geteCMquantity().toString());
            u.setId(queryRow.geteId());
            UPatientsEntity thisP = queryRow.getPatient();
            Patient newP = new Patient();
            newP.setId(thisP.getpId());
            newP.setAdresse(thisP.getpAddress());
            newP.setGeburtsdatum(LocalDate.parse(thisP.getpBirthdate()));
            newP.setGeschlecht(Geschlecht.valueOf(thisP.getpGender()));
            newP.setSvnr(thisP.getpSvnr());
            newP.setVorname(thisP.getpFirstname());
            newP.setNachname(thisP.getpLastname());
            u.setPatient(newP);
            List<Personal> thisExStaff = new ArrayList<>();
            for (UStaffEntity thisStaff : queryRow.getExaminationStaff()) {
                Personal newStaff = new Personal();
                newStaff.setId(thisStaff.getsId());
                newStaff.setAdresse(thisStaff.getsAddress());
                newStaff.setGeburtsdatum(LocalDate.parse(thisStaff.getsBirthdate()));
                newStaff.setGeschlecht(Geschlecht.valueOf(thisStaff.getsGender()));
                newStaff.setSvnr(thisStaff.getsSvnr());
                newStaff.setVorname(thisStaff.getsFirstname());
                newStaff.setNachname(thisStaff.getsLastname());
                thisExStaff.add(newStaff);
            }
            u.setPersonal(thisExStaff);
            out.add(u);
        }
        return out;
    }

    @Override
    public void newExamination(Untersuchung u) {
        em.getTransaction().begin();
        UExaminationEntity eNew = new UExaminationEntity();
        eNew.seteCMquantity(u.getMengeKM().doubleValue());
        eNew.seteContrastMedium(u.getKm());
        eNew.seteEnd(u.getEnde());
        eNew.seteStart(u.getBeginn());
        List<UStaffEntity> out = new ArrayList<>();
        for (Personal p : u.getPersonal()) {
            UStaffEntity newP = new UStaffEntity();
            newP.setsId(p.getId());
            newP.setsAddress(p.getAdresse());
            newP.setsBirthdate(p.getGeburtsdatum().toString());
            newP.setsGender(p.getGeschlecht().toString());
            newP.setsSvnr((short)p.getSvnr());
            newP.setsFirstname(p.getVorname());
            newP.setsLastname(p.getNachname());
            out.add(newP);
        }
        eNew.setExaminationStaff(out);
        Patient p = u.getPatient();
        UPatientsEntity newP = new UPatientsEntity();
        newP.setpId(p.getId());
        newP.setpAddress(p.getAdresse());
        newP.setpBirthdate(p.getGeburtsdatum().toString());
        newP.setpGender(p.getGeschlecht().toString());
        newP.setpSvnr((short)p.getSvnr());
        newP.setpFirstname(p.getVorname());
        newP.setpLastname(p.getNachname());
        eNew.setPatient(newP);
        eNew.seteType(exType);
        em.persist(eNew);
        em.getTransaction().commit();
        u.setId(eNew.geteId());
        //System.out.println(eNew.geteId());
    }

    @Override
    public void editExamination(Untersuchung u) {
        UExaminationEntity updateExamination = em.find(UExaminationEntity.class, u.getId());
        em.getTransaction().begin();
        updateExamination.seteCMquantity(u.getMengeKM().doubleValue());
        updateExamination.seteContrastMedium(u.getKm());
        updateExamination.seteEnd(u.getEnde());
        updateExamination.seteStart(u.getBeginn());
        List<UStaffEntity> out = new ArrayList<>();
        for (Personal p : u.getPersonal()) {
            UStaffEntity newP = new UStaffEntity();
            newP.setsId(p.getId());
            newP.setsAddress(p.getAdresse());
            newP.setsBirthdate(p.getGeburtsdatum().toString());
            newP.setsGender(p.getGeschlecht().toString());
            newP.setsSvnr((short)p.getSvnr());
            newP.setsFirstname(p.getVorname());
            newP.setsLastname(p.getNachname());
            out.add(newP);
        }
        updateExamination.setExaminationStaff(out);
        Patient p = u.getPatient();
        UPatientsEntity newP = new UPatientsEntity();
        newP.setpId(p.getId());
        newP.setpAddress(p.getAdresse());
        newP.setpBirthdate(p.getGeburtsdatum().toString());
        newP.setpGender(p.getGeschlecht().toString());
        newP.setpSvnr((short)p.getSvnr());
        newP.setpFirstname(p.getVorname());
        newP.setpLastname(p.getNachname());
        updateExamination.setPatient(newP);
        updateExamination.seteType(u.getBezeichnung());
        em.getTransaction().commit();
    }

    @Override
    public void deleteExamination(Untersuchung u) {
        UExaminationEntity delExamination = em.find(UExaminationEntity.class, u.getId());
        em.getTransaction().begin();
        if(delExamination != null){
            em.remove(delExamination);
        }
        em.getTransaction().commit();
    }

    @Override
    public List<Patient> allPatients() {
        TypedQuery<UPatientsEntity> query = em.createNamedQuery("UPatientsEntity.fetchAll", UPatientsEntity.class);
        List<UPatientsEntity> dptList = query.getResultList();
        List<Patient> out = new ArrayList<>();
        for (UPatientsEntity p : dptList) {
            Patient newP = new Patient();
            newP.setId(p.getpId());
            newP.setAdresse(p.getpAddress());
            newP.setGeburtsdatum(LocalDate.parse(p.getpBirthdate()));
            newP.setGeschlecht(Geschlecht.valueOf(p.getpGender()));
            newP.setSvnr(p.getpSvnr());
            newP.setNachname(p.getpLastname());
            newP.setVorname(p.getpFirstname());
            out.add(newP);
        }
        return out;
    }

    @Override
    public void newPatient(Patient p) {
        em.getTransaction().begin();
        UPatientsEntity pNew = new UPatientsEntity();
        pNew.setpAddress(p.getAdresse());
        pNew.setpBirthdate(p.getGeburtsdatum().toString());
        pNew.setpFirstname(p.getVorname());
        pNew.setpLastname(p.getNachname());
        pNew.setpGender(p.getGeschlecht().toString());
        pNew.setpInsurance("");
        pNew.setpSvnr((short)p.getSvnr());
        em.persist(pNew);
        em.getTransaction().commit();
        //em.flush();
        p.setId(pNew.getpId());
    }

    @Override
    public void editPatient(Patient p) {
        UPatientsEntity updatePatient = em.find(UPatientsEntity.class, p.getId());
        em.getTransaction().begin();
        updatePatient.setpAddress(p.getAdresse());
        updatePatient.setpBirthdate(p.getGeburtsdatum().toString());
        updatePatient.setpFirstname(p.getVorname());
        updatePatient.setpLastname(p.getNachname());
        updatePatient.setpGender(p.getGeschlecht().toString());
        updatePatient.setpInsurance("");
        updatePatient.setpSvnr((short)p.getSvnr());
        em.getTransaction().commit();
    }

    @Override
    public void deletePatient(Patient p) {
        UPatientsEntity delPatient = em.find(UPatientsEntity.class, p.getId());
        em.getTransaction().begin();
        if(delPatient != null){
            em.remove(delPatient);
        }
        em.getTransaction().commit();
    }

    @Override
    public List<Personal> allPersons() {
        TypedQuery<UStaffEntity> query = em.createNamedQuery("UStaffEntity.fetchAll", UStaffEntity.class);
        List<UStaffEntity> dptList = query.getResultList();
        List<Personal> out = new ArrayList<>();
        for (UStaffEntity p : dptList) {
            Personal newP = new Personal();
            newP.setId(p.getsId());
            newP.setAdresse(p.getsAddress());
            newP.setGeburtsdatum(LocalDate.parse(p.getsBirthdate()));
            newP.setGeschlecht(Geschlecht.valueOf(p.getsGender()));
            newP.setSvnr(p.getsSvnr());
            newP.setNachname(p.getsLastname());
            newP.setVorname(p.getsFirstname());
            out.add(newP);
        }
        return out;
    }

    @Override
    public List<Personal> allOldPersons() {
        LocalDate adultAge = LocalDate.now().minus(18, ChronoUnit.YEARS);
        TypedQuery<UStaffEntity> query = em.createNamedQuery("UStaffEntity.byBirth", UStaffEntity.class);
        //System.out.println(adultAge);
        query.setParameter("dateLimit", adultAge.toString());
        List<UStaffEntity> dptList = query.getResultList();
        List<Personal> out = new ArrayList<>();
        for (UStaffEntity p : dptList) {
            Personal newP = new Personal();
            newP.setId(p.getsId());
            newP.setAdresse(p.getsAddress());
            newP.setGeburtsdatum(LocalDate.parse(p.getsBirthdate()));
            newP.setGeschlecht(Geschlecht.valueOf(p.getsGender()));
            newP.setSvnr(p.getsSvnr());
            newP.setNachname(p.getsLastname());
            newP.setVorname(p.getsFirstname());
            out.add(newP);
        }
        return out;
    }

    @Override
    public void newPerson(Personal p) {
        em.getTransaction().begin();
        UStaffEntity pNew = new UStaffEntity();
        pNew.setsAddress(p.getAdresse());
        pNew.setsBirthdate(p.getGeburtsdatum().toString());
        pNew.setsFirstname(p.getVorname());
        pNew.setsLastname(p.getNachname());
        pNew.setsGender(p.getGeschlecht().toString());
        pNew.setsSvnr((short)p.getSvnr());
        em.persist(pNew);
        em.getTransaction().commit();
        p.setId(pNew.getsId());
        System.out.println(pNew.getsId());
    }

    @Override
    public void editPerson(Personal p) {
        UStaffEntity updateStaff = em.find(UStaffEntity.class, p.getId());
        em.getTransaction().begin();
        updateStaff.setsAddress(p.getAdresse());
        updateStaff.setsBirthdate(p.getGeburtsdatum().toString());
        updateStaff.setsFirstname(p.getVorname());
        updateStaff.setsLastname(p.getNachname());
        updateStaff.setsGender(p.getGeschlecht().toString());
        updateStaff.setsSvnr((short)p.getSvnr());
        em.getTransaction().commit();
    }

    @Override
    public void deletePerson(Personal p) {
        UStaffEntity delStaff = em.find(UStaffEntity.class, p.getId());
        em.getTransaction().begin();
        if(delStaff != null){
            em.remove(delStaff);
        }
        em.getTransaction().commit();
    }

    @Override
    public void close() throws IOException { }
}
