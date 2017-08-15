package com.Client;

import java.awt.Point;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CardCtrl {

	// ���ͱȽ�,a����b����true
	public static boolean cardCompare(List<Card> a, List<Card> b) {
		if(a.size()==0)
			return false;
		if(b.size()==0)
			return true;
		
		if (judgCard(b) == CardType.c0000) 
			return false;
		if (judgCard(a) == CardType.c0000)
			return true;
		
		if(judgCard(a) != judgCard(b)){
			if(judgCard(a)==CardType.c4&&judgCard(b)!=CardType.c4)
				return true;
			if(judgCard(a)!=CardType.c4&&judgCard(b)==CardType.c4)
				return false;
			return false;
		}
		
		int len = a.size();
		switch (judgCard(a)) {
		case c1:
			return cardCompare(a.get(0), b.get(0)) ? true : false;
		case c2:
			return cardCompare(a.get(0), b.get(0)) ? true : false;
		case c3:
			return cardCompare(a.get(0), b.get(0)) ? true : false;
		case c4:
			if (a.size() > b.size()) {
				return true;
			} else if (a.size() < b.size()) {
				return false;
			} else if (a.size() == b.size()) {
				return cardCompare(a.get(0), b.get(0)) ? true : false;
			}
		case c32:
			return cardCompare(a.get(2), b.get(2)) ? true : false;
		case c1122:
			return cardCompare(a.get(0), b.get(0)) ? true : false;
		case c111222:
			return cardCompare(a.get(0), b.get(0)) ? true : false;
		case c1112223344:
			return feijiCompare(a,b);
		default:
			break;
		}

		return false;
	}
	
	//�ɻ��ıȽ�
	private static boolean feijiCompare(List<Card> a, List<Card> b) {
		int len = a.size();
		int[] flaga = new int[17];// ÿ���Ƶ�����

		for (Card card : a) {
			if (card.getColor() == 5) {
				flaga[card.getPoints() + 14]++;
				continue;
			}
			if (card.getPoints() == 1) {
				flaga[card.getPoints() + 13]++;
				continue;
			}
			flaga[card.getPoints()]++;
		}
		int starta = 0;// �ɻ���ʼ��λ��
		for (int i = 0; i < flaga.length; i++) {
			if (flaga[i] == 3) {
				starta = i;
				break;
			}
		}
		
		int[] flagb = new int[17];// ÿ���Ƶ�����

		for (Card card : a) {
			if (card.getColor() == 5) {
				flagb[card.getPoints() + 14]++;
				continue;
			}
			if (card.getPoints() == 1) {
				flagb[card.getPoints() + 13]++;
				continue;
			}
			flagb[card.getPoints()]++;
		}
		int startb = 0;// �ɻ���ʼ��λ��
		for (int i = 0; i < flagb.length; i++) {
			if (flagb[i] == 3) {
				startb = i;
				break;
			}
		}
		
		return cardCompare(a.get(starta), b.get(startb)) ? true : false;
	}

	// �ж�����
	public static CardType judgCard(List<Card> list) {
		// list�Ѿ���ǰ����
		int len = list.size();
		// ����,���ӣ�3������ը��(����ը)
		if (len <= 8) { // �����һ����������ͬ��˵��ȫ����ͬ
			if (list.size() > 0 && list.get(0).getPoints() == list.get(len - 1).getPoints()) {
				switch (len) {
				case 1:
					return CardType.c1;
				case 2:
					return CardType.c2;
				case 3:
					return CardType.c3;
				case 4:
					return CardType.c4;
				case 5:
					return CardType.c4;
				case 6:
					return CardType.c4;
				case 7:
					return CardType.c4;
				case 8:
					return CardType.c4;
				}
			}
			// ��ը
			if (len == 4 && list.get(3).getPoints() == 5)
				return CardType.c0000;
		}

		// ��5������ʱ�����֣�3��2���ɻ���2˳��4��2�ȵ�
		if (len >= 5) {
			// 3��2-----�غ��ظ�3�ε���
			if (len == 5) {
				if (list.get(0).getPoints() == list.get(2).getPoints())
					if (list.get(3).getPoints() == list.get(4).getPoints())
						return CardType.c32;
				if (list.get(0).getPoints() == list.get(1).getPoints())
					if (list.get(2).getPoints() == list.get(4).getPoints())
						return CardType.c32;
			}

			// ����------����Ϊ˫������ͷ����2Ҳ��������ȫ�Ƕ���
			
			if (len % 2 == 0) {
				if (list.get(0).getPoints() != 2 && list.get(0).getColor() != 5) {
					for (int i = 0; i < len; i += 2) {
						if (i == len - 2)
							return CardType.c1122;
						if (list.get(i).getPoints() != list.get(i + 1).getPoints()
								|| (list.get(i).getPoints() - 1) != list.get(i + 2).getPoints())
							break;
					}
				}
			}

			// �ɻ��������-----����Ϊ���ı�������ͷ����2Ҳ��������ȫ��������
			if (len % 3 == 0) {
				if (list.get(0).getPoints() != 2 && list.get(0).getColor() != 5) {
					for (int i = 0; i < len; i += 3) {
						if (i == len - 3)
							return CardType.c111222;
						if (!(list.get(i).getPoints() == list.get(i + 2).getPoints()
								&& (list.get(i).getPoints() - 1) == list.get(i + 3).getPoints()))
							break;
					}
				}
			}

			// �ɻ������-----����Ϊ��ı�������ͷ����2Ҳ����������len53����������������������len/5������
			if (len % 5 == 0) {
				boolean isFeiji = false;
				int[] flag = new int[17];// ÿ���Ƶ�����
				int duizi = 0;// ��������

				for (Card card : list) {
					if (card.getColor() == 5) {
						flag[card.getPoints() + 14]++;
						continue;
					}
					if (card.getPoints() == 1) {
						flag[card.getPoints() + 13]++;
						continue;
					}
					flag[card.getPoints()]++;
				}

				for (int i : flag) {
					if (i % 2 == 0)
						duizi += i / 2;
				}
				if (duizi != len / 5)
					isFeiji = false;
				int start = 0;// �ɻ���ʼ��λ��
				for (int i = 0; i < flag.length; i++) {
					if (flag[i] == 3) {
						start = i;
						break;
					}
				}
				for (int i = start; i <= start + duizi - 1; i++) {
					if (i == start + duizi - 1 && flag[i] == 3)
						isFeiji = true;
					if (flag[i] != 3)
						break;
					if (i == 2)
						break;
					if (flag[i] != 3)
						break;
				}

				if (duizi != len / 5)
					isFeiji = false;
				if (isFeiji)
					return CardType.c1112223344;
			}
		}
		return CardType.c0;
	}

	// ���ƱȽ�
	public static boolean cardCompare(Card a, Card b) {
		int a1 = a.getColor();// ��ɫ
		int a2 = b.getColor();
		int b1 = a.getPoints();// ��ֵ
		int b2 = b.getPoints();
		// ��������Ļ�
		if (a1 == 5)
			b1 += 100;
		if (a1 == 5 && b1 == 1)
			b1 += 50;
		if (a2 == 5)
			b2 += 100;
		if (a2 == 5 && b2 == 1)
			b2 += 50;
		// �����A����2
		if (b1 == 1)
			b1 += 20;
		if (b2 == 1)
			b2 += 20;
		if (b1 == 2)
			b1 += 30;
		if (b2 == 2)
			b2 += 30;
		return b1 - b2 > 0 ? true : false;
	}

	// ����List����
	public static void cardListSort(List<Card> list) {
		Collections.sort(list, new Comparator<Card>() {
			@Override
			public int compare(Card o1, Card o2) {
				// TODO Auto-generated method stub
				int a1 = o1.getColor();// ��ɫ
				int a2 = o2.getColor();
				int b1 = o1.getPoints();// ��ֵ
				int b2 = o2.getPoints();
				int flag = 0;
				// ��������Ļ�
				if (a1 == 5)
					b1 += 100;
				if (a1 == 5 && b1 == 1)
					b1 += 50;
				if (a2 == 5)
					b2 += 100;
				if (a2 == 5 && b2 == 1)
					b2 += 50;
				// �����A����2
				if (b1 == 1)
					b1 += 20;
				if (b2 == 1)
					b2 += 20;
				if (b1 == 2)
					b1 += 30;
				if (b2 == 2)
					b2 += 30;
				flag = b2 - b1;
				if (flag == 0)
					return a2 - a1;
				else {
					return flag;
				}
			}
		});
	}

	// �����ƶ�����
	public static void move(Card card, Point from, Point to) {
		if(to.x!=from.x){
			double k=(1.0)*(to.y-from.y)/(to.x-from.x);
			double b=to.y-to.x*k;
			int flag=0;//�ж������������ƶ�����
			if(from.x<to.x)
				flag=20;
			else {
				flag=-20;
			}
			for(int i=from.x;Math.abs(i-to.x)>20;i+=flag)
			{
				double y=k*i+b;//������Ҫ�õ���ѧ�е����Ժ���

				card.setLocation(i,(int)y);
				try {
					Thread.sleep(5); //�ӳ٣����Լ�����
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//λ��У׼
		//card.setBounds(to.x,to.y,70,90);
	}

}
