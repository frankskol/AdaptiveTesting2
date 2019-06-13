package at.jku.ce.adaptivetesting.questions.geogebra;

/*This file is part of the project "Reisisoft Adaptive Testing",
 * which is licenced under LGPL v3+. You may find a copy in the source,
 * or obtain one at http://www.gnu.org/licenses/lgpl-3.0-standalone.html */
import at.jku.ce.adaptivetesting.core.LogHelper;
import at.jku.ce.adaptivetesting.questions.accounting.util.ProfitPossibleAnswers;
import at.jku.ce.adaptivetesting.core.IQuestion;
import at.jku.ce.adaptivetesting.views.html.HtmlLabel;
import at.jku.ce.adaptivetesting.questions.XmlQuestionData;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;

import java.io.*;

public class GeogebraQuestion extends VerticalLayout implements
        IQuestion<GeogebraDataStorage>, Cloneable {

    private static final long serialVersionUID = 6373936654529246422L;
    private GeogebraDataStorage solution;
    private float difficulty = 0;
    final GeogebraComponent mycomponent;
    private boolean submitted = false;
    private Label question;

    private String id;

    public GeogebraQuestion(GeogebraDataStorage solution, Float difficulty,
                          String questionText, String id) {
        this(solution, GeogebraDataStorage.getEmptyDataStorage(), difficulty,
                questionText, id);
    }

    public GeogebraQuestion(GeogebraDataStorage solution,
                            GeogebraDataStorage prefilled, float difficulty, String questionText, String id) {
        // super(1, 2);
        this.difficulty = difficulty;
        this.id = id;
        // Variable questionText must be structured in the following manner:
        // The material ID is before "/", the question Text for the label after it.
        this.solution = solution;
        solution.setValue(1);
        setSpacing(true);
        mycomponent = new GeogebraComponent();
        mycomponent.setValue(questionText);
        mycomponent.setWidth("800");
        mycomponent.setHeight("650");
        mycomponent.addValueChangeListener(
                new GeogebraComponent.ValueChangeListener() {
                    @Override
                    public void valueChange() {
                        submitted = true;
                    }
                });
        question = new HtmlLabel();
        setQuestionText(solution.getPredicate());
        addComponent(question);
        addComponent(mycomponent);
        setSpacing(true);
    }

    @Override
    public String getQuestionID() {
        return id;
    }

    public GeogebraQuestion clone() throws CloneNotSupportedException {
        GeogebraQuestion objClone = (GeogebraQuestion)super.clone();
        return objClone;

    }

    @SuppressWarnings("unchecked")
    public static  GeogebraQuestion cloneThroughSerialize(GeogebraQuestion t) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        serializeToOutputStream(t, bos);
        byte[] bytes = bos.toByteArray();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        return (GeogebraQuestion)ois.readObject();
    }

    private static void serializeToOutputStream(Serializable ser, OutputStream os)
            throws IOException {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(os);
            oos.writeObject(ser);
            oos.flush();
        } finally {
            oos.close();
        }
    }

    @Override
    public String getQuestionText() {
        return question.getValue();
    }

    public void setQuestionText(String questionText) {
        question.setValue(questionText);
    }

    public void setDifficulty(float difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public GeogebraDataStorage getSolution() {
        return solution;
    }

    @Override
    public GeogebraDataStorage getUserAnswer() {
        //Geogebra Questions must have a boolean Variable component. That variable will be used for checking user input.
        //If the user does not click on submit, the answer will be counted as wrong.
        try {
            return new GeogebraDataStorage((int) Double.parseDouble(mycomponent.getValue()));
        } catch(java.lang.NumberFormatException e){
            LogHelper.logInfo("Answer was not submitted!");
            return new GeogebraDataStorage(0);
        }

    }

    @Override
    public double checkUserAnswer() {
        LogHelper.logInfo("Questionfile: " + id);
        if (solution.equals(getUserAnswer())) {
            LogHelper.logInfo("Correct answer");
            return 1.0d;
        } else {
            LogHelper.logInfo("Incorrect answer");
            return 0.0d;
        }
    }

    @Override
    public double performQueryDiagnosis() {
        return 0;
    }

    @Override
    public float getDifficulty() {
        return difficulty;
    }

    @Override
    public XmlQuestionData<GeogebraDataStorage> toXMLRepresentation() {
        return new GeogebraQuestionXml(getSolution(), getQuestionText(),
                getDifficulty());
    }

    @Override
    public double getMaxPoints() {
        return 1d;
    }

    public boolean getSubmitted() {
        return submitted;
    }


}
