package at.jku.ce.adaptivetesting.views.test.geogebra;

import at.jku.ce.adaptivetesting.core.LogHelper;
import at.jku.ce.adaptivetesting.core.db.ConnectionProvider;
import at.jku.ce.adaptivetesting.core.engine.TestVariants;
import at.jku.ce.adaptivetesting.questions.geogebra.util.GeogebraXmlHelper;
import at.jku.ce.adaptivetesting.questions.geogebra.GeogebraQuestionXml;
import at.jku.ce.adaptivetesting.questions.geogebra.GeogebraQuestion;
import at.jku.ce.adaptivetesting.questions.geogebra.GeogebraDataStorage;
import at.jku.ce.adaptivetesting.views.def.DefaultView;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter
 */

public class GeogebraQuestionKeeper {

    private List<GeogebraQuestion> geogebraList = new ArrayList<>();
    private int size = 0;

    public List<GeogebraQuestion> getGeogebraList() throws Exception {
        List<GeogebraQuestion> clone = new ArrayList<>();
        for (GeogebraQuestion item : geogebraList){
            GeogebraQuestion copy = item.cloneThroughSerialize(item);
            clone.add(copy);
        }
        return clone;
    }

    public int getSize() {
        return size;
    }

    public void initialize() {
        try {
            size = initialize(new File(DefaultView.Servlet.getQuestionFolderName() + TestVariants.GEOGEBRA.getFolderName()));

        } catch (JAXBException e) {
            //e.printStackTrace();
            e.getCause().printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int initialize (File containingFolder) throws JAXBException, IOException {
        assert containingFolder.exists() && containingFolder.isDirectory();

        JAXBContext geoJAXB = JAXBContext.newInstance(
                GeogebraQuestionXml.class, GeogebraDataStorage.class);

        Unmarshaller geoUnmarshaller = geoJAXB
                .createUnmarshaller();

        geogebraList = new ArrayList<>();

        String geoRootElement = GeogebraQuestionXml.class.getAnnotation(
                XmlRootElement.class).name();

        File[] questions = containingFolder.listFiles(f -> f
                .isFile()
                && (f.canRead() || f.setReadable(true))
                && f.getName().endsWith(".xml"));

        // read all questions
        LogHelper.logInfo("Found " + questions.length + " potential question(s)");
        int successfullyLoaded = 0;
        for (File f : questions) {
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();
            try {
                reader = new BufferedReader(new InputStreamReader(
                        new BOMInputStream(new FileInputStream(f),
                                ByteOrderMark.UTF_8), "UTF8"));

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
            String fileAsString = sb.toString().replaceAll("& ", "&amp; ");
            if (fileAsString.contains(geoRootElement)) {
                // Geogebra Question
                questionInitializedInfo(f, successfullyLoaded, GeogebraQuestion.class.getName());
                GeogebraQuestionXml question = (GeogebraQuestionXml) geoUnmarshaller
                        .unmarshal(new StringReader(fileAsString));
                GeogebraQuestion pq = GeogebraXmlHelper.fromXml(question, f.getName());
                geogebraList.add(pq);
                successfullyLoaded++;
            }
            else {
                LogHelper.logInfo("GeogebraTestView: item type not supported for " + f.getName() + ", ignoring file.");
            }
        }
        LogHelper.logInfo("Successfully loaded " + successfullyLoaded + " question(s).");
        String notificationCaption, notificationDescription;
        switch (successfullyLoaded) {
            case 0:
                notificationCaption = "Ladevorgang fehlgeschlagen";
                notificationDescription = "Es wurden keine ladbaren Items gefunden";
                break;
            case 1:
                notificationCaption = "Ladevorgang abgeschlossen";
                notificationDescription = "Es wurde (" + successfullyLoaded + ") Frage erfolgreich geladen";
                break;
            default:
                notificationCaption = "Ladevorgang abgeschlossen";
                notificationDescription = "Es wurden (" + successfullyLoaded + ") Fragen erfolgreich geladen";
        }
        Notification.show(notificationCaption, notificationDescription, Notification.Type.TRAY_NOTIFICATION);

        return questions.length;
    }


    private static void questionInitializedInfo(File file, int counter, String questionType) {
        counter++;
        LogHelper.logInfo("(" + counter + ") Loading questionfile: " + file.getName());
        LogHelper.logInfo("Type: " + questionType);
    }
}
