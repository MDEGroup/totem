package totem.drm.editor;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;

import MM_uncertainty.UBoolean;

public class SetToAbstract implements IExternalJavaAction {

	public SetToAbstract() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		for (EObject eObject : selections) {
			MM_uncertainty.Class c=(MM_uncertainty.Class)eObject;
			c.setIsAbstract(UBoolean.TRUE);
		}
	}

	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		// TODO Auto-generated method stub
		return true;
	}

}
