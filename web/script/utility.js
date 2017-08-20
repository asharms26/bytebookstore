
function showLoadingGif() {
    var ele = document.createElement("div");
    ele.style.position = "fixed";
    ele.style.backgroundColor = "rgba(0,0,0,0.7)";
    ele.style.width = "100%";
    ele.style.height = "100%";
    ele.style.top = "0";
    ele.style.left = "0";

    var text = document.createElement("h1");
    text.innerHTML = "Loading...";
    text.style.color = "rgb(255,255,255)";
    text.style.marginTop = "50px";
    text.style.marginLeft = "50px";
    text.style.fontSize = "20px";
    ele.appendChild(text);
    ele.id = "loading-icon";
    ele.style.display = "none";

    var jqueryElement = $(ele);
    $("body").append(jqueryElement);
    jqueryElement.fadeIn();
}

function hideLoadingGif() {
    if ($("#loading-icon") != null && typeof ($("#loading-icon")) != "undefined") {
        $("#loading-icon").fadeOut();
        $("#loading-icon").detach();
    }
}

function doPost(r, d, s, f) {
    $.ajax({
        type: "POST",
        dataType: "json",
        url: r,
        data: d,
        success: s,
        error: f
    });
}

