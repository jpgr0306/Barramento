import java.net.*;
import java.io.*;

public class Conexao{

	public Boolean Cliente(String msg) throws Exception{
		try{
			Socket client = new Socket("localhost", 4999);
			PrintWriter pw = new PrintWriter(client.getOutputStream());
			pw.println(msg);
			pw.flush();
			System.out.println("Conexão com o servidor aceita");
			client.close();
		}
		catch(Exception e){
			System.out.print("\nErro no cliente detectado\n\n");
			throw e;
		}
		return true;
	}
	
	public String Servidor() throws Exception{
		String msgRec = "";
		try{
			ServerSocket server = new ServerSocket(4999);
			Socket client = server.accept();
			System.out.println("Cliente conectado");
			InputStreamReader in = new InputStreamReader(client.getInputStream());
			BufferedReader br = new BufferedReader(in);
			msgRec = br.readLine();
			System.out.println(msgRec);
			server.close();
			client.close();
		}
		catch(Exception e){
			System.out.print("\nErro no servidor detectado\n\n");
			throw e;
		}
		return msgRec;
	}

	public static String Ligar(){
		Conexao aux = new Conexao();
		String msg = "";
		try {
			msg = aux.Servidor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}


    public static void Enviar(String msg) {
		try{
			Conexao aux = new Conexao();
			aux.Cliente(msg);
		}
		catch(Exception e){
			e.printStackTrace();
		}
    }



	// public static void main(String[] args){
	// 	if(args.length < 1){
	// 		System.out.println("Utilize \"cliente\" ou \"servidor\" como argumento");
	// 		System.exit(1);
	// 	}
	// 	else if(args.length > 1){
	// 		System.out.println("Excesso de argumentos");
	// 		System.exit(1);
	// 	}
	// 	try{
	// 		Conexao aux = new Conexao();
	// 		if(args[0].equals("servidor"))
	// 			aux.Servidor();
	// 		else if(args[0].equals("cliente"))
	// 			aux.Cliente();
	// 		else
	// 			System.out.println("Utilize apenas \"cliente\" ou \"servidor\" como argumento");
	// 	}
	// 	catch(Exception e){
	// 		e.printStackTrace();
	// 	}
	// }
}