package chattingapplication;

import static chattingapplication.Server.a1;
import static chattingapplication.Server.dout;
import static chattingapplication.Server.f1;
import static chattingapplication.Server.formatLabel;
import static chattingapplication.Server.vertical;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Client implements ActionListener {

    JPanel p1;
    JTextField t1;
    JButton b1;

    static JPanel a1;
    static JFrame f1 = new JFrame();
    static Box vertical = Box.createVerticalBox();
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    Boolean typing;

    Client() {
        f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(75, 0, 130));
        p1.setBounds(0, 0, 450, 70);
        f1.add(p1);

        JLabel l1 = new JLabel("");
        l1.setFont(new Font("SAN_SERIF", Font.PLAIN, 40));
        l1.setForeground(Color.white);
        l1.setBounds(15, 15, 40, 40);
        p1.add(l1);
        l1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("chattingapplication/icons/3.png"));
        Image i5 = i4.getImage().getScaledInstance(55, 55, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel l2 = new JLabel(i6);
        l2.setBounds(55, 7, 55, 55);
        p1.add(l2);

        JLabel l5 = new JLabel("");
        l5.setFont(new Font("SAN_SERIF", Font.PLAIN, 50));
        l5.setForeground(Color.white);
        l5.setBounds(280, 5, 50, 50);
        p1.add(l5);

        JLabel l6 = new JLabel("");
        l6.setFont(new Font("SAN_SERIF", Font.PLAIN, 40));
        l6.setForeground(Color.white);
        l6.setBounds(350, 15, 40, 40);
        p1.add(l6);

        JLabel l7 = new JLabel("");
        l7.setFont(new Font("SAN_SERIF", Font.PLAIN, 25));
        l7.setForeground(Color.white);
        l7.setBounds(400, 18, 35, 35);
        p1.add(l7);

        JLabel l3 = new JLabel("Ruchita");
        l3.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
        l3.setForeground(Color.white);
        l3.setBounds(120, 15, 100, 18);
        p1.add(l3);

        JLabel l4 = new JLabel("Active Now");
        l4.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        l4.setForeground(Color.white);
        l4.setBounds(120, 35, 100, 20);
        p1.add(l4);

        Timer t = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (!typing) {
                    l4.setText("Active Now");
                }
            }
        });
        t.setInitialDelay(2000);

        
        a1 = new JPanel();
        a1.setBounds(00, 70, 450, 580);
        a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f1.add(a1);

        JScrollPane sp = new JScrollPane(a1);
        sp.setBounds(0, 70, 450, 580);
        sp.getVerticalScrollBar().setUnitIncrement(20);
        sp.setBorder(BorderFactory.createEmptyBorder());
        f1.add(sp);

        
         t1 = new JTextField();
        t1.setBounds(10, 660, 340, 40);
       // t1.setBorder(null);
        t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f1.add(t1);

        t1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                l4.setText("typing...");

                t.stop();

                typing = true;
            }

            public void keyReleased(KeyEvent ke) {
                typing = false;

                if (!t.isRunning()) {
                    t.start();
                }
            }
        });

       
        b1 = new JButton("Send");
        b1.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
        b1.setForeground(Color.white);
        b1.setBackground(Color.decode("#4B0082"));
        b1.setBorder(null);
        b1.setBounds(360, 660, 60, 40);
        b1.addActionListener(this);
        f1.add(b1);

        f1.setLayout(null);
        f1.setSize(450, 750);
        f1.setLocation(400, 200);
        // setUndecorated(true);
        f1.setVisible(true);

    }

    public void actionPerformed(ActionEvent ae) {

        try {
            String out = t1.getText();
            JPanel p2 = formatLabel(out);
            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);
            dout.writeUTF(out);
            t1.setText("");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static JPanel formatLabel(String out) {
       JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        p3.setBorder(new EmptyBorder(5, 0, 5, 0));
        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">" + out + "</p></html>");
        l1.setBackground(new Color(215, 215, 250));
        l1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(10, 10, 10, 10));
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));
        p3.add(l1);
        p3.add(l2);
        return p3;
    }

    public static void main(String[] args) {
        new Client().f1.setVisible(true);

        try {
            s = new Socket("127.0.0.1", 6001);
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            String msginput = "";

            while (true) {
                a1.setLayout(new BorderLayout());
                msginput = din.readUTF();
                JPanel p2 = formatLabel(msginput);
                JPanel left = new JPanel(new BorderLayout());
                left.add(p2, BorderLayout.LINE_START);

                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);
                f1.validate();
            }

        } catch (Exception e) {
        }
    }
}
