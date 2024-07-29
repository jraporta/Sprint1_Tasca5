Summary of commands:

compiling the project: mvn clean install
running the program from the project directory: java jar target\sprint1-tasca5-1
		  
n1exercici1:	Show contents of a directory: 'java -jar target\sprint1-tasca5-1.jar' followed by the path.
	
	- using relative paths: java -jar target\sprint1-tasca5-1.jar target
	- using absolute paths: java -jar target\sprint1-tasca5-1.jar c:\users
	
n1exercici2: 	Write at the end of the command the modifiers -a(show all contents), -t(show type of files) and/or
				-d(show last modification date). The commands can be combined as wished.
	
	-example to get information of the current directory: java -jar target\sprint-tasca5-1.jar .\ -t -a -d
	
n1exercici3: 	use modifier -f to output the data to the file 'output.txt' which will be created in the current directory.
	
	example: java -jar target\sprint-tasca5-1.jar ..\ -f -a
	
n1exercici4: 	To view the content of a .txt file use the key word 'read' followed by the file path.
	
	example: java -jar target\sprint-tasca5-1.jar read .\output.txt
	
n1exercici5: 	The app remembers the last directory explored as it automatically serializes it's path.
				Use keyword last + any combination of the modifiers listed above.
	
	exemple: java -jar target\sprint1-tasca5-1.jar last
 	exemple: java -jar target\sprint1-tasca5-1.jar last -t -a

n2exercici1:	Changed the way to call the different functions of the program:
				- Show the contents of directory (dir + path): java -jar target\sprint1-tasca5-1.jar dir .\
				- Show the contents of las directory (dir): java -jar target\sprint1-tasca5-1.jar dir
				- Display the contents of a txt file (read + path): java -jar target\sprint1-tasca5-1.jar read output.txt
				- Editing the configuration settings (config): java -jar target\sprint1-tasca5-1.jar config
				- Ask for help: java -jar target\sprint1-tasca5-1.jar -h
	
 n2exercici3:	encrypt and decrypt files using the AES algorithm, with CBC mode and PKCS5Padding.
 	
 	example: java -jar target\sprint1-tasca5-1.jar encrypt document.txt
 	example: java -jar target\sprint1-tasca5-1.jar decrypt document.txt.secret