package com.tdin360.zjw.marathon.AESPsw;

import android.support.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by jeffery on 2017/12/5.
 */

public class Base64Encoder extends FilterOutputStream {


    private static final char[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', '+', '/'};
    private static final int[] ints = new int[128];
        static {
                for (int i = 0; i < 64; i++) {
                ints[chars[i]] = i;
                }
                }
        private int charCount;
        private int carryOver;
        public Base64Encoder(@NonNull OutputStream out) {
        super(out);
    }
        public void write(int b) throws IOException {
                if (b < 0) {
                b += 256;
                }
                if (charCount % 3 == 0) {
                int lookup = b >> 2;
                carryOver = b & 3;
                out.write(chars[lookup]);
                } else if (charCount % 3 == 1) {
                int lookup = ((carryOver << 4) + (b >> 4)) & 63;
                carryOver = b & 15;
                out.write(chars[lookup]);
                } else if (charCount % 3 == 2) {
                int lookup = ((carryOver << 2) + (b >> 6)) & 63;
                out.write(chars[lookup]);
                lookup = b & 63;
                out.write(chars[lookup]);
                carryOver = 0;
                }
                charCount++;
                if (charCount % 57 == 0) {
                out.write('\n');
                }
                }
        public void write(byte[] buf, int off, int len) throws IOException {
                for (int i = 0; i < len; i++) {
                write(buf[off + i]);
                }
                }
        public  void  close() throws IOException{
                if(charCount%3==1){
                int lookup =(carryOver<<4)&63;
                out.write(chars[lookup]);
                out.write('=');
                out.write('=');}
                else if(charCount%3==2){
                int lookup=(carryOver<<2)&63;
                out.write(chars[lookup]);
                out.write('=');
                }super.close();
                }
        public static String encode(byte[] bytes){
                ByteArrayOutputStream out=new ByteArrayOutputStream((int)(bytes.length*1.37));
                Base64Encoder encodedOut=new Base64Encoder(out);
                try{encodedOut.write(bytes);
                encodedOut.close();
                return out.toString("UTF-8");
                }catch(IOException ignored){
                return null;}
                }
        public static void main(String[] args)throws Exception {
                if(args.length!=1){System.err.println("Usage: java com.oreilly.servlet.Base64Encoder fileToEncode");
                return;}
                Base64Encoder encoder=null;
                BufferedInputStream in=null;
                try{
                encoder=new Base64Encoder(System.out);
                in=new BufferedInputStream(new FileInputStream(args[0]));
                byte[] buf=new byte[4*1024];
                int bytesRead;
                while((bytesRead=in.read(buf))!=-1){
                encoder.write(buf,0,bytesRead);}
                }finally{if(in!=null)
                in.close();
                if(encoder!=null)encoder.close();}
                }
}
