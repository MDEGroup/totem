package totem.drm.editor;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;

import MM_uncertainty.UBoolean;

public class SetToConcrete implements IExternalJavaAction {

	public SetToConcrete() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {
		for (EObject eObject : selections) {
			MM_uncertainty.Class c=(MM_uncertainty.Class)eObject;
			c.setIsAbstract(UBoolean.FALSE);
		}
	}

	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		return true;
	}

}
