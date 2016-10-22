package org.miso_disim.requirementmetamodel.reduce;

import java.util.HashMap;

import MM_uncertainty.Class;
import MM_uncertainty.Feature;
import MM_uncertainty.MM_uncertaintyFactory;
import MM_uncertainty.Reference;
import MM_uncertainty.UnknowClass;
import anatlyzer.atlext.OCL.NavigationOrAttributeCallExp;
import anatlyzer.atlext.OCL.VariableExp;
import anatlyzer.atlext.processing.AbstractVisitor;

public class DiscoveryNavigationPathVisitor extends AbstractVisitor {
	private Class root;
	
	public Class getRoot() {
		return root;
	}
	private NavigationOrAttributeCallExp head;
	
	public void perform(Object root, NavigationOrAttributeCallExp navExp){
		head = navExp;
		if(root instanceof Class)
			this.root = (Class) root;
		else {
			UnknowClass uk = TypeUtils.createUnknownClass();
			this.root = uk;
		}
		startVisiting(navExp);
	}
	private Feature checkIsPresent(String name, Class klass) {
		for (Feature f : klass.getFeats()) {
			if(f.getName().equals(name))
				return f;
		}
		return null;
	}
	HashMap<Object, Object> temp = new HashMap<Object,Object>();
	private Class result;
	@Override
	public void inNavigationOrAttributeCallExp(NavigationOrAttributeCallExp self) {
		if(head == self) {
			result = root;
			return;
		}
		root = getClass(root, self.getName()); 
		
		
	}
	private Class getClass(Class c, String name){
		for (Feature f : c.getFeats()) {
			if(f.getHasType() instanceof Reference)
				if(((Reference)f.getHasType()).getTarget().size() > 0)
					return ((Reference)f.getHasType()).getTarget().get(0);
				else return TypeUtils.createUnknownClass();
		}
		return root;
	}
	public Class getResult() {
		return result;
	}

}
