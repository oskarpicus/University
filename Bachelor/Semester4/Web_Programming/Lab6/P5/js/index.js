let url = ".", lastCall = undefined;

$(function () {
    getContents();
});


function getContents() {
    let request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let files = JSON.parse(this.responseText);
            if (files instanceof Array) {  // was a directory
                let ul = $("#treeView").empty();
                for (let i = 0; i < files.length; i++) {
                    if (files[i] !== ".") {
                        ul.append($("<li></li>").text(files[i]));
                    }
                }
                $("#treeView li").click(function () {
                    modifyURL($(this).text());
                    getContents();
                    $("#currentURL").text(url);
                });
                $("#textAreaFile").text("");
                lastCall = "directory";
            }
            else {  // was just a file
                $("#textAreaFile").text(files);
                lastCall = "file";
            }
        }
    };
    request.open("GET", "../getContents.php?url=" + url, false);
    request.send("");
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