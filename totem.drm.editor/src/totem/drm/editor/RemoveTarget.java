package totem.drm.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.eclipse.sirius.diagram.business.internal.metamodel.spec.DEdgeSpec;
import org.eclipse.sirius.diagram.business.internal.metamodel.spec.DNodeListSpec;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;

import MM_uncertainty.Class;
import MM_uncertainty.Feature;
import MM_uncertainty.FeatureType;
import MM_uncertainty.Reference;

public class RemoveTarget implements IExternalJavaAction {

	public RemoveTarget() {
	}

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {
		DEdgeSpec edge = ((ArrayList<DEdgeSpec>)(parameters.get("target"))).get(0);
		EdgeTarget dnls= edge.getTargetNode();
		DNodeListSpec dnls2 = ((DNodeListSpec)edge.getTargetNode());
		Class target = (Class)dnls2.getTarget();
		for (EObject eObject : selections) {
			if (eObject instanceof Feature) {
				Feature feature = (Feature) eObject;
				for (FeatureType featType : feature.getHasType()) 
					if(featType instanceof Reference) 
						((Reference) featType).getTarget().remove(target);
			}
		}
		
		
	}

	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		return true;
	}

}
