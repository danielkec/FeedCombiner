/**
 * On load gets time to refresh feeds from server and sets it as countdown val.
 * Then runs countdown method.
 * 
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 9, 2014
 */
window.onload = new function () {
    resetCountdown();
    countdown();
};

/**
 * Get timout from the timer rest api and sets it as
 * starting value for the countdout el.
 * 
 * @returns {undefined} void
 */
function resetCountdown() {
    var restTimeoutQuery = {
        "type": "GET",
        "url": "timer/"
    };
    //call rest/timer to get seconds left before new refresh
    $.ajax(restTimeoutQuery).then(function (data) {
        $("#countdown").text(data);//setting new value to countdown div
    });
}

/**
 * Countdown preset number of seconds and reload a page on zero
 * 
 * @returns {undefined} void
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

/**
 * Check if param is a number.
 * Used with new interval dialog.
 * 
 * @param {type} n val to be evaluated
 * @returns {Boolean} true if is a number
 */
function isNumber(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}

/**
 * Fixing current html url to call atom/json
 * Called from:http://localhost:8080/wls-fc/rest/expose/html/All%20in%20one
 * Example  input: json
 * Example output: http://localhost:8080/wls-fc/rest/expose/json/All%20in%20one
 * 
 * @param {type} protocol atom/json
 * @returns {String} fixed current url
 */
function getfixedUrl(protocol) {
    return document.URL.replace("/html/", "/" + protocol + "/");
}

/**
 * Redirect from html feed to its json or atom version
 * 
 * Called from:http://localhost:8080/wls-fc/rest/expose/html/All%20in%20one
 * Example  input: json
 * Redirect page to: http://localhost:8080/wls-fc/rest/expose/json/All%20in%20one
 * 
 * @param {type} protocol
 * @returns {undefined} void
 */
function redirect(protocol) {
    var fixedUrl = getfixedUrl(protocol);
    window.open(fixedUrl, "_self");
}

/**
 * Redirect from html feed home, to overview of all CombinedFeeds
 * 
 * @returns {undefined} void
 */
function redirectToOverView() {
    //remove html part of current url
    window.open(document.URL.replace(new RegExp("\\b/html/\\b.*", "gi"), ""), "_self");
}