package org.ld4l.bib2lod.testing;

/**
 * Test data for various FGDC tests.
 */
public class FgdcTestData {
    
	public static final String VALID_CITEINFO = 
        "<metadata layerid='CAMBRIDGE14PUBLICLIBRARIES'>" +
        	"<idinfo>" +
				"<citation>" +
					"<citeinfo>" +
						"<title>Public Libraries, Cambridge, Massachusetts, 2014</title>" +
					"</citeinfo>" +
				"</citation>" +
			"</idinfo>" +
		"</metadata>";

	public static final String INVALID_CITEINFO_MISSING_TITLE = 
        "<metadata layerid='CAMBRIDGE14PUBLICLIBRARIES'>" +
				"<idinfo>" +
					"<citation>" +
						"<citeinfo>" +
						"</citeinfo>" +
					"</citation>" +
				"</idinfo>" +
		"</metadata>";

//////////////

	public static final String VALID_BOUNDING = 
        "<metadata layerid='CAMBRIDGE14PUBLICLIBRARIES'>" +
			"<idinfo>" +
				"<citation>" +
					"<citeinfo>" +
						"<title>Public Libraries, Cambridge, Massachusetts, 2014</title>" +
					"</citeinfo>" +
				"</citation>" +
				"<spdom>" +
					"<bounding>" +
						"<westbc>-71.146912</westbc>" +
						"<eastbc>-71.084684</eastbc>" +
						"<northbc>42.392720</northbc>" +
						"<southbc>42.363838</southbc>" +
					"</bounding>" +
				"</spdom>" +
			"</idinfo>" +
		"</metadata>";
  
	public static final String INVALID_BOUNDING_MISSING_WEST = 
        "<metadata layerid='CAMBRIDGE14PUBLICLIBRARIES'>" +
			"<idinfo>" +
				"<citation>" +
					"<citeinfo>" +
						"<title>Public Libraries, Cambridge, Massachusetts, 2014</title>" +
					"</citeinfo>" +
				"</citation>" +
				"<spdom>" +
					"<bounding>" +
						"<eastbc>-71.084684</eastbc>" +
						"<northbc>42.392720</northbc>" +
						"<southbc>42.363838</southbc>" +
					"</bounding>" +
				"</spdom>" +
			"</idinfo>" +
		"</metadata>";
 
	public static final String INVALID_BOUNDING_EMPTY_WEST = 
        "<metadata layerid='CAMBRIDGE14PUBLICLIBRARIES'>" +
			"<idinfo>" +
				"<citation>" +
					"<citeinfo>" +
					"<title>Public Libraries, Cambridge, Massachusetts, 2014</title>" +
					"</citeinfo>" +
				"</citation>" +
				"<spdom>" +
					"<bounding>" +
						"<westbc></westbc>" +
						"<eastbc>-71.084684</eastbc>" +
						"<northbc>42.392720</northbc>" +
						"<southbc>42.363838</southbc>" +
					"</bounding>" +
				"</spdom>" +
			"</idinfo>" +
		"</metadata>";
////////////////

	public static final String VALID_KEYWORDS = 
        "<metadata layerid='CAMBRIDGE14PUBLICLIBRARIES'>" +
			"<idinfo>" +
				"<citation>" +
					"<citeinfo>" +
						"<title>Public Libraries, Cambridge, Massachusetts, 2014</title>" +
					"</citeinfo>" +
				"</citation>" +
				"<spdom>" +
					"<bounding>" +
						"<westbc>-71.146912</westbc>" +
						"<eastbc>-71.084684</eastbc>" +
						"<northbc>42.392720</northbc>" +
						"<southbc>42.363838</southbc>" +
					"</bounding>" +
				"</spdom>" +
				"<keywords>" +
					"<theme>" +
						"<themekt>LCSH</themekt>" +
						"<themekey>Libraries</themekey>" +
						"<themekey>Public libraries</themekey>" +
					"</theme>" +
					"<theme>" +
						"<themekt>ISO 19115 Topic Category</themekt>" +
						"<themekey>structure</themekey>" +
					"</theme>" +
					"<theme>" +
						"<themekt>None</themekt>" +
						"<themekey>City of Cambridge</themekey>" +
						"<themekey>Libraries</themekey>" +
						"<themekey>public libraries</themekey>" +
					"</theme>" +
					"<place>" +
						"<placekt>GNIS</placekt>" +
						"<placekey>Massachusetts</placekey>" +
						"<placekey>Cambridge</placekey>" +
					"</place>" +
				"</keywords>" +
			"</idinfo>" +
		"</metadata>";

	public static final String VALID_THEME = 
		"<theme>" +
			"<themekt>LCSH</themekt>" +
			"<themekey>City of Cambridge</themekey>" +
			"<themekey>Libraries</themekey>" +
			"<themekey>public libraries</themekey>" +
		"</theme>";

	public static final String VALID_PLACE = 
		"<place>" +
			"<placekt>GNIS</placekt>" +
			"<placekey>Massachusetts</placekey>" +
			"<placekey>Cambridge</placekey>" +
		"</place>";
}
