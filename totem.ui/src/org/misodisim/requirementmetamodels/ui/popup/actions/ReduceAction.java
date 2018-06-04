package org.misodisim.requirementmetamodels.ui.popup.actions;

import java.io.File;

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

public class ReduceAction implements IObjectActionDelegate {

	private Shell shell;
	
	/**
	 * Constructor for Action1.
	 */
	public ReduceAction() {
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
		
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		// File workspaceDirectory = workspace.getRoot().getLocation().toFile();
		// path = workspaceDirectory.toString();
		if (window != null)
	    {
	        IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
	        
	        
	        if (selection instanceof IStructuredSelection) {
	            IStructuredSelection ssel = (IStructuredSelection) selection;
	            Object obj = ssel.getFirstElement();
	            IFile file  = (IFile) Platform.getAdapterManager().getAdapter(obj,
	                    IFile.class);
	            if (file == null) {
	                if (obj instanceof IAdaptable) {
	                    file = (IFile) ((IAdaptable) obj).getAdapter(IFile.class);
	                    System.out.println(file);
	                }
	            }
	            if (file != null) {
	                // path+=file.toString().substring(1);
	            	path = file.getLocation().toOSString();
	            }
	        }
	    }
		ReduceRequirementMetamodels r = new ReduceRequirementMetamodels();
		try {
			r.reduce(path);
			MessageDialog.openInformation(shell, "Process is done.", "DRM models and compatibility model are computed");		
		} catch (ATLCoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MessageDialog.openInformation(shell, "Process with errors.", "Error: " + e.getMessage());
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
