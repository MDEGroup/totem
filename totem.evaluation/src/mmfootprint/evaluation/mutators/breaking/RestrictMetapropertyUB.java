/**
 * It restricts the multiplicity of a metaproperty (e.g., from 0..* to 0..1) 
 */
package mmfootprint.evaluation.mutators.breaking;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EStructuralFeature;

import mmfootprint.evaluation.mutators.AbstractMutator;
import mmfootprint.utils.MMResource;

public class RestrictMetapropertyUB extends AbstractMutator {

	@Override
	public void mutate(MMResource metamodel, String outputFolder) {
		// obtain candidate metaproperties (unbounded upper cardinality)
		List<EStructuralFeature> candidates = new ArrayList<EStructuralFeature>();
		for (EStructuralFeature feature : metamodel.getEStructuralFeatures()) 
			if (feature.getUpperBound() == -1)
				candidates.add(feature);
		
		// generate each possible mutant
		for (EStructuralFeature feature : candidates) {
			// restrict upper bound to 1
			int aux = feature.getUpperBound();
			feature.setUpperBound(1);
			
			// register mutation
			EAnnotation annotation = registerMutation(null, null, new ENamedElement[]{feature});
			
			// save mutant			
			save(metamodel, outputFolder);
			
			// unregister mutation
			unregisterMutation(annotation);
			

			
			// undo the mutation
			feature.setUpperBound(aux);
		}
	}

	@Override
	public boolean isBreaking() {
		return true;
	}
}
