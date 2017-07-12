/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv.fgdc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class IsoTopicConcordanceManager extends AbstractConcordanceManager<IsoTopicConcordanceBean> {
	
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
		
		public String toString() {
			return columnName;
		}
	}
	
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
		super(fileName);
	}
	
	/**
	 * @see org.ld4l.bib2lod.csv.fgdc.AbstractConcordanceManager#initBeanMap(com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy, com.opencsv.CSVReader)
	 */
	@Override
	protected Map<String, IsoTopicConcordanceBean> initBeanMap(
			HeaderColumnNameTranslateMappingStrategy<IsoTopicConcordanceBean> strat,
			CSVReader reader) {
		
	    CsvToBean<IsoTopicConcordanceBean> csv = new CsvToBean<>();
	    List<IsoTopicConcordanceBean> list = csv.parse(strat, reader);
	    // populate local map of keyword name to Bean
	    Map<String, IsoTopicConcordanceBean> map = new HashMap<>(list.size());
	    for (IsoTopicConcordanceBean item : list) {
	    	map.put(item.getTopicKeyword(), item);
	    }
	    return map;
	}

	/**
	 * @see org.ld4l.bib2lod.csv.fgdc.AbstractConcordanceManager#getCsvColumnEnums()
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
	 * @see org.ld4l.bib2lod.csv.fgdc.AbstractConcordanceManager#getBeanClass()
	 */
	@Override
	protected Class<IsoTopicConcordanceBean> getBeanClass() {
		return IsoTopicConcordanceBean.class;
	}

}
