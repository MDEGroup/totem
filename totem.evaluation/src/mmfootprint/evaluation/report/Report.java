package mmfootprint.evaluation.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Report {

	private Map<String,SortedSet<Record>> report =  new HashMap<String,SortedSet<Record>>();
	private String resultFileName = "result.txt";

	public Report() { }
	public Report(String resultFileName) {
		this.resultFileName = resultFileName;
	}
	 
	
	/** clear report **/
	public void clear () {
		report.clear();
	}
	
	/** analysis of a new transformation */
	public SortedSet<Record> getResult (String transformation) {
		String transformation2 = getFileName(transformation);
		if (!report.containsKey(transformation2)) 
			report.put(transformation2, new TreeSet<Record>());
		return report.get(transformation2);
	}

	/** analysis of a new transformation */
	public boolean addResult (String transformation) {
		this.getResult(transformation);
		return false;
	}

	/** anatlyser reported an error in the transformation */
	public boolean setAnatlyserError (String transformation, String error) {
		Record record = new Record(null/*for any model*/, error, ERROR_KIND.ANATLYZER_DETECTED_ERROR); 
		this.getResult(transformation).add(record);
		return true;
	}
	
	/** anatlyser finished with an exception */
	public boolean setAnatlyserException (String transformation, String error) {
		Record record = new Record(null/*for any model*/, error, ERROR_KIND.ANATLYZER_DOES_NOT_FINISH); 
		this.getResult(transformation).add(record);
		return true;
	}
	
	/** metamodel does not conform to requirements model */
	public boolean setMmDoesNotConformToRMM (String transformation, String error) {
		Record record = new Record(null/*for any model*/, error, ERROR_KIND.MM_DOES_NOT_CONFORM_TO_RMM); 
		this.getResult(transformation).add(record);
		return true;
	}
	
	private List<String> invalidMutants = new ArrayList<String>();
	
	public void setMutantDoesNotValidate(String string) {
		invalidMutants.add(string);
	}	

	
	/** results of evaluation */
	public boolean getAnatlyserNotifiesError   (String transformation) { return getResult(transformation).stream().anyMatch( record -> record.anatlyserNotifiesError() ); }
	public boolean getAnatlyserDoesNotFinish   (String transformation) { return getResult(transformation).stream().anyMatch( record -> record.anatlyserDoesNotFinish() ); }
	public boolean getMmDoesNotConformToRMM    (String transformation) { return getResult(transformation).stream().anyMatch( record -> record.mmDoesNotConformToRMM() ); }
	public String  getAnatlyserError (String transformation) {
		if (getAnatlyserDoesNotFinish(transformation) || getAnatlyserNotifiesError(transformation)) {
			SortedSet<Record> records = getResult(transformation);
			Stream<Record>   errors   = records.stream().filter( record -> record.anatlyserDoesNotFinish() || record.anatlyserNotifiesError() );
			return errors.map(Record::getError).collect(Collectors.joining(" -- "));
		}
		return "";
	}
	public String  getMmConformanceError (String transformation) {
		if (getMmDoesNotConformToRMM(transformation)) {
			SortedSet<Record> records = getResult(transformation);
			Stream<Record>   errors   = records.stream().filter( record -> record.mmDoesNotConformToRMM() );
			return errors.map(Record::getError).collect(Collectors.joining(" -- "));
		}
		return "";
	}
	
	/** print report to console 
	 * @throws FileNotFoundException */
	public void printToConsole(String temporalFolder) throws FileNotFoundException {
		ReportConsole console = new ReportConsole(new FileOutputStream(temporalFolder + File.separator + resultFileName));
		console.clear();
		long total          = 0;
		long falsePositives = 0;
		long falseNegatives = 0;
		long anatlyserExceptions = 0;
		
		// print header
		console.println("==============================================================");
		console.println("RESULTS");
		console.println("==============================================================");
		
		// print result of each transformation
		for (String transformation : new TreeSet<String>(report.keySet())) {			
			boolean anatlyserNotifiesError   = getAnatlyserNotifiesError(transformation);
			boolean anatlyserDoesNotFinish   = getAnatlyserDoesNotFinish(transformation);
			boolean mmDoesNotConformToRMM    = getMmDoesNotConformToRMM (transformation);
			String  anatlyserError           = getAnatlyserError(transformation);
			String  conformanceError         = getMmConformanceError(transformation);

			if (anatlyserDoesNotFinish) {
				anatlyserExceptions++;
				console.println("    ---> WARNING: anATLyser raised the exception " + anatlyserError );
				continue; // To avoid counting this case
			}

			boolean discrepancy = anatlyserNotifiesError ^ mmDoesNotConformToRMM;						
			if (discrepancy) {
				if (anatlyserNotifiesError) 
					 falsePositives++;
				else falseNegatives++;
				console.println( " ** discrepancy in " + transformation + " --" + 
								 " Anatlyser:   " + (anatlyserNotifiesError? "error "+anatlyserError+" --":"ok --") +
	                             " Conformance: " + (mmDoesNotConformToRMM?  ("error :" + conformanceError):"ok") );
			}			
			else console.println( anatlyserNotifiesError? 
						 		  " ok: " +  transformation + " is incorrect [ANATLYSER =>" + anatlyserError + "; CONFORMANCE => " + conformanceError + "]" :
								  " ok: " +  transformation + " is correct" );	
			
			
			total++;
		}

		if ( invalidMutants.size() > 0 ) {
			console.println("\n\nInvalid mutants:");
			for (String string : invalidMutants) {
				console.println("  * " + string);
			}
			console.println("");
		}
		// print summary
		console.println("==============================================================");
		console.println("SUMARY");
		console.println("==============================================================");
		console.println(" Evaluated transformations = " + total);
		if (total>0) {
			console.println(" Correctly anatlysed ..... = " + (total-falseNegatives-falsePositives) + " (" + (100*(total-falseNegatives-falsePositives)/total) + "%)");
			console.println(" False positives ......... = " + falsePositives + " (" + (100*falsePositives/total) + "%)");
			console.println(" False negatives ......... = " + falseNegatives + " (" + (100*falseNegatives/total) + "%)");
			// if (anatlyserExceptions>0) console.println(" [anATLyser exceptions ... = " + anatlyserExceptions + "]");
			console.println(" [anATLyser exceptions ... = " + anatlyserExceptions + "]");
		}
		
	
		console.display();
	}
	
	/** print report to file **/
	public void print (String folder) {
		if (folder==null || !new File(folder).exists()) return;
		
		PrintWriter xls = null;
		try { xls = new PrintWriter(folder + File.separator + "evaluation.xls", "UTF-8"); } 
	    catch (FileNotFoundException|UnsupportedEncodingException e) {}
		if (xls == null) return;

		Integer numrecords = 0;

		// printer header
		xls.println("MUTANT\t" +
			    "ANATLYSER\t" +
			    "CONFORMANCE\t" +
			    "RESULT\t" +
			    "AN. OUTPUT\t" +
			    "EX. OUTPUT\t" +
			    "AN. EXCEPTION\t" +
			    "ConformsTo\t"+
			    "NotConformsTo\t"+
			    "NotCompatible"
				);
		
		// print result of each transformation
		for (String transformation : new TreeSet<String>(report.keySet())) {
			if ( getAnatlyserDoesNotFinish(transformation) )
				continue;
			
			String conformanceError = getMmConformanceError(transformation);
			numrecords++;
			xls.println(transformation + "\t" + 
		            convert(getAnatlyserNotifiesError(transformation)) + "\t" + 
				    convert(getMmDoesNotConformToRMM(transformation)) + "\t" +
		            "=SI(Y(B" + (numrecords+1) + "=\"error\";C" + (numrecords+1) + "=\"error\"); \"true positive\";" +
		            " SI(Y(B" + (numrecords+1) + "=\"correct\";C" + (numrecords+1) + "=\"correct\"); \"true negative\";" +
		            " SI(Y(B" + (numrecords+1) + "=\"error\";C" + (numrecords+1) + "=\"correct\"); \"false positive\";" +
		            " SI(Y(B" + (numrecords+1) + "=\"correct\";C" + (numrecords+1) + "=\"error\"); \"false negative\"; \"unknown\"))))" + "\t" +
		            (getAnatlyserNotifiesError(transformation)?   convert(getAnatlyserError(transformation)) : "") + "\t" +
		            (conformanceError!=null? convert(conformanceError) : "") + "\t" +
		            (getAnatlyserDoesNotFinish(transformation)? "***WARNING*** anATLyser raised the exception " + convert(getAnatlyserError(transformation)) : "")+ "\t" + 
		            (!conformanceError.contains("Not conform") ? "1" : "") + "\t" +
		            (conformanceError.contains("Not conform") ? "1" : "") + "\t" +
		            (conformanceError.contains("Compatibility") ? "1" : "") + "\t" 
		            );
		}
		
		// print summary
		xls.println();
		xls.println();
		xls.println("\tEvaluated transformations\t"+numrecords);
		xls.println("\tTrue positives\t=CONTAR.SI(D2:D"+(numrecords+1)+";\"true positive\")\t=CONCATENAR(REDONDEAR(C"+(numrecords+5)+"/C"+(numrecords+4)+"%;2);\"%\")");
		xls.println("\tTrue negatives\t=CONTAR.SI(D2:D"+(numrecords+1)+";\"true negative\")\t=CONCATENAR(REDONDEAR(C"+(numrecords+6)+"/C"+(numrecords+4)+"%;2);\"%\")");
		xls.println("\tFalse positives\t=CONTAR.SI(D2:D"+(numrecords+1)+";\"false positive\")\t=CONCATENAR(REDONDEAR(C"+(numrecords+7)+"/C"+(numrecords+4)+"%;2);\"%\")");
		xls.println("\tFalse negatives\t=CONTAR.SI(D2:D"+(numrecords+1)+";\"false negative\")\t=CONCATENAR(REDONDEAR(C"+(numrecords+8)+"/C"+(numrecords+4)+"%;2);\"%\")");
		xls.println("\tPrecision\t=REDONDEAR(C"+(numrecords+5)+"/(C"+(numrecords+5)+"+C"+(numrecords+7)+");2)");
		xls.println("\tRecall\t=REDONDEAR(C"+(numrecords+5)+"/(C"+(numrecords+5)+"+C"+(numrecords+8)+");2)");
		xls.println("\tAccuracy\t=REDONDEAR((C"+(numrecords+5)+"+C"+(numrecords+6)+")/(C"+(numrecords+5)+"+C"+(numrecords+6)+"+C"+(numrecords+7)+"+C"+(numrecords+8)+");2)");
		xls.println("\tF-score\t=REDONDEAR((2*C"+(numrecords+9)+"*C"+(numrecords+10)+")/(C"+(numrecords+9)+"+C"+(numrecords+10)+");2)");
		xls.println("\tAnatlyser exceptions\t=C"+(numrecords+4)+"-CONTAR.BLANCO(G2:G"+(numrecords+1)+")");
		xls.println("");
		xls.println("\tTotal conforms\t=CONTAR(H2:H"+(numrecords+1)+")");
		xls.println("\tTotal NOT conforms\t=CONTAR(I2:I"+(numrecords+1)+")");
		xls.println("\tTotal NOT compatible\t=CONTAR(J2:J"+(numrecords+1)+")");
		
		xls.close();
		
		ReportConsole console = new ReportConsole();
		console.println("\n>> The results of the analysis were saved in file evaluation.xls");
		console.display();
	}
	
	public void printError (String error) {
		ReportConsole console = new ReportConsole();
		console.println(error);
		console.display();
	}
	
	private String convert (boolean value) { return value? "error" : "correct"; }
	private String convert (String  value) { String value2 = value.replaceAll("\\s", " "); return value2.length()<100? value2 : value2.substring(0, 100); }
	
	/** it returns the name of a file, given its full or relative path**/
	private String getFileName(String path) {
		String name = new File(path).getName();
		return name!=null? name : path;
	}
	
	// --------------------------------------------------------------------------------------------------
	// INNER CLASSES AND ENUMERATES
	// --------------------------------------------------------------------------------------------------
	
	enum ERROR_KIND { 
		// anatlyser errors
		ANATLYZER_DETECTED_ERROR, ANATLYZER_DOES_NOT_FINISH,
		// requirements-model errors
		MM_DOES_NOT_CONFORM_TO_RMM
	};
	
	public class Record implements Comparable<Record> {
		
		/** attributes */
		private String     model        = null;
		private String     errorMessage = "";
		private ERROR_KIND errorKind    = null;
		
		public Record (String model) { 
			this.model = model; 
		}
		
		public Record (String model, String error, ERROR_KIND kind) { 
			this(model);
			this.errorMessage = error;
			this.errorKind    = kind;
		}
		
		public String  getModel()                  { return model; }
		public String  getError()                  { return (model!=null? "[witness model "+model+"]: " : "") + errorMessage; }
		public boolean anatlyserNotifiesError ()   { return errorKind==ERROR_KIND.ANATLYZER_DETECTED_ERROR;    }
		public boolean anatlyserDoesNotFinish ()   { return errorKind==ERROR_KIND.ANATLYZER_DOES_NOT_FINISH;   }
		public boolean mmDoesNotConformToRMM  ()   { return errorKind==ERROR_KIND.MM_DOES_NOT_CONFORM_TO_RMM;  }
		
		@Override
		public int compareTo(Record other) {
			// This is needed to allow records for the same model/trafo to be together in the results, but with different kinds
			if (model==null && other.model==null ) return errorKind.compareTo(other.errorKind);
			
			if (model==null && other.model==null) return 0;
			
			if (model==null)       return 1;
			if (other.model==null) return -1;
			
			int r = model.compareTo(other.model);
			// This is needed to allow records for the same model/trafo to be together in the results, but with different kinds
			if ( r == 0 ) {
				return errorKind.compareTo(other.errorKind);
			}			
			return r;
		}
	}

}
