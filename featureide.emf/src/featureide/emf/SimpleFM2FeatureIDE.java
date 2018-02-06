package featureide.emf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.IllegalSelectorException;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.resource.Resource;

import simplefm.AndExpr;
import simplefm.BinaryConstraint;
import simplefm.ChildrenKind;
import simplefm.Constraint;
import simplefm.Feature;
import simplefm.FeatureContainer;
import simplefm.FeatureExists;
import simplefm.FeatureModel;
import simplefm.ImpliesExpr;
import simplefm.OrExpr;

public class SimpleFM2FeatureIDE {

	public SimpleFM2FeatureIDE() {
		
	}

	public void generateXMLFormat(Resource r, File file) throws IOException {
		String s = generateXMLFormat(r);
		FileOutputStream os = new FileOutputStream(file);
		os.write(s.getBytes());
		os.close();
	}

	public String generateXMLFormat(Resource r) {
		FeatureModel fm = (FeatureModel) r.getContents().get(0);
		
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
		
		s += "<featureModel>";
			s += "<struct>";
			s += mapChildren(fm, fm.getName(), true);
			s += "</struct>";
			
			if ( fm.getConstraints().size() > 0  ) {
				s += "<constraints>";
				s += fm.getConstraints().stream().map(c -> createConstraintTag(c)).collect(Collectors.joining("\n"));				
				s += "</constraints>";				
			}
				
				
		s += "</featureModel>";
		
		
		return s;
		
	}

	private String  createConstraintTag(Constraint c) {
		String s = "<rule>";
		s += mapConstraint(c);
		s += "</rule>";
		return s;
	}

	private String mapConstraint(Constraint c) {
		String s = "";
		if ( c instanceof FeatureExists ) {
			s += "<var>" + ((FeatureExists) c).getFeature().getName() + "</var>";
		} else if ( c instanceof BinaryConstraint ) {
			String tag = null;
			if      ( c instanceof ImpliesExpr) tag = "imp";
			else if ( c instanceof AndExpr) tag = "conj";
			else if ( c instanceof OrExpr) tag = "disj";
			else throw new UnsupportedOperationException();
			s += "<" + tag + ">" + "\n";
			s += mapConstraint(((BinaryConstraint) c).getLeft()) + "\n";
			s += mapConstraint(((BinaryConstraint) c).getRight()) + "\n";
			s += "</" + tag + ">" + "\n";
		} else {
			throw new UnsupportedOperationException();
		}
				
		return s;
	}

	private String mapChildren(FeatureContainer c, String parentName, boolean parentMandatory) {
		String tag = getTag(c.getKind());
		String s = "<" + tag + " ";
		if ( parentMandatory ) {
			s += "mandatory=\"true\" ";
		}
		
		s += "name=\"" + parentName + "\" ";
		s += ">" + "\n";
		if ( c instanceof Feature ) {
			String origin = ((Feature) c).getOrigin();
			if ( origin != null ) {
				s += "<description>" + "\nFeature:Origin=" + origin + "\n</description>";
			}
		}
		for (Feature feature : c.getChildren()) {
			s += mapChildren(feature, feature.getName(), feature.isMandatory()); 
		}
		
		s += "</" + tag + ">" + "\n";
		return s;
	}

	private String getTag(ChildrenKind kind) {
		switch (kind) {
		case ALTERNATIVE:
			return "alt";
		case OR:
			return "or";
		case AND:
			return "and";
		}
		throw new IllegalStateException();
	}

	
//	<?xml version="1.0" encoding="UTF-8" standalone="no"?>
//	<featureModel>
//		<struct>
//			<and mandatory="true" name="GraphLibrary">
//				<alt mandatory="true" name="Edges">
//					<feature mandatory="true" name="Directed"/>
//					<feature mandatory="true" name="Undirected"/>
//				</alt>
//				<or name="Algorithms">
//					<feature mandatory="true" name="Number"/>
//					<feature mandatory="true" name="Cycle"/>
//				</or>
//			</and>
//		</struct>
//		<constraints>
//			<rule>
//				<imp>
//					<var>Cycle</var>
//					<var>Directed</var>
//				</imp>
//			</rule>
//		</constraints>
//		<comments/>
//	</featureModel>
	
}
