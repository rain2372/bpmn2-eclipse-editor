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
package org.jboss.bpmn2.editor.core.features.lane;

import java.io.IOException;

import org.eclipse.bpmn2.Lane;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.jboss.bpmn2.editor.core.Activator;
import org.jboss.bpmn2.editor.core.ModelHandler;
import org.jboss.bpmn2.editor.core.utils.FeatureSupport;

public class MoveFromLaneToDiagramFeature extends MoveLaneFeature {

	public MoveFromLaneToDiagramFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canMoveShape(IMoveShapeContext context) {
		return true;
	}

	@Override
	protected void internalMove(IMoveShapeContext context) {
		modifyModelStructure(context);
		FeatureSupport.redraw(context.getSourceContainer());
	}

	private void modifyModelStructure(IMoveShapeContext context) {
		Lane parentLane = (Lane) getBusinessObjectForPictogramElement(context.getSourceContainer());
		Lane movedLane = (Lane) getBusinessObjectForPictogramElement(context.getShape());
		parentLane.getChildLaneSet().getLanes().remove(movedLane);
		try {
			ModelHandler mh = ModelHandler.getInstance(getDiagram());
			mh.laneToTop(movedLane);
		} catch (IOException e) {
			Activator.logError(e);
		}
	}
}