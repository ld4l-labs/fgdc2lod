/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Represents the publisher activity in a FGDC record.
 */
public class FgdcPublisherActivity extends FgdcField {
	
    private enum Field {
        AGENT("publish"),
        LOCATION("pubplace"),
        DATE("pubdate");
        
        private final String tagName;
        
        private Field(String tagName) {
            this.tagName = tagName;
        }       
    }
	
	private Element citeinfoElement;
	private FgdcAgent agent;
	private String location;
	private String date;
	
	/**
	 * @param element
	 */
	public FgdcPublisherActivity(Element element) {
		super(element);
		this.citeinfoElement = element;

		if (this.citeinfoElement != null) {
			NodeList publishNodes = this.citeinfoElement.getElementsByTagName(Field.AGENT.tagName);
			if (publishNodes != null && publishNodes.getLength() > 0) {
				// should only be one agent
				this.agent = new FgdcAgent((Element)publishNodes.item(0));
			}
			NodeList locationNodes = this.citeinfoElement.getElementsByTagName(Field.LOCATION.tagName);
			if (locationNodes != null && locationNodes.getLength() > 0) {
				// should only be one location
				this.location = locationNodes.item(0).getTextContent();
			}
			NodeList dateNodes = this.citeinfoElement.getElementsByTagName(Field.DATE.tagName);
			if (dateNodes != null && dateNodes.getLength() > 0) {
				// should only be one agent
				this.date = dateNodes.item(0).getTextContent();
			}
		}
	}

	public FgdcAgent getAgent() {
		return agent;
	}

	public String getLocation() {
		return location;
	}

	public String getDate() {
		return date;
	}

	/* (non-Javadoc)
	 * @see org.ld4l.bib2lod.record.RecordField#isValid()
	 */
	@Override
	public boolean isValid() {
		if (this.citeinfoElement == null) {
			return false;
		}
		
		if (this.agent == null && this.location == null && this.date == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FgdcPublisherActivity [citeinfoElement=");
		builder.append(citeinfoElement);
		builder.append(", agent=");
		builder.append(agent);
		builder.append(", location=");
		builder.append(location);
		builder.append(", date=");
		builder.append(date);
		builder.append("]");
		return builder.toString();
	}
	
}
