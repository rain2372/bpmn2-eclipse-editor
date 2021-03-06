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
package org.jboss.bpmn2.editor.ui.features.event.definitions;

import org.eclipse.bpmn2.BoundaryEvent;
import org.eclipse.bpmn2.CatchEvent;
import org.eclipse.bpmn2.CompensateEventDefinition;
import org.eclipse.bpmn2.Event;
import org.eclipse.bpmn2.EventDefinition;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.SubProcess;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.jboss.bpmn2.editor.core.ModelHandler;
import org.jboss.bpmn2.editor.core.features.event.definitions.CreateEventDefinition;
import org.jboss.bpmn2.editor.core.features.event.definitions.DecorationAlgorithm;
import org.jboss.bpmn2.editor.core.features.event.definitions.EventDefinitionFeatureContainer;
import org.jboss.bpmn2.editor.core.utils.GraphicsUtil;
import org.jboss.bpmn2.editor.core.utils.GraphicsUtil.Compensation;
import org.jboss.bpmn2.editor.core.utils.StyleUtil;
import org.jboss.bpmn2.editor.ui.ImageProvider;

public class CompensateEventDefinitionContainer extends EventDefinitionFeatureContainer {

	@Override
	public boolean canApplyTo(Object o) {
		return super.canApplyTo(o) && o instanceof CompensateEventDefinition;
	}

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new CreateCompensateEventDefinition(fp);
	}

	@Override
	protected Shape drawForStart(DecorationAlgorithm algorithm, ContainerShape shape) {
		return draw(algorithm, shape);
	}

	@Override
	protected Shape drawForEnd(DecorationAlgorithm algorithm, ContainerShape shape) {
		return drawFilled(algorithm, shape);
	}

	@Override
	protected Shape drawForThrow(DecorationAlgorithm algorithm, ContainerShape shape) {
		return drawFilled(algorithm, shape);
	}

	@Override
	protected Shape drawForCatch(DecorationAlgorithm algorithm, ContainerShape shape) {
		return null; // NOT ALLOWED ACCORDING TO SPEC
	}

	@Override
	protected Shape drawForBoundary(DecorationAlgorithm algorithm, ContainerShape shape) {
		return draw(algorithm, shape);
	}

	private Shape draw(DecorationAlgorithm algorithm, ContainerShape shape) {
		Shape compensateShape = Graphiti.getPeService().createShape(shape, false);
		Compensation compensation = GraphicsUtil.createEventCompensation(compensateShape);
		compensation.arrow1.setFilled(false);
		compensation.arrow1.setForeground(algorithm.manageColor(StyleUtil.CLASS_FOREGROUND));
		compensation.arrow2.setFilled(false);
		compensation.arrow2.setForeground(algorithm.manageColor(StyleUtil.CLASS_FOREGROUND));
		return compensateShape;
	}

	private Shape drawFilled(DecorationAlgorithm algorithm, ContainerShape shape) {
		Shape compensateShape = Graphiti.getPeService().createShape(shape, false);
		Compensation compensation = GraphicsUtil.createEventCompensation(compensateShape);
		compensation.arrow1.setFilled(true);
		compensation.arrow1.setForeground(algorithm.manageColor(StyleUtil.CLASS_FOREGROUND));
		compensation.arrow1.setBackground(algorithm.manageColor(StyleUtil.CLASS_FOREGROUND));
		compensation.arrow2.setFilled(true);
		compensation.arrow2.setForeground(algorithm.manageColor(StyleUtil.CLASS_FOREGROUND));
		compensation.arrow2.setBackground(algorithm.manageColor(StyleUtil.CLASS_FOREGROUND));
		return compensateShape;
	}

	public static class CreateCompensateEventDefinition extends CreateEventDefinition {

		public CreateCompensateEventDefinition(IFeatureProvider fp) {
			super(fp, "Compensate Definition", "Adds compensate trigger to event");
		}

		@Override
		public boolean canCreate(ICreateContext context) {
			if (!super.canCreate(context)) {
				return false;
			}

			Event e = (Event) getBusinessObjectForPictogramElement(context.getTargetContainer());

			if (e instanceof BoundaryEvent) {
				BoundaryEvent be = (BoundaryEvent) e;
				return be.isCancelActivity();
			}

			if (e instanceof StartEvent) {
				if (((StartEvent) e).isIsInterrupting() == false) {
					return false;
				}

				EObject container = context.getTargetContainer().eContainer();
				if (container instanceof Shape) {
					Object o = getBusinessObjectForPictogramElement((Shape) container);
					return o != null && o instanceof SubProcess;
				}

				return false;
			}

			if (e instanceof CatchEvent) {
				return false;
			}

			return true;
		}

		@Override
		protected EventDefinition createEventDefinition(ICreateContext context) {
			return ModelHandler.FACTORY.createCompensateEventDefinition();
		}

		@Override
		protected String getStencilImageId() {
			return ImageProvider.IMG_16_COMPENSATE;
		}
	}
}