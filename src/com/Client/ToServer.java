package com.Client;

import java.io.DataOutputStream;
import java.io.IOException;

public class ToServer {
	// ���������˷�����Ϣ
	public static void sendMsg(String str, DataOutputStream dos) throws IOException {
		System.out.println("���͸���������" + str);
		dos.write(str.getBytes());
	}
}
