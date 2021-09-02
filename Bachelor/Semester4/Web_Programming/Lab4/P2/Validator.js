class Validator{

    /**
     *
     * @param {Object}formElements
     */
    static validate(formElements){
        let errors = "";
        errors += this.validateName(formElements["name"]);
        errors += this.validateDate(formElements["birthday"]);
        errors += this.validateAge(formElements["age"]);
        errors += this.validateEmail(formElements["email"]);
        if (errors !== "")
            throw new FormValidError(errors);
    }

    /**
     *
     * @param {HTMLInputElement}input
     * @returns {string}
     */
     static validateName(input){
        let value = input.value;
        if (value === "") {
            this.fillBorder(input);
            return "You must fill the name input\n";
        }
        this.clearBorder(input);
        return "";
    }

    /**
     *
     * @param {HTMLInputElement}input
     * @returns {string}
     */
    static validateDate(input){
        let errors = "", value = input.value;
        let date = new Date(value);
        if (value === ""){
            errors += "You must select a date\n";
        }
        if (date === "Invalid date"){
            errors += "Invalid date\n";
        }
        if (date.getFullYear() < 1900){
            errors += "Birthday should be in this century\n";
        }
        if (errors !== "")
            this.fillBorder(input);
        else
            this.clearBorder(input);
        return errors;
    }

    /**
     *
     * @param {HTMLInputElement}input
     */
    static fillBorder(input){
        input.style.border = "3px solid red";
    }

    /**
     *
     * @param {HTMLInputElement}input
     */
    static clearBorder(input){
        input.style.border = "1px solid black";
    }

    /**
     *
     * @param {HTMLInputElement}formElement
     * @returns {string}
     */
    static validateAge(formElement) {
        let value = formElement.value;
        let errors = "";
        if (value.search(/^[0-9]+$/))
            errors += "Invalid input for age\n";
        if (Number(value) < 18 && value !== "")
            errors += "You are underage!\n";
        if (errors !== "")
            this.fillBorder(formElement);
        else
            this.clearBorder(formElement);
        return errors;
    }

    /**
     *
     * @param {HTMLInputElement}formElement
     * @returns {string}
     */
    static validateEmail(formElement) {
        let regex = /^[a-zA-Z]+.*@.+\.[a-z]+$/;
        if (regex.test(formElement.value)) {
            this.clearBorder(formElement);
            return "";
        }
        else{
            this.fillBorder(formElement);
            return "Invalid email\n";
        }
    }
}