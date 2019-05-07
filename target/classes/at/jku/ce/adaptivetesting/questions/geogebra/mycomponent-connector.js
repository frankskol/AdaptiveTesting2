at_jku_ce_adaptivetesting_questions_geogebra_GeogebraComponent = function() {
    // Create the component
    var geogebraComponent =
        new mylibrary.GeogebraComponent(this.getElement());

    // Handle changes from the server-side
    this.onStateChange = function() {
        geogebraComponent.setValue(this.getState().value);
    };

    // Pass user interaction to the server-side
    var self = this;
    mycomponent.click = function() {
        self.onClick(geogebraComponent.getValue());
    };
};