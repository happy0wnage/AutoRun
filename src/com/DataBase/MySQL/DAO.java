package com.DataBase.MySQL;


import java.util.List;
import java.util.Vector;

import com.DataBase.entity.Developer;
import com.DataBase.entity.Program;
import com.DataBase.entity.ProgrammingLanguage;
import com.DataBase.entity.Type;
import com.builder.overlapView.Programs;


interface DAO {


	boolean insertProgram(Program p, Developer d, ProgrammingLanguage pl, Type t);
	
	boolean changeProgram(Program p, Developer d, ProgrammingLanguage pl, Type t);

	void renameProgram(String sName, String fName);

	boolean changePath(Program p);
	
	int getLastId();
	
}