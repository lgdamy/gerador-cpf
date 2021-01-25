package com.damytec.view;

import com.damytec.pojo.Cpf;
import com.damytec.service.GeradorService;
import com.damytec.service.GithubService;
import com.damytec.ui.CustomButton;
import com.damytec.ui.MotionPanel;
import com.damytec.util.SimpleDocumentListener;

import javax.swing.*;
import java.awt.*;

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
    private JLabel logoButton;
    private GeradorService service;

    private ImageIcon ok;
    private ImageIcon nok;
    private ImageIcon logo;

    private static final int WIDTH = 400;
    private static final int HEIGHT = 200;

    public MainWindow() {
        this.buildImages();
        this.buildFrame();
        service = GeradorService.getInstance();
        inputCpf.getDocument().addDocumentListener(inputListener());
    }

    private void buildFrame() {
        this.setIconImage(logo.getImage());
        this.add(basePanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((dim.width - WIDTH) / 2, (dim.height - HEIGHT) / 2, 400, 200);
        this.setUndecorated(true);
        this.setVisible(true);
        this.setResizable(false);
    }

    private SimpleDocumentListener inputListener() {
        return e -> {
            Cpf pojo = service.calcularDV(inputCpf.getText());
            if (!pojo.isValid()) {
                Toolkit.getDefaultToolkit().beep();
            }
            validImage.setIcon(pojo.isValid() ? ok : nok);
            validImage.setVisible(true);
            dvField.setText(pojo.getDv());
            rawField.setText(pojo.getRaw());
            formattedField.setText(pojo.getFormatted());
        };
    }

    private void buildImages() {
        this.ok = new ImageIcon(MainWindow.class.getClassLoader().getResource("images/ok.gif"));
        this.nok = new ImageIcon(MainWindow.class.getClassLoader().getResource("images/nok.png"));
        this.logo = new ImageIcon(MainWindow.class.getClassLoader().getResource("images/logo.png"));
    }

    private void createUIComponents() {
        basePanel = new MotionPanel(this);
        closeButton = new CustomButton("images/close.png") {
            @Override
            public void actionPerformed() {
                System.exit(0);
            }
        };
        logoButton = new CustomButton("images/logomini.png") {
            @Override
            public void actionPerformed() {
                GithubService.getInstance().openGithub();
            }
        };
    }

}
