package com.DataBase.MySQL;


import java.util.List;
import java.util.Vector;

import com.builder.overlapView.Programs;


interface AbstractDAO {


	/**
	 * ���������� ������ � ��������� � �� � � ������
	 * 
	 * @param program description
	 * @param program data
	 * @param index of element
	 */
	boolean insertProgram(String[] fields, int index);
	
	/**
	 * ��������� ������ � ��������� � �� � � ������
	 * 
	 * @param program description
	 * @param program data
	 * @param index of element
	 */
	boolean changeProgram(String[] fields, int index);


	/**
	 * ������ ��������������� �������� � �� � ������
	 * 
	 * @param textF - �������� ��� ���������
	 * @param index - ������ ���������� ��������
	 */
	void renameProgram(String sName, String fName);

	/**
	 * ��������� ���������� � ���������� � �������
	 * @return 
	 */
	Vector<?>[][] updateTable();

	/**
	 * ����������� ���������� �������� � ������� ����
	 * 
	 * @param index
	 */
	String setDescription(int index);

	/**
	 * �������������� ������� �������� � ������ � ������ �������� � ��
	 * @param index
	 * @return DB index
	 */
	int getDBIndexByListIndex(String index);
	
	/**
	 * �������� ������ �������� ������������ �������� 
	 * @return index
	 */
	int getCount();
	
	/**
	 * ��������� ���� � ����� � ����������� �� ������������ �����
	 * @return 
	 */
	boolean changePath(String detectedDisk);
	
	/**
	 * ���������� ������ �� ��
	 * @return 
	 */
	List<Programs> reading();
	
	/**
	 * ����������� ������� ��� ������ ������ � ��
	 * @return 
	 */
	Vector<?>[][] showOnSearch(String searchQuery);
	
	/**
	 * ����������� �������� � ��������� � ������� ����
	 */
	String[] fieldsView(int index);

	/**
	 * ���������� ������������ ��
	 * @return
	 */
	int getLastId();
	
}