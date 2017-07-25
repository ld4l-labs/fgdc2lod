/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv.fgdc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ld4l.bib2lod.csv.AbstractConcordanceManager;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class FASTConcordanceManager extends AbstractConcordanceManager<FASTConcordanceBean> {
	
	private enum ConcordanceCsvColumn {
		
		MATCHING_TEXT("matchingText"),
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
	
	private static final String CONCORANCE_FILE_NAME = "/FAST_topics_concordance_v1.csv";

    /**
     * Constructor which loads default CSV file.
     */
	public FASTConcordanceManager() throws URISyntaxException, IOException {
		this(CONCORANCE_FILE_NAME);
	}
	
	/**
	 * This constructor can be used for unit tests.
	 * 
	 * @param fileName - Name of CSV file in classpath to load.
	 */
	protected FASTConcordanceManager(String fileName) throws URISyntaxException, IOException {
		super(fileName);
	}
	
	/**
	 * @see org.ld4l.bib2lod.csv.AbstractConcordanceManager#initBeanMap(com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy, com.opencsv.CSVReader)
	 */
	@Override
	protected Map<String, FASTConcordanceBean> initBeanMap(
			HeaderColumnNameTranslateMappingStrategy<FASTConcordanceBean> strat,
			CSVReader reader) {
		
	    CsvToBean<FASTConcordanceBean> csv = new CsvToBean<>();
	    List<FASTConcordanceBean> list = csv.parse(strat, reader);
	    // populate local map of keyword name to Bean
	    Map<String, FASTConcordanceBean> map = new HashMap<>(list.size());
	    for (FASTConcordanceBean item : list) {
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
	protected Class<FASTConcordanceBean> getBeanClass() {
		return FASTConcordanceBean.class;
	}

}
