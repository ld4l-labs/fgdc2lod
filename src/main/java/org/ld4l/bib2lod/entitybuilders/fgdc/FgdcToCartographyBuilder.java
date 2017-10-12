/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.fgdc;

import java.io.FileNotFoundException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.ld4l.bib2lod.csv.fgdc.IsoTopicConcordanceBean;
import org.ld4l.bib2lod.csv.fgdc.IsoTopicConcordanceManager;
import org.ld4l.bib2lod.csv.fgdc.PlaceKeyConcordanceBean;
import org.ld4l.bib2lod.csv.fgdc.PlaceKeyConcordanceManager;
import org.ld4l.bib2lod.csv.fgdc.UriLabelConcordanceBean;
import org.ld4l.bib2lod.csv.fgdc.UriLabelConcordanceManager;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
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
    
    private static final String GENRE_FORM_URI = "http://id.loc.gov/authorities/genreForms/gf2011026297";
    private static final String ENG_LANGUAGE_URI = "http://lexvo.org/id/iso639-3/eng";
    private static final String ISO_TOPIC_CATEGORY_MARKER = "ISO 19115 Topic Category";
    private static final String LCSH_MARKER = "LCSH";
    
    public FgdcToCartographyBuilder() throws EntityBuilderException {
    	String concordanceManagerName = "";
    	try {
    		concordanceManagerName = IsoTopicConcordanceManager.class.getSimpleName();
			this.isoTopicConcordanceManager = new IsoTopicConcordanceManager();
			concordanceManagerName = UriLabelConcordanceManager.class.getSimpleName();
			this.fastConcordanceManager = UriLabelConcordanceManager.getFastConcordanceManager();
			this.placeKeyConcordanceManager = new PlaceKeyConcordanceManager();
		} catch ( FileNotFoundException e) {
			throw new EntityBuilderException("Could not instantiate " + concordanceManagerName, e);
		}
    }
  
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

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
    
    private void addIdentifiers() {
    	
		Entity identifier = new Entity(HarvardType.HGLID);
		String layerId = record.getLayerId(); // should be validated as non-null
		identifier.addAttribute(Ld4lDatatypeProp.VALUE, layerId);
		work.addRelationship(Ld4lObjectProp.IDENTIFIED_BY, identifier);
    	
		identifier = new Entity(HarvardType.HOLLIS_NUMBER);
		String hollisNumber = record.getHollisNumber(); // could be null or empty
		if (hollisNumber != null && hollisNumber.length() > 0) {
			identifier.addAttribute(Ld4lDatatypeProp.VALUE, hollisNumber);
			work.addRelationship(Ld4lObjectProp.IDENTIFIED_BY, identifier);
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
        	addThemes(keywordsField);
        }
        
        // add place keywords
        if (keywordsField != null && keywordsField.getPlaces().size() > 0) {
        	addPlaces(keywordsField);
        }
    }
    
    private void addThemes(FgdcKeywordsField keywordsField) {
    	
        if (keywordsField != null && keywordsField.getThemes() != null) {
        	for (FgdcThemeField themeField : keywordsField.getThemes()) {
        		
                Entity source = new Entity();
                String themeKtFieldText = themeField.getThemeKt().getTextValue();
                // check each themekey against concordance file
                for (FgdcTextField themeKey : themeField.getThemeKeys()) {
                	IsoTopicConcordanceBean isoTopicConcordanceBean = null;
                	UriLabelConcordanceBean fastConcordanceBean = null;
                	String concordanceUri = null;
                	// check to see if themekt is of the type that requires a concordance file check
                	if (FgdcToCartographyBuilder.ISO_TOPIC_CATEGORY_MARKER.equalsIgnoreCase(themeKtFieldText)) {
                		// expect only one
                		isoTopicConcordanceBean = isoTopicConcordanceManager.getConcordanceEntry(themeKey.getTextValue());
                    	if (isoTopicConcordanceBean != null) {
                    		concordanceUri = isoTopicConcordanceBean.getUri();
                    	}
                	} else if (FgdcToCartographyBuilder.LCSH_MARKER.equalsIgnoreCase(themeKtFieldText)) {
                		fastConcordanceBean = fastConcordanceManager.getConcordanceEntry(themeKey.getTextValue());
                    	if (fastConcordanceBean != null) {
                    		concordanceUri = fastConcordanceBean.getUri();
                    	}
                	}
                	
                	if (concordanceUri != null) {
                		// for concordance match add external relationship to corresponding URI
                		work.addExternalRelationship(Ld4lObjectProp.HAS_SUBJECT, concordanceUri);
                	} else {
                		// if no concordance, create a Concept for each key, add source,
                		// and add each to Work (Cartography)
                		source.addAttribute(Ld4lDatatypeProp.LABEL, themeKtFieldText);
                		String editorialNote = "Harvard FGDC themekey derived from: " + themeKtFieldText;
                		source.addAttribute(Ld4lDatatypeProp.EDITORIAL_NOTE, editorialNote);
            			Entity concept = new Entity(Ld4lConceptType.defaultType());
            			concept.addAttribute(Ld4lDatatypeProp.LABEL, themeKey.getTextValue());
            			concept.addRelationship(Ld4lObjectProp.HAS_SOURCE, source);
            			work.addRelationship(Ld4lObjectProp.HAS_SUBJECT, concept);
                	}
                }
        	}
        }
    }
    
    private void addPlaces(FgdcKeywordsField keywordsField) {
    	
        if (keywordsField != null && keywordsField.getPlaces().size() > 0) {
        	String layerId = record.getLayerId();
        	List<PlaceKeyConcordanceBean> beans = placeKeyConcordanceManager.getConcordanceEntries(layerId);
        	if (beans.size() > 0) {
        		for (PlaceKeyConcordanceBean bean : beans) {
        			String source = bean.getSource();
        			String uri = bean.getUri();
        			if (!StringUtils.isEmpty(uri)) {
        				work.addExternalRelationship(FgdcObjectProp.GEOGRAPHIC_COVERAGE, uri);
        			} else {
         				Entity geographicCoverage = new Entity(GeographicCoverageType.GEOGRAPHIC_COVERAGE);
         				geographicCoverage.addAttribute(Ld4lDatatypeProp.LABEL, bean.getLabel());
         				Entity sourceEntity = new Entity();
         				sourceEntity.addAttribute(Ld4lDatatypeProp.LABEL, source);
            			String editorialNote = "Harvard FGDC placekey derived from: " + source;
            			sourceEntity.addAttribute(Ld4lDatatypeProp.EDITORIAL_NOTE, editorialNote);
            			geographicCoverage.addRelationship(Ld4lObjectProp.HAS_SOURCE, sourceEntity);
            			work.addRelationship(FgdcObjectProp.GEOGRAPHIC_COVERAGE, geographicCoverage);
        			}
        		}
        	} else {
        		for (FgdcPlaceField placeField : keywordsField.getPlaces()) {
        			
        			Entity source = new Entity();
        			String placeKtFieldText = placeField.getPlaceKt().getTextValue();
        			source.addAttribute(Ld4lDatatypeProp.LABEL, placeKtFieldText);
        			String editorialNote = "Harvard FGDC placekey derived from: " + placeKtFieldText;
        			source.addAttribute(Ld4lDatatypeProp.EDITORIAL_NOTE, editorialNote);
        			
        			// create a GeographicCoverage for each key and add each to Work (Cartography)
        			for (FgdcTextField key : placeField.getPlaceKeys()) {
        				Entity geographicCoverage = new Entity(GeographicCoverageType.GEOGRAPHIC_COVERAGE);
        				geographicCoverage.addAttribute(Ld4lDatatypeProp.LABEL, key.getTextValue());
        				geographicCoverage.addRelationship(Ld4lObjectProp.HAS_SOURCE, source);
        				work.addRelationship(FgdcObjectProp.GEOGRAPHIC_COVERAGE, geographicCoverage);
        			}
        		}
        	}
        }
    }
        
}
