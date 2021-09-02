$(function (){
    // get departures
    $.get("../getDepartures.php", function (data, status) {
        if (status === "success") {
            let departures = JSON.parse(data);
            let selectElement = $("#departures");
            for (let i = 0; i < departures.length; i++){
                selectElement.append($("<option></option>").text(departures[i]["departure"]));
            }
            selectElement[0].selectedIndex = -1;
        }
    });
    //get arrivals
    $("#departures").change(function (){
       $.get("../getArrivals.php?departure-city=" + $("#departures option:selected").text(), function (data, status){
           if (status === "success") {
               let selectElement = $("#arrivals").empty();
               let arrivals = JSON.parse(data);
               for(let i = 0; i < arrivals.length; i++){
                   selectElement.append($("<option></option>").text(arrivals[i]["arrival"]));
               }
           }
       })
    });
})