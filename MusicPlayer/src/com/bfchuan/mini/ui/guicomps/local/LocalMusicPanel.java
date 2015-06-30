package com.bfchuan.mini.ui.guicomps.local;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.bfchuan.mini.ui.myguis.MyTreeCellRenderer;

/**
 * 作者:Loenidas
 * 时间:2012-5-10
 * piaobomengxiang@163.com
 * 版本:v1.0
 *
 */
@SuppressWarnings("serial")
public class LocalMusicPanel extends JPanel {

    private static LocalMusicPanel lmpnl;
    private DefaultMutableTreeNode root;
    private DefaultMutableTreeNode parent;
    private JTree listTree;
    private JScrollPane listPanel;
    private String treeNode;
    private DownloadedTablePanel detpnl = DownloadedTablePanel.getInstance();
    private DownloadingTablePanel ditpnl = DownloadingTablePanel.getInstance();
    private LoaclTablePanel ltpnl = LoaclTablePanel.getInstance();
    private JLabel positionLabel = new JLabel();


    private LocalMusicPanel() {
        setLayout(null);
        setOpaque(false);
        initListTree();
        changePage("正在下载歌曲");
        
        positionLabel.setFont(new Font("宋体", 15, 15));
        positionLabel.setText("正在下载歌曲");
        this.add(positionLabel);
        this.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
    }

    public static LocalMusicPanel getInstance() {
    	if (lmpnl == null) {
    		lmpnl = new LocalMusicPanel();
    	}
        return lmpnl;
    }

    public void initListTree() {
        root = new DefaultMutableTreeNode();
        parent = new DefaultMutableTreeNode("下载管理");
        parent.add(new DefaultMutableTreeNode("正在下载歌曲"));
        parent.add(new DefaultMutableTreeNode("已下载歌曲"));
        root.add(parent);
        parent = new DefaultMutableTreeNode("本地歌曲");
        root.add(parent);
        listTree = new JTree(root);
        MyTreeCellRenderer render = new MyTreeCellRenderer();
        render.setBackground(getBackground());
        listTree.setCellRenderer(render);
        listTree.setRootVisible(false);
        listTree.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    if (e.getClickCount() == 2) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) ((JTree) e.getSource()).getPathForLocation(e.getX(), e.getY()).getLastPathComponent();
                        Object userInfo = node.getUserObject();
                        if (node.isLeaf()) {
                            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
                            StringBuffer path = new StringBuffer();
                            while (!parent.isRoot()) {
                                path.insert(0, parent.toString() + "->");
                                parent = (DefaultMutableTreeNode) parent.getParent();
                            }
                            path.append(userInfo.toString());
                            positionLabel.setText(path.toString());
                            changePage(userInfo.toString());
                        }
                    }
                } catch (Exception e1) {
                }
            }
        });
        listTree.expandRow(0);
        listPanel = new JScrollPane(listTree);
        add(listPanel);
    }

    //根据node值显示不同的Page
    public void changePage(String node) {
        if (treeNode != null && treeNode.equals(node)) {
        	if (node.equals("本地歌曲")) {
        		LocalTable.getInstance().initLocalSong();
        	}
            return;
        }
        if (treeNode != null) {
            this.remove(getPanelFromPage(treeNode));
        }
        this.add(getPanelFromPage(node));
        treeNode = node;
        repaint();
    }

    private JPanel getPanelFromPage(String node) {
        if (node.equals("正在下载歌曲")) {
        	detpnl.setVisible(false);
        	ltpnl.setVisible(false);
        	ditpnl.setVisible(true);
            return ditpnl;
        } else if (node.equals("已下载歌曲")) {
        	ditpnl.setVisible(false);
        	ltpnl.setVisible(false);
        	detpnl.setVisible(true);
            return detpnl;
        } else if (node.equals("本地歌曲")) {
        	ditpnl.setVisible(false);
        	detpnl.setVisible(false);
        	ltpnl.setVisible(true);
            return ltpnl;
        }
        return null;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Rectangle clip = g.getClipBounds();
        Graphics2D g2d = (Graphics2D) g;
        //渐变效果
        GradientPaint background = new GradientPaint(2f, getHeight() - 59, Color.white, 2f, getHeight() - 1, new Color(255, 240, 255));
        g2d.setPaint(background);
        g2d.fillRect(clip.x, clip.y, clip.width, clip.height);

        positionLabel.setBounds(125, 5, 250, 20);

        listPanel.setBounds(0, 0, 120, getHeight());
        ditpnl.setBounds(120, 30, getWidth() - 121, getHeight() - 1);
        detpnl.setBounds(120, 30, getWidth() - 121, getHeight() - 1);
        ltpnl.setBounds(120, 30, getWidth() - 121, getHeight() - 1);
        
        detpnl.getScrollPane().setBounds(0, 0, getWidth() - 121, getHeight() - 31);
        detpnl.getScrollPane().setPreferredSize(new Dimension(getWidth() - 121, getHeight() - 1));
        detpnl.getScrollPane().updateUI();

        ditpnl.getScrollPane().setBounds(0, 0, getWidth() - 121, getHeight() - 1);
        ditpnl.getScrollPane().setPreferredSize(new Dimension(getWidth() - 121, getHeight() - 1));
        ditpnl.getScrollPane().updateUI();

        ltpnl.getScrollPane().setBounds(0, 55, getWidth() - 121, getHeight() - 1);
        ltpnl.getScrollPane().setPreferredSize(new Dimension(getWidth() - 121, getHeight() - 1));
        ltpnl.getScrollPane().updateUI();
    }
}
