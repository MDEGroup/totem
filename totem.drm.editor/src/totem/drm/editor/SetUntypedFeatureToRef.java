package totem.drm.editor;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;

import MM_uncertainty.MM_uncertaintyFactory;
import MM_uncertainty.Reference;

public class SetUntypedFeatureToRef implements IExternalJavaAction {

	public SetUntypedFeatureToRef() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		for (EObject eObject : selections) {
			MM_uncertainty.Feature feat = (MM_uncertainty.Feature) eObject;
			Reference attr = MM_uncertaintyFactory.eINSTANCE.createReference();
			
			attr.getTarget().addAll((Collection<? extends MM_uncertainty.Class>) parameters.get("target"));
			feat.getHasType().add(attr);
		}
	}

	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		// TODO Auto-generated method stub
		return true;
	}

}
