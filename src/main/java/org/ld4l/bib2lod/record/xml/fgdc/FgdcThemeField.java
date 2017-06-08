package org.ld4l.bib2lod.record.xml.fgdc;

import java.util.ArrayList;
import java.util.List;

import org.ld4l.bib2lod.records.Record.RecordException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Container field containing one 'key' and one or more 'values'.
 */
public class FgdcThemeField extends FgdcField {

    private enum Field {
		THEME_KT("themekt"),   // The 'one'
    	THEME_KEY("themekey"); // The 'many'
        
        private final String tagName;
        
        private Field(String tagName) {
            this.tagName = tagName;
        }       
    }	
	
	private FgdcField themekt;
	private List<FgdcField> themekeys;

	public FgdcThemeField(Element element) throws RecordException {
		super(element);
		themekt = buildField(element, Field.THEME_KT);
		themekeys = buildThemeKeys(element);
		isValid();
	}

	private void isValid() throws RecordFieldException {
		if (themekt == null) {
			throw new RecordFieldException("themekt is null");
		}
		if (themekeys.isEmpty()) {
			throw new RecordFieldException("themekeys is empty");
		}
	}
	
	private FgdcField buildField(Element element, Field field) throws RecordException {
		NodeList nodes = 
				element.getElementsByTagName(field.tagName);
        if (nodes.getLength() == 0) {
            return null;
        }

        // There should only be one - ignore any others.
        return new FgdcTextOnlyField((Element)nodes.item(0), field.tagName);       
	}
	
	private List<FgdcField> buildThemeKeys(Element element) throws RecordException {
		List<FgdcField> themeKeys = new ArrayList<FgdcField>();
		NodeList themeKeyNodes =
				element.getElementsByTagName(Field.THEME_KEY.tagName);
        for (int i = 0; i < themeKeyNodes.getLength(); i++) {
        	themeKeys.add(
                    new FgdcTextOnlyField((Element) themeKeyNodes.item(i), Field.THEME_KEY.tagName));
        }
		return themeKeys;
	}

	public FgdcField getThemeKt() {
		return themekt;
	}

	public List<FgdcField> getThemeKeys() {
		return themekeys;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FgdcThemeField [themekt=");
		builder.append(themekt);
		builder.append(", themekeys=");
		builder.append(themekeys);
		builder.append("]");
		return builder.toString();
	}

}
