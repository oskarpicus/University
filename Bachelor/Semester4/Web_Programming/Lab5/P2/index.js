$(function (){
    $("form").submit(function (event){
        let inputs = $(this).find("input");
        inputs.removeClass("invalid");

        let errors = "";
        inputs.not(":submit").each(function (i, obj){
            let wrapper = $(obj);
            let bad = false;
            if (wrapper.val() === "") {
                errors += "Invalid " + wrapper.attr("id") + "\n";
                bad = true;
            }
            if (!bad) {
                if (wrapper.attr("type") === "number" && !/^[0-9]+$/.test(wrapper.val())) {
                    errors += "Invalid number for " + wrapper.attr("id") + "\n";
                    bad = true;
                }
                if (wrapper.attr("id") === "email" && !/^[a-zA-Z]+.*@.+\.[a-z]+$/.test(wrapper.val())) {
                    errors += "Invalid " + wrapper.attr("id") + "\n";
                    bad = true;
                }
            }
            if (bad)
                wrapper.addClass("invalid");
        })

        if (errors !== "") {
            event.preventDefault();
            alert(errors);
        }
        else {
            inputs.removeClass("invalid");
            alert("Form successfully sent");
        }
    })
})