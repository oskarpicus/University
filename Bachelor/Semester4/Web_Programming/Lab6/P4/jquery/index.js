let computerSign = undefined;

$(function () {
    if (Math.random() < 0.5) {  // computer moves first
        computerSign = "X";
        ajaxCall(undefined, undefined);
    }
    else
        computerSign = "0";
    $("#tableGame td").click(function (){
        let td = $(this);
        if (td.text() === ""){
            ajaxCall(td.parent().index(), td.index());  // line, column
        }
    });
});

/**
 *
 * @param {Number}line
 * @param {Number}column
 */
function ajaxCall(line, column) {
    let url = "../play.php";
    if (line !== undefined && column !== undefined)
        url += "?line=" + line + "&column=" + column;
    $.get(url, function (data, status) {
        if (status === "success") {
            let table = $("#tableGame");
            table.find("tr").eq(line).find("td").eq(column).text(computerSign === "X" ? "0" : "X");
            if (!/^[0-9]/.test(data)) {
                alert("Game ended " + data + "!");
                return;
            }
            let digits = data.split("");
            let lineComputer = Number(digits[0]), columnComputer = Number(digits[1]);
            table.find("tr").eq(lineComputer).find("td").eq(columnComputer).text(computerSign);
            if (data.split(/^[0-9]{2}/)[1] !== "")
                alert("Game ended " + data.split(/^[0-9]{2}/)[1] + "!!");
        }
    });
}