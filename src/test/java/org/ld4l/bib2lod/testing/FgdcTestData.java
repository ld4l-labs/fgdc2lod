package org.ld4l.bib2lod.testing;

/**
 * Test data for various FGDC tests.
 */
public class FgdcTestData {
    
	public static final String VALID_CITEINFO = 
        "<metadata layerid='CAMBRIDGE14PUBLICLIBRARIES' hollisno='014266502'>" +
        	"<idinfo>" +
				"<citation>" +
					"<citeinfo>" +
						"<title>Public Libraries, Cambridge, Massachusetts, 2014</title>" +
					"</citeinfo>" +
				"</citation>" +
			"</idinfo>" +
		"</metadata>";
    
	public static final String VALID_CITEINFO_DUPLICATE_LAYER_ID_AND_HOLLIS = 
        "<metadata layerid='CAMBRIDGE14PUBLICLIBRARIES' hollisno='014266502'>" +
        	"<idinfo>" +
				"<citation>" +
					"<citeinfo>" +
						"<title>Private Libraries, East Podunk, Mississippi, 2015</title>" +
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
	
		public static final String VALID_ACTIVITIES =
	        "<metadata layerid='CAMBRIDGE14PUBLICLIBRARIES'>" +
            	"<idinfo>" +
    				"<citation>" +
    					"<citeinfo>" +
    	                "<origin>Cambridge (Mass.). Geographic Information Systems</origin>" +
    	                "<origin>Cambridge (Mass.). Public Library</origin>" +
    	                "<pubdate>2014</pubdate>" +
    	                "<title>Public Libraries, Cambridge, Massachusetts, 2014</title>" +
    	                "<edition>2014 revised ed.</edition>" +
    	                "<pubinfo>" +
    	                    "<pubplace>Cambridge, Massachusetts</pubplace>" +
    	                    "<publish>Cambridge</publish>" +
    	                "</pubinfo>" +
    	                "<onlink>http://hgl.harvard.edu:8080/HGL/hgl.jsp?action=VColl&amp;VCollName=CAMBRIDGE14PUBLICLIBRARIES</onlink>" +
    					"</citeinfo>" +
    				"</citation>" +
    			"</idinfo>" +
    		"</metadata>";
		
		public static final String VALID_ACTIVITIES_NO_CONCORDANCE_MATCH =
	        "<metadata layerid='CAMBRIDGE14PUBLICLIBRARIES'>" +
            	"<idinfo>" +
    				"<citation>" +
    					"<citeinfo>" +
    	                "<origin>Cambridge (Mass.). Geographic Information Systems</origin>" +
    	                "<origin>Cambridge (Mass.). Public Library</origin>" +
    	                "<pubdate>2014</pubdate>" +
    	                "<title>Public Libraries, Cambridge, Massachusetts, 2014</title>" +
    	                "<edition>2014 revised ed.</edition>" +
    	                "<pubinfo>" +
    	                    "<pubplace>Upper Slabovia</pubplace>" +
    	                    "<publish>Cambridge</publish>" +
    	                "</pubinfo>" +
    	                "<onlink>http://hgl.harvard.edu:8080/HGL/hgl.jsp?action=VColl&amp;VCollName=CAMBRIDGE14PUBLICLIBRARIES</onlink>" +
    					"</citeinfo>" +
    				"</citation>" +
    			"</idinfo>" +
    		"</metadata>";

				
////////////////

	public static final String VALID_KEYWORDS = 
        "<metadata layerid='CAMBRIDGE14PUBLICLIBRARIES' hollisno='014266502'>" +
			"<idinfo>" +
				"<citation>" +
					"<citeinfo>" +
						"<origin>Cambridge (Mass.). Geographic Information Systems</origin>" +
						"<origin>Cambridge (Mass.). Public Library</origin>" +
						"<pubdate>2014</pubdate>" +
						"<title>Public Libraries, Cambridge, Massachusetts, 2014</title>" +
						"<geoform>map</geoform>" +
						"<edition>2014 revised ed.</edition>" +
						"<pubinfo>" +
							"<pubplace>Cambridge, Massachusetts</pubplace>" +
							"<publish>Cambridge (Mass.). Geographic Information Systems</publish>" +
						"</pubinfo>" +
					"</citeinfo>" +
				"</citation>" +
				"<descript>" +
					"<abstract>Abstract text</abstract>" +
					"<purpose>Purpose text</purpose>" +
				"</descript>" +
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
		            "<place>" +
		                "<placekt>LCNA</placekt>" +
		                "<placekey>Masschusetts</placekey>" +
		                "<placekey>Cambridge</placekey>" +
		            "</place>" +
				"</keywords>" +
			"</idinfo>" +
		"</metadata>";

	public static final String VALID_DUPLICATE_KEYWORDS = 
        "<metadata layerid='CAMBRIDGE14PUBLICLIBRARIES' hollisno='014266502'>" +
			"<idinfo>" +
				"<citation>" +
					"<citeinfo>" +
						"<origin>Cambridge (Mass.). Geographic Information Systems</origin>" +
						"<origin>Cambridge (Mass.). Public Library</origin>" +
						"<pubdate>2014</pubdate>" +
						"<title>Public Libraries, Cambridge, Massachusetts, 2014</title>" +
						"<geoform>map</geoform>" +
						"<edition>2014 revised ed.</edition>" +
						"<pubinfo>" +
							"<pubplace>Cambridge, Massachusetts</pubplace>" +
							"<publish>Cambridge (Mass.). Geographic Information Systems</publish>" +
						"</pubinfo>" +
					"</citeinfo>" +
				"</citation>" +
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
		            "<place>" +
		                "<placekt>LCNA</placekt>" +
		                "<placekey>Masschusetts</placekey>" +
		                "<placekey>Cambridge</placekey>" +
		            "</place>" +
				"</keywords>" +
			"</idinfo>" +
		"</metadata>";

	public static final String NON_MATCHING_CONCORDANCE_SUBTYPE_TEXT = "BOGUS";
	public static final String VALID_BUT_NON_MATCHING_CONCORDANCE_SUBTYPE = 
        "<metadata layerid='CAMBRIDGE14PUBLICLIBRARIES'>" +
			"<idinfo>" +
				"<citation>" +
					"<citeinfo>" +
						"<origin>Cambridge (Mass.). Geographic Information Systems</origin>" +
						"<origin>Cambridge (Mass.). Public Library</origin>" +
						"<pubdate>2014</pubdate>" +
						"<title>Public Libraries, Cambridge, Massachusetts, 2014</title>" +
						"<geoform>" + NON_MATCHING_CONCORDANCE_SUBTYPE_TEXT + "</geoform>" +
					"</citeinfo>" +
				"</citation>" +
			"</idinfo>" +
		"</metadata>";

	public static final String VALID_THEME = 
		"<theme>" +
			"<themekt>LCSH</themekt>" +
			"<themekey>City of Cambridge</themekey>" +
			"<themekey>Libraries</themekey>" +
			"<themekey>public libraries</themekey>" +
		"</theme>";

	public static final String INVALID_THEME_MISSING_THEME_KT = 
		"<theme>" +
			"<themekey>City of Cambridge</themekey>" +
			"<themekey>Libraries</themekey>" +
			"<themekey>public libraries</themekey>" +
		"</theme>";

	public static final String INVALID_THEME_EMPTY_THEME_KT = 
		"<theme>" +
			"<themekt></themekt>" +
			"<themekey>City of Cambridge</themekey>" +
			"<themekey>Libraries</themekey>" +
			"<themekey>public libraries</themekey>" +
		"</theme>";

	public static final String INVALID_THEME_MISSING_THEME_KEY = 
		"<theme>" +
			"<themekt>LCSH</themekt>" +
		"</theme>";

	public static final String INVALID_THEME_EMPTY_THEME_KEY = 
		"<theme>" +
			"<themekt>LCSH</themekt>" +
			"<themekey></themekey>" +
		"</theme>";

	public static final String VALID_PLACE = 
		"<place>" +
			"<placekt>GNIS</placekt>" +
			"<placekey>Massachusetts</placekey>" +
			"<placekey>Cambridge</placekey>" +
		"</place>";

	public static final String INVALID_PLACE_MISSING_PLACE_KT = 
		"<place>" +
			"<placekey>Massachusetts</placekey>" +
			"<placekey>Cambridge</placekey>" +
		"</place>";

	public static final String INVALID_PLACE_EMPTY_PLACE_KT = 
		"<place>" +
			"<placekt></placekt>" +
			"<placekey>Massachusetts</placekey>" +
			"<placekey>Cambridge</placekey>" +
		"</place>";

	public static final String INVALID_PLACE_MISSING_PLACE_KEY = 
		"<place>" +
			"<placekt>GNIS</placekt>" +
		"</place>";

	public static final String INVALID_PLACE_EMPTY_PLACE_KEY = 
		"<place>" +
			"<placekt>GNIS</placekt>" +
			"<placekey></placekey>" +
			"<placekey>Cambridge</placekey>" +
		"</place>";
	
//////////
	public static final String VALID_AGENT =
		"<agent>Agent Text</agent>";

	public static final String VALID_DUPLICATE_AGENT =
			"<agent>Agent Text</agent>";

///////////
	public static final String VALID_ABSTRACT =
		"<abstract>Abstract text</abstract>";
	
	public static final String VALID_PURPOSE =
		"<purpose>Purpose text</purpose>";
}
