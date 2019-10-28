// Runmu Yue
// USC ID: 8918706466
// Extra credit: error handling--anything divided by 0 would show "Error" on the screen

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Calculator extends Application
{
	Label result = new Label();
	int count = 0;
	int n = 0, m = 0, answer = 0;
	double answerD = 0.0;
	String operator = "";
	Boolean operation = false;
	
    public static void main( String[] args )
    { launch(args); } // Application.launch()
	
    @Override
    public void start(Stage stage)
    { initUI(stage); }

    private void initUI(Stage stage)
    { 
    	stage.setTitle("BoxGrid");
    	GridPane root = new GridPane(); 
    	root.setPadding(new Insets(10,10,10,10));
		//root.setGridLinesVisible(true);
    	root.add(result, 2, 0);
    	root.add(doAGridPane(10),2, 1);
        Scene scene = new Scene(root, 300, 300);
    	stage.setScene(scene);
    	stage.show();	
    }
    
    public Button counterButton()
    {
        Button btn = new Button();
        btn.setText(""+(count++));
        Font ff = Font.font("Verdana", FontWeight.EXTRA_BOLD, 18);
        btn.setFont(ff);
        btn.setOnAction(ae -> {	
        	if (operation == true) {
        		result.setText("");
        		operation = false;
        	}
        	if (answer!=0 && operator == "=") {  
        		result.setText("");
        		answer = 0;
        	}
        	if(result.getText().equals("0")) {
        		result.setText(btn.getText());
        	} else {
        		result.setText(result.getText()+btn.getText());
        	}
		});
        return btn;
    }
    
    public Button signButton(String c)
    {
        Button btn = new Button();
        btn.setText(c);
        Font ff = Font.font("Verdana", FontWeight.EXTRA_BOLD, 18);
        btn.setFont(ff);
        btn.setOnAction(ae -> {
        	if (c == "+") {
        		n = Integer.parseInt(result.getText());
        		operator = "+";
        		operation = true;
        	} else if (c == "-") {
        		if (result.getText()=="") {
        			result.setText("-");
        		} else {
	        		n = Integer.parseInt(result.getText());
	        		operator = "-";
	        		operation = true;
        		}
        	} else if (c == "*") {
        		n = Integer.parseInt(result.getText());
        		operator = "*";
        		operation = true;
        	} else if (c == "/") {        		
        		n = Integer.parseInt(result.getText());
        		operator = "/";
            	operation = true;      		
        	} else if (c == "=") {
        		if (n == 0 && m != 0 && operation == false) {
        			result.setText(result.getText());
        		} else {
	        		m = Integer.parseInt(result.getText());
	        		if (operator == "+") {
	            		answer = n+m;
	            	} else if (operator == "-") {
	            		answer = n-m;
	            	} else if (operator == "*") {
	            		answer = n*m;
	            	} else if (operator == "/") {
	            		if (m == 0) {
	            			result.setText("Error");
	            			n = 0;
	    	        		operator = "=";
	    	        		operation = false;
	    	        		return;
	            		}
	            		if (n%m == 0) {
	            			answer = n/m;
	            		} else {
	            			answerD = (double)n/m;
	            		}
	            	} else {
	            		answer = n;
	            	}
	        		if (operator=="/" && n%m!=0) {
	        			result.setText(Double.toString(answerD));
	        		} else {
	        			result.setText(Integer.toString(answer));
	        		}
	        		n = 0;
	        		m = 0;
	        		operator = "=";
	        		operation = false;
        		}
        	} else if (c == "c") {
        		result.setText("");
        		n = 0;
        		m = 0;
        		operator = "c";
        		operation = false;
        	}
        }); 
        return btn;
    }
    
    public GridPane doAGridPane( int numButtons)
    {
    	GridPane pane = new GridPane();
    	pane.add(counterButton(), 1, 3);
        for ( int i=0; i<numButtons-1; i++ )
        {
    	    pane.add(counterButton(), i%3, i/3);
        }
    	pane.add(signButton("="),2, 3);
    	pane.add(signButton("c"),0, 3);
    	pane.add(signButton("+"),3, 0);
    	pane.add(signButton("-"),3, 1);
    	pane.add(signButton("*"),3, 2);
    	pane.add(signButton("/"),3, 3);
        return pane;
    }
}