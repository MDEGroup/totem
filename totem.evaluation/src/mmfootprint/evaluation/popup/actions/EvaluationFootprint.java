package mmfootprint.evaluation.popup.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import mmfootprint.evaluation.Tester;
import mmfootprint.evaluation.TesterFootprint;

public class EvaluationFootprint implements IObjectActionDelegate {

	private Shell shell;
	private IFile file;
	
	/**
	 * Constructor for Action1.
	 */
	public EvaluationFootprint() {
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
		IPath completePath = file.getRawLocation();
		String trafo  = completePath.toString();
		String folder = completePath.removeLastSegments(1).toString() + "/temp";
	    try {
	    	new TesterFootprint(trafo, folder).runEvaluation();
			MessageDialog.openInformation(shell, "Evaluation", "Finished!!!");
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    	MessageDialog.openError(null, "Evaluation", e.toString());
	    }
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		file = (IFile) ((IStructuredSelection) selection).getFirstElement();
	}

}
