function getDepartures() {
    let request = new XMLHttpRequest();
    request.onreadystatechange = function (){
        if (request.readyState === 4 && request.status === 200){
            let selectElement = document.getElementById("departures");
            let departures = JSON.parse(request.responseText);
            for(let i = 0; i < departures.length; i++){
                let optionElement = document.createElement("option");
                optionElement.innerHTML = departures[i]["departure"];
                optionElement.onclick = function (){ getArrivals(this);};
                selectElement.appendChild(optionElement);
            }
            selectElement.selectedIndex = -1;
            selectElement.onchange = function (){
                getArrivals(this.selectedOptions[0]);
            }
        }
    }
    request.open("GET", "../getDepartures.php", true);
    request.send("");
}

/**
 *
 * @param {HTMLOptionElement}optionElement
 */
function getArrivals(optionElement){
    let request = new XMLHttpRequest();
    request.onreadystatechange = function (){
        if (request.readyState === 4 && request.status === 200){
            let arrivals = JSON.parse(request.responseText);
            let selectElement = document.getElementById("arrivals");
            selectElement.innerHTML = "";
            for(let i = 0; i < arrivals.length; i++){
                let optionElement = document.createElement("option");
                optionElement.innerText = arrivals[i]["arrival"];
                selectElement.appendChild(optionElement);
            }
        }
    }
    request.open("GET", "../getArrivals.php?departure-city=" + optionElement.innerText);
    request.send("");
}