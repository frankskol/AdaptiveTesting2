package at.jku.ce.adaptivetesting.questions.geogebra.util;

/*This file is part of the project "Reisisoft Adaptive Testing",
 * which is licenced under LGPL v3+. You may find a copy in the source,
 * or obtain one at http://www.gnu.org/licenses/lgpl-3.0-standalone.html */


import at.jku.ce.adaptivetesting.questions.geogebra.GeogebraQuestionXml;
import at.jku.ce.adaptivetesting.questions.geogebra.GeogebraQuestion;

public final class GeogebraXmlHelper {
	public static GeogebraQuestion fromXml(GeogebraQuestionXml xml, String id) {
		return new GeogebraQuestion(xml.getDataStorage(), xml.getDifficulty(),
				xml.getQuestion().replace("\\n", " <br />"), id);

	}
}
