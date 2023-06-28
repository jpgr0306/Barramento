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

public class Janela extends JFrame {

       private static final long serialVersionUID = 1L;

    public static void main(String[] args)
    {
       Janela field = new Janela();
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
        labelEscr.setBounds(50,40,150,20);
        labelCrip.setBounds(50,80,150,20);
        labelBin.setBounds(50,120,150,20);
        labelAlg.setBounds(50,160,150,20);

        //Seta as máscaras nos objetos JFormattedTextField
        JTextField jTextEscr = new JTextField();
        JTextField jTextCrip = new JTextField();
        JTextField jTextBin = new JTextField();
        JTextField jTextAlg = new JTextField();
        jTextEscr.setBounds(200,40,100,20);
        jTextCrip.setBounds(200,80,100,20);
        jTextBin.setBounds(200,120,100,20);
        jTextAlg.setBounds(200,160,100,20);


        // Botoes
        JButton buttonEnviar = new JButton("Enviar");
        JButton buttonConfig = new JButton("Configurar");
        JButton buttonConect = new JButton("Conectar");
        buttonEnviar.setBounds(200, 200, 100, 30);
        buttonConfig.setBounds(50, 200, 100, 30);
        buttonConect.setBounds(50, 240, 100, 30);
    
        //Adiciona os rótulos e os campos de textos com máscaras na tela
        janela.add(labelEscr);
        janela.add(labelCrip);
        janela.add(labelBin);
        janela.add(labelAlg);
        janela.add(jTextEscr);
        janela.add(jTextCrip);
        janela.add(jTextBin);
        janela.add(jTextAlg);
        janela.add(buttonEnviar);
        janela.add(buttonConfig);

        
        // Ouvinte de eventos para o botão "Configurar"
        buttonConfig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String mensagemEscr = jTextEscr.getText();

                // Lógica para criptografar a mensagem em binário
                String mensagemCriptografada = Cript.criptografar(mensagemEscr);
                jTextCrip.setText(mensagemCriptografada);

                // Lógica para converter a mensagem em binário
                String mensagemBinaria = convertToBinary(mensagemCriptografada);
                jTextBin.setText(mensagemBinaria);

                //Lógica para converter mensagem pelo Algoritmo Manchester
                String mensagemAlg = encodeManchester(mensagemBinaria);
                jTextAlg.setText(mensagemAlg);
            }
        });


        buttonEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String mensagemAlg = jTextBin.getText(); //mudar prara jTextAlg
                Conexao.Enviar(mensagemAlg);
                
                String mensagemBinaria = jTextBin.getText();
                ManchesterDiferencial Grafico = new ManchesterDiferencial(mensagemBinaria);
                Grafico.setVisible(true);
            }
        }); 
        
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static String convertToBinary(String input) {
        StringBuilder binaryString = new StringBuilder();
        
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            int decimalValue = (int) c;
            StringBuilder binaryValue = new StringBuilder();
            
            while (decimalValue > 0) {
                int remainder = decimalValue % 2;
                binaryValue.insert(0, remainder);
                decimalValue /= 2;
            }
            while (binaryValue.length() < 8) {
                binaryValue.insert(0,"0");
            }
            binaryString.append(binaryValue);
        }
        
        return binaryString.toString().trim();
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

}