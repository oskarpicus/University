$(function (){
    $("#start").click(function (){
        $("#divPlay").css("visibility", "hidden");
        $("#tableGame").addClass("tablePlay");
        generateTable(Number($("#nrRows").val()), Number($("#nrColumns").val()));
    })
})

/**
 *
 * @param {Number}nrRows
 * @param {Number}nrColumns
 */
function generateTable(nrRows, nrColumns) {
    let sources = ["images/doctor.png", "images/doctor.png", "images/policeman.png", "images/policeman.png",
        "images/woman.png", "images/woman.png"];
    let table = $("#tableGame");
    for(let i = 0; i < nrRows; i++){
        let row = $("<tr></tr>");
        table.append(row);
        for(let j = 0; j < nrColumns; j++){
            row.append($("<td></td>").append("<img>"));
        }
    }
    table.find("img").each(function (i, obj){
        let index = Math.floor(Math.random() * sources.length);
        $(obj).attr("src", sources[index])
            .addClass("invisible")
            .parent().click(selected);
        sources.splice(index, 1);
    })
}

async function selected(){
    $(this).children("img").removeClass("invisible").addClass("selected");
    let selectedImages = $("#tableGame").find(".selected");
    if (selectedImages.length === 2){ // there was another one selected
        if (selectedImages.first().attr("src") !== selectedImages.last().attr("src")){ // match
            selectedImages.parent().addClass("red");
            await new Promise(resolve => setTimeout(resolve, 1000));
            selectedImages.removeClass("selected")
                .addClass("invisible")
                .parent()
                .removeClass("red");
        }
        else {
            selectedImages.removeClass("selected")
                .css("visibility", "visible")
                .parent()
                .addClass("green");
        }
    }
}