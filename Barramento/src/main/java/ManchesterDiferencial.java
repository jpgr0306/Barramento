import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManchesterDiferencial extends JFrame {
    private JPanel panel;
    private JTextField textField;
    private JButton generateButton;
    private String sequence = "";

    public ManchesterDiferencial(String binary) {
        setTitle("Manchester Diferencial");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int width = getWidth();
                int height = getHeight();

                // Desenha o fundo branco
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, width, height);

                // Define a posi��o inicial do gr�fico
                int graphStartX = 100;
                int graphStartY = height / 2 - 150;
                int graphWidth = width - 200;
                int graphHeight = 300;

                // Desenha os eixos X e Y
                g.setColor(Color.BLACK);
                g.drawLine(graphStartX, graphStartY + graphHeight / 2, graphStartX + graphWidth, graphStartY + graphHeight / 2); // Eixo X
                g.drawLine(graphStartX, graphStartY, graphStartX, graphStartY + graphHeight); // Eixo Y

                // Desenha o valor 0 no meio do eixo Y
                FontMetrics fontMetrics = g.getFontMetrics();
                String labelZero = "0";
                int labelZeroWidth = fontMetrics.stringWidth(labelZero);
                int labelZeroXPos = graphStartX - labelZeroWidth - 10;
                int labelZeroYPos = graphStartY + graphHeight / 2 + fontMetrics.getAscent() / 2;
                g.drawString(labelZero, labelZeroXPos, labelZeroYPos);

                // Define o espa�o entre os valores do eixo X
                int xSpacing = 1 + graphWidth / (binary.length());

                // Define a posi��o inicial do eixo X
                int xPos = graphStartX + xSpacing / 2;
                int yPos = graphStartY + graphHeight / 2 + 20;

                // Percorre a sequ�ncia de caracteres
               // for (int i = 0; i < binary.length()/8; i++) {
                    /*// Obt�m o caractere atual
                    char c = sequence.charAt(i);

                    // Converte o caractere em uma sequ�ncia de bits
                    String binary = Integer.toBinaryString(c);

                    // Completa com zeros � esquerda, se necess�rio
                    binary = String.format("%8s", binary).replace(' ', '0');*/

                    // Percorre os bits da sequ�ncia*/
                    for (int j = 0; j < binary.length(); j++) {
                        // Obt�m o bit atual
                        char bit = binary.charAt(j);

                        // Desenha a linha vertical no meio do bit
                        g.setColor(Color.DARK_GRAY);
                        int xMidPos = xPos + xSpacing / 2;
                        g.drawLine(xMidPos, graphStartY, xMidPos, graphStartY + graphHeight);

                        // Desenha o valor no eixo X
                        g.setColor(Color.BLACK);
                        g.drawString(String.valueOf(bit), xPos, yPos);

                        // Atualiza a posi��o atual
                        xPos += xSpacing;
                    }
                //}

                // Desenha os r�tulos do eixo Y
                String labelTop = "1";
                String labelBottom = "-1";
                int labelTopWidth = fontMetrics.stringWidth(labelTop);
                int labelBottomWidth = fontMetrics.stringWidth(labelBottom);
                int labelXPos = graphStartX - labelTopWidth - 10;
                int labelTopYPos = graphStartY + fontMetrics.getAscent();
                int labelBottomYPos = graphStartY + graphHeight + fontMetrics.getAscent();
                g.drawString(labelTop, labelXPos, labelTopYPos);
                g.drawString(labelBottom, labelXPos, labelBottomYPos);

                // Define a posi��o inicial do sinal
                xPos = graphStartX;
                yPos = graphStartY + graphHeight / 2;
                yPos = Math.max(yPos - graphHeight / 2, graphStartY + 1);

                // Define o estado inicial do sinal
                boolean isHigh = true;

                // Percorre a sequ�ncia de caracteres
                /*for (int i = 0; i < sequence.length(); i++) {
                    // Obt�m o caractere atual
                    char c = sequence.charAt(i);

                    // Converte o caractere em uma sequ�ncia de bits
                    String binary = Integer.toBinaryString(c);

                    // Completa com zeros � esquerda, se necess�rio
                    binary = String.format("%8s", binary).replace(' ', '0');*/

                    // Percorre os bits da sequ�ncia
                    for (int j = 0; j < binary.length(); j++) {
                        // Obt�m o bit atual
                        char bit = binary.charAt(j);

                        // Inverte o estado do sinal se necess�rio
                        if (bit == '0') {
                        for(int k = 0; k<2; k++) {
                            isHigh = !isHigh;
                        
                        
                        

                        // Define o pr�ximo ponto de refer�ncia
                        int nextXPos = xPos + xSpacing/2;
                        int nextYPos;

                        // Verifica o estado do sinal
                        if (isHigh) {
                            nextYPos = (graphStartY + 1);
                        } else {
                            nextYPos = (graphStartY + graphHeight - 1);
                        }
                        // Desenha a transi��o
                        g.setColor(Color.RED);
                        g.drawLine(xPos, yPos, nextXPos, yPos);
                        if(k == 1 && j < binary.length() - 1 && binary.charAt(j + 1) == '0') {
                        System.out.print(isHigh);
                        g.drawLine(nextXPos, yPos, nextXPos, nextYPos);}
                        else if(k == 0) {
                        g.drawLine(nextXPos, yPos, nextXPos, nextYPos);
                        }

                        // Atualiza a posi��o atual
                        xPos = nextXPos;
                        if(k == 1 && j < binary.length() - 1 && binary.charAt(j + 1) == '0')
                        yPos = nextYPos;
                        else if(k == 0)
                        yPos = nextYPos;
                        }}

                        else if (bit == '1') {
                        	int nextXPos = xPos + xSpacing/2;
                            int nextYPos;
                            if(j == 0)
                            isHigh = !isHigh;
                            
                            
                            g.setColor(Color.RED);
                            if (isHigh) {
                                nextYPos = (graphStartY + 1);
                            } else {
                                nextYPos = (graphStartY + graphHeight - 1);
                            }
                            
                            
                            
                            g.drawLine(xPos, yPos, nextXPos, yPos);
                            g.drawLine(nextXPos, yPos, nextXPos, nextYPos);
                            xPos = nextXPos;
                            yPos = nextYPos;
                            
                            g.setColor(Color.RED);
                            //g.drawLine(xPos, yPos, nextXPos, yPos);
                            //g.drawLine(nextXPos, yPos, nextXPos, nextYPos);
                            
                            // Define o pr�ximo ponto de refer�ncia
                            nextXPos = xPos + xSpacing/2;

                            // Verifica o estado do sinal
                            isHigh = !isHigh;
                            
                            if (isHigh) {
                                nextYPos = (graphStartY + 1);
                            } else {
                                nextYPos = (graphStartY + graphHeight - 1);
                            }

                            // Desenha a transi��o
                            g.setColor(Color.RED);
                            g.drawLine(xPos, yPos, nextXPos, yPos);
                            
                            
                            if(j < binary.length() - 1 && binary.charAt(j + 1) == '0') 
                            
                            g.drawLine(nextXPos, yPos, nextXPos, nextYPos);

                            // Atualiza a posi��o atual
                            xPos = nextXPos;
                            if(j < binary.length() - 1 && binary.charAt(j + 1) == '0') 
                            yPos = nextYPos;
                        }
                    }
                //}
            }
        };

        //textField = new JTextField(20);
        /**generateButton = new JButton("Gerar Gr�fico");

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sequence = textField.getText();
                panel.repaint();
            }
        });*/

        JPanel inputPanel = new JPanel();
        //inputPanel.add(textField);
        //inputPanel.add(generateButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(panel, BorderLayout.CENTER);
    }


}