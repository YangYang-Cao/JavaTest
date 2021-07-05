package com.atguigu.command;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//ʹ���������ģʽ�����ͨ��ң�������Ե�ƵĲ���
		
		//������ƵĶ���(������)
		com.atguigu.command.LightReceiver lightReceiver = new com.atguigu.command.LightReceiver();
		
		//���������صĿ�������
		com.atguigu.command.LightOnCommand lightOnCommand = new com.atguigu.command.LightOnCommand(lightReceiver);
		com.atguigu.command.LightOffCommand lightOffCommand = new com.atguigu.command.LightOffCommand(lightReceiver);
		
		//��Ҫһ��ң����
		com.atguigu.command.RemoteController remoteController = new com.atguigu.command.RemoteController();
		
		//�����ǵ�ң������������, ���� no = 0 �ǵ�ƵĿ��͹صĲ���
		remoteController.setCommand(0, lightOnCommand, lightOffCommand);
		
		System.out.println("--------���µƵĿ���ť-----------");
		remoteController.onButtonWasPushed(0);
		System.out.println("--------���µƵĹذ�ť-----------");
		remoteController.offButtonWasPushed(0);
		System.out.println("--------���³�����ť-----------");
		remoteController.undoButtonWasPushed();
		
		
		System.out.println("=========ʹ��ң�����������ӻ�==========");
		
		com.atguigu.command.TVReceiver tvReceiver = new com.atguigu.command.TVReceiver();
		
		com.atguigu.command.TVOffCommand tvOffCommand = new com.atguigu.command.TVOffCommand(tvReceiver);
		com.atguigu.command.TVOnCommand tvOnCommand = new com.atguigu.command.TVOnCommand(tvReceiver);
		
		//�����ǵ�ң������������, ���� no = 1 �ǵ��ӻ��Ŀ��͹صĲ���
		remoteController.setCommand(1, tvOnCommand, tvOffCommand);
		
		System.out.println("--------���µ��ӻ��Ŀ���ť-----------");
		remoteController.onButtonWasPushed(1);
		System.out.println("--------���µ��ӻ��Ĺذ�ť-----------");
		remoteController.offButtonWasPushed(1);
		System.out.println("--------���³�����ť-----------");
		remoteController.undoButtonWasPushed();

	}

}
