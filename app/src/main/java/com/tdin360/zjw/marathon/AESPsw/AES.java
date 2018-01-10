package com.tdin360.zjw.marathon.AESPsw;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by jeffery on 2017/12/5.
 */

public class AES {
    private static final String KEY_GENERATION_ALG="PBEWITHSHAANDTWOFISH-CBC";
    private static final String CIPHERMODEPADDING="AES/CBC/PKCS5Padding";// AES/CBC/PKCS7Padding 
           private static final int HASH_ITERATIONS=10000;
           private static final int KEY_LENGTH=128;
           private static char[] humanPassphrase={'P','e','r',' ','v','a','l','l', 'u',
                   'm',' ','d','u','c','e','s',' ','L','a','b','a', 'n','t'};// per vallum duces labant  
    private static byte[] salt={0,1,2,3,4,5,6,7,8,9,0xA,0xB,0xC,0xD, 0xE,0xF};// must save this for next time we want the key  

  /*          private PBEKeySpec myKeyspec=new PBEKeySpec(humanPassphrase,salt, HASH_ITERATIONS,KEY_LENGTH);
             
            private SecretKeyFactory keyfactory=null;
            private SecretKey sk=null;
            private static SecretKeySpec skforAES =null;
            private static String ivParameter="bjxtvi17sjappapi";// 密钥默认偏移，可更改  
       private byte[] iv=ivParameter.getBytes();
       private static IvParameterSpec IV;
       String sKey="tdzhbjxtviapsjap";// key必须为16位，可更改为自己的key */
   public AES(){
       //initAES();
        }
   /* private void initAES() {
        try{
            keyfactory= SecretKeyFactory.getInstance(KEY_GENERATION_ALG);
            sk=keyfactory.generateSecret(myKeyspec);
            }catch(NoSuchAlgorithmException nsae){
                    Log.e("AESdemo","no key factory support for PBEWITHSHAANDTWOFISH-CBC");
            }catch(InvalidKeySpecException ikse){
                       Log.e("AESdemo","invalid key spec for PBEWITHSHAANDTWOFISH-CBC");
            }
        byte[] skAsByteArray;
        try{
        skAsByteArray=sKey.getBytes("ASCII");
        skforAES=new SecretKeySpec(skAsByteArray,"AES");
        }catch(UnsupportedEncodingException e){
        e.printStackTrace();
        }
        IV=new IvParameterSpec(iv);
    }*/

    public static String encrypt(byte[] plaintext){
         PBEKeySpec myKeyspec=new PBEKeySpec(humanPassphrase,salt, HASH_ITERATIONS,KEY_LENGTH);
         SecretKeyFactory keyfactory=null;
         SecretKey sk=null;
          SecretKeySpec skforAES =null;
         String ivParameter="bjxtvi17sjappapi";// 密钥默认偏移，可更改  
        byte[] iv=ivParameter.getBytes();
          IvParameterSpec IV;
        String sKey="tdzhbjxtviapsjap";// key必须为16位，可更改为自己的key 
        try{
            keyfactory= SecretKeyFactory.getInstance(KEY_GENERATION_ALG);
            sk=keyfactory.generateSecret(myKeyspec);
        }catch(NoSuchAlgorithmException nsae){
            Log.e("AESdemo","no key factory support for PBEWITHSHAANDTWOFISH-CBC");
        }catch(InvalidKeySpecException ikse){
            Log.e("AESdemo","invalid key spec for PBEWITHSHAANDTWOFISH-CBC");
        }
        byte[] skAsByteArray;
        try{
            skAsByteArray=sKey.getBytes("ASCII");
            skforAES=new SecretKeySpec(skAsByteArray,"AES");
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        IV=new IvParameterSpec(iv);
        byte[] ciphertext=encrypt(CIPHERMODEPADDING,skforAES,IV,plaintext);
        String base64_ciphertext =Base64Encoder.encode(ciphertext);
        return base64_ciphertext;
    }

    public static String decrypt(String ciphertext_base64){
        PBEKeySpec myKeyspec=new PBEKeySpec(humanPassphrase,salt, HASH_ITERATIONS,KEY_LENGTH);
        SecretKeyFactory keyfactory=null;
        SecretKey sk=null;
        SecretKeySpec skforAES =null;
        String ivParameter="bjxtvi17sjappapi";// 密钥默认偏移，可更改  
        byte[] iv=ivParameter.getBytes();
        IvParameterSpec IV;
        String sKey="tdzhbjxtviapsjap";// key必须为16位，可更改为自己的key 
        try{
            keyfactory= SecretKeyFactory.getInstance(KEY_GENERATION_ALG);
            sk=keyfactory.generateSecret(myKeyspec);
        }catch(NoSuchAlgorithmException nsae){
            Log.e("AESdemo","no key factory support for PBEWITHSHAANDTWOFISH-CBC");
        }catch(InvalidKeySpecException ikse){
            Log.e("AESdemo","invalid key spec for PBEWITHSHAANDTWOFISH-CBC");
        }
        byte[] skAsByteArray;
        try{
            skAsByteArray=sKey.getBytes("ASCII");
            skforAES=new SecretKeySpec(skAsByteArray,"AES");
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        IV=new IvParameterSpec(iv);
        byte[] s=Base64Decoder.decodeToBytes(ciphertext_base64);
        String decrypted =new String(decrypt(CIPHERMODEPADDING,skforAES,IV,s));
        return decrypted;
        }
    private byte[] addPadding(byte[] plain){
        byte plainpad[] =null;
        int shortage=16-(plain.length%16);
        if(shortage==0)
        shortage=16;
        plainpad=new byte[plain.length+shortage];
        for(int i=0;i<plain.length;i++){
        plainpad[i]=plain[i];
        }
        for(int i=plain.length;i<plain.length+shortage;i++){
        plainpad[i]=(byte)shortage;
        }
        return plainpad;
        }
    private byte[] dropPadding(byte[] plainpad){
        byte plain[]=null;
        int drop=plainpad[plainpad.length-1];
        plain=new byte[plainpad.length-drop];
        for(int i=0;i<plain.length;i++){
        plain[i]=plainpad[i];
        plainpad[i]=0;// don't keep a copy of the decrypt  
        }
        return plain;
        }
    private static byte[] encrypt(String cmp,SecretKey sk,IvParameterSpec IV, byte[] msg){
        try{
            Cipher c=Cipher.getInstance(cmp);
            c.init(Cipher.ENCRYPT_MODE,sk,IV);
            return c.doFinal(msg);
            }catch(NoSuchAlgorithmException nsae){
            Log.e("AESdemo","no cipher getinstance support for "+cmp);
            }catch(NoSuchPaddingException   nspe){
            Log.e("AESdemo","no cipher getinstance support for padding "+cmp);
            }catch(InvalidKeyException e){
            Log.e("AESdemo","invalid key exception");
            }catch(InvalidAlgorithmParameterException e){
            Log.e("AESdemo","invalid algorithm parameter exception");
            }catch(IllegalBlockSizeException e){
            Log.e("AESdemo","illegal block size exception");
            }catch(BadPaddingException e){
            Log.e("AESdemo","bad padding exception");
            }
            return null;
            }
    private static byte[] decrypt(String cmp,SecretKey sk,IvParameterSpec IV, byte[] ciphertext){
        try{
        Cipher c =Cipher.getInstance(cmp);
        c.init(Cipher.DECRYPT_MODE,sk,IV);
        return c.doFinal(ciphertext);
        }catch(NoSuchAlgorithmException nsae){
        Log.e("AESdemo","no cipher getinstance support for "+cmp);
        }catch(NoSuchPaddingException nspe){
        Log.e("AESdemo","no cipher getinstance support for padding "+cmp);
        }catch(InvalidKeyException e){
        Log.e("AESdemo","invalid key exception");
        }catch(InvalidAlgorithmParameterException e){
        Log.e("AESdemo","invalid algorithm parameter exception");
        }catch(IllegalBlockSizeException e){
        Log.e("AESdemo","illegal block size exception");
        }catch(BadPaddingException e){
        Log.e("AESdemo","bad padding exception");
        e.printStackTrace();
        }
        return null;
        }
}
