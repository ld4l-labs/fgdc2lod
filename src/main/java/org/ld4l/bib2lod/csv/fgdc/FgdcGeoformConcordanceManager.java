/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv.fgdc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class FgdcGeoformConcordanceManager {
	
	private enum ConcordanceCsvColumn {
		
		GEOFORM_TEXT("geoformText"),
		MAPPING_EQUIVALENT("mappingEquivalent"),
		LABEL("label");
		
		private String columnName;
		
		private ConcordanceCsvColumn(String columnName) {
			this.columnName = columnName;
		}
		
		private String getColumnName() {
			return columnName;
		}
	}
	
	private final Map<String, FgdcGeoformConcordanceBean> map;
	
	private static final String CONCORANCE_FILE_NAME = "/FGDC_Geospatial_Data_Presentation_Form_to_Cartotek-o_mapping.csv";

    /**
     * Constructor which loads default CSV file.
     * 
	 * @throws FileNotFoundException - If file not found on classpath.
     */
	public FgdcGeoformConcordanceManager() throws URISyntaxException, IOException {
		this(CONCORANCE_FILE_NAME);
	}
	
	/**
	 * This constructor can be used for unit tests.
	 * 
	 * @param fileName - Name of CSV file in classpath to load.
	 * @throws URISyntaxException 
	 * @throws FileNotFoundException - If file not found on classpath.
	 */
	protected FgdcGeoformConcordanceManager(String fileName) throws URISyntaxException, IOException {
		this.map = new HashMap<>();
		
		HeaderColumnNameTranslateMappingStrategy<FgdcGeoformConcordanceBean> strat = new HeaderColumnNameTranslateMappingStrategy<FgdcGeoformConcordanceBean>() {
			
			/**
			 * Return the column name referring to the FgdcGeoformConcordanceBean attributes
			 * rather than column names in CSV file.
			 */
			@Override
			public String getColumnName(int col) {
				return col < ConcordanceCsvColumn.values().length ? ConcordanceCsvColumn.values()[col].getColumnName() : null;
			}
		};
		strat.setType(FgdcGeoformConcordanceBean.class);

	    CsvToBean<FgdcGeoformConcordanceBean> csv = new CsvToBean<>();
	    InputStream is = getClass().getResourceAsStream(fileName);
	    if (is == null) {
	    	throw new FileNotFoundException("[" + fileName + "] cannot be found in classpath.");
	    }
	    CSVReader reader = new CSVReader(new InputStreamReader(is));
	    List<FgdcGeoformConcordanceBean> list = csv.parse(strat, reader);
	    // populate local map of keyword name to Bean
	    for (FgdcGeoformConcordanceBean item : list) {
// TODO: what gets mapped???
	    	map.put(item.getGeoformText(), item);
	    }
	    reader.close();
	}

	/**
	 * Map of keyword to FgdcGeoformConcordanceBean - for use in unit tests.
	 */
	protected Map<String, FgdcGeoformConcordanceBean> getMap() {
		return map;
	}
	
	/**
	 * Returns the entry for the topicKeyword; <code>null</code> if there is no entry for the given value;
	 * 
	 * @param topicKeyword - The keyword for which an entry is to be returned.
	 * @return - The corresponding entry if one exists; <code>null</code> otherwise.
	 */
	public FgdcGeoformConcordanceBean getConcordanceEntry(String topicKeyword) {
		return map.get(topicKeyword);
	}

}
