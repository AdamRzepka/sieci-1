/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Client.java
 *
 * Created on 2012-10-12, 15:12:40
 */

/**
 *
 * @author gastlich
 */
package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractListModel;

public class Client extends javax.swing.JFrame {

	/**
	 * Creates new form Client
	 * 
	 * @throws IOException
	 */
	public Client() throws IOException {
		initComponents();
	}
	

	@SuppressWarnings("unchecked")
	private void initComponents() {

		AvailableSensorsPanel = new javax.swing.JPanel();
		AvailableSensorsScrollPane = new javax.swing.JScrollPane();
		AvailableSensorsList = new javax.swing.JList();
		ConnectedSensorsPanel = new javax.swing.JPanel();
		ConnectedSensorsScrollPane = new javax.swing.JScrollPane();
		ConnectedSensorsList = new javax.swing.JList();
		connectToSensor = new javax.swing.JButton();
		disconnectFromSensor = new javax.swing.JButton();
		AvailableResourcesPanel = new javax.swing.JPanel();
        AvailableResourcesScrollPane = new javax.swing.JScrollPane();
        AvailableResourcesList = new javax.swing.JList();
        connectToResource = new javax.swing.JButton();
        disconnectFromResource = new javax.swing.JButton();
		Menu = new javax.swing.JMenuBar();
		File = new javax.swing.JMenu();
		Exit = new javax.swing.JMenuItem();
		SensorsInformationTabs = new javax.swing.JTabbedPane();
		ConnectedSensorsListModel = new javax.swing.DefaultListModel();
		AvaibleMetricsListModel = new javax.swing.DefaultListModel();
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        AvailableSensorsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Avaible metrics"));

        AvailableSensorsList.setModel(AvaibleMetricsListModel);
		AvailableSensorsScrollPane.setViewportView(AvailableSensorsList);

        javax.swing.GroupLayout AvailableSensorsPanelLayout = new javax.swing.GroupLayout(AvailableSensorsPanel);
        AvailableSensorsPanel.setLayout(AvailableSensorsPanelLayout);
        AvailableSensorsPanelLayout.setHorizontalGroup(
            AvailableSensorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AvailableSensorsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AvailableSensorsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addContainerGap())
        );
        AvailableSensorsPanelLayout.setVerticalGroup(
            AvailableSensorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AvailableSensorsPanelLayout.createSequentialGroup()
                .addComponent(AvailableSensorsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addContainerGap())
        );

        ConnectedSensorsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Connected to"));

        ConnectedSensorsList.setModel(ConnectedSensorsListModel);
		ConnectedSensorsScrollPane.setViewportView(ConnectedSensorsList);

        javax.swing.GroupLayout ConnectedSensorsPanelLayout = new javax.swing.GroupLayout(ConnectedSensorsPanel);
        ConnectedSensorsPanel.setLayout(ConnectedSensorsPanelLayout);
        ConnectedSensorsPanelLayout.setHorizontalGroup(
            ConnectedSensorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConnectedSensorsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ConnectedSensorsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addContainerGap())
        );
        ConnectedSensorsPanelLayout.setVerticalGroup(
            ConnectedSensorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConnectedSensorsPanelLayout.createSequentialGroup()
                .addComponent(ConnectedSensorsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addContainerGap())
        );

        connectToSensor.setText(">>");
        connectToSensor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectToSensorActionPerformed(evt);
            }
        });

        disconnectFromSensor.setText("<<");
        disconnectFromSensor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectFromSensorActionPerformed(evt);
            }
        });

        AvailableResourcesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Avaible resources"));

        AvailableResourcesList.setModel(new ResourcesListModel());
        AvailableResourcesScrollPane.setViewportView(AvailableResourcesList);

        javax.swing.GroupLayout AvailableResourcesPanelLayout = new javax.swing.GroupLayout(AvailableResourcesPanel);
        AvailableResourcesPanel.setLayout(AvailableResourcesPanelLayout);
        AvailableResourcesPanelLayout.setHorizontalGroup(
            AvailableResourcesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AvailableResourcesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AvailableResourcesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addContainerGap())
        );
        AvailableResourcesPanelLayout.setVerticalGroup(
            AvailableResourcesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AvailableResourcesPanelLayout.createSequentialGroup()
                .addComponent(AvailableResourcesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addContainerGap())
        );

        connectToResource.setText(">>");
        connectToResource.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectToResourceActionPerformed(evt);
            }
        });

        disconnectFromResource.setText("<<");
        disconnectFromResource.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectFromResourceActionPerformed(evt);
            }
        });

        File.setText("File");

        Exit.setText("exit");
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });
        File.add(Exit);

        Menu.add(File);

        setJMenuBar(Menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(
														SensorsInformationTabs,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														460, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AvailableResourcesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(disconnectFromResource)
                    .addComponent(connectToResource))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(AvailableSensorsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(disconnectFromSensor)
                    .addComponent(connectToSensor))
                .addGap(12, 12, 12)
                .addComponent(ConnectedSensorsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            
            .addGroup(layout.createSequentialGroup()
            		
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(connectToSensor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(disconnectFromSensor))
                            .addComponent(ConnectedSensorsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AvailableResourcesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AvailableSensorsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(connectToResource)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(disconnectFromResource)))
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(SensorsInformationTabs,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						222, Short.MAX_VALUE).addContainerGap())
        );

        pack();

		

		
	}

	private void connectToSensorActionPerformed(java.awt.event.ActionEvent evt) {

		// Dodanie nowego sensora na liście connected, jeżeli któryś jest
		// zaznaczony oraz nie ma go już na liście connected
		if (!AvailableSensorsList.isSelectionEmpty()
				&& ConnectedSensorsListModel.indexOf(AvailableSensorsList
						.getSelectedValue().toString()) < 0) {
			String selectedResourceMetric = new String(AvailableSensorsList
						.getSelectedValue().toString());
			
			try {
				Pattern p = Pattern.compile("^(.[a-zA-Z0-9\\-_]*)#(.[a-zA-Z0-9\\-_]*)$");
				System.out.println(AvailableSensorsList
						.getSelectedValue().toString());
				Matcher m = p.matcher(AvailableSensorsList
						.getSelectedValue().toString());
				
				URL url;

				url = new URL("http://localhost:8080/subscriptions/");
				
				String charset = "UTF-8";
				String query = new String();
				if(m.find()){
					System.out.println(m.group(1)+m.group(2));
					query = m.group(1)+"\n"+m.group(2);
				}
				

				URLConnection conn;
				conn = url.openConnection();
				
				OutputStream output = null;
				conn.setDoOutput(true);
				try {
				     output = conn.getOutputStream();
				     output.write(query.getBytes(charset));
				} finally {
				     if (output != null) try { output.close(); } catch (IOException logOrIgnore) {}
				}
				
				// Get the response
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				String line;
				
				
				while ((line = reader.readLine()) != null) {
//					AvaibleMetricsListModel.addElement(AvailableResourcesList.getSelectedValue().toString()+"#"+line);
					System.out.println(line);
				}
				reader.close();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// TODO: wybieranie i subskrypcja kanału
			ConnectedSensorsListModel.addElement(selectedResourceMetric);

			// Dodanie nowej zakładki dla sensora
			javax.swing.JScrollPane jScrollPane = new javax.swing.JScrollPane();
			javax.swing.JTextArea jTextArea1 = new javax.swing.JTextArea();
			jTextArea1.setColumns(20);
			jTextArea1.setRows(5);
			jTextArea1.setText(selectedResourceMetric);
			jScrollPane.setViewportView(jTextArea1);
			SensorsInformationTabs.addTab(selectedResourceMetric, jScrollPane);
		}

	}

	private void disconnectFromSensorActionPerformed(
			java.awt.event.ActionEvent evt) {
		// Dodanie nowego sensora na liście connected, jeżeli któryś jest
		// zaznaczony oraz nie ma go już na liście connected
		if (!ConnectedSensorsList.isSelectionEmpty()) {
			SensorsInformationTabs
					.removeTabAt(ConnectedSensorsListModel
							.indexOf(ConnectedSensorsList.getSelectedValue()
									.toString()));
			ConnectedSensorsListModel.removeElement(ConnectedSensorsList
					.getSelectedValue().toString());

		}
	}
	
	private void connectToResourceActionPerformed(java.awt.event.ActionEvent evt) {
		if (!AvailableResourcesList.isSelectionEmpty()) {
			try {
				URL url;

				url = new URL("http://localhost:8080/subscriptions/metrics/"+AvailableResourcesList.getSelectedValue().toString());

				URLConnection conn;
				conn = url.openConnection();
				
				// Get the response
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				String line;
				
				AvaibleMetricsListModel.clear();
				while ((line = reader.readLine()) != null) {
					AvaibleMetricsListModel.addElement(AvailableResourcesList.getSelectedValue().toString()+"#"+line);
				}
				reader.close();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void disconnectFromResourceActionPerformed(java.awt.event.ActionEvent evt) {
		AvaibleMetricsListModel.clear();
	}

	private void ExitActionPerformed(java.awt.event.ActionEvent evt) {
		System.exit(0);
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Client.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Client.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Client.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Client.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Client().setVisible(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		while (true) {
			try {
				Thread.currentThread().sleep(1000);// sleep for 1000 ms
				if (SensorsInformationTabs.getTabCount() > 0) {
					javax.swing.JScrollPane scrollPane = (javax.swing.JScrollPane) SensorsInformationTabs
							.getSelectedComponent();
					javax.swing.JViewport viewPort = (javax.swing.JViewport) scrollPane
							.getViewport();
					javax.swing.JTextArea textArea = (javax.swing.JTextArea) viewPort
							.getView();

					// TODO: wyświetlanie informacji o konkretnej metryce....
					// textArea.setText("sdf");
				}

			} catch (Exception ie) {
			}
		}

	}

	private javax.swing.JList AvailableSensorsList;
	private javax.swing.JPanel AvailableSensorsPanel;
	private javax.swing.JScrollPane AvailableSensorsScrollPane;
	private javax.swing.JList ConnectedSensorsList;
	private javax.swing.JPanel ConnectedSensorsPanel;
	private javax.swing.JScrollPane ConnectedSensorsScrollPane;
	private javax.swing.JMenuItem Exit;
	private javax.swing.JMenu File;
	private javax.swing.JMenuBar Menu;
	private static javax.swing.JTabbedPane SensorsInformationTabs;
	private javax.swing.JButton connectToSensor;
	private javax.swing.JButton disconnectFromSensor;
	private javax.swing.DefaultListModel ConnectedSensorsListModel;
	private javax.swing.DefaultListModel AvaibleMetricsListModel;
	private javax.swing.JList AvailableResourcesList;
    private javax.swing.JPanel AvailableResourcesPanel;
    private javax.swing.JScrollPane AvailableResourcesScrollPane;
    private javax.swing.JButton connectToResource;
    private javax.swing.JButton disconnectFromResource;

}

//class SensorsListModel extends AbstractListModel {
//	private ArrayList<String> sensorArray = null;
//
//	public SensorsListModel() {
//		sensorArray = new ArrayList<String>();
//
//	}
//
//	public int getSize() {
//		return sensorArray.size();
//	}
//
//	public Object getElementAt(int n) {
//		return sensorArray.get(n);
//	}
//}

class ResourcesListModel extends AbstractListModel {
	private ArrayList<String> resourcesArray = null;

	public ResourcesListModel() {

		try {
			resourcesArray = new ArrayList<String>();
			URL url;

			url = new URL("http://localhost:8080/subscriptions/");

			URLConnection conn;
			conn = url.openConnection();
			
			// Get the response
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				resourcesArray.add(line);
			}
			reader.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getSize() {
		return resourcesArray.size();
	}

	public Object getElementAt(int n) {
		return resourcesArray.get(n);
	}
}
