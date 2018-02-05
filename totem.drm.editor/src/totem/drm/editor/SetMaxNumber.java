package totem.drm.editor;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;
import org.eclipse.ui.actions.PartEventAction;

import MM_uncertainty.Attribute;
import MM_uncertainty.Feature;
import MM_uncertainty.MM_uncertaintyFactory;

public class SetMaxNumber implements IExternalJavaAction {

	public SetMaxNumber() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {
		for (EObject eObject : selections) {
			MM_uncertainty.Feature c = null;
			if(eObject instanceof Feature)
				c = (MM_uncertainty.Feature)eObject;
			if(eObject instanceof Attribute)
				c = (MM_uncertainty.Feature)eObject.eContainer();
			MM_uncertainty.Number n = MM_uncertaintyFactory.eINSTANCE.createNumber();
			try {
				int value = Integer.parseInt(parameters.get("value").toString());
				n.setValue(value);
				c.setMax(n);
			} catch (NumberFormatException e) {
			}
		}
	}

	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		return true;
	}

}
