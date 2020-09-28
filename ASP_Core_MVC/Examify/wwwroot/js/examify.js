refreshExamTable = (needle) => {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("examTable").innerHTML = xhttp.responseText;
        }
    };
    xhttp.open("POST", "_DisplayTableAjax", true);
    xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhttp.send("needle=" + needle);
} 

deleteExam = (examId) => {
    let r = confirm("Are you sure you want to delete this element?");
    if (r == true) {
        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                console.log(xhttp.responseText);
                let r = JSON.parse(xhttp.responseText);
                if (r.status == "success") {
                    document.getElementById(examId).remove();
                }
            }
        };
        xhttp.open("POST", "Ajax", true);
        xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhttp.send("examId=" + examId);
    }
}

completeExam = (e) => {
    let examId = e.getAttribute('data-id');
    let status = e.checked ? 1 : 0;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let r = JSON.parse(xhttp.responseText);
            if (r.status == "success") {
                let errorDisplay = document.getElementById("errorDisplay");
                errorDisplay.insertAdjacentHTML("afterbegin",
                    "<div class='alert alert-" + (e.checked ? 'success' : 'danger') + " alert-dismissible' id='alert-" + examId + "'>" +
                    "<a onclick='closeAlert(this.parentNode.id)' href='#' class='close'>&times;</a>" +
                    (e.checked ? 'Congratulations, you <strong>passed</strong>' : 'Oh no, you are a <strong>failure</strong>..') +
                    "</div >"
                );
                scrollUp();
            }
            console.log(xhttp.responseText);
        }
    };
    xhttp.open("POST", "CompleteExam", true);
    xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhttp.send("examId=" + examId + "&status=" + status);
}

closeAlert = (e) => {
    document.getElementById(e).remove();
}

formatDate = (date) => {
    let res = date.split("/");
    return res[2] + "/" + res[1] + "/" + (parseInt(res[0]) + 1);
}

clearForm = () => {
    document.getElementById("e_title").value = "";
    document.getElementById("e_type").value = "";
    document.getElementById("e_subject").value = "";
    document.getElementById('e_date').value = "";
    let e = document.getElementsByClassName("text-danger");
    for (let i = 0; i < e.length; i++) {
        e[i].innerHTML = "";
    }
}

var updateId = null;

saveExam = () => {
    let needle = document.getElementById("search").value;
    let formElement = document.getElementById("examForm");
    let formData = new FormData(formElement);
    if (updateId != null) {
        formData.append('id', updateId);
        updateId = null;
    }
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            try {
                JSON.parse(xhttp.responseText);
                document.getElementById("modal-btn-no").click();
                refreshExamTable(needle);
            } catch (e) { // response payload is c# validation form partial view return, if json parse failed
                document.getElementById("examModalFrame").innerHTML = xhttp.responseText;
            }
        }
    };
    xhttp.open("POST", "_ExamForm");
    xhttp.send(formData);
}

updateExam = (id, title, type, subject, date) => {
    updateId = id;
    document.getElementById("btn-create").click();
    document.getElementById("e_title").value = title;
    document.getElementById("e_type").value = type;
    document.getElementById("e_subject").value = subject;
    document.getElementById('e_date').valueAsDate = new Date(formatDate(date));
}