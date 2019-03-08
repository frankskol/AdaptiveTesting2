package at.jku.ce.adaptivetesting.questions.geogebra;

/*This file is part of the project "Reisisoft Adaptive Testing",
 * which is licenced under LGPL v3+. You may find a copy in the source,
 * or obtain one at http://www.gnu.org/licenses/lgpl-3.0-standalone.html */

import at.jku.ce.adaptivetesting.questions.XmlQuestionData;
import at.jku.ce.adaptivetesting.questions.accounting.AccountingDataStorage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "accountingtQuestionDataStorage") //todo: fix spelling of root element - needs to be fixed in all XML files
public class GeogebraQuestionXml extends
		XmlQuestionData<AccountingDataStorage> {

	private static final long serialVersionUID = 3262285204313858210L;

	public GeogebraQuestionXml() {
	}

	public GeogebraQuestionXml(AccountingDataStorage solution,
                               String questionText, float difficulty) {
		super(solution, questionText, difficulty);
	}

	@Override
	public Class<AccountingDataStorage> getDataStorageClass() {
		return AccountingDataStorage.class;
	}

}
