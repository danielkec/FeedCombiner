var KOModel = function () {

    this.addUrl = function (name,indx) {
        console.log("Adding url " + "urls"+indx);
        var url = window.prompt("Enter URL of a new feed source", "http://www.zive.cz/rss/sc-47/");
        var parentDiv = $("#"+"urls"+indx);
        var lastUrlEl = parentDiv.children().last();//.children().last().text("ssssssss");
        if (parentDiv.children().length !== 0) {
            var cloned = lastUrlEl.clone();
            cloned.appendTo(parentDiv);
            cloned.children().last().text(url);
            cloned.children().last().attr("href", url);
            var dataBindVal = cloned.children().first().attr("data-bind").split(",");
            var idNum = cloned.attr("id").split(".")[1];
            idNum++;
            cloned.children().first().attr("data-bind", dataBindVal[0] + "," + dataBindVal[1] + "," + idNum + ")");
            cloned.attr("id", "url." + idNum);
        }else{
           var urlDiv = $('<div></div>');
           urlDiv.appendTo(parentDiv);
           urlDiv.attr("id","url."+indx+"1");//its first one
           var remButton = $('<button class="small-btn" data-bind="click: deleteUrl.bind($data,\''+name+'\',\''+indx+'1\')">x</button>');
           remButton.appendTo(urlDiv);
           var ahref = $('<a class="url"></a>');
           ahref.text(url);
           ahref.attr("href",url);
           ahref.appendTo(urlDiv);

        }

        //rebind knockout model after changing view
        ko.cleanNode(document);
        ko.applyBindings(new KOModel());
    };

    this.createFeed = function () {
        var title = window.prompt("Enter unique name if a new combined feed.", "");
        console.log("Creating feed " + title);
        $.ajax({
            type: "GET",
            url: "manage/create",
            data: {"title": title}
        }).then(function (data) {
            location.reload();
        });
    };

    this.deleteFeed = function (title) {
        console.log("Deleting feed " + title);
        $.ajax({
            type: "GET",
            url: "manage/delete",
            data: {"title": title}
        }).then(function (data) {
            location.reload();
        });
    };

    /**
     * Saves changes of current combined feed over RS manage/update
     * @param {type} kontext id of div representing combined feed
     */
    this.updateFeed = function (kontext) {
        console.log("Updating feed " + kontext);

        var name = $("#" + kontext).find("#name").text();
        var desc = $("#" + kontext).find("#description").val();

        this.restQuery = {
            "type": "GET",
            "url": "manage/update",
            "traditional": "true",
            "data": {"title": name,
                "description": desc,
                "urls": []
            }
        };

        var contextSelf = this; // reference to this in current scope
        $("#" + kontext).find(".url").each(function (ind, obj) {
            var url = $(this).text();
            console.log(url);
            contextSelf.restQuery.data.urls.push(url);
        });

        $.ajax(this.restQuery).then(function (data) {
            location.reload();
        });

    };

    this.deleteUrl = function (title, posId) {
        console.log("Deleting url " + "#url\\." + posId);
        $("#url\\." + posId).remove();
    };
    
    /**
     * Invoked by change interval button.
     * Asks for numer between 3-MAX_INT and than call RS if timer - setinterval.
     * @returns {undefined}
     */
    this.changeTimerInterval = function () {
        console.log("Changing timer interval");
        var interval = window.prompt("Enter integer value on new ", "10");
        if(!isNumber(interval)){
            window.alert(interval+"is not a number!");
            return;
        }
        if(interval<3){
            window.alert("Interval has to be higher than 3 seconds!");
            return;
        }
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
