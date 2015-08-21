package com.textokit.lemmatizer.exp;

import java.io.Serializable;
/**
 * Структура для хранения трансформации слоформы и ее части речи
 *
 */
public class Struct_TransformationPartOfSpeech implements Serializable {
	private static final long serialVersionUID = 7863262235394607247L;

	private String fromTransformation;
	private String intoTransformation;
	private String partOfSpeech;

	public Struct_TransformationPartOfSpeech() {
		fromTransformation = "";
		intoTransformation = "";
		partOfSpeech = "";
	}
	public Struct_TransformationPartOfSpeech(String from, String into) {
		fromTransformation = from;
		intoTransformation = into;
		partOfSpeech = "";
	}

	public Struct_TransformationPartOfSpeech(String from, String into, String p) {
		fromTransformation = from;
		intoTransformation = into;
		partOfSpeech = p;
	}
	

	public boolean equals(Struct_TransformationPartOfSpeech str1, Struct_TransformationPartOfSpeech str2) {
		if(str1.getFromTransformation().equals(str2.getFromTransformation()))
		{
			if(str1.getIntoTransformation().equals(str2.getIntoTransformation()))
			{
				if(str1.getPartOfSpeech().equals(str2.getPartOfSpeech()))
				{
					return true;
				}
					
			}
		}
			
      return false;
		
	}
	@Override
	public String toString() {
		return "Struct_TransformationPartOfSpeech [fromTransformation="
				+ fromTransformation + ", intoTransformation="
				+ intoTransformation + ", partOfSpeech=" + partOfSpeech + "]";
	}

	public String getFromTransformation() {
		return fromTransformation;
	}

	public void setFromTransformation(String from) {
		fromTransformation = from;
	}

	public String getIntoTransformation() {
		return intoTransformation;
	}

	public void setIntoTransformation(String into) {
		intoTransformation = into;
	}

	public String getPartOfSpeech() {
		return partOfSpeech;

	}

	public void setPartOfSpeech(String p) {
		partOfSpeech = p;
	}

}
