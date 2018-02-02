package totem.drm.editor;

import java.util.Collection;
import java.util.StringJoiner;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypedElement;

import MM_uncertainty.Attribute;
import MM_uncertainty.Cardinality;
import MM_uncertainty.Class;
import MM_uncertainty.DataType;
import MM_uncertainty.Feature;
import MM_uncertainty.MM_uncertaintyFactory;
import MM_uncertainty.Many;
import MM_uncertainty.Metamodel;
import MM_uncertainty.Numeric;
import MM_uncertainty.Reference;
import MM_uncertainty.UBoolean;
import MM_uncertainty.UnknowClass;
import MM_uncertainty.UnknownCardinality;

/**
 * The services class used by VSM.
 */
public class Services {

	/**
	 * See
	 * http://help.eclipse.org/neon/index.jsp?topic=%2Forg.eclipse.sirius.doc%2Fdoc%2Findex.html&cp=24
	 * for documentation on how to write service methods.
	 */

	public Boolean isUnknownClsss(EObject any) {
		return any instanceof UnknowClass;
	}

	public Boolean isClass(EObject any) {
		return any instanceof Class;
	}

	public Boolean isUntypedFeature(EObject any) {
		if (any instanceof Feature) {
			Feature f = (Feature) any;
			if (f.getHasType().size() == 0) {
				return true;
			}
		}
		return false;
	}
	public Boolean isFeatureMaxAnyCardinality(EObject any) {
		if (any instanceof Feature) {
			Feature f = (Feature) any;
			if(f.getMax() != null) {
				if(f.getMax() instanceof UnknownCardinality)
					return true;
			}
			return false;
		}
		return false;
	}
	public Boolean isFeatureMaxLowerCardinality(EObject any) {
		if (any instanceof Feature) {
			Feature f = (Feature) any;
			if(f.getMax() != null) {
				if(f.getMax() instanceof MM_uncertainty.Number)
					if (((MM_uncertainty.Number)f.getMax()).isAllowLess() )
						return true;
			}
			return false;
		}
		return false;
	}
	public Boolean isFeatureMaxHigherCardinality(EObject any) {
		if (any instanceof Feature) {
			Feature f = (Feature) any;
			if(f.getMax() != null) {
				if(f.getMax() instanceof MM_uncertainty.Number)
					if (!((MM_uncertainty.Number)f.getMax()).isAllowLess() )
						return true;
			}
			return false;
		}
		return false;
	}

	public Boolean canBecomeAbstract(EObject any) {

		if (any instanceof Class) {
			Class c = (Class) any;
			if (c.getIsAbstract() == UBoolean.DONT_KNOW) {
				return true;
			}
		}
		return false;
	}
	
	public Boolean canBecomeConcrete(EObject any) {

		if (any instanceof Class) {
			Class c = (Class) any;
			if (c.getIsAbstract() == UBoolean.DONT_KNOW) {
				return true;
			}
		}
		return false;
	}

	public EList<Class> getEReferenceTarget(Feature self) {
		EList<Class> result = new BasicEList<>();
		if (!self.getHasType().isEmpty()) {
			if (self.getHasType().get(0) instanceof Reference) {
				result.addAll(((Reference) self.getHasType().get(0)).getTarget());
			}
		}
		return result;
	}

	public Attribute createAttr(Feature self, EList<DataType> types) {

		Attribute a = MM_uncertaintyFactory.eINSTANCE.createAttribute();

		a.getType().addAll(types);

		return a;
	}

	public String GenericFeatureLabel(Feature self) {

		return "? " + self.getName() + " (" + getCardinality(self) + ")";

	}
	
	public String GenericAttributeLabel(Attribute self) {
		Feature feat = (Feature)self.eContainer();
		StringBuilder result = new StringBuilder();
		result.append(feat.getName()).append("(")
			  .append(getCardinality(feat))
			  .append(")").append("[");
		StringJoiner sj = new StringJoiner(",");
		for (DataType i : self.getType()) {
			sj.add(i.eClass().getName());
		}
		result.append(sj.toString());
		result.append("]");
		return result.toString();

	}
	

	public String getCardinality(Feature self) {
		String min = "?";
		String max = "?";
		MM_uncertaintyFactory factory = MM_uncertaintyFactory.eINSTANCE;

		if (self.getMin() == null) {
			self.setMin(factory.createUnknownCardinality());

			min = "?";
		}

		if (self.getMax() == null) {
			self.setMax(factory.createUnknownCardinality());
			// System.out.println("----> "+self.getMax());
			max = "?";
		}

		if (self.getMin() instanceof MM_uncertainty.Number) {
			MM_uncertainty.Number num = (MM_uncertainty.Number) self.getMin();
			min = Integer.toString(num.getValue());
		}
		if (self.getMax() instanceof MM_uncertainty.Number) {
			MM_uncertainty.Number num = (MM_uncertainty.Number) self.getMax();
			max = Integer.toString(num.getValue());
		}

		if (self.getMax() instanceof Many) {
			max = "*";
		}
		if (self.getMin() instanceof Many) {
			min = "*";
		}

		return new StringBuilder()
				.append(min)
				.append("..")
				.append( max).toString();
	}
}
