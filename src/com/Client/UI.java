package com.Client;

import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import org.json.JSONException;
import org.json.JSONObject;

public class UI extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Player[] playerList = new Player[5];// ����б�
	public static int LocalNumber;// ����������

	public static int whoBoss;
	public static int flag;// ��һ�β�����������
	public static int operatingNum;// ���ڽ��в��������
	public static int lastTakeNum;// �ϴγ���������

	public Container container = null;// ��������
	public JMenuItem exit, replay, about;// ����˵���ť
	public static JButton landlord[] = new JButton[2];// ��������ť
	public static JButton publishCard[] = new JButton[2];// ���ư�ť

	JLabel wait;
	public static JLabel clock[] = new JLabel[5];
	JLabel[] playName = new JLabel[4];// �������
	JLabel[] playPhoto = new JLabel[4];// ���ͷ��
	JLabel[] cardsWest = new JLabel[33];// ������
	JLabel[] cardsNorth = new JLabel[33];// ������
	JLabel[] cardsEast = new JLabel[33];// ������
	static List<Card> bossCards = new ArrayList<>();// ������
	List<Card> putsList = new ArrayList<Card>();// �Լ�����
	List<Card> sendServerCardList = new ArrayList<Card>();;// �Լ�����
	static List<Card> lastPuts = new ArrayList<>();// �ϼҳ���
	JLabel[] dizhuJLabels = new JLabel[8];

	String serverIP;
	String playername;
	Socket socket;

	/*
	 * UI�๹�췽��:��newUI����ʱ,�Զ�����������������
	 */
	public UI(String serverIP, String playername) throws UnknownHostException, IOException {
		this.serverIP = serverIP;
		this.playername = playername;
		this.socket = new Socket(serverIP, 8888);
		this.setTitle("������ض�����-" + playername);
		this.setSize(1200, 700);
		setResizable(false);
		new Thread(new Music()).start();
		wait = new JLabel("�ȴ����������", JLabel.CENTER);
		wait.setSize(300, 110);
		wait.setVisible(true);
		this.add(wait);
		setLocationRelativeTo(getOwner());

		for (int i = 1; i <= 4; i++) {
			playerList[i] = new Player("δ����", 0);
			playerList[i].cardList = new ArrayList<Card>();
		}

		this.setVisible(true);
	}

	/*
	 * �����沼��:�̶�λ�� ����������
	 */
	public void init() {

		wait.setVisible(false);
		setLocationRelativeTo(getOwner()); // ��Ļ����
		container = this.getContentPane();
		container.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		container.setBackground(new Color(0, 112, 26)); // ����Ϊ��ɫ

	}

	// �˵�������
	public void setMenu() {
		JMenuBar jMenuBar = new JMenuBar();
		JMenu game = new JMenu("��Ϸ");
		JMenu help = new JMenu("����");
		replay = new JMenuItem("���¿�ʼ");
		exit = new JMenuItem("�˳�");
		about = new JMenuItem("����");
		game.add(replay);
		game.add(exit);
		help.add(about);
		jMenuBar.add(game);
		jMenuBar.add(help);
		this.setJMenuBar(jMenuBar);
	}

	// ���ö�����Ҳ���-�������ͷ������;�˿��Ʊ�����ʾ;����ʱ�ı���;Сʱ��ͼ��
	private void setEast() {
		clock[getEastNum()] = new JLabel(new ImageIcon("images/clock.gif"));
		clock[getEastNum()].setBounds(920, 305, 30, 30);
		clock[getEastNum()].setVisible(false);
		this.add(clock[getEastNum()]);
		// �����������
		playName[0] = new JLabel(playerList[getEastNum()].getName()); // δ��ȡ�������
		playName[0].setBounds(1100, 270, 80, 30);
		playName[0].setVisible(true);
		playName[0].setBackground(new Color(255, 255, 255));
		this.add(playName[0]);
		// �������ͷ��
		playPhoto[0] = new JLabel(new ImageIcon("images/nongmin.png"));
		playPhoto[0].setBounds(1100, 300, 80, 70);
		playPhoto[0].setVisible(true);
		this.add(playPhoto[0]);
		upEast();
	}

	// ���ñ�����Ҳ���-�������ͷ������;�˿��Ʊ�����ʾ;����ʱ�ı���;Сʱ��ͼ��
	private void setNorth() {
		clock[getNorthNum()] = new JLabel(new ImageIcon("images/clock.gif"));
		clock[getNorthNum()].setBounds(530, 175, 30, 30);
		clock[getNorthNum()].setVisible(false);
		this.add(clock[getNorthNum()]);
		// �����������
		playName[2] = new JLabel(playerList[getNorthNum()].getName()); // δ��ȡ�������
		playName[2].setBounds(820, 105, 80, 30);
		playName[2].setVisible(true);
		playName[2].setBackground(new Color(255, 255, 255));
		this.add(playName[2]);
		// �������ͷ��
		playPhoto[2] = new JLabel(new ImageIcon("images/nongmin.png"));
		playPhoto[2].setBounds(820, 35, 80, 70);
		playPhoto[2].setVisible(true);
		this.add(playPhoto[2]);
		upNorth();

	}

	// ����������Ҳ���-�������ͷ������;�˿��Ʊ�����ʾ;����ʱ�ı���;Сʱ��ͼ��
	private void setWest() {
		clock[getWestNum()] = new JLabel(new ImageIcon("images/clock.gif"));
		clock[getWestNum()].setBounds(280, 305, 30, 30);
		clock[getWestNum()].setVisible(false);
		this.add(clock[getWestNum()]);
		// �����������
		playName[3] = new JLabel(playerList[getWestNum()].name); // δ��ȡ�������
		playName[3].setBounds(20, 270, 80, 30);
		playName[3].setVisible(true);
		playName[3].setBackground(new Color(255, 255, 255));
		this.add(playName[3]);
		// �������ͷ��
		playPhoto[3] = new JLabel(new ImageIcon("images/nongmin.png"));
		playPhoto[3].setBounds(20, 300, 80, 70);
		playPhoto[3].setVisible(true);
		this.add(playPhoto[3]);
		// �������߿���λ��
		upWest();

	}

	// �����м����,��Ҫ����1,������;2��ʾ����;3,��ʾʤ��
	private void setCenter() {
		for (int i = dizhuJLabels.length - 1; i >= 0; i--) {
			dizhuJLabels[i] = new JLabel(new ImageIcon("images/rear.gif"));
			dizhuJLabels[i].setBounds(400 + i * 25, 275, 71, 96);
			dizhuJLabels[i].setVisible(true);
			this.add(dizhuJLabels[i]);
		}
	}

	// �������汾����Ҳ���
	private void setSouth() {
		// �����������
		playName[1] = new JLabel(playerList[LocalNumber].getName()); // ��ȡ�������
		playName[1].setBounds(200, 610, 80, 30);
		playName[1].setVisible(true);
		playName[1].setBackground(new Color(255, 255, 255));
		this.add(playName[1]);
		// �������ͷ��
		playPhoto[1] = new JLabel(new ImageIcon("images/nongmin.png"));
		playPhoto[1].setBounds(200, 540, 80, 70);
		playPhoto[1].setVisible(true);
		this.add(playPhoto[1]);
		// �����ϱ߿���λ��

		for (int i = playerList[LocalNumber].cardList.size() - 1; i >= 0; i--) {
			Card a = playerList[LocalNumber].cardList.get(i);
			// a.canClick = true;
			this.add(a);
			a.setLocation(300 + i * 15, 540);
		}

	}

	// �������汾����Ҳ���
	private void setLocal() {
		clock[LocalNumber] = new JLabel(new ImageIcon("images/clock.gif"));
		clock[LocalNumber].setBounds(530, 460, 30, 30);// 500, 460
		clock[LocalNumber].setVisible(false);
		this.add(clock[LocalNumber]);

		new Thread(new Runnable() {
			// Сʱ���߳�
			@Override
			public void run() {
				while (true) {
					for (int i = 1; i <= 4; i++) {
						if (operatingNum == i)
							clock[i].setVisible(true);
						else
							clock[i].setVisible(false);
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		// ���
		landlord[0] = new JButton("Ҫ����");
		landlord[1] = new JButton("��     Ҫ");
		publishCard[0] = new JButton("����");
		publishCard[1] = new JButton("��Ҫ");

		for (int i = 0; i < 2; i++) {
			publishCard[i].setBounds(450 + i * 100, 500, 60, 20);
			landlord[i].setBounds(450 + i * 100, 500, 75, 20);
			container.add(landlord[i]);
			landlord[i].setVisible(false);
			publishCard[i].setVisible(false);
			container.add(publishCard[i]);
		}

		// ��������ť������
		landlord[0].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					JSONObject json = new JSONObject();
					json.put("type", 1);
					json.put("mark", LocalNumber);
					json.put("msg", "yes");
					ToServer.sendMsg(json.toString(), new DataOutputStream(socket.getOutputStream()));
				} catch (JSONException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				for (int i = 0; i < 2; i++) {
					landlord[i].setVisible(false);
				}
				for (int i = dizhuJLabels.length - 1; i >= 0; i--) {
					dizhuJLabels[i].setVisible(false);
				}
				for (int i = 0; i < 2; i++) {
					publishCard[i].setVisible(true);
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				for (Card a : bossCards) {
					playerList[LocalNumber].cardList.add(a);
				}

				// �õ������ƺ� ��������������Ϊδ���״̬
				for (int i = 0; i < playerList[LocalNumber].cardList.size(); i++) {
					playerList[LocalNumber].cardList.get(i).clicked = false;
				}
				CardCtrl.cardListSort(playerList[LocalNumber].cardList);

				for (int i = playerList[LocalNumber].cardList.size() - 1; i >= 0; i--) {
					Card a = playerList[LocalNumber].cardList.get(i);
					container.add(a);
					a.setLocation(300 + i * 15, 540);
				}

				// ���ĵ���ͷ��
				playPhoto[1].setIcon(new ImageIcon("images/dizhu.png"));
			}
		});

		// ��Ҫ������ť������
		landlord[1].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					JSONObject json = new JSONObject();
					json.put("type", 1);
					json.put("mark", LocalNumber);
					json.put("msg", "no");
					ToServer.sendMsg(json.toString(), new DataOutputStream(socket.getOutputStream()));
				} catch (JSONException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				for (int i = 0; i < 2; i++) {
					landlord[i].setVisible(false);
				}
			}
		});

		// ���ư��
		publishCard[0].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// �����������Ʒ���list ÿ�ε������ո�lIst
				List<Card> temp = new ArrayList<Card>();
				putsList.clear();

				for (int i = 0; i < playerList[LocalNumber].cardList.size(); i++) {
					Card a = playerList[LocalNumber].cardList.get(i);
					if (a.clicked) {
						putsList.add(a);
					}
				}

				temp.addAll(putsList);

				int count = 0;
				for (Card a : lastPuts) {
					System.out.print(a.name + " ");
				}
				System.out.println();
				for (Card a : putsList) {
					System.out.print(a.name + " ");
				}
				System.out.println();

				if (CardCtrl.judgCard(putsList) != CardType.c0 && CardCtrl.cardCompare(putsList, lastPuts)) {
					for (Card a : lastPuts) {
						a.setVisible(false);
					}

					for (Card a : putsList) {
						CardCtrl.move(a, getLocation(), new Point(400 + count * 15, 275));
						a.canClick = false;// ���ܷ���
						count++;
					}

					// �رճ��ư�ť
					for (int j = 0; j < 2; j++) {
						publishCard[j].setVisible(false);
					}

					// ������������Ϊһ���ַ���
					String putCards = "";
					for (Card c : putsList) {
						putCards += c.getName() + " ";
					}

					try {
						JSONObject json = new JSONObject();
						json.put("type", 2);
						json.put("mark", LocalNumber);
						json.put("msg", putCards.trim());
						ToServer.sendMsg(json.toString(), new DataOutputStream(socket.getOutputStream()));
					} catch (JSONException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					playerList[LocalNumber].cardList.removeAll(temp);
					if (playerList[LocalNumber].cardList.size() == 0) {
						try {
							JSONObject json = new JSONObject();
							json.put("type", 3);
							json.put("mark", LocalNumber);
							ToServer.sendMsg(json.toString(), new DataOutputStream(socket.getOutputStream()));
						} catch (JSONException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
				// ���ƺ���������
				reCardList(playerList[LocalNumber].cardList);
				for (int i = 0; i < playerList[LocalNumber].cardList.size(); i++) {
					playerList[LocalNumber].cardList.get(i).clicked = false;
				}
				// ��������б��ؽ��ϲ����
				// setSouth();
			}
		});

		// ������ť
		publishCard[1].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (lastPuts.size() != 0) {
					// ���������˷��Ͳ���ָ��
					try {
						JSONObject json = new JSONObject();
						json.put("type", 2);
						json.put("mark", LocalNumber);
						json.put("msg", "0");
						ToServer.sendMsg(json.toString(), new DataOutputStream(socket.getOutputStream()));
					} catch (JSONException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					for (int j = 0; j < 2; j++) {
						publishCard[j].setVisible(false);
					}
				}
			}
		});
	}

	// ���ƺ���������
	protected void reCardList(List<Card> cardsLocal2) {
		for (int i = playerList[LocalNumber].cardList.size() - 1; i >= 0; i--) {
			Card a = playerList[LocalNumber].cardList.get(i);
			this.add(a);
			a.setBounds(300 + i * 15, 540, 71, 96);
		}
	}

	// ���±���������
	public void getLocalPlayer(Player[] playerList) {
		for (int i = 1; i <= 4; i++) {
			if (playerList[i].getName().equals(playername)) {
				LocalNumber = i;
			}
		}
	}

	// ���²���
	public void upLocal() {

	}

	public void upWest() {
		for (int i = 0; i < cardsWest.length; i++) {
			if (cardsWest[i] != null)
				cardsWest[i].setVisible(false);
		}
		for (int i = 0; i < playerList[getWestNum()].cardList.size(); i++) {
			cardsWest[i] = new JLabel(new ImageIcon("images/rear.gif"));
			cardsWest[i].setBounds(110, 150 + i * 10, 71, 96);
			cardsWest[i].setVisible(true);
			this.add(cardsWest[i]);
		}
	}

	public void upEast() {
		// ���Ö|�߿���λ��
		for (int i = 0; i < cardsEast.length; i++) {
			if (cardsEast[i] != null)
				cardsEast[i].setVisible(false);
		}

		for (int i = 0; i < playerList[getEastNum()].cardList.size(); i++) {
			cardsEast[i] = new JLabel(new ImageIcon("images/rear.gif"));
			cardsEast[i].setBounds(1020, 150 + i * 10, 71, 96);
			cardsEast[i].setVisible(true);
			this.add(cardsEast[i]);
		}
	}

	public void upNorth() {
		for (int i = 0; i < cardsNorth.length; i++) {
			if (cardsNorth[i] != null)
				cardsNorth[i].setVisible(false);
		}
		// ���ñ��߿���λ��
		for (int i = 0; i < playerList[getNorthNum()].cardList.size(); i++) {
			cardsNorth[i] = new JLabel(new ImageIcon("images/rear.gif"));
			cardsNorth[i].setBounds(730 - i * 15, 35, 71, 96);
			cardsNorth[i].setVisible(true);
			this.add(cardsNorth[i]);
		}
	}

	public void upLastPuts() {
		for (int i = lastPuts.size() - 1; i >= 0; i--) {
			lastPuts.get(i).setBounds(400 + i * 15, 275, 71, 96);
			lastPuts.get(i).setVisible(true);
			this.add(lastPuts.get(i));
		}
	}

	public void upExceptLocal() {
		// ���³�ȥ�����������������ҵ�����
		upWest();
		upEast();
		upNorth();
	}

	// ��ø�����λ��ҵĺ���
	public int getEastNum() {
		return (LocalNumber + 1) > 4 ? (LocalNumber + 1 - 4) : (LocalNumber + 1);
	}

	public int getWestNum() {
		return (LocalNumber + 3) > 4 ? (LocalNumber + 3 - 4) : (LocalNumber + 3);
	}

	public int getNorthNum() {
		return (LocalNumber + 2) > 4 ? (LocalNumber + 2 - 4) : (LocalNumber + 2);
	}

	@Override
	public void run() {

		try {

			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

			dos.write(playername.getBytes());

			while (true) {
				repaint();
				byte[] b = new byte[3000];
				dis.read(b);
				String msg = new String(b).trim();
				System.out.println("����˷�����" + msg);
				JSONObject json = new JSONObject(msg);
				int commandType = json.getInt("type");
				switch (commandType) {
				case 1:// �յ�ȷ��������� end
					Music.jiaodizhu();
					for (int i = dizhuJLabels.length - 1; i >= 0; i--) {
						dizhuJLabels[i].setVisible(false);
					}
					playerList = PlayerCtrl.determineBoss(json, playerList, LocalNumber);
					int n = json.getInt("mark");
					operatingNum = n;
					for (int i = 1; i <= 4; i++) {
						if (playerList[i].playNumber == n) {
							if (i == ((LocalNumber + 3) > 4 ? (LocalNumber - 1) : (LocalNumber + 3)))
								playPhoto[3].setIcon(new ImageIcon("images/dizhu.png"));
							if (i == ((LocalNumber + 2) > 4 ? (LocalNumber - 2) : (LocalNumber + 2)))
								playPhoto[2].setIcon(new ImageIcon("images/dizhu.png"));
							if (i == ((LocalNumber + 1) > 4 ? (LocalNumber - 3) : (LocalNumber + 1)))
								playPhoto[0].setIcon(new ImageIcon("images/dizhu.png"));
						}
					}
					break;
				case 2:// �յ�����
					int num = json.getInt("mark");
					if (json.getString("msg").equals("0")) {
						Music.yaobuqi();
						if (lastTakeNum == LocalNumber) {
							for (Card a : lastPuts) {
								a.setVisible(false);
							}
							lastPuts.clear();
						}
					}
					for (Card a : lastPuts) {
						System.out.print(a.name + " ");
					}

					if (!json.getString("msg").equals("0")) {
						lastTakeNum = num;
						for (Card a : lastPuts) {
							a.setVisible(false);
						}

						lastPuts = PlayerCtrl.takeCards(json);
						for (Card a : lastPuts) {
							System.out.print(a.name + " ");
						}
						System.out.println("\n" + lastTakeNum);

						if (num != LocalNumber) {
							for (int i = 0; i < lastPuts.size(); i++) {
								playerList[num].cardList.remove(0);
							}
						}
						for (Card a : putsList) {
							a.setVisible(false);
						}
					}

					upLastPuts();
					upExceptLocal();

					num = num + 1;
					if (num > 4)
						num -= 4;
					if (num == LocalNumber) {
						for (int i = 0; i < 2; i++) {
							publishCard[i].setVisible(true);
						}
						operatingNum = num;
					}
					operatingNum = num;
					break;

				case 3:// �յ����� end
					playerList = PlayerCtrl.releaseCards(json, playerList, LocalNumber);
					for (int i = playerList[LocalNumber].cardList.size() - 1; i >= 0; i--) {
						Card a = playerList[LocalNumber].cardList.get(i);
						// a.canClick = true;
						this.add(a);
						a.setLocation(300 + i * 15, 540);
					}
					break;
				case 4:// �յ���Ϸ����
					Music.shengli();
					int VictoryNum = json.getInt("mark");
					String VictoryCamp = playerList[VictoryNum].isBoss ? "����" : "ũ��";
					GameOver.show(playerList[VictoryNum].name + "������~\n" + VictoryCamp + "���ʤ��~");
					this.setVisible(false);
					break;

				case 5:// �յ������ż����� end
					playerList = PlayerCtrl.getLocalPlayer(json, playerList);
					getLocalPlayer(playerList);// ���±���������-LocalNumber

					for (int i = 1; i <= 4; i++) {
						if (i != LocalNumber) {
							for (int j = 1; j <= 25; j++) {
								Card a = new Card("1-1", false);
								a.canClick = false;
								playerList[i].cardList.add(a);
							}
						}
					}
					break;
				case 6:// �յ������� end
					bossCards = PlayerCtrl.getBOssCards(json, bossCards);
					break;
				case 7:// �յ�ѯ�ʵ������� end
					whoBoss = json.getInt("mark");
					operatingNum = whoBoss;
					init();// ��ʼ�������棬���ó���ߣ�������ɫ��
					setMenu();// ���ò˵���
					setWest();// ���ö�����ҵ�ͷ�������Լ�����λ�ã���Ҫ��̬��ȡ���������ͷ��
					setNorth();// ���ñ�����ҵ�ͷ�������Լ�����λ�ã���Ҫ��̬��ȡ���������ͷ��
					setEast();// �����ϱ���ҵ�ͷ�������Լ�����λ�ã���Ҫ��̬��ȡ���������ͷ��
					setCenter();// ���������м��������ʾ
					setSouth();// �������棨���أ���ҵ�������ͷ���Լ���ʾ������ҵ�����
					setLocal();// �������棨���أ���ҵġ�����������Ҫ�������������ƣ������ơ��ĸ���ť��λ�ã�Ĭ��Ϊ���ɼ���-�����ĸ���ť����ʾʱ��
					if (whoBoss == LocalNumber) {
						for (int i = 0; i < 2; i++) {
							landlord[i].setVisible(true);
						}
					}
					break;
				default:
					System.out.println("json���ݲ��Ϸ�");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}

class GameOver {
	public static void show(String text) {
		JDialog dialog = new JDialog();
		JLabel label = new JLabel(text, JLabel.CENTER);
		dialog.add(label);
		dialog.setSize(300, 110);
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
		dialog.setResizable(true);
	}
}
