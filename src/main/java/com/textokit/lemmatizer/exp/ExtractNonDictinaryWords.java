package com.textokit.lemmatizer.exp;

import static org.apache.uima.aae.jms_adapter.JmsAnalysisEngineServiceStub.PARAM_BROKER_URL;
import static org.apache.uima.aae.jms_adapter.JmsAnalysisEngineServiceStub.PARAM_ENDPOINT;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.uima.UIMAFramework;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.fit.pipeline.JCasIterator;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.fit.util.LifeCycleUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.aae.jms_adapter.JmsAnalysisEngineServiceAdapter;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.collection.CollectionProcessingEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.collection.metadata.CpeDescriptorException;
import org.apache.uima.resource.CustomResourceSpecifier;
import org.apache.uima.resource.Parameter;
import org.apache.uima.resource.Resource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceManager;
import org.apache.uima.resource.impl.CustomResourceSpecifier_impl;
import org.apache.uima.resource.impl.Parameter_impl;
import org.apache.uima.util.InvalidXMLException;
import org.opencorpora.cas.Word;
import org.xml.sax.SAXException;

import com.google.common.collect.Lists;

import ru.kfu.itis.cll.uima.cpe.CpeBuilder;
import ru.kfu.itis.cll.uima.cpe.FileDirectoryCollectionReader;

public class ExtractNonDictinaryWords {

	@SuppressWarnings("deprecation")
	public static void main(String[] args)
			throws ResourceInitializationException, IOException, SAXException,
			CpeDescriptorException, InvalidXMLException, CASException {


	CustomResourceSpecifier jmsAEServiceAdapterDesc = new CustomResourceSpecifier_impl();
	jmsAEServiceAdapterDesc
			.setResourceClassName(JmsAnalysisEngineServiceAdapter.class
					.getName());
	List<Parameter> params = Lists.newArrayList();
	params.add(new Parameter_impl(PARAM_BROKER_URL,
			"tcp://issst.itis.kpfu.ru:61616"));
	// a queue name defined in demo-pipeline-deployment.xml
	params.add(new Parameter_impl(PARAM_ENDPOINT, "top-lemmatizer-queue"));
	jmsAEServiceAdapterDesc.setParameters(params
			.toArray(new Parameter[params.size()]));

	Resource _remotePreprocessor = UIMAFramework.produceResource(
			jmsAEServiceAdapterDesc, null);

	CollectionReaderDescription colReaderDesc = FileDirectoryCollectionReader
			.createDescription(new File("D:\\nf-news-2015.02.11"));

	CollectionReader reader = UIMAFramework
			.produceCollectionReader(colReaderDesc);
	AnalysisEngine remotePreprocessor = (AnalysisEngine) _remotePreprocessor;

	AnalysisEngine myAnnotator = AnalysisEngineFactory
			.createEngine(Annatator_.class);

	JCasIterator i = new JCasIterator(reader, remotePreprocessor, myAnnotator);

	Collection<Word> wColl;
	Iterator<Word> iter;

	while (i.hasNext()) {
		i.next();
	}

	i.destroy();

	}
}
