package com.zhuxian.ui;

import java.io.*;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class T extends JFrame {

 /**
  * @param args
  * @throws Exception 
  */
 public static void main(String[] args) throws Exception {
  // TODO Auto-generated method stub
  new T();
 }

JButton btnA = new JButton("add");
 JTable tab = new JTable();
 JScrollBar bar = new JScrollBar();
 JScrollPane sp = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
 
 int row = 0;

 Vector<Hashtable<String,String>> data = new Vector<Hashtable<String,String>>();

int page;
 int totalPage;
 int pageSize = 20;
 int displayCount;
 
 public T() {
  super("table");
  
  this.setSize(400,400);
  this.setLayout(new BorderLayout());
  this.add(btnA,BorderLayout.SOUTH);
  
  JPanel p = new JPanel(new BorderLayout());
  //p.add(sp,BorderLayout.CENTER);
  //p.add(bar,BorderLayout.EAST);
  bar.addAdjustmentListener(new AdjustmentListener() {

   public void adjustmentValueChanged(AdjustmentEvent e) {
    page = bar.getValue();
    System.out.println(e.getAdjustmentType());
    refresh(false);
   }
   
  });
  bar.setUnitIncrement(1);
  bar.setMinimum(1);
  bar.setMaximum(1);
  bar.setValue(1);
  sp.setViewportView(tab);
  //sp.setVerticalScrollBar(bar);
  //sp.revalidate();
  //bar.revalidate();
  p.add(sp,BorderLayout.CENTER);
  p.add(bar,BorderLayout.EAST);
  this.add(p,BorderLayout.CENTER);
  tab.setModel(new TModel());
  btnA.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    Hashtable<String,String> h = new Hashtable<String,String>();
    h.put("a", "aa");
    h.put("b", "bb");
    data.add(h);
    refresh(true);
    bar.setValue(page);
    bar.setMaximum(totalPage);
    
    bar.revalidate();
    bar.repaint();
   }
  });
  
  this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
  this.setVisible(true);
 }

void refresh(boolean end) {
  totalPage = data.size()/pageSize;
  if(data.size()%pageSize!=0)totalPage++;
  if(end)page = totalPage;
  displayCount = (data.size()-(page-1)*pageSize)>pageSize?pageSize:data.size()-(page-1)*pageSize;
  if(page==0)displayCount = 0;
  System.out.println(displayCount);
  //System.out.println(data.size()+","+totalPage+","+page+","+displayCount);
  tab.revalidate();
  this.repaint();
  
 }
 
 class TModel extends AbstractTableModel {

  public int getColumnCount() {
   return 2;
  }

  public int getRowCount() {
   return displayCount;
  }

  public Object getValueAt(int rowIndex, int columnIndex) {
   int dataIndex = (page-1)*pageSize+rowIndex;
   if(dataIndex>=data.size()||dataIndex<0)return null;
   else {
    Hashtable<String,String> h = data.get(dataIndex);
    switch(columnIndex) {
    case 0:return h.get("a");
    case 1:return h.get("b");
    default:return null;
    }
   }
  }
  
 }
 
}