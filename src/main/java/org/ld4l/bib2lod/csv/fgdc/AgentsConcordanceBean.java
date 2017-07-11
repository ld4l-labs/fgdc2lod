/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv.fgdc;

/**
 * This bean represents one row in the concordance Agents_concordance_v1.csv file.
 */
public class AgentsConcordanceBean {
	
	private String matchingText;
	private String uri;
	private String label;
	
	public String getMatchingText() {
		return matchingText;
	}
	
	public void setMatchingText(String matchingText) {
		this.matchingText = matchingText;
	}
	
	public String getUri() {
		return uri;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [matchingText=");
		builder.append(matchingText);
		builder.append(", uri=");
		builder.append(uri);
		builder.append(", label=");
		builder.append(label);
		builder.append("]");
		return builder.toString();
	}

}
