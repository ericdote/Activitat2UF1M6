/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercici2;

import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 *
 * @author Eric
 */
public class GestioProducte extends GestioXML<Producte> {

    Document dom;

    public GestioProducte(String ruta) {
        super(ruta);
    }

    /**
     * Metode heredat utilitzat per afegir objectes al .xml Li arriba un
     * Producte generic per parametre
     *
     * @param t
     */
    @Override
    public void AfegirGenericDom(Producte t) {
        //Provem a carregar el fitxer
        try {
            dom = carregarFitxerDom();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(GestioProducte.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Creem una llista de nodes amb els nodes de Productes
        NodeList nl = dom.getElementsByTagName("Productes");
        //Fem un bucle for per recorrer tots els nodes
        for (int i = 0; i < nl.getLength(); i++) {
            //Creem un Node que es queda amb les etiquetes del primer node
            Node node = nl.item(i);
            //Si el node es del tipus ELEMENT_NODE entra
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                //Obtenim el primer element del node.
                Element ele = (Element) dom.getFirstChild();
                //Creem un node producte
                Element product = dom.createElement("producte");
                //El posem com a fill i li assignem el atribut codi
                ele.appendChild(product);
                product.setAttribute("codi", String.valueOf(t.getCodi()));
                //Amb els seguents elements es lo mateix que lo anterior pero adaptat als atributs en questio
                Element nom = dom.createElement("nom");
                Text nomEle = dom.createTextNode(t.getNom());
                nom.appendChild(nomEle);

                Element uni = dom.createElement("unitats");
                Text uniEle = dom.createTextNode(String.valueOf(t.getUnitats()));
                nom.appendChild(uniEle);

                Element preu = dom.createElement("preu");
                Text preuEle = dom.createTextNode(String.valueOf(t.getPreu()));
                preu.appendChild(preuEle);
                //Afegim el Element child que hem creat al Pare
                product.appendChild(nom);
                product.appendChild(uni);
                product.appendChild(preu);

                break;
            }
        }
        //Guardem l'estat del document DOM
        try {
            GuardarEstatDom(dom);
        } catch (TransformerException e) {
        }
    }
    /**
     * Metode que obte a partir de un codi el Producte que te relacio amb aquest codi.
     * Si no existeix no mostra res.
     * @param codi
     * @return 
     */
    @Override
    public Producte obtenirObjecteCodiDom(int codi) {
        try {
            dom = carregarFitxerDom();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(GestioProducte.class.getName()).log(Level.SEVERE, null, ex);
        }

        Producte product = null;
        NodeList nodes = dom.getElementsByTagName("producte");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element ele = (Element) node;
                //Busca si el element es igual que el codi li passem.
                if (ele.getAttribute("codi").equals(String.valueOf(codi))) {
                    //Inicialitzem variables per recogir els valors.
                    String nom = "";
                    int unitats = 0;
                    double preu = 0;
                    //Creem una llista de nodes pels nodes fills.
                    NodeList nl = ele.getChildNodes();
                    for (int j = 0; j < nl.getLength(); j++) {
                        //Obtenim cada node fill
                        Node child = nl.item(j);
                        //Si el nom del node es igual a nom asignem nom a la variable
                        //(Repetim aixo per les altres variables)
                        if (child.getNodeName().equals("nom")) {
                            nom = child.getTextContent();
                        }
                        if (child.getNodeName().equals("unitat")) {
                            unitats = parseInt(child.getTextContent());
                        }
                        if (child.getNodeName().equals("preu")) {
                            preu = parseDouble(child.getTextContent());
                        }
                        //Creem un nou objecte producte
                        product = new Producte(codi, nom, unitats, preu);
                        break;
                    }
                }
            }
        }
        try {
            GuardarEstatDom(dom);
        } catch (TransformerException e) {
        }
        System.out.println(product);
        return product;
    }
    /**
     * Metode que s'utilitza per obtenir tota la llista de objectes que tenim al fitxer
     * Aquest metode es casi identic al d'amunt, treient que en comptes de crear un unic objecte creem diversos i l'afegim a un arrayList
     * @return 
     */
    @Override
    public ArrayList<Producte> obtenirLlistaObjectesDom() {
        ArrayList<Producte> list = new ArrayList();
        Producte product;
        try {
            dom = carregarFitxerDom();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(GestioProducte.class.getName()).log(Level.SEVERE, null, ex);
        }
        NodeList nodes = dom.getElementsByTagName("producte");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element ele = (Element) node;
                String nom = "";
                int unitats = 0, codi = 0;
                double preu = 0;
                NodeList nl = node.getChildNodes();
                for (int j = 0; j < nl.getLength(); j++) {
                    Node child = nl.item(j);
                    if (child.getNodeType() == Node.ELEMENT_NODE) {
                        if (child.getNodeName().equals("codi")) {
                            codi = parseInt(child.getTextContent());
                        }
                        if (child.getNodeName().equals("nom")) {
                            nom = child.getTextContent();
                        }
                        if (child.getNodeName().equals("unitat")) {
                            unitats = parseInt(child.getTextContent());
                        }
                        if (child.getNodeName().equals("preu")) {
                            preu = parseDouble(child.getTextContent());
                        }
                        product = new Producte(codi, nom, unitats, preu);
                        list.add(product);
                    }
                }
            }
            try {
                GuardarEstatDom(dom);
            } catch (TransformerException ex) {
                Logger.getLogger(GestioProducte.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return list;
    }
    /**
     * Metode que li arriba un objecte tipus Producte per parametre i busca l'objecte, un cop trobat el codi que coincideix
     * Modifica l'objecte del fitxer amb els valors del objecte que li entra per parametre
     * @param objeto 
     */
    @Override
    public void modificarObjecteDom(Producte objeto) {
        try {
            dom = carregarFitxerDom();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(GestioProducte.class.getName()).log(Level.SEVERE, null, ex);
        }
        NodeList nodes = dom.getElementsByTagName("producte");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element ele = (Element) node;
                //Aqui mirem si el codi del objecte es igual que el codi que te el node.
                if (ele.getAttribute("codi").equals(String.valueOf(objeto.getCodi()))) {
                    //Modifiquem l'atribut amb els valors del objecte que li arriba
                    ele.getElementsByTagName("nom").item(0).setTextContent(objeto.getNom());
                    ele.getElementsByTagName("unitats").item(0).setTextContent(String.valueOf(objeto.getUnitats()));
                    ele.getElementsByTagName("preu").item(0).setTextContent(String.valueOf(objeto.getPreu()));
                    System.out.println("Modificado");
                    break;
                }
            }
        }

        try {
            GuardarEstatDom(dom);
        } catch (TransformerException ex) {
            Logger.getLogger(GestioProducte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Metode que elimina un objecte del fitxer en cas de coincideixi el codi
     * @param objeto 
     */
    @Override
    public void eliminarObjecteDom(Producte objeto) {
        try {
            dom = carregarFitxerDom();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(GestioProducte.class.getName()).log(Level.SEVERE, null, ex);
        }
        NodeList nodes = dom.getElementsByTagName("producte");
        
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element ele = (Element) node;
                if (ele.getAttribute("codi").equals(String.valueOf(objeto.getCodi()))) {
                    //Fem tots els pasos anteriors i si existeix el objecte amb el codi corresponent eliminem l'objecte del fitxer
                    ele.getParentNode().removeChild(ele);
                    System.out.println("Eliminado");
                    break;
                }
            }
        }
        try {
            GuardarEstatDom(dom);
        } catch (TransformerException ex) {
            Logger.getLogger(GestioProducte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
