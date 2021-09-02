let idSelectedItem = undefined;

$(function () {
    $("#divForm input").keyup(() => $("#saveButton").removeAttr("disabled"));
    $.get("../getPersonsIds.php", function (data, status) {
        if (status === "success") {
            let ids = JSON.parse(data);
            let selectElement = $("#personsIds");
            for(let i = 0; i < ids.length; i++) {
                for (let property in ids[i])
                    selectElement.append($("<option></option>").text(ids[i][property]));
            }
            selectElement.change(selectionChanged);
            selectElement[0].selectedIndex = -1;
        }
    })
    $("#formPerson").submit(function (event) {
        event.preventDefault();
        saveModifications();
    });
});

function selectionChanged() {
    if ($("#saveButton").attr("disabled") === false)
        if(confirm("You have unsaved modifications. Do you want to save them ?"))
            saveModifications();
    let selectedOption = $("#personsIds option:selected");
    $.get("../getPersonDetails.php?id=" + selectedOption.text(), function (data, status) {
        if (status === "success") {
            let details = JSON.parse(data);
            for(let property in details) {
                $("#" + property).val(details[property]);
            }
            $("#saveButton").attr("disabled", "disabled");
        }
    });
    idSelectedItem = selectedOption.text();
}

function saveModifications() {
    let data = {};
    $("#divForm input").each(function (index, element) {
        let wrapper = $(element);
        data[wrapper.attr("id")] = wrapper.val();
    });

    data["id"] = idSelectedItem;

    $.post("../saveModifications.php", JSON.stringify(data), function (data, status) {
        if (status === "success") {
            $("#saveButton").attr("disabled", "disabled");
            alert("Successfully saved the modifications");
        }
    });
}