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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractListModel;

class Subscription {
	public Subscription(int id, String resource, String metric, String host,
			int httpPort, int port, SocketChannel channel) {
		this.id = id;
		this.resource = resource;
		this.metric = metric;
		this.host = host;
		this.port = port;
		this.channel = channel;
		this.httpPort = httpPort;
	}

	public int getId() {
		return id;
	}

	public String getResource() {
		return resource;
	}

	public SocketChannel getChannel() {
		return channel;
	}

	public String getMetric() {
		return metric;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public int getHttpPort() {
		return httpPort;
	}

	private int id;
	private String resource;
	private String metric;
	private String host;
	private int port;
	private int httpPort;
	private SocketChannel channel;
}

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
		hostNameText = new javax.swing.JTextField("localhost");
		httpPortText = new javax.swing.JTextField("8080");
		connectHostButton = new javax.swing.JButton("Connect to monitor");

		connectHostButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				connectHostClicked();
			}
		});

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		AvailableSensorsPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Avaible metrics"));

		AvailableSensorsList.setModel(AvaibleMetricsListModel);
		AvailableSensorsScrollPane.setViewportView(AvailableSensorsList);

		javax.swing.GroupLayout AvailableSensorsPanelLayout = new javax.swing.GroupLayout(
				AvailableSensorsPanel);
		AvailableSensorsPanel.setLayout(AvailableSensorsPanelLayout);
		AvailableSensorsPanelLayout
				.setHorizontalGroup(AvailableSensorsPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								AvailableSensorsPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												AvailableSensorsScrollPane,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												171, Short.MAX_VALUE)
										.addContainerGap()));
		AvailableSensorsPanelLayout
				.setVerticalGroup(AvailableSensorsPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								AvailableSensorsPanelLayout
										.createSequentialGroup()
										.addComponent(
												AvailableSensorsScrollPane,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												210, Short.MAX_VALUE)
										.addContainerGap()));

		ConnectedSensorsPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Connected to"));

		ConnectedSensorsList.setModel(ConnectedSensorsListModel);
		ConnectedSensorsScrollPane.setViewportView(ConnectedSensorsList);

		javax.swing.GroupLayout ConnectedSensorsPanelLayout = new javax.swing.GroupLayout(
				ConnectedSensorsPanel);
		ConnectedSensorsPanel.setLayout(ConnectedSensorsPanelLayout);
		ConnectedSensorsPanelLayout
				.setHorizontalGroup(ConnectedSensorsPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								ConnectedSensorsPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												ConnectedSensorsScrollPane,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												171, Short.MAX_VALUE)
										.addContainerGap()));
		ConnectedSensorsPanelLayout
				.setVerticalGroup(ConnectedSensorsPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								ConnectedSensorsPanelLayout
										.createSequentialGroup()
										.addComponent(
												ConnectedSensorsScrollPane,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												210, Short.MAX_VALUE)
										.addContainerGap()));

		connectToSensor.setText(">>");
		connectToSensor.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				connectToSensorActionPerformed(evt);
			}
		});

		disconnectFromSensor.setText("<<");
		disconnectFromSensor
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						disconnectFromSensorActionPerformed(evt);
					}
				});

		AvailableResourcesPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Avaible resources"));

		resourcesList = new ResourcesListModel(hostNameText.getText() + ":"
				+ httpPortText.getText());
		AvailableResourcesList.setModel(resourcesList);
		AvailableResourcesScrollPane.setViewportView(AvailableResourcesList);

		javax.swing.GroupLayout AvailableResourcesPanelLayout = new javax.swing.GroupLayout(
				AvailableResourcesPanel);
		AvailableResourcesPanel.setLayout(AvailableResourcesPanelLayout);
		AvailableResourcesPanelLayout
				.setHorizontalGroup(AvailableResourcesPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								AvailableResourcesPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												AvailableResourcesScrollPane,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												171, Short.MAX_VALUE)
										.addContainerGap()));
		AvailableResourcesPanelLayout
				.setVerticalGroup(AvailableResourcesPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								AvailableResourcesPanelLayout
										.createSequentialGroup()
										.addComponent(
												AvailableResourcesScrollPane,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												210, Short.MAX_VALUE)
										.addContainerGap()));

		connectToResource.setText(">>");
		connectToResource
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						connectToResourceActionPerformed(evt);
					}
				});

		disconnectFromResource.setText("<<");
		disconnectFromResource
				.addActionListener(new java.awt.event.ActionListener() {
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

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(hostNameText)
								.addComponent(httpPortText).addGap(10, 10, 10)
								.addComponent(connectHostButton))
				.addComponent(SensorsInformationTabs,
						javax.swing.GroupLayout.DEFAULT_SIZE, 460,
						Short.MAX_VALUE)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(AvailableResourcesPanel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(11, 11, 11)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														disconnectFromResource)
												.addComponent(connectToResource))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(AvailableSensorsPanel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(12, 12, 12)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														disconnectFromSensor)
												.addComponent(connectToSensor))
								.addGap(12, 12, 12)
								.addComponent(ConnectedSensorsPanel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)

				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(hostNameText)
												.addComponent(httpPortText)
												.addComponent(connectHostButton))
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addContainerGap()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGap(6,
																										6,
																										6)
																								.addComponent(
																										connectToSensor)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										disconnectFromSensor))
																				.addComponent(
																						ConnectedSensorsPanel,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						AvailableResourcesPanel,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						AvailableSensorsPanel,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(18, 18,
																		18)
																.addComponent(
																		connectToResource)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		disconnectFromResource)))
								.addContainerGap(20, Short.MAX_VALUE)
								.addComponent(SensorsInformationTabs,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										222, Short.MAX_VALUE).addContainerGap()));

		pack();

	}

	protected void connectHostClicked() {
		AvaibleMetricsListModel.clear();
		resourcesList.clear();
		resourcesList = new ResourcesListModel(hostNameText.getText() + ":"
				+ httpPortText.getText());
		AvailableResourcesList.setModel(resourcesList);
	}

	private void connectToSensorActionPerformed(java.awt.event.ActionEvent evt) {

		// Dodanie nowego sensora na liście connected, jeżeli któryś jest
		// zaznaczony oraz nie ma go już na liście connected

		if (!AvailableSensorsList.isSelectionEmpty()
				&& ConnectedSensorsListModel.indexOf(AvailableSensorsList
						.getSelectedValue().toString()) < 0) {
			String selectedResourceMetric = new String(AvailableSensorsList
					.getSelectedValue().toString());
			String res = "Connecting...";

			Subscription sub;

			try {
				Pattern p = Pattern
						.compile("^(.[a-zA-Z0-9\\.\\-_]*)#(.[a-zA-Z0-9\\-_]*)$");
				Matcher m = p.matcher(AvailableSensorsList.getSelectedValue()
						.toString());

				URL url;

				String monitor = hostNameText.getText() + ":"
						+ httpPortText.getText();
				url = new URL("http://" + monitor + "/subscriptions/");

				String charset = "UTF-8";
				String query = new String();
				if (m.find()) {
					query = m.group(1) + "\n" + m.group(2);
				}

				HttpURLConnection conn;
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");

				OutputStream output = null;
				conn.setDoOutput(true);
				try {
					output = conn.getOutputStream();
					output.write(query.getBytes(charset));
				} finally {
					if (output != null)
						try {
							output.close();
						} catch (IOException logOrIgnore) {
						}
				}

				// Get the response
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(conn.getInputStream()));

				String id = reader.readLine();
				String resource = reader.readLine();
				String metric = reader.readLine();
				String portStr = reader.readLine();

				reader.close();

				System.out.println(id + ", " + resource + ", " + metric + ", "
						+ portStr);

				int port = Integer.parseInt(portStr);
				try {
					channel = SocketChannel.open(new InetSocketAddress(
							hostNameText.getText(), port));
					channel.configureBlocking(false);
				} catch (IOException e) {
					res = String.format("Unable to connect to %s:%d\n",
							hostNameText.getText(), port);
					return;
				}

				sub = new Subscription(Integer.parseInt(id), resource, metric,
						monitor, Integer.parseInt(httpPortText.getText()),
						port, channel);

				// TODO: Tutaj skończyło się pobieranie informacji od serwera

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}

			// wybieranie i subskrypcja kanału
			assert (ConnectedSensorsListModel.size() == subscriptions.size());
			ConnectedSensorsListModel.addElement(sub.getHost() + "/"
					+ sub.getResource() + ":" + sub.getMetric());
			subscriptions.add(sub);

			// Dodanie nowej zakładki dla sensora
			javax.swing.JScrollPane jScrollPane = new javax.swing.JScrollPane();
			javax.swing.JTextArea jTextArea1 = new javax.swing.JTextArea();
			jTextArea1.setColumns(20);
			jTextArea1.setRows(5);
			jTextArea1.setText(res);
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
			Subscription sub = subscriptions.get(ConnectedSensorsList
					.getSelectedIndex());
			// try {
			// sub.getChannel().close();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// // ignore
			// }
			// try {
			// URL url = new URL(sub.getHost() + ":" + new
			// Integer(sub.getHttpPort()).toString());
			// HttpURLConnection urlConnection =
			// (HttpURLConnection)url.openConnection();
			// urlConnection.setRequestMethod("DELETE");
			//
			// } catch (MalformedURLException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (IOException e) {
			//
			// }

			subscriptions.remove(sub);
		}
	}

	private void connectToResourceActionPerformed(java.awt.event.ActionEvent evt) {
		if (!AvailableResourcesList.isSelectionEmpty()
				&& resourcesList.isConnected()) {
			try {
				URL url;
				String monitor = hostNameText.getText() + ":"
						+ httpPortText.getText();
				url = new URL("http://" + monitor + "/subscriptions/metrics/"
						+ AvailableResourcesList.getSelectedValue().toString());

				URLConnection conn;
				conn = url.openConnection();

				// Get the response
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(conn.getInputStream()));
				String line;

				AvaibleMetricsListModel.clear();
				while ((line = reader.readLine()) != null) {
					AvaibleMetricsListModel.addElement(AvailableResourcesList
							.getSelectedValue().toString() + "#" + line);
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

	private void disconnectFromResourceActionPerformed(
			java.awt.event.ActionEvent evt) {
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
					instance = new Client();
					instance.setVisible(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.exit(-1);
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

					SocketChannel channel = instance
							.getSubscriptions()
							.get(instance.getConnectedSensorsList()
									.getSelectedIndex()).getChannel();

					if (channel != null) {
						ByteBuffer buff = ByteBuffer.allocate(1024);
						int ret = channel.read(buff);
						buff.flip();
						if (ret > 0) {
							String msg = Charset.defaultCharset()
									.decode(buff).toString();
							// format wiadomosci:
							// #zasob#metryka#wartosc#
							String[] tokens = msg.split("#");
							// Do wiadomości mogą czasem dostać się
							// jakieś
							// śmieci (szczególnie przy pierwszej
							// wiadomości),
							// dlatego sprawdzamy poprawność formatu;
							if (tokens.length >= 4
									&& tokens[0].isEmpty())
								textArea.setText(tokens[3]);
						} else if (ret < 0) {
							textArea.setText("Subscritpion not available");
						} else {
							// Serwer jeszcze nic nie wysłał
						}
					}
				}

			} catch (Exception ie) {
			}
		}

		// Te dane trzeba uzupełnić na podstawie http (co najmniej port;P).
		// I przenieść kod otwierający socket w odpowiednie miejsce (po
		// otrzymaniu portu).
		// String host = "localhost";
		// int port = 12098; // port wzięty z sufitu. TODO zastąpić prawidlowym
		//
		// try {
		// channel = SocketChannel.open(new InetSocketAddress(host, port));
		// channel.configureBlocking(false);
		// } catch (IOException e) {
		// javax.swing.JScrollPane scrollPane = (javax.swing.JScrollPane)
		// SensorsInformationTabs
		// .getSelectedComponent();
		// javax.swing.JViewport viewPort = (javax.swing.JViewport) scrollPane
		// .getViewport();
		// javax.swing.JTextArea textArea = (javax.swing.JTextArea) viewPort
		// .getView();
		// textArea.setText(String.format("Unable to connect to %s:%d",host,
		// port));
		// }

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
	private javax.swing.JTextField hostNameText;
	private javax.swing.JTextField httpPortText;
	private javax.swing.JButton connectHostButton;
	private static SocketChannel channel;
	private ResourcesListModel resourcesList;
	private static Client instance;

	ArrayList<Subscription> subscriptions = new ArrayList<Subscription>();

	public ArrayList<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public javax.swing.JList getConnectedSensorsList() {
		return ConnectedSensorsList;
	}
	// private String monitorURL;
	// private int subscriptionId;
	// private int subscriptionPort;
}

// class SensorsListModel extends AbstractListModel {
// private ArrayList<String> sensorArray = null;
//
// public SensorsListModel() {
// sensorArray = new ArrayList<String>();
//
// }
//
// public int getSize() {
// return sensorArray.size();
// }
//
// public Object getElementAt(int n) {
// return sensorArray.get(n);
// }
// }

class ResourcesListModel extends AbstractListModel {
	private ArrayList<String> resourcesArray = null;
	private boolean connected = false;

	public ResourcesListModel(String monitorName) {

		try {
			resourcesArray = new ArrayList<String>();
			URL url;

			url = new URL("http://" + monitorName + "/subscriptions/");

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
			connected = true;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println("MalformedURLException");
			resourcesArray.add("Specified address is incorrect");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Włącz serwer HTTP!");
			// e.printStackTrace();
			resourcesArray.add("Server not found");
			resourcesArray.add(String.format("at %s", monitorName));
		}

	}

	public boolean isConnected() {
		return connected;
	}

	public int getSize() {
		return resourcesArray.size();
	}

	public Object getElementAt(int n) {
		return resourcesArray.get(n);
	}

	public void clear() {
		resourcesArray.clear();
	}
}
