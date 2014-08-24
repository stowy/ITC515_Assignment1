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

public class ConfirmCompleteDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		try {
			ConfirmCompleteDialog dialog = new ConfirmCompleteDialog(null, null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	JTextArea loanListTA;
	List<ILoan> loanList;
	BorrowGuiUI owner;
	
	public ConfirmCompleteDialog(BorrowGuiUI owner, List<ILoan> loanList) {
		super(owner, true );
		this.owner = owner;
		setTitle("Confirm Current Loans");
		setBounds(100, 100, 450, 300);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Current Loan List", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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

		JButton btnReject = new JButton("Reject");
		btnReject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onReject();
			}
		});
		btnReject.setBounds(67, 223, 89, 23);
		getContentPane().add(btnReject);
		
		JButton btnAccept = new JButton("Accept");
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onAccept();
			}
		});
		btnAccept.setBounds(264, 223, 89, 23);
		getContentPane().add(btnAccept);
		
	}
	
	private void onReject() {
		//System.err.printf("BorrowUI : ConfirmCompleteDialog : onReject\n");
		this.setVisible(false);
		this.dispose();
		owner.reject();
	}
	
	private void onAccept() {
		//System.err.printf("BorrowUI : ConfirmCompleteDialog : onAccept\n");
		this.setVisible(false);
		this.dispose();
		owner.accept();
	}
}
