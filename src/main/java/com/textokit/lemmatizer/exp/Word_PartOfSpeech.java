package com.textokit.lemmatizer.exp;

import org.opencorpora.cas.Word;
/**
 * Структура для хранения словоформы, ее части речи и леммы
 *
 */
public class Word_PartOfSpeech {
	private String wordString;
	private Word word;
	private String partOfSpeech;
	private String lemma;

	public Word_PartOfSpeech(String w, String p, String l) {
		wordString = w;
		partOfSpeech = p;
		lemma = l;
	}

	public Word_PartOfSpeech(Word w, String p) {
		word = w;
		wordString = word.getCoveredText();
		partOfSpeech = p;
		lemma = "";
	}

	public Word_PartOfSpeech() {
		word = null;
		partOfSpeech = "";
		wordString = "";
		lemma = "";
	}

	public Word getWord() {
		return word;
	}

	@Override
	public String toString() {
		return "Word_PartOfSpeech [wordString=" + wordString
				+ ", partOfSpeech=" + partOfSpeech + ", lemma=" + lemma + "]";
	}

	public void setWord(Word w) {
		word = w;
		wordString = word.getCoveredText();
	}

	public String getPartOfSpeech() {
		return partOfSpeech;
	}

	public void setPartOfSpeech(String s) {
		partOfSpeech = s;
	}

	public String getLemma() {
		return lemma;
	}

	public void setLemma(String l) {
		lemma = l;
	}

	public String getStringWord() {
		return wordString;
	}

	public void setStringWord(String w) {
		wordString = w;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((partOfSpeech == null) ? 0 : partOfSpeech.hashCode());
		result = prime * result
				+ ((wordString == null) ? 0 : wordString.hashCode());
		return result;
	}

	public boolean equals(Word_PartOfSpeech str) {

		if (wordString.equals(str.getStringWord())) {
			if (partOfSpeech.equals(str.getPartOfSpeech())) {
				if (lemma.equals(str.getLemma())) {
					// System.out.println("true");
					return true;
				}
			}
		}

		return false;

	}

}
