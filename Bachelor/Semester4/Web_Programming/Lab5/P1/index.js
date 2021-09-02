$(function (){
    $("option").click(function (){
        let selectedOption = $(this);
        let selectParent = selectedOption.parents("select");
        let otherSelect = selectParent.siblings("select").first();
        otherSelect.append(selectedOption);
    })
});