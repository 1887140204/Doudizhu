package com.Client;

import java.util.List;

public class Player {
	
	String name;//�������
	boolean isBoss;//�Ƿ��ǵ���
	boolean isLocal;//�Ƿ��Ǳ������
	int playNumber;//������
	int cardNumber;//�����������
	List<Card> cardList;//��ҳ�������
	
	public Player(String name, int playNumber) {
		super();
		this.name = name;
		this.playNumber = playNumber;
		this.isLocal = false;
		this.isBoss = false;
	}
	
	public void setCards(List<Card> list){
		this.cardNumber+=list.size();
		this.cardList.addAll(list);
	}

	public String getName() {
		return name;
	}

	public boolean isBoss() {
		return isBoss;
	}

	public int getPlayNumber() {
		return playNumber;
	}

	public int getCardNumber() {
		return cardNumber;
	}

	public List<Card> getCardList() {
		return cardList;
	}
	
	

}
