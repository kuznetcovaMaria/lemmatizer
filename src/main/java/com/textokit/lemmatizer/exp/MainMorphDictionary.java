package com.textokit.lemmatizer.exp;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.SAXException;

import ru.kfu.itis.issst.uima.morph.dictionary.WordUtils;
import ru.kfu.itis.issst.uima.morph.dictionary.resource.MorphDictionary;
import ru.ksu.niimm.cll.uima.morph.opencorpora.resource.DefaultDictionaryExtension;
import ru.ksu.niimm.cll.uima.morph.opencorpora.resource.MorphDictionaryImpl;
import ru.ksu.niimm.cll.uima.morph.opencorpora.resource.MorphDictionaryListener;
import ru.ksu.niimm.cll.uima.morph.opencorpora.resource.MorphDictionaryListenerBase;
import ru.ksu.niimm.cll.uima.morph.opencorpora.resource.XmlDictionaryParser;

public class MainMorphDictionary {

	public static void main(String[] args) throws IOException, SAXException, ClassNotFoundException {
		// TODO Auto-generated method stub
		MorphDictionaryImpl dist = new MorphDictionaryImpl();
		Listener l = new Listener();

		MorphDictionaryListener listener = l;
		dist.addListener(listener);
		DefaultDictionaryExtension exp = new DefaultDictionaryExtension();

		InputStream input = new FileInputStream(
				"D:\\Dictionary\\dict.opcorpora.xml");
		XmlDictionaryParser.parse(dist, input, exp);
		System.out.println(l.getMorphDictionary().size());
		BufferedWriter outputFile_ = new BufferedWriter(new FileWriter(
				"D:\\newCollectionWord\\map!.txt", false));
		for (Map.Entry entry : l.getMorphDictionary().entrySet()) {
		outputFile_.write(entry.getKey()+"   "+entry.getValue());
		outputFile_.newLine();
		}
		outputFile_.flush();
		
		
		FileOutputStream fos = new FileOutputStream("D:\\newCollectionWord\\out.txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(l.getMorphDictionary());
		oos.flush();
		oos.close();

		FileInputStream fis = new FileInputStream("D:\\newCollectionWord\\out.txt");
		ObjectInputStream oin = new ObjectInputStream(fis);
		Map<Struct_TransformationPartOfSpeech, Integer> newl = new HashMap<Struct_TransformationPartOfSpeech, Integer>();
		newl = (Map<Struct_TransformationPartOfSpeech, Integer>)oin.readObject();
		System.out.println(newl.size());
		BufferedWriter outputFile = new BufferedWriter(new FileWriter(
				"D:\\newCollectionWord\\map.txt", false));
		for (Map.Entry entry : newl.entrySet()) {
		outputFile.write(entry.getKey()+"   "+entry.getValue());
		outputFile.newLine();
		}
		outputFile.flush();
	}

}
