/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Persistencia.DAAluno;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import testeanalise.Aluno;

public class ReadXMLFile {

    public static void main(String argv[]) {

        try {

            File fXmlFile = new File("alunos.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            System.out.println("Raiz :" + doc.getDocumentElement().getNodeName());

            NodeList alunos = doc.getElementsByTagName("aluno"); //lista de alunos

            System.out.println("----------------------------");

            for (int temp = 0; temp < alunos.getLength(); temp++) {
                Node aluno = alunos.item(temp);
                //System.out.println("Aluno completo: " + aluno.getTextContent());

                Element campo = (Element) aluno;
                //System.out.println("qtd" + aluno.getChildNodes().getLength());
                String nome = campo.getElementsByTagName("nome").item(0).getTextContent();
                String matricula = campo.getElementsByTagName("matricula").item(0).getTextContent();
                System.out.println("Nome: " + nome);
                System.out.println("Matricula: " + matricula);
                Aluno a = new Aluno();
                a.setNome(nome);
                a.setMatricula(matricula);
                
                //DAAluno.CadastrarAluno(a);

                //System.out.println("\nElement:" + .getNodeName() + " - " + nNode.getTextContent());
                /*
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) nNode;

			System.out.println("Staff id : " + eElement.getAttribute("id"));
			System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
			System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
			System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
			System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());

		}
                 */
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
