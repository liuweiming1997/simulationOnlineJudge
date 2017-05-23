/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gcccompilar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Liu
 */
public class GccCompilar extends Application {

	private final String RED = "-fx-text-fill: red";
	private final String BLUE = "-fx-text-fill: blue";
	private final String YELLOW = "-fx-text-fill: yellow";

	private final String GCC = "H:/CodeBlocks/MinGW/bin/mingw32-g++.exe ";

	private final String LOCALIN = "freopen(\"data.txt\", \"r\", stdin);";
	private final String LOCALOUTMYCODE = "freopen(\"dataOutMyCode.txt\", \"w\", stdout);";
	private final String LOCALOUTANSCODE = "freopen(\"dataOutAnsCode.txt\", \"w\", stdout);";
	private final String LOCALOUTDATACODE = "freopen(\"data.txt\", \"w\", stdout);";

	private final String MYCODEFILENAME = "myCode.cpp";
	private final String ANSCODEFILENAME = "ansCode.cpp";
	private final String DATACODEFILENAME = "dataCode.cpp";

	@Override
	public void start(Stage primaryStage) {
		TextArea textAreaMyCode = new TextArea("");

		textAreaMyCode.setPrefColumnCount(50);
		textAreaMyCode.setPrefRowCount(5);
//		textAreaMyCode.setStyle(BLUE);
		textAreaMyCode.setFont(Font.font("Times", 15));
//		textAreaMyCode.setWrapText(true); //是否满足一行后换行
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		gridPane.setHgap(5.5);
		gridPane.setVgap(5.5);
		Text text = new Text("put your code here :");
		text.setFont(Font.font("Times", 20));
		gridPane.add(text, 0, 0);
		gridPane.add(textAreaMyCode, 0, 1);
		text = new Text("");
		text.setFont(Font.font("Times", 20));
		gridPane.add(text, 0, 2);

		text = new Text("put Answer Code here :");
		text.setFont(Font.font("Times", 20));
		gridPane.add(text, 0, 4);

		TextArea textAreaAnswerCode = new TextArea();
		textAreaAnswerCode.setPrefColumnCount(50);
		textAreaAnswerCode.setPrefRowCount(5);
		textAreaAnswerCode.setFont(Font.font("Times", 15));
		textAreaAnswerCode.setStyle(RED);
		gridPane.add(textAreaAnswerCode, 0, 5);

		text = new Text("");
		text.setFont(Font.font("Times", 20));
		gridPane.add(text, 0, 6);

		text = new Text("put Data Code here :");
		text.setFont(Font.font("Times", 20));
		gridPane.add(text, 0, 8);

		TextArea textAreaDataCode = new TextArea();
		textAreaDataCode.setPrefColumnCount(50);
		textAreaDataCode.setPrefRowCount(5);
		textAreaDataCode.setStyle(BLUE);
		textAreaDataCode.setFont(Font.font("Times", 15));
		gridPane.add(textAreaDataCode, 0, 9);

		Button btnCheck = new Button("Check it");
		btnCheck.setFont(Font.font("Times", 20));
		
		text = new Text("");
		text.setFont(Font.font("Times", 20));
		gridPane.add(text, 0, 10);
		gridPane.add(btnCheck, 0, 11);
		GridPane.setHalignment(btnCheck, HPos.CENTER);
		
		TextField textField = new TextField();
		textField.setFont(Font.font("Times", 20));
		textField.setText("当前运行第0次，默认运行10次");
		textField.setStyle(RED);
		textField.setEditable(false);
		gridPane.add(textField, 0, 12);
		btnCheck.setOnAction(e -> {
			try {
				String strDataCode = textAreaDataCode.getText();
				StringBuffer strBufferDataCode = new StringBuffer(strDataCode);
				addString(strBufferDataCode, LOCALOUTDATACODE);
				saveToFile(strBufferDataCode, DATACODEFILENAME);
				compilarFile(DATACODEFILENAME, DATACODEFILENAME + ".exe");

				String strMyCode = textAreaMyCode.getText();
				StringBuffer strBufferMyCode = new StringBuffer(strMyCode);
				addString(strBufferMyCode, LOCALOUTMYCODE);
				addString(strBufferMyCode, LOCALIN);
				saveToFile(strBufferMyCode, MYCODEFILENAME);
				compilarFile(MYCODEFILENAME, MYCODEFILENAME + ".exe");

				String strAnsCode = textAreaAnswerCode.getText();
				StringBuffer strBufferAnsCode = new StringBuffer(strAnsCode);
				addString(strBufferAnsCode, LOCALOUTANSCODE);
				addString(strBufferAnsCode, LOCALIN);
				saveToFile(strBufferAnsCode, ANSCODEFILENAME);
				compilarFile(ANSCODEFILENAME, ANSCODEFILENAME + ".exe");
				Thread.sleep(1500);
				
				int cnt = 0;
				textField.setText("正在运行第" + cnt + "次");
				Thread.sleep(1500);
				while (!WA()) {
					++cnt;
					//一直寻找wa数据
					System.out.println(cnt);
					textField.setText("正在运行第" + cnt + "次");
					Thread.sleep(1500);
					if (cnt >= 5) break;
				}
//				WA();
				System.out.println("WA");
//				run(DATACODEFILENAME);
			} catch (IOException ex) {
				Logger.getLogger(GccCompilar.class.getName()).log(Level.SEVERE, null, ex);
			} catch (InterruptedException ex) {
				Logger.getLogger(GccCompilar.class.getName()).log(Level.SEVERE, null, ex);
			}
		});

		
		
		Scene scene = new Scene(gridPane, 800, 600);
		primaryStage.setTitle("Accept");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public void addString(StringBuffer toChange, String toAdd) {
		char ch1 = ' ', ch2 = ' ', ch3 = ' ', ch4 = ' ';
		boolean isok = false;
		for (int i = 0; i < toChange.length(); ++i) {
			ch1 = ch2;
			ch2 = ch3;
			ch3 = ch4;
			ch4 = toChange.charAt(i);
			if (ch1 == 'm' && ch2 == 'a' && ch3 == 'i' && ch4 == 'n') {
				isok = true;
			}
			if (isok == true && toChange.charAt(i) == '{') {
				toChange.insert(i + 1, toAdd);
				return;
			}
		}
	}

	public boolean WA() throws IOException, InterruptedException {
		
		run(DATACODEFILENAME + ".exe");
		run(MYCODEFILENAME + ".exe");
		run(ANSCODEFILENAME + ".exe");
		Thread.sleep(2000);
		if (!compareTwoFile("dataOutMyCode.txt", "dataOutAnsCode.txt")) {
			return true;
		} else {
			return false;
		}
	}

	public void saveToFile(StringBuffer str, String fileName) throws FileNotFoundException {
		try (
			PrintWriter printWriter = new PrintWriter(new File(fileName));
		) {
			printWriter.println(str);
		}
	}

	public boolean compareTwoFile(String fileNameOne, String fileNameTwo) throws FileNotFoundException {
		try (
			Scanner scannerOne = new Scanner(new File(fileNameOne));
			Scanner scannerTwo = new Scanner(new File(fileNameTwo));
		) {
			while (scannerOne.hasNext() && scannerTwo.hasNext()) {
				String strOne = scannerOne.next();
				String strTwo = scannerTwo.next();
				if (strOne.equals(strTwo)) {
					continue;
				}
				return false;
			}
			if (scannerOne.hasNext()) {
				return false;
			}
			if (scannerTwo.hasNext()) {
				return false;
			}
			return true;
		}
	}

	public void compilarFile(String str, String res) throws IOException {
		Runtime.getRuntime().exec(GCC + str + " -o " + res);
	}

	public void run(String path) throws IOException { // /b可以不闪出黑框框
		Runtime.getRuntime().exec("cmd /c start /b " + path);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
