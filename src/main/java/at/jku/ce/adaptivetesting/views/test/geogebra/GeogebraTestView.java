package at.jku.ce.adaptivetesting.views.test.geogebra;

/*This file is part of the project "Reisisoft Adaptive Testing",
 * which is licenced under LGPL v3+. You may find a copy in the source,
 * or obtain one at http://www.gnu.org/licenses/lgpl-3.0-standalone.html */

import at.jku.ce.adaptivetesting.core.LogHelper;
import at.jku.ce.adaptivetesting.core.db.ConnectionProvider;
import at.jku.ce.adaptivetesting.core.engine.StudentData;
import at.jku.ce.adaptivetesting.questions.datamod.SqlQuestion;
import at.jku.ce.adaptivetesting.questions.geogebra.*;
import at.jku.ce.adaptivetesting.views.Views;
import at.jku.ce.adaptivetesting.views.html.HtmlLabel;
import at.jku.ce.adaptivetesting.views.test.TestView;
import at.jku.ce.adaptivetesting.views.test.geogebra.GeogebraQuestionKeeperProvider;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeogebraTestView extends TestView {

	private static final long serialVersionUID = -4764723794449575244L;
	private String studentIDCode = new String();
	private String studentGender = new String();
	private String studentExperience = new String();
	private StudentData student;
	private final String imageFolder = VaadinServlet.getCurrent().getServletConfig().
			getServletContext().getInitParameter("at.jku.ce.adaptivetesting.imagefolder") + "/";
	private GeogebraQuestionKeeperProvider QuestionProvider;

	public GeogebraTestView(String quizName) {
		super(quizName);
		QuestionProvider = new GeogebraQuestionKeeperProvider();
		//Window mainWindow = new Window("Injecttest Application");
		Label ggbElement = new Label("<div id=\"ggb-element\"></div>",ContentMode.HTML);
		Button cancel = new Button("Test abbrechen");
		cancel.addClickListener(e -> {
			getUI().getNavigator().navigateTo(Views.DEFAULT.toString());
			LogHelper.logInfo("The test has been canceled by the student");
		});
		//StringBuilder script = new StringBuilder();
		//script
		//		.append("var ggbApp = new GGBApplet({\"appName\": \"graphing\", \"width\": 800, \"height\": 600, \"showToolBar\": true, \"showAlgebraInput\": true, \"showMenuBar\": true }, true);")
		//		.append("  window.addEventListener(\"load\", function() { ggbApp.inject('ggb-element');\n" +
		//				"    });");
		// @formatter:on
		//mainWindow.executeJavaScript(script.toString());

		addComponent(ggbElement);
		addHelpButton(cancel);
	}

	//data of the students with the question asked at the beginning
	@Override
	public void startQuiz(StudentData student) {
		// Remove everything from the layout, save it for displaying after clicking OK
		final Component[] components = new Component[getComponentCount()];
		for (int i = 0; i < components.length; i++) {
			components[i] = getComponent(i);
		}
		removeAllComponents();
		// Create first page
		VerticalLayout layout = new VerticalLayout();
		addComponent(layout);

		String defaultValue = "-- Bitte auswählen --";
		ComboBox gender = new ComboBox("Geschlecht");
		String[] genderItems = {defaultValue, "männlich", "weiblich"};
		gender.addItems(genderItems);
		gender.setWidth(15, Unit.PERCENTAGE);
		gender.setValue(defaultValue);
		gender.setNullSelectionAllowed(false);
		gender.setFilteringMode(FilteringMode.CONTAINS);
		gender.setEnabled(true);

		ComboBox experience = new ComboBox("Erfahrung mit Geogebra");
		String[] experienceItems = {defaultValue, "Anfänger", "Fortgeschritten", "Profi"};
		experience.addItems(experienceItems);
		experience.setWidth(15, Unit.PERCENTAGE);
		experience.setValue(defaultValue);
		experience.setNullSelectionAllowed(false);
		experience.setFilteringMode(FilteringMode.CONTAINS);
		experience.setEnabled(true);

		Label studentCode = new Label("<p/>Damit deine Antworten mit späteren Fragebogenergebnissen verknüpft werden können, ist es notwendig, einen anonymen Benutzernamen anzulegen. Erstelle deinen persönlichen Code nach folgendem Muster:",ContentMode.HTML);
		TextField studentCodeC1 = new TextField("Tag und Monat der Geburt (DDMM), z.B. \"1008\" für Geburtstag am 10. August");
		TextField studentCodeC2 = new TextField("Zwei Anfangsbuchstaben des Vornamens, z.B. \"St\" für \"Stefan\"");
		TextField studentCodeC3 = new TextField("Zwei Anfangsbuchstaben des Vornamens der Mutter,, z.B. \"Jo\" für \"Johanna\"");

		Label thankYou = new Label("<p/>Danke für die Angaben.<p/>", ContentMode.HTML);
		Button cont = new Button("Weiter", e -> {
			removeAllComponents();
			studentIDCode = new String(studentCodeC1.getValue() + studentCodeC2.getValue() + studentCodeC3.getValue());
			studentGender = (gender.getValue() == null) ? new String("undefined") : gender.getValue().toString();
			studentExperience = (experience.getValue() == null) ? new String("undefined") : experience.getValue().toString();

			this.student = new StudentData(studentIDCode, quizName, studentGender, studentExperience);
			LogHelper.logInfo("StudentData: " + this.student.toString());

			quizRules(components);
		});

		layout.addComponent(HtmlLabel.getCenteredLabel("h1", "Fragen zu deiner Person"));// Title of the quiz
		layout.addComponent(gender);
		layout.addComponent(experience);
		layout.addComponent(studentCode);
		layout.addComponent(studentCodeC1);
		layout.addComponent(studentCodeC2);
		layout.addComponent(studentCodeC3);
		layout.addComponent(thankYou);
		layout.addComponent(cont);
//		layout.setComponentAlignment(components[0], Alignment.MIDDLE_CENTER);
	}

	//describes what happens, when you click on the weiter button
	public void quizRules(Component[] components){

		VerticalLayout verticalLayout = new VerticalLayout();

		Button cont = new Button("Test beginnen", e -> {

			removeAllComponents();
			for (Component c : components) {
				addComponent(c);
			}
			super.startQuiz(student);
		});

		verticalLayout.addComponent(cont);
		addComponent(verticalLayout);
	}

	@Override
	public void loadQuestions() {
		try {
			QuestionProvider.initialize();

			List<GeogebraQuestion> geogebraList = QuestionProvider.getGeogebraList();

			geogebraList.forEach(q -> addQuestion(q));

		} catch (JAXBException | IOException e1) {
			LogHelper.logThrowable(e1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
