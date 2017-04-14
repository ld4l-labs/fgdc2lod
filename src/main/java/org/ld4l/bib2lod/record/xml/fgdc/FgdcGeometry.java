/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.fgdc;

import org.w3c.dom.Element;

/**
 * Represents the bounding in a FGDC record.
 */
public class FgdcGeometry extends FgdcField {

	/**
	 * @param element
	 */
	public FgdcGeometry(Element element) {
		super(element);
	}

	/* (non-Javadoc)
	 * @see org.ld4l.bib2lod.record.RecordField#isValid()
	 */
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

}
