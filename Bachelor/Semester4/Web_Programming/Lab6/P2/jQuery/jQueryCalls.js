let currentPage = 0;

$(function (){
    getPersons();
    $("#next").click(function () {
        currentPage++;
        getPersons();
    })
    $("#previous").click(function () {
        currentPage--;
        getPersons();
    })
})

function getPersons() {
    $.get("../getPersons.php?page-number=" + currentPage, function (data, status) {
        if (status === "success") {
            let response = data.split("]");
            let nrPages = JSON.parse(response[1] + "]")[0];
            let persons = JSON.parse(response[0] + ']');

            if (currentPage === nrPages - 1)
                $("#next").attr("disabled", "disabled");
            else
                $("#next").removeAttr("disabled");

            if (currentPage === 0)
                $("#previous").attr("disabled", "disabled");
            else
                $("#previous").removeAttr("disabled");

            $("#persons tbody").html("");
            for(let i = 0; i < persons.length; i++){
                let row = $("<tr></tr>");
                for(let property in persons[i]) {
                    row.append($("<td></td>").text(persons[i][property]));
                }
                $("#persons").append(row);
            }
        }
    });
}