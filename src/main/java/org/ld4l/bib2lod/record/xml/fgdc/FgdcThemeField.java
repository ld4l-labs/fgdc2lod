package org.ld4l.bib2lod.record.xml.fgdc;

import java.util.ArrayList;
import java.util.List;

import org.ld4l.bib2lod.records.Record.RecordException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Container field containing one 'key' and one or more 'values'.
 */
public class FgdcThemeField extends BaseFgdcField {

    private enum Field {
		THEME_KT("themekt"),   // The 'one'
    	THEME_KEY("themekey"); // The 'many'
        
        private final String tagName;
        
        private Field(String tagName) {
            this.tagName = tagName;
        }       
    }	
	
	private FgdcTextField themekt;
	private List<FgdcTextField> themekeys;

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
				element.getElementsByTagName(Field.THEME_KEY.tagName);
        for (int i = 0; i < themeKeyNodes.getLength(); i++) {
        	themeKeys.add(
                    new FgdcTextField((Element) themeKeyNodes.item(i), Field.THEME_KEY.tagName));
        }
		return themeKeys;
	}

	public FgdcTextField getThemeKt() {
		return themekt;
	}

	public List<FgdcTextField> getThemeKeys() {
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
