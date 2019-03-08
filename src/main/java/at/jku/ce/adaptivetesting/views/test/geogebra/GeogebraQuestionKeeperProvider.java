package at.jku.ce.adaptivetesting.views.test.geogebra;

import at.jku.ce.adaptivetesting.questions.geogebra.*;

import java.util.List;

public class GeogebraQuestionKeeperProvider {
    private static GeogebraQuestionKeeper geoKeeper = new GeogebraQuestionKeeper();

    public void initialize() {
        geoKeeper.initialize();
    }

    public int getSize() {
        return geoKeeper.getSize();
    }

    public List<GeogebraQuestion> getGeogebraList() throws Exception {
        return geoKeeper.getGeogebraList();
    }

}
