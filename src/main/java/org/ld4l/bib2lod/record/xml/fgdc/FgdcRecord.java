/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.BaseXmlRecord;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Represents a FGDC XML record.
 */
public class FgdcRecord extends BaseXmlRecord {
    
    private static final String LAYER_ID_ATTRIBUTE_NAME = "layerid";
    private static final String HOLLIS_NUMBER_ATTRIBUTE_NAME = "hollisno";
    
    private enum Field {
    		CITEINFO("citeinfo"),
    		ABSTRACT("abstract"),
    		PURPOSE("purpose"),
    		BOUNDING("bounding"),
    		KEYWORDS("keywords");
        
        private final String tagName;
        
        private Field(String tagName) {
            this.tagName = tagName;
        }       
    }
    
    private String layerId;
    private String hollisNumber;
    private FgdcCiteinfoField citeinfoField;
    private BaseFgdcField abstractField;
    private BaseFgdcField purposeField;
    private FgdcBoundingField boundingField;
    private FgdcKeywordsField keywordsField;

	/**
	 * Constructor
	 * 
	 * @param record - The top-most FGDC XML element.
	 */
	public FgdcRecord(Element record) throws RecordException {
		super(record);
		
		citeinfoField = buildFgdcCiteinfo(record);
		abstractField = buildField(record, Field.ABSTRACT);
		purposeField = buildField(record, Field.PURPOSE);
		boundingField = buildFgdcBoundingField(record);
		keywordsField = buildFgdcKeywordsField(record);
		layerId = record.getAttribute(LAYER_ID_ATTRIBUTE_NAME);
		hollisNumber = record.getAttribute(HOLLIS_NUMBER_ATTRIBUTE_NAME);
		isValid();
	}
	
	private final FgdcCiteinfoField buildFgdcCiteinfo(Element record) throws RecordException {
        NodeList citeinfoNodes = 
                record.getElementsByTagName(Field.CITEINFO.tagName);
        if (citeinfoNodes.getLength() == 0) {
            return null;
        }

        // There should be only one - ignore any others.
        return new FgdcCiteinfoField((Element)citeinfoNodes.item(0));       
		
	}
	
	private final FgdcBoundingField buildFgdcBoundingField(Element record) throws RecordException {
        NodeList boundingNodes = 
                record.getElementsByTagName(Field.BOUNDING.tagName);
        if (boundingNodes.getLength() == 0) {
            return null;
        }

        // There should be only one - ignore any others.
        return new FgdcBoundingField((Element)boundingNodes.item(0));       
		
	}
	
	private FgdcKeywordsField buildFgdcKeywordsField(Element record) throws RecordException {
		NodeList nodes = 
				record.getElementsByTagName(Field.KEYWORDS.tagName);
		if (nodes.getLength() == 0) {
			return null;
		}
		
		// There should be only one - ignore any others.
		return new FgdcKeywordsField((Element)nodes.item(0));       
	}
	
	private BaseFgdcField buildField(Element record, Field field) throws RecordException {
		NodeList nodes = 
				record.getElementsByTagName(field.tagName);
        if (nodes.getLength() == 0) {
            return null;
        }

        // There should be only one - ignore any others.
        return new FgdcTextField((Element)nodes.item(0), field.tagName);       
	}
	
	private void isValid() throws RecordFieldException {
        if (layerId == null) {
            throw new RecordFieldException("layerId attribute is null");
        }
        if (layerId.isEmpty()) {
            throw new RecordFieldException("layerId attribute is empty");
        }
	}
	
	public String getLayerId() {
		return layerId;
	}
	
	public String getHollisNumber() {
		return hollisNumber;
	}

	public FgdcCiteinfoField getCiteinfoField() {
		return citeinfoField;
	}

	public BaseFgdcField getAbstractField() {
		return abstractField;
	}

	public BaseFgdcField getPurposeField() {
		return purposeField;
	}

	public FgdcBoundingField getBoundingField() {
		return boundingField;
	}
	
	public FgdcKeywordsField getKeywordsField() {
		return keywordsField;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FgdcRecord [layerId=");
		builder.append(layerId);
		builder.append(", hollisNumber=");
		builder.append(hollisNumber);
		builder.append(", citeinfoField=");
		builder.append(citeinfoField);
		builder.append(", abstractField=");
		builder.append(abstractField);
		builder.append(", purposeField=");
		builder.append(purposeField);
		builder.append(", boundingField=");
		builder.append(boundingField);
		builder.append(", keywordsField=");
		builder.append(keywordsField);
		builder.append("]");
		return builder.toString();
	}

}
