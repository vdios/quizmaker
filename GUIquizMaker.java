import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class GUIquizMaker extends Application{
	
	Stage errorStage;

	public void start(Stage stage) throws InvalidQuizFormatException{
		
		StackPane startPane = new StackPane();
		Button bt = new Button("Load File");
		startPane.getChildren().add(bt);		
		stage.setScene(new Scene(startPane, 400, 300));
		stage.setX(450);
		stage.setY(200);
		stage.setTitle("Quiz Viewer");
		stage.setResizable(false);
		stage.show();

		bt.setOnAction(e ->{
		FileChooser fileChooser = new FileChooser();
		//user can choose only .txt files
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		File selectedFile = fileChooser.showOpenDialog(stage);
	
		try{
		Quiz quiz = Quiz.loadFromFile(selectedFile.getName());
		QuizPane pane = new QuizPane(quiz);
		stage.setScene(new Scene(pane, 400, 300));	}

		catch(InvalidQuizFormatException ex1){
			System.out.println("Invalid Quiz Format");

			errorStage = new Stage();
			errorStage.initModality(Modality.APPLICATION_MODAL);
			errorStage.setResizable(false);
			errorStage.setX(490);
			errorStage.setY(280);
			errorStage.setTitle("Quiz Viewer: Error");
			errorStage.setScene(new Scene(getErrorMessagePane(), 320, 150));			
			errorStage.show();

		}

		catch(NullPointerException ex2){
			System.out.println("File has not been selected");	}
	});

}

public VBox getErrorMessagePane(){

		VBox paneForError = new VBox(10);		
		paneForError.setPadding(new Insets(5,5,5,5));
		paneForError.setAlignment(Pos.CENTER);	

		ImageView redX = new ImageView("redx.png");
		redX.setFitHeight(30);
		redX.setFitWidth(30);
		Label lbUp = new Label("InvalidQiuzFormatException  ", redX);
		lbUp.setTextFill(Color.RED);
		lbUp.setFont(Font.font("Bahnschrift Condensed", FontWeight.BOLD, 15));
		lbUp.setContentDisplay(ContentDisplay.RIGHT);

		String errorStr = "The file selected does not fit the requirements\n 	for a standart Quiz text file format...";
		Label lbDown = new Label(errorStr);
		lbDown.setFont(Font.font("Bahnschrift Condensed", 12));

		Button btErrOk = new Button("Ok");
		btErrOk.setPrefSize(50, 20);

		btErrOk.setOnAction(e1 -> errorStage.close());

		paneForError.getChildren().addAll(lbUp, lbDown, btErrOk);

		return paneForError;
	}
}