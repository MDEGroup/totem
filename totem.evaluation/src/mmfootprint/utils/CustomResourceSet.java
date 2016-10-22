package mmfootprint.utils;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

public class CustomResourceSet extends ResourceSetImpl {
	
	@Override
	public Resource getResource (URI uri, boolean loadOnDemand) {
		try {
			return super.getResource(uri, loadOnDemand);
		}
		catch (WrappedException e) {
			//System.out.println(e.getCause().getCause().getMessage());
			Resource r = this.createResource(uri);
//			try {
//				r.load(null);
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				//e1.printStackTrace();
//			}			
			return r;
		}
	}
}
