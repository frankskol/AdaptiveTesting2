package at.jku.ce.adaptivetesting.questions.geogebra;

/*This file is part of the project "Reisisoft Adaptive Testing",
 * which is licenced under LGPL v3+. You may find a copy in the source,
 * or obtain one at http://www.gnu.org/licenses/lgpl-3.0-standalone.html */
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import at.jku.ce.adaptivetesting.core.AnswerStorage;
import at.jku.ce.adaptivetesting.questions.accounting.util.AccountRecordData;

public class GeogebraDataStorage extends AnswerStorage {
    private static final long serialVersionUID = -8179746363246548456L;
    public static GeogebraDataStorage getEmptyDataStorage() {
        GeogebraDataStorage ds = new GeogebraDataStorage();
        return ds;
    }

    public GeogebraDataStorage() {
    }

    @Override
    public String toString() {
        return toString(true);
    }

    public String toString(boolean html) {
        return "";
    }
}
