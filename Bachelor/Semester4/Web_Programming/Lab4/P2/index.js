let formElements = undefined;

function submitForm(){
    formElements = {};
    const form = document.getElementsByTagName("form")[0];
    for(let child of form.childNodes){
        if (typeof child !== HTMLLabelElement)
            formElements[child.id] = child;
    }
    try {
        Validator.validate(formElements);
        alert("Form successfully sent");
        return true;
    }catch (e){
        alert(e.message);
        return false;
    }
}