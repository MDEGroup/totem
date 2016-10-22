package mmfootprint.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.IOWrappedException;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.ecore.delegate.OCLDelegateDomain;
//import org.eclipse.ocl.pivot.model.OCLstdlib;
//import org.eclipse.ocl.xtext.oclinecore.OCLinEcoreStandaloneSetup;

public class CustomEObjectValidator extends EObjectValidator {
	
	private List<String> loadingErrors = new ArrayList<String>();
	
	/**
	 * Use this constructor if the resource of the objects to be validated is loaded.
	 */
	private CustomEObjectValidator () {
		super();
		// register OCL delegate to check OCL metamodel invariants
		OCL.initialize(null);
		OCLDelegateDomain.initialize(null);
//		OCLinEcoreStandaloneSetup.doSetup();
//		OCLstdlib.install();
	}
	
	/**
	 * Use this constructor if the resource of the objects to be validated is not loaded.
	 * The constructor stores the errors detected during the resource load, so that they 
	 * can be reported when the method validate is invoked.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public CustomEObjectValidator (Resource r) throws IOException {
		this();
		try {
			r.load(null);
		}
		catch (IOWrappedException e) {
			// the model contains errors
			String message = e.getCause().getCause()!=null? e.getCause().getCause().getMessage() : e.getCause().getMessage();
			loadingErrors.add(message);
//			Pattern pattern = Pattern.compile("@(\\S*)");
//			Matcher m = pattern.matcher(message);
//			System.out.println("----------");
//			System.out.println(message);
//			if (m.find()) System.out.println(m.group(0));
//			System.out.println("----------");			
		} 
	}	
	
	/**
	 * It validates the given object.
	 */
	public boolean validate(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		// TODO: return loadingErrors in diagnostics
		boolean validation = super.validate(eObject, diagnostics, context);
		validation = this.validate_EveryLinkValueConforms(eObject, diagnostics, context) && validation;
		return validation;
	}

	/**
	 * For each link of the received object, the method validates whether it contains objects
	 * whose type conforms to the link type.
	 * @param eObject
	 * @param diagnostics
	 * @param context
	 * @return
	 */
	private boolean validate_EveryLinkValueConforms (EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean validation = true;
		for (EReference sf : eObject.eClass().getEAllReferences()) {
			List<EObject> objects = new ArrayList<EObject>();
			if (sf.getUpperBound()>1 || sf.getUpperBound()==-1) 
				 objects.addAll( (List)eObject.eGet(sf) );
			else objects.add( (EObject)eObject.eGet(sf) );
			for (EObject object : objects) {
				if (!conforms(object, sf.getEReferenceType())) {
					validation = true;
					diagnostics.merge(
							new BasicDiagnostic(
									Diagnostic.ERROR, 
									eObject.toString(), 
									0, 
									"The feature '" + getFeatureLabel(sf, context) + "' of '" + getObjectLabel(eObject, context) + "' contains objects with a type incompatible with " + sf.getEReferenceType().getName() +".", 
									new Object [] {eObject, eObject.eGet(sf)} )
							);
					break;
				}
			}
		}
		return validation;
	}
	
	/**
	 * It returns whether an object conforms to a class.
	 * @param eObject
	 * @param eClass
	 * @return
	 */
	private boolean conforms (EObject eObject, EClass eClass) {
		return eObject.eClass() == eClass || eObject.eClass().getEAllSuperTypes().contains(eClass);
	}
}
