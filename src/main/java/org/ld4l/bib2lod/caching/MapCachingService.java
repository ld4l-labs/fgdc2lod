package org.ld4l.bib2lod.caching;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Holds maps for caching data.
 * 
 * @author dan179
 */
public class MapCachingService implements CachingService {
	
	private Map<MapType, Map<String,String>> mapOfMaps;

    private static final Logger LOGGER = LogManager.getLogger();

	public MapCachingService() {
		mapOfMaps = new HashMap<>();
		for (MapType type : MapType.values()) {
			mapOfMaps.put(type, new HashMap<String, String>());
		}
	}

	/**
	 * @see org.ld4l.bib2lod.caching.CachingService#getMap(org.ld4l.bib2lod.caching.CachingService.MapType)
	 */
	@Override
	public Map<String, String> getMap(MapType mapType) {
		return Collections.unmodifiableMap(mapOfMaps.get(mapType));
	}

	/**
	 * @see org.ld4l.bib2lod.caching.CachingService#putUri(org.ld4l.bib2lod.caching.CachingService.MapType, java.lang.String, java.lang.String)
	 */
	@Override
	public String putUri(MapType mapType, String name, String uri) throws CachingServiceException {
		if (mapType == null || name == null || uri == null) {
			throw new CachingServiceException("no parameters cannot be null");
		}
		Map<String, String> map = mapOfMaps.get(mapType);
		return map.put(name, uri);
	}
}
