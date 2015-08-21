package com.textokit.lemmatizer.exp;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.StringArrayFS;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.opencorpora.cas.Word;

import ru.kfu.itis.cll.uima.cas.FSUtils;
import ru.kfu.itis.issst.uima.morph.dictionary.WordUtils;
import ru.kfu.itis.issst.uima.morph.model.Wordform;

public class Annatator_MorphDictionary extends JCasAnnotator_ImplBase {

	private Map<Struct_TransformationPartOfSpeech, Integer> dictionaryTransformationSort;
	private BufferedWriter outputFile;

	public void initialize(UimaContext aContext) {

		try {
			outputFile = new BufferedWriter(new FileWriter(
					"D:\\newCollectionWord\\test\\newWord.txt", false));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		FileInputStream fis;
		try {
			fis = new FileInputStream(
					"D:\\newCollectionWord\\out.txt");
			ObjectInputStream oin = new ObjectInputStream(fis);

			Map<Struct_TransformationPartOfSpeech, Integer> morphDictinary = new HashMap<Struct_TransformationPartOfSpeech, Integer>();
			morphDictinary = (Map<Struct_TransformationPartOfSpeech, Integer>) oin
					.readObject();
			dictionaryTransformationSort = sortMap(morphDictinary);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		// TODO Auto-generated method stub

		Collection<Word> wordFormColl = JCasUtil.select(aJCas, Word.class);
		Set<Word_PartOfSpeech> newCollectinWord = new HashSet<Word_PartOfSpeech>();
		Iterator iter = wordFormColl.iterator();
		Word_PartOfSpeech wordPartOfSpeech;

		Iterator iter_;

		
		while (iter.hasNext()) {
			String wf = "", pos = "", lemma = "";
			Word word = (Word) iter.next();
			wf = word.getCoveredText();

			Set<String> set = FSUtils.toSet((StringArrayFS) word
					.getWordforms(0).getGrammems());

			iter_ = set.iterator();
			if (iter_.hasNext())
				pos = (String) iter_.next();
			
			// если леммы нет, то
			//if (!word.getWordforms(0).getLemma().isEmpty()) {
				NonDictionaryWordLemmatizer lemmatizer = new NonDictionaryWordLemmatizer(
						dictionaryTransformationSort);
				
						lemma = lemmatizer.nonDictiomaryWordLemmatizer(wf, pos);
			//}
						newCollectinWord.add(new Word_PartOfSpeech(wf, pos, lemma));
		}
		try {
			showNewCollectionWord(newCollectinWord);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void showNewCollectionWord(Set<Word_PartOfSpeech> newCollectinWord )
			throws IOException {
		Iterator<Word_PartOfSpeech> iter = newCollectinWord.iterator();

		while (iter.hasNext()) {
			Word_PartOfSpeech wordPartOfSpeech = iter.next();
			outputFile.write(wordPartOfSpeech.getStringWord()+"  "+wordPartOfSpeech.getLemma()+"  "+wordPartOfSpeech.getPartOfSpeech());
			outputFile.newLine();
			outputFile.flush();
		}
	}
	@Override
	public void collectionProcessComplete()
			throws AnalysisEngineProcessException {
		// TODO Auto-generated method stub
		super.collectionProcessComplete();
		try {
			outputFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
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

}
