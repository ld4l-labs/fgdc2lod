package org.ld4l.bib2lod.csv.fgdc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class UriLabelConcordanceManagerTest {

	private static final String TEST_PLACES_CSV_FILE = "/test_Place_of_Publication_Concordance.csv";
	private static final String TEST_AGENTS_CSV_FILE = "/test_Agents_concordance_v1.csv";
	private static final String TEST_FAST_CSV_FILE = "/test_FAST_concordance.csv";

	private static final Logger LOGGER = LogManager.getLogger();

	@Test
	public void readFileIntoManager() {
		try {
			new UriLabelConcordanceManager(TEST_AGENTS_CSV_FILE);
		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}

	@Test(expected=FileNotFoundException.class)
	public void readMissingFileIntoManager() throws URISyntaxException, IOException {
		new UriLabelConcordanceManager("/missing-file.csv");
		Assert.fail("File should be missing and exception thrown");
	}
	
	@Test
	public void readAgentsData() {
		try {
			UriLabelConcordanceManager mgr = new UriLabelConcordanceManager(TEST_AGENTS_CSV_FILE);
			Map<String, UriLabelConcordanceBean> map = mgr.getMap();
			Assert.assertNotNull(map);
			Assert.assertFalse(map.isEmpty());
			Assert.assertEquals(4, map.size());
			
			UriLabelConcordanceBean bean = mgr.getConcordanceEntry("Lange, Henry, 1821-1893.");
			Assert.assertNotNull(bean);
			Assert.assertEquals("Lange, Henry, 1821-1893.", bean.getMatchingText());
			Assert.assertEquals("http://id.loc.gov/rwo/agents/n2001120246", bean.getUri());
			Assert.assertEquals("Lange, Henry, 1821-1893.", bean.getLabel());
			
			bean = mgr.getConcordanceEntry("no-entry");
			Assert.assertNull(bean);
		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void readPlaceData() {
		try {
			UriLabelConcordanceManager mgr = new UriLabelConcordanceManager(TEST_PLACES_CSV_FILE);
			Map<String, UriLabelConcordanceBean> map = mgr.getMap();
			Assert.assertNotNull(map);
			Assert.assertFalse(map.isEmpty());
			Assert.assertEquals(15, map.size());
			
			UriLabelConcordanceBean bean = mgr.getConcordanceEntry("Cambridge, Mass.");
			Assert.assertNotNull(bean);
			Assert.assertEquals("Cambridge, Mass.", bean.getMatchingText());
			Assert.assertEquals("http://sws.geonames.org/4932004", bean.getUri());
			Assert.assertEquals("City of Cambridge", bean.getLabel());
			
			bean = mgr.getConcordanceEntry("no-entry");
			Assert.assertNull(bean);
		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void readFastData() {
		try {
			UriLabelConcordanceManager mgr = new UriLabelConcordanceManager(TEST_FAST_CSV_FILE);
			Map<String, UriLabelConcordanceBean> map = mgr.getMap();
			Assert.assertNotNull(map);
			Assert.assertFalse(map.isEmpty());
			Assert.assertEquals(3, map.size());
			
			UriLabelConcordanceBean bean = mgr.getConcordanceEntry("Human settlements");
			Assert.assertNotNull(bean);
			Assert.assertEquals("Human settlements", bean.getMatchingText());
			Assert.assertEquals("http://id.worldcat.org/fast/963433", bean.getUri());
			Assert.assertEquals("Human settlements", bean.getLabel());
			
			bean = mgr.getConcordanceEntry("no-entry");
			Assert.assertNull(bean);
		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}

}
