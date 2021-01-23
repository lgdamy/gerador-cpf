package com.damytec.view;

import com.damytec.pojo.Cpf;
import com.damytec.service.GeradorService;
import com.damytec.util.MotionPanel;
import com.damytec.util.SimpleDocumentListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author lgdamy@raiadrogasil.com on 22/01/2021
 */
public class MainWindow extends JFrame {
    private JPanel basePanel;
    private JTextField inputCpf;
    private JTextField dvField;
    private JTextField formattedField;
    private JTextField rawField;
    private JLabel validImage;
    private JLabel closeButton;
    private GeradorService service;

    private ImageIcon ok;
    private ImageIcon nok;
    private ImageIcon logo;
    private ImageIcon closeIdle;
    private ImageIcon closeHover;
    private ImageIcon closePerformed;

    private static final int WIDTH = 400;
    private static final int HEIGHT = 200;

    public MainWindow() {
        this.buildImages();
        this.buildFrame();
        service = GeradorService.getInstance();
        inputCpf.getDocument().addDocumentListener(inputListener());
        closeButton.addMouseListener(closeButtonListener());
    }

    private void buildFrame() {
        this.setIconImage(logo.getImage());
        this.add(basePanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((dim.width - WIDTH ) / 2, (dim.height - HEIGHT) / 2, 400, 200);
        this.setUndecorated(true);
        this.setVisible(true);
        this.setResizable(false);
    }

    private SimpleDocumentListener inputListener() {
        return e -> {
            Cpf pojo = service.calcularDV(inputCpf.getText());
            validImage.setIcon(pojo.isValid()? ok : nok);
            validImage.setVisible(true);
            dvField.setText(pojo.getDv());
            rawField.setText(pojo.getRaw());
            formattedField.setText(pojo.getFormatted());
        };
    }

    private MouseListener closeButtonListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Thread(() -> {
                    closeButton.setIcon(closePerformed);
                    closeButton.removeMouseListener(closeButton.getMouseListeners()[0]);
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException ex){}
                    System.exit(0);
                }).start();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                closeButton.setIcon(closeIdle);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                closeButton.setIcon(closeHover);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setIcon(closeHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setIcon(closeIdle);
            }
        };
    }

    private void buildImages() {
        this.ok = new ImageIcon(MainWindow.class.getClassLoader().getResource("images/ok.gif"));
        this.nok = new ImageIcon(MainWindow.class.getClassLoader().getResource("images/nok.png"));
        this.logo = new ImageIcon(MainWindow.class.getClassLoader().getResource("images/logo.png"));
        this.closeIdle = new ImageIcon(MainWindow.class.getClassLoader().getResource("images/close.png"));
        this.closeHover = new ImageIcon(MainWindow.class.getClassLoader().getResource("images/close-hover.png"));
        this.closePerformed = new ImageIcon(MainWindow.class.getClassLoader().getResource("images/logomini.png"));
    }

    private void createUIComponents() {
        basePanel = new MotionPanel(this);
    }
}
