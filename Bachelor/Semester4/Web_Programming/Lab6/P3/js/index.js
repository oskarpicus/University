let idSelectedItem = undefined;

$(function () {
    getPersonsIds();
    let saveButton = $("#saveButton");
    $("#divForm input").keyup(() => saveButton.removeAttr("disabled"));
    $("#formPerson").submit(function (event) {
        event.preventDefault();
        saveModifications();
    });
});

function getPersonsIds() {
    let request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let ids = JSON.parse(this.responseText);
            let selectElement = $("#personsIds");
            for(let i = 0; i < ids.length; i++) {
                for (let property in ids[i])
                    selectElement.append($("<option></option>").text(ids[i][property]));
            }
            selectElement.change(selectionChanged);
            selectElement[0].selectedIndex = -1;
        }
    }
    request.open("GET", "../getPersonsIds.php", true);
    request.send("");
}

function selectionChanged() {
    if ($("#saveButton").attr("disabled") === false)
        if(confirm("You have unsaved modifications. Do you want to save them ?"))
            saveModifications();
    let request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let details = JSON.parse(this.responseText);
            for(let property in details) {
                $("#" + property).val(details[property]);
            }
            $("#saveButton").attr("disabled", "disabled");
        }
    }
    let selectedOption = $("#personsIds option:selected").text();
    request.open("GET", "../getPersonDetails.php?id=" + selectedOption, true);
    request.send("");
    idSelectedItem = selectedOption;
}

function saveModifications() {
    let saveButton = $("#saveButton");
    let data = {};
    $("#divForm input").each(function (index, element) {
        let wrapper = $(element);
        data[wrapper.attr("id")] = wrapper.val();
    });

    data["id"] = idSelectedItem;

    let request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            saveButton.attr("disabled", "disabled");
            alert("Successfully saved the modifications");
        }
    }
    request.open("POST", "../saveModifications.php", true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send(JSON.stringify(data));
}