/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Represents the originator activity in a FGDC record.
 */
public class FgdcOriginatorActivity extends FgdcField {
	
    private enum Field {
        ORIGIN("origin");
        
        private final String tagName;
        
        private Field(String tagName) {
            this.tagName = tagName;
        }       
    }
	
	private Element citeinfoElement;
	private List<FgdcAgent> origins;

	/**
	 * @param element
	 */
	public FgdcOriginatorActivity(Element element) {
		super(element);
		this.citeinfoElement = element;
		this.origins = new ArrayList<>();
		if (this.citeinfoElement != null) {
			NodeList originNodes = this.citeinfoElement.getElementsByTagName(Field.ORIGIN.tagName);
			if (originNodes != null && originNodes.getLength() > 0) {
				for (int i = 0; i < originNodes.getLength(); i++) {
					Node node = originNodes.item(i);
					this.origins.add(new FgdcAgent((Element)node));
				}
			}
		}
	}
	
	public List<FgdcAgent> getOrigins() {
		return this.origins;
	}

	/* (non-Javadoc)
	 * @see org.ld4l.bib2lod.record.RecordField#isValid()
	 */
	@Override
	public boolean isValid() {
		if (this.citeinfoElement == null) {
			return false;
		}
		if (this.origins.isEmpty()) {
			return false;
		}
		
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FgdcOriginatorActivity [origins=");
		builder.append(origins);
		builder.append("]");
		return builder.toString();
	}
	
}
