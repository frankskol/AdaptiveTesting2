package at.jku.ce.adaptivetesting.questions.geogebra;

import com.vaadin.annotations.JavaScript;
import com.vaadin.shared.ui.JavaScriptComponentState;
import com.vaadin.ui.AbstractJavaScriptComponent;

import java.io.Serializable;
import java.util.ArrayList;

@JavaScript({"mylibrary.js", "mycomponent-connector.js"})
public class GeogebraComponent extends AbstractJavaScriptComponent {
    public interface ValueChangeListener extends Serializable {
        void valueChange();
    }
    ArrayList<ValueChangeListener> listeners =
            new ArrayList<ValueChangeListener>();
    public void addValueChangeListener(
            ValueChangeListener listener) {
        listeners.add(listener);
    }
    public void setValue(String value) {
        getState().value = value;
    }

    public String getValue() {
        return getState().value;
    }

    @Override
    protected GeogebraComponentState getState() {
        return (GeogebraComponentState) super.getState();
    }
}