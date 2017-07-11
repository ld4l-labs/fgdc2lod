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

public class IsoTopicConcordanceManager {
	
	private enum ConcordanceCsvColumn {
		
		TOPIC_KEYWORD("topicKeyword"),
		URI("uri"),
		DOMAIN_CODE("domainCode"),
		LABEL("label"),
		DEFINITION("definition");
		
		private String columnName;
		
		private ConcordanceCsvColumn(String columnName) {
			this.columnName = columnName;
		}
		
		private String getColumnName() {
			return columnName;
		}
	}
	
	private final Map<String, IsoTopicConcordanceBean> map;
	
	private static final String CONCORANCE_FILE_NAME = "/ISO_19115_Topic_Keyword_to_URI_mapping.csv";

    /**
     * Constructor which loads default CSV file.
     */
	public IsoTopicConcordanceManager() throws URISyntaxException, IOException {
		this(CONCORANCE_FILE_NAME);
	}
	
	/**
	 * This constructor can be used for unit tests.
	 * 
	 * @param fileName - Name of CSV file in classpath to load.
	 */
	protected IsoTopicConcordanceManager(String fileName) throws URISyntaxException, IOException {
		this.map = new HashMap<>();
		
		HeaderColumnNameTranslateMappingStrategy<IsoTopicConcordanceBean> strat = new HeaderColumnNameTranslateMappingStrategy<IsoTopicConcordanceBean>() {
			
			/**
			 * Return the column name referring to the IsoTopicConcordanceBean attributes
			 * rather than column names in CSV file.
			 */
			@Override
			public String getColumnName(int col) {
				return col < ConcordanceCsvColumn.values().length ? ConcordanceCsvColumn.values()[col].getColumnName() : null;
			}
		};
		strat.setType(IsoTopicConcordanceBean.class);

	    CsvToBean<IsoTopicConcordanceBean> csv = new CsvToBean<>();
	    
	    InputStream is = getClass().getResourceAsStream(fileName);
	    if (is == null) {
	    	throw new FileNotFoundException("[" + fileName + "] cannot be found in classpath.");
	    }
	    
	    CSVReader reader = new CSVReader(new InputStreamReader(is));
	    List<IsoTopicConcordanceBean> list = csv.parse(strat, reader);
	    // populate local map of keyword name to Bean
	    for (IsoTopicConcordanceBean item : list) {
	    	map.put(item.getTopicKeyword(), item);
	    }
	    reader.close();
	}

	/**
	 * Map of keyword to IsoTopicConcordanceBean - for use in unit tests.
	 */
	protected Map<String, IsoTopicConcordanceBean> getMap() {
		return map;
	}
	
	/**
	 * Returns the entry for the topicKeyword; <code>null</code> if there is no entry for the given value;
	 * 
	 * @param topicKeyword - The keyword for which an entry is to be returned.
	 * @return - The corresponding entry if one exists; <code>null</code> otherwise.
	 */
	public IsoTopicConcordanceBean getConcordanceEntry(String topicKeyword) {
		return map.get(topicKeyword);
	}

}
