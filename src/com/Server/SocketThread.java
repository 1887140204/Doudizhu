package com.Server;

import java.io.DataInputStream;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class SocketThread implements Runnable {

	private DataInputStream dis;
	private ServerCtrl sc;
	private int bossflag ;

	public SocketThread(ServerCtrl sc, DataInputStream dis) {
		super();
		this.sc = sc;
		this.dis = dis;
		this.bossflag=0;
	}

	@Override
	public void run() {
		try {
			while (true) {
				byte [] b = new byte[10240];
				this.dis.read(b);
				String clientMsg = new String(b).trim();// ���Կͻ��˵�����
				JSONObject json = new JSONObject(clientMsg);// ת��Ϊjson���ݸ�ʽ
				System.out.println("�������յ�:"+clientMsg);
				switch (json.getInt("type")) {
				case 1:
					receiveSetBoss(json);
					break;
				case 2:
					receivePutCards(json);
					break;
				case 3:
					receiveGameOver(json);
					break;
				default:
					System.out.println("json���ݲ��Ϸ�");
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//�յ��е������У�yes ���У�no��
	private void receiveSetBoss(JSONObject json) throws JSONException, IOException {
		int num = json.getInt("mark");
		String flag = json.getString("msg");
		if(flag.equals("yes")){
			sc.sendBoss(num);
			sc.sendBossCards(num);
		}else if(flag.equals("no")){
			bossflag++;
			if(bossflag==4){
				sc.sendGameOver(0);
			}else{
				sc.sendBossMsg((num+1)>4?(num-3):(num+1));
			}
		}
	}

	//�յ���������
	private void receivePutCards(JSONObject json) throws JSONException, IOException {
		int num = json.getInt("mark");
		String cards = json.getString("msg");
		sc.sendPutCards(num, cards);
	}
	
	//�յ�ʤ������
	private void receiveGameOver(JSONObject json) throws JSONException, IOException {
		int num = json.getInt("mark");
		sc.sendGameOver(num);
	}
}
