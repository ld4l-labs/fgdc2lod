/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

/**
 * Base class for ingesting CSV files and mapping to JavaBean classes using the OpenCSV package.
 *
 * @param <T> the JavaBean target.
 */
abstract public class AbstractConcordanceManager<T> {
	
	// map of index to JavaBean representing a row in CSV file
	private Map<String, T> map;

	// encapsulates CSV file to be ingested
	private CSVReader reader;

	protected AbstractConcordanceManager(String fileName) throws IOException {

		HeaderColumnNameTranslateMappingStrategy<T> strat = new HeaderColumnNameTranslateMappingStrategy<T>() {
			
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
	    reader.close();
	}

	/**
	 * Get a list of the enums representing the columns in the ingested CSV file.
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected abstract List<Enum> getCsvColumnEnums();
	
	/**
	 * Get the JavaBean class representing a row in the ingested CSV file.
	 * 
	 * @return The JavaBean representing a row in the ingested CSV file.
	 */
	protected abstract Class<T> getBeanClass();

	/**
	 * Initialize the mapping of the index (lookup value) for JavaBean classes representing rows in the
	 * ingested CSV
	 * 
	 * @param strat The strategy class for the CSVReader.
	 * @param reader The CSVReader class representing the ingested CSV file.
	 * @return The generated Map of indexed JavaBean classes representing the ingested CSV file.
	 */
	protected abstract Map<String, T> initBeanMap(HeaderColumnNameTranslateMappingStrategy<T> strat, CSVReader reader);

	/**
	 * Map of keyword to AgentsConcordanceBean - for use in unit tests.
	 */
	public Map<String, T> getMap() {
		return map;
	}
	
	/**
	 * Returns the entry for the topicKeyword; <code>null</code> if there is no entry for the given value;
	 * 
	 * @param key - The keyword for which an entry is to be returned.
	 * @return - The corresponding entry if one exists; <code>null</code> otherwise.
	 */
	public T getConcordanceEntry(String key) {
		return map.get(key);
	}
	
}
