const selectId1 = "selectFruit";
const selectId2 = "selectVegetable";

/**
 *
 * @param {HTMLSelectElement}selectElement
 */
function switchOptions(selectElement){
    let selectedElementId = selectElement.id;
    let otherSelectedElementId = (selectedElementId===selectId1) ? selectId2 : selectId1;
    let otherSelectElement = document.getElementById(otherSelectedElementId);

    let option = selectElement.selectedOptions[0];
    selectElement.remove(selectElement.selectedIndex);
    otherSelectElement.add(option);
}
