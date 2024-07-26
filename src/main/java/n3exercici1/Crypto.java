package n3exercici1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
	
	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
	private static final String SALT = "saltytastesbetter";
	
	
	public static void main(String[] args) throws IllegalBlockSizeException, BadPaddingException, IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException {
		Path input = Path.of("sensible.txt").normalize();
		Path output = Path.of("cifrado.txt").normalize();
		Path output2 = Path.of("recuperado.txt").normalize();
		
		
		encryptFile(input, output, "password");
		decryptFile(output, output2, "password");
	}
	
	
	

	public static void encryptFile(Path inputFile, Path outputFile, String password) 
			throws NoSuchAlgorithmException,NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException,
			InvalidAlgorithmParameterException, IOException, IllegalBlockSizeException, BadPaddingException {
	
		byte[] iv = IV.generate();
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		Key key = getKeyFromPassword(password, SALT);
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
		
		try (InputStream input = Files.newInputStream(inputFile);
				OutputStream output = Files.newOutputStream
				(outputFile, StandardOpenOption.CREATE,	StandardOpenOption.TRUNCATE_EXISTING)){
			
			output.write(iv);
			
			byte[] buffer = new byte[64];
			int bytesRead = input.read(buffer);
			while (bytesRead != -1) {
				output.write(cipher.update(buffer, 0, bytesRead));
				bytesRead = input.read(buffer);
			}
			output.write(cipher.doFinal());
		}
	}
	
	
	
	public static void decryptFile(Path inputFile, Path outputFile, String password) throws IOException,
	NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,
	InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
		
		try (InputStream input = Files.newInputStream(inputFile);
				OutputStream output = Files.newOutputStream
				(outputFile, StandardOpenOption.CREATE,	StandardOpenOption.TRUNCATE_EXISTING)){
			
			IvParameterSpec iv = IV.retrieve(input.readNBytes(16));
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			Key key = getKeyFromPassword(password, SALT);
			cipher.init(Cipher.DECRYPT_MODE, key, iv);
			
			byte[] buffer = new byte[64];
			int bytesRead = input.read(buffer);
			while (bytesRead != -1) {
				output.write(cipher.update(buffer, 0, bytesRead));
				bytesRead = input.read(buffer);
			}
			output.write(cipher.doFinal());
		}
		
	}
	
		
	
	private static SecretKey getKeyFromPassword(String password, String salt)
		    throws NoSuchAlgorithmException, InvalidKeySpecException {

		    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
		    SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
		        .getEncoded(), "AES");
		    return secret;
		}
	
	
	static class IV {
			
		static byte[] generate() {
			byte[] iv = new byte[16];
			new SecureRandom().nextBytes(iv);
			return iv;
		}
		
		static IvParameterSpec retrieve(byte[] bytes) {
			if (bytes.length == 16) {
				return new IvParameterSpec(bytes);
			}else {
				throw new RuntimeException("Error retrieving IV. It must have 16 bytes.");
			}
		}
		
	}
	
	

	

	
}
