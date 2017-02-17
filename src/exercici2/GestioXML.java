/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercici2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Eric
 */
public abstract class GestioXML<T> {

    protected File file;

    public GestioXML(String ruta) {
        this.file = new File(ruta);
    }
    /**
     * Metode que carrega el fitxer DOM
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException 
     */
    public Document carregarFitxerDom() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        return doc;
    }
    /**
     * Metode que guarda el fitxer DOM
     * @param dom
     * @throws TransformerException 
     */
    public void GuardarEstatDom(Document dom) throws TransformerException{
        Transformer trans = TransformerFactory.newInstance().newTransformer();
        StreamResult result = new StreamResult(file);
        DOMSource doms = new DOMSource(dom);
        trans.transform(doms, result);
    }

    
    public abstract void AfegirGenericDom(T t);

    public abstract T obtenirObjecteCodiDom(int codi);

    public abstract ArrayList<T> obtenirLlistaObjectesDom();

    public abstract void modificarObjecteDom(T objeto);

    public abstract void eliminarObjecteDom(T objeto);

}
