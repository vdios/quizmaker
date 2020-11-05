import javafx.scene.layout.VBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import java.util.ArrayList;
import java.util.Collections;
import javafx.geometry.Insets;


class TestPane extends VBox{

	private ArrayList<RadioButton> rb = new ArrayList<>();
	private ToggleGroup group = new ToggleGroup();

	public TestPane(Test test) throws InvalidQuizFormatException{
			
			this.setPadding(new Insets(20,20,20,20));
			if(test.getDescription().length() == 0 || test.getAnswer().length() == 0)
				throw new InvalidQuizFormatException("");

			rb.add(new RadioButton(test.getAnswer()));
			rb.get(0).setToggleGroup(group);	

		for(int i = 0; i < 3; i++){
			if(test.getOptionAt(i).length() == 0)
				throw new InvalidQuizFormatException("");

			rb.add(new RadioButton(test.getOptionAt(i)));
		}
		
		Collections.shuffle(rb);	

		for(RadioButton a: rb){
			a.setToggleGroup(group);
			this.getChildren().add(a);		
		}
	}

	public String getSelectedOption(){
		for(RadioButton x: rb)
				if(x.isSelected())
					return x.getText();
				
			return null;
	}

}