import java.util.ArrayList;
import java.util.Collections;

import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class QuizPane extends BorderPane{
	
	private ArrayList<Question> questions;
	private TextArea descrip;
	private Label status;
	private Label underStatus;
	private int currInd; 
	private int correctAnswers;
	private Pane[] arrPanes;

	public QuizPane(Quiz quiz) throws InvalidQuizFormatException{

		questions = quiz.getQuestions();
		Collections.shuffle(questions);
		//creating description 
		descrip = new TextArea(questions.get(0).getDescription().replace("{blank}", "_____"));
		descrip.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));
		descrip.setEditable(false);
		descrip.setWrapText(true);
		descrip.setPrefRowCount(4);
		this.setTop(descrip);

	//array of panes	
		arrPanes = createQuizPanes();
		this.setCenter(arrPanes[0]);
		
	//left and right buttons
		Button left = new Button("<<");
		Button right = new Button(">>");
		this.setLeft(left);
		this.setRight(right);

		//setting on action(buttons)
		right.setOnAction(e -> moveToRight());
		left.setOnAction(e -> moveToLeft());

	//pane for statusBar and checkAnswer button
		HBox paneForBottom = new HBox(30);
		paneForBottom.setPadding(new Insets(5,5,5,5));
		paneForBottom.setAlignment(Pos.CENTER);
		VBox paneForStatus = new VBox();
		status = new Label("Status: " + (currInd+1) + "/" + questions.size() + " questions.");
		underStatus = new Label(null);
		paneForStatus.getChildren().addAll(status, underStatus);
		Button btCheckAns = new Button("Check Answers");
		paneForBottom.getChildren().addAll(paneForStatus, btCheckAns);
		this.setBottom(paneForBottom); 

		btCheckAns.setOnAction(e ->	checkingAnswers());

	}
//method for right button
public void moveToRight(){
	underStatus.setText(null);
	if(questions.get(currInd) instanceof FillIn)	//saving user's input in the text field
		((FillInPane)arrPanes[currInd]).setInput(((FillInPane)arrPanes[currInd]).getTextField());


	if(currInd < questions.size()-1)
		currInd++;
	else
		underStatus.setText("End of Quiz!");

	changeScene(questions.get(currInd));	}

//method for left button
public void moveToLeft(){
	underStatus.setText(null);
	if(questions.get(currInd) instanceof FillIn)	//saving user's input in the text field
		((FillInPane)arrPanes[currInd]).setInput(((FillInPane)arrPanes[currInd]).getTextField());

	if(currInd > 0)
		currInd--;
	else
		underStatus.setText("Start of Quiz!");

	changeScene(questions.get(currInd));	}

	//checking answer with creating new stage
public void checkingAnswers(){

			calcRes();
			System.out.println("Number of correct answers: " + correctAnswers);

			Stage resultSt = new Stage();
			resultSt.initModality(Modality.APPLICATION_MODAL);
			resultSt.setResizable(false);
			resultSt.setX(490);
			resultSt.setY(290);

			VBox resVb = new VBox(15);
			resVb.setPadding(new Insets(5,5,5,5));
			resVb.setAlignment(Pos.CENTER);
			Label reslb1 = new Label("Number of correct answers: " + correctAnswers + "/" + questions.size());
			reslb1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 15));

			BorderPane resButtomPane = new BorderPane();
			Label reslb2 = new Label("You may try again.");
			reslb2.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));
			Button btOk = new Button("OK");
			btOk.setPrefSize(60, 20);
			resButtomPane.setCenter(reslb2);
			resButtomPane.setRight(btOk);
			resVb.getChildren().addAll(reslb1, resButtomPane);

			btOk.setOnAction(a -> resultSt.close());

			if(correctAnswers == questions.size()){
				reslb1.setTextFill(Color.GREEN);
				reslb2.setText("Good job =)");	}

			resultSt.setTitle("Quiz Viewer: Results");
			resultSt.setScene(new Scene(resVb, 300, 130));
			resultSt.show();
}

public Pane[] createQuizPanes() throws InvalidQuizFormatException{

	Pane[] arrPanes = new Pane[questions.size()];
		
	for(int i = 0; i < questions.size(); i++){
		if(questions.get(i) instanceof FillIn)
			arrPanes[i] = new FillInPane(((FillIn)questions.get(i)));
	
		else if(questions.get(i) instanceof Test)
			arrPanes[i] = new TestPane(((Test)questions.get(i)));
	}

	return arrPanes;	}

//go to the next or previous quistion, upload the status bar
public void changeScene(Question curQues){

		descrip.setText(curQues.getDescription().replace("{blank}", "_____"));
		status.setText("Status: " + (currInd+1) + "/" + questions.size() + " questions.");
		this.setCenter(null);	

		if(curQues instanceof FillIn)
	     		((FillInPane)(arrPanes[currInd])).fillTextField();			

		this.setCenter(arrPanes[currInd]);	
	}

	//counts and returns amount of correct answers
	public void calcRes() {

	correctAnswers = 0;	

	if(questions.get(currInd) instanceof FillIn)	//saving user's input in the text field
			((FillInPane)arrPanes[currInd]).setInput(((FillInPane)arrPanes[currInd]).getTextField());

	for(int i = 0; i < questions.size(); i++){
		if(questions.get(i) instanceof FillIn){
			if( (((FillInPane)arrPanes[i]).getInput().toLowerCase()).equals(questions.get(i).getAnswer().toLowerCase()) ) 
				correctAnswers++;	}
	
		else if(questions.get(i) instanceof Test){
			if((((TestPane)arrPanes[i]).getSelectedOption()) == null)
				continue;//skip the question if user doesn't select any option

			if(	(((TestPane)arrPanes[i]).getSelectedOption()).equals(questions.get(i).getAnswer())	)	
				correctAnswers++;	}
		}
	}
}