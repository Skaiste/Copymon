package com.copymon.fileHandling;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;

public class ReadFromXml {
	
	public static int readInt(String fileName, String element, String attribute){
		int i = 0;
		FileHandle file = Gdx.files.internal(fileName);
		try {
			XmlReader.Element reader = new XmlReader().parse(file);
			i = reader.getChildByName(element).getIntAttribute(attribute);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return i;
	}
	public static int readInt(String fileName, String element, String element2, String attribute){
		FileHandle file = Gdx.files.internal(fileName);
		try {
			XmlReader.Element reader = new XmlReader().parse(file);
			return reader.getChildByName(element).getChildByName(element2).getIntAttribute(attribute);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public static String readString(String fileName, String element, String attribute){
		FileHandle file = Gdx.files.internal(fileName);
		try {
			XmlReader.Element reader = new XmlReader().parse(file);
			return reader.getChildByName(element).getAttribute(attribute);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String readString(String fileName, String element, String element2, String attribute){
		FileHandle file = Gdx.files.internal(fileName);
		try {
			XmlReader.Element reader = new XmlReader().parse(file);
			return reader.getChildByName(element).getChildByName(element2).getAttribute(attribute);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
