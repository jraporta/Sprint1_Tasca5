package n1exercici1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AppTest {
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	
//	@BeforeEach
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}
	
//	@AfterEach
	public void tearDown() {
		System.setOut(System.out);
	}

	
	@DisplayName(value = "Si no rep cap paràmetre mostra el missatge: \"expected: path of a directory\"")
	@Test
	public void test1() {
		setUpStreams();
		App.main(new String[0]);
		assertEquals("expected: path of a directory", outContent.toString().trim());
		tearDown();
	}
	
	@DisplayName(value = "Test unitari de llegeixPath()")
	@Test
	public void test2() throws IOException {
		//"Si el paràmetre rebut no és un path vàlid d'un directori retorna null"
		String path = "c:\\CarpetaNoExistent\\CarpetaImprobable";
		assertEquals(null, App.llegeixPath(path));
		//"Si el paràmetre rebut és un arxiu retorna null"
		Path pathEsborra = Path.of("esborrar\\esborrar.txt").toAbsolutePath();
		try{
			Files.createDirectory(pathEsborra.getParent());
			Files.createFile(pathEsborra);
			assertEquals(null, App.llegeixPath(pathEsborra.toString()));
			//"Si el paràmetre rebut és un directori retorna un path no null"
			assertNotNull(pathEsborra.getParent());
		}finally {
			Files.deleteIfExists(pathEsborra);
			Files.deleteIfExists(pathEsborra.getParent());
		}
	}

}