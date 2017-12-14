package mmfootprint.evaluation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.eclipse.m2m.atl.core.ModelFactory;
import org.eclipse.m2m.atl.core.emf.EMFModel;
import org.eclipse.m2m.atl.core.emf.EMFModelFactory;
import org.eclipse.m2m.atl.core.emf.EMFReferenceModel;
import org.eclipse.m2m.atl.engine.parser.AtlParser;
import org.eclipse.ocl.ParserException;
import org.miso_disim.requirementmetamodel.reduce.TestMain;

import anatlyzer.atl.analyser.Analyser;
import anatlyzer.atl.analyser.AnalysisResult;
import anatlyzer.atl.analyser.batch.RuleConflictAnalysis.OverlappingRules;
import anatlyzer.atl.analyser.namespaces.GlobalNamespace;
import anatlyzer.atl.editor.builder.AnalyserExecutor;
import anatlyzer.atl.editor.builder.AnalyserExecutor.AnalyserData;
import anatlyzer.atl.editor.witness.EclipseUseWitnessFinder;
import anatlyzer.atl.errors.Problem;
import anatlyzer.atl.errors.ProblemStatus;
import anatlyzer.atl.errors.atl_error.BindingPossiblyUnresolved;
import anatlyzer.atl.errors.atl_error.BindingWithoutRule;
import anatlyzer.atl.model.ATLModel;
import anatlyzer.atl.util.ATLUtils;
import anatlyzer.atl.util.ATLUtils.ModelInfo;
import anatlyzer.atl.util.AnalyserUtils;
import anatlyzer.atl.util.AnalyserUtils.CannotLoadMetamodel;
import anatlyzer.atl.util.AnalyserUtils.IAtlFileLoader;
import anatlyzer.atl.util.AnalyserUtils.PreconditionParseError;
import anatlyzer.ui.actions.CheckRuleConflicts;
import anatlyzer.ui.util.AtlEngineUtils;
import mmfootprint.codegen.RequirementsModel;
import mmfootprint.codegen.VariabilityModel;
import mmfootprint.evaluation.report.Report;
import testing.metamodel.EcoreMutantGenerator;
import transML.exceptions.transException;
import transML.utils.modeling.EMFUtils;

public class Tester {
	
	private String   atlFile;          // name of transformation file
	private EMFModel atlModel;         // model of the original transformation
	private GlobalNamespace namespace; // meta-models used by the transformation (union of inputMetamodels and outputMetamodels)
	private List<String> inputMetamodels  = new ArrayList<String>(); // input metamodels (IN)
	private List<String> outputMetamodels = new ArrayList<String>(); // output metamodels (OUT)
    private HashMap<String, ModelInfo> aliasToPaths = new HashMap<String, ModelInfo>();
	private ResourceSet rs;
	private Report report;
    
    // requirement model
	private final String REQUIREMENTS_MODEL_SOURCE      = "Input.ecore";
	private final String REQUIREMENTS_MODEL_TARGET      = "Output.ecore";
	private final String REQUIREMENTS_MODEL_VARIABILITY = "variability.xmi";
    private RequirementsModel reqmodel_source = null; // REQUIREMENTS_MODEL_SOURCE
    private RequirementsModel reqmodel_target = null; // REQUIREMENTS_MODEL_TARGET
    private VariabilityModel reqmodel_varity = null; // REQUIREMENTS_MODEL_VARIABILITY
	
	// temporal folders
	private String folderMutants; 
	private String folderRMM;
	private String temporalFolder;     
	
	/**
	 * @param trafo transformation to be used in the evaluation
	 * @param temporalFolder temporal folder used to store the generated metamodel mutants
	 * @throws ATLCoreException 
	 * @throws transException 
	 * @throws PreconditionParseError 
	 * @throws CoreException 
	 */
	public Tester (String trafo, String temporalFolder) throws ATLCoreException, transException, PreconditionParseError {
		this.rs       = new ResourceSetImpl();
		this.report   = new Report();
		this.atlFile  = trafo;
		this.atlModel = this.loadTransformationModel(trafo);
		this.loadMetamodelsFromTransformation();
		
		// initialize temporal folders
		this.temporalFolder = temporalFolder;
		this.folderRMM     = temporalFolder + File.separator + "rmm"     + File.separator;
		this.folderMutants = temporalFolder + File.separator + "mutants" + File.separator;
		
		this.deleteDirectory(folderRMM, true); // delete temporal folder
	}

	/**
	 * It runs the evaluation, consisting on two steps:
	 * (1) generate requirements model of the transformation
	 * (2) generate mutants of the original metamodels
	 * (3) for each mutant metamodel, evaluate whether:
	 *     (3a) the requirements model accepts the mutant metamodel
	 *     (3b) the anatlyzer reports an error for the  mutant metamodels
	 * @throws IOException 
	 */
	public void runEvaluation () {
		// if the original transformation has errors, do not perform the evaluation
		this.evaluateTransformation(atlFile);
		String error = "";
		if      (report.getAnatlyserNotifiesError(atlFile)) error = "The evaluation CANNOT be performed\nbecause the anatlyser detected an error in the transformation:\n\n"+report.getAnatlyserError(atlFile);
		else if (report.getAnatlyserDoesNotFinish(atlFile)) error = "The evaluation CANNOT be performed\nbecause the anatlyser raised an exception when analysing the transformation:\n\n"+report.getAnatlyserError(atlFile);
		// (1) generate requirements model of the transformation
		else if (!this.generateRequirementsModel())         error = "The evaluation CANNOT be performed\nbecause the requirements model could not be generated";
		
		if (error.isEmpty()) {
			report.clear();
			
			// (2) generate mutants of the original metamodels
			boolean genMutants = true;
			if ( new File(folderMutants).exists() ) {
				if ( ! MessageDialog.openConfirm(null, "", "Do you want to re-generate mutants?") ) {
					genMutants = false;
				}
			}
			if ( genMutants )
				this.generateMetamodelMutants();
			
			// (3) for each mutant metamodel, evaluate conformance and analyse transformation  
			this.evaluate();
			this.printReport();
			
//			this.printReport(URI.createURI(atlFile).trimSegments(1).toString());
		}
		
		// otherwise, return an error
		else report.printError(error);		
	}
	
	
	
	/**
	 * For each mutant metamodel, evaluate conformance and analyse transformation
	 */
	private void evaluate() {
		EcoreMutantGenerator  generator  = new EcoreMutantGenerator();
		Map<String, Resource> metamodels = this.namespace.getLogicalNamesToMetamodels();
		
		// The "info" map is used to determine if we need to use the source or the target
		// requirements model based on whether the meta-model is used as input or output
		ATLModel model = new ATLModel(this.atlModel.getResource());
		HashMap<String, ModelInfo> info = (HashMap<String, ModelInfo>) ATLUtils.getModelInfo(model).stream().collect(Collectors.toMap(m -> m.getMetamodelName(), m -> m));
		
		String inMMname  = info.entrySet().stream().filter(entry -> entry.getValue().isInput()).findFirst().orElseThrow(() -> new IllegalStateException()).getKey();
		String outMMname = info.entrySet().stream().filter(entry -> entry.getValue().isOutput()).findFirst().orElseThrow(() -> new IllegalStateException()).getKey();
			
		for (String name : metamodels.keySet()) {
			String folder = getMutantsFolder(name);
			try {
				// Find all meta-models in the folder
				List<java.nio.file.Path> mutantMetamodels = Files.list(new File(folder).toPath()).collect(Collectors.toList());
				for (java.nio.file.Path p : mutantMetamodels) {
					Map<String, Resource> newMetamodels = new HashMap<String, Resource>(metamodels); 

					System.out.println("Evaluating: " + p.toString());
					
					ResourceSetImpl rs = new ResourceSetImpl();
					Resource r  = rs.getResource(URI.createFileURI(p.toAbsolutePath().toString()), true);
					
					if ( Diagnostician.INSTANCE.validate(r.getContents().get(0)).getChildren().size() > 0 ) {
						report.setMutantDoesNotValidate(p.toString());
						continue;
					}
					
					newMetamodels.put(name, r);
					GlobalNamespace namespace = new GlobalNamespace(newMetamodels.values(), newMetamodels);
					
					String reportFileName = atlFile + "_" + name + "_" + p.getFileName().toString();
					try {
						report.addResult(reportFileName);
						String problems = typing(atlFile, namespace);
						if ( ! problems.isEmpty() ) {
							report.setAnatlyserError(reportFileName, problems);
						}
					} catch (Exception e) {
						report.setAnatlyserException(reportFileName, e.getMessage());
					}
					
					
					RequirementsModel toCheck;  
//					String sourceMetamodelPath = metamodels.get(inMMname).getURI().toFileString();
//					String targetMetamodelPath = metamodels.get(outMMname).getURI().toFileString();
					String sourceMetamodelPath = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path( metamodels.get(inMMname).getURI().toPlatformString(true) )).getLocation().toOSString();
					String targetMetamodelPath = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path( metamodels.get(outMMname).getURI().toPlatformString(true) )).getLocation().toOSString();
					
					if ( info.get(name).isInput() ) {
						toCheck = reqmodel_source;	
						sourceMetamodelPath = p.toAbsolutePath().toString();
					} else {
						toCheck = reqmodel_target;
						targetMetamodelPath = p.toAbsolutePath().toString();
					}
					
					if ( reportFileName.endsWith("JavaSource2Table.atl_Table_RestrictMetapropertyUB_mutant_1.ecore")) {
						System.out.println("Here");
					}
					
					// Now try with the requirements, using the new meta-model "p" and the original
					boolean conforms = toCheck.checkConformance(p.toAbsolutePath().toString());
					if ( ! conforms ) {
						report.setMmDoesNotConformToRMM(reportFileName, "Not conform: " + p.toString());
					} else {
						// If it conforms, we check compatibility
						conforms = reqmodel_varity.checkConformance(sourceMetamodelPath, targetMetamodelPath);
						if ( ! conforms ) {
							report.setMmDoesNotConformToRMM(reportFileName, "Compatibility problem: " + p.toString());
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			URI ecoreFile = metamodels.get(name).getURI();

		}
		
	}

	/**
	 * It prints the result of the evaluation to the console.
	 */
	private void printReport () {
		try {
			report.printToConsole(temporalFolder);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		report.print(temporalFolder);
	}
		
	/**
	 * It generates mutants of the transformation's metamodels.
	 */
	private void generateMetamodelMutants () {
		EcoreMutantGenerator  generator  = new EcoreMutantGenerator();
		Map<String, Resource> metamodels = this.namespace.getLogicalNamesToMetamodels(false);
		for (String name : metamodels.keySet()) {
			URI ecoreFile = metamodels.get(name).getURI();
			String folder = getMutantsFolder(name);
			generator.run(ecoreFile, folder);
		}
	}
	
	/**
	 * It generates the requirements model of a transformation
	 * @return true if the requirements model was generated, false otherwise.
	 */
	private boolean generateRequirementsModel () {
		try {
			// generate requirements model
			new TestMain().generateRMM(atlFile, folderRMM);
			// create requirement models (it will be used to check conformance)
			reqmodel_source = new RequirementsModel(folderRMM + REQUIREMENTS_MODEL_SOURCE, folderRMM);
			reqmodel_target = new RequirementsModel(folderRMM + REQUIREMENTS_MODEL_TARGET, folderRMM);

			// jesusc: removed because variability model does not conform to requirements mm. 
			reqmodel_varity = new VariabilityModel(folderRMM + REQUIREMENTS_MODEL_VARIABILITY, folderRMM);
			return true;
		} 
		catch (ATLCoreException | ParserException e) {}
		return false;
	}
	
	/**
	 * It checks whether the result of anatlysing the transformation is correct, and whether
	 * the metamodels conform to the requirements model. Both should yield the same result
	 * (both successful or unsuccessful).
	 * @param transformation
	 */
	private void evaluateTransformation (String transformation) {		
		System.out.println("evaluating " + transformation + "...");
		String  errorsAnatlyser   = "";
		String  errorsConformance = "";
		
		// initialize report
		report.addResult(transformation);  // TODO: extend report to store the metamodels
		
		// check conformance of metamodels to requirements model
		errorsConformance = this.checkMetamodelConformance(transformation);

		// anatlyse transformation
		try {	
			errorsAnatlyser = this.typing(transformation);
			if (!errorsAnatlyser.isEmpty()) 
				report.setAnatlyserError(transformation, errorsAnatlyser);
		}
		catch (Exception e) { 
			e.printStackTrace();
			report.setAnatlyserException(transformation, e.getMessage()); 
		}
	}
	
	// TODO check conformance of metamodels to requirements model
	private String checkMetamodelConformance (String transformation) {
		return "";
	}
	
	/**
	 * It perform the type checking phase of a transformation. Method used by evaluateTransformation.
	 * @param atlTransformationFile
	 * @return list of confirmed problems
	 * @throws IOException
	 * @throws ATLCoreException
	 */
	private String typing (String atlTransformationFile) throws IOException, ATLCoreException {
		// the anatlyser needs to create the global namespace each time...
		ResourceSet               rs                      = new ResourceSetImpl();
		HashMap<String, Resource> logicalNamesToResources = new HashMap<String, Resource>();
		for (String metamodel : this.namespace.getLogicalNamesToMetamodels().keySet()) {
			Resource resource = this.namespace.getLogicalNamesToMetamodels().get(metamodel);
			Resource refresh  = rs.getResource(resource.getURI(), true);
			logicalNamesToResources.put(metamodel, refresh);
		}
		GlobalNamespace namespace = new GlobalNamespace(logicalNamesToResources.values(), logicalNamesToResources);

		return typing(atlTransformationFile, namespace);
	}

	private String typing(String atlTransformationFile, GlobalNamespace namespace) throws ATLCoreException {
		String        problem = "";

		// load transformation
		EMFModel  atlModel          = this.loadTransformationModel(atlTransformationFile);			
		ATLModel  atlTransformation = new ATLModel(atlModel.getResource());

		// anatlyse
//		IFile f = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(Path.fromOSString(atlTransformationFile));
//		Analyser analyser = null;
//		try {
//			analyser = new AnalyserExecutor().exec(f, false).getAnalyser();
//		} catch (IOException | CoreException | CannotLoadMetamodel | PreconditionParseError e1) {
//			e1.printStackTrace();
//			throw new RuntimeException(e1);
//		}
		Analyser analyser = new Analyser(namespace, atlTransformation);
		analyser.perform();
		
		// build list of problems found 
		ProblemStatus status  = null;
		for (Problem error : analyser.getErrors().getAnalysis().getProblems())  {
			if ( isErrorExcluded(error) ) 
				continue;
			
			status = error.getStatus();
			// if needed, generate witness to confirm the problem
			if (status==ProblemStatus.WITNESS_REQUIRED) 
				try                 { status = new EclipseUseWitnessFinder().checkDiscardCause(false).find(error, new AnalysisResult(analyser)); }
				catch (Exception e) { status = ProblemStatus.IMPL_INTERNAL_ERROR; }	
			// the problem has been confirmed
			if (status != ProblemStatus.ERROR_DISCARDED) problem += error.getDescription() + " (" + status + ") ;";
		}
		
		// if no problem was found, check rule conflicts
		if (problem.isEmpty() || status == ProblemStatus.IMPL_INTERNAL_ERROR) {
			List<OverlappingRules> rules = new CheckRuleConflicts().performAction(new AnalyserData(analyser), null);
			if      (rules.stream().anyMatch(or -> or.getAnalysisResult() == ProblemStatus.ERROR_CONFIRMED))      problem = "CONFLICT: " + ProblemStatus.ERROR_CONFIRMED.getLiteral();
			else if (rules.stream().anyMatch(or -> or.getAnalysisResult() == ProblemStatus.STATICALLY_CONFIRMED)) problem = "CONFLICT: " + ProblemStatus.STATICALLY_CONFIRMED.getLiteral();
			//for (OverlappingRules or : rules) System.out.println("------------->"+or.getAnalysisResult() );
		}
		
		return problem;
	}
	
	/**
	 * It loads a transformation as a model.
	 * @param atlTransformationFile 
	 * @return transformation model
	 * @throws ATLCoreException
	 */
	private EMFModel loadTransformationModel (String atlTransformationFile) throws ATLCoreException {
		ModelFactory      modelFactory = new EMFModelFactory();
		EMFReferenceModel atlMetamodel = (EMFReferenceModel)modelFactory.getBuiltInResource("ATL.ecore");
		AtlParser         atlParser    = new AtlParser();		
		EMFModel          atlModel     = (EMFModel)modelFactory.newModel(atlMetamodel);
		atlParser.inject (atlModel, atlTransformationFile);	
		atlModel.setIsTarget(true);				
		return atlModel;
	}	

	/**
	 * It loads the metamodels used by the transformation. The path of the meta-models must
	 * be defined as comments (starting by -- @path) at the beginning of the transformation.
	 * @return
	 * @throws PreconditionParseError 
	 */
	private void loadMetamodelsFromTransformation() throws transException, PreconditionParseError {
		// register ecore factory
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		
		try {
			ATLModel tmpAtlModel = new ATLModel(atlModel.getResource());
			this.namespace = AnalyserUtils.prepare(tmpAtlModel, new IAtlFileLoader() {			
				@Override
				public Resource load(IFile f) {
					EMFModel libModel = AtlEngineUtils.loadATLFile(f);
					return libModel.getResource();
				}

				@Override
				public Resource load(String text) {
					EMFModel libModel = AtlEngineUtils.loadATLText(text);
					return libModel.getResource();
				}
			});
			
			for (ModelInfo info : ATLUtils.getModelInfo(tmpAtlModel)) {
				if (info.isInput()) 
					 this.inputMetamodels.add (info.getMetamodelName());
				else this.outputMetamodels.add(info.getMetamodelName());
				aliasToPaths.put(info.getMetamodelName(), info);

				// register metamodel
				List<EPackage> metamodels = EMFUtils.loadEcoreMetamodel(info.getURIorPath());
		        for (EPackage p: metamodels) {
		        	if (p.getNsURI()!=null && !p.getNsURI().equals("")) rs.getPackageRegistry().put(p.getNsURI(), p);
		        	if (p.getName().equals(info.getMetamodelName()))    rs.getPackageRegistry().put(info.getMetamodelName(), p);
		        	
		        	// assign instance class name to data types (it is empty in uml/kermeta meta-models)
		        	for (EClassifier classifier : p.getEClassifiers())
		        		if (classifier instanceof EDataType)
		        			if (((EDataType)classifier).getInstanceClassName() == null)
		        				if      (classifier.getName().equals("String"))  ((EDataType)classifier).setInstanceClassName("java.lang.String");
		        				else if (classifier.getName().equals("Integer")) ((EDataType)classifier).setInstanceClassName("java.lang.Integer");
		        				else if (classifier.getName().equals("Boolean")) ((EDataType)classifier).setInstanceClassName("java.lang.Boolean");
		        }
			}
		} 
		catch (CoreException | CannotLoadMetamodel e) {
			throw new transException(transException.ERROR.GENERIC_ERROR, e.getMessage());
		}
	}
	
	/**
	 * It returns the name of the folder that contains the mutants of a metamodel.
	 * @param mmName
	 * @return
	 */
	private String getMutantsFolder (String mmName) {
		return this.folderMutants + File.separator + mmName;		
	}

	/**
	 * It deletes a directory.
	 * @param folder name of directory
	 * @param recursive it deletes the subdirectories recursively
	 */
	private void deleteDirectory (String directory, boolean recursive) {		
		File folder = new File(directory);
		if (folder.exists())
			for (File file : folder.listFiles()) {				
				if (file.isDirectory()) deleteDirectory(file.getPath(), recursive);
				file.delete();
			}
		folder.delete();
	}
	
	/**
	 * Some kinds of errors raised by anATLyzer are excluded, as they can be considered
	 * smells, but no typing errors.
	 * @param error
	 * @return
	 */
	private boolean isErrorExcluded(Problem error) {
		return  error instanceof BindingPossiblyUnresolved || 
				error instanceof BindingWithoutRule;
	}

	
}
