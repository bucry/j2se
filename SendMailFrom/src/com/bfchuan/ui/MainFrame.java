package com.bfchuan.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.bfchuan.entity.TbUser;
import com.bfchuan.rec.mail.ReciveMail;

public class MainFrame {
	
	public static TbUser tbUser = new TbUser();
	private static DefaultTableModel model;
	private static JTable table;
	private static JFrame mainWin = new JFrame("TT邮箱");
	
	

	
	
	public  void getMail(){
		System.out.println(tbUser.getName());
		System.out.println(tbUser.getPass());
		try{
			 Properties props = System.getProperties();  
	         props.put("mail.smtp.host", "smtp.163.com");  
	         props.put("mail.smtp.auth", "true");  
	         Session session = Session.getDefaultInstance(props, null); 
	         URLName urln = new URLName("pop3", "pop.qq.com", 110, null,  
	        		 tbUser.getName(), tbUser.getPass());  
	         Store store = session.getStore(urln);  
	         store.connect();  
	         Folder folder = store.getFolder("INBOX");  
	         folder.open(Folder.READ_ONLY);  
	         Message message[] = folder.getMessages();  
	         System.out.println("Messages's length: " + message.length);  
	         ReciveMail pmm = null;  
	         model = new DefaultTableModel(message.length ,3);
	 		table = new JTable(model);
	         for (int i = 0; i < message.length; i++) {  
	             System.out.println("======================");  
	             pmm = new ReciveMail((MimeMessage) message[i]);  
	             model.setValueAt("标题：" + pmm.getSubject(), i , 0);
	             model.setValueAt("来自：：" + pmm.getFrom(), i , 1);
	             model.setValueAt("时间：" + pmm.getSentDate(), i , 2);
	             System.out.println("Message " + i + " subject: " + pmm.getSubject());  
	             System.out.println("Message " + i + " sentdate: "+ pmm.getSentDate());  
	             System.out.println("Message " + i + " replysign: "+ pmm.getReplySign());  
	             System.out.println("Message " + i + " hasRead: " + pmm.isNew());  
	             System.out.println("Message " + i + "  containAttachment: "+ pmm.isContainAttach((Part) message[i]));  
	             System.out.println("Message " + i + " form: " + pmm.getFrom());  
	             System.out.println("Message " + i + " to: "+ pmm.getMailAddress("to"));  
	             System.out.println("Message " + i + " cc: "+ pmm.getMailAddress("cc"));  
	             System.out.println("Message " + i + " bcc: "+ pmm.getMailAddress("bcc"));  
	             pmm.setDateFormat("yy年MM月dd日 HH:mm");  
	             System.out.println("Message " + i + " sentdate: "+ pmm.getSentDate());  
	             System.out.println("Message " + i + " Message-ID: "+ pmm.getMessageId());  
	             // 获得邮件内容===============  
	             pmm.getMailContent((Part) message[i]);  
	             System.out.println("Message " + i + " bodycontent: \r\n"  
	                     + pmm.getBodyText());  
	             pmm.setAttachPath("c:\\");   
	             pmm.saveAttachMent((Part) message[i]);   
	         }   
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"考！你在乱输邮箱？我没做正则匹配，老实点");
		}
		
		mainWin.add(new JScrollPane(table), BorderLayout.CENTER);
		mainWin.setTitle("TT邮箱2013");
		mainWin.setSize(800, 600);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		mainWin.setLocation((screen.width - mainWin.getSize().width) / 2,
				(screen.height - mainWin.getSize().height) / 2);
		mainWin.setVisible(true);
		mainWin.setResizable(false);
		mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
