package com.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Main {

	public static void main(String[] args) {

		try {
			Socket[] socket = new Socket[5];
			DataOutputStream[] dos = new DataOutputStream[5];
			DataInputStream[] dis = new DataInputStream[5];
			Thread[] thread = new Thread[5];
			int x = 1;
			// �ȴ�����
			ServerSocket ss = new ServerSocket(8888);
			String[] playernames = new String[5];
			while (true) {
				socket[x] = ss.accept();
				dos[x] = new DataOutputStream(socket[x].getOutputStream());
				dis[x] = new DataInputStream(socket[x].getInputStream());
				System.out.println(socket[x].getInetAddress().getHostAddress() + "����");
				byte[] b = new byte[1024];
				dis[x].read(b);
				playernames[x] = new String(b).trim();
				System.out.println(playernames[x]);
				x++;
				if (x == 5) {
					break;
				}
			}
			ServerCtrl sc = new ServerCtrl(socket, dos, dis);
			for (int i = 1; i <= 4; i++) {
				thread[i] = new Thread(new SocketThread(sc, dis[i]));
			}
			// ��������б�,�����͵�����

			String playername = playernames[1] + " " + playernames[2] + " " + playernames[3] + " " + playernames[4];
			sc.setPlayer(playername);

			Random rand = new Random();
			int ran = rand.nextInt(4) + 1;
			sc.sendBossMsg(ran);

			// ������Ϻ����ĸ��߳̽��ո����ͻ��˵���Ϣ
			thread[1].start();
			thread[2].start();
			thread[3].start();
			thread[4].start();
			sc.sendCards();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}