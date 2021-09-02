let url = ".", lastCall = undefined;

$(getContents);

function getContents() {
    $.get("../getContents.php?url=" + url, function (data, status) {
        if (status === "success") {
            let files = JSON.parse(data);
            if (files instanceof Array) {  // was a directory
                let ul = $("#treeView").empty();
                for (let i = 0; i < files.length; i++) {
                    if (files[i] !== ".") {
                        ul.append($("<li></li>").text(files[i]));
                    }
                }

                lastCall = "directory";
                $("#treeView li").click(function () {
                    modifyURL($(this).text());
                    getContents();
                    $("#currentURL").text(url);
                })
                $("#textAreaFile").text("");
            }
            else {  // was just a file
                lastCall = "file";
                $("#textAreaFile").text(files);
            }
        }
    });
}

/**
 *
 * @param {String}resource
 */
function modifyURL(resource) {
    if (lastCall === "file")
        url = url.split("/").slice(0, -1).join("/");
    if (resource !== ".." || (resource === ".." && !/[^\.]$/.test(url)))
        url += "/" + resource;
    else
        url = url.split("/").slice(0, -1).join("/");
}