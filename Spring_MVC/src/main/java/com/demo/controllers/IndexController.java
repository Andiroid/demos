package com.demo.controllers;

import com.demo.model.Patient;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class IndexController {

    private final String driver = "org.sqlite.JDBC";
    private final String url = "jdbc:sqlite:/home/andiroid/Dropbox/CURRENT/JAVA/SpringMVC_APP_1/patient.db";
    private Connection con = null;


//    @RequestMapping("/")
//    public ModelAndView showIndex(HttpServletRequest request, HttpServletResponse response){
//        String x = request.getParameter("foo");
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("index");
//        mv.addObject("result",x);
//        //return "index";
//        return mv;
//    }

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String listPatients(Model model) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Patient> listS = new ArrayList<>();
        try {
            if (con == null) {
                Class.forName(driver);
                con = DriverManager.getConnection(url);
            }
            String sql = "SELECT * FROM patient";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                Patient s = new Patient(rs.getInt("ID"), rs.getInt("SVNR"),
                rs.getString("ADRESSE"), LocalDate.parse(rs.getString("GEBURTSDATUM")),
                rs.getString("GESCHLECHT"),rs.getString("VORNAME"),rs.getString("NACHNAME"),
                        rs.getString("KRANKENKASSE"));
                listS.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("listPatients", listS);
        return "list";
    }

    @RequestMapping(value="/add")
    public String showAdd(@ModelAttribute("patientObject")Patient patient, Model model) {
        model.addAttribute("patientObject", new Patient());
        return "add";
    }

    @RequestMapping(value="/addProcess", method=RequestMethod.POST)
    public String addProcess(@ModelAttribute("patientObject")Patient patient, Model model) {
        //model.addAttribute("patientObject", new Patient());
        try {
            if (con == null) {
                Class.forName(driver);
                con = DriverManager.getConnection(url);
            }

            PreparedStatement pstmt = null;
            String sql = "INSERT INTO patient (SVNR, ADRESSE, GEBURTSDATUM, GESCHLECHT, VORNAME, NACHNAME, KRANKENKASSE) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, patient.getSsn());
            pstmt.setString(2, patient.getAddress());
            pstmt.setString(3, patient.getBirthday().toString());
            pstmt.setString(4, patient.getGender());
            pstmt.setString(5, patient.getFirstname());
            pstmt.setString(6, patient.getLastname());
            pstmt.setString(7, patient.getInsurance());
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }


    @RequestMapping(value="/edit")
    public ModelAndView showEdit(@ModelAttribute("patientObject")Patient patient, ModelAndView model,  @RequestParam("nr") int id) {
        try {
            if (con == null) {
                Class.forName(driver);
                con = DriverManager.getConnection(url);
            }
            String sql = "SELECT * FROM patient WHERE ID=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                patient = new Patient(rs.getInt("ID"), rs.getInt("SVNR"),
                        rs.getString("ADRESSE"), LocalDate.parse(rs.getString("GEBURTSDATUM")),
                        rs.getString("GESCHLECHT"),rs.getString("VORNAME"),rs.getString("NACHNAME"),
                        rs.getString("KRANKENKASSE"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addObject("patientObject",patient);
        model.addObject("genderList", Arrays.asList("WEIBLICH","MAENNLICH"));
        model.setViewName("edit");
        return model;
    }


    @RequestMapping(value="/editProcess", method=RequestMethod.POST)
    public String editProcess(@ModelAttribute("patientObject")Patient patient, Model model, @RequestParam("nr") int id) {
        //model.addAttribute("patientObject", new Patient());
        try {
            if (con == null) {
                Class.forName(driver);
                con = DriverManager.getConnection(url);
            }

            PreparedStatement pstmt = null;
            String sql = "UPDATE patient SET SVNR=?, ADRESSE=?, GEBURTSDATUM=?, GESCHLECHT=?, VORNAME=?, NACHNAME=?, KRANKENKASSE=?"
                        + "WHERE ID=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, patient.getSsn());
            pstmt.setString(2, patient.getAddress());
            pstmt.setString(3, patient.getBirthday().toString());
            pstmt.setString(4, patient.getGender());
            pstmt.setString(5, patient.getFirstname());
            pstmt.setString(6, patient.getLastname());
            pstmt.setString(7, patient.getInsurance());
            pstmt.setInt(8, id);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }


    @RequestMapping("/delete")
    public String deletePatient(@RequestParam("nr") int id) {
        try {
            if (con == null) {
                Class.forName(driver);
                con = DriverManager.getConnection(url);
            }
            String sql = "DELETE FROM patient WHERE ID=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

//    @RequestMapping("/add")
//    public String showAdd(){
//        return "add";
//    }
//
//    @RequestMapping("/edit")
//    public ModelAndView showEdit(){
//
//        String firstname = null;
//        try {
//            if (con == null) {
//                //Class.forName(driver);
//                Class.forName("org.sqlite.JDBC");
//                con = DriverManager.getConnection(url);
//            }
//            String sql = "SELECT * FROM patient WHERE ID=?";
//            PreparedStatement pstmt = con.prepareStatement(sql);
//            pstmt.setInt(1, 1);
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                firstname = rs.getString("VORNAME");
//                System.out.println(firstname);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("edit");
//        mv.addObject("result",firstname);
//
//        return mv;
//    }
}
