import java.io.*;
import java.util.*;

public class QuizMaker{

	public static void main(String[] args) throws FileNotFoundException, InvalidQuizFormatException{
		try{
		Quiz quiz = Quiz.loadFromFile(args[0]);
		quiz.start();
	}

	catch(FileNotFoundException e){
		System.out.println(e.getMessage());
		}

	catch(InvalidQuizFormatException e){
		e.printStackTrace();	
	}



	}
}