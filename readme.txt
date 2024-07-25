Compilació del projecte Maven: mvn clean install
Execució de l'aplicació: java jar target\nom-de-arxiu.jar
		  en aquest cas: java jar sprint1-tasca5-1


n1exercici1:
	- Executar des de la carpeta del projecte 'java -jar target\sprint1-tasca5-1.jar' seguit de la ruta de la carpeta.
	- prova de ruta relativa: java -jar target\sprint1-tasca5-1.jar target
	- prova de ruta absoluta: java -jar target\sprint1-tasca5-1.jar c:\users
	
n1exercici2: Write at the end of the command the modifiers -a(show all contents), -t(show type of files), -d(show last modification date).
	the commands can be combined as wished.
	
	example to get information of the current directory: java -jar target\sprint-tasca5-1.jar .\ -t -a -d
	
n1exercici3: use modifier -f to output the data to the file output.txt which will be created int the current directory.
	
	example: java -jar target\sprint-tasca5-1.jar ..\ -f -a
	
n1exercici4: can read txt files. Use key word 'read' + the file path.
	
	example: java -jar target\sprint-tasca5-1.jar ..\ read .\output.txt
	
n1exercici5: remembers the last directory explored. Use keyword last + any combination of the modifiers listed above.
	
	exemple: java -jar target\sprint1-tasca5-1.jar last
 	exemple: java -jar target\sprint1-tasca5-1.jar last -t -a