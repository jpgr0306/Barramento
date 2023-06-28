import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ManchesterDiferencial extends JFrame {
    private JPanel panel;
    private JTextField textField;
    private JButton generateButton;
    private String sequence = "";
    private int sig;
    private int sigBACK;
    private int it;

    public ManchesterDiferencial(String binary) {
    	it = 0;
        setTitle("Manchester Diferencial");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //textField = new JTextField(20);
        /**generateButton = new JButton("Gerar Gráfico");

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sequence = textField.getText();
                panel.repaint();
            }
        });*/
        sig = 1;
    	criarPanel(binary.substring(0, 8), 0, sig);


 
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    // Remova o painel do JFrame
                    //getContentPane().remove(panel);
                    //revalidate();
                    //repaint();
                    it++;
                    int startIndex = it * 8;
                    int endIndex = startIndex + 8;
                    String parte = "";

                    if (endIndex < binary.length()) {
                        parte = binary.substring(startIndex, endIndex);
                        System.out.println("Substring: " + sig);
                        criarPanel(parte, 1, sig);
                    } else {
                        System.out.println("Não há caracteres suficientes na string.");
                        it--;
                    }
                    
                }
                /*if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    // Remova o painel do JFrame
                    //getContentPane().remove(panel);
                    //revalidate();
                    //repaint();
                    it--;
                    int startIndex = it * 8;
                    int endIndex = startIndex + 8;
                    String parte = "";

                    if (startIndex >= 0) {
                        parte = binary.substring(startIndex, endIndex);
                        System.out.println("Substring: " + parte);
                        criarPanel(parte, 1, sigBACK);
                    } else {
                        System.out.println("Não há caracteres suficientes na string.");
                        it++;
                    }
                    
                }*/
            }
        });
                

        }

    
    
    public void criarPanel(String binary, int cond, int signal)
    {
    	sigBACK = 1;
    	if(panel!=null) {

    	panel.removeAll();
    	panel.revalidate();
    	panel.repaint();
        getContentPane().remove(panel);
    	}
    	panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
            	boolean isHigh;
                super.paintComponent(g);

                int width = getWidth();
                int height = getHeight();

                // Desenha o fundo branco
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, width, height);

                // Define a posição inicial do gráfico
                int graphStartX = 100;
                int graphStartY = height / 2 - 150;
                int graphWidth = width - 200;
                int graphHeight = 300;

                // Desenha os eixos X e Y
                g.setColor(Color.BLACK);
                g.drawLine(graphStartX, graphStartY + graphHeight / 2, graphStartX + graphWidth + 7, graphStartY + graphHeight / 2); // Eixo X
                g.drawLine(graphStartX, graphStartY, graphStartX, graphStartY + graphHeight); // Eixo Y

                int fontSize = 20;
                Font font = new Font("Arial", Font.PLAIN, fontSize);
                // Desenha o valor 0 no meio do eixo Y
                FontMetrics fontMetrics = g.getFontMetrics();
                
                g.setFont(font);
                g.drawString(binary, 950, 200);
                String labelZero = "0";
                int labelZeroWidth = fontMetrics.stringWidth(labelZero);
                int labelZeroXPos = graphStartX - labelZeroWidth - 10;
                int labelZeroYPos = graphStartY + graphHeight / 2 + fontMetrics.getAscent() / 2;
                g.drawString(labelZero, labelZeroXPos, labelZeroYPos);

                // Define o espaço entre os valores do eixo X
                int xSpacing = 1 + graphWidth / (binary.length());

                // Define a posição inicial do eixo X

                int xPos = graphStartX + xSpacing / 2;
                int yPos = graphStartY + graphHeight / 2 + 20;

                // Percorre a sequência de caracteres
               // for (int i = 0; i < binary.length()/8; i++) {
                    /*// Obtém o caractere atual
                    char c = sequence.charAt(i);

                    // Converte o caractere em uma sequência de bits
                    String binary = Integer.toBinaryString(c);

                    // Completa com zeros à esquerda, se necessário
                    binary = String.format("%8s", binary).replace(' ', '0');*/

                    // Percorre os bits da sequência*/
                    for (int j = 0; j < binary.length(); j++) {
                        // Obtém o bit atual
                        char bit = binary.charAt(j);

                        // Desenha a linha vertical no meio do bit
                        g.setColor(Color.DARK_GRAY);
                        int xMidPos = xPos + xSpacing / 2;
                        g.drawLine(xMidPos, graphStartY, xMidPos, graphStartY + graphHeight);

                        // Desenha o valor no eixo X
                        g.setColor(Color.BLACK);
                        g.drawString(String.valueOf(bit), xPos, yPos);

                        // Atualiza a posição atual
                        xPos += xSpacing;
                    }
                //}

                // Desenha os rótulos do eixo Y
                String labelTop = "1";
                String labelBottom = "-1";
                int labelTopWidth = fontMetrics.stringWidth(labelTop);
                int labelBottomWidth = fontMetrics.stringWidth(labelBottom);
                int labelXPos = graphStartX - labelTopWidth - 10;
                int labelTopYPos = graphStartY + fontMetrics.getAscent();
                int labelBottomYPos = graphStartY + graphHeight + fontMetrics.getAscent();
                g.drawString(labelTop, labelXPos, labelTopYPos);
                g.drawString(labelBottom, labelXPos, labelBottomYPos);

                // Define a posição inicial do sinal
                xPos = graphStartX;

                if(signal == 1) {
                if(binary.charAt(0) == '1') {
                yPos = graphStartY + graphHeight / 2;
                yPos = Math.max(yPos - graphHeight / 2, graphStartY + 1);
                isHigh = true;}
                else
                {                   
                yPos = graphStartY + graphHeight;
                isHigh = false;}
                }
                
                else
                {
                	if(binary.charAt(0) == '1') {
                    yPos = graphStartY + graphHeight;
                    isHigh = false;}
                    else
                    {
                    yPos = graphStartY + graphHeight / 2;
                    yPos = Math.max(yPos - graphHeight / 2, graphStartY + 1);
                    isHigh = true;
                    }
                }

                // Percorre a sequência de caracteres
                /*for (int i = 0; i < sequence.length(); i++) {
                    // Obtém o caractere atual
                    char c = sequence.charAt(i);

                    // Converte o caractere em uma sequência de bits
                    String binary = Integer.toBinaryString(c);

                    // Completa com zeros à esquerda, se necessário
                    binary = String.format("%8s", binary).replace(' ', '0');*/

                    // Percorre os bits da sequência
                    for (int j = 0; j < binary.length(); j++) {
                        // Obtém o bit atual
                        char bit = binary.charAt(j);

                        // Inverte o estado do sinal se necessário
                        if (bit == '0') {
                        for(int k = 0; k<2; k++) {

                        if(j == 0)
                        {
                            g.setColor(Color.RED);
                            g.drawLine(xPos, graphStartY + graphHeight*2 / 2, xPos, (graphStartY + 1));
                            sigBACK = 0;
                        }
                        
                        int nextXPos = xPos + xSpacing/2;
                        int nextYPos = yPos;
                            
                        isHigh = !isHigh;
                        /*if(j == 0)
                        {
   
                            if (isHigh) {
                                nextYPos = (graphStartY + 1);
                            } else {
                                nextYPos = graphStartY + graphHeight;
                            }
                            g.setColor(Color.RED);
                            g.drawLine(xPos, yPos, xPos, nextYPos);
                            yPos = nextYPos;
                         	//isHigh = !isHigh;
                        }*/
                        //isHigh = !isHigh;
                        

                        // Define o próximo ponto de referência

                        // Verifica o estado do sinal
                        if (isHigh) {
                            nextYPos = (graphStartY + 1);
                        } else {
                            nextYPos = (graphStartY + graphHeight - 1);
                        }
                        // Desenha a transição
                        g.setColor(Color.RED);
                        g.drawLine(xPos, yPos, nextXPos, yPos);
                        if(k == 1 && j < binary.length() - 1 && binary.charAt(j + 1) == '0') {
                        System.out.print(isHigh);
                        g.drawLine(nextXPos, yPos, nextXPos, nextYPos);}
                        else if(k == 0) {
                        g.drawLine(nextXPos, yPos, nextXPos, nextYPos);
                        }

                        // Atualiza a posição atual
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
                            
                            // Define o próximo ponto de referência
                            nextXPos = xPos + xSpacing/2;

                            // Verifica o estado do sinal
                            isHigh = !isHigh;
                            
                            if (isHigh) {
                                nextYPos = (graphStartY + 1);
                            } else {
                                nextYPos = (graphStartY + graphHeight - 1);
                            }

                            // Desenha a transição
                            g.setColor(Color.RED);
                            g.drawLine(xPos, yPos, nextXPos, yPos);
                            
                            
                            if(j < binary.length() - 1 && binary.charAt(j + 1) == '0') 
                            
                            g.drawLine(nextXPos, yPos, nextXPos, nextYPos);

                            // Atualiza a posição atual
                            xPos = nextXPos;
                            if(j < binary.length() - 1 && binary.charAt(j + 1) == '0') 
                            yPos = nextYPos;
                        }
}
                //}
                    if(!isHigh)
                    	sig = 1;
                    	else
                    	sig = 0;}
};
        
        
        JPanel inputPanel = new JPanel();
        
        //inputPanel.add(textField);
        //inputPanel.add(generateButton);
        
        if(cond == 0) {
        getContentPane().setLayout(new BorderLayout());
        //getContentPane().add(inputPanel, BorderLayout.NORTH);
        }
        getContentPane().add(panel, BorderLayout.CENTER);
        //getContentPane().add(texto);
        //getContentPane().add(texto, 800, 800);
        getContentPane().setVisible(true);

    	
    }


}
