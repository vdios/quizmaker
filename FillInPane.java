import javafx.scene.layout.StackPane;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;

public class FillInPane extends StackPane{

	private TextField tf = new TextField();
	private String input = "";

	public FillInPane(FillIn fillin) throws InvalidQuizFormatException{
		if(fillin.getDescription().length() == 0 || fillin.getAnswer().length() == 0)
			throw new InvalidQuizFormatException("");

		this.getChildren().add(tf);
		tf.setAlignment(Pos.CENTER);
		tf.setPromptText("Enter your answer...");
	}

	public String getTextField(){	return tf.getText();	}

	public String getInput(){	return input;	}

	public void fillTextField(){	tf.setText(input);	}
	
	public void setInput(String str){	input = str;	} 

}