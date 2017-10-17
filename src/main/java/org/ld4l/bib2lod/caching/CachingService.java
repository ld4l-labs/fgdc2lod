/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.caching;

import java.util.Map;

import org.ld4l.bib2lod.configuration.Bib2LodObjectFactory;

/**
 * A service for caching collections of values.
 */
public interface CachingService {
	
    /**
     * Indicates a problem in the operation of the CachingService.
     */
    public static class CachingServiceException extends Exception {
        private static final long serialVersionUID = 1L;

        public CachingServiceException(String message, Throwable cause) {
            super(message, cause);
        }

        public CachingServiceException(String message) {
            super(message);
        }

        public CachingServiceException(Throwable cause) {
            super(cause);
        }
    }
    
    public static enum MapType {
    	ISO_THEME_THESAURUS_KEYWORD_TO_URI("ISO 19115 Topic Category"),
    	LCSH_THEME_THESAURUS_KEYWORD_TO_URI("LCSH"),
    	OTHER_THEME_THESAURUS_KEYWORK_TO_URI(""),
    	GNIS_PLACE_THESAURUS_KEYWORD_TO_URI("GNIS"),
    	LCNA_PLACE_THESAURUS_KEYWORD_TO_URI("LCNA"),
    	NAMES_TO_URI("");
    	
    	private String marker;
    	
    	private MapType(String marker) {
    		this.marker = marker;
    	}
    	
    	public String marker() {
    		return marker;
    	}
    }

    /**
     * Factory method
     */
    static CachingService instance() {
        return Bib2LodObjectFactory.getFactory()
                .instanceForInterface(CachingService.class);
    }

    /**
     * Mapping of key to value.
     * 
     * @return Unmodifiable map.
     */
    Map<String, String> getMap(MapType mapType);
    
    /**
     * Saves or updates a value for the given map type and key.
     * 
     * @param mapType The specific map data to cache.
     * @param name The key for storing in map.
     * @param uri The value for storing in map.
     * @return Returns the value being replaced should the key already map to a value.
     * @throws CachingServiceException if any of the parameters are <code>null</code>.
     */
    String putUri(MapType mapType, String name, String uri) throws CachingServiceException;

}
