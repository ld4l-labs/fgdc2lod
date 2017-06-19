package org.ld4l.bib2lod.csv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class IsoTopicConcordanceManagerTest {

	private static final String TEST_CSV_FILE = "/test_ISO_Topic_concordance.csv";
    private static final Logger LOGGER = LogManager.getLogger(); 

	@Test
	public void readFileIntoManager() {
		try {
			new IsoTopicConcordanceManager(TEST_CSV_FILE);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test(expected=FileNotFoundException.class)
	public void readMissingFileIntoManager() throws URISyntaxException, IOException {
		new IsoTopicConcordanceManager("/missing-file.csv");
		Assert.fail("File should be missing and exception thrown");
	}
	
	@Test
	public void readData() {
		try {
			IsoTopicConcordanceManager mgr = new IsoTopicConcordanceManager(TEST_CSV_FILE);
			Map<String, IsoTopicConcordanceBean> map = mgr.getMap();
			Assert.assertNotNull(map);
			Assert.assertFalse(map.isEmpty());
			Assert.assertEquals(2, map.size());
			
			IsoTopicConcordanceBean bean = mgr.getConcordanceEntry("farming");
			Assert.assertNotNull(bean);
			Assert.assertEquals("farming", bean.getTopicKeyword());
			Assert.assertEquals("http://farming", bean.getUri());
			Assert.assertEquals("1", bean.getDomainCode());
			Assert.assertEquals("Agriculture", bean.getLabel());
			Assert.assertEquals("Agriculture definition", bean.getDefinition());
			
			bean = mgr.getConcordanceEntry("no-entry");
			Assert.assertNull(bean);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
