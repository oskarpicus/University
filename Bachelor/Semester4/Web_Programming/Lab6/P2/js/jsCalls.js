let currentPage = 0;

$(function (){
    getPersons();
    $("#previous").click(function (){
        currentPage--;
        getPersons();
    });
    $("#next").click(function (){
        currentPage++;
        getPersons();
    })
})

function getPersons() {
    let request = new XMLHttpRequest();
    request.onreadystatechange = function (){
        if (request.readyState === 4 && request.status === 200){
            let response = request.responseText.split("]");
            let persons = JSON.parse(response[0] + "]");
            let nrPages = JSON.parse(response[1] + "]")[0];

            if (currentPage === 0)
                $("#previous").attr("disabled", "disabled");
            else
                $("#previous").removeAttr("disabled");

            if (currentPage === nrPages - 1)
                $("#next").attr("disabled", "disabled");
            else
                $("#next").removeAttr("disabled");

            $("#persons tbody").html("");
            for(let i = 0; i < persons.length; i++){
                let row = $("<tr></tr>");
                for(let property in persons[i]) {
                    row.append($("<td></td>").text(persons[i][property]));
                }
                $("#persons").append(row);
            }
        }
    };
    request.open("GET", "../getPersons.php?page-number=" + currentPage);
    request.send("");
}