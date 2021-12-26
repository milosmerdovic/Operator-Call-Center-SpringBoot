package net.beotel.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class TextUtil {
	public static final String SEPARATOR = "##";
	public static final String INPUT_SEPARATOR = "#";
	private static TextUtil instance;
	
	private TextUtil(){
	}
	
	public static TextUtil getInstance(){
		if(instance == null){
			instance = new TextUtil();
		}
		return instance;
	}
	/**
	 * Metoda koja od stringa pravi listu, sa trimovanjem
	 * @param input
	 * @param delimiter
	 * @return
	 */
	public List<String> splitString(String input, String delimiter){
		return splitString(input, delimiter, false);
	}
	
	public List<String> splitString(String input, String delimiter, boolean includeEmpty){
		List<String> resultList = new ArrayList<String>();
		if(input != null && delimiter != null && delimiter.length()>0){
			StringTokenizer tokenizer = new StringTokenizer(input, delimiter);
			while(tokenizer.hasMoreTokens()){
				String element = tokenizer.nextToken().trim();
				if(includeEmpty || element.length()>0){
					resultList.add(element);
				}
			}
		}
		return resultList;
	}
	
	public boolean notEmpty(String input){
		return input != null && input.trim().length() >0;
	}
	
	
	/**
	 * Elemente kolekcije spaja u jedan string postavljajuci separator '#' izmedju svakog elementa,<br>
	 * Kao i na pocetku i na kraju krajnjeg rezultata. <br>
	 * Ako naznacimo primenice sortiranje rezultata po prirodnom redosledu (rastuce).
	 * 
	 * @param collection
	 * @param sorted
	 * */
	
	public <T> String stringifyCollectionSeparatorJoining(Collection<T> collection, boolean sorted) {
		if(TextUtil.getInstance().notEmpty(collection)) {
			return TextUtil.INPUT_SEPARATOR + ( sorted ?
					collection.stream().sorted().map(String::valueOf).collect(Collectors.joining(TextUtil.INPUT_SEPARATOR))
					: collection.stream().map(String::valueOf).collect(Collectors.joining(TextUtil.INPUT_SEPARATOR))					
				) + TextUtil.INPUT_SEPARATOR;					
		}
		return null;
	}
	
	/**
	 * Da li kolekcija nije prazna
	 * 
	 * @param collection
	 * @return
	 */
	public boolean notEmpty(Collection<?> collection) {
		return collection != null && collection.size() > 0;
	}
	
}
