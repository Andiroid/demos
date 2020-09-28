<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix = "form"%>
<html>
<head>
    <title>Patient</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
    <script>
        $(function() {
            $("#datepicker").datepicker({dateFormat: "dd.mm.yy"});
        });
    </script>
</head>

<body>
<div style="font-family:verdana;">
    <div id="patient-form-wrap">
        <div id="patient-form-inner">
            <fieldset>
                <legend><strong>Patient Bearbeiten</strong></legend>

                <form:form  modelAttribute="patientObject" method="POST" action="editProcess">
                <table>
                    <tr>
                        <td><form:label path = "firstname">Vorname</form:label></td>
                        <td><form:input path = "firstname" /></td>
                    </tr>
                    <tr>
                        <td><form:label path = "lastname">Nachname</form:label></td>
                        <td><form:input path = "lastname" /></td>
                    </tr>
                    <tr>
                        <td><form:label path = "birthday">Geburtsdatum</form:label></td>
                        <td><form:input id="datepicker" path = "birthday" /></td>
                    </tr>
                    <tr>
                        <td><form:label path = "address">Adresse</form:label></td>
                        <td><form:input path = "address" /></td>
                    </tr>
                    <tr>
                        <td><form:label path = "ssn">SVNR</form:label></td>
                        <td><form:input path = "ssn" /></td>
                    </tr>
                    <tr>
                        <td><form:label path = "insurance">Versicherung</form:label></td>
                        <td><form:input path = "insurance" /></td>
                    </tr>
                    <tr>
                        <td><form:label path = "gender">Geschlecht</form:label></td>
                        <td>
                            <form:select path = "gender">
                                <form:options items="${genderList}"/>
<%--                                <option value="MAENNLICH">MAENNLICH</option>--%>
<%--                                <option value="WEIBLICH">WEIBLICH</option>--%>
                            </form:select>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <br><br>
            <input type="hidden" name="nr" value="${patientObject.id}"/>
            <input type="submit" value="Speichern"/>
            <button onclick="location.href=document.referrer" type="button">
                Zurueck</button>
            </form:form>
        </div>
    </div>

    <style>
        #patient-form-inner{
            transition: 0.4s ease-in-out;
            border-radius:5px;
            background-repeat: repeat;
            padding:20px;
            width:360px;
            margin:auto;
        }
        table{
            text-align: left !important;
        }
        #patient-form-wrap{
            transition: 0.4s ease-in-out;
            width:100%;
            margin-top: -50px;
            text-align: center;
            position: absolute;
            top: 50%;
            -ms-transform: translateY(-50%);
            transform: translateY(-50%);
            animation: fadein 1.5s;
            -moz-animation: fadein 1.5s; /* Firefox */
            -webkit-animation: fadein 1.5s; /* Safari and Chrome */
            -o-animation: fadein 1.5s; /* Opera */
        }

    </style>
</div>
</body>
</html>