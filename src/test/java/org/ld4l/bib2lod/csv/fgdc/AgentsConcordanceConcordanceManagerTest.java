package org.ld4l.bib2lod.csv.fgdc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class AgentsConcordanceConcordanceManagerTest {

	private static final String TEST_CSV_FILE = "/test_Agents_concordance_v1.csv";
    private static final Logger LOGGER = LogManager.getLogger();

	@Test
	public void readFileIntoManager() {
		try {
			new AgentsConcordanceManager(TEST_CSV_FILE);
		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}

	@Test(expected=FileNotFoundException.class)
	public void readMissingFileIntoManager() throws URISyntaxException, IOException {
		new AgentsConcordanceManager("/missing-file.csv");
		Assert.fail("File should be missing and exception thrown");
	}
	
	@Test
	public void readData() {
		try {
			AgentsConcordanceManager mgr = new AgentsConcordanceManager(TEST_CSV_FILE);
			Map<String, AgentsConcordanceBean> map = mgr.getMap();
			Assert.assertNotNull(map);
			Assert.assertFalse(map.isEmpty());
			Assert.assertEquals(4, map.size());
			
			AgentsConcordanceBean bean = mgr.getConcordanceEntry("Lange, Henry, 1821-1893.");
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

}
