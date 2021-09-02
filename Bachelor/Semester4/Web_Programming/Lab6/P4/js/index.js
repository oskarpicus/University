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
    let request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let table = $("#tableGame");
            table.find("tr").eq(line).find("td").eq(column).text(computerSign === "X" ? "0" : "X");
            if (!/^[0-9]/.test(this.responseText)) {
                alert("Game ended  " + this.responseText + "!");
                return;
            }
            let digits = this.responseText.split("");
            let lineComputer = Number(digits[0]), columnComputer = Number(digits[1]);
            table.find("tr").eq(lineComputer).find("td").eq(columnComputer).text(computerSign);
            if (this.responseText.split(/^[0-9]{2}/)[1] !== "")
                alert("Game ended " + this.responseText.split(/^[0-9]{2}/)[1] + "!!");
        }
    };
    let url = "../play.php";
    if (line !== undefined && column !== undefined)
        url += "?line=" + line + "&column=" + column;
    request.open("GET", url, true);
    request.send("");
}