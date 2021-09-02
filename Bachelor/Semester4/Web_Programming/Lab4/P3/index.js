let previousSelected = undefined;


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
    let randoms = generateRandomValues(nrRows * nrColumns / 2);
    const table = document.getElementById("tableGame");
    for(let i=0; i<nrRows; i++){
        let tr = document.createElement("tr");
        for(let j=0; j<nrColumns; j++) {
            let td = document.createElement("td");
            let div = document.createElement("div");

            div.setAttribute("class", "divGame");
            div.innerHTML = chooseRandomValue(randoms);
            td.onclick = function () { selectedElement(div); };

            td.appendChild(div);
            tr.appendChild(td);
        }
        table.appendChild(tr);
    }
    table.className = "tablePlay";
}

/**
 *
 * @param {Number}n
 */
function generateRandomValues(n){
    let v = [];
    for(let i=0;i<n;i++){
        let random = Math.floor(Math.random() * n * n);
        if(v.find(element => element===random)!==undefined){
            i--;
            continue;
        }
        v.push(random);
        v.push(random);
    }
    return v;
}

/**
 *
 * @param {Array}v
 */
function chooseRandomValue(v){
    let index = Math.floor(Math.random() * v.length);
    let value = v[index];
    v.splice(index, 1);
    return value;
}

/**
 *
 * @param {HTMLDivElement}divElement
 */
async function selectedElement(divElement) {
    console.log("click click");
    if (divElement.style.backgroundColor==="green"){
        return;
    }
    if (previousSelected === undefined) {
        previousSelected = divElement;
        previousSelected.style.visibility = "visible";
    } else if(divElement !== previousSelected){
        let divs = [previousSelected, divElement];
        divElement.style.visibility = "visible";
        if (divElement.innerHTML === previousSelected.innerHTML) {  // guessed the number
            divs.forEach(div => div.style.backgroundColor = "green");
        } else {
            let beforeColour = previousSelected.style.backgroundColor;
            divs.forEach(div => div.style.backgroundColor = "red");
            await new Promise(resolve => setTimeout(resolve, 1000));
            divs.forEach(div => {
                div.style.backgroundColor = beforeColour;
                div.style.visibility = "hidden";
            })
        }
        previousSelected = undefined;
    }
}