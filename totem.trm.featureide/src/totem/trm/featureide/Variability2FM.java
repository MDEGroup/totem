package totem.trm.featureide;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.emf.ecore.resource.Resource;

import VariabilityFM.VariabilityFMPackage;
import anatlyzer.testing.atl.launching.ATLExecutor;
import anatlyzer.testing.atl.launching.ATLLaunchException;
import simplefm.SimpleFMPackage;

import static anatlyzer.testing.atl.launching.ATLExecutor.inModel;
import static anatlyzer.testing.atl.launching.ATLExecutor.outModel;


public class Variability2FM {

	public Resource exec(Resource input) {
		InputStream transformation;
		try {
			transformation = Activator.getContext().getBundle().getEntry("/transformations/variability2fm.asm").openStream();
			ATLExecutor executor = 
					new ATLExecutor().perform(transformation, 
							inModel("IN", input, "TRM", VariabilityFMPackage.eINSTANCE.getNsURI()),
							outModel("OUT", "output.xmi", "FM", SimpleFMPackage.eINSTANCE.getNsURI())
							);
			
			return executor.getModelResource("OUT");
		} catch (IOException e) {
			throw new ATLLaunchException(e);
		}
		
	}
	
}
