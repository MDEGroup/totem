package totem.drm.editor;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;

import MM_uncertainty.Attribute;
import MM_uncertainty.DataType;
import MM_uncertainty.MM_uncertaintyFactory;

public class SetUntypedFeatureToAttr implements IExternalJavaAction {

	public SetUntypedFeatureToAttr() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		for (EObject eObject : selections) {
			MM_uncertainty.Feature feat=(MM_uncertainty.Feature)eObject;
			Attribute attr=MM_uncertaintyFactory.eINSTANCE.createAttribute();
			attr.getType().addAll((Collection<? extends DataType>) parameters.get("types"));
			feat.getHasType().add(attr);
		}
	}

	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		// TODO Auto-generated method stub
		return true;
	}

}
