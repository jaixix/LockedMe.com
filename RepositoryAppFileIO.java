package repositoryAppFileIO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RepositoryAppFileIO {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		List<String> fileNamesC = new ArrayList<String>();
		List<String> fileNamesD = new ArrayList<String>();
		
		Map<String,ArrayList<String>> directory = new HashMap<String,ArrayList<String>>();
		
		displayWelcomeScreen(); //Displaying Welcome Message.
		
		loadDefaultFiles(fileNamesC, fileNamesD, directory); //Pre-added sample filesand folders.
		
		
		int decision = 1; //Int var. to store if User wants to continue performing operations or stop.
		
		do {
			String directoryName = getDirectoryChoice(sc);
			featuresList(); //To display features/functionalities of the program.
			int input=100;
			try {
			System.out.println("Please provide a non-decimal Numeric Input corresponding to the above available options/operations : (1, 2 or 3) to continue!");
			input = sc.nextInt();
			
			} catch(InputMismatchException e) { //Exception Handling to prevent program from stopping on unexpected Inputs.
				sc.nextLine();
			}
			switch(input) {
				case 1 :
					displayFiles(directory, directoryName); //Method to display existing files in current directory.
					break;
				case 2 :
					repOperations(sc, directory,directoryName); //Method to perform ops. in the current directory.
					break;
				case 3 :
					exitProgram(); //Method to exit the program.
					break;
				default :
					System.out.println("Oops! Looks like you provided a wrong input, please follow Input instructions specified above.");
					
			}
		continueStatements(); //Asking user's choice to continue or stop.
		try {
			decision = sc.nextInt();
			sc.nextLine();
			} catch (Exception e) { //Exception Handling to prevent program from stopping on unexpected Inputs.
				System.out.println("You have provided a wrong Input! Please follow Input Instructions.\n");
				sc.nextLine();
			}
		} while(decision == 1);
		System.out.println("Thankyou for using this program!");
	}
	
	public static void exitProgram() {
		System.out.println("Exited Successfully..Thankyou for using this program!");
		System.exit(0);
	}
	
	public static void repOperations(Scanner sc, Map<String, ArrayList<String>> directory,String directoryName) {
		System.out.println("Please Input a numeric value for required Operations : \n");
		System.out.println("1) Add a file to the current directory - "+ directoryName +":\\\\");
		System.out.println("2) Delete a specific file from the current directory - "+ directoryName +":\\\\");
		System.out.println("3) Search for a specific file from the current directory - "+ directoryName +":\\\\");
		System.out.println("4) Return to Root Directory/Previous Menu of the current directory - "+ directoryName +":\\\\");
		int inputOp=100;
		try {
		inputOp = sc.nextInt();
		} catch(InputMismatchException e) {
			System.out.println("You have provided a wrong Input! Please follow Input Instructions.");
		}
		switch(inputOp) { //Second switch case to get input for specific op.
			case 1 :
				sc.nextLine();
				addFile(sc, directory, directoryName); //Method to add a new file to current directory.
				break;
			case 2 :
				sc.nextLine();
				deleteFile(sc, directory,directoryName); //Method to delete a file from current directory.
				break;
			case 3 :
				sc.nextLine();
				searchFile(sc, directory,directoryName); //Method to search for a file in current directory.
				break;
			case 4 :
				if(directoryName.equalsIgnoreCase("Root")) {
					sc.nextLine();
					System.out.println("Taking you to the Welcome Screen! \n");
					displayWelcomeScreen();
					featuresList(); //Redirecting to previous menu.
				}
				else {
					sc.nextLine();
					System.out.println("Taking you to the ROOT:\\\\ Directory Operations! \n");
//					featuresList();
					repOperations(sc, directory,"ROOT");
				}
				break;
			default :
				System.out.println("Oops! Looks like you provided a wrong input, please follow Input instructions specified above.");
		}
	}
	
	public static void searchFile(Scanner sc, Map<String, ArrayList<String>> directory,String directoryName) {
		System.out.println("Please enter the name of file to be SEARCHED : [Case Sensitive]");
		String searchFile = sc.nextLine();
		if(directory.get(directoryName).contains(searchFile)) {
			System.out.println("Success! '" + searchFile + "' : Found Successfully in "+ directoryName +":\\\\");
		}
		else
			System.out.println("Sorry! '" + searchFile + "' : Not Found in "+ directoryName +":\\\\");
	}
	
	public static void deleteFile(Scanner sc, Map<String, ArrayList<String>> directory,String directoryName) {
		System.out.println("Please enter the name of file to be DELETED : [Case Sensitive]");
		String deleteFile = sc.nextLine();
		if(directory.get(directoryName).contains(deleteFile)) {
			directory.get(directoryName).remove(deleteFile);
			System.out.println("'" + deleteFile + "' : Deleted Successfully!");
			if(directoryName.equalsIgnoreCase("root")) {
				String tempDeleteFile = "C:\\folder\\" + deleteFile;
				try {
		            Files.delete(Paths.get(tempDeleteFile));
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			}
			else {
				String tempDeleteFile = "C:\\D\\" + deleteFile;
				try {
		            Files.delete(Paths.get(tempDeleteFile));
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			}
		}
		else
			System.out.println(deleteFile + " : File Not Found in "+ directoryName +":\\\\");
	}
	
	public static void addFile(Scanner sc, Map<String, ArrayList<String>> directory,String directoryName) {
		System.out.println("Please enter the name of file to be ADDED : ");
		String newFile = sc.nextLine();
		
		if(directory.get(directoryName).contains(newFile))
			System.out.println("File already exists in "+ directoryName +":\\\\");
		else {
			try {
				if(directoryName.equalsIgnoreCase("root")) {
					FileOutputStream fos = new FileOutputStream(("C:\\folder\\" + newFile));
					fos.write(0);
					fos.close();
				}
				else {
					FileOutputStream fos = new FileOutputStream(("C:\\D\\" + newFile));
					fos.write(0);
					fos.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			directory.get(directoryName).add(newFile);
			System.out.println("'" + newFile + "' : Added Successfully!");
		}
	}

	public static String getDirectoryChoice(Scanner sc) {
		System.out.println("Please Enter Directory Name to perform operations");
		System.out.println("Available Directories : Root and D");
		String directoryName = sc.nextLine().toUpperCase();
		
//		while((!directoryName.equalsIgnoreCase("ROOT")) && (!directoryName.equalsIgnoreCase("D"))) {
//			System.out.println("\nOops! Wrong Input. Please enter Input as mentioned above.\n");
//			getDirectoryChoice(sc);
//		}
		if((!directoryName.equalsIgnoreCase("ROOT")) && (!directoryName.equalsIgnoreCase("D"))) {
			System.out.println("\nOops! Wrong Input. Please enter Input as mentioned above.\n");
			getDirectoryChoice(sc);
		}
		System.out.println("{"+directoryName+"}");
		return directoryName;
	}
	
	
	
	public static void displayFiles(Map<String, ArrayList<String>> directory, String directoryName) {
		System.out.println("Files in Current Directory - " + directoryName + ":\\\\");
		Collections.sort(directory.get(directoryName));
//		directory.get(directoryName).sort(Comparator.naturalOrder());
		System.out.printf("%15s %18s %29s", "Directory", "Type","File Name");
		System.out.println();
		System.out.println("-----------------------------------------------------------------------");
		Iterator<String> it = directory.get(directoryName).iterator();
		if(directoryName.equalsIgnoreCase("Root")) {
			System.out.format("%11s %23s %27s", "D", "Folder", "-");
			System.out.println();
			System.out.println("-----------------------------------------------------------------------");
		}
		while(it.hasNext()) {
			System.out.format("%13s %20s %32s", directoryName, "File", it.next());
			System.out.println();
			System.out.println("-----------------------------------------------------------------------");
		}
		System.out.println("Total Number Of Files Present : " + directory.get(directoryName).size());
	}

	public static void continueStatements() {
		System.out.println("\n");
		System.out.println("-----------------------------------------------------------------");
		System.out.println("Do you wish to continue using the program?");
		System.out.println("Enter 1 to CONTINUE or ANY OTHER DIGIT to EXIT");
	}
	
	public static void featuresList() {
		System.out.println("FEATURES of this Program : \n");
		System.out.println("1 Display Files of Current Directory");
		System.out.println("2 Operations Menu");
		System.out.println("3 Exit the program");
		System.out.println("-----------------------------------------------------------------");
	}

	public static void loadDefaultFiles(List<String> fileNamesC, List<String> fileNamesD, Map<String,ArrayList<String>> directory) {
		fileNamesC.add("sampleFile1.txt");
		fileNamesC.add("sampleFile2.js");
		fileNamesC.add("sampleFile3.html");
		directory.put("ROOT", (ArrayList<String>) fileNamesC);
		for(String fileName: fileNamesC) {
			try {
					FileOutputStream fos = new FileOutputStream(("C:\\folder\\" + fileName));
					fos.write(0);
					fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		fileNamesD.add("sampleFile1.txt");
		fileNamesD.add("sampleFile2.js");
		fileNamesD.add("sampleFile3.html");
		directory.put("D", (ArrayList<String>) fileNamesD);
		for(String fileName: fileNamesD) {
			try {
					FileOutputStream fos = new FileOutputStream(("C:\\D\\" + fileName));
					fos.write(0);
					fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void displayWelcomeScreen() {
			System.out.println("-----------------------------------------------------------------");
			System.out.println("  WELCOME TO LockedMe.com! A 'Company Lockers Pvt. Ltd.' PRODUCT ");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("               Developed by : JAIDEEP LALCHANDANI                ");
			System.out.println("-----------------------------------------------------------------");
			System.out.println();
	}

}
