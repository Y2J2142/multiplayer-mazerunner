import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;

public class Config
{
    String ip;
    int port;
    int rows;
    int cols;
    int blockSize;

    Config(String configFile)
    {
        try
        {
            this.ip = new String();
            File xmlFile = new File(configFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("config");

            Node node = nodeList.item(0);
            if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                this.ip = element.getElementsByTagName("ip").item(0).getTextContent();
                try{
                    this.port = Integer.parseInt(element.getElementsByTagName("port").item(0).getTextContent());
                    this.rows = Integer.parseInt(element.getElementsByTagName("rows").item(0).getTextContent());
                    this.cols = Integer.parseInt(element.getElementsByTagName("rows").item(0).getTextContent());
                    this.blockSize = Integer.parseInt(element.getElementsByTagName("blocksize").item(0).getTextContent());
                
                
                }catch(NumberFormatException e){e.printStackTrace();}
            }

        }catch(ParserConfigurationException | SAXException | IOException e)
        {
            e.printStackTrace();
        }
    }

    public String geIP()
    {
        return this.ip;
    }

    public int getPort()
    {
        return this.port;
    }
    public int getRows()
    {
        return this.rows;
    }
    public int getCols()
    {
        return this.cols;
    }
    public int getBlockSize()
    {
        return this.blockSize;
    }
    public static void main(String[] args)
    {
        Config config = new Config("config.xml");
    }
}