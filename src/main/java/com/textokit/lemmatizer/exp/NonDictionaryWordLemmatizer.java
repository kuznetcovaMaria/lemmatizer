package com.textokit.lemmatizer.exp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NonDictionaryWordLemmatizer {

	private Map<Struct_TransformationPartOfSpeech, Integer> morphDictionaryTransformation;
	private Set<Word_PartOfSpeech> nonDictionaryWordGold;
	private Set<Word_PartOfSpeech> nonDictionaryWordNew;
	private Word_PartOfSpeech word;

	public NonDictionaryWordLemmatizer(
			Map<Struct_TransformationPartOfSpeech, Integer> m,
			Set<Word_PartOfSpeech> s) {
		morphDictionaryTransformation = m;
		nonDictionaryWordGold = s;
		nonDictionaryWordNew = new HashSet<Word_PartOfSpeech>();
	}

	public NonDictionaryWordLemmatizer(
			Map<Struct_TransformationPartOfSpeech, Integer> m) {
		morphDictionaryTransformation = m;
	}

	public String nonDictiomaryWordLemmatizer(String wf, String pos) {
		String lemma = "";
		int i = 0;
		for (Map.Entry<Struct_TransformationPartOfSpeech, Integer> entry : morphDictionaryTransformation
				.entrySet()) {

			Struct_TransformationPartOfSpeech str = entry.getKey();
			if (pos.equals(str.getPartOfSpeech())) {

				if (wf.endsWith(str.getFromTransformation())) {
					int length = wf.length()
							- str.getFromTransformation().length();
					lemma = wf.substring(0, length)
							+ str.getIntoTransformation();
					return lemma;

				}

			}
		}
		return lemma;
	}

	public void nonDictiomaryLemmatizer() {
		Iterator iter = nonDictionaryWordGold.iterator();
		while (iter.hasNext()) {
			Word_PartOfSpeech wf = (Word_PartOfSpeech) iter.next();
			nonDictionaryWordNew.add(new Word_PartOfSpeech(wf.getStringWord(),
					wf.getPartOfSpeech(), nonDictiomaryWordLemmatizer(
							wf.getStringWord(), wf.getPartOfSpeech())));
		}
	}

	public void showSet() throws IOException {
		BufferedWriter outputFile1 = new BufferedWriter(new FileWriter(
				"D:\\newCollectionWord\\test\\set1Gold.txt", false));
		BufferedWriter outputFile2 = new BufferedWriter(new FileWriter(
				"D:\\newCollectionWord\\test\\setNew.txt", false));
		Iterator iter1, iter2;
		iter1 = nonDictionaryWordGold.iterator();
		iter2 = nonDictionaryWordNew.iterator();

		while (iter1.hasNext()) {
			outputFile1.write(iter1.next() + "   ");
			outputFile1.newLine();
		}
		outputFile1.flush();

		while (iter2.hasNext()) {
			outputFile2.write(iter2.next() + "   ");
			outputFile2.newLine();
		}
		outputFile2.flush();

	}

	public int showcontrol() throws IOException {
		BufferedWriter outputFile = new BufferedWriter(new FileWriter(
				"D:\\newCollectionWord\\test\\control.txt", false));
		Iterator iter1, iter2;
		iter1 = nonDictionaryWordGold.iterator();
		iter2 = nonDictionaryWordNew.iterator();
		int kol = 0;
		while (iter1.hasNext() && iter2.hasNext()) {
			Word_PartOfSpeech str1 = (Word_PartOfSpeech) iter1.next();
			Word_PartOfSpeech str2 = (Word_PartOfSpeech) iter2.next();
			if (str1.equals(str2) == false) {
				kol++;
				outputFile.write(str1.getStringWord() + "  GoldLemma = "
						+ str1.getLemma() + " -> " + str2.getLemma());
				outputFile.newLine();
			}
		}
		outputFile.flush();
		return kol;
	}
}
