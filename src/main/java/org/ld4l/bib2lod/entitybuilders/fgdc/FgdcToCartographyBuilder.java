/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.fgdc;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.ld4l.bib2lod.caching.CachingService;
import org.ld4l.bib2lod.caching.CachingService.CachingServiceException;
import org.ld4l.bib2lod.caching.CachingService.MapType;
import org.ld4l.bib2lod.configuration.Bib2LodObjectFactory;
import org.ld4l.bib2lod.csv.fgdc.IsoTopicConcordanceBean;
import org.ld4l.bib2lod.csv.fgdc.IsoTopicConcordanceManager;
import org.ld4l.bib2lod.csv.fgdc.PlaceKeyConcordanceBean;
import org.ld4l.bib2lod.csv.fgdc.PlaceKeyConcordanceManager;
import org.ld4l.bib2lod.csv.fgdc.UriLabelConcordanceBean;
import org.ld4l.bib2lod.csv.fgdc.UriLabelConcordanceManager;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.ontology.OwlThingType;
import org.ld4l.bib2lod.ontology.fgdc.CartographySubType;
import org.ld4l.bib2lod.ontology.fgdc.CartographyType;
import org.ld4l.bib2lod.ontology.fgdc.FgdcObjectProp;
import org.ld4l.bib2lod.ontology.fgdc.GeographicCoverageType;
import org.ld4l.bib2lod.ontology.fgdc.HarvardType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAnnotationType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lConceptType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.fgdc.BaseFgdcField;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcKeywordsField;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcPlaceField;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcTextField;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcThemeField;

/**
 * Builds a Cartography individual from a Record.
 */
public class FgdcToCartographyBuilder extends FgdcToLd4lEntityBuilder {
    
    private FgdcRecord record;
    private Entity work;
    private IsoTopicConcordanceManager isoTopicConcordanceManager;
    private UriLabelConcordanceManager fastConcordanceManager;
    private PlaceKeyConcordanceManager placeKeyConcordanceManager;
    private CachingService cachingService;
    
    private static final String GENRE_FORM_URI = "http://id.loc.gov/authorities/genreForms/gf2011026297";
    private static final String ENG_LANGUAGE_URI = "http://lexvo.org/id/iso639-3/eng";
    
    public FgdcToCartographyBuilder() throws EntityBuilderException {
    	String concordanceManagerName = "";
    	try {
    		concordanceManagerName = IsoTopicConcordanceManager.class.getSimpleName();
			this.isoTopicConcordanceManager = new IsoTopicConcordanceManager();
			concordanceManagerName = UriLabelConcordanceManager.class.getSimpleName();
			this.fastConcordanceManager = UriLabelConcordanceManager.getFastConcordanceManager();
			concordanceManagerName = PlaceKeyConcordanceManager.class.getSimpleName();
			this.placeKeyConcordanceManager = new PlaceKeyConcordanceManager();
		} catch ( FileNotFoundException e) {
			throw new EntityBuilderException("Could not instantiate " + concordanceManagerName, e);
		}
    }
  
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
    	this.cachingService = CachingService.instance();

    	this.record = (FgdcRecord) params.getRecord();
        if (record == null) {
            throw new EntityBuilderException(
                    "A FgdcRecord is required to build a Cartography.");
        }
        
        this.work = new Entity(CartographyType.DATASET);
        this.work.addType(Ld4lWorkType.CARTOGRAPHY); // (This is defined as two different types.)
        
        buildTitle();
        buildInstances();
        buildOriginatorActivity();
        addIdentifiers();
        buildGeometry();
        buildAnnotations();
        addKeywords();
        buildSubtype();
        
        // add genericForm
        this.work.addExternalRelationship(Ld4lObjectProp.GENRE_FORM, FgdcToCartographyBuilder.GENRE_FORM_URI);
        // add language
        this.work.addExternalRelationship(Ld4lObjectProp.HAS_LANGUAGE, ENG_LANGUAGE_URI);

        return this.work;
    }
    
    private void buildTitle() throws EntityBuilderException { 
        
        EntityBuilder builder = getBuilder(Ld4lTitleType.TITLE);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(work);
        builder.build(params);
    }
    
    private void buildInstances() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lInstanceType.INSTANCE);

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(work);        
        builder.build(params);
    }
    
    private void buildGeometry() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(CartographyType.GEOMETRY);

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(work);        
        builder.build(params);
    }
    
    private void addIdentifiers() throws EntityBuilderException {

    	Map<String, String> mapToUriCache = cachingService.getMap(MapType.NAMES_TO_URI);

    	// create and add HGLD identifier
    	String layerId = record.getLayerId(); // should have been validated as non-null
    	// find out which institution
    	FgdcToLd4lEntityBuilderFactory factory = (FgdcToLd4lEntityBuilderFactory) Bib2LodObjectFactory.getFactory()
                .instanceForInterface(EntityBuilderFactory.class);
    	Entity layerIdentifier = factory.getInstitutionEntity();
        layerIdentifier.addAttribute(Ld4lDatatypeProp.VALUE, layerId);
        work.addRelationship(Ld4lObjectProp.IDENTIFIED_BY, layerIdentifier);
    	
    	// create and add Hollis number identifier
		String hollisNumber = record.getHollisNumber(); // could be null or empty
		if ( !StringUtils.isEmpty(hollisNumber)) {
			String cachedHollisUri = mapToUriCache.get(hollisNumber);
			Entity hollisIdentifier = new Entity(HarvardType.HOLLIS_NUMBER);
			hollisIdentifier.addAttribute(Ld4lDatatypeProp.VALUE, hollisNumber);
	        if (cachedHollisUri == null) {
	        	hollisIdentifier.buildResource();
	        	String uri = hollisIdentifier.getResource().getURI();
	        	try {
					cachingService.putUri(MapType.NAMES_TO_URI, hollisNumber, uri);
				} catch (CachingServiceException e) {
					throw new EntityBuilderException(e);
				}
	        } else {
	        	hollisIdentifier.buildResource(cachedHollisUri);
	        }
			work.addRelationship(Ld4lObjectProp.IDENTIFIED_BY, hollisIdentifier);
		}
    }
    
    private void buildOriginatorActivity() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lActivityType.ACTIVITY);
		List<FgdcTextField> origins = record.getCiteinfoField().getOrigins();
		if (origins.size() > 0) {
			for (BaseFgdcField originField : origins) {
				BuildParams params = new BuildParams()
					.setRecord(record)
					.setField(originField)
	    			.setParent(work)
	    			.setType(Ld4lActivityType.ORIGINATOR_ACTIVITY);
				builder.build(params);
			}
		}
    }
    
    private void buildAnnotations() throws EntityBuilderException {
        EntityBuilder builder = getBuilder(Ld4lAnnotationType.ANNOTATION);
        
        BaseFgdcField field = record.getPurposeField();
        if (field != null) {
        	BuildParams params = new BuildParams()
        			.setRecord(record)
        			.setField(field)
        			.setParent(work);
        	builder.build(params);
        }

        field = record.getAbstractField();
        if (field != null) {
        	BuildParams params = new BuildParams()
        			.setRecord(record)
        			.setField(field)
        			.setParent(work);
        	builder.build(params);
        }
    }
    
    private void buildSubtype() throws EntityBuilderException {
        
    	if (record.getCiteinfoField() != null) {
    		EntityBuilder builder = getBuilder(CartographySubType.CART_SUBTYPE);
    		
    		BuildParams params = new BuildParams()
    				.setRecord(record)
    				.setParent(work);        
    		builder.build(params);
    	}
    }
    
    private void addKeywords() throws EntityBuilderException {
        
        FgdcKeywordsField keywordsField = record.getKeywordsField();
                
        // add theme keywords
        if (keywordsField != null && keywordsField.getThemes().size() > 0) {
        	try {
				addThemes(keywordsField);
			} catch (CachingServiceException e) {
				throw new EntityBuilderException(e);
			}
        }
        
        // add place keywords
        if (keywordsField != null && keywordsField.getPlaces().size() > 0) {
        	try {
				addPlaces(keywordsField);
			} catch (CachingServiceException e) {
				throw new EntityBuilderException(e);
			}
        }
    }
    
    private void addThemes(FgdcKeywordsField keywordsField) throws CachingServiceException {
    	
        if (keywordsField != null && keywordsField.getThemes().size() > 0) {
        	for (FgdcThemeField themeField : keywordsField.getThemes()) {
        		
                Entity sourceEntity = new Entity();
                String themeKtFieldText = themeField.getThemeKt().getTextValue();
                // check each themekey against concordance file
                for (FgdcTextField themeKey : themeField.getThemeKeys()) {
                	String themeKeyFieldText = themeKey.getTextValue();
                	IsoTopicConcordanceBean isoTopicConcordanceBean = null;
                	UriLabelConcordanceBean fastConcordanceBean = null;
                	String concordanceUri = null;
                	// check to see if themekt is of the type that requires a concordance file check
                	if (themeKtFieldText.startsWith(MapType.ISO_THEME_THESAURUS_KEYWORD_TO_URI.marker())) { // using startsWith() since variants on Harvard & Stanford data
                		// expect only one
                		isoTopicConcordanceBean = isoTopicConcordanceManager.getConcordanceEntry(themeKeyFieldText);
                    	if (isoTopicConcordanceBean != null) {
                    		concordanceUri = isoTopicConcordanceBean.getUri();
                    	}
                	} else if (MapType.LCSH_THEME_THESAURUS_KEYWORD_TO_URI.marker().equalsIgnoreCase(themeKtFieldText)) {
                		// expect only one
                		fastConcordanceBean = fastConcordanceManager.getConcordanceEntry(themeKeyFieldText);
                    	if (fastConcordanceBean != null) {
                    		concordanceUri = fastConcordanceBean.getUri();
                    	}
                	}
                	
                	if (concordanceUri != null) {
                		// for concordance match add relationship to corresponding external URI along with label
    	    	        Entity topicEntity = new Entity(OwlThingType.THING);
    	    	        topicEntity.addAttribute(Ld4lDatatypeProp.LABEL, themeKeyFieldText);
    	    	        topicEntity.buildResource(concordanceUri);
    	    	        work.addRelationship(Ld4lObjectProp.HAS_SUBJECT, topicEntity);
                	} else {
                		// check cache for thesaurus URI already created
                		Map<String, String> keywordToUri;
                		CachingService.MapType cacheMapType;
                		
                		if (MapType.ISO_THEME_THESAURUS_KEYWORD_TO_URI.marker().equalsIgnoreCase(themeKtFieldText)) {
                			cacheMapType = MapType.ISO_THEME_THESAURUS_KEYWORD_TO_URI;
                			keywordToUri = cachingService.getMap(MapType.ISO_THEME_THESAURUS_KEYWORD_TO_URI);
                		} else if (MapType.LCSH_THEME_THESAURUS_KEYWORD_TO_URI.marker().equalsIgnoreCase(themeKtFieldText)) {
                			cacheMapType = MapType.LCSH_THEME_THESAURUS_KEYWORD_TO_URI;
                			keywordToUri = cachingService.getMap(MapType.LCSH_THEME_THESAURUS_KEYWORD_TO_URI);
                		}  else {
                			// If the 'themekt' value is neither of the above, use another catch-all cache
                			cacheMapType = MapType.OTHER_THEME_THESAURUS_KEYWORK_TO_URI;
                			keywordToUri = cachingService.getMap(MapType.OTHER_THEME_THESAURUS_KEYWORK_TO_URI);
                		}
                		
                		
                		// if no concordance, create a Concept for each key, add source,
                		// and add each to Work (Cartography)
            			sourceEntity.addAttribute(Ld4lDatatypeProp.LABEL, themeKtFieldText);
            			String editorialNote = "Harvard FGDC themekey derived from: " + themeKtFieldText;
            			sourceEntity.addAttribute(Ld4lDatatypeProp.EDITORIAL_NOTE, editorialNote);
            			
            			// Look for URI in cache for 'themekt' value. If none, create and store in cache
            			String cachedSourceUri = keywordToUri.get(themeKtFieldText);
            			if (cachedSourceUri != null) {
            				sourceEntity.buildResource(cachedSourceUri);
            			} else {
            				sourceEntity.buildResource();
            				String sourceUri = sourceEntity.getResource().getURI();
            				cachingService.putUri(cacheMapType, themeKtFieldText, sourceUri);
            			}

            			Entity concept = new Entity(Ld4lConceptType.defaultType());
            			concept.addAttribute(Ld4lDatatypeProp.LABEL, themeKeyFieldText);
            			concept.addRelationship(Ld4lObjectProp.HAS_SOURCE, sourceEntity);

            			// also store 'themekey' URI in local cache
            			String cachedConceptUri = keywordToUri.get(themeKeyFieldText);
            			if (cachedConceptUri != null) {
            				concept.buildResource(cachedConceptUri);
            			} else {
            				concept.buildResource();
            				String conceptUri = concept.getResource().getURI();
            				cachingService.putUri(cacheMapType, themeKeyFieldText, conceptUri);
            			}

            			work.addRelationship(Ld4lObjectProp.HAS_SUBJECT, concept);
                	}
                }
        	}
        }
    }
    
    private void addPlaces(FgdcKeywordsField keywordsField) throws CachingServiceException {
    	
        if (keywordsField != null && keywordsField.getPlaces().size() > 0) {
        	String layerId = record.getLayerId();
    		List<PlaceKeyConcordanceBean> beans = placeKeyConcordanceManager.getConcordanceEntries(layerId);
    		if (beans.size() < 1) {
    			return;
    		}
    		for (FgdcPlaceField placeField : keywordsField.getPlaces()) {
        		String placeKtFieldText = placeField.getPlaceKt().getTextValue();
    			for (PlaceKeyConcordanceBean bean : beans) {
    				String source = bean.getSource();
    				String uri = bean.getUri();
    				// need to compare 'placekt' value to concordance record source value
    				if (bean.getSource().equals(placeKtFieldText)) {
    					if ( !StringUtils.isEmpty(uri)) {
    			    		Entity geographicCoverageEntity = new Entity(OwlThingType.THING);
    			    		geographicCoverageEntity.addAttribute(Ld4lDatatypeProp.LABEL, bean.getLabel());
    			    		geographicCoverageEntity.buildResource(uri);
    						work.addRelationship(FgdcObjectProp.GEOGRAPHIC_COVERAGE, geographicCoverageEntity);
    					} else {

    		        		Map<String, String> keywordToUri;
                    		CachingService.MapType cacheMapType;
    		        		if (MapType.GNIS_PLACE_THESAURUS_KEYWORD_TO_URI.marker().equalsIgnoreCase(source)) {
    		        			cacheMapType = MapType.GNIS_PLACE_THESAURUS_KEYWORD_TO_URI;
    		        			keywordToUri = cachingService.getMap(MapType.GNIS_PLACE_THESAURUS_KEYWORD_TO_URI);
    		        		} else if (MapType.LCNA_PLACE_THESAURUS_KEYWORD_TO_URI.marker().equalsIgnoreCase(source)) {
    		        			cacheMapType = MapType.LCNA_PLACE_THESAURUS_KEYWORD_TO_URI;
    		        			keywordToUri = cachingService.getMap(MapType.LCNA_PLACE_THESAURUS_KEYWORD_TO_URI);
    		        		} else {
    		        			// If the 'placekt' value is neither of the above, do nothing.
    		        			continue;
    		        		}
    		        		
    		        		Entity sourceEntity = new Entity();
	    					sourceEntity.addAttribute(Ld4lDatatypeProp.LABEL, source);
	    					String editorialNote = "Harvard FGDC placekey derived from: " + source;
	    					sourceEntity.addAttribute(Ld4lDatatypeProp.EDITORIAL_NOTE, editorialNote);

	    					// create and  save local URI for concordance 'source' if it doesn't already exist
                			String cachedSourceUri = keywordToUri.get(source);
                			if (cachedSourceUri != null) {
                				sourceEntity.buildResource(cachedSourceUri);
                			} else {
                				sourceEntity.buildResource();
                				String sourceUri = sourceEntity.getResource().getURI();
                				cachingService.putUri(cacheMapType, source, sourceUri);
                			}
    					
	    					Entity geographicCoverage = new Entity(GeographicCoverageType.GEOGRAPHIC_COVERAGE);
	    					String concordanceLabel = bean.getLabel();
	    					geographicCoverage.addAttribute(Ld4lDatatypeProp.LABEL, concordanceLabel);
	    					geographicCoverage.addRelationship(Ld4lObjectProp.HAS_SOURCE, sourceEntity);
	    					
	    					String cachedPlaceLabelUri = keywordToUri.get(concordanceLabel);
	    					if (cachedPlaceLabelUri != null) {
	    						geographicCoverage.buildResource(cachedPlaceLabelUri);
	    					} else {
	    						geographicCoverage.buildResource();
	    						String placeLabelUri = geographicCoverage.getResource().getURI();
	    						cachingService.putUri(cacheMapType, concordanceLabel, placeLabelUri);
	    					}
	    					
	    					work.addRelationship(FgdcObjectProp.GEOGRAPHIC_COVERAGE, geographicCoverage);
    					}
    				}
    			}
    		}
        }
    }
        
}
