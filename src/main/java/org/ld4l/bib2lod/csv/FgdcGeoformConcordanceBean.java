/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv;

/**
 * This bean represents one row in the concordance FGDC_Geospatial_Data_Presentation_Form_to_Cartotek-o_mapping.csv file.
 */
public class FgdcGeoformConcordanceBean {
	
	private String geoformText;
	private String mappingEquivalent;
	private String label;
	
	public String getGeoformText() {
		return geoformText;
	}
	
	public void setGeoformText(String geoformText) {
		this.geoformText = geoformText;
	}
	
	public String getMappingEquivalent() {
		return mappingEquivalent;
	}
	
	/**
	 * Split this attribute into tokens.
	 * @return An array of tokens split by ',' and whitespace characters.
	 */
	public String[] getMappingEquivalentArray() {
		return (mappingEquivalent == null ? new String[0] : mappingEquivalent.split(",\\s"));
	}
	
	public void setMappingEquivalent(String mappingEquivalent) {
		this.mappingEquivalent = mappingEquivalent;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FgdcGeoformConcordanceBean [geoformText=");
		builder.append(geoformText);
		builder.append(", mappingEquivalent=");
		builder.append(mappingEquivalent);
		builder.append(", label=");
		builder.append(label);
		builder.append("]");
		return builder.toString();
	}

}
