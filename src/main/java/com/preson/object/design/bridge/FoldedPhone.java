package com.preson.object.design.bridge;


import com.preson.object.design.bridge.Brand;

//�۵�ʽ�ֻ��࣬�̳� ������ Phone
public class FoldedPhone extends Phone {

	//������
	public FoldedPhone(Brand brand) {
		super(brand);
	}
	
	public void open() {
		super.open();
		System.out.println(" �۵���ʽ�ֻ� ");
	}
	
	public void close() {
		super.close();
		System.out.println(" �۵���ʽ�ֻ� ");
	}
	
	public void call() {
		super.call();
		System.out.println(" �۵���ʽ�ֻ� ");
	}
}
