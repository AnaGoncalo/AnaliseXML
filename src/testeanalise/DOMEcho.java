/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testeanalise;

import static com.sun.org.apache.xerces.internal.impl.xs.opti.SchemaDOM.indent;
import java.io.File;
//Estas são as APIs JAXP usadas pelo DOMEcho :
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
//Essas classes são para as exceções que podem ser lançadas quando o documento XML é analisado:
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.*;
//Essas classes lêem o arquivo XML de exemplo e gerenciam a saída:
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import static java.lang.System.out;
//Finalmente, importe as definições do W3C para um DOM, excepções de DOM, entidades e nós:
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Ana Gonçalo
 */
public class DOMEcho {

    static final String outputEncoding = "UTF-8";
    private PrintWriter out;
    private int indent = 0;
    private final String basicIndent = " ";

    DOMEcho(PrintWriter out)
    {
        this.out = out;
    }
    public static void usage() {

    }

//    public static void main(String[] args) throws Exception {
//
//        String filename = null;
//        boolean dtdValidate = false;
//        boolean xsdValidate = false;
//        String schemaSource = null;
//
//        for (int i = 0; i < args.length; i++) {
//            if (args[i].equals("-dtd")) {
//                dtdValidate = true;
//            } else if (args[i].equals("-xsd")) {
//                xsdValidate = true;
//            } else if (args[i].equals("-xsdss")) {
//                if (i == args.length - 1) {
//                    usage();
//                }
//                xsdValidate = true;
//                schemaSource = args[++i];
//            } else {
//                filename = args[i];
//                if (i != args.length - 1) {
//                    usage();
//                }
//            }
//        }
//
//        if (filename == null) {
//            usage();
//        }
//
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        dbf.setNamespaceAware(true);
//        dbf.setValidating(dtdValidate || xsdValidate);
//
//        DocumentBuilder db = dbf.newDocumentBuilder();
//        OutputStreamWriter errorWriter = new OutputStreamWriter(System.err, outputEncoding);
//        db.setErrorHandler(new MyErrorHandler(new PrintWriter(errorWriter, true)));
//        Document doc = db.parse(new File("src/testeanalise/alunos.xml"));
//        
//        
//    }


    private void printlnCommon(Node n) {
        out.print(" nodeName=\"" + n.getNodeName() + "\"");

        String val = n.getNamespaceURI();
        if (val != null) {
            out.print(" uri=\"" + val + "\"");
        }

        val = n.getPrefix();

        if (val != null) {
            out.print(" pre=\"" + val + "\"");
        }

        val = n.getLocalName();
        if (val != null) {
            out.print(" local=\"" + val + "\"");
        }

        val = n.getNodeValue();
        if (val != null) {
            out.print(" nodeValue=");
            if (val.trim().equals("")) {
                // Whitespace
                out.print("[WS]");
            } else {
                out.print("\"" + n.getNodeValue() + "\"");
            }
        }
        out.println();
    }

    private void outputIndentation() {
        for (int i = 0; i < indent; i++) {
            out.print(basicIndent);
        }
    }

    private void echo(Node n) {
        outputIndentation();
        int type = n.getNodeType();

        switch (type) {
            case Node.ATTRIBUTE_NODE:
                out.print("ATTR:");
                printlnCommon(n);
                break;

            case Node.CDATA_SECTION_NODE:
                out.print("CDATA:");
                printlnCommon(n);
                break;

            case Node.COMMENT_NODE:
                out.print("COMM:");
                printlnCommon(n);
                break;

            case Node.DOCUMENT_FRAGMENT_NODE:
                out.print("DOC_FRAG:");
                printlnCommon(n);
                break;

            case Node.DOCUMENT_NODE:
                out.print("DOC:");
                printlnCommon(n);
                break;

            case Node.DOCUMENT_TYPE_NODE:
                out.print("DOC_TYPE:");
                printlnCommon(n);
                NamedNodeMap nodeMap = ((DocumentType) n).getEntities();
                indent += 2;
                for (int i = 0; i < nodeMap.getLength(); i++) {
                    Entity entity = (Entity) nodeMap.item(i);
                    echo(entity);
                }
                indent -= 2;
                break;

            case Node.ELEMENT_NODE:
                out.print("ELEM:");
                printlnCommon(n);

                NamedNodeMap atts = n.getAttributes();
                indent += 2;
                for (int i = 0; i < atts.getLength(); i++) {
                    Node att = atts.item(i);
                    echo(att);
                }
                indent -= 2;
                break;

            case Node.ENTITY_NODE:
                out.print("ENT:");
                printlnCommon(n);
                break;

            case Node.ENTITY_REFERENCE_NODE:
                out.print("ENT_REF:");
                printlnCommon(n);
                break;

            case Node.NOTATION_NODE:
                out.print("NOTATION:");
                printlnCommon(n);
                break;

            case Node.PROCESSING_INSTRUCTION_NODE:
                out.print("PROC_INST:");
                printlnCommon(n);
                break;

            case Node.TEXT_NODE:
                out.print("TEXT:");
                printlnCommon(n);
                break;

            default:
                out.print("UNSUPPORTED NODE: " + type);
                printlnCommon(n);
                break;
        }

        indent++;
        for (Node child = n.getFirstChild(); child != null;
                child = child.getNextSibling()) {
            echo(child);
        }
        indent--;
    }

    public Node findSubNode(String name, Node node) {
        if (node.getNodeType() != Node.ELEMENT_NODE) {
            System.err.println("Error: Search node not of element type");
            System.exit(22);
        }

        if (!node.hasChildNodes()) {
            return null;
        }

        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node subnode = list.item(i);
            if (subnode.getNodeType() == Node.ELEMENT_NODE) {
                if (subnode.getNodeName().equals(name)) {
                    return subnode;
                }
            }
        }
        return null;
    }

    public String getText(Node node) {
        StringBuffer result = new StringBuffer();
        if (!node.hasChildNodes()) {
            return "";
        }

        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node subnode = list.item(i);
            if (subnode.getNodeType() == Node.TEXT_NODE) {
                result.append(subnode.getNodeValue());
            } else if (subnode.getNodeType() == Node.CDATA_SECTION_NODE) {
                result.append(subnode.getNodeValue());
            } else if (subnode.getNodeType() == Node.ENTITY_REFERENCE_NODE) {
                // Recurse into the subtree for text
                // (and ignore comments)
                result.append(getText(subnode));
            }
        }

        return result.toString();
    }
    /*
    
    public void LerArquivo(String caminho) {
        File arquivo = new File(caminho);
    }

    public static void XML() {
        try {
            File fXmlFile = new File("alunos.xhtml");

            OutputStreamWriter errorWriter = new OutputStreamWriter(System.err, outputEncoding);
            db.setErrorHandler(new MyErrorHandler(new PrintWriter(errorWriter, true)));
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("staff");
            System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Aluno eElement = (Aluno) nNode;
                    System.out.println("Staff id : " + eElement.getMatricula());
                }
            }
        } catch (Exception e) {
        }
    }
    */
}
