/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv.fgdc;

/**
 * This bean represents one row in the concordance ISO_19115_Topic_Keyword_to_URI_mapping.csv file.
 */
public class PlaceKeyConcordanceBean {
	
	private String layerId;
	private String title;
	private String source;
	private String uri;
	private String label;

	public String getLayerId() {
		return layerId;
	}

	public void setLayerId(String layerId) {
		this.layerId = layerId;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IsoTopicConcordanceBean [layerId=");
		builder.append(layerId);
		builder.append(", uri=");
		builder.append(uri);
		builder.append(", title=");
		builder.append(title);
		builder.append(", label=");
		builder.append(label);
		builder.append(", source=");
		builder.append(source);
		builder.append("]");
		return builder.toString();
	}

}
