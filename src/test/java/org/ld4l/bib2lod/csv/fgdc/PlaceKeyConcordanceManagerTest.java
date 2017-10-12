package org.ld4l.bib2lod.csv.fgdc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.util.collections.MapOfLists;

public class PlaceKeyConcordanceManagerTest {

	private static final String TEST_CSV_FILE = "/test_placekey_concordance.csv";
    private static final Logger LOGGER = LogManager.getLogger(); 

	@Test
	public void readFileIntoManager() {
		try {
			new PlaceKeyConcordanceManager(TEST_CSV_FILE);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test(expected=FileNotFoundException.class)
	public void readMissingFileIntoManager() throws URISyntaxException, IOException {
		new PlaceKeyConcordanceManager("/missing-file.csv");
		Assert.fail("File should be missing and exception thrown");
	}
	
	@Test
	public void readData() {
		try {
			PlaceKeyConcordanceManager mgr = new PlaceKeyConcordanceManager(TEST_CSV_FILE);
			MapOfLists<String,PlaceKeyConcordanceBean> map = mgr.getMap();
			Assert.assertNotNull(map);
			Assert.assertFalse(map.isEmpty());
			Assert.assertEquals(7, map.values().size());
			
			List<PlaceKeyConcordanceBean> beans = mgr.getConcordanceEntries("CAMBRIDGE14PRIVATEWALKWAYS");
			Assert.assertEquals(2, beans.size());
			
			PlaceKeyConcordanceBean bean = mgr.getConcordanceEntry("CAMBRIDGE14PUBLICPOOLS");
			Assert.assertNotNull(bean);
			
			Assert.assertEquals("CAMBRIDGE14PUBLICPOOLS", bean.getLayerId());
			Assert.assertEquals("Public Pools, Cambridge, Massachusetts, 2006 (pub. 2014)", bean.getTitle());
			Assert.assertEquals("GNIS", bean.getSource());
			Assert.assertEquals("http://sws.geonames.org/4932004", bean.getUri());
			Assert.assertEquals("City of Cambridge", bean.getLabel());
			
			bean = mgr.getConcordanceEntry("no-entry");
			Assert.assertNull(bean);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
