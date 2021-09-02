let previousSelected = undefined;
let sources = ["images/doctor.png", "images/doctor.png", "images/policeman.png", "images/policeman.png",
    "images/woman.png", "images/woman.png"];


function startPlay(){
    let nrRows = parseInt(document.getElementById("nrRows").value);
    let nrColumns = parseInt(document.getElementById("nrColumns").value);
    document.getElementById("divPlay").style.display = "none";
    console.log("Rows: " + nrRows + " Columns:" + nrColumns);
    initTable(nrRows, nrColumns);
}

/**
 *
 * @param {Number}nrRows
 * @param {Number}nrColumns
 */
function initTable(nrRows, nrColumns){
    const table = document.getElementById("tableGame");
    for(let i=0; i<nrRows; i++){
        let tr = document.createElement("tr");
        for(let j=0; j<nrColumns; j++) {
            let td = document.createElement("td");
            let imageElement = document.createElement("img");

            imageElement.setAttribute("class", "game");
            imageElement.src = chooseRandomValue();
            td.onclick = function () { selectedElement(td); };

            td.appendChild(imageElement);
            tr.appendChild(td);
        }
        table.appendChild(tr);
    }
    table.className = "tablePlay";
}

function chooseRandomValue(){
    let index = Math.floor(Math.random() * sources.length);
    let value = sources[index];
    sources.splice(index, 1);
    return value;
}

/**
 *
 * @param {HTMLTableDataCellElement}tdElement
 */
async function selectedElement(tdElement) {
    console.log("click click");
    if (tdElement.style.backgroundColor==="green"){
        return;
    }
    if (previousSelected === undefined) {
        previousSelected = tdElement;
        previousSelected.childNodes[0].style.visibility = "visible";
    } else if(tdElement !== previousSelected){
        let images = [previousSelected.childNodes[0], tdElement.childNodes[0]];
        let tds = [previousSelected, tdElement];
        images[1].style.visibility = "visible";
        if (tdElement.childNodes[0].src === previousSelected.childNodes[0].src) {  // guessed the number
            tds.forEach(td => td.style.backgroundColor = "green");
        } else {
            let beforeColour = previousSelected.style.backgroundColor;
            tds.forEach(td => td.style.backgroundColor = "red");
            await new Promise(resolve => setTimeout(resolve, 1000));
            tds.forEach(td => {
                td.style.backgroundColor = beforeColour;
            })
            images.forEach(image => image.style.visibility = "hidden");
        }
        previousSelected = undefined;
    }
}