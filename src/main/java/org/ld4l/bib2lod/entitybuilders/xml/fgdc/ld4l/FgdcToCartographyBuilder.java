/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.fgdc.ld4l;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import org.ld4l.bib2lod.csv.IsoTopicConcordanceBean;
import org.ld4l.bib2lod.csv.IsoTopicConcordanceManager;
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
import org.ld4l.bib2lod.record.xml.fgdc.FgdcField;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcKeywordsField;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcPlaceField;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcRecord;
import org.ld4l.bib2lod.record.xml.fgdc.FgdcThemeField;

/**
 * Builds an Cartography individual from a Record.
 */
public class FgdcToCartographyBuilder extends FgdcToLd4lEntityBuilder {
    
    private FgdcRecord record;
    private Entity work;
    private IsoTopicConcordanceManager concordanceManager;
    
    private static final String GENRE_FORM_URI = "http://id.loc.gov/authorities/genreForms/gf2011026297";
    private static final String ENG_LANGUAGE_URI = "http://lexvo.org/id/iso639-3/eng";
    private static final String ISO_TOPIC_CATEGORY_MARKER = "ISO 19115 Topic Category";
  
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

    	try {
			this.concordanceManager = new IsoTopicConcordanceManager();
		} catch ( URISyntaxException | IOException e) {
			throw new EntityBuilderException("Could not instantiate IsoTopicConcordanceManager", e);
		}

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
        this.work.addExternalRelationship(FgdcObjectProp.GENRE_FORM, FgdcToCartographyBuilder.GENRE_FORM_URI);
        // add language
        this.work.addExternalRelationship(Ld4lObjectProp.HAS_LANGUAGE, ENG_LANGUAGE_URI);

        return this.work;
    }
    
    private void buildTitle() throws EntityBuilderException { 
        
        EntityBuilder builder = getBuilder(Ld4lTitleType.class);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setRelatedEntity(work);
        builder.build(params);
    }
    
    private void buildInstances() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lInstanceType.class);

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setRelatedEntity(work);        
        builder.build(params);
    }
    
    private void buildGeometry() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(CartographyType.class);

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setRelatedEntity(work);        
        builder.build(params);
    }
    
    private void addIdentifiers() {
    	
		Entity identifier = new Entity(HarvardType.HGLID);
		String layerId = record.getLayerId(); // should be validated as non-null
		identifier.addAttribute(Ld4lDatatypeProp.VALUE, layerId);
		work.addRelationship(Ld4lObjectProp.IDENTIFIED_BY, identifier);
    }
    
    private void buildOriginatorActivity() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lActivityType.class);
    	BuildParams params = new BuildParams()
    			.setRecord(record)     
    			.setRelatedEntity(work)
    			.setType(Ld4lActivityType.ORIGINATOR_ACTIVITY);
    	builder.build(params);
    }
    
    private void buildAnnotations() throws EntityBuilderException {
        EntityBuilder builder = getBuilder(Ld4lAnnotationType.class);
        
        FgdcField field = record.getPurposeField();
        if (field != null) {
        	BuildParams params = new BuildParams()
        			.setRecord(record)
        			.setField(field)
        			.setRelatedEntity(work);
        	builder.build(params);
        }

        field = record.getAbstractField();
        if (field != null) {
        	BuildParams params = new BuildParams()
        			.setRecord(record)
        			.setField(field)
        			.setRelatedEntity(work);
        	builder.build(params);
        }
    }
    
    private void buildSubtype() throws EntityBuilderException {
        
    	if (record.getCiteinfoField() != null) {
    		EntityBuilder builder = getBuilder(CartographySubType.class);
    		
    		BuildParams params = new BuildParams()
    				.setRecord(record)
    				.setRelatedEntity(work);        
    		builder.build(params);
    	}
    }
    
    // Logic too complex to add a Builder class
    private void addKeywords() throws EntityBuilderException {
        
        FgdcKeywordsField keywordsField = record.getKeywordsField();
        
        // First check for theme keywords
        if (keywordsField != null && keywordsField.getThemes() != null) {
        	for (FgdcThemeField themeField : keywordsField.getThemes()) {
        		
                Entity source = new Entity();
                String themeKtFieldText = themeField.getThemeKt().getTextValue();
                // check each themekey against concordance file
                for (FgdcField themeKey : themeField.getThemeKeys()) {
                	IsoTopicConcordanceBean concordanceBean = null;
                	// check to see if themekt is of the type that requires a concordance file check
                	if (FgdcToCartographyBuilder.ISO_TOPIC_CATEGORY_MARKER.equalsIgnoreCase(themeKtFieldText)) {
                		// expect only one
                		concordanceBean = concordanceManager.getConcordanceEntry(themeKey.getTextValue());
                	}
                	
                	if (concordanceBean != null) {
                		// for concordance match add external relationship to corresponding URI
                		String uri = concordanceBean.getUri();
                		work.addExternalRelationship(Ld4lObjectProp.HAS_SUBJECT, uri);
                	} else {
                		// if no concordance, create a Concept for each key, add source,
                		// and add each to Work (Cartography)
                		source.addAttribute(Ld4lDatatypeProp.LABEL, themeKtFieldText);
                		String editorialNote = "Harvard FGDC themekey derived from " + themeKtFieldText;
                		source.addAttribute(Ld4lDatatypeProp.EDITORIAL_NOTE, editorialNote);
            			Entity concept = new Entity(Ld4lConceptType.superClass());
            			concept.addAttribute(Ld4lDatatypeProp.LABEL, themeKey.getTextValue());
            			concept.addRelationship(FgdcObjectProp.HAS_SOURCE, source);
            			work.addRelationship(Ld4lObjectProp.HAS_SUBJECT, concept);
                	}
                }
        	}
        }
        
        // Next check for place keywords
        if (keywordsField != null && keywordsField.getPlaces() != null) {
        	// field.getPlaces() could be empty
        	// if not, already validation that each place will have at least one
        	// each of 'placekt' and 'placekey'
        	for (FgdcPlaceField placeField : keywordsField.getPlaces()) {
        		
                Entity source = new Entity();
                String placeKtFieldText = placeField.getPlaceKt().getTextValue();
                source.addAttribute(Ld4lDatatypeProp.LABEL, placeKtFieldText);
                String editorialNote = "Harvard FGDC placekey derived from " + placeKtFieldText;
                source.addAttribute(Ld4lDatatypeProp.EDITORIAL_NOTE, editorialNote);
                
                // create a GeographicCoverage for each key and add each to Work (Cartography)
                for (FgdcField key : placeField.getPlaceKeys()) {
                	Entity geographicCoverage = new Entity(GeographicCoverageType.superClass());
                	geographicCoverage.addAttribute(Ld4lDatatypeProp.LABEL, key.getTextValue());
                	geographicCoverage.addRelationship(FgdcObjectProp.HAS_SOURCE, source);
                	work.addRelationship(FgdcObjectProp.GEOGRAPHIC_COVERAGE, geographicCoverage);
                }
        	}
        }
    }
        
}
