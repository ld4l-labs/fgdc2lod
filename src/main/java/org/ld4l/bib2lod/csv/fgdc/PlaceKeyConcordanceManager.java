/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv.fgdc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.util.collections.MapOfLists;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

/**
 * Special version of a concordance manager that does not extend AbstractConcordanceManager since there
 * can be multiple values for a given key.
 * 
 * @author dan179
 */
public class PlaceKeyConcordanceManager {
	
	private enum ConcordanceCsvColumn {

		LAYER_ID("layerId"),
		TITLE("title"),
		SOURCE("source"),
		URI("uri"),
		LABEL("label");
		
		private String columnName;
		
		private ConcordanceCsvColumn(String columnName) {
			this.columnName = columnName;
		}
		
		public String toString() {
			return columnName;
		}
	}
	
	
	// map of index to JavaBean representing a row in CSV file
	private MapOfLists<String, PlaceKeyConcordanceBean> map;

	// encapsulates CSV file to be ingested
	private CSVReader reader;

	private static final Logger LOGGER = LogManager.getLogger();

	private static final String CONCORANCE_FILE_NAME = "/placekey_concordance.csv";

    /**
     * Constructor which loads default CSV file.
	 * 
	 * @param fileName - Name of CSV file in classpath to load.
     */
	public PlaceKeyConcordanceManager() throws FileNotFoundException {
		this(CONCORANCE_FILE_NAME);
	}
	
	/**
	 * This constructor can be used for unit tests.
	 * 
	 * @param fileName - Name of CSV file in classpath to load.
	 */
	protected PlaceKeyConcordanceManager(String fileName) throws FileNotFoundException {

		HeaderColumnNameTranslateMappingStrategy<PlaceKeyConcordanceBean> strat = new HeaderColumnNameTranslateMappingStrategy<PlaceKeyConcordanceBean>() {
			
			/**
			 * Return the column name referring to the bean's attributes
			 * rather than column names in CSV file.
			 */
			@Override
			public String getColumnName(int col) {
				return (col < getCsvColumnEnums().size() ? getCsvColumnEnums().get(col).toString() : null);
			}
		};
		strat.setType(getBeanClass()); // set target JavaBean class

	    InputStream is = getClass().getResourceAsStream(fileName);
	    if (is == null) {
	    	throw new FileNotFoundException("[" + fileName + "] cannot be found in classpath.");
	    }
	    
	    reader = new CSVReader(new InputStreamReader(is));
	    map = initBeanMap(strat, reader);
	    try {
	    	reader.close();
	    } catch (IOException ioe) {
	    	// nothing to do but report
	    	LOGGER.warn("Could not close file: " + fileName, ioe);
	    }
	}
	
	private MapOfLists<String, PlaceKeyConcordanceBean> initBeanMap(
			HeaderColumnNameTranslateMappingStrategy<PlaceKeyConcordanceBean> strat,
			CSVReader reader) {
		
	    CsvToBean<PlaceKeyConcordanceBean> csv = new CsvToBean<>();
	    List<PlaceKeyConcordanceBean> list = csv.parse(strat, reader);
	    // populate local map of keyword name to Bean
	    MapOfLists<String, PlaceKeyConcordanceBean> map = new MapOfLists<>();
	    for (PlaceKeyConcordanceBean item : list) {
	    	map.addValue(item.getLayerId(), item);
	    }
	    return map;
	}

	@SuppressWarnings("rawtypes")
	private List<Enum> getCsvColumnEnums() {
		List<Enum> list = new ArrayList<>();
		for (ConcordanceCsvColumn val : ConcordanceCsvColumn.values()) {
			list.add(val);
		}
		return list;
	}
	
	private Class<PlaceKeyConcordanceBean> getBeanClass() {
		return PlaceKeyConcordanceBean.class;
	}


	/**
	 * Map of keyword to AgentsConcordanceBean - for use in unit tests.
	 */
	public MapOfLists<String, PlaceKeyConcordanceBean> getMap() {
		return map;
	}
	
	/**
	 * Returns the entry for the layer ID; <code>null</code> if there is no entry for the given value;
	 * 
	 * @param key - The keyword for which an entry is to be returned.
	 * @return - The corresponding entry if one exists; <code>null</code> otherwise.
	 */
	public PlaceKeyConcordanceBean getConcordanceEntry(String key) {
		return map.getValue(key);
	}
	
	/**
	 * Return the list of entries for the layer ID; an empty list if there are no entries.
	 * 
	 * @param key - The keyword for which an entry is to be returned.
	 * @return - The corresponding entries if they exists; an empty list otherwise.
	 */
	public List<PlaceKeyConcordanceBean> getConcordanceEntries(String key) {
		return map.getValues(key);
	}
}
