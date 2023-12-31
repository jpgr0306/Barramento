import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import com.mycompany.mavenproject1.Cript;

public class JanelaServ extends JFrame {
    private static final long serialVersionUID = 1L;

    public static void main(String[] args)
    {
       JanelaServ field = new JanelaServ();
       field.CriaJanela();
    }

    private void CriaJanela() {
        Container janela = getContentPane();
        setLayout(null);

        //Define os rótulos dos botões
        JLabel labelEscr = new JLabel("Mensagem Escrita: ");
        JLabel labelCrip = new JLabel("Mensagem Criptografada: ");
        JLabel labelBin = new JLabel("Mensagem em Binário: ");
        JLabel labelAlg = new JLabel("Mensagem Algoritmo: ");
        labelAlg.setBounds(50,40,150,20);
        labelBin.setBounds(50,80,150,20);
        labelCrip.setBounds(50,120,150,20);
        labelEscr.setBounds(50,160,150,20);

        //Textos a inserir
        JTextField jTextEscr = new JTextField();
        JTextField jTextCrip = new JTextField();
        JTextField jTextBin = new JTextField();
        JTextField jTextAlg = new JTextField();
        jTextAlg.setBounds(200,40,100,20);
        jTextBin.setBounds(200,80,100,20);
        jTextCrip.setBounds(200,120,100,20);
        jTextEscr.setBounds(200,160,100,20);
        jTextEscr.setEditable(false);
        jTextCrip.setEditable(false);
        jTextBin.setEditable(false);
        jTextAlg.setEditable(false);

        // Botoes
        JButton buttonLigar = new JButton("Ligar");
        JButton buttonLimpar = new JButton("Limpar");
        buttonLigar.setBounds(200, 200, 100, 30);
        buttonLimpar.setBounds(50, 200, 100, 30);
    
        //Adiciona os rótulos e os campos de textos com máscaras na tela
        janela.add(labelEscr);
        janela.add(labelCrip);
        janela.add(labelBin);
        janela.add(labelAlg);
        janela.add(jTextEscr);
        janela.add(jTextCrip);
        janela.add(jTextBin);
        janela.add(jTextAlg);
        janela.add(buttonLigar);
        janela.add(buttonLimpar);

        buttonLigar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String msgAlg = Conexao.Ligar();
                jTextAlg.setText(msgAlg);

                String msgBin = decodeManchester(msgAlg);
                jTextBin.setText(msgBin);

                String msgCrip = convertToString(msgBin);
                jTextCrip.setText(msgCrip);

                String msgEscr = Cript.descriptografar(msgCrip);
                jTextEscr.setText(msgEscr);

                String mensagemBinaria = jTextBin.getText();
                ManchesterDiferencial Grafico = new ManchesterDiferencial(mensagemBinaria);
                Grafico.setVisible(true);                
            }
        });

        buttonLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                jTextEscr.setText("");
                jTextCrip.setText("");
                jTextBin.setText("");
                jTextAlg.setText("");
            }
        });

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static String convertToString(String binaryInput) {
        //     String stringaux = "";
        //     char caracterAtual =   
        //     for (int i =0; i<binaryInput.length(); i+=8){
        //         stringaux.charAt(i%8)
            
        //     for (String binaryValue : binaryArray) {
        //         int decimalValue = Integer.parseInt(binaryValue, 2);
        //         char c = (char) decimalValue;
        //         stringBuilder.append(c);
        //     }
        // }
        // public static String convertToString(String string) {
        //     StringBuilder caracteresFiltrados = new StringBuilder();
        //     for (int i = 0; i < string.length(); i++) {
        //         char caractere = string.charAt(i);
        //         if (caractere == '0' || caractere == '1') {
        //             caracteresFiltrados.append(caractere);
        //         }
        //     }
        //     caracteresFiltrados.length();
        // return caracteresFiltrados.toString();
        // }
        int it = 0;
        StringBuilder asciiString = new StringBuilder();

        while (it * 8 < binaryInput.length()) {
            int startIndex = it * 8;
            int endIndex = startIndex + 8;
            if (endIndex <= binaryInput.length()) {
                String parte = binaryInput.substring(startIndex, endIndex);
                int asciiValue = Integer.parseInt(parte, 2);
                char asciiChar = (char) asciiValue;
                asciiString.append(asciiChar);
            }
            it++;
        }

        String resultado = asciiString.toString();
        System.out.println("Resultado: " + resultado);
        return resultado;
    }

    public static String encodeManchester(String input) {
        char[] encoded = new char[input.length() * 2];
        char previousBit = '1';
        for (int i = 0; i < input.length(); i++) {
            char currentBit = input.charAt(i);
            if (currentBit == '0') {
                encoded[i * 2] = 'H';
                encoded[i * 2 + 1] = 'L';
            } 
            else{
                encoded[i * 2] = 'L';
                encoded[i * 2 + 1] = 'H';
            }
        }
        return new String(encoded);
    }

	public static String decodeManchester(String input) {
    	StringBuilder output = new StringBuilder();
    	char previousBit = '1';
    	for (int i = 1; i < input.length(); i += 2) {
    	    char firstTransition = input.charAt(i-1);
    	    char secondTransition = input.charAt(i);
	        if (firstTransition == secondTransition) {
	            output.append(previousBit);
	        } else if (firstTransition == 'H' && secondTransition == 'L') {
	            output.append('0');
	            previousBit = '0';
	        } else {
	            output.append('1');
	            previousBit = '1';
	        }
	    }
	    char firstTransition = input.charAt(input.length()-2);
	    char secondTransition = input.charAt(input.length()-1);
	    if (firstTransition == secondTransition) {
	            output.append(previousBit);
	        } else if (firstTransition == 'H' && secondTransition == 'L') {
	            output.append('0');
    	        previousBit = '0';
    	    } else {
    	        output.append('1');
        	    previousBit = '1';
    	}
    	return output.toString();
	}    
}
