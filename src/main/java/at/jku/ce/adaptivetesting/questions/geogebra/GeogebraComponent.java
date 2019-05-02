package at.jku.ce.adaptivetesting.questions.geogebra;

import com.vaadin.annotations.JavaScript;
import com.vaadin.shared.ui.JavaScriptComponentState;
import com.vaadin.ui.AbstractJavaScriptComponent;

import java.io.Serializable;
import java.util.ArrayList;

@JavaScript({ "js_label.js" })
public class GeogebraComponent extends AbstractJavaScriptComponent {

    public GeogebraComponent(String xhtml) {
        getState().xhtml = xhtml;
    }

    @Override
    protected GeogebraComponentState getState() {
        return (GeogebraComponentState) super.getState();
    }
}