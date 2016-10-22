package mmfootprint.evaluation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import mmfootprint.utils.CustomEObjectValidator;
import mmfootprint.utils.CustomResourceSet;

public class Conformance {

	public static void main(String[] args) throws IOException {
		// check whether the output model is conformant to the output metamodel
		ResourceSet rs = new CustomResourceSet();// new ResourceSetImpl();

		List<EPackage> metamodels = mmfootprint.evaluation.Conformance.loadEcoreMetamodel("MMtarget.ecore");
        for (EPackage p: metamodels) 
        	if (p.getNsURI()!=null && !p.getNsURI().equals("")) 
        		rs.getPackageRegistry().put(p.getNsURI(), p);

		String oModel = "target2.model";
		URI uri = URI.createFileURI(oModel);
		
		Resource resource = rs.createResource(uri);
		EObjectValidator validator = new CustomEObjectValidator(resource);
		DiagnosticChain diagnostics = new BasicDiagnostic();
		
		for (EObject eObject : resource.getContents()) 
			validator.validate(eObject, diagnostics, null);
		
		for (Diagnostic d : ((BasicDiagnostic)diagnostics).getChildren()) 
			System.out.println(d.getMessage());
	}

	public static List<EPackage> loadEcoreMetamodel (String uri) {

			List<EPackage> metamodel = new ArrayList<EPackage>();
			
			// check if it is already registered
			EPackage pck = EPackage.Registry.INSTANCE.getEPackage(uri);
			
			// otherwise
			if (pck==null) {
				EPackage.Registry.INSTANCE.put(uri, EPackage.class);
				if (Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().size() == 0)
					Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
				
				ResourceSetImpl resourceSet = new ResourceSetImpl();
				Resource        resource    = resourceSet.getResource(URI.createFileURI(uri), true);
				for (EObject obj : resource.getContents()) {
					if (obj instanceof EPackage) {						
						resourceSet.getPackageRegistry().put(((EPackage)obj).getNsURI(), ((EPackage)obj).getEFactoryInstance().getEPackage());
						metamodel.add((EPackage)obj);
					}
				}
			}
			else metamodel.add(pck);
			
			return metamodel;
	}
}
