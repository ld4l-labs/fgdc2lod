package org.ld4l.bib2lod.record.xml.fgdc;

import java.util.ArrayList;
import java.util.List;

import org.ld4l.bib2lod.records.Record.RecordException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Container field
 */
public class FgdcKeywordsField extends FgdcField {

    private enum Field {
    		THEME("theme"),
    		PLACE("place");
        
        private final String tagName;
        
        private Field(String tagName) {
            this.tagName = tagName;
        }       
    }	
	
	private List<FgdcThemeField> themes;
	private List<FgdcPlaceField> places;

	public FgdcKeywordsField(Element element) throws RecordException {
		super(element);
		themes = buildThemes(element);
		places = buildPlaces(element);
		isValid();
	}

	private void isValid() throws RecordFieldException {
	}
	
	private List<FgdcThemeField> buildThemes(Element element) throws RecordException {
		List<FgdcThemeField> themes = new ArrayList<FgdcThemeField>();
		NodeList themeNodes =
				element.getElementsByTagName(Field.THEME.tagName);
        for (int i = 0; i < themeNodes.getLength(); i++) {
        	themes.add(
                    new FgdcThemeField((Element) themeNodes.item(i)));
        }
		return themes;
	}
	
	private List<FgdcPlaceField> buildPlaces(Element element) throws RecordException {
		List<FgdcPlaceField> places = new ArrayList<FgdcPlaceField>();
		NodeList placeNodes =
				element.getElementsByTagName(Field.PLACE.tagName);
        for (int i = 0; i < placeNodes.getLength(); i++) {
        	places.add(
                    new FgdcPlaceField((Element) placeNodes.item(i)));
        }
		return places;
	}

	public List<FgdcThemeField> getThemes() {
		return themes;
	}

	public List<FgdcPlaceField> getPlaces() {
		return places;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FgdcKeywordsField [themes=");
		builder.append(themes);
		builder.append(", places=");
		builder.append(places);
		builder.append("]");
		return builder.toString();
	}
	
}
