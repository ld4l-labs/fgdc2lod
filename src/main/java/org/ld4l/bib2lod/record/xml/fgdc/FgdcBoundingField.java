package org.ld4l.bib2lod.record.xml.fgdc;

import org.ld4l.bib2lod.records.Record.RecordException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Container bounding field for coordinates
 */
public class FgdcBoundingField extends BaseFgdcField {
    
    private enum Field {
    		WEST("westbc"),
    		EAST("eastbc"),
    		NORTH("northbc"),
    		SOUTH("southbc");
        
        private final String tagName;
        
        private Field(String tagName) {
            this.tagName = tagName;
        }       
    }	
	
	private FgdcTextField west;
	private FgdcTextField east;
	private FgdcTextField north;
	private FgdcTextField south;

	public FgdcBoundingField(Element element) throws RecordException {
		super(element);
		west = buildField(element, Field.WEST);
		east = buildField(element, Field.EAST);
		north = buildField(element, Field.SOUTH);
		south = buildField(element, Field.NORTH);
		isValid();
	}

	private void isValid() throws RecordFieldException {
		if (west == null) {
			throw new RecordFieldException("west bounding is null");
		}
		if (east == null) {
			throw new RecordFieldException("east bounding is null");
		}
		if (north == null) {
			throw new RecordFieldException("north bounding is null");
		}
		if (south == null) {
			throw new RecordFieldException("south bounding is null");
		}
	}
	
	private FgdcTextField buildField(Element element, Field field) throws RecordException {
		NodeList nodes = 
				element.getElementsByTagName(field.tagName);
        if (nodes.getLength() == 0) {
            return null;
        }

        // There should be only one title - ignore any others.
        return new FgdcTextField((Element)nodes.item(0), field.tagName);       
	}

	public BaseFgdcField getWestBounding() {
		return west;
	}

	public BaseFgdcField getEastBounding() {
		return east;
	}
	
	public BaseFgdcField getNorthBounding() {
		return north;
	}

	public BaseFgdcField getSouthBounding() {
		return south;
	}
	
	/**
	 * Build the Well Known Text from the 4 coordinates
	 * @return The Well Known Text coordinates
	 */
	public String getWKT() {
    	StringBuilder wkt = new StringBuilder("POLYGON((");
    	wkt.append(west.getTextValue());
    	wkt.append(' ');
    	wkt.append(north.getTextValue());
    	wkt.append(", ");
    	wkt.append(east.getTextValue());
    	wkt.append(' ');
    	wkt.append(north.getTextValue());
    	wkt.append(", ");
    	wkt.append(east.getTextValue());
    	wkt.append(' ');
    	wkt.append(south.getTextValue());
    	wkt.append(", ");
    	wkt.append(west.getTextValue());
    	wkt.append(' ');
    	wkt.append(south.getTextValue());
    	wkt.append(", ");
    	wkt.append(west.getTextValue());
    	wkt.append(' ');
    	wkt.append(north.getTextValue());
    	wkt.append("))");
    	return wkt.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FgdcBoundingField [west=");
		builder.append(west);
		builder.append(", east=");
		builder.append(east);
		builder.append(", north=");
		builder.append(north);
		builder.append(", south=");
		builder.append(south);
		builder.append("]");
		return builder.toString();
	}
	
}
