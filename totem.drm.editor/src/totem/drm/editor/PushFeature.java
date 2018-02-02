package totem.drm.editor;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.diagram.business.internal.metamodel.spec.DEdgeSpec;
import org.eclipse.sirius.diagram.business.internal.metamodel.spec.DNodeListElementSpec;
import org.eclipse.sirius.diagram.business.internal.metamodel.spec.DSemanticDiagramSpec;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;

import MM_uncertainty.Attribute;
import MM_uncertainty.Class;
import MM_uncertainty.Feature;
import MM_uncertainty.MM_uncertaintyFactory;
import MM_uncertainty.Metamodel;
import MM_uncertainty.UnknowClass;

public class PushFeature implements IExternalJavaAction {

	public PushFeature() {
	}

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {
		UnknowClass c = MM_uncertaintyFactory.eINSTANCE.createUnknowClass();
		Metamodel m = null;
		Class old = null;
		for (EObject eObject : selections) {
			if (eObject instanceof DNodeListElementSpec) {
				DNodeListElementSpec d = (DNodeListElementSpec) eObject;d.eContainer();
				if (d.getTarget() instanceof Attribute) {
					DSemanticDiagramSpec temp = (DSemanticDiagramSpec) d.eContainer().eContainer();
					old = (Class)d.getTarget().eContainer().eContainer();
					c.getFeats().add((Feature)d.getTarget().eContainer());
					m = (Metamodel) temp.getTarget();
				}
				if (d.getTarget() instanceof Feature) {
					DSemanticDiagramSpec temp = (DSemanticDiagramSpec) d.eContainer().eContainer();
					old = (Class)d.getTarget().eContainer();
					c.getFeats().add((Feature)d.getTarget());
					m = (Metamodel) temp.getTarget();
				}
				//c.getFeats().add((Feature) eObject);
			}
			if(eObject instanceof DEdgeSpec) {
				DEdgeSpec d = (DEdgeSpec) eObject;
				old = (Class)d.getTarget().eContainer();
				c.getFeats().add((Feature)d.getTarget());
				m = (Metamodel) old.eContainer();
			}
		}
		old.getAncs().add(c);
		m.getClasses().add(c);
		
		
	}

	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		return true;
	}

}
