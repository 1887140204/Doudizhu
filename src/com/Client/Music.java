package com.Client;

import java.applet.Applet;
import java.io.FileInputStream;
import java.io.IOException;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Music extends Applet implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	AudioStream as;

	public Music() {
		System.out.println("��Ƶ��ʼ���С���");
		as = null;
	}

	public void run() {
		while (true) {
			try {
				FileInputStream fileau = new FileInputStream("music/����.wav");
				as = new AudioStream(fileau);
			} catch (IOException e) {
				e.printStackTrace();
			}
			AudioPlayer.player.start(as);
			try {
				Thread.sleep(94000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void shengli() {
		System.out.println("��Ƶ��ʼ���С���");
		try {
			FileInputStream fileau = new FileInputStream("music/ʤ��.wav");
			AudioStream c = new AudioStream(fileau);
			AudioPlayer.player.start(c);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void jiaodizhu() {
		System.out.println("��Ƶ��ʼ���С���");
		try {
			FileInputStream fileau = new FileInputStream("music/�е���.wav");
			AudioStream b = new AudioStream(fileau);
			AudioPlayer.player.start(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void yaobuqi() {
		System.out.println("��Ƶ��ʼ���С���");
		try {
			FileInputStream fileau = new FileInputStream("music/Ҫ����.wav");
			AudioStream a = new AudioStream(fileau);
			AudioPlayer.player.start(a);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void feiji() {
		System.out.println("��Ƶ��ʼ���С���");
		try {
			FileInputStream fileau = new FileInputStream("music/�ɻ�.wav");
			AudioStream b = new AudioStream(fileau);
			AudioPlayer.player.start(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void wangzha() {
		System.out.println("��Ƶ��ʼ���С���");
		try {
			FileInputStream fileau = new FileInputStream("music/��ը.wav");
			AudioStream b = new AudioStream(fileau);
			AudioPlayer.player.start(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
