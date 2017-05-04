/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.records.xml.BaseXmlRecord;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Represents a FGDC XML record.
 */
public class FgdcRecord extends BaseXmlRecord {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    private enum Field {
        TITLE("title"),
        GEOMETRY("bounding");
        
        private final String tagName;
        
        private Field(String tagName) {
            this.tagName = tagName;
        }       
    }
    
    private FgdcTitle title;
    private FgdcGeometry geometry;
    private String layerId;

	/**
	 * Constructor
	 * 
	 * @param element - The top-most FGDC XML element.
	 */
	public FgdcRecord(Element element) {
		super(element);
		
		this.title = buildTitle(element);
		this.geometry = buildGeometry(element);
		this.layerId = buildLayerId(element);
	}
	
    /*
     * Builds this Record's title from the FGDC input. Only a single title
     * is allowed; others are ignored. Returns null if no title node is found.
     */
	private FgdcTitle buildTitle(Element element) {
        NodeList titleNodes = element.getElementsByTagName(Field.TITLE.tagName);
        if (titleNodes.getLength() == 0) {
            return null;
        }

        // There should be only one title - ignore any others.
        return new FgdcTitle((Element) titleNodes.item(0));       
    }

	/*
	 * Builds this Record's geography from the FGDC input.
	 */
	private FgdcGeometry buildGeometry(Element element) {
		NodeList boundingNodes = element.getElementsByTagName(Field.GEOMETRY.tagName);
		if (boundingNodes.getLength() == 0) {
			return null;
		}
		
		// There should be only one bounding - ignore any others.
		FgdcGeometry fdgcGeometry = new FgdcGeometry((Element)boundingNodes.item(0));
		return fdgcGeometry;
	}
	
	private String buildLayerId(Element element) {
		return element.getAttribute("layerid");
	}
	
	/* (non-Javadoc)
	 * @see org.ld4l.bib2lod.record.Record#isValid()
	 */
	@Override
	public boolean isValid() {

		if (!hasTitle()) {
			return false;
		}
		
		if (!hasGeometry()) {
			return false;
		}
		
		return true;
	}
    
    private boolean hasTitle() {
        return this.title != null;
    }
    
    public FgdcTitle getTitle() {
    	return this.title;
    }
    
    private boolean hasGeometry() {
    	return this.geometry != null;
    }
    
    public FgdcGeometry getGeometry() {
    	return this.geometry;
    }
    
    public String getLayerId() {
    	return this.layerId;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FgdcRecord [title=");
		builder.append(title);
		builder.append(", geometry=");
		builder.append(geometry);
		builder.append(", layerId=");
		builder.append(layerId);
		builder.append("]");
		return builder.toString();
	}

}
