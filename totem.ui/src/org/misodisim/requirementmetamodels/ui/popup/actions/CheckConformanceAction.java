package org.misodisim.requirementmetamodels.ui.popup.actions;

import java.io.File;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.miso_disim.requirementmetamodel.reduce.ReduceRequirementMetamodels;

import mmfootprint.codegen.RequirementsModel;

public class CheckConformanceAction implements IObjectActionDelegate {

	private Shell shell;
	
	/**
	 * Constructor for Action1.
	 */
	public CheckConformanceAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		String path = null;
		String path2 = null;
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		File workspaceDirectory = workspace.getRoot().getLocation().toFile();
		path = workspaceDirectory.toString();
		path2 = workspaceDirectory.toString();
		
		if (window != null)
	    {
	        IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
	        
	        
	        if (selection instanceof IStructuredSelection) {
	            IStructuredSelection ssel = (IStructuredSelection) selection;
	            if(ssel.size() != 2) {
	            	MessageDialog.openInformation(shell, "Error", "You have to select 2 files");
	            }
	            Iterator iterator = ssel.iterator();
	            Object rmodel = iterator.next();
	            Object metamodel = iterator.next();
	            IFile file  = (IFile) Platform.getAdapterManager().getAdapter(rmodel,
	                    IFile.class);
	            if (file == null) {
	                if (rmodel instanceof IAdaptable) {
	                    file = (IFile) ((IAdaptable) rmodel).getAdapter(IFile.class);
	                    System.out.println(file);
	                }
	            }
	            if (file != null) {
	                path+=file.toString().substring(1);
	            }
	            IFile file2  = (IFile) Platform.getAdapterManager().getAdapter(metamodel,
	                    IFile.class);
	            if (file2 == null) {
	                if (rmodel instanceof IAdaptable) {
	                    file = (IFile) ((IAdaptable) metamodel).getAdapter(IFile.class);
	                    System.out.println(file2);
	                }
	            }
	            if (file != null) {
	                path2+=file2.toString().substring(1);
	            }
	            
	        }
	    }
		RequirementsModel toCheck = new RequirementsModel(path, path.substring(0,path.lastIndexOf("/")+1));  
		boolean b = toCheck.checkConformance(path2);
		if (b)
			MessageDialog.openInformation(shell, "Conformance evaluation", "Metamodel is conform to DRM model");
		else MessageDialog.openError(shell, "Conformance evaluation", "Metamodel is not conform to DRM model");		
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
