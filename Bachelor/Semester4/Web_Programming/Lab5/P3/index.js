$(function (){
    $("#start").click(function (){
        $("#divPlay").css("visibility", "hidden");
        generateTable(Number($("#nrRows").val()), Number($("#nrColumns").val()))
    })
})

/**
 *
 * @param {Number}rows
 * @param {Number}columns
 */
function generateTable(rows, columns){
    let numbers = generateRandomValues(rows * columns / 2)
    let table = $("#tableGame");
    table.addClass("tableGame");
    for(let i = 0; i < rows; i++){
        let row = $("<tr></tr>");
        table.append(row);
        for(let j = 0; j < columns; j++){
            row.append($("<td></td>").append("<div></div>"));
        }
    }
    table.find("div").each(function (i, obj){
        $(obj).html(chooseRandomValue(numbers))
            .addClass("invisible")
            .parent().click(selected);
    });
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

async function selected(){
    console.log(this);
    $(this).children("div").removeClass("invisible").addClass("selected");
    let selectedDivs = $("#tableGame").find(".selected");
    if (selectedDivs.length === 2){ // there is another one selected
        if (selectedDivs.first().text() !== selectedDivs.last().text()) {  // not a match
            selectedDivs.addClass("red");
            await new Promise(resolve => setTimeout(resolve, 1000));
            selectedDivs.removeClass("red selected").addClass("invisible");
        }
        else {
            selectedDivs.addClass("green").removeClass("selected").css("visibility", "visible");
        }
    }
}