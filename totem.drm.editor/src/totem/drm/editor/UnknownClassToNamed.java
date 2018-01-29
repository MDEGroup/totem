package totem.drm.editor;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;

import MM_uncertainty.Class;
import MM_uncertainty.MM_uncertaintyFactory;
import MM_uncertainty.Metamodel;
import MM_uncertainty.Reference;
import MM_uncertainty.UnknowClass;

public class UnknownClassToNamed implements IExternalJavaAction {

	public UnknownClassToNamed() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		// System.out.println("ciao");
		for (EObject eObject : selections) {
			UnknowClass uc = (UnknowClass) eObject;
			MM_uncertaintyFactory factory = MM_uncertaintyFactory.eINSTANCE;
			Class c = factory.createClass();

			c.setIsAbstract(uc.getIsAbstract());
			c.setMandatoryAllowed(uc.isMandatoryAllowed());

			c.setName("Assign Class Name");
			for (MM_uncertainty.Feature f : uc.getFeats()) {
				c.getFeats().add(EcoreUtil.copy(f));
			}
			for (Class antiac : uc.getAntiancs()) {
				c.getAntiancs().add(EcoreUtil.copy(antiac));
			}
			for (Class anc : uc.getAncs()) {
				c.getAncs().add(EcoreUtil.copy(anc));
			}

			Metamodel mm = (Metamodel) uc.eContainer();

			mm.getClasses().remove(uc);
			mm.getClasses().add(c);

			// Update references types
			for (Iterator i = mm.eResource().getAllContents(); i.hasNext();) {
				Object object = i.next();
				if (Reference.class.isInstance(object)) {
					// ...
					Reference ref = (Reference) object;
					EList<Class> targets = ref.getTarget();
					boolean found = false;
					for (Class class1 : targets) {

						if (class1.equals(uc)) {
							System.err.println("found dangling node");
							// redirect target to the new class
							found = true;

						}
					}
					// if found a dangling node
					if (found)
						ref.getTarget().add(c);
				}
			}
		}
	}

	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		// TODO Auto-generated method stub
		for (EObject eObject : selections) {
			if (!(eObject instanceof UnknowClass)) {
				return false;
			}
		}
		return true;
	}

}
