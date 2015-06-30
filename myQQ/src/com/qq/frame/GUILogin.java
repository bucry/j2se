package com.qq.frame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;

public class GUILogin extends JFrame{
        private static final long serialVersionUID = 1L;
        //窗口背景
        private BgPanel bg=new BgPanel();
        private JInternalFrame jif=new JInternalFrame();
        //记录窗体位置
        private Point axis;
        boolean isDragged=false;
        private GUILogin mainLogin=this;
        //设置，最小化，关闭按钮
        ImageIcon []setup={new ImageIcon(getClass().getResource("pics/btn_set_normal.png")),
                                         new ImageIcon(getClass().getResource("pics/btn_set_hover.png")),
                                         new ImageIcon(getClass().getResource("pics/btn_set_press.png"))};
        
        ImageIcon []min= {new ImageIcon(getClass().getResource("pics/btn_mini_normal.png")),
                                          new ImageIcon(getClass().getResource("pics/btn_mini_highlight.png")),
                         new ImageIcon(getClass().getResource("pics/btn_mini_down.png"))};
        
        ImageIcon []close={new ImageIcon(getClass().getResource("pics/btn_close_normal.png")),
                         new ImageIcon(getClass().getResource("pics/btn_close_highlight.png")),
                         new ImageIcon(getClass().getResource("pics/btn_close_down.png"))};
        
        ImageIcon []mina={new ImageIcon(getClass().getResource("pics/mima.png")),
         new ImageIcon(getClass().getResource("pics/mima_hover.png")),
         new ImageIcon(getClass().getResource("pics/mima_press.png"))};
        
        ImageIcon []regs={new ImageIcon(getClass().getResource("pics/zhuce.png")),
         new ImageIcon(getClass().getResource("pics/zhuce_hover.png")),
         new ImageIcon(getClass().getResource("pics/zhuce_press.png"))};
        
        ImageIcon []remPwds={new ImageIcon(getClass().getResource("pics/checkbox_normal.png")),
         new ImageIcon(getClass().getResource("pics/checkbox_hightlight.png")),
         new ImageIcon(getClass().getResource("pics/checkbox_tick_highlight.png")),
         new ImageIcon(getClass().getResource("pics/checkbox_tick_normal.png"))};
        
        ImageIcon []swits={new ImageIcon(getClass().getResource("pics/switch_single_normal.png")),
         new ImageIcon(getClass().getResource("pics/switch_single_hover.png")),
         new ImageIcon(getClass().getResource("pics/switch_single_down.png"))};
        
        ImageIcon []loginImages={new ImageIcon(getClass().getResource("pics/button_login_normal.png")),
         new ImageIcon(getClass().getResource("pics/button_login_hover.png")),
         new ImageIcon(getClass().getResource("pics/button_login_down.png"))};
        
        ImageIcon []rights={new ImageIcon(getClass().getResource("pics/corner_right_normal_breath.png")),
         new ImageIcon(getClass().getResource("pics/corner_right_hover.png")),
         new ImageIcon(getClass().getResource("pics/corner_right_normal_down.png"))};
        
        
        ImageIcon []states={new ImageIcon(getClass().getResource("pics/invisible.png"))};
        
        private JButton jsetup=new JButton(setup[0]);
        private JButton jmin=new JButton(min[0]);
        private JButton jclose=new JButton(close[0]);
        
        private JLabel header=new JLabel(new ImageIcon(getClass().getResource("pics/login_head_bkg__normal.png")));
        private JLabel photo=new JLabel(new ImageIcon(getClass().getResource("pics/UserHeadTemp100_843438416.png")));
        
        //ID输入与密码输入
        private JComboBox<String> userInput=new JComboBox<String>();
        private JQPasswordField pwdInput=new JQPasswordField();
        
        //找回密码与注册
        private JLabel minaFind=new JLabel(mina[0]);
        private JLabel register=new JLabel(regs[0]);
        //CheckBox
        private JCheckBox remPwd=new JCheckBox("记住密码",remPwds[0]);
        private JCheckBox autoLogin=new JCheckBox("自动登录",remPwds[0]);
        
        //给窗体增加多人登录        
        private JLabel swit=new JLabel(swits[0]);
        
        //登录
        private JButton jlogin=new JButton("登 录",loginImages[0]);
        
        //右侧
        private JLabel right=new JLabel(rights[0]);
        
        
        //状态
        private JLabel state=new JLabel(states[0]);
        
        //给窗体填加拖动功能
        void setWindowDray(JComponent jc){
                jc.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                                isDragged=true;
                                axis=new Point(e.getX(),e.getY());
                                jif.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                        }
                        @Override
                        public void mouseReleased(MouseEvent e) {
                                isDragged=false;
                                jif.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }                        
                });
                jc.addMouseMotionListener(new MouseMotionAdapter() {
                        @Override
                        public void mouseDragged(MouseEvent e) {
                                if(isDragged){
                                        mainLogin.setLocation(e.getXOnScreen()-axis.x,e.getYOnScreen()-axis.y);
                                }
                        }
                });
        }
        //窗体中按钮的监听器
        class ButtonListener extends MouseAdapter{

                @Override
                public void mousePressed(MouseEvent e) {
                        Object obj=e.getSource();
                        if( obj instanceof JButton){
                                JButton jbtn=(JButton)obj;
                                if(jbtn==jsetup){
                                        jbtn.setIcon(setup[2]);
                                }
                                else if(jbtn==jclose){
                                        jbtn.setIcon(close[2]);
                                        System.exit(0);
                                }
                                else if(jbtn==jmin){
                                        jbtn.setIcon(min[2]);
                                        //jdk7 setType
                                        mainLogin.setExtendedState(JFrame.ICONIFIED);
                                }
                                else if(jbtn==jlogin){
                                        jbtn.setIcon(loginImages[2]);
                                }
                        }
                        else if(obj instanceof JLabel){
                                JLabel jbl=(JLabel)obj;
                                if(jbl==register){
                                        jbl.setIcon(regs[2]);
                                }
                                else if(jbl==minaFind){
                                        jbl.setIcon(mina[2]);
                                }
                                else if(jbl==swit){
                                        jbl.setIcon(swits[2]);
                                }
                                else if(jbl==right){
                                        jbl.setIcon(rights[2]);
                                }
                        }
                        else if(obj instanceof JCheckBox){
                                JCheckBox jcb=(JCheckBox)obj;
                                if(!jcb.isSelected()){
                                        jcb.doClick();
                                        jcb.setSelected(true);
                                        jcb.setSelectedIcon(remPwds[2]);
                                }
                                else{
                                        jcb.doClick();
                                        jcb.setSelected(false);
                                        jcb.setIcon(remPwds[1]);
                                }
                        }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                        Object obj=e.getSource();
                        if( obj instanceof JButton){
                                JButton jbtn=(JButton)obj;
                                if(jbtn==jsetup){
                                        jbtn.setIcon(setup[0]);
                                }
                                else if(jbtn==jclose){
                                        jbtn.setIcon(close[0]);
                                }
                                else if(jbtn==jmin){
                                        jbtn.setIcon(min[0]);
                                }
                                else if(jbtn==jlogin){
                                        jbtn.setIcon(loginImages[0]);
                                }
                        }
                        else if(obj instanceof JLabel){
                                JLabel jbl=(JLabel)obj;
                                if(jbl==register){
                                        jbl.setIcon(regs[0]);
                                }
                                else if(jbl==minaFind){
                                        jbl.setIcon(mina[0]);
                                }
                                else if(jbl==swit){
                                        jbl.setIcon(swits[0]);
                                }
                                else if(jbl==right){
                                        jbl.setIcon(rights[0]);
                                }
                        }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                        Object obj=e.getSource();
                        if( obj instanceof JButton){
                                JButton jbtn=(JButton)obj;
                                if(jbtn==jsetup){
                                        jbtn.setIcon(setup[1]);
                                }
                                else if(jbtn==jclose){
                                        jbtn.setIcon(close[1]);
                                }
                                else if(jbtn==jmin){
                                        jbtn.setIcon(min[1]);
                                }
                                else if(jbtn==jlogin){
                                        jbtn.setIcon(loginImages[1]);
                                }
                        }
                        else if(obj instanceof JLabel){
                                JLabel jbl=(JLabel)obj;
                                if(jbl==register){
                                        jbl.setIcon(regs[1]);
                                }
                                else if(jbl==minaFind){
                                        jbl.setIcon(mina[1]);
                                }
                                else if(jbl==swit){
                                        jbl.setIcon(swits[1]);
                                }
                                else if(jbl==right){
                                        jbl.setIcon(rights[1]);
                                }
                        }
                        else if(obj instanceof JCheckBox){
                                JCheckBox jcb=(JCheckBox)obj;
                                if(!jcb.isSelected())
                                        jcb.setIcon(remPwds[1]);
                                else
                                        jcb.setSelectedIcon(remPwds[2]);
                                }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                        Object obj=e.getSource();
                        if( obj instanceof JButton){
                                JButton jbtn=(JButton)obj;
                                if(jbtn==jsetup){
                                        jbtn.setIcon(setup[0]);
                                }
                                else if(jbtn==jclose){
                                        jbtn.setIcon(close[0]);
                                }
                                else if(jbtn==jmin){
                                        jbtn.setIcon(min[0]);
                                }
                                else if(jbtn==jlogin){
                                        jbtn.setIcon(loginImages[0]);
                                }
                        }
                        else if(obj instanceof JLabel){
                                JLabel jbl=(JLabel)obj;
                                if(jbl==register){
                                        jbl.setIcon(regs[0]);
                                }
                                else if(jbl==minaFind){
                                        jbl.setIcon(mina[0]);
                                }
                                else if(jbl==swit){
                                        jbl.setIcon(swits[0]);
                                }
                                else if(jbl==right){
                                        jbl.setIcon(rights[0]);
                                }
                        }
                        else if(obj instanceof JCheckBox){
                                JCheckBox jcb=(JCheckBox)obj;
                                if(!jcb.isSelected())
                                        jcb.setIcon(remPwds[0]);
                                else
                                        jcb.setSelectedIcon(remPwds[3]);
                        }
                }
                
        }
        public void setButton(JButton jbtn){
                jbtn.setOpaque(false);
                jbtn.setMargin(new Insets(0, 0, 0, 0));
                jbtn.setContentAreaFilled(false);
                jbtn.setFocusPainted(false);
        jbtn.setBorderPainted(false);
        jbtn.setBorder(null);
        jbtn.addMouseListener(new ButtonListener());
        }
        public void setJLabel(JLabel jlb){
                jlb.setOpaque(false);
                jlb.setBorder(null);
                jlb.setFocusable(false);
                jlb.addMouseListener(new ButtonListener());
        }
        public void setJCheckbox(JCheckBox jcb){
                jcb.setOpaque(false);
                jcb.setBorder(null);
                jcb.setBorderPainted(false);
                jcb.setMargin(new Insets(0, 0, 0, 0));
                jcb.setContentAreaFilled(false);
                jcb.setFocusable(false);
                jcb.setFont(new Font("宋体",0,12));
                jcb.addMouseListener(new ButtonListener());
        }
        //窗口的基本配置
        public void initWindow(){
                dispose();
                setUndecorated(true);
                setRootPaneCheckingEnabled(false);
                setSize(380, 296);
                setResizable(false);
                javax.swing.plaf.InternalFrameUI jf=jif.getUI();
                ((javax.swing.plaf.basic.BasicInternalFrameUI)jf).setNorthPane(null);
                jif.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
                jif.setSize(380, 296);
                jif.setEnabled(false);
                
        jsetup.setBounds(287, -2, 28, 20);
        jclose.setBounds(343, -2, 39, 20);
        jmin.setBounds(315, -2, 28, 20);
                jif.getContentPane().add(jsetup);
                jif.getContentPane().add(jclose);
                jif.getContentPane().add(jmin);
                
                setJLabel(header);
                header.setBounds(20,140,87,87);
                jif.getContentPane().add(header);
                
                setJLabel(photo);
                photo.setBounds(23,142,83,83);
                jif.getContentPane().add(photo);
                
                userInput.setBounds(118,142,188,25);
                userInput.setEditable(true);
                userInput.setFont(new Font("Times New Roman",0,14));
                jif.getContentPane().add(userInput);
                
                setJLabel(register);
                register.setBounds(316, 144, 51, 16);
                jif.getContentPane().add(register);
                
                pwdInput.setBounds(118,175,188,25);
                jif.getContentPane().add(pwdInput);
                
                setJLabel(minaFind);
                minaFind.setBounds(316, 178, 51, 16);
                jif.getContentPane().add(minaFind);
                
                remPwd.setBounds(117,210,72,16);
                jif.getContentPane().add(remPwd);
                setJCheckbox(remPwd);
                
                autoLogin.setBounds(202,210,72,16);
                jif.getContentPane().add(autoLogin);
                setJCheckbox(autoLogin);
                
                setJLabel(swit);
                swit.setBounds(15,255,25,25);
                jif.getContentPane().add(swit);
                
                setButton(jlogin);
                jlogin.setBounds(115, 247, 165, 40);
                jlogin.setFont(new Font("宋体",0,12));
                jlogin.setIconTextGap(-108);
                jif.getContentPane().add(jlogin);
                
                setJLabel(right);
                right.setBounds(333,248,38,38);
                jif.getContentPane().add(right);
                
                setJLabel(state);
                state.setBounds(92,210,11,11);
                jif.getContentPane().add(state,3);
                
                setWindowDray(jif);
                jif.getContentPane().add(bg);
                getContentPane().add(jif);
                setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-getSize().width)/2,
                                (Toolkit.getDefaultToolkit().getScreenSize().height-getSize().height)/2);
                setButton(jsetup);
        setButton(jclose);
        setButton(jmin);
        
        this.setTitle("PQQ-http://www.yanyulin.info");
        this.setIconImage(new ImageIcon(getClass().getResource("pics/xiaomi.jpg")).getImage());
                setAlwaysOnTop(true);
                jif.setVisible(true);
                setVisible(true);
        }
        public GUILogin(){
                initWindow();
        }
        public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {                
                @Override
                public void run() {
                        new GUILogin();                
                }
        });
        }
}