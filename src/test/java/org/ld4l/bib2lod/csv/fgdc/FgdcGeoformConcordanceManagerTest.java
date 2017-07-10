package org.ld4l.bib2lod.csv.fgdc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.csv.fgdc.FgdcGeoformConcordanceBean;
import org.ld4l.bib2lod.csv.fgdc.FgdcGeoformConcordanceManager;

public class FgdcGeoformConcordanceManagerTest {

	private static final String TEST_CSV_FILE = "/test_FGDC_Geospatial_concordance.csv";
    private static final Logger LOGGER = LogManager.getLogger();

	@Test
	public void readFileIntoManager() {
		try {
			new FgdcGeoformConcordanceManager(TEST_CSV_FILE);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test(expected=FileNotFoundException.class)
	public void readMissingFileIntoManager() throws URISyntaxException, IOException {
		new FgdcGeoformConcordanceManager("/missing-file.csv");
		Assert.fail("File should be missing and exception thrown");
	}
	
	@Test
	public void readData() {
		try {
			FgdcGeoformConcordanceManager mgr = new FgdcGeoformConcordanceManager(TEST_CSV_FILE);
			Map<String, FgdcGeoformConcordanceBean> map = mgr.getMap();
			Assert.assertNotNull(map);
			Assert.assertFalse(map.isEmpty());
			Assert.assertEquals(5, map.size());
			
			FgdcGeoformConcordanceBean bean = mgr.getConcordanceEntry("map");
			Assert.assertNotNull(bean);
			Assert.assertEquals("map", bean.getGeoformText());
			Assert.assertEquals("Maps, GeoreferencedCartographicResources", bean.getMappingEquivalent());
			List<String> expected = Arrays.asList("Maps", "GeoreferencedCartographicResources");
			String[] equivalents = bean.getMappingEquivalentArray();
			for (String e : equivalents) {
				Assert.assertTrue(expected.contains(e));
			}
			Assert.assertEquals("Map, Georeferenced Cartographic Resource", bean.getLabel());
			
			bean = mgr.getConcordanceEntry("no-entry");
			Assert.assertNull(bean);
		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}

}
