package org.ld4l.bib2lod.record.xml.fgdc;

import java.util.ArrayList;
import java.util.List;

import org.ld4l.bib2lod.records.Record.RecordException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Container field
 */
public class FgdcCiteinfoField extends BaseFgdcField {

    private enum Field {
    		ORIGIN("origin"),
    		PUBDATE("pubdate"),
    		TITLE("title"),
    		EDITION("edition"),
    		PUBPLACE("pubplace"),
    		PUBLISH("publish"),
    		ONLINK("onlink"),
    		GEOFORM("geoform");
        
        private final String tagName;
        
        private Field(String tagName) {
            this.tagName = tagName;
        }       
    }	
	
	private List<FgdcTextField> origins;
	private FgdcTextField pubdate;
	private FgdcTextField title;
	private FgdcTextField edition;
	private FgdcTextField pubplace;
	private FgdcTextField publish;
	private FgdcTextField onlink;
	private FgdcTextField geoform;

	public FgdcCiteinfoField(Element element) throws RecordException {
		super(element);
		origins = buildOrigins(element);
		pubdate = buildField(element, Field.PUBDATE);
		title = buildField(element, Field.TITLE);
		edition = buildField(element, Field.EDITION);
		pubplace = buildField(element, Field.PUBPLACE);
		publish = buildField(element, Field.PUBLISH);
		onlink = buildField(element, Field.ONLINK);
		geoform = buildField(element, Field.GEOFORM);
		isValid();
	}

	private void isValid() throws RecordFieldException {
		if (title == null) {
			throw new RecordFieldException("title is null");
		}
	}
	
	private List<FgdcTextField> buildOrigins(Element element) throws RecordException {
		List<FgdcTextField> origins = new ArrayList<FgdcTextField>();
		NodeList originsNodes =
				element.getElementsByTagName(Field.ORIGIN.tagName);
        for (int i = 0; i < originsNodes.getLength(); i++) {
        	origins.add(
                    new FgdcTextField((Element) originsNodes.item(i), Field.ORIGIN.tagName));
        }
		return origins;
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

	public List<FgdcTextField> getOrigins() {
		return origins;
	}

	public FgdcTextField getPubdate() {
		return pubdate;
	}

	public FgdcTextField getTitle() {
		return title;
	}

	public FgdcTextField getEdition() {
		return edition;
	}

	public FgdcTextField getPubplace() {
		return pubplace;
	}

	public FgdcTextField getPublish() {
		return publish;
	}

	public FgdcTextField getOnlink() {
		return onlink;
	}

	public FgdcTextField getGeoform() {
		return geoform;
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
		builder.append(", geoform=");
		builder.append(geoform);
		builder.append("]");
		return builder.toString();
	}
	
}
