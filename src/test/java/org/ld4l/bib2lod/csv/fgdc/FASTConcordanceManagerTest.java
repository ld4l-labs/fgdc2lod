package org.ld4l.bib2lod.csv.fgdc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class FASTConcordanceManagerTest {

	private static final String TEST_CSV_FILE = "/test_FAST_concordance.csv";
    private static final Logger LOGGER = LogManager.getLogger();

	@Test
	public void readFileIntoManager() {
		try {
			new FASTConcordanceManager(TEST_CSV_FILE);
		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}

	@Test(expected=FileNotFoundException.class)
	public void readMissingFileIntoManager() throws URISyntaxException, IOException {
		new FASTConcordanceManager("/missing-file.csv");
		Assert.fail("File should be missing and exception thrown");
	}
	
	@Test
	public void readData() {
		try {
			FASTConcordanceManager mgr = new FASTConcordanceManager(TEST_CSV_FILE);
			Map<String, FASTConcordanceBean> map = mgr.getMap();
			Assert.assertNotNull(map);
			Assert.assertFalse(map.isEmpty());
			Assert.assertEquals(3, map.size());
			
			FASTConcordanceBean bean = mgr.getConcordanceEntry("Human settlements");
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
