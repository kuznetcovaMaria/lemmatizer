package com.textokit.lemmatizer.exp;

import org.opencorpora.cas.Word;

public class Word_PartOfSpeech {
	private Word word;
	private String partOfSpeech;

	public Word_PartOfSpeech(Word w, String p) {
		word = w;
		partOfSpeech = p;
	}

	public Word_PartOfSpeech() {
		word = null;
		partOfSpeech = "";
	}

	@Override
	public String toString() {
		return "Word_PartOfSpeech [word=" + word + ", partOfSpeech="
				+ partOfSpeech + "]";
	}

	public Word getWord() {
		return word;
	}

	public void setWord(Word w) {
		word = w;
	}

	public String getPartOfSpeech() {
		return partOfSpeech;
	}

	public void setPartOfSpeech(String s) {
		partOfSpeech = s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((partOfSpeech == null) ? 0 : partOfSpeech.hashCode());
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word_PartOfSpeech other = (Word_PartOfSpeech) obj;
		if (partOfSpeech == null) {
			if (other.partOfSpeech != null)
				return false;
		} else if (!partOfSpeech.equals(other.partOfSpeech))
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}



	
	
}
