package org.jboss.bpmn2.editor.ui.features.data;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.DataInput;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.Polygon;
import org.jboss.bpmn2.editor.core.ModelHandler;
import org.jboss.bpmn2.editor.core.features.data.AbstractCreateDataInputOutputFeature;
import org.jboss.bpmn2.editor.core.features.data.AbstractDataFeatureContainer;
import org.jboss.bpmn2.editor.core.features.data.AddDataFeature;
import org.jboss.bpmn2.editor.core.utils.GraphicsUtil;
import org.jboss.bpmn2.editor.core.utils.StyleUtil;
import org.jboss.bpmn2.editor.ui.ImageProvider;

public class DataInputFeatureContainer extends AbstractDataFeatureContainer {

	@Override
	public boolean canApplyTo(BaseElement element) {
		return element instanceof DataInput;
	}

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new CreateDataInputFeature(fp);
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new AddDataFeature<DataInput>(fp) {
			@Override
			protected boolean isSupportCollectionMarkers() {
				return false;
			}

			@Override
			protected void decorate(Polygon p) {
				Polygon arrow = GraphicsUtil.createDataArrow(p);
				arrow.setFilled(false);
				arrow.setForeground(manageColor(StyleUtil.CLASS_FOREGROUND));
			}
		};
	}

	public static class CreateDataInputFeature extends AbstractCreateDataInputOutputFeature {

		public CreateDataInputFeature(IFeatureProvider fp) {
			super(fp, "Data Input", "Declaration that a particular kind of data will be used as input");
		}

		@SuppressWarnings("unchecked")
		@Override
		public DataInput add(Object target, ModelHandler handler) {
			DataInput dataInput = ModelHandler.FACTORY.createDataInput();
			dataInput.setId(EcoreUtil.generateUUID());
			dataInput.setName("Data Input");
			handler.addDataInput(target, dataInput);
			return dataInput;
		}

		@Override
		public String getStencilImageId() {
			return ImageProvider.IMG_16_DATA_INPUT;
		}
	}
}