package application;

import java.util.*;
import java.io.*;

public class Main {
	//The actual folder the program is working in
	private static File wd = new File(System.getProperty("user.dir"));
	
	//The main method that controls the program
	public static void main(String[] args) {
		System.out.println("You can list the commands with the |help| command");
		Scanner s = new Scanner(System.in);
		
		//Infinite loop that reads and splits from the standard input
		//The needed methods get called here
		while(true) {
			pwd();
			String[] cmd = s.nextLine().split(" ");
			if(cmd[0].equals("exit")) {
				exit();
			}
			else if(cmd[0].equals("ls")) {
				ls(cmd);
			}
			else if(cmd[0].equals("cd")) {
				cd(cmd);
			}
			else if(cmd[0].equals("rm")) {
				rm(cmd);
			}
			else if(cmd[0].equals("mkdir")) {
				mkdir(cmd);
			}
			else if(cmd[0].equals("mv")) {
				mv(cmd);
			}
			else if(cmd[0].equals("cp")) {
				cp(cmd);
			}
			else if(cmd[0].equals("cat")) {
				cat(cmd);
			}
			else if(cmd[0].equals("help")) {
				help();
			}
			else if(cmd[0].equals("length")) {
				length(cmd);
			}
			else if(cmd[0].equals("head")) {
				head(cmd);
			}
		}
	}
	//Prints the first n lines of the file given by a parameter
	public static void head(String[] cmd) {
		if(!cmd[1].equals("-n")) {
			System.err.println("Command error: Command not recognised, did you mean help -n ?");
		}else if(cmd.length == 4) {
			try {
				FileReader fr = new FileReader(wd.getCanonicalPath() + "/" + cmd[3]);
				BufferedReader br = new BufferedReader(fr);
				String line;
				for(int i = 0; i < Integer.valueOf(cmd[2]); i++) {
					if((line = br.readLine()) != null) {
						System.out.println(line);
					}else {
						break;
					}
				}
			}catch (FileNotFoundException e) {
				System.err.println("Command Error: File not found");
			}catch (IOException e) {
				e.printStackTrace();
			}
		}else if(cmd.length == 3) {
			try {
				FileReader fr = new FileReader(wd.getCanonicalPath() + "/" + cmd[2]);
				BufferedReader br = new BufferedReader(fr);
				String line;
				for(int i = 0; i < 10; i++) {
					if((line = br.readLine()) != null) {
						System.out.println(line);
					}else {
						break;
					}
				}
			}catch (FileNotFoundException e) {
				System.err.println("Command Error: File not found");
			}catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			System.err.println("Command Error: No proper parameter count");
		}
	}
	//Prints the length of the file given as a parameter
	public static void length(String[] cmd) {
		if(cmd.length == 2) {
			try {
				File f = new File(wd.getCanonicalPath() + "/" + cmd[1]);
				if(f.exists()) {
					System.out.println(f.length() + " byte");
				}else {
					System.err.println("Command Error: File not found");
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			System.err.println("Command Error: No proper parameter count");
		}
	}
	//Lists all the commands
	public static void help() {
		System.out.println("-- COMMANDS --");
		System.out.println();
		System.out.println("cat <file> - Reads the contents of the file");
		System.out.println("cp <source> <destination> - Copies the contents of the source file and pastes them into the destination file");
		System.out.println("mv <file> - Renames the file");
		System.out.println("mkdir <folder name> - Creates folder by the name added as the parameter");
		System.out.println("rm <file> - Removes the file");
		System.out.println("cd <folder> - Enters the directory added as the parameter");
		System.out.println("ls - Lists the content of the current directory, has an optional \"-l\" parameter that will list the size and type of the contained files");
		System.out.println("length <file> - Gives back the length of the file given as the parameter");
		System.out.println("head -n <n> <file> - Prints the first n lines of the file, given by the third (optional) and fourth parameters. If the third parameter is missing then it will print the first 10 lines");
		System.out.println("exit - Terminates the program");
	}
	//Reads the contents of the file added as a parameter
	public static void cat(String[] cmd) {
		if(cmd.length == 2) {
			try {
				FileReader fr = new FileReader(wd.getCanonicalPath() + "/" + cmd[1]);
				BufferedReader br = new BufferedReader(fr);
				String line;
				while((line = br.readLine()) != null){
					System.out.println(line);
				}
			}catch (FileNotFoundException e) {
				System.err.println("Command Error: File not found");
			}catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			System.err.println("Command Error: No proper parameter count");
		}
	}
	//Copies the contents of a file referred by the first parameter to the file referred by the second
	public static void cp(String[] cmd) {
		if(cmd.length == 3) {
			try {
				//Initializes the needed files and streams for copying
				File dirin = new File(wd.getCanonicalPath() + "/" + cmd[1]);
				File dirout = new File(wd.getCanonicalPath() + "/" + cmd[2]);
				FileInputStream fis = new FileInputStream(dirin);
				FileOutputStream fos = new FileOutputStream(dirout);
				int i = 0;
				String result = "";
				while((i = fis.read()) != -1) {
					result += (char)i;
				}
				byte[] resultB = result.getBytes();
				fos.write(resultB);
				fis.close();
				fos.flush();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			System.err.println("Command Error: No proper parameter count");
		}
	}
	//Renames the folder referred by the first parameter to the name referred by the second
	public static void mv(String[] cmd) {
		if(cmd.length == 3) {
			File dir = null;
			//Creates the file that the program rename
			try {
				dir = new File(wd.getCanonicalPath() + "/" + cmd[1]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//Checks if the folder exists, if so then it renames it
			if(dir.renameTo(new File(cmd[2]))) {
				System.out.println("Renamed succesfully");
			}else {
				System.err.println("Command Error: Couldn't rename");
			}
		}else {
			System.err.println("Command Error: No proper parameter count");
		}
	}
	//Creates the folder pointed by the parameter
	public static void mkdir(String[] cmd) {
		if(cmd.length == 2) {
			File dir = null;
			//Creates the file that the program should make
			try {
				dir = new File(wd.getCanonicalPath() + "/" + cmd[1]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//Checks if the folder exists, if so then it makes it
			if(dir.mkdir()) {
				System.out.println("Made file succesfully");
			}else {
				System.err.println("Command Error: Couldn't make the folder");
			}
		}else {
			System.err.println("Command Error: No proper parameter count");
		}
	}
	//Removes the file pointed by the parameter
	public static void rm(String[] cmd) {
		if(cmd.length == 2) {
			File dir = null;
			//Creates the file that the program should delete
			try {
				dir = new File(wd.getCanonicalPath() + "/" + cmd[1]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//Checks if the folder exists, if so then it deletes it
			if(dir.delete()) {
				System.out.println("Deleted file succesfully");
			}else {
				System.err.println("Command Error: No folder or file found by this parameter");
			}
		}else {
			System.err.println("Command Error: No proper parameter count");
		}
	}
	//Moves the "wd" folder around
	public static void cd(String[] cmd) {
		if(cmd.length == 2) {
			File dir = null;
			//Creates the file that the program should navigate to
			try {
				dir = new File(wd.getCanonicalPath() + "/" + cmd[1]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//Checks if the folder exists, if so then it navigates to it
			if(dir.exists()) {
				if(cmd[1].equals("..")){
					wd = wd.getParentFile();
				}else {
					wd = dir;
				}
			}else {
				System.err.println("Command Error: No folder found by this parameter");
			}
		}else {
			System.err.println("Command Error: No proper parameter count");
		}
	}
	//Lists the contained files of the "wd" folder
	//Has an optional -l parameter that makes the command list the size and type of the contained files
	public static void ls(String[] cmd) {
		File[] files = wd.listFiles();
		//The command got one parameter
		if(cmd.length == 1) {
			for(int i = 0; i < files.length; i++)
					System.out.println(files[i].getName());
		//The command got two parameters
		}else if(cmd.length == 2) {
			if(cmd[1].equals("-l")) {
				for(int i = 0; i < files.length; i++) {
					if(files[i].isDirectory())
						System.out.println(files[i].getName() + '\t' + files[i].length() + " byte" + "\td");
					else
						System.out.println(files[i].getName() + '\t' + files[i].length() + " byte" + "\tf");
				}
			}else
				System.err.println("Command Error: Wrong parameter");
		}else
			System.err.println("Command Error: No proper parameter count");
	}
	//Prints the name of the "wd" directory
	public static void pwd() {
		try {
			String dirName = wd.getCanonicalPath();
			System.out.print(dirName + " ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//Terminates the program
	public static void exit() {
		System.exit(0);
	}
}