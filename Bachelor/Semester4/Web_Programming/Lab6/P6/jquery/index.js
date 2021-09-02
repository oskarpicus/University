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
    $.get(script, function (data, status) {
       if (status === "success") {
           let items = JSON.parse(data);
           let selectElement = $("#" + nameColumn);
           for (let i = 0; i < items.length; i++) {
               selectElement.append($("<option></option>").text(items[i][nameColumn]));
           }
       }
    });
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

    $.get(url, function (data, status) {
       if (status === "success") {
           let games = JSON.parse(data);
           $("#games tbody").empty();
           for(let i = 0; i < games.length; i++) {
               let row = $("<tr></tr>");
               for (let property in games[i]) {
                   row.append($("<td></td>").text(games[i][property]));
               }
               $("#games").append(row);
           }
       }
    });
}