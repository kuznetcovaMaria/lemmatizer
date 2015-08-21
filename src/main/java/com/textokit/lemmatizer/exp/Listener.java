package com.textokit.lemmatizer.exp;

import java.awt.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;

import ru.kfu.itis.issst.uima.morph.dictionary.resource.GramModel;
import ru.kfu.itis.issst.uima.morph.dictionary.resource.MorphDictionary;
import ru.kfu.itis.issst.uima.morph.model.Grammeme;
import ru.kfu.itis.issst.uima.morph.model.Lemma;
import ru.kfu.itis.issst.uima.morph.model.Wordform;
import ru.ksu.niimm.cll.uima.morph.opencorpora.resource.MorphDictionaryImpl;
import ru.ksu.niimm.cll.uima.morph.opencorpora.resource.MorphDictionaryListener;

public class Listener implements MorphDictionaryListener, Serializable {
	private static final long serialVersionUID = 7863262235394607247L;

	private Map<Struct_TransformationPartOfSpeech, Integer> map = new HashMap<Struct_TransformationPartOfSpeech, Integer>();
	transient private Struct_TransformationPartOfSpeech str = new Struct_TransformationPartOfSpeech();


	public void transformation(String lemma, String wf, String pos) {

		String into = "";
		String from = "";

		if (wf.equals(lemma)) {
			int lengthWF = wf.length();
			if (pos == "NOUN" || pos == "ADJS"|| pos == "ADVB") {
				into = wf.substring(lengthWF - 1, lengthWF);
				from = into;
			} else {
				if(lengthWF > 2)
				into = wf.substring(lengthWF - 2, lengthWF);
				from = into;

			}

		} else {

			boolean flag = false;
			int size = wf.length();

			while (!flag) {
				size--;
				String word = wf.substring(0, size);
				if (lemma.startsWith(word, 0)) {
					flag = true;
					into = lemma.substring(size, lemma.length());
					from = wf.substring(size, wf.length());

				}
			}
		}
		// return
		// str = new Struct_TransformationPartOfSpeech(from, into, pos);
		str.setFromTransformation(from);
		str.setIntoTransformation(into);
		str.setPartOfSpeech(pos);
	}

	public void addNewWord(String lemma, String wf, String pos) {
		transformation(lemma, wf, pos);
		boolean flag = false;

		if (str.getFromTransformation() == str.getIntoTransformation()
				&& str.getFromTransformation() == "") {
			return;
		}

		for (Map.Entry<Struct_TransformationPartOfSpeech, Integer> entry : map
				.entrySet()) {

			if (entry.getKey().equals(str, entry.getKey())) {
				int key = entry.getValue();
				entry.setValue(key + 1);
				flag = true;
			}
		}

		if (!flag) {
			Struct_TransformationPartOfSpeech strNew = new Struct_TransformationPartOfSpeech(
					str.getFromTransformation(), str.getIntoTransformation(),
					str.getPartOfSpeech());
			map.put(strNew, 1);

			/*
			 * System.out.println("map size: " + map.entrySet().size()); for
			 * (Map.Entry<Struct_TransformationPartOfSpeech, Integer> entry :
			 * map .entrySet()) {
			 * System.out.println(entry.getKey().getFromTransformation() + "->"
			 * + entry.getKey().getIntoTransformation() + "   " +
			 * entry.getKey().getPartOfSpeech()); }
			 */
		}
	}

	@Override
	public void onGramModelSet(MorphDictionary dict) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onWordformAdded(MorphDictionary dict, String wfString,
			Wordform wf) {

		// TODO Auto-generated method stub
		GramModel gramm = dict.getGramModel();
		String pos = gramm.toGramSet(wf.getAllGramBits(wf, dict)).get(0);

		String lemma = dict.getLemma(wf.getLemmaId()).getString();
		// System.out.println(wfString+"  "+lemma+" "+pos);

		addNewWord(lemma, wfString, pos);

	}

	public Map<Struct_TransformationPartOfSpeech, Integer> getMorphDictionary() {

		return map;
	}

}
