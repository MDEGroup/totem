package mmfootprint.evaluation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.m2m.atl.core.ATLCoreException;

import anatlyzer.atl.analyser.Analyser;
import anatlyzer.atl.analyser.namespaces.MetamodelNamespace;
import anatlyzer.atl.editor.builder.AnalyserExecutor;
import anatlyzer.atl.footprint.TrafoMetamodelData;
import anatlyzer.atl.model.ATLModel;
import anatlyzer.atl.util.ATLUtils;
import anatlyzer.atl.util.ATLUtils.ModelInfo;
import anatlyzer.atl.util.AnalyserUtils.PreconditionParseError;
import mmfootprint.codegen.RequirementsModel;
import mmfootprint.evaluation.report.ReportConsole;
import transML.exceptions.transException;

/**
 * This reuses Tester class just for implementation means, but
 * both have a clearly unrelated behaviour, so be careful.
 * 
 * @author jesus
 */
public class TesterFootprint extends Tester {

	private TrafoMetamodelData footprintInput;
	private TrafoMetamodelData footprintOutput;
	private FootprintReport footprintReport;

	public TesterFootprint(String trafo, String temporalFolder)
			throws ATLCoreException, transException, PreconditionParseError {
		super(trafo, temporalFolder);
		
		this.footprintReport = new FootprintReport(temporalFolder);
	}

	@Override
	public void runEvaluation() {
		String error = "";
		if (!this.generateRequirementsModel())    
			error = "The evaluation CANNOT be performed\nbecause the requirements model could not be generated";
				
		if ( error.isEmpty() ) {
			extractMetamodelFootprint();
			compareFootprints();
			try {
				footprintReport.printToConsole();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			report.printError(error);
		}	
	}
	
	private void compareFootprints() {
		compare(footprintInput, reqmodel_source, "Input");
		compare(footprintOutput, reqmodel_target, "Output");
	}

	private void compare(TrafoMetamodelData footprint, RequirementsModel reqmodel, String kind) {
		footprintReport.append("** ", kind, " **");
		footprintReport.append("Direct used classes: ", footprint.getDirectUsedClasses().size() + "");
		footprintReport.append("           Features: ", footprint.getUsedFeatures().size() + "");
		
//		footprintReport.append("DRM");
//		footprintReport.append("Direct used classes: ", reqmodel.getDirectUsedClasses().size() + "");
//		footprintReport.append("           Features: ", footprint.getUsedFeatures().size() + "");
		
		IModel m = reqmodel.createEmfModel();
		//m.getAllOfType("Class");
		
		
	}

	private void extractMetamodelFootprint() {
		Map<String, Resource> metamodels = this.namespace.getLogicalNamesToMetamodels();
		
		// The "info" map is used to determine if we need to use the source or the target
		// requirements model based on whether the meta-model is used as input or output
		ATLModel model = new ATLModel(this.atlModel.getResource());
		HashMap<String, ModelInfo> info = (HashMap<String, ModelInfo>) ATLUtils.getModelInfo(model).stream().collect(Collectors.toMap(m -> m.getMetamodelName(), m -> m));
		
		String inMMname  = info.entrySet().stream().filter(entry -> entry.getValue().isInput()).findFirst().orElseThrow(() -> new IllegalStateException()).getKey();
		String outMMname = info.entrySet().stream().filter(entry -> entry.getValue().isOutput()).findFirst().orElseThrow(() -> new IllegalStateException()).getKey();

		MetamodelNamespace input = this.namespace.getNamespace(inMMname);
		MetamodelNamespace output = this.namespace.getNamespace(outMMname);
		
		Analyser analyser = new Analyser(namespace, model);
		analyser.perform();
		
		
        TrafoMetamodelData footprintInput = new TrafoMetamodelData(model, input);
        TrafoMetamodelData footprintOutput = new TrafoMetamodelData(model, output);

        this.footprintInput  = footprintInput;
        this.footprintOutput = footprintOutput;
        
		/*
		
		
        Set<EClass> allFootprintClasses = new HashSet<>();
        HashMap<EClass, IMetaDriver> class2driver = new HashMap<EClass, IMetaDriver>();
        List<ModelInfo> inputs = ATLUtils.getModelInfo(result.getATLModel()).stream().filter(ModelInfo::isInput).collect(Collectors.toList());
        for(ModelInfo i : inputs) {
                MetamodelNamespace mm = result.getNamespaces().getNamespace(i.getMetamodelName());
                
                
                TrafoMetamodelData footprint = new TrafoMetamodelData(result.getATLModel(), mm);
                
                Set<EClass> classes = footprint.getDirectUsedClasses();
                allFootprintClasses.addAll(classes);
                
                IMetaDriver d = drivers.get(i.getMetamodelName());
                for (EClass eClass : classes) {
                        class2driver.put(eClass, d);
                }                       
        }

		*/
	}
	
	public static class FootprintReport {
		private StringBuffer buffer = new StringBuffer();
		private String temporalFolder;
		private String resultFileName;
		
		public FootprintReport(String temporalFolder) {
			this.temporalFolder = temporalFolder;
			this.resultFileName = "footprint.txt";
		}

		public void append(String... strings) {
			String s = String.join("", strings);
			buffer.append(s);
			buffer.append("\n");			
		}
		
		public void printToConsole() throws FileNotFoundException {
			ReportConsole console = new ReportConsole(new FileOutputStream(temporalFolder + File.separator + resultFileName));
		
			console.print(buffer.toString());
		}
		
	}
	
}
