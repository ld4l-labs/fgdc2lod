package org.ld4l.bib2lod.record.xml.fgdc;

import java.util.ArrayList;
import java.util.List;

import org.ld4l.bib2lod.records.Record.RecordException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Container field containing one 'key' and one or more 'values'.
 */
public class FgdcPlaceField extends BaseFgdcField {

    private enum Field {
		PLACE_KT("placekt"),   // The 'one'
    	PLACE_KEY("placekey"); // The 'many'
        
        private final String tagName;
        
        private Field(String tagName) {
            this.tagName = tagName;
        }       
    }	
	
	private FgdcTextField placekt;
	private List<FgdcTextField> placekeys;

	public FgdcPlaceField(Element element) throws RecordException {
		super(element);
		placekt = buildField(element, Field.PLACE_KT);
		placekeys = buildThemeKeys(element);
		isValid();
	}

	private void isValid() throws RecordFieldException {
		if (placekt == null) {
			throw new RecordFieldException("placekt is null");
		}
		if (placekeys.isEmpty()) {
			throw new RecordFieldException("placekeys is empty");
		}
	}
	
	private FgdcTextField buildField(Element element, Field field) throws RecordException {
		NodeList nodes = 
				element.getElementsByTagName(field.tagName);
        if (nodes.getLength() == 0) {
            return null;
        }

        // There should only be one - ignore any others.
        return new FgdcTextField((Element)nodes.item(0), field.tagName);       
	}
	
	private List<FgdcTextField> buildThemeKeys(Element element) throws RecordException {
		List<FgdcTextField> themeKeys = new ArrayList<FgdcTextField>();
		NodeList themeKeyNodes =
				element.getElementsByTagName(Field.PLACE_KEY.tagName);
        for (int i = 0; i < themeKeyNodes.getLength(); i++) {
        	themeKeys.add(
                    new FgdcTextField((Element) themeKeyNodes.item(i), Field.PLACE_KEY.tagName));
        }
		return themeKeys;
	}

	public FgdcTextField getPlaceKt() {
		return placekt;
	}

	public List<FgdcTextField> getPlaceKeys() {
		return placekeys;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FgdcPlaceField [placekt=");
		builder.append(placekt);
		builder.append(", placekeys=");
		builder.append(placekeys);
		builder.append("]");
		return builder.toString();
	}

}
