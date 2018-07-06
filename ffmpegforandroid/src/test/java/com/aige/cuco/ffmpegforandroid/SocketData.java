package com.aige.cuco.ffmpegforandroid;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SocketData {


    public byte[] createRequest(int contentlengh, int header, boolean compress, int addressNum, short addressLengh, byte[] addContent) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(stream);

        try {
            dout.write(contentlengh);
            dout.write(header);
            dout.writeBoolean(compress);
            dout.write(addressNum);
            dout.writeShort(addressLengh);
            dout.write(addContent, 0, addContent.length - 1);
            return stream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void getResponsePack(InputStream out) {
//        DataOutputStream outputStream = new DataOutputStream(out);
        DataInputStream inputStream = new DataInputStream(out);
        try {
            int contentlenght = inputStream.readInt();
            int header = inputStream.readInt();
            boolean compress = inputStream.readBoolean();
            int addNum = inputStream.readInt();
            int addLenght = inputStream.readShort();
            int addContent = inputStream.readByte();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
