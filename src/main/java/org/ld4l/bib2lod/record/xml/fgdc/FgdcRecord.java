/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import java.util.ArrayList;
import java.util.List;

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
        GEOMETRY("bounding"),
        CITEINFO("citeinfo"),
        ABSTRACT("abstract"),
        PURPOSE("purpose");
        
        private final String tagName;
        
        private Field(String tagName) {
            this.tagName = tagName;
        }       
    }
    
    private FgdcHglLayerId layerId;
    private FgdcTitle title;
    private FgdcGeometry geometry;
    private FgdcOriginatorActivity fgdcOriginatorActivity;
    private FgdcPublisherActivity fgdcPublisherActivity;
    private List<FgdcAnnotation> fgdcAnnotations;

	/**
	 * Constructor
	 * 
	 * @param element - The top-most FGDC XML element.
	 */
	public FgdcRecord(Element element) {
		super(element);
		
		this.layerId = buildLayerId(element);
		this.title = buildTitle(element);
		this.geometry = buildGeometry(element);
		this.fgdcOriginatorActivity = buildFgdcOriginator(element);
		this.fgdcPublisherActivity = buildFgdcPublisher(element);
		this.fgdcAnnotations = buildFgdcAnnotations(element);
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
	
	private FgdcHglLayerId buildLayerId(Element element) {
		return new FgdcHglLayerId(element);
	}
	
	private FgdcOriginatorActivity buildFgdcOriginator(Element element) {
        NodeList citeinfoNodes = element.getElementsByTagName(Field.CITEINFO.tagName);
        if (citeinfoNodes.getLength() == 0) {
            return null;
        }
        
        // should be only one node, ignore others
        return new FgdcOriginatorActivity((Element) citeinfoNodes.item(0));
	}
	
	private FgdcPublisherActivity buildFgdcPublisher(Element element) {
        NodeList citeinfoNodes = element.getElementsByTagName(Field.CITEINFO.tagName);
        if (citeinfoNodes.getLength() == 0) {
            return null;
        }
        
        // should be only one node, ignore others
        return new FgdcPublisherActivity((Element) citeinfoNodes.item(0));
	}
	
	private List<FgdcAnnotation> buildFgdcAnnotations(Element element) {
		List<FgdcAnnotation> annots = new ArrayList<>();
		NodeList abstractNodes = element.getElementsByTagName(Field.ABSTRACT.tagName);
		if (abstractNodes.getLength() > 0) {
			// should only be one
			FgdcAnnotation abstractAnnot =
					new FgdcAnnotation((Element)abstractNodes.item(0),
							FgdcAnnotation.AnnotationType.SUMMARIZING);
			annots.add(abstractAnnot);
		}
		NodeList purposeNodes = element.getElementsByTagName(Field.PURPOSE.tagName);
		if (purposeNodes.getLength() > 0) {
			// should only be one
			FgdcAnnotation purposeAnnot =
					new FgdcAnnotation((Element)purposeNodes.item(0),
							FgdcAnnotation.AnnotationType.PROVIDING_PURPOSE);
			annots.add(purposeAnnot);
		}
		
		return annots;
	}
	
	/* (non-Javadoc)
	 * @see org.ld4l.bib2lod.record.Record#isValid()
	 */
	@Override
	public boolean isValid() {

		if (this.layerId == null || !this.layerId.isValid()) {
			return false;
		}
		
		return true;
	}
    
    public FgdcTitle getTitle() {
    	return this.title;
    }
    
    public FgdcGeometry getGeometry() {
    	return this.geometry;
    }
    
    public FgdcHglLayerId getLayerId() {
    	return this.layerId;
    }
    
    public FgdcOriginatorActivity getFgdcOriginatorActivity() {
    	return this.fgdcOriginatorActivity;
    }
    
    public FgdcPublisherActivity getFgdcPublisherActivity() {
    	return this.fgdcPublisherActivity;
    }
    
    public List<FgdcAnnotation> getFgdcAnnotations() {
    	return this.fgdcAnnotations;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FgdcRecord [layerId=");
		builder.append(layerId);
		builder.append(", title=");
		builder.append(title);
		builder.append(", geometry=");
		builder.append(geometry);
		builder.append(", fgdcOriginatorActivity=");
		builder.append(fgdcOriginatorActivity);
		builder.append(", fgdcPublisherActivity=");
		builder.append(fgdcPublisherActivity);
		builder.append(", fgdcAnnotations=");
		builder.append(fgdcAnnotations);
		builder.append("]");
		return builder.toString();
	}

}
