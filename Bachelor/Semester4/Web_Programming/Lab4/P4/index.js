let matrix = {}


function initialise(){
    Array.from(document.getElementsByTagName("th"))
        .forEach(th => th.addEventListener("click", () => sort(th)));
    for(let table of document.getElementsByTagName("table")){
        matrix[table.id] = buildMatrix(table);
    }
}

/**
 *
 * @param {HTMLTableHeaderCellElement}th
 */
function sort(th){
    let table = th.closest("table");
    let index = 0;  // position of th inside the table
    for (let header of table.getElementsByTagName("th")){
        if (header === th)
            break;
        index++;
    }
    matrix[table.id].sort((l1, l2) => {
        if (l1[index].innerHTML === l2[index].innerHTML)
            return 0;
        let nr1 = Number(l1[index].innerHTML), nr2 = Number(l2[index].innerHTML);
        if (!isNaN(nr1) && !isNaN(nr1)){
           return nr1 < nr2 ? -1 : 1;
        }
        if (l1[index].innerHTML < l2[index].innerHTML)
            return -1;
        return 1;
    });
    console.log(matrix[table.id]);
    matrix[table.id].forEach(arr => arr.forEach(td => console.log(td.innerHTML)));
    rebuildTable(table);
}

/**
 *
 * @param {HTMLTableElement}table
 */
function buildMatrix(table){
    let result = [];
    let nrColumns = table.getElementsByTagName("tr")[0].getElementsByTagName("td").length;
    for(let i = 0; i < nrColumns; i++)
        result.push([]);
    for(let row of table.getElementsByTagName("tr")){
        let tds = row.getElementsByTagName("td");
        let length = tds.length;
        for(let i = 0; i < length; i++){
            result[i].push(tds[i]);
        }
    }
    return result;
}

/**
 *
 * @param {HTMLTableElement}table
 */
function rebuildTable(table){
    let trs = table.getElementsByTagName("tr");
    let nrColumns = matrix[table.id].length;
    let currentFeature = 0;
    for (let tr of trs){
        for (let i = 0; i < nrColumns ; i++)
            tr.appendChild(matrix[table.id][i][currentFeature]);
        currentFeature++;
    }
}