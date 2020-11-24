import java.io.*;
import java.util.*;

public class Quiz { 
	
	private String name;
	private static ArrayList<Question> questions = new ArrayList<>();

	public Quiz(){}

	public void setName(String n){
		name = n;
	}
	public String getName(){
		return name;
	}

	public static void addQuestion(Question q){
		questions.add(q);
	}

	public static Quiz loadFromFile(String sourceFile) throws FileNotFoundException, InvalidQuizFormatException{

		Quiz quiz = new Quiz();
		File source = new File(sourceFile);
		quiz.setName(sourceFile); 
		
		if(!source.exists())
			throw new FileNotFoundException("Such a file does not exist!");

		Scanner in = new Scanner(source);
			
		if(!in.hasNextLine())
			throw new InvalidQuizFormatException("No line found");

			while(in.hasNextLine()){

				String firstLine = in.nextLine();

				if(firstLine.replace(" ", "").isEmpty())
					continue;

				if(firstLine.contains("{blank}")){
				
					Question f = new FillIn();
					f.setDescription(firstLine);

					if(!in.hasNextLine())
							throw new InvalidQuizFormatException("Answer for FillIn question is not found");

					String answer = in.nextLine();
					if(answer.replace(" ", "").isEmpty())
						throw new InvalidQuizFormatException("Answer for FillIn question is not found");

					f.setAnswer(answer.toLowerCase());
					addQuestion(f);	

					if(in.hasNextLine() && !(in.nextLine().replace(" ", "").isEmpty()))
						throw new InvalidQuizFormatException("FillIn question can not have more than one answer");
											
				}

				else{
					Question t = new Test();
					t.setDescription(firstLine);
					String testOptions[] = new String[4];
					
					for(int i = 0; i < 4; i++){

						if(!in.hasNextLine())
							throw new InvalidQuizFormatException("Test question should have 4 options");

						testOptions[i] = in.nextLine();
						if(testOptions[i].replace(" ", "").isEmpty())
							throw new InvalidQuizFormatException("Test question should have 4 options");
					}
					
					t.setAnswer(testOptions[0]);

					((Test)t).setOptions(testOptions);
					addQuestion(t);    

					if(in.hasNextLine() && !(in.nextLine().replace(" ", "").isEmpty()))
						throw new InvalidQuizFormatException("Test questions can not have more than 4 options");

					  }
			

		}


		if(questions.size() == 0)
			throw new InvalidQuizFormatException("No line found");

		return quiz;
}


	public void start(){

			System.out.println("===========================================================");
			System.out.println("");
			System.out.println("WELCOME TO \"" + getName().replace(".txt", "") + "\" QUIZ!");
			System.out.println("___________________________________________________________");
		
			Collections.shuffle(questions);
			Scanner in = new Scanner(System.in);
			int correctAnswers = 0;
	
			for(int i = 0; i < questions.size(); i++){

			Question thisQuestion = questions.get(i);

			System.out.println("");
			System.out.println((i+1) + ". " + thisQuestion.toString());
			System.out.println("---------------------------");
			

			if(thisQuestion instanceof FillIn){

				System.out.print("Type your answer: ");
				String myAnswer = in.next().toLowerCase();

				if(myAnswer.equals(thisQuestion.getAnswer())){
					correctAnswers++;
					System.out.println("Correct!"); }
				else
					System.out.println("Incorrect!");

			}


			if(thisQuestion instanceof Test){

				System.out.print("Enter the correct choice: ");
				boolean validChoice = false;
				String myAnswer;
				char myChoice;
				
				do{ 
				myAnswer = in.next();
				myChoice = myAnswer.charAt(0);
				
				if(myAnswer.length() == 1 && (myChoice == 'A' || myChoice == 'B' || myChoice == 'C' || myChoice == 'D'))
					validChoice = true;
				else
					System.out.print("Invalid choice! Try again (Ex: A, B, ...): ");
				
						}while(!validChoice);

				if( (((Test)(thisQuestion)).getOptionsAt( (int)(myChoice-'A') )).equals(thisQuestion.getAnswer())  ){
					correctAnswers++;
					System.out.println("Correct!");}

				else
					System.out.println("Incorrect!");
			
			}

			System.out.println("___________________________________________________________");

		}
			String percents = correctAnswers*100.0/(questions.size()) + "";
			System.out.println("\nCorrect Answers: " + correctAnswers + "/" + questions.size() + " (" + (percents).substring(0,percents.indexOf('.') + 2) + "%)");


				}

}