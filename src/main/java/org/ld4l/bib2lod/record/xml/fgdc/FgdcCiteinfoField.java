package org.ld4l.bib2lod.record.xml.fgdc;

import java.util.ArrayList;
import java.util.List;

import org.ld4l.bib2lod.records.Record.RecordException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Container field
 */
public class FgdcCiteinfoField extends FgdcField {

    private enum Field {
    		ORIGIN("origin"),
    		PUBDATE("pubdate"),
    		TITLE("title"),
    		EDITION("edition"),
    		PUBPLACE("pubplace"),
    		PUBLISH("publish"),
    		ONLINK("onlink");
        
        private final String tagName;
        
        private Field(String tagName) {
            this.tagName = tagName;
        }       
    }	
	
	private List<FgdcField> origins;
	private FgdcField pubdate;
	private FgdcField title;
	private FgdcField edition;
	private FgdcField pubplace;
	private FgdcField publish;
	private FgdcField onlink;

	public FgdcCiteinfoField(Element element) throws RecordException {
		super(element);
		origins = buildOrigins(element);
		pubdate = buildField(element, Field.PUBDATE);
		title = buildField(element, Field.TITLE);
		edition = buildField(element, Field.EDITION);
		pubplace = buildField(element, Field.PUBPLACE);
		publish = buildField(element, Field.PUBLISH);
		onlink = buildField(element, Field.ONLINK);
		isValid();
	}

	private void isValid() throws RecordFieldException {
		if (title == null) {
			throw new RecordFieldException("title is null");
		}
	}
	
	private List<FgdcField> buildOrigins(Element element) throws RecordException {
		List<FgdcField> origins = new ArrayList<FgdcField>();
		NodeList originsNodes =
				element.getElementsByTagName(Field.ORIGIN.tagName);
        for (int i = 0; i < originsNodes.getLength(); i++) {
        	origins.add(
                    new FgdcTextOnlyField((Element) originsNodes.item(i), Field.ORIGIN.tagName));
        }
		return origins;
	}
	
	private FgdcField buildField(Element element, Field field) throws RecordException {
		NodeList nodes = 
				element.getElementsByTagName(field.tagName);
        if (nodes.getLength() == 0) {
            return null;
        }

        // There should be only one title - ignore any others.
        return new FgdcTextOnlyField((Element)nodes.item(0), field.tagName);       
	}

	public List<FgdcField> getOrigins() {
		return origins;
	}

	public FgdcField getPubdate() {
		return pubdate;
	}

	public FgdcField getTitle() {
		return title;
	}

	public FgdcField getEdition() {
		return edition;
	}

	public FgdcField getPubplace() {
		return pubplace;
	}

	public FgdcField getPublish() {
		return publish;
	}

	public FgdcField getOnlink() {
		return onlink;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FgdcCiteinfoField [origins=");
		builder.append(origins);
		builder.append(", pubdate=");
		builder.append(pubdate);
		builder.append(", title=");
		builder.append(title);
		builder.append(", edition=");
		builder.append(edition);
		builder.append(", pubplace=");
		builder.append(pubplace);
		builder.append(", publish=");
		builder.append(publish);
		builder.append(", onlink=");
		builder.append(onlink);
		builder.append("]");
		return builder.toString();
	}
	
}
