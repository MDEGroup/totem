package mmfootprint.evaluation.report;

import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class ReportConsole {
	
	private MessageConsole        console       = null;
	private String                consoleName   = "Evaluation Requirements model";
	private MessageConsoleStream  consoleStream = null;
	private PrintWriter fileWriter;
	
	public ReportConsole() {
		console       = findConsole(consoleName);
		consoleStream = console.newMessageStream();
	}
	
	public ReportConsole(FileOutputStream fileOutputStream) {
		this();
		fileWriter = new PrintWriter(fileOutputStream);
	}
	
	/**
	 * clear the console
	 */
	public void clear() {
		console.clearConsole();                     // clear console
		consoleStream = console.newMessageStream(); // create new output stream
	}
	
	/**
	 * display the console
	 */
	public void display() {
		IWorkbench       wb   = PlatformUI.getWorkbench();
		IWorkbenchWindow win  = wb.getActiveWorkbenchWindow();
	    IWorkbenchPage   page = win.getActivePage();			
		IConsoleView view; 
		try {
			console.activate();
			view = (IConsoleView) page.showView(IConsoleConstants.ID_CONSOLE_VIEW);
			view.display(console);
		} catch (PartInitException e) {}
		
		if ( fileWriter != null ) {
			fileWriter.flush();
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		fileWriter.close();
	}
	
	/**
	 * print message in the console
	 */
	public void print  (String message) { consoleStream.print(message);	if (fileWriter!=null) fileWriter.print(message);  }
	public void println(String message) { consoleStream.println(message); if (fileWriter!=null) fileWriter.println(message); }


	/**
	 * It finds a console with a given name
	 * @param name
	 */
	private MessageConsole findConsole (String name) {
		// find console
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++) 
			if (name.equals(existing[i].getName()))
				return (MessageConsole) existing[i];
		
		// no console found, so create a new one
		MessageConsole myConsole = new MessageConsole(name, null);
		conMan.addConsoles(new IConsole[]{myConsole});
		return myConsole;
	}
}
