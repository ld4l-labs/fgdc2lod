/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv.fgdc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ld4l.bib2lod.csv.AbstractConcordanceManager;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

/**
 * This manager is applicable for concordance CSV files with 3 columns that have matching text,
 * a URI, and a label, in that order.
 */
public class UriLabelConcordanceManager extends AbstractConcordanceManager<UriLabelConcordanceBean> {
	
	private enum ConcordanceCsvColumn {
		
		MATCHING_TEXT("matchingText"),
		URI("uri"),
		LABEL("label");
		
		private String columnName;
		
		private ConcordanceCsvColumn(String columnName) {
			this.columnName = columnName;
		}
		
		/**
		 * Must match getters/setters for JavaBean reflection used by OpenCSV.
		 */
		public String toString() {
			return columnName;
		}
	}
	
	private static final String AGENTS_CONCORANCE_FILE_NAME = "/Agents_concordance_v1.csv";
	private static final String FAST_CONCORANCE_FILE_NAME = "/FAST_topics_concordance_v1.csv";
	private static final String PLACES_CONCORANCE_FILE_NAME = "/Place_of_Publication_Concordance.csv";
	
	/**
	 * Concordance manager containing the Agents CSV data.
	 * 
	 * @return UriLabelConcordanceManager
	 * @throws IOException
	 */
	public static UriLabelConcordanceManager getAgentsConcordanceManager() throws FileNotFoundException {
		return new UriLabelConcordanceManager(AGENTS_CONCORANCE_FILE_NAME);
	}
	
	/**
	 * Concordance manager containing the FAST CSV data.
	 * 
	 * @return UriLabelConcordanceManager
	 * @throws IOException
	 */
	public static UriLabelConcordanceManager getFastConcordanceManager() throws FileNotFoundException {
		return new UriLabelConcordanceManager(FAST_CONCORANCE_FILE_NAME);
	}
	
	/**
	 * Concordance manager containing the Places CSV data.
	 * 
	 * @return UriLabelConcordanceManager
	 * @throws IOException
	 */
	public static UriLabelConcordanceManager getPlacesConcordanceManager() throws FileNotFoundException {
		return new UriLabelConcordanceManager(PLACES_CONCORANCE_FILE_NAME);
	}
	
	/**
	 * This constructor can be used for unit tests to load a specific CSV file.
	 * 
	 * @param fileName - Name of CSV file in classpath to load.
	 * @throws FileNotFoundException - If file not found on classpath.
	 */
	protected UriLabelConcordanceManager(String fileName) throws FileNotFoundException {
		super(fileName);
	}
	
	/**
	 * @see org.ld4l.bib2lod.csv.AbstractConcordanceManager#initBeanMap(com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy, com.opencsv.CSVReader)
	 */
	@Override
	protected Map<String, UriLabelConcordanceBean> initBeanMap(
			HeaderColumnNameTranslateMappingStrategy<UriLabelConcordanceBean> strat,
			CSVReader reader) {
		
	    CsvToBean<UriLabelConcordanceBean> csv = new CsvToBean<>();
	    List<UriLabelConcordanceBean> list = csv.parse(strat, reader);
	    // populate local map of keyword name to Bean
	    Map<String, UriLabelConcordanceBean> map = new HashMap<>(list.size());
	    for (UriLabelConcordanceBean item : list) {
	    	map.put(item.getMatchingText(), item);
	    }
	    return map;
	}

	/**
	 * @see org.ld4l.bib2lod.csv.AbstractConcordanceManager#getCsvColumnEnums()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected List<Enum> getCsvColumnEnums() {
		List<Enum> list = new ArrayList<>();
		for (ConcordanceCsvColumn val : ConcordanceCsvColumn.values()) {
			list.add(val);
		}
		return list;
	}
	
	/**
	 * @see org.ld4l.bib2lod.csv.AbstractConcordanceManager#getBeanClass()
	 */
	@Override
	protected Class<UriLabelConcordanceBean> getBeanClass() {
		return UriLabelConcordanceBean.class;
	}
	
}
