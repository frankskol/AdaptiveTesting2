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
    private int value;

    public String getPredicate() {
        return predicate;
    }

    @XmlElement(name = "predicate")
    private String predicate;

    public static GeogebraDataStorage getEmptyDataStorage() {
        GeogebraDataStorage ds = new GeogebraDataStorage(1);
        return ds;
    }

    public GeogebraDataStorage() {
    }
    public GeogebraDataStorage(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value == 1 ? "<Korrekte Lösung>" : "<Falsche Lösung>";
    }

    public void setValue(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
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
        return value == other.value;
    }
}
