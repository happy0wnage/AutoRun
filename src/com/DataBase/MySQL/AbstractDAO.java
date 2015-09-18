package com.DataBase.MySQL;


import java.util.List;
import java.util.Vector;

import com.builder.overlapView.Programs;


interface AbstractDAO {


	/**
	 * Добавление данных о программе в БД и в список
	 * 
	 * @param program description
	 * @param program data
	 * @param index of element
	 */
	boolean insertProgram(String[] fields, int index);
	
	/**
	 * Изменение данных о программе в БД и в списке
	 * 
	 * @param program description
	 * @param program data
	 * @param index of element
	 */
	boolean changeProgram(String[] fields, int index);


	/**
	 * Быстро переименовывает пограмму в БД и списке
	 * 
	 * @param textF - значение для изменения
	 * @param index - индекс выбранного элемента
	 */
	void renameProgram(String sName, String fName);

	/**
	 * Обновляет инфомрацию о приложении в таблице
	 * @return 
	 */
	Vector<?>[][] updateTable();

	/**
	 * Присваивает приложению описание в обычном виде
	 * 
	 * @param index
	 */
	String setDescription(int index);

	/**
	 * Преобразование индекса элемента в списке в индекс элемента в БД
	 * @param index
	 * @return DB index
	 */
	int getDBIndexByListIndex(String index);
	
	/**
	 * Получает индекс последне добавленного элемента 
	 * @return index
	 */
	int getCount();
	
	/**
	 * Измемение пути к файлу в зависимости от наименования диска
	 * @return 
	 */
	boolean changePath(String detectedDisk);
	
	/**
	 * Считывание данных из БД
	 * @return 
	 */
	List<Programs> reading();
	
	/**
	 * Отображение таблицы при поиске данных в БД
	 * @return 
	 */
	Vector<?>[][] showOnSearch(String searchQuery);
	
	/**
	 * Отображение описание к программе в обычном виде
	 */
	String[] fieldsView(int index);

	/**
	 * Отоюражает максимальный ид
	 * @return
	 */
	int getLastId();
	
}