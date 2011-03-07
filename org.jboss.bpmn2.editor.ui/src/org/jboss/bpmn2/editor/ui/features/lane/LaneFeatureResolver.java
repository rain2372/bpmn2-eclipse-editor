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
package org.jboss.bpmn2.editor.ui.features.lane;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Lane;
import org.eclipse.bpmn2.TextAnnotation;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;
import org.jboss.bpmn2.editor.core.features.FeatureResolver;
import org.jboss.bpmn2.editor.core.features.lane.AddLaneFeature;
import org.jboss.bpmn2.editor.core.features.lane.DirectEditLaneFeature;
import org.jboss.bpmn2.editor.core.features.lane.LayoutLaneFeature;
import org.jboss.bpmn2.editor.core.features.lane.MoveLaneFeature;
import org.jboss.bpmn2.editor.core.features.lane.ResizeLaneFeature;
import org.jboss.bpmn2.editor.core.features.lane.UpdateLaneFeature;

public class LaneFeatureResolver implements FeatureResolver {

	@Override
	public List<ICreateConnectionFeature> getCreateConnectionFeatures(IFeatureProvider fp) {
		return new ArrayList<ICreateConnectionFeature>();
	}

	@Override
	public List<ICreateFeature> getCreateFeatures(IFeatureProvider fp) {
		List<ICreateFeature> list = new ArrayList<ICreateFeature>();
		list.add(new CreateLaneFeature(fp));
		return list;
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp, BaseElement e) {
		if (e instanceof Lane) {
			return new AddLaneFeature(fp);
		}
		return null;
	}

	@Override
	public IDirectEditingFeature getDirectEditingFeature(IFeatureProvider fp, BaseElement e) {
		if (e instanceof Lane) {
			return new DirectEditLaneFeature(fp);
		}
		return null;
	}

	@Override
	public ILayoutFeature getLayoutFeature(IFeatureProvider fp, BaseElement e) {
		if (e instanceof Lane) {
			return new LayoutLaneFeature(fp);
		}
		return null;
	}

	@Override
	public IUpdateFeature getUpdateFeature(IFeatureProvider fp, BaseElement e) {
		if (e instanceof Lane) {
			return new UpdateLaneFeature(fp);
		}
		return null;
	}

	@Override
	public IMoveShapeFeature getMoveFeature(IFeatureProvider fp, BaseElement e) {
		if (e instanceof Lane) {
			return new MoveLaneFeature(fp);
		}
		return null;
	}

	@Override
	public IResizeShapeFeature getResizeFeature(IFeatureProvider fp, BaseElement e) {
		if (e instanceof Lane) {
			return new ResizeLaneFeature(fp);
		}
		return null;
	}

	@Override
	public IDeleteFeature getDeleteFeature(IFeatureProvider fp, BaseElement e) {
		if (e instanceof TextAnnotation) {
			return new DefaultDeleteFeature(fp);
		}
		return null;
	}
}
