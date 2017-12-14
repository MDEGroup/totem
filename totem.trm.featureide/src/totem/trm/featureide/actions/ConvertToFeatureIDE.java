package totem.trm.featureide.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import featureide.emf.SimpleFM2FeatureIDE;
import totem.trm.featureide.Variability2FM;

public class ConvertToFeatureIDE implements IObjectActionDelegate {

	private Shell shell;
	

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
		File workspaceDirectory = workspace.getRoot().getLocation().toFile();
		path = workspaceDirectory.toString();
		if (window != null)
	    {
	        IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
	        if (selection instanceof IStructuredSelection) {
	            IStructuredSelection ssel = (IStructuredSelection) selection;
	            Object obj = ssel.getFirstElement();
	            IFile file  = (IFile) Platform.getAdapterManager().getAdapter(obj, IFile.class);
	            if (file == null) {
	                if (obj instanceof IAdaptable) {
	                    file = (IFile) ((IAdaptable) obj).getAdapter(IFile.class);
	                    System.out.println(file);
	                }
	                
	            }
	            convert(file);
	        }
	    }
	}

	private void convert(IFile file) {
		ResourceSetImpl rs = new ResourceSetImpl();
		Resource input = rs.getResource(URI.createURI(file.getFullPath().toPortableString()), true);
		
		Resource output = new Variability2FM().exec(input);
		try {
			output.save(new FileOutputStream(file.getLocation().removeFileExtension().addFileExtension("fm.xmi").toPortableString()), null );
			
			String fmFile = file.getLocation().removeFileExtension().addFileExtension("xml").toPortableString();
			
			new SimpleFM2FeatureIDE().generateXMLFormat(output, new File(fmFile));			
			MessageDialog.openInformation(shell, "Process is done.", "FeatureIDE model computed");		
		} catch (IOException e) {
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
