package ca.mcgill.ecse223.tileo.view;

import ca.mcgill.ecse223.tileo.controller.Controller;
import javax.swing.*;

public class MainPage extends javax.swing.JFrame {
	private static final long serialVersionUID = 4824752469577874705L;

	public MainPage() {
		controller = new Controller(this);
		
		//Init window
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			//Too bad
		}
		
		initComponents();
	}

	private void initComponents() {
		jButton1 = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jButton2 = new javax.swing.JButton();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBackground(new java.awt.Color(204, 229, 255));
		setBounds(new java.awt.Rectangle(0, 25, 0, 0));
		setResizable(false);

		jButton1.setBackground(new java.awt.Color(153, 153, 255));
		jButton1.setText("Play");
		jButton1.setToolTipText("");
		jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				controller.play();
			}
		});

		jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
		jLabel1.setText("TILE-O GAME");

		jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N

		jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N

		jButton2.setBackground(new java.awt.Color(153, 153, 255));
		jButton2.setText("Design");
		jButton2.setToolTipText("");
		jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				controller.design();
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGap(53, 53, 53).addComponent(jLabel1))
								.addGroup(layout.createSequentialGroup().addGap(94, 94, 94).addComponent(jLabel2)))
						.addGap(0, 0, Short.MAX_VALUE))
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										layout.createSequentialGroup().addComponent(jLabel3).addGap(43, 43, 43))
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										layout.createSequentialGroup()
												.addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 119,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 119,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(15, 15, 15)))));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup().addGap(15, 15, 15).addComponent(jLabel1).addGap(18, 18, 18)
						.addComponent(jLabel2).addGap(18, 18, 18).addComponent(jLabel3)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 47,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(30, 30, 30)));

		pack();
	}

	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;

	private Controller controller;
}
