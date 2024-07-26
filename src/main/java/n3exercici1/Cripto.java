package n3exercici1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.*;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

public class Cripto {
	
	static Key key = null;
	
	static {
		KeyGenerator keyGen;
		try {
			keyGen = KeyGenerator.getInstance("AES");
			key = keyGen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws IllegalBlockSizeException, BadPaddingException, IOException {
		encript("output.txt");
		decript("cypheredMaterial.txt");
	}
	
	private static void decript(String s) throws IllegalBlockSizeException, BadPaddingException, IOException {
		// TODO Auto-generated method stub

		Cipher cipher;
		
		try {
			cipher = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Path inputPath = Path.of(s).normalize();
//		BufferedReader input = Files.newBufferedReader(inputPath);
		
		Path outputPath = Path.of("restoredMaterial.txt").normalize();
//		Files.createFile(inputPath);
//		BufferedWriter output = Files.newBufferedWriter(outputPath, StandardOpenOption.CREATE);
		
		
		byte[] cipheredMsg = cipher.doFinal(Files.readAllBytes(inputPath));
		
		System.out.println(cipher.getAlgorithm());
		
		Files.write(outputPath, cipheredMsg, StandardOpenOption.CREATE);

		
	}

	public static void encript(String s) throws IOException, IllegalBlockSizeException, BadPaddingException {
//		char[] password = "password".toCharArray();		
		
		
		Cipher cipher;
		
		try {
			cipher = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		
		
//		Cipher cipher2 = Cipher.getInstance(CBC);
//		Cipher cipher3 = Cipher.getInstance(PKCS5Padding);
		
//		KeyStore keyStore;
//		try {
//			keyStore = KeyStore.getInstance("pkcs12");
//		} catch (KeyStoreException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return;
//		}
		
//		try {
//			keyStore.setKeyEntry("Key for the ITAcademy tasks", key, password, null);
//		} catch (KeyStoreException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Path inputPath = Path.of(s).normalize();
//		BufferedReader input = Files.newBufferedReader(inputPath);
		
		Path outputPath = Path.of("cypheredMaterial.txt").normalize();
//		Files.createFile(inputPath);
//		BufferedWriter output = Files.newBufferedWriter(outputPath, StandardOpenOption.CREATE);
		
		
		byte[] cipheredMsg = cipher.doFinal(Files.readAllBytes(inputPath));
		
		Files.write(outputPath, cipheredMsg, StandardOpenOption.CREATE);
		
	}

}
