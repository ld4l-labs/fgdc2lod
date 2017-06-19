/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv;

/**
 * This bean represents one row in the concordance ISO_19115_Topic_Keyword_to_URI_mapping.csv file.
 */
public class IsoTopicConcordanceBean {
	
	private String topicKeyword;
	private String uri;
	private String domainCode;
	private String label;
	private String definition;

	public String getTopicKeyword() {
		return topicKeyword;
	}

	public void setTopicKeyword(String topicKeyword) {
		this.topicKeyword = topicKeyword;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IsoTopicConcordanceBean [topicKeyword=");
		builder.append(topicKeyword);
		builder.append(", uri=");
		builder.append(uri);
		builder.append(", domainCode=");
		builder.append(domainCode);
		builder.append(", label=");
		builder.append(label);
		builder.append(", definition=");
		builder.append(definition);
		builder.append("]");
		return builder.toString();
	}

}
