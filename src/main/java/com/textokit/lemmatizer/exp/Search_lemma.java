package com.textokit.lemmatizer.exp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
/**
 * Main для парсинга словаря и эксперементов тестового множества(несловарные словоформы)
 *
 */
public class Search_lemma {
	public static Map<Struct_TransformationPartOfSpeech, Integer> sortMap(
			final Map<Struct_TransformationPartOfSpeech, Integer> map) {
		Map<Struct_TransformationPartOfSpeech, Integer> sortedMap = new TreeMap<>(
				new Comparator<Struct_TransformationPartOfSpeech>() {
					@Override
					public int compare(Struct_TransformationPartOfSpeech lhs,
							Struct_TransformationPartOfSpeech rhs) {

						if (map.get(rhs) <= map.get(lhs))
							return -1;
						else if (map.get(rhs) > map.get(lhs))
							return 1;
						return 0;
					}
				});
		sortedMap.putAll(map);
		return sortedMap;

	}

	public static void main(String[] args) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub

		BufferedReader inputFile = new BufferedReader(new FileReader(
				"D:\\newCollectionWord\\newCollectionWord2.txt"));

		String[] tmp = null;
		String line;
		Set<Word_PartOfSpeech> collWordLemma = new HashSet<Word_PartOfSpeech>();

		while ((line = inputFile.readLine()) != null) {
			tmp = line.split("\\s");
			collWordLemma.add(new Word_PartOfSpeech(tmp[0], tmp[1], tmp[2]));

		}

		FileInputStream fis = new FileInputStream(
				"D:\\newCollectionWord\\out.txt");
		ObjectInputStream oin = new ObjectInputStream(fis);
		
		Map<Struct_TransformationPartOfSpeech, Integer> dictionaryTransformation = new HashMap<Struct_TransformationPartOfSpeech, Integer>();
		dictionaryTransformation = (Map<Struct_TransformationPartOfSpeech, Integer>) oin
				.readObject();

		Map<Struct_TransformationPartOfSpeech, Integer> dictionaryTransformationSort = sortMap(dictionaryTransformation);

		NonDictionaryWordLemmatizer nonDlemmatizer = new NonDictionaryWordLemmatizer(
				dictionaryTransformationSort, collWordLemma);
		
		nonDlemmatizer.nonDictiomaryLemmatizer();
		nonDlemmatizer.showSet();
		System.out.println(nonDlemmatizer.showcontrol());
	}
}
