package library.classes.uis;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;


import library.interfaces.entities.ILoan;
import library.classes.entities.Loan;
import library.classes.uis.BorrowGuiUI;

import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoanSlipDialog extends JDialog {

	private static final long serialVersionUID = 1L;


	JTextArea loanListTA;
	List<ILoan> loanList;
	BorrowGuiUI owner;
	
	public LoanSlipDialog(BorrowGuiUI owner, List<ILoan> loanList) {
		super(owner, true );
		this.owner = owner;
		setTitle("Loan Slip");
		setBounds(100, 100, 450, 300);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "New Loans", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 415, 200);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 20, 395, 170);
		panel.add(scrollPane);
		
		loanListTA = new JTextArea();
		loanListTA.setEditable(false);
		scrollPane.setViewportView(loanListTA);

		StringBuilder bld = new StringBuilder();
		for (ILoan loan : loanList) {
			bld.append(((Loan)loan).toString()+"\n\n");
		}
		loanListTA.setText(bld.toString());
		loanListTA.setCaretPosition(0);
		
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onOK();
			}
		});
		btnOK.setBounds(168, 221, 89, 23);
		getContentPane().add(btnOK);
		
	}
	
	private void onOK() {
		//System.err.printf("BorrowUI : LoanSlipDialog : onOK\n");
		this.setVisible(false);
		this.dispose();
	}
}
