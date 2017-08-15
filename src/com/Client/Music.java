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
		try {
			FileInputStream fileau = new FileInputStream("music/ground.wav");
			as = new AudioStream(fileau);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			AudioPlayer.player.start(as);
		}
	}

	public static void shengli() {
		System.out.println("��Ƶ��ʼ���С���");
		try {
			FileInputStream fileau = new FileInputStream("music/v.wav");
			AudioStream c = new AudioStream(fileau);
			AudioPlayer.player.start(c);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void jiaodizhu() {
		System.out.println("��Ƶ��ʼ���С���");
		try {
			FileInputStream fileau = new FileInputStream("music/takeboss.wav");
			AudioStream b = new AudioStream(fileau);
			AudioPlayer.player.start(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void yaobuqi() {
		System.out.println("��Ƶ��ʼ���С���");
		try {
			FileInputStream fileau = new FileInputStream("music/yaobuqi.wav");
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
