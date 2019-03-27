package at.jku.ce.adaptivetesting.questions.geogebra;

import at.jku.ce.adaptivetesting.core.AnswerStorage;
import at.jku.ce.adaptivetesting.core.LogHelper;
import at.jku.ce.adaptivetesting.core.db.ConnectionProvider;
import at.jku.ce.adaptivetesting.questions.accounting.ProfitDataStorage;
import at.jku.ce.adaptivetesting.views.html.HtmlLabel;
import at.jku.ce.adaptivetesting.views.test.datamod.TableWindow;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Window;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Vector;

/**
 * Created by Peter
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class GeogebraDataStorage extends AnswerStorage {

    private static final long serialVersionUID = -8379746363246548567L;

    @XmlElement(name = "Id")
    private String Id;

    public static GeogebraDataStorage getEmptyDataStorage() {
        return new GeogebraDataStorage();
    }

    public GeogebraDataStorage() {
        this.Id = null;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (Id == null ? 0 : Id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        GeogebraDataStorage other = (GeogebraDataStorage) obj;
        return Id == other.Id;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Question ID: <p>" + Id + "</p>");
        return buffer.toString();
    }

}