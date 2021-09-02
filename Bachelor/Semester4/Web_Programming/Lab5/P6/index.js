let n = 4;

$(function (){
    generateTable();
    $("body").keydown(event => keyPressed(event));
})

function generateTable(){
    let table = $("#tablePlay");
    let numbers = generateNumbers();
    for(let i = 0; i < n ; i++){
        let row = $("<tr></tr>");
        table.append(row);
        for(let j = 0; j < n; j++){
            row.append("<td></td>")
        }
    }
    table.find("td").each(function (index, td){
        $(td).html(numbers[index]);
        if (numbers[index] === "_")
            $(td).addClass("empty");
    })
}

function generateNumbers(){
    let numbers = [];
    let nSquare = Math.pow(n, 2);
    let addedEmpty = false;
    for(let i = 0; i < nSquare; i++){
        let nr = Math.floor(Math.random() * nSquare) + 1;
        while (numbers.find(value => value === nr) !== undefined)
            nr = Math.floor(Math.random() * nSquare) + 1;
        if (nr === nSquare && !addedEmpty){
            addedEmpty = true;
            numbers.push("_");
        }
        else
            numbers.push(nr);
    }
    return numbers;
}

function keyPressed(event){
    let neighbour = undefined;
    let empty = $(".empty");
    let index = empty.index();
    let indexRow = empty.parent().index();

    if (event.which === 37 && index - 1 >= 0) { // ArrowLeft
        neighbour = empty.siblings().eq(index - 1);
    }
    else if (event.which === 38 && indexRow - 1 >= 0) { // ArrowUp
        neighbour = empty.parents("table").find("tr").eq(indexRow - 1).children().eq(index);
    }
    else if (event.which === 39) { // ArrowRight
        neighbour = empty.siblings().eq(index);
    }
    else if (event.which === 40) { // ArrowDown
        neighbour = empty.parents("table").find("tr").eq(indexRow + 1).children().eq(index);
    }
    else
        return;
    if (neighbour !== undefined && neighbour.length !== 0){
        let text = neighbour.text();
        neighbour.text(empty.text());
        empty.text(text);
        empty.removeClass("empty");
        neighbour.addClass("empty");
    }
}