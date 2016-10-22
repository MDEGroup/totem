/**
 * It adds a new non-mandatory metaproperty (i.e., a new reference between two existing classes, with lower bound 0) 
 */
package mmfootprint.evaluation.mutators.nonbreaking;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;

import mmfootprint.evaluation.mutators.AbstractMutator;
import mmfootprint.utils.MMResource;

public class AddOptionalMetaproperty extends AbstractMutator {

	@Override
	public void mutate(MMResource metamodel, String outputFolder) {
		// obtain candidate source classes (all)
		List<EClass> candidates = new ArrayList<EClass>();
		candidates.addAll(metamodel.getEClasses());
		
		// generate each possible mutant
		EcoreFactory factory = EcoreFactory.eINSTANCE;
		Random random = new Random();
		for (EClass clazz : candidates) {
			// add new optional reference to the candidate class
			EReference   newRef   = factory.createEReference();
			newRef.setEType( candidates.get( random.nextInt(candidates.size()) ) );
			newRef.setLowerBound(0);
			newRef.setName( this.getRandomString(6) );
			clazz.getEStructuralFeatures().add(newRef);
			
			// register mutation
			EAnnotation annotation = registerMutation(new ENamedElement[]{newRef}, null, null);
			
			// save mutant			
			save(metamodel, outputFolder);
			
			// unregister mutation
			unregisterMutation(annotation);
			
			// undo the mutation
			clazz.getEStructuralFeatures().remove(newRef);
		}
	}

	@Override
	public boolean isBreaking() {
		return false;
	}
}
