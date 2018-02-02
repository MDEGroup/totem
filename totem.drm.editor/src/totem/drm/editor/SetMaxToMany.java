package totem.drm.editor;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;

import MM_uncertainty.Attribute;
import MM_uncertainty.Feature;
import MM_uncertainty.MM_uncertaintyFactory;
import MM_uncertainty.UBoolean;
import MM_uncertainty.UnknowClass;

public class SetMaxToMany implements IExternalJavaAction {

	public SetMaxToMany() {
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
			
			c.setMax(MM_uncertaintyFactory.eINSTANCE.createMany());
		}
	}

	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		return true;
	}

}
