package assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 
 * @author MI ONIM
 *
 */
public class Assembler
{
	public static final char COMMENT = '\'';
	public static final String OUTPUT_FILE_NAME = "output.txt";
	
	protected HashMap<String, Integer> _symbolTable;
	protected LookupTable _opcodes;
	protected String _inputFileName;
	protected String _inputFileContent;
	
	public Assembler(String inputFileName) throws AssemblerException
	{
		_symbolTable = new HashMap<>(100);
		_opcodes = new LookupTable();
		_inputFileName = inputFileName;
		_inputFileContent = "";
	}
	
	public void readFile() throws AssemblerException
	{
		File file = new File(_inputFileName);
		if (!file.exists()) {
			throw new AssemblerException("Input file does not exist");
		} //if
		if (!file.canRead()) {
			throw new AssemblerException("Unable to read input file");
		} //if
		if (!file.isFile()) {
			throw new AssemblerException("Not a file");
		} //if
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(_inputFileName));
			String line;
			while ((line = in.readLine()) != null) {
				_inputFileContent += line;
				_inputFileContent += System.getProperty(Utils.LINE_SEPARATOR);
			} //while
			in.close();
		} //try
		catch (IOException e) {
			throw new AssemblerException(e.getMessage());
		} //catch
	}
	
	public void firstPass() throws AssemblerException
	{
		int labelLocation = 0;
		int lineNumber = 0;
		Scanner scanner = new Scanner(_inputFileContent);
		String line;
		
		try {
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				lineNumber++;
				line = line.trim();    //remove leading and trailing whitespace
				
				if (!line.isEmpty() && line.charAt(0) != COMMENT) {
					labelLocation++;
					int labelPos = line.indexOf(':');
					
					if (labelPos >= 0) {   //the line contains a label
						if (labelPos == 0 || !Utils.isAlpha(line.charAt(0))) {
							throw new AssemblerException("Invalid label name at line: " + lineNumber);
						} //if
						
						String labelName = line.substring(0, labelPos);
						_symbolTable.put(labelName, labelLocation);  //stores into HashMap
					} //if
				} //if
			} //while
		} //try
		finally {
			scanner.close();
		} //finally
	}
	
	public String secondPass() throws AssemblerException
	{
		String output = "";
		int lineNumber = 0;
		Scanner scanner = new Scanner(_inputFileContent);
		String line;
		
		try {
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				lineNumber++;
				line = line.trim();
				
				if (!line.isEmpty() && line.charAt(0) != COMMENT) {
					int labelPos = line.indexOf(Utils.COLON);
					int commentPos = line.indexOf(COMMENT);
					
					/* extract the Instruction only - without any label or comment */
					if (labelPos >= 0) {
						line = line.substring(labelPos+1).trim();
					} //if
					if (commentPos >= 0) {
						line = line.substring(0, commentPos).trim();
					} //if
					
					if (line.isEmpty()) continue;
					
					String[] parts = line.split(Utils.WHITESPACE, 2);
					String mnemonic = parts[0].toLowerCase();
					
					if (!_opcodes.hasMnemonic(mnemonic)) {
						throw new AssemblerException("Invalid mnemonic");
					} //if
					Instruction instruction = new Instruction(new Mnemonic(mnemonic, _opcodes.getOpcode(mnemonic)), parts[1]);
					
					output += instruction.getBinary(_symbolTable);
					output += System.getProperty(Utils.LINE_SEPARATOR);
				} //if
			} //while
		} //try
		catch (AssemblerException e) {
			throw new AssemblerException(e.getMessage() + " at line: " + lineNumber);
		} //catch
		finally {
			scanner.close();
		} //finally
		
		return output;
	}
	
	public void createOutputFile(String output) throws AssemblerException
	{
		/* FileWriter is efficient here because writing only once */
		try {
			FileWriter writer = new FileWriter(OUTPUT_FILE_NAME);
			writer.write(output);
			writer.close();
		} //try
		catch (IOException e) {
			throw new AssemblerException(e.getMessage());
        } //catch
	}
	
	public void run() throws AssemblerException
	{
		readFile();
		firstPass();
		String output = secondPass();
		createOutputFile(output);
		System.out.println("Successfully Assembled!");
		System.out.println("The output file created is: " + OUTPUT_FILE_NAME);
	}
}
