var geogebraLibrary = geogebraLibrary || {};

geogebraLibrary.GeogebraComponent = function (element) {
    element.innerHTML =
        "<div id='applet_container'></div>" +
        "<input type='button' value='Submit'/>" +
        "</div>";
    //Variable for the GeoGebra-Exercise
    var ggbExercise = null;

    //Returns the exercise fraction, if the GeoGebra-Applet is an exercise
    this.getValue = function () {
            return geoApp.getValue("correct");
    };

    //Injects the Div "applet_container" with an GeoGebra-Applet with the given material number
    this.setValue = function (materialNr) {
        if (materialNr != null) {
            ggbExercise = new GGBApplet({
                "id": 'geoApp',
                "material_id": materialNr,
                "width": 800,
                "height": 600,
                "borderColor": "#55FF00"
            }, true);
            ggbExercise.inject('applet_container');
            //alert("App with the material number: " + value + " loaded!");
        } else {
            alert("No correct material number specified!");
        }
    };

    //Default implementation of the click fuction; Is overwritten in the JavaScript-Connector
    this.click = function () {
        alert(geoApp.getValue("correct"));
    };

    var button = element.getElementsByTagName("input")[0];
    var self = this;

    //Function click is asigned to the button
    button.onclick = function () {
        self.click();
    };
};