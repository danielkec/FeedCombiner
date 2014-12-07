var CombinedFeedCRUDModel = function() {
    this.numberOfClicks = ko.observable(0);

    this.deleteFeed = function() {
        this.numberOfClicks(this.numberOfClicks() + 1);
    };

    this.updateFeed = function() {
        this.numberOfClicks(0);
    };

    this.hasClickedTooManyTimes = ko.computed(function() {
        return this.numberOfClicks() >= 3;
    }, this);
};

ko.applyBindings(new CombinedFeedCRUDModel());
