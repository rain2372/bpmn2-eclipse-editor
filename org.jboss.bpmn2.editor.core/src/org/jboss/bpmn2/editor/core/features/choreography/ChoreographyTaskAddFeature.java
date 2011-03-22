/******************************************************************************* 
 * Copyright (c) 2011 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.bpmn2.editor.core.features.choreography;

import org.eclipse.bpmn2.ChoreographyActivity;
import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.jboss.bpmn2.editor.core.features.BusinessObjectUtil;

public class ChoreographyTaskAddFeature extends AbstractChoreographyAddFeature {

	public ChoreographyTaskAddFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context)
				|| BusinessObjectUtil.containsElementOfType(context.getTargetContainer(), FlowElementsContainer.class);
	}

	@Override
	protected void createTexts(ContainerShape containerShape, ChoreographyActivity choreography, int w, int h,
			int bandHeight) {
		// createText(containerShape, "Participant A", 0, 0, w, bandHeight, TOP_BAND_TEXT);
		// createText(containerShape, "Participant B", 0, h - bandHeight, w, bandHeight, BOTTOM_BAND_TEXT);
		// createText(containerShape, choreography.getName(), 0, bandHeight - 5, w, h - (2 * bandHeight) + 10,
		// BODY_BAND_TEXT);
	}
}