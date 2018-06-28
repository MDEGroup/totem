package org.miso_disim.requirementmetamodel.reduce;

import java.util.HashMap;

import MM_uncertainty.Class;
import MM_uncertainty.MM_uncertaintyFactory;
import MM_uncertainty.Metamodel;
import MM_uncertainty.UBoolean;
import MM_uncertainty.UnknowClass;
import anatlyzer.atl.model.ATLModel;
import anatlyzer.atl.types.PrimitiveType;
import anatlyzer.atlext.ATL.SimpleInPatternElement;
import anatlyzer.atlext.ATL.SimpleOutPatternElement;
import anatlyzer.atlext.OCL.BooleanType;
import anatlyzer.atlext.OCL.IntegerType;
import anatlyzer.atlext.OCL.Iterator;
import anatlyzer.atlext.OCL.Parameter;
import anatlyzer.atlext.OCL.RealType;
import anatlyzer.atlext.OCL.StringType;
import anatlyzer.atlext.processing.AbstractVisitor;

public class VariableVisitor extends AbstractVisitor {
	private Metamodel rootIn = MM_uncertaintyFactory.eINSTANCE.createMetamodel();;
	
	private Metamodel rootOut = MM_uncertaintyFactory.eINSTANCE.createMetamodel();;
	
	public Metamodel getRootOut() {
		return rootOut;
	}

	public void setRootOut(Metamodel rootOut) {
		this.rootOut = rootOut;
	}

	public Metamodel getRootIn() {
		return rootIn;
	}

	public void setRootIn(Metamodel root) {
		this.rootIn = root;
	}

	private HashMap<String, Class> klassHashMapIn = new HashMap<String, Class>();
	private HashMap<String, Class> klassHashMapOut = new HashMap<String, Class>();
	private HashMap<Object, Object> oclComputedType = new HashMap<Object, Object>();

	private Class getClassFromName(HashMap<String, Class> klassHashMap, String name, Metamodel root){
		Class klass = klassHashMap.get(name);
		if (klass == null) {
			klass = MM_uncertaintyFactory.eINSTANCE.createClass();
			klass.setName(name);
			klassHashMap.put(klass.getName(), klass);
			root.getClasses().add(klass);
		}
		return klass;
	}
	
	public void perform(ATLModel model ) {
		startVisiting(model.getRoot());
	}
	
	public HashMap<Object, Object> getOclComputedType() {
		return oclComputedType;
	}
	
	public void setOclComputedType(HashMap<Object, Object> oclComputedType) {
		this.oclComputedType = oclComputedType;
	}
	
	public HashMap<String, Class> getKlassHashMap() {
		return klassHashMapIn;
	}
	
	public void setKlassHashMap(HashMap<String, Class> klassHashMap) {
		this.klassHashMapIn = klassHashMap;
	}
	
	@Override
	public void beforeSimpleInPatternElement(SimpleInPatternElement self) {
		Class c = getClassFromName(klassHashMapIn, self.getType().getName(), rootIn);
		c.setIsAbstract(UBoolean.DONT_KNOW);
		oclComputedType.put(self, c);
	}
	@Override
	public void beforeSimpleOutPatternElement(SimpleOutPatternElement self) {
		Class c = getClassFromName(klassHashMapOut, self.getType().getName(), rootOut);
		c.setIsAbstract(UBoolean.FALSE);
		oclComputedType.put(self, c);
	}

	@Override
	public void beforeParameter(Parameter self) {
		if(!( (self.getType() instanceof StringType)) ||
		   ((self.getType() instanceof BooleanType)) ||
		   ((self.getType() instanceof IntegerType)) ||
		   ((self.getType() instanceof RealType)))
		{
			Class c = getClassFromName(klassHashMapIn, self.getType().getName(), rootIn);
			c.setIsAbstract(UBoolean.DONT_KNOW);
			oclComputedType.put(self, getClassFromName(klassHashMapIn, self.getType().getName(),rootIn));
		}
	}
	@Override
	public void beforeIterator(Iterator self) {
		UnknowClass c = MM_uncertaintyFactory.eINSTANCE.createUnknowClass();
		c.setName(self.getVarName());
		oclComputedType.put(self, c);
	}
	
	

}
