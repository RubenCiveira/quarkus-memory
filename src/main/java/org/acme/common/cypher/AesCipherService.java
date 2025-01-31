/* @autogenerated */
package org.acme.common.cypher;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.ProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.text.StringEscapeUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Utilidad para cifrar y descifrar.
 */
@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class AesCipherService {
  /**
   * Algoritmo de encriptacion
   */
  private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
  /**
   * Longitud del tag must be one of {128, 120, 112, 104, 96}
   */
  private static final int TAG_LENGTH_BIT = 128;
  /**
   * Longitud de IV
   */
  private static final int IV_LENGTH_BYTE = 12;
  /**
   * Longitud de salt
   */
  private static final int SALT_LENGTH_BYTE = 16;

  @ConfigProperty(name = "app.security.encryption.key")
  private final String cipherKey;

  /**
   * Encripta un texto usando una clave de aplicación
   * 
   * @param value
   * @return
   */
  public String encryptForAll(String value) {
    return encrypt(value, cipherKey);
  }

  /**
   * Encripta un texto usando una clave genérica
   * 
   * @param value El texto plano a encriptar
   * @param password La clave
   * @return El texto cifrado
   * @throws GeneralSecurityException
   */
  public String encrypt(String value, String password) {
    try {
      byte[] pText = value.getBytes(StandardCharsets.UTF_8);

      // Generate random salt and IV
      byte[] salt = getRandomNonce(SALT_LENGTH_BYTE);
      byte[] iv = getRandomNonce(IV_LENGTH_BYTE);

      // Derive the key from the password using PBKDF2
      SecretKey aesKeyFromPassword = getAESKeyFromPassword(password.toCharArray(), salt);

      Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
      cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword,
          new GCMParameterSpec(TAG_LENGTH_BIT, iv));

      byte[] cipherText = cipher.doFinal(pText);

      // Combine IV, salt, and ciphertext
      byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length)
          .put(iv).put(salt).put(cipherText).array();

      // Convert to Base64
      return Base64.getEncoder().encodeToString(cipherTextWithIvSalt);
    } catch (GeneralSecurityException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * 
   * @param cText
   * @return
   */
  public Optional<String> decryptForAll(String cText) {
    return decrypt(cText, cipherKey);
  }

  /**
   * Desencripta un texto usando una clave genérica
   * 
   * @param cText el texto cifrado
   * @param password la contraseña
   * @return El texto fue cifrado con la contraseña, un opcional con el texto descifrado. Un
   *         opcional vacio si no fué cifrado.
   * @throws GeneralSecurityException
   */
  public Optional<String> decrypt(String cipherText, String password) {
    try {
      byte[] decode = Base64.getDecoder().decode(cipherText);

      // get back the iv and salt from the cipher text
      ByteBuffer bb = ByteBuffer.wrap(decode);

      byte[] iv = new byte[IV_LENGTH_BYTE];
      bb.get(iv);

      byte[] salt = new byte[SALT_LENGTH_BYTE];
      bb.get(salt);

      byte[] cipherBytes = new byte[bb.remaining()];
      bb.get(cipherBytes);

      // get back the aes key from the same password and salt
      SecretKey aesKeyFromPassword = getAESKeyFromPassword(password.toCharArray(), salt);

      Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

      cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword,
          new GCMParameterSpec(TAG_LENGTH_BIT, iv));

      byte[] plainText = cipher.doFinal(cipherBytes);

      return Optional.of(new String(plainText, StandardCharsets.UTF_8));
    } catch (BufferUnderflowException | IllegalArgumentException | GeneralSecurityException
        | ProviderException ex) {
      if (log.isErrorEnabled()) {
        log.error("Unable to decript text for {}", StringEscapeUtils.escapeJson(ex.getMessage()));
      }
      return Optional.empty();
    }
  }

  public String generateSecureRandomApiSecret() {
    Stream<Character> pwdStream = Stream.concat(getRandomNumbers(2),
        Stream.concat(getRandomSpecialChars(2),
            Stream.concat(getRandomAlphabets(2, true),
                Stream.concat(getRandomSpecialChars(3), Stream.concat(getRandomAlphabets(10, false),
                    Stream.concat(getRandomNumbers(4), getRandomAlphabets(10, false)))))));
    List<Character> charList = pwdStream.collect(Collectors.toList());
    Collections.shuffle(charList);
    return charList.stream()
        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
  }

  public String generateSecureRandomPassword() {
    Stream<Character> pwdStream =
        Stream.concat(getRandomNumbers(2), Stream.concat(getRandomSpecialChars(2),
            Stream.concat(getRandomAlphabets(2, true), getRandomAlphabets(4, false))));
    List<Character> charList = pwdStream.collect(Collectors.toList());
    Collections.shuffle(charList);
    return charList.stream()
        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
  }

  private Stream<Character> getRandomAlphabets(int count, boolean uppercase) {
    Random random = new SecureRandom();
    IntStream specialChars = random.ints(count, uppercase ? 65 : 97, uppercase ? 90 : 122);
    return specialChars.mapToObj(data -> (char) data);
  }

  private Stream<Character> getRandomNumbers(int count) {
    Random random = new SecureRandom();
    IntStream specialChars = random.ints(count, 48, 57);
    return specialChars.mapToObj(data -> (char) data);
  }

  public Stream<Character> getRandomSpecialChars(int count) {
    Random random = new SecureRandom();
    IntStream specialChars = random.ints(count, 33, 45);
    return specialChars.mapToObj(data -> (char) data);
  }

  private static byte[] getRandomNonce(int numBytes) {
    byte[] nonce = new byte[numBytes];
    new SecureRandom().nextBytes(nonce);
    return nonce;
  }

  private static SecretKey getAESKeyFromPassword(char[] password, byte[] salt)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    KeySpec spec = new PBEKeySpec(password, salt, 100000, 256);
    return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
  }

}
