/* 2018-05-27 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class FileManager {

	static String[] envNameList = new String[3];
	static String[] tmRootList = new String[3];
	static String[] serviceNameList = new String[3];
	static String[] appRootList = new String[3];
	static String[] serverList = new String[3];
	static String[] dbNameList = new String[3];
	static String[] userList = new String[3];
	static String[] passwordList = new String[3];
	
	static JTextField envTextField = new JTextField();
	static JTextField tmRootTextField = new JTextField();
	static JTextField serviceNameTextField = new JTextField();
	static JTextField appRootTextField = new JTextField();
	static JTextField serverTextField = new JTextField();
	static JTextField dbNameTextField = new JTextField();
	static JTextField userTextField = new JTextField();
	static JTextField passwordTextField = new JTextField();
	
	static int index = 0;
	static int di=0, pi=0, ti=0;
	
	public static String chooseFile() {
		String path = null;
		JFileChooser fc = new JFileChooser();
//		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
//      fc.setFileFilter(null);
        int returnVal = fc.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	File file = fc.getSelectedFile();
        	path = file.getPath();
            System.out.println("You chose file: " + file.getName());
            System.out.println("file path : " + path);
        }
		return path;
	}
	
	public static String chooseDir() {
		String path = null;
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
//      fc.setFileFilter(null);
        int returnVal = fc.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	File file = fc.getSelectedFile();
        	path = file.getPath();
            System.out.println("You chose directory: " + file.getName());
            System.out.println("dir path : " + path);
        }
		return path;
	}
	
	/*read property.xml */
	public static void readPropertyFile(String path) throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//		dbf.setIgnoringComments(true);
//		dbf.setIgnoringElementContentWhitespace(false);
//		dbf.setCoalescing(false);
//		dbf.setExpandEntityReferences(false);		
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File(path));
		doc.getDocumentElement().normalize();
		
		NodeList envList = doc.getElementsByTagName("environment");
		for (int i=0; i<envList.getLength(); i++) {
			Node nNode = envList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
			    envNameList[i] = eElement.getElementsByTagName("envName").item(0).getTextContent().trim().toLowerCase();
			    tmRootList[i] = eElement.getElementsByTagName("tmRoot").item(0).getTextContent().trim();
			    serviceNameList[i] = eElement.getElementsByTagName("serviceName").item(0).getTextContent().trim();
			    appRootList[i] = eElement.getElementsByTagName("appRoot").item(0).getTextContent().trim();
			    serverList[i] = eElement.getElementsByTagName("server").item(0).getTextContent().trim();
			    dbNameList[i] = eElement.getElementsByTagName("dbName").item(0).getTextContent().trim();
			    userList[i] = eElement.getElementsByTagName("user").item(0).getTextContent().trim();
			    passwordList[i] = eElement.getElementsByTagName("password").item(0).getTextContent().trim();
			    
			    System.out.println("envName = " + envNameList[i]);
			    System.out.println("tmRoot = " + tmRootList[i]);
			    
			    updateData();
			}
		}
	}
	
	public static void updateData() {
		Data.setEnvNameList(envNameList);
	    Data.setTmRootList(tmRootList);
	    Data.setServiceNameList(serviceNameList);
	    Data.setAppRootList(appRootList);
	    Data.setServerList(serverList);
	    Data.setDbNameList(dbNameList);
	    Data.setUserList(userList);
	    Data.setPasswordList(passwordList);
	}
	
	public static void editPropertyFile(String path) {
		JFrame frame = new JFrame("Properties");
		
		envNameList = Data.getEnvNameList();
		tmRootList = Data.getTmRootList();
		serviceNameList = Data.getServiceNameList();
		appRootList = Data.getAppRootList();
		serverList = Data.getServerList();
		dbNameList = Data.getDbNameList();
		userList = Data.getUserList();
		passwordList = Data.getPasswordList();
				
		for (int i=0; i<envNameList.length; i++) {
			if (envNameList[i].equals("dev")) {
				di = i;
			} else if (envNameList[i].equals("prod")) {
				pi = i;
			} else if (envNameList[i].equals("train")) {
				ti = i;
			}
		}
		System.out.println("di = " + di);
		System.out.println("pi = " + pi);
		System.out.println("ti = " + ti);
		JFrame.setDefaultLookAndFeelDecorated(true);	
		
		JPanel panel = new JPanel();
		JButton devButton = new JButton("dev");
		JButton prodButton = new JButton("prod");
		JButton trainButton = new JButton("train");
		
		JLabel envLabel = new JLabel("envName");
		envTextField.setText(envNameList[di]);
		envTextField.setEnabled(false);
		
		JLabel tmRootLabel = new JLabel("Tomcat Root");
		tmRootTextField.setText(tmRootList[di]);			
		
		JLabel serviceNameLabel = new JLabel("Service name");
		serviceNameTextField.setText(serviceNameList[di]);
		
		JLabel appRootLabel = new JLabel("Application Root");
		appRootTextField.setText(appRootList[di]);
		
		JLabel serverLabel = new JLabel("Database Server");
		serverTextField.setText(serverList[di]);
		
		JLabel dbNameLabel = new JLabel("Database Name");
		dbNameTextField.setText(dbNameList[di]);
		
		JLabel userLabel = new JLabel("User");
		userTextField.setText(userList[di]);
		
		JLabel passwordLabel = new JLabel("password");
		passwordTextField.setText(passwordList[di]);
		
		JButton saveButton = new JButton("Save");
		JButton exitButton = new JButton("Exit");
		
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addGroup(layout.createSequentialGroup()
								.addComponent(devButton)
								.addComponent(prodButton)
								.addComponent(trainButton))							
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(envLabel)
										.addComponent(tmRootLabel)
										.addComponent(serviceNameLabel)
										.addComponent(appRootLabel)
										.addComponent(serverLabel)
										.addComponent(dbNameLabel)
										.addComponent(userLabel)
										.addComponent(passwordLabel))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(envTextField)
										.addComponent(tmRootTextField)
										.addComponent(serviceNameTextField)
										.addComponent(appRootTextField)
										.addComponent(serverTextField)
										.addComponent(dbNameTextField)
										.addComponent(userTextField)
										.addComponent(passwordTextField)))
						.addComponent(saveButton)
						.addComponent(exitButton)
				)					
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(devButton)
						.addComponent(prodButton)
						.addComponent(trainButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(envLabel)
						.addComponent(envTextField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(tmRootLabel)
						.addComponent(tmRootTextField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(serviceNameLabel)
						.addComponent(serviceNameTextField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(appRootLabel)
						.addComponent(appRootTextField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(serverLabel)
						.addComponent(serverTextField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(dbNameLabel)
						.addComponent(dbNameTextField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(userLabel)
						.addComponent(userTextField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(passwordLabel)
						.addComponent(passwordTextField))
				.addComponent(saveButton)
				.addComponent(exitButton)
		);
		
		devButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				updateTable(di);
				index = di;
			}
		});
		
		prodButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				updateTable(pi);
				index = pi;
			}
		});
		
		trainButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				updateTable(ti);
				index = ti;
			}
		});
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				envNameList[index] = envTextField.getText();
			    tmRootList[index] = tmRootTextField.getText();
			    serviceNameList[index] = serviceNameTextField.getText();
			    appRootList[index] = appRootTextField.getText();
			    serverList[index] = serverTextField.getText();				    
			    dbNameList[index] = dbNameTextField.getText();
			    userList[index] = userTextField.getText();				    
			    passwordList[index] = passwordTextField.getText();
			    
			    updateData();
			    
			    //write property.xml				    
			    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				dbf.setIgnoringComments(true);
				dbf.setIgnoringElementContentWhitespace(false);
				dbf.setCoalescing(false);
				DocumentBuilder db;
				try {
					db = dbf.newDocumentBuilder();
					Document doc = db.parse(new File(path));
					doc.getDocumentElement().normalize();
					NodeList envList = doc.getElementsByTagName("environment");
					for (int i=0; i<envList.getLength(); i++) {
						Node nNode = envList.item(i);
						if (nNode.getNodeType() == Node.ELEMENT_NODE) {
							Element eElement = (Element) nNode;
							eElement.getElementsByTagName("envName").item(0).setTextContent(envNameList[i]);
						    eElement.getElementsByTagName("tmRoot").item(0).setTextContent(tmRootList[i]);
						    eElement.getElementsByTagName("serviceName").item(0).setTextContent(serviceNameList[i]);
						    eElement.getElementsByTagName("appRoot").item(0).setTextContent( appRootList[i]);
						    eElement.getElementsByTagName("server").item(0).setTextContent(serverList[i]);
						    eElement.getElementsByTagName("dbName").item(0).setTextContent(dbNameList[i]);
						    eElement.getElementsByTagName("user").item(0).setTextContent(userList[i]);
						    eElement.getElementsByTagName("password").item(0).setTextContent(passwordList[i]);
						}
					}
					 // send DOM to file
					Transformer tr = TransformerFactory.newInstance().newTransformer();
//			            tr.setOutputProperty(OutputKeys.INDENT, "yes");
		            tr.setOutputProperty(OutputKeys.METHOD, "xml");
		            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//			            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");
		            tr.transform(new DOMSource(doc), 
		                                 new StreamResult(new FileOutputStream(path)));
		            
				} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {						
					e.printStackTrace();
				}
			}
		});
		
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				frame.dispose();
			}
		});
		
		frame.add(panel);			
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void updateTable(int ind) {
		envTextField.setText(envNameList[ind]);		
		tmRootTextField.setText(tmRootList[ind]);
		serviceNameTextField.setText(serviceNameList[ind]);
		appRootTextField.setText(appRootList[ind]);
		serverTextField.setText(serverList[ind]);
		dbNameTextField.setText(dbNameList[ind]);
		userTextField.setText(userList[ind]);
		passwordTextField.setText(passwordList[ind]);
	}

}

