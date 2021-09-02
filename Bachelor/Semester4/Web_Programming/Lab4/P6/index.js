let n = 4;
let emptyCharacter = "_";
let matrix = [];
let rowIndex = -1, columnIndex = -1;

function initiateTable(){
    let table = document.getElementById("tablePlay");
    let randomValues = [];
    for(let i = 0; i < n; i++){
        matrix.push([]);
        let tr = document.createElement("tr");
        for(let j = 0; j < n; j++){
            let td = document.createElement("td");
            td.innerHTML = chooseRandomValue(randomValues);
            tr.appendChild(td);
            matrix[i].push(td);
            if (td.innerHTML === emptyCharacter){
                rowIndex = i;
                columnIndex = j;
            }
        }
        table.appendChild(tr);
    }
    document.getElementsByTagName("body")[0].addEventListener("keydown", ev => keyPressed(ev));
}

/**
 *
 * @param {Array}alreadyPicked
 */
function chooseRandomValue(alreadyPicked){
    let r = Math.floor(Math.random() * Math.pow(n, 2)) + 1;
    while (alreadyPicked.find(value => value === r) !== undefined){
        r = Math.floor(Math.random() * Math.pow(n, 2)) + 1;
    }
    alreadyPicked.push(r);
    if (r === Math.pow(n, 2))
        return emptyCharacter;
    return r;
}

/**
 *
 * @param {KeyboardEvent}ev
 */
function keyPressed(ev){
    console.log(ev);
    let empty = matrix[rowIndex][columnIndex];
    let newRowIndex = rowIndex, newColumnIndex = columnIndex;
    switch (ev.key){
        case "ArrowUp":
            newRowIndex--;
            break;
        case "ArrowLeft":
            newColumnIndex--;
            break;
        case "ArrowRight":
            newColumnIndex++;
            break;
        case "ArrowDown":
            newRowIndex++;
            break;
        default:
            return;
    }
    try {
        let otherTd = matrix[newRowIndex][newColumnIndex]
        if (otherTd !== undefined) {
            let text = otherTd.innerHTML;
            otherTd.innerHTML = empty.innerHTML;
            empty.innerHTML = text;
            rowIndex = newRowIndex;
            columnIndex = newColumnIndex;
        }
    }catch (e){
    }
}