import java.io.*;
import java.util.*;

class Test extends Question{
	
	private String[] options;
	private int numOfOptions;
	private ArrayList<Character> labels;
		
	public Test(){
		numOfOptions = 4;
		options = new String[numOfOptions];
		labels = new ArrayList<>();

		for(int i = 0; i < numOfOptions; i++)
			labels.add((char)('A'+ i));
	}

	public void setOptions(String[] arr){
		
		ArrayList<String> temp = new ArrayList<>(Arrays.asList(arr));
		Collections.shuffle(temp);
		String[] shuffledOptions = new String[numOfOptions];
		for(int i = 0; i < numOfOptions; i++)
			shuffledOptions[i] = temp.get(i);

		options = shuffledOptions;
	}

	public String getOptionsAt(int i){
		return options[i];
	}

	public String toString(){
	
		String str = "";
		for(int i = 0; i < numOfOptions; i++)
			str += "\n" + labels.get(i) + ") " + getOptionsAt(i);

		return (getDescription() + str);
	}
	
}