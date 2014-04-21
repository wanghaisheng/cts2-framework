/*
 * Copyright: (c) 2004-2011 Mayo Foundation for Medical Education and 
 * Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 *
 * Except as contained in the copyright notice above, or as used to identify 
 * MFMER as the author of this software, the trade names, trademarks, service
 * marks, or product names of the copyright holder shall not be used in
 * advertising, promotion or otherwise in connection with this software without
 * prior written authorization of the copyright holder.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.mayo.cts2.framework.model.util;

import edu.mayo.cts2.framework.model.codesystemversion.CodeSystemVersionCatalogEntry;
import edu.mayo.cts2.framework.model.core.*;
import edu.mayo.cts2.framework.model.entity.Designation;
import edu.mayo.cts2.framework.model.entity.EntityDescription;
import edu.mayo.cts2.framework.model.entity.EntityDescriptionBase;
import edu.mayo.cts2.framework.model.entity.types.DesignationRole;
import edu.mayo.cts2.framework.model.service.core.EntityNameOrURI;
import edu.mayo.cts2.framework.model.service.core.NameOrURI;
import edu.mayo.cts2.framework.model.updates.ChangeableResource;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * The Class RestModelUtils.
 *
 * @author <a href="mailto:kevin.peterson@mayo.edu">Kevin Peterson</a>
 */
public class ModelUtils {
	
	//TODO: This is probably an overly simplistic check
	private static Pattern URI_PATTERN = Pattern.compile(
			"(urn:[a-zA-Z]+:.*)|([a-zA-Z]+://.*)");
 
	/**
	 * Instantiates a new rest model utils.
	 */
	private ModelUtils(){
		super();
	}
	
	/**
	 * To ts any type.
	 *
	 * @param string the string
	 * @return the ts any type
	 */
	public static TsAnyType toTsAnyType(String string){
		TsAnyType ts = new TsAnyType();
		ts.setContent(string);
		
		return ts;
	}
	
	/**
	 * Gets the resource synopsis value.
	 *
	 * @param entry the entry
	 * @return the resource synopsis value
	 */
	public static String getResourceSynopsisValue(AbstractResourceDescription entry){
		EntryDescription synopsis = entry.getResourceSynopsis();
		if(synopsis != null){
			TsAnyType value = synopsis.getValue();
			
			if(value != null){
				return value.getContent();
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the resource synopsis value.
	 *
	 * @param entry the entry
	 * @return the resource synopsis value
	 */
	public static String getResourceSynopsisValue(ResourceDescriptionDirectoryEntry entry){
		EntryDescription synopsis = entry.getResourceSynopsis();
		if(synopsis != null){
			TsAnyType value = synopsis.getValue();
			
			if(value != null){
				return value.getContent();
			}
		}
		
		return null;
	}
	
	/**
	 * Creates the scoped entity name.
	 *
	 * @param name the name
	 * @param namespace the namespace
	 * @return the scoped entity name
	 */
	public static ScopedEntityName createScopedEntityName(String name, String namespace){
		ScopedEntityName scopedName = new ScopedEntityName();
		scopedName.setName(name);
		scopedName.setNamespace(namespace);
		
		return scopedName;
	}
	
	/**
	 * Creates the opaque data.
	 *
	 * @param text the text
	 * @return the opaque data
	 */
	public static OpaqueData createOpaqueData(String text){
		OpaqueData data = new OpaqueData();
		data.setValue(toTsAnyType(text));
		
		return data;
	}
	
	/**
	 * Gets the entity.
	 *
	 * @param entityDescription the entity description
	 * @return the entity
	 */
	public static EntityDescriptionBase getEntity(EntityDescription entityDescription){
		return (EntityDescriptionBase) entityDescription.getChoiceValue();
	}
	
	/**
	 * Sets the entity.
	 *
	 * @param wrapper the wrapper
	 * @param entityDescription the entity description
	 */
	public static void setEntity(EntityDescription wrapper,
			EntityDescriptionBase entityDescription) {
		try {
			for(Method method : EntityDescription.class.getDeclaredMethods()){
				if(method.getName().startsWith("set") && 
						method.getParameterTypes().length == 1 &&
						method.getParameterTypes()[0].equals(entityDescription.getClass())){
					method.invoke(wrapper, entityDescription);
				}
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	/**
	 * To entity description.
	 *
	 * @param entityDescriptionBase the entity description base
	 * @return the entity description
	 */
	public static EntityDescription toEntityDescription(
			EntityDescriptionBase entityDescriptionBase) {
		EntityDescription wrapper = new EntityDescription();
		
		setEntity(wrapper, entityDescriptionBase);
		
		return wrapper;
	}
	
	/**
	 * Gets the preferred designation.
	 *
	 * @param entity the entity
	 * @return the preferred designation
	 */
	public static Designation getPreferredDesignation(EntityDescriptionBase entity){
		if(entity.getDesignationCount() == 1){
			return entity.getDesignation(0);
		}
		
		for(Designation designation : entity.getDesignation()){
			DesignationRole role = designation.getDesignationRole();
			if(role != null && role.equals(DesignationRole.PREFERRED)){
				return designation;
			}
		}
		
		return null;
	}

	/**
	 * Gets the code system name of code system version.
	 *
	 * @param resource the resource
	 * @return the code system name of code system version
	 */
	public static String getCodeSystemNameOfCodeSystemVersion(
			CodeSystemVersionCatalogEntry resource) {
		return resource.getVersionOf().getContent();
	}
	
	public static boolean isValidUri(String uriCandidate){
		return URI_PATTERN.matcher(uriCandidate).matches();	
	}
	
	public static NameOrURI nameOrUriFromEither(String nameOrUri) {
		if(StringUtils.isBlank(nameOrUri)){
			return null;
		}

		NameOrURI n;
		if(isValidUri(nameOrUri)){
			n = nameOrUriFromUri(nameOrUri);
		} else {
			n = nameOrUriFromName(nameOrUri);
		}
		return n;
	}

	public static NameOrURI nameOrUriFromName(String name) {
		if(StringUtils.isBlank(name)){
			return null;
		}
		
		NameOrURI nameOrUri = new NameOrURI();
		nameOrUri.setName(name);
		
		return nameOrUri;
	}
	
	public static EntityNameOrURI entityNameOrUriFromName(ScopedEntityName name) {
		if(name == null){
			return null;
		}
		
		EntityNameOrURI nameOrUri = new EntityNameOrURI();
		nameOrUri.setEntityName(name);
		
		return nameOrUri;
	}
	
	public static EntityNameOrURI entityNameOrUriFromUri(String uri) {
		EntityNameOrURI nameOrUri = new EntityNameOrURI();
		nameOrUri.setUri(uri);
		
		return nameOrUri;
	}
	
	public static ChangeableResource createChangeableResource(IsChangeable changeable) {
		ChangeableResource choice = new ChangeableResource();
		
		for(Field field : choice.getClass().getDeclaredFields()){
			if(field.getType().equals(changeable.getClass())
					||
					field.getName().equals("_choiceValue")){
				field.setAccessible(true);
				try {
					field.set(choice, changeable);
				} catch (IllegalArgumentException e) {
					throw new IllegalStateException(e);
				} catch (IllegalAccessException e) {
					throw new IllegalStateException(e);
				}
			}
		}
		
		return choice;
	}


	public static NameOrURI nameOrUriFromUri(String uri) {
		NameOrURI nameOrUri = new NameOrURI();
		nameOrUri.setUri(uri);
		
		return nameOrUri;
	}

    public static boolean matches(
            ComponentReference componentReference1,
            ComponentReference componentReference2){
        if(componentReference1.getAttributeReference() != null &&
                componentReference2.getAttributeReference() != null){
            return StringUtils.equals(
                    componentReference1.getAttributeReference(),
                    componentReference2.getAttributeReference());
        }
        if(componentReference1.getPropertyReference() != null &&
                componentReference2.getPropertyReference() != null){
            URIAndEntityName ref1 = componentReference1.getPropertyReference();
            URIAndEntityName ref2 = componentReference2.getPropertyReference();
            return (StringUtils.equals(ref1.getName(), ref2.getName())
                    ||
                    StringUtils.equals(ref1.getUri(), ref2.getUri()));
        }
        if(componentReference1.getSpecialReference() != null &&
                componentReference2.getSpecialReference() != null){
            return StringUtils.equals(
                    componentReference1.getSpecialReference(),
                    componentReference2.getSpecialReference());
        }

        return false;
    }

}
