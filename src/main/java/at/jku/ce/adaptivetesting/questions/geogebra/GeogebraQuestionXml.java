package at.jku.ce.adaptivetesting.questions.geogebra;

/*This file is part of the project "Reisisoft Adaptive Testing",
 * which is licenced under LGPL v3+. You may find a copy in the source,
 * or obtain one at http://www.gnu.org/licenses/lgpl-3.0-standalone.html */

import at.jku.ce.adaptivetesting.questions.XmlQuestionData;
import at.jku.ce.adaptivetesting.questions.accounting.AccountingDataStorage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GeogebraQuestionDataStorage") //todo: fix spelling of root element - needs to be fixed in all XML files
public class GeogebraQuestionXml extends
		XmlQuestionData<GeogebraDataStorage> {

	private static final long serialVersionUID = 3262285204313858210L;

	public GeogebraQuestionXml() {
	}

	public GeogebraQuestionXml(GeogebraDataStorage solution,
                               String questionText, float difficulty) {
		super(solution, questionText, difficulty);
	}

	@Override
	public Class<GeogebraDataStorage> getDataStorageClass() {
		return GeogebraDataStorage.class;
	}

}
