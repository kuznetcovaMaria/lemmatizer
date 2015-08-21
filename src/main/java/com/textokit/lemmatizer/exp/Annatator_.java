package com.textokit.lemmatizer.exp;

import static org.apache.uima.aae.jms_adapter.JmsAnalysisEngineServiceStub.PARAM_BROKER_URL;
import static org.apache.uima.aae.jms_adapter.JmsAnalysisEngineServiceStub.PARAM_ENDPOINT;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.uima.UIMAFramework;
import org.apache.uima.UimaContext;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.fit.pipeline.JCasIterator;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.fit.util.LifeCycleUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.aae.jms_adapter.JmsAnalysisEngineServiceAdapter;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.StringArrayFS;
import org.apache.uima.collection.CollectionProcessingEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.collection.metadata.CpeDescriptorException;
import org.apache.uima.resource.CustomResourceSpecifier;
import org.apache.uima.resource.Parameter;
import org.apache.uima.resource.Resource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.impl.CustomResourceSpecifier_impl;
import org.apache.uima.resource.impl.Parameter_impl;
import org.apache.uima.util.InvalidXMLException;
import org.opencorpora.cas.Word;
import org.xml.sax.SAXException;

import ru.kfu.itis.cll.uima.cas.FSUtils;
import ru.kfu.itis.cll.uima.cpe.FileDirectoryCollectionReader;
import ru.kfu.itis.cll.uima.util.CachedResourceTuple;

import com.google.common.collect.Lists;

import ru.kfu.itis.issst.uima.morph.dictionary.MorphDictionaryAPI;
import ru.kfu.itis.issst.uima.morph.dictionary.MorphDictionaryAPIFactory;
import ru.kfu.itis.issst.uima.morph.dictionary.WordUtils;
import ru.kfu.itis.issst.uima.morph.dictionary.resource.MorphDictionary;
import ru.kfu.itis.issst.uima.morph.model.Wordform;

public class Annatator_ extends JCasAnnotator_ImplBase {
	private static final CachedResourceTuple<MorphDictionary> MorphDictionary = null;
	private MorphDictionary mDictionary;
	private BufferedWriter outputFile;

	@Override
	public void process(JCas aJCas) {

		Iterator<Word> iter;
		Iterator<String> iter_;
		
		Collection<Word> wColl = JCasUtil.select(aJCas, Word.class);
		Set<Word_PartOfSpeech> newCollectinWord = new HashSet<Word_PartOfSpeech>();
		
		
		Word_PartOfSpeech wordPartOfSpeech;
		iter = wColl.iterator();
		List<Wordform> listWordform;
		int i = 0;
		while (iter.hasNext()) {
			wordPartOfSpeech = new Word_PartOfSpeech();
			wordPartOfSpeech.setWord(iter.next());
		
			listWordform = mDictionary.getEntries(WordUtils.normalizeToDictionaryForm(wordPartOfSpeech.getWord().getCoveredText()));
			Set<String> set = FSUtils.toSet((StringArrayFS)wordPartOfSpeech.getWord().getWordforms(0).getGrammems());
			
			iter_ = set.iterator();
			
			if(iter_.hasNext())

				wordPartOfSpeech.setPartOfSpeech(iter_.next());
		
			if (listWordform.isEmpty() /* && i < 100 */) {
				newCollectinWord.add(wordPartOfSpeech);
				i++;
			}
		}
		try {
			showNewCollectionWord(newCollectinWord);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
	}

	public void showNewCollectionWord(Set<Word_PartOfSpeech> newCollectinWord)
			throws IOException {
		Iterator<Word_PartOfSpeech> iter = newCollectinWord.iterator();

		while (iter.hasNext()) {
			Word_PartOfSpeech wordPartOfSpeech = iter.next();
			outputFile.write(WordUtils.normalizeToDictionaryForm(wordPartOfSpeech.getWord().getCoveredText())+" ");
			outputFile.write(wordPartOfSpeech.getPartOfSpeech());
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

	public void initialize(UimaContext aContext) {
		MorphDictionaryAPI mDictionaryAPI = MorphDictionaryAPIFactory
				.getMorphDictionaryAPI();
		try {
			mDictionary = mDictionaryAPI.getCachedInstance().getResource();
			
			outputFile = new BufferedWriter(new FileWriter(
					"D:\\newCollectionWord\\newCollectionWord.txt", false));		
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
