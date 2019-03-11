package at.jku.ce.adaptivetesting.questions.geogebra;

import at.jku.ce.adaptivetesting.core.AnswerStorage;
import at.jku.ce.adaptivetesting.core.LogHelper;
import at.jku.ce.adaptivetesting.core.db.ConnectionProvider;
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

    private static final long serialVersionUID = -8179746363246548567L;
    private String answer;

    @XmlElement(name = "Id")
    private String Id;


    public static GeogebraDataStorage getEmptyDataStorage() {
        return new GeogebraDataStorage();
    }

    public String getId() {
        return Id;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Question ID: <p>" + Id + "</p>");
        buffer.append("Output: <p>" + answer + "</p>");
        return buffer.toString();
    }

}