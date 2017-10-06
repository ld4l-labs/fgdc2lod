/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv.fgdc;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ld4l.bib2lod.csv.AbstractConcordanceManager;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class FgdcGeoformConcordanceManager extends AbstractConcordanceManager<FgdcGeoformConcordanceBean> {
	
	private enum ConcordanceCsvColumn {
		
		GEOFORM_TEXT("geoformText"),
		MAPPING_EQUIVALENT("mappingEquivalent"),
		LABEL("label");
		
		private String columnName;
		
		private ConcordanceCsvColumn(String columnName) {
			this.columnName = columnName;
		}
		
		public String toString() {
			return columnName;
		}
	}
	
	private static final String CONCORANCE_FILE_NAME = "/FGDC_Geospatial_Data_Presentation_Form_to_Cartotek-o_mapping.csv";

    /**
     * Constructor which loads default CSV file.
     * 
	 * @throws FileNotFoundException - If file not found on classpath.
     */
	public FgdcGeoformConcordanceManager() throws FileNotFoundException {
		this(CONCORANCE_FILE_NAME);
	}
	
	/**
	 * This constructor can be used for unit tests.
	 * 
	 * @param fileName - Name of CSV file in classpath to load.
	 * @throws URISyntaxException 
	 * @throws FileNotFoundException - If file not found on classpath.
	 */
	protected FgdcGeoformConcordanceManager(String fileName) throws FileNotFoundException {
		super(fileName);
	}
	
	/**
	 * @see org.ld4l.bib2lod.csv.AbstractConcordanceManager#initBeanMap(com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy, com.opencsv.CSVReader)
	 */
	@Override
	protected Map<String, FgdcGeoformConcordanceBean> initBeanMap(
			HeaderColumnNameTranslateMappingStrategy<FgdcGeoformConcordanceBean> strat,
			CSVReader reader) {
		
	    CsvToBean<FgdcGeoformConcordanceBean> csv = new CsvToBean<>();
	    List<FgdcGeoformConcordanceBean> list = csv.parse(strat, reader);
	    // populate local map of keyword name to Bean
	    Map<String, FgdcGeoformConcordanceBean> map = new HashMap<>(list.size());
	    for (FgdcGeoformConcordanceBean item : list) {
	    	map.put(item.getGeoformText(), item);
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
	protected Class<FgdcGeoformConcordanceBean> getBeanClass() {
		return FgdcGeoformConcordanceBean.class;
	}

}
