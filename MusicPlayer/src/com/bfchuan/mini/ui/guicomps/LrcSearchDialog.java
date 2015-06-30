package com.bfchuan.mini.ui.guicomps;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import netlrc.search.LRC;
import netlrc.search.SearchLRC;


import com.bfchuan.mini.bo.LrcBo;
import com.bfchuan.mini.dao.DaoFactory;
import com.bfchuan.mini.ui.myguis.ColorPanel;
import com.bfchuan.mini.ui.myguis.LinkButton;
import com.bfchuan.mini.util.ImageTool;

/**
 * 歌词搜索对话框
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class LrcSearchDialog extends JDialog implements ActionListener {

    public static LrcSearchDialog skinDlg;
    private ColorPanel mainPnl;
    private ImageTool imgTool = ImageTool.getInstance();
	private JTextField keywordInput;//关键字输入框
	private LinkButton searchBtn;//搜索按钮
	private JRadioButton searchConditions[] = new JRadioButton[2];
	private ButtonGroup bgroup = new ButtonGroup();
	private Object[] attributes = {"歌曲名称", "歌手名称", "专辑", "预览", "载入"};
	private DefaultTableModel myModel = new DefaultTableModel();
	private JTable resultTable = new JTable(myModel);
	private JScrollPane outPane = new JScrollPane(resultTable);
	
	private JLabel lab1 = new JLabel("歌词预览:");
	private JTextArea lrcAppearArea = new JTextArea();
	private JScrollPane lrcAppearPane = new JScrollPane(lrcAppearArea);
	
	private SearchLRC searchLRC;

    private LrcSearchDialog() {
    	this.setLayout(null);
    	
    	mainPnl = new ColorPanel(Color.WHITE);

        mainPnl.setLayout(null);
        mainPnl.setBounds(0, 0, 700, 400);

        init();
        
        add(mainPnl);
        setTitle("歌词搜索");
        setIconImage(imgTool.getImage("images/titleImage.jpg"));
        setResizable(false);
        setSize(700, 400);
        setLocationRelativeTo(null);
    }
    
	private void init() {
		keywordInput = new JTextField();
		keywordInput.setBounds(10, 10, 400, 25);
		mainPnl.add(keywordInput);
		
		searchBtn = new LinkButton("搜索");
		searchBtn.setBounds(420, 10, 30, 25);
		searchBtn.addActionListener(this);
		mainPnl.add(searchBtn);
		
        searchConditions[0] = new JRadioButton("按歌名");
        searchConditions[1] = new JRadioButton("按歌手");
        searchConditions[0].setSelected(true);
        bgroup.add(searchConditions[0]);
        bgroup.add(searchConditions[1]);
        searchConditions[0].setBounds(10, 40, 70, 25);
        searchConditions[1].setBounds(100, 40, 70, 25);
        mainPnl.add(searchConditions[0]);
        mainPnl.add(searchConditions[1]);
        
		outPane.setBounds(10, 76, 400, 280);
		outPane.setBackground(Color.white);
		outPane.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		outPane.getViewport().setBackground(Color.white);
		mainPnl.add(outPane);
        
		resultTable.getTableHeader().setReorderingAllowed(false);
        resultTable.setRowHeight(21);
		myModel.setColumnIdentifiers(attributes);
		
		lab1.setBounds(420, 45, 70, 25);
		mainPnl.add(lab1);
		
		lrcAppearArea.setEditable(false);
		lrcAppearArea.setBackground(Color.white);
		lrcAppearPane.setBounds(420, 76, 260, 280);
		lrcAppearPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		mainPnl.add(lrcAppearPane);
		
		initTableListener();
	}
	
	/**
	 * 根据歌曲名称搜索歌词,并显示搜索歌词的对话框
	 * @param songName
	 */
	public void searchByNameAndShowDialog(String songName) {
		this.setVisible(true);
		this.searchByName(songName);
	}
	
	/**
	 * 根据歌曲名称搜索歌词
	 * @param songName
	 */
	public void searchByName(String songName) {
		lrcAppearArea.setText("");
		keywordInput.setText(songName);
		searchLRC = new SearchLRC(DaoFactory.getInstance().getLrcDao().getNetLrcList(), songName);
		searchLRC.setDefaultTableModel(this.myModel);
		searchLRC.setSearchWay(SearchLRC.Search_By_Name);
		searchLRC.start();
	}
	
	/**
	 * 初始化歌词搜索结果的table监听
	 */
	private void initTableListener() {
		resultTable.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				ArrayList<LRC> lrcList = DaoFactory.getInstance().getLrcDao().getNetLrcList();
				int row = resultTable.getSelectedRow();
				int col = resultTable.getSelectedColumn();
				
				LRC tempLRC = lrcList.get(row);
				if ("预览".equals(resultTable.getColumnName(col))) {
					lrcAppearArea.setText("正在载入  " + tempLRC.getName() + " 的歌词...\n请稍后.....");
					String lrcContent = SearchLRC.getLrcContent(tempLRC.getUrl());
					lrcAppearArea.setText(lrcContent);
					lrcAppearArea.setCaretPosition(0);//从上面开始显示
				} else if ("载入".equals(resultTable.getColumnName(col))) {
					LrcBo.getInstance().loadNetLrc(tempLRC.getUrl());
					LrcSearchDialog.this.setVisible(false);
				}
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == searchBtn) {
			String keyword = keywordInput.getText().trim();
			if (!"".equals(keyword)) {
				lrcAppearArea.setText("");
				searchLRC = new SearchLRC(DaoFactory.getInstance().getLrcDao().getNetLrcList(), keyword);
				searchLRC.setDefaultTableModel(this.myModel);
				
				//设置搜索方式
				if (searchConditions[0].isSelected()) {//按歌名
					searchLRC.setSearchWay(SearchLRC.Search_By_Name);
				} else {
					searchLRC.setSearchWay(SearchLRC.Search_By_Singer);
				}
				searchLRC.start();
			}
		}
	}
    
    public static LrcSearchDialog getInstance() {
    	if (skinDlg == null) {
    		skinDlg = new LrcSearchDialog();
    	}
    	return skinDlg;
    }

/*    public static void main(String args[]) {
        LrcSearchDialog sd = LrcSearchDialog.getInstance();
        sd.setVisible(true);
    }*/
  
}
