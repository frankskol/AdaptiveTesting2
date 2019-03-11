package at.jku.ce.adaptivetesting.questions.geogebra;

import at.jku.ce.adaptivetesting.core.IQuestion;
import at.jku.ce.adaptivetesting.core.LogHelper;
import at.jku.ce.adaptivetesting.core.db.ConnectionProvider;
import at.jku.ce.adaptivetesting.questions.XmlQuestionData;
import at.jku.ce.adaptivetesting.questions.datamod.SqlDataStorage;
import at.jku.ce.adaptivetesting.questions.datamod.SqlQuestionXml;
import at.jku.ce.adaptivetesting.views.html.HtmlLabel;
import at.jku.ce.adaptivetesting.views.test.datamod.TableWindow;
import com.vaadin.server.Page;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.hene.expandingtextarea.ExpandingTextArea;

import java.io.*;

//import org.jooq.exception.DataAccessException;

/**
 * Created by Peter
 */

public class GeogebraQuestion extends VerticalLayout implements IQuestion<GeogebraDataStorage>, Cloneable {

    private static final long serialVersionUID = 6373936654529246422L;
    private GeogebraDataStorage solution, userAnswer;
    private float difficulty = 0;
    private Label question;
    private String id;



    public GeogebraQuestion(GeogebraDataStorage solution, GeogebraDataStorage userAnswer, float difficulty, String id) {
        this.difficulty = difficulty;
        this.id = id;
        this.userAnswer = userAnswer;
        this.solution = solution;
    }

    public String getQuestionID() {
        return id;
    }

    public GeogebraQuestion clone() throws CloneNotSupportedException {
        GeogebraQuestion objClone = (GeogebraQuestion)super.clone();
        return objClone;
    }
    public String getQuestionText() {
        return question.getValue();
    }

    @SuppressWarnings("unchecked")
    public GeogebraQuestion cloneThroughSerialize(GeogebraQuestion t) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        serializeToOutputStream(t, bos);
        byte[] bytes = bos.toByteArray();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        return (GeogebraQuestion)ois.readObject();
    }

    private static void serializeToOutputStream(Serializable ser, OutputStream os) throws IOException {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(os);
            oos.writeObject(ser);
            oos.flush();
        } finally {
            oos.close();
        }
    }


    public void setText(Label label, String text) {
        label.setValue(text);
    }

    public void setQuestionText(Label label, String text) {
        label.setValue("<p style=\"color:#0099ff\">" + text + "</p>");
    }

    public void setDifficulty(float difficulty) {
        this.difficulty = difficulty;
    }

    public GeogebraDataStorage getSolution() {
        return solution;
    }
    public GeogebraDataStorage getUserAnswer() {
        return userAnswer;
    }


    public double checkUserAnswer() {
        //TODO
        return 0;
    }

    @Override
    public double performQueryDiagnosis() {
        return 0;
    }

    public float getDifficulty() {
        return difficulty;
    }

    public XmlQuestionData<GeogebraDataStorage> toXMLRepresentation() {
        return new GeogebraQuestionXml(getSolution(), getQuestionText(), getDifficulty());
    }


    public double getMaxPoints() {
        return 1d;
    }

    // Custom Notification that stays on-screen until user presses it
    private void createAndShowNotification(String caption, String description, Notification.Type type) {
        description += "<span style=\"position:fixed;top:0;left:0;width:100%;height:100%\"></span>";
        Notification notif = new Notification(caption, description, type, true);
        notif.setDelayMsec(-1);
        notif.show(Page.getCurrent());
    }
}