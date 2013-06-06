import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class HOTP {
    private Mac mac;
    
    /**
     * @author aritzbi
     * Algoritmos de hasheo implementados en Java.
     */
    public enum AlgorithmType{
        SHA1,SHA256,MD5;
    }

    /**
     * Constructor de la clase HOTP
     * @param alg El tipo de algoritmo, SHA1 o SHA256 o MD5.
     * @param digits De momento no sirve para nada pero en futuro para limitar la longitud de la clave.
     * @param passPhrase La semilla.
     */
    public HOTP(AlgorithmType alg, int digits,final byte[] passPhrase){
        try {
        	System.out.println("Tipo de algoritmo: "+alg);
        	System.out.println(passPhrase);
        	//Creaci�n con ning�n algoritmo asociado
            SecretKeySpec key=new SecretKeySpec(passPhrase,"raw");
            //Asignaci�n del algoritmo de hasheo a usar.
            mac=Mac.getInstance("Hmac"+alg);
            //Asignaci�n de la semilla al mac.
            mac.init(key);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }
    /**
     * Generaci�n del OTP.
     * @param counter Se envia como parametros el contador que se ir� decrementando.
     * @return La clave OTP de 10 dígitos.
     */
    public int generateHTOPPassword(long counter){
    	//Se genera un array de bytes desde el contador
        byte [] bytes=ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN).putLong(counter).array();
        System.out.println("Counter: " +counter);
        System.out.println("bytes del contador: "+counter);
        //Se aplica el algoritmo HMAC al contador que est� en bytes, devolviendo 20 bytes que ser�n un string.
        byte []resultingHash=mac.doFinal(bytes);
        System.out.println("Hash resultate: "+resultingHash);
        //Se consigue el offset con los ultimos 4 bytes (0*0F=0000 1111) del �ltimo byte del resultado.
        System.out.println(resultingHash[19]);
        int offset=resultingHash[19]& 0x0F ;
        System.out.println("Offset: "+offset);
        //Devuelve el valor int del byte que est� en la posici�n definida por el offset y se asegura que siempre sea positivo con 0x7FFFFFFF
        int password=((ByteBuffer)ByteBuffer.wrap(resultingHash).order(ByteOrder.BIG_ENDIAN).position(offset)).getInt()& 0x7FFFFFFF;
        System.out.println("OTP:" +password);
        return password;
    }

}

