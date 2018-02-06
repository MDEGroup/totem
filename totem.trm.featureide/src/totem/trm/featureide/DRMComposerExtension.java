package totem.trm.featureide;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import MM_uncertainty.Feature;
import MM_uncertainty.MM_uncertaintyFactory;
import MM_uncertainty.Number;
import VariabilityFM.FeatureCardinalityGroup;
import VariabilityFM.FeatureMaxCardinalityChoice;
import VariabilityFM.FeatureMinCardinalityChoice;
import VariabilityFM.Node;
import VariabilityFM.VariableFeature;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.SelectableFeature;

public class DRMComposerExtension extends de.ovgu.featureide.core.builder.ComposerExtensionClass {

	public DRMComposerExtension() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void performFullBuild(IFile config) {
		// TODO Auto-generated method stub
		System.out.println(config);
		System.out.println("---");
	}

	@Override
	public void buildConfiguration(IFolder folder, Configuration configuration, String configurationName) {
		IProject project = folder.getProject();
		IFile file = project.getFile(new Path("model.xmi"));
		IFile inputFile = project.getFile(new Path("Input.ecore"));
		IFile outputFile = project.getFile(new Path("Output.ecore"));
		
		IFolder outputDir = project.getFolder("drms");
		if ( ! outputDir.exists() ) {
			try {
				outputDir.create(true, true, null);
			} catch (CoreException e) {
				throw new RuntimeException(e);
			}
		}
		
		ResourceSet rs = new ResourceSetImpl();
		Resource variability = rs.getResource(URI.createFileURI(file.getLocation().toOSString()), true);
		Resource input  = rs.getResource(URI.createFileURI(inputFile.getLocation().toOSString()), true);
		Resource output = rs.getResource(URI.createFileURI(outputFile.getLocation().toOSString()), true);
		
		EcoreUtil.resolveAll(rs);
		
		HashMap<String, Node> index = new HashMap<>();
		TreeIterator<EObject> c = variability.getAllContents();
		while ( c.hasNext() ) {
			EObject o = c.next();
			if ( o instanceof Node ) {
				index.put( ((Node) o).getUniqueId(), (Node) o );
			}
		}
		
		// TODO: Notify if model.xmi is not found. There must be a way to do this in the UI using FeatureIDE
		System.out.println("Resource loadeded");
		
		boolean change = false;
		List<IFeature> selected = configuration.getSelectedFeatures();
		for (IFeature f : selected) {
			// Skip the root
			if ( f.getName().equals("variability") ) 
				continue;
			
			Node element = getVariabilityElement(index,	f.getProperty().getDescription() );
			if ( element != null ) {
				change = change || pruneDRM(f, element);
			}
		}
		
		if ( change ) {
			IFile newInput  = outputDir.getFile("Input-" + configurationName + ".ecore");
			IFile newOutput = outputDir.getFile("Output-" + configurationName + ".ecore");
			
			try {
				input.save(new FileOutputStream(newInput.getLocation().toOSString()), null);
				output.save(new FileOutputStream(newOutput.getLocation().toOSString()), null);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			System.out.println("Saved DRMS for configuration: " + configurationName);
		}
		
		super.buildConfiguration(folder, configuration, configurationName);
	}

	private boolean pruneDRM(IFeature f, Node element) {
		if ( element instanceof FeatureMinCardinalityChoice ) {
			FeatureMinCardinalityChoice choice = (FeatureMinCardinalityChoice) element;
			VariableFeature vFeature = (VariableFeature) ((FeatureCardinalityGroup) element.eContainer()).eContainer();
			Feature mmFeature = ((VariableFeature) vFeature).getFeature();
			
			Number number = MM_uncertaintyFactory.eINSTANCE.createNumber();
			number.setValue(choice.getValue());

			mmFeature.setMin(number);
			return true;
		} else if ( element instanceof FeatureMaxCardinalityChoice ) {
			FeatureMaxCardinalityChoice choice = (FeatureMaxCardinalityChoice) element;
			VariableFeature vFeature = (VariableFeature) ((FeatureCardinalityGroup) element.eContainer()).eContainer();
			Feature mmFeature = ((VariableFeature) vFeature).getFeature();
			
			Number number = MM_uncertaintyFactory.eINSTANCE.createNumber();
			number.setValue(choice.getValue());

			mmFeature.setMax(number);
			return true;
		} else {
			System.out.println("TODO: Handle: " + element);
		}
		
		return false;
	}

	private Node getVariabilityElement(HashMap<String, Node> index, String description) {
		String tag = ":Origin=";
		int idx = description.indexOf(tag);
		if ( idx == -1 ) {
			return null;
		}
		
		String idText = description.substring(idx + tag.length(), description.length());
		
		return index.get(idText);
	}
	
}
