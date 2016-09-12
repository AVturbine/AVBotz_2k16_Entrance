import java.util.List;
import java.io.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class DistanceParser {

	
	public static void main(String[] args) {
		DistanceParser dparser = new DistanceParser();
		String inputFileName = JOptionPane.showInputDialog("What is the name of the input file?");
		String outputFileName = JOptionPane.showInputDialog("What yould you like to call the output file?");
		File fileToParse = dparser.obtainFile(inputFileName);
		List<String> linesParsed = dparser.parseFileByLine(fileToParse);
		List<Integer> distances = new ArrayList<Integer>();
		for (int i = 0; i < linesParsed.size(); i++) {
			List<Integer> currentLineCoords = dparser.parseCurrentLine(linesParsed, i);
			int distance = dparser.doTheMath(currentLineCoords);
			distances.add(distance);
		}
		dparser.makeOutputFile(distances, outputFileName);
		int numOfBigDistances = 0;
		for(int j = 0; j < distances.size(); j++) {
			if (distances.get(j) > 200){
				numOfBigDistances++;
			}
		}
		File outputFile = dparser.obtainFile(outputFileName);
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true))); // dont ask im sorry
			out.println("Distances more than 200: " + Integer.toString(numOfBigDistances));
			out.close();
		}
		catch (IOException ioe) {
			System.out.println(ioe + ": Incorrect file name.");
		}
		
		
		
	}
	public File obtainFile(String fileName) {
		try {
			File directory = new File(".");
			File inputFile = new File(directory.getCanonicalPath() + File.separator + fileName); // Obtains directory the file is in
			return inputFile;
		}
		catch(FileNotFoundException fnfe) {
			System.out.println(fnfe + ": File not found.");
			return null;
		}
		catch(IOException ioe) {
			System.out.println(ioe + ": Directory not found.");
			return null;
		}

	}
	
	public List<String> parseFileByLine (File inputFile) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			String currentLine = null;
			List<String> parsedFile = new ArrayList<String>();
			while((currentLine = reader.readLine()) != null) {
				parsedFile.add(currentLine);
			}
			reader.close();
			return parsedFile;
		}
		catch(IOException ioe) {
			System.out.println(ioe + ": Problem reading file. Are you sure this is is a plaintext file?");
			return null;
		}
	}
	
	public List<Integer> parseCurrentLine(List<String> parsedFile, int lineNumber) {
		List<Integer> parsedLine = new ArrayList<>();
		String currentLine = parsedFile.get(lineNumber);
		String[] splitString = currentLine.split(" ");
		for(int j = 0; j < splitString.length; j++) {
			String thisElement = splitString[j];
			int thisNumber = Integer.parseInt(thisElement);
			parsedLine.add(thisNumber);
		}
		return parsedLine;
		
	}
	
	public int doTheMath(List<Integer> parsedLine) { 
		int x = parsedLine.get(0);
		int y = parsedLine.get(1);
		int z = parsedLine.get(2);
		int distance = (int)(Math.sqrt(Math.pow((0 - x), 2) + Math.pow((0 - y), 2) + Math.pow((0 - z), 2))); // 3d distance formula, x1, y1, z1 are all 0 (origin)
		return distance; 
	}
		
	


	
	
	public void makeOutputFile(List<Integer> distances, String name) {
		try {
			PrintWriter fileWriter = new PrintWriter(name, "UTF-8");
			for(int i = 0; i < distances.size(); i++){
				fileWriter.println(distances.get(i));
			}
			fileWriter.close();
		} 
		catch (FileNotFoundException fnfe) {
			System.out.println(fnfe + ": Please specify a different name.");
			
		} catch (UnsupportedEncodingException uee) {
			System.out.println(uee + ": Invalid encoding.");
		}
	}

}
