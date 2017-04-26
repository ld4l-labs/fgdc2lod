/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Represents the bounding in a FGDC record.
 * TODO: Eventually create subclasses for WKT, Source Type and Projection.
 */
public class FgdcGeometry extends FgdcField {
	
    private enum Field {
        WEST_BC("westbc"),
        EAST_BC("eastbc"),
        NORTH_BC("northbc"),
        SOUTH_BC("southbc");
        
        private final String tagName;
        
        private Field(String tagName) {
            this.tagName = tagName;
        }       
    }

	
	private Element geometryElement;
	private String westbc;
	private String eastbc;
	private String northbc;
	private String southbc;

	/**
	 * @param element
	 */
	public FgdcGeometry(Element element) {
		super(element);
		this.geometryElement = element;
		if (this.geometryElement != null) {
			NodeList nodes = element.getElementsByTagName(Field.WEST_BC.tagName);
	        if (nodes.getLength() > 0) {
	        	// There should be only one - ignore any others.
	        	this.westbc = nodes.item(0).getTextContent();
	        }
			nodes = element.getElementsByTagName(Field.EAST_BC.tagName);
	        if (nodes.getLength() > 0) {
	        	// There should be only one - ignore any others.
	        	this.eastbc = nodes.item(0).getTextContent();
	        }
			nodes = element.getElementsByTagName(Field.NORTH_BC.tagName);
	        if (nodes.getLength() > 0) {
	        	// There should be only one - ignore any others.
	        	this.northbc = nodes.item(0).getTextContent();
	        }
			nodes = element.getElementsByTagName(Field.SOUTH_BC.tagName);
	        if (nodes.getLength() > 0) {
	        	// There should be only one - ignore any others.
	        	this.southbc = nodes.item(0).getTextContent();
	        }
		}
		
	}
	
	/**
	 * Build the Well Known Text from the 4 coordinates
	 * @return The Well Known Text coordinates
	 */
	public String getWKT() {
    	StringBuilder wkt = new StringBuilder("POLYGON((");
    	wkt.append(this.westbc);
    	wkt.append(' ');
    	wkt.append(this.northbc);
    	wkt.append(", ");
    	wkt.append(this.eastbc);
    	wkt.append(' ');
    	wkt.append(this.northbc);
    	wkt.append(", ");
    	wkt.append(this.eastbc);
    	wkt.append(' ');
    	wkt.append(this.southbc);
    	wkt.append(", ");
    	wkt.append(this.westbc);
    	wkt.append(' ');
    	wkt.append(this.southbc);
    	wkt.append(", ");
    	wkt.append(this.westbc);
    	wkt.append(' ');
    	wkt.append(this.northbc);
    	wkt.append("))");
    	wkt.append("^^geo:wktLiteral");
    	return wkt.toString();
	}

	/* (non-Javadoc)
	 * @see org.ld4l.bib2lod.record.RecordField#isValid()
	 */
	@Override
	public boolean isValid() {
		if (this.geometryElement == null) {
			return false;
		}
		if (this.westbc == null || this.eastbc == null || this.northbc == null || this.southbc == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FgdcGeometry [geometryElement=");
		builder.append(geometryElement);
		builder.append(", westbc=");
		builder.append(westbc);
		builder.append(", eastbc=");
		builder.append(eastbc);
		builder.append(", northbc=");
		builder.append(northbc);
		builder.append(", southbc=");
		builder.append(southbc);
		builder.append("]");
		return builder.toString();
	}
	
}
