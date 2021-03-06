package lpoo.pokemonascii.data.pokemon;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PokemonSpecies {
    private int pokedexNumber;
    private String name;
    private PokemonType primaryType;
    private PokemonType secondaryType;
    private PokemonStats baseStats;
    private int totalExperience;
    private ArrayList<PokemonMove> levelMoves;

    public PokemonSpecies(int pokedexNumber) throws IOException, SAXException, ParserConfigurationException {
        this.pokedexNumber = pokedexNumber;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        builder = factory.newDocumentBuilder();

        // Load the input XML document, parse it and return an instance of the Document class.
        Document document;

        document = builder.parse(new File("data" + File.separator + "xml" + File.separator + "pokemon" +
                File.separator + pokedexNumber + ".xml"));

        NodeList nodeList = document.getChildNodes();

        // Read the XML document
        Node node = nodeList.item(0);

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element elem = (Element) node;

            // Name
            name = elem.getElementsByTagName("name").item(0).getTextContent();

            // Primary type
            primaryType = new PokemonType(elem.getElementsByTagName("type").item(0).getTextContent().toUpperCase());

            // Secondary type, if the pokémon has one
            if (elem.getElementsByTagName("type").item(1) != null) // TODO: Introduce NULL object
                secondaryType = new PokemonType(elem.getElementsByTagName("type").item(1).getTextContent().toUpperCase());

            // Total Experience
            totalExperience = Integer.parseInt(elem.getElementsByTagName("exp").item(0).getTextContent());

            // Base Stats
            NodeList statsNodes = elem.getElementsByTagName("stats").item(0).getChildNodes();
            List<Integer> stats = new ArrayList<>();

            for (int i = 0; i < statsNodes.getLength(); i++) {
                Node statNode = statsNodes.item(i);

                if (statNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element nodeElement = (Element) statNode;
                    stats.add(Integer.parseInt(nodeElement.getTextContent()));
                }
            }

            baseStats = new PokemonStats(stats);

            // Moves
            NodeList movesNodes = elem.getElementsByTagName("moves").item(0).getChildNodes();
            levelMoves = new ArrayList<>();

            for (int i = 0; i < movesNodes.getLength(); i++) {
                Node moveNode = movesNodes.item(i);

                if (moveNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element nodeElement = (Element) moveNode;
                    String moveName = nodeElement.getElementsByTagName("name").item(0).getTextContent();

                    if (nodeElement.getAttribute("type").equals("level-up")) {
                        try {
                            levelMoves.add(new PokemonMove(moveName));
                        } catch (Exception ignored) {
                            // The correspondent file wasn't found
                            // This might be, because it was a move from another generation
                            // Or its name was spelled incorrectly
                        }
                    }
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public PokemonType getPrimaryType() {
        return primaryType;
    }

    public PokemonType getSecondaryType() {
        return secondaryType;
    }

    public int getPokedexNumber() {
        return pokedexNumber;
    }

    public PokemonStats getBaseStats() {
        return baseStats;
    }

    public int getTotalExperience() {
        return totalExperience;
    }

    public List<PokemonMove> getLevelMoves() {
        return levelMoves;
    }
}
