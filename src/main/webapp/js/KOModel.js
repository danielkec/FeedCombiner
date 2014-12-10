/**
 * ViewModel of the overview.xsl
 * 
 * @author Daniel Kec <daniel at kecovi.cz>
 * @since Dec 9, 2014
 */
var KOModel = function () {
    
    /**
     * Add url localy to the combined feed view, has to be updated then by update button!
     * 
     * @param {type} name title of the feed to be updated
     * @param {type} indx index of the feed
     * @returns {undefined} void
     */
    this.addUrl = function (name, indx) {
        console.log("Adding url " + "urls" + indx);
        var url = window.prompt("Enter URL of a new feed source", "http://www.zive.cz/rss/sc-47/");
        var parentDiv = $("#" + "urls" + indx);
        var lastUrlEl = parentDiv.children().last();
        //if there is at least one url in the feed, we can just clone it and iterate id
        if (parentDiv.children().length !== 0) {
            var cloned = lastUrlEl.clone();
            cloned.appendTo(parentDiv);
            cloned.children().last().text(url);
            cloned.children().last().attr("href", url);
            //parsing attr example: data-bind="click: addUrl.bind($data,"BuzzFeed and Zive.cz","2")"
            var dataBindVal = cloned.children().first().attr("data-bind").split(",");
            var idNum = cloned.attr("id").split(".")[1];
            idNum++;
            cloned.children().first().attr("data-bind", dataBindVal[0] + "," + dataBindVal[1] + "," + idNum + ")");
            cloned.attr("id", "url." + idNum);
        // there are no urls in the feed yet    
        } else {
            var urlDiv = $('<div></div>');
            urlDiv.appendTo(parentDiv);
            urlDiv.attr("id", "url." + indx + "1");//its first one, index of the url is 1
            var remButton = $('<button class="small-btn" data-bind="click: deleteUrl.bind($data,\'' + name + '\',\'' + indx + '1\')">x</button>');
            remButton.appendTo(urlDiv);
            var ahref = $('<a class="url"></a>');
            ahref.text(url);
            ahref.attr("href", url);
            ahref.appendTo(urlDiv);

        }
        //rebind knockout model after changing view
        ko.cleanNode(document);
        ko.applyBindings(new KOModel());
    };

    /**
     * Creates new empty view with prompted name.
     * 
     * @returns {undefined} void
     */
    this.createFeed = function () {
        var title = window.prompt("Enter unique name of a new combined feed.", "");
        console.log("Creating feed " + title);
        $.ajax({
            type: "GET",
            url: "manage/create",
            data: {"title": title}
        }).then(function (data) {
            location.reload();
        });
    };

    /**
     * Deletes combined feed from the server by its name/title
     * 
     * @param {type} title which feed is going to be deleted
     * @returns {undefined} void
     */
    this.deleteFeed = function (title) {
        console.log("Deleting feed " + title);
        $.ajax({
            type: "GET",
            url: "manage/delete",
            data: {"title": title}
        }).then(function (data) {
            if (data.deleted) {
                console.log("Cobined feed " + title + " has been succesfully deleted.");
            } else {
                console.log("Cobined feed " + title + " has NOT been deleted.");
            }
            location.reload();
        });
    };

    /**
     * Saves changes of current combined feed over RS manage/update
     * 
     * @param {type} kontext id of div representing combined feed
     * @returns {undefined} void
     */
    this.updateFeed = function (kontext) {
        console.log("Updating feed " + kontext);

        var name = $("#" + kontext).find("#name").text();
        var desc = $("#" + kontext).find("#description").val();

        this.restQuery = {
            "type": "GET",
            "url": "manage/update",
            "traditional": "true", //jquery cant send urls as List<String> without this
            "data": {"title": name,
                "description": desc,
                "urls": []//urls array will be send as multitple queryparams with same name
            }
        };

        var contextSelf = this; // reference to this in current scope
        $("#" + kontext).find(".url").each(function (ind, obj) {
            var url = $(this).text();
            contextSelf.restQuery.data.urls.push(url);
        });
        //updating feed with title-name by data from overview
        $.ajax(this.restQuery).then(function (data) {
            location.reload();
        });

    };

    /**
     * Remove URL from combined feed html view, needs to be updated to server then.
     * 
     * @param {type} title name of the combined feed
     * @param {type} posId position id is two digit number, first is number of feed on overview and second digit is number of url 
     * @returns {undefined} void
     */
    this.deleteUrl = function (title, posId) {
        console.log("Deleting url " + "#url\\." + posId);
        $("#url\\." + posId).remove();
    };

    /**
     * Invoked by change interval button.
     * Asks for numer between 3-MAX_INT and than call RS if timer - setinterval.
     * 
     * @returns {undefined} void
     */
    this.changeTimerInterval = function () {
        console.log("Changing timer interval");
        var interval = window.prompt("Enter integer value on new ", "10");
        if (!isNumber(interval)) {
            window.alert("Entered value " + interval + "is not a number!");
            return;
        }
        if (interval < 3) {
            window.alert("Interval has to be higher than 3 seconds!");
            return;
        }
        //setting new interval to server's timer
        $.ajax({
            type: "GET",
            url: "timer/setinterval",
            traditional: "true",
            data: {"interval": interval}
        }).then(function (data) {
            location.reload();
        });
    };
};
//initial binding of the knockout view
ko.applyBindings(new KOModel());

