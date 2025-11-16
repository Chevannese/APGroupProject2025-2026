package view;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;


public class Test extends JFrame implements ActionListener {
	private JPanel mainPanel;
	private JButton button;
	private JFileChooser fileDialog;
	
	public Test() {
		super();
		this.setSize(200, 300);
		
		mainPanel = new JPanel();
		button = new JButton("Save PDF");
		fileDialog = new JFileChooser();
		fileDialog.setFileFilter(new FileNameExtensionFilter("PDF File","pdf"));
		
		//button.addActionListener(this);
		button.setActionCommand("gen-report");
		
		JButton tmp = new JButton("Other");
		//tmp.addActionListener(this);
		tmp.setActionCommand("other");
		
		
		mainPanel.add(button);
		mainPanel.add(tmp);
		this.add(mainPanel);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("gen-report")) {
			generateReport();
		}
		else {
			System.out.println("other action");
		}
	}
	
	public void generateReport() {
		if (fileDialog.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = fileDialog.getSelectedFile();
			// if the file extension is not pdf append .pdf
			if (!file.getName().endsWith(".pdf")) {
				file = new File(file.toString() + ".pdf");
			}
			
			try (PDDocument document = new PDDocument()) {
				PDPage blankPage = new PDPage();
				
				document.addPage(blankPage);
				// save pdf data to file
				document.save(file);
			}
			catch (IOException err) {
				System.err.println(err.getMessage());
				JOptionPane.showMessageDialog(this, "Failed to save PDF file", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			finally {
				JOptionPane.showMessageDialog(this, "Operation complete");
			}
		}
	}
	
	public static void main(String[] args) {
		new Test();
	}
}
