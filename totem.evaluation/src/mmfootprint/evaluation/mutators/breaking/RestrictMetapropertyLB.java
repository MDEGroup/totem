/**
 * It restricts the multiplicity of a metaproperty (e.g., from x..n to x+1..n) 
 */
package mmfootprint.evaluation.mutators.breaking;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EStructuralFeature;

import mmfootprint.evaluation.mutators.AbstractMutator;
import mmfootprint.utils.MMResource;

public class RestrictMetapropertyLB extends AbstractMutator {

	@Override
	public void mutate(MMResource metamodel, String outputFolder) {
		// obtain candidate metaproperties (different lower and upper bound)
		List<EStructuralFeature> candidates = new ArrayList<EStructuralFeature>();
		for (EStructuralFeature feature : metamodel.getEStructuralFeatures()) 
			if (feature.getLowerBound() != feature.getUpperBound())
				candidates.add(feature);
		
		// generate each possible mutant
		for (EStructuralFeature feature : candidates) {
			// restrict lower bound by adding 1
			int aux = feature.getLowerBound();
			feature.setLowerBound(aux+1);
			
			// register mutation
			EAnnotation annotation = registerMutation(null, null, new ENamedElement[]{feature});
			
			// save mutant			
			save(metamodel, outputFolder);
			
			// unregister mutation
			unregisterMutation(annotation);
			
			// undo the mutation
			feature.setLowerBound(aux);
		}
	}

	@Override
	public boolean isBreaking() {
		return true;
	}
}
