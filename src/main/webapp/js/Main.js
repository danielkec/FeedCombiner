/**
 * On load gets time to refresh feeds from server and sets it as countdown val.
 * Then runs countdown method.
 */
window.onload = new function () {
    resetCountdown();
    countdown();
};

function resetCountdown() {
    var restTimeoutQuery = {
        "type": "GET",
        "url": "timer/"
    };

    $.ajax(restTimeoutQuery).then(function (data) {
        $("#countdown").text(data);
    });
}

/**
 * Countdown preset number of seconds and reload a page on zero
 */
function countdown() {
    seconds = parseInt($("#countdown").text(), 10);
    if (seconds === 0) {
        resetCountdown();
        seconds = 'Refreshing...';
    } else {
        seconds--;
    }
    $("#countdown").text(seconds);
    setTimeout(countdown, 1000);
}

function isNumber(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}

function getfixedUrl(protocol) {
    return document.URL.replace("/html/", "/" + protocol + "/");
}
function redirect(protocol) {
    var fixedUrl = getfixedUrl(protocol);
    window.open(fixedUrl, "_self");
}
function redirectToOverView() {
    window.open(document.URL.replace(new RegExp("\\b/html/\\b.*", "gi"), ""), "_self");
}