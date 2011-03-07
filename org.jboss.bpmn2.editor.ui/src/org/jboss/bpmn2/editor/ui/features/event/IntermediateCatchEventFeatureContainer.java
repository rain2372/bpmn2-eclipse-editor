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
package org.jboss.bpmn2.editor.ui.features.event;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.IntermediateCatchEvent;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.mm.algorithms.Ellipse;
import org.jboss.bpmn2.editor.core.ModelHandler;
import org.jboss.bpmn2.editor.core.features.event.AbstractCreateEventFeature;
import org.jboss.bpmn2.editor.core.features.event.AddEventFeature;
import org.jboss.bpmn2.editor.core.utils.GraphicsUtil;
import org.jboss.bpmn2.editor.core.utils.StyleUtil;
import org.jboss.bpmn2.editor.ui.ImageProvider;

public class IntermediateCatchEventFeatureContainer extends AbstractEventFeatureContainer {

	@Override
    public boolean canApplyTo(BaseElement element) {
	    return element instanceof IntermediateCatchEvent;
    }

	@Override
    public ICreateFeature getCreateFeature(IFeatureProvider fp) {
	    return new CreateIntermediateCatchEventFeature(fp);
    }

	@Override
    public IAddFeature getAddFeature(IFeatureProvider fp) {
	    return new AddEventFeature(fp){
	    	@Override
	    	protected void decorateEllipse(Ellipse e) {
	    		Ellipse circle = GraphicsUtil.createIntermediateEventCircle(e);
	    		circle.setForeground(manageColor(StyleUtil.CLASS_FOREGROUND));
	    	}
	    };
    }
	
	public static class CreateIntermediateCatchEventFeature extends AbstractCreateEventFeature {

		public CreateIntermediateCatchEventFeature(IFeatureProvider fp) {
			super(fp, "Catch Event", "Token remains at the event until event trigger will occur");
		}

		@Override
		protected IntermediateCatchEvent createFlowElement(ICreateContext context) {
			IntermediateCatchEvent event = ModelHandler.FACTORY.createIntermediateCatchEvent();
			event.setName("Catch");
			return event;
		}
		
		@Override
		public String getStencilImageId() {
			return ImageProvider.IMG_16_INTERMEDIATE_CATCH_EVENT;
        }
	}
}