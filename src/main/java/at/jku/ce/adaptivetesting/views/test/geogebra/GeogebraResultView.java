package at.jku.ce.adaptivetesting.views.test.geogebra;

import at.jku.ce.adaptivetesting.core.AnswerStorage;
import at.jku.ce.adaptivetesting.core.IQuestion;
import at.jku.ce.adaptivetesting.core.IResultView;
import at.jku.ce.adaptivetesting.core.LogHelper;
import at.jku.ce.adaptivetesting.core.engine.HistoryEntry;
import at.jku.ce.adaptivetesting.core.engine.ResultFiredArgs;
import at.jku.ce.adaptivetesting.questions.datamod.SqlDataStorage;
import at.jku.ce.adaptivetesting.questions.datamod.SqlQuestion;
import at.jku.ce.adaptivetesting.views.def.DefaultView;
import at.jku.ce.adaptivetesting.views.html.HtmlLabel;
import at.jku.ce.adaptivetesting.views.test.datamod.TableWindow;
import com.github.rcaller.exception.ExecutionException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Peter
 */

public class GeogebraResultView extends VerticalLayout implements View, IResultView {

	private static final long serialVersionUID = -6619938011293967055L;
	private final String imageFolder = VaadinServlet.getCurrent().getServletConfig().
			getServletContext().getInitParameter("at.jku.ce.adaptivetesting.imagefolder") + "/";
	private String resultsFolder;

	public GeogebraResultView(ResultFiredArgs args, String title, String resultsFolder) {
		this.resultsFolder = resultsFolder;
		setSpacing(true);
		addComponent(new HtmlLabel(title));
		//addComponent(HtmlLabel.getCenteredLabel("h2", "Test abgeschlossen"));
		addComponent(HtmlLabel.getCenteredLabel("Der Test wurde beendet, da "
				+ (args.outOfQuestions ? "keine weiteren Fragen verfügbar sind."
				: "dein Kompetenzniveau bestimmt wurde.")));

		addComponent(HtmlLabel
				.getCenteredLabel("Im Folgenden siehst du die Fragen und die gegebenen Antworten in zeitlich absteigender Reihenfolge."));
		addComponent(HtmlLabel
				.getCenteredLabel("Die Zahl in der ersten Spalte bezieht sich auf die Schwierigkeit der jeweiligen Frage.<br/>Negative Zahlen stehen für leichtere Fragen, positive Zahlen kennzeichnen schwierigere Fragen."));

		// Create HTML table of the history
		Table table = new Table();
		table.addContainerProperty("#", Integer.class, null);
		table.addContainerProperty("Schwierigkeitsgrad", Float.class, null);
		table.addContainerProperty("Resultat", String.class, null);
		//List<HistoryEntry> entries = Lists.reverse(args.history);
		List<HistoryEntry> entries = new ArrayList<HistoryEntry>(args.history);
		Collections.reverse(entries);
		int nr = entries.size();
		for (HistoryEntry entry : entries) {
			table.addItem(new Object[] { new Integer(nr), entry.question.getDifficulty(),
					isCorrect(entry.points, entry.question.getMaxPoints())}, null);
			nr--;
		}
		int size = table.size();
		if (size > 10) {
			size = 10;
		}
		table.setPageLength(size);
		addComponent(table);
		setComponentAlignment(table, Alignment.MIDDLE_CENTER);

		addComponent(HtmlLabel.getCenteredLabel("h3",
				"Dein Kompetenzniveau ist: <b>" + args.skillLevel + "</b>"));
		addComponent(HtmlLabel.getCenteredLabel("Delta:  " + args.delta));
		storeResults(args);

		Image image = new Image("", new FileResource(new File(imageFolder + "accounting_Kompetenzmodell.png")));

		addComponent(image);
		setComponentAlignment(image, Alignment.MIDDLE_CENTER);

		/*Link link = new Link("Umfrage Adaptive Testing",
				new ExternalResource("https://docs.google.com/forms/d/e/1FAIpQLSdg0GyIhMymJaLB6hCSkutV41WqJs09qCUSn9DMmSYJ3Lu_Pg/viewform?c=0&w=1"));
		link.setTargetName("_blank");
		link.setDescription("Der Fragebogen soll Schwächen der Performance und Usability der Test-Software aufzeigen. " +
				"Die Auswertung wird zur Verbessung eventueller Schwachstellen herangezogen. " +
				"Damit die Ergebnisse des Fragebogens denen des Tests zugeordnet werden können, " +
				"ist es notwendig, den zuvor erstellen anonymen Benutzernamen anzugeben.");
		addComponent(link);
		setComponentAlignment(link, Alignment.MIDDLE_CENTER);*/
	}

	private void storeResults(ResultFiredArgs args) {
		File resultFile;
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
			LocalDateTime now = LocalDateTime.now();
			String fileName = new String(args.student.getStudentIDCode()+ "_" + dtf.format(now) + ".csv");
			resultFile = new File(new File(resultsFolder),fileName);
			BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile));
			writer.write(args.student.toString()+"\n");
			writer.write(Double.toString(args.skillLevel)+"\n");
			writer.write(Double.toString(args.delta)+"\n");
			writer.write(Boolean.toString(args.outOfQuestions)+"\n");
			writer.write(args.history.size()+"\n");
			for (HistoryEntry entry : args.history) {
				writer.write(
						entry.question.getQuestionText() + ";" +
								entry.question.getDifficulty() + ";" +
								entry.question.getSolution().toString() + ";" +
								entry.question.getUserAnswer().toString() + ";" +
								isCorrect(entry.points, entry.question.getMaxPoints()) + "\n");
			}
			writer.close();
		} catch (Exception var9) {
			throw new ExecutionException("Can not create a temporary file for storing the results: " + var9.toString());
		}
	}

	private String isCorrect(double points, double maxPoints) {
		return points + " / " + maxPoints + " (" + 100 * points / maxPoints + "% )";
	}

	@Override
	public void enter(ViewChangeEvent event) {
		DefaultView.setCurrentPageTitle(event);
	}
}
