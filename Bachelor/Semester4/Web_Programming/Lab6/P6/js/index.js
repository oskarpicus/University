$(function () {
    initialiseSelect("brand", "../getBrands.php");
    initialiseSelect("platform", "../getPlatforms.php");
    initialiseSelect("genre", "../getGenres.php");
    filterGames();  // will display all games
    $("#comboBoxes select").change(filterGames);
});

/**
 *
 * @param {String}nameColumn
 * @param {String}script
 */
function initialiseSelect(nameColumn, script) {
    let request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let items = JSON.parse(this.responseText);
            let selectElement = $("#" + nameColumn);
            for(let i = 0; i < items.length; i++) {
                selectElement.append($("<option></option>").text(items[i][nameColumn]));
            }
        }
    }
    request.open("GET", script, true);
    request.send("");
}

function filterGames() {
    let url = "../filterGames.php?";
    $("#comboBoxes select").each(function (index, value) {
        let element = $(value);
        if (index === 0)
            url += element.attr("id") + "=" + element.val();
        else
            url += "&" + element.attr("id") + "=" + element.val();
    });

    let request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let games = JSON.parse(this.responseText);
            $("#games tbody").empty();
            for(let i = 0; i < games.length; i++) {
                let row = $("<tr></tr>");
                for (let property in games[i]) {
                    row.append($("<td></td>").text(games[i][property]));
                }
                $("#games").append(row);
            }
        }
    }
    request.open("GET", url, true);
    request.send("");
}