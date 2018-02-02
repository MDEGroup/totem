package totem.drm.editor;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;

import MM_uncertainty.Attribute;
import MM_uncertainty.DataType;
import MM_uncertainty.MM_uncertaintyFactory;
import MM_uncertainty.Number;

public class SetMinToSpecificNumber implements IExternalJavaAction {

	public SetMinToSpecificNumber() {
	}

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {
		for (EObject eObject : selections) {
			MM_uncertainty.Feature feat=(MM_uncertainty.Feature)eObject;
			if(feat.getMin() instanceof MM_uncertainty.Number) {
				MM_uncertainty.Number number = (Number) feat.getMin();
				try{
					int value = Integer.parseInt(parameters.get("value").toString());
					boolean allowedLess = Boolean.parseBoolean(parameters.get("allowedLess").toString());
					if(allowedLess) {
						if(value < number.getValue())
							number.setValue(value);
					} else {
						if(value > number.getValue())
							number.setValue(value);
					}
						
				} catch (NumberFormatException e) {
					System.err.println("Wrong value");
				}
			}
		}
	}

	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		return true;
	}
}
