package GUI;
<<<<<<< Updated upstream
=======
/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
>>>>>>> Stashed changes

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

import ed_trabalho.*;

public class GUI {

    private Game game;
    private JFrame frame;
    private JPanel cards;
    private CardLayout cardLayout;
    private JComboBox<String> flagsComboBox1 = new JComboBox<>();
    private JComboBox<String> flagsComboBox2 = new JComboBox<>();
    private int numberofbots;
    private JComboBox<String> comboBox1; 
    private JComboBox<String> comboBox2;
    private JLabel lbplayer;
    private Random random = new Random();
    private int vez = random.nextInt(2);

    public GUI() {
        game = new Game();

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // Página de inserção de jogadores
        JPanel playerPanel = createPlayerPanel();
        cards.add(playerPanel, "PLAYER_PANEL");

        // Página de seleção de mapa
        JPanel mapPanel = createMapPanel();
        cards.add(mapPanel, "MAP_PANEL");

        JPanel flagsPanel = flagsPanel();
        cards.add(flagsPanel, "FLAGS_PANEL");

        JPanel insertmapPanel = insertMapPanel();
        cards.add(insertmapPanel, "INSERTMAP_PANEL");

        JPanel botPanel = botPanel();
        cards.add(botPanel, "BOT_PANEL");

        JPanel startPanel = StartPanel();
        cards.add(startPanel, "START_PANEL");

        cardLayout.show(cards, "PLAYER_PANEL"); // Mostra a página de inserção de jogadores por padrão

        frame.add(cards);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createPlayerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lbplayer1 = new JLabel("Insira o nome do jogador 1");
        JTextField txtplayer1 = new JTextField(20);
        JLabel lbplayer2 = new JLabel("Insira o nome do jogador 2");
        JTextField txtplayer2 = new JTextField(20);
        JButton btn_players = new JButton("Players");
        btn_players.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name1 = txtplayer1.getText();
                String name2 = txtplayer2.getText();
                game.initiatePlayer(name1, name2);
                cardLayout.show(cards, "MAP_PANEL"); // Alterna para a página de seleção de mapa
            }
        });

        panel.add(lbplayer1);
        panel.add(txtplayer1);
        panel.add(lbplayer2);
        panel.add(txtplayer2);
        panel.add(btn_players);

        return panel;
    }

    private JPanel createMapPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton btnImportMap = new JButton("Import Map");
        JButton btnCreateMap = new JButton("Create Map");
        
        btnImportMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getMap().importMap("map.json");
                fillComboBox();
                cardLayout.show(cards, "FLAGS_PANEL");
            }
        });

        btnCreateMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "INSERTMAP_PANEL");    
            }
        });

        panel.add(btnImportMap);
        panel.add(btnCreateMap);

        return panel;
    }

    private JPanel insertMapPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        JLabel lbNumVertices = new JLabel("Number of Vertices");
        JTextField txtNumVertices = new JTextField(10);
    
        JLabel lbDensidade = new JLabel("Density of the graph (0%-100%)");
        JTextField txtDensidade = new JTextField(10);
        
        JCheckBox checkBox = new JCheckBox("Bidirectional");
        checkBox.setSelected(false);


        JButton btnCriarMapa = new JButton("Create Map");
        btnCriarMapa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numVert = Integer.parseInt(txtNumVertices.getText());
                float densidade = Float.parseFloat(txtDensidade.getText());
                
                try {
                    if (checkBox.isSelected()) {
                        game.createBiMap(numVert, densidade);
                    } 
                    else {
                        game.createMap(numVert, densidade);
                    }
                    game.getMap().exportMap();
                    JOptionPane.showMessageDialog(frame, "Map created successfully!");
    
                    // Preenche a ComboBox apenas quando o mapa estiver criado e a janela estiver aberta
                    fillComboBox();
    
                    cardLayout.show(cards, "FLAGS_PANEL");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    
        panel.add(lbNumVertices);
        panel.add(txtNumVertices);
        panel.add(lbDensidade);
        panel.add(txtDensidade);
        panel.add(checkBox);
        panel.add(btnCriarMapa);
    
        return panel;
    }
    
    private void fillComboBox() {
        String[] op = new String[game.getMap().getLocations().length];
        for (int i = 0; i < game.getMap().getLocations().length; i++) {
            op[i] = String.valueOf(i);
        }

        // Atualiza a ComboBox existente
        flagsComboBox1.setModel(new DefaultComboBoxModel<>(op));
        flagsComboBox1.setSelectedIndex(0);
        flagsComboBox2.setModel(new DefaultComboBoxModel<>(op));
        flagsComboBox2.setSelectedIndex(0);
    }
    
    private JPanel flagsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //flagsComboBox1 = new JComboBox<>();
        JLabel lbFlag1 = new JLabel("Flag 1 position:");
    
        //flagsComboBox2 = new JComboBox<>();
        JLabel lbFlag2 = new JLabel("Flag 2 position:");
        
        JButton btn_flags = new JButton("Flags");
    
        btn_flags.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Converta para String primeiro antes de converter para int
                int flag1 = Integer.parseInt((String) flagsComboBox1.getSelectedItem());
                int flag2 = Integer.parseInt((String) flagsComboBox2.getSelectedItem());
                int op = 0;
                try {
                    op = game.chooseFlags(flag1, flag2);
                    if (op == -1) {
                        JOptionPane.showMessageDialog(frame, "Invalid flag location!");
                        cardLayout.show(cards, "FLAGS_PANEL");
                    } else if (op == 0) {
                        JOptionPane.showMessageDialog(frame, "Flags in the same location!");
                    } else if (op == 1) {
                        JOptionPane.showMessageDialog(frame, "Flags chosen successfully!");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if(op == 1){
                cardLayout.show(cards, "BOT_PANEL"); // Alterna para a página de seleção de algoritmos
                }
                else{
                    cardLayout.show(cards, "FLAGS_PANEL");
                }
            }
        });
        
    
        // Adiciona a ComboBox à sua janela ou painel conforme necessário
        
    
        panel.add(lbFlag1);
        panel.add(flagsComboBox1);
        panel.add(lbFlag2);
        panel.add(flagsComboBox2);
        panel.add(btn_flags);
    
        // ...
    
        return panel;
    }
    
    //funçao para adicionar bots
    private JPanel botPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lbplayer = new JLabel("Insert the number of bots");
        JTextField txtplayer = new JTextField(20);
        JButton btn_players = new JButton("Bots");
        btn_players.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name1 = txtplayer.getText();
                numberofbots = Integer.parseInt(name1);
                JPanel algorithmPanel = AlgorithmPanel(0);
                for(int i = 0; i < 2; i++){
                    for(int j = 0; j < numberofbots; j++){
                        Bot bot = new Bot(game.getPlayers().get(i).getFlag().getIndex(),j+1);
                        game.getMap().getLocation(game.getPlayers().get(i).getFlag().getIndex()).addBot(bot);
                        game.getPlayers().get(i).addBot(bot);
                    }
                }
                cards.add(algorithmPanel, "ALGORITHM_PANEL");
                cardLayout.show(cards, "ALGORITHM_PANEL");
            }
        });
        
        panel.add(lbplayer);
        panel.add(txtplayer);
        panel.add(btn_players);

        return panel;
    }

    private JPanel AlgorithmPanel(int temp) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
    
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(2, 3));
    
        gridPanel.add(new JLabel("Players"));
        gridPanel.add(new JLabel("Player 1"));
        gridPanel.add(new JLabel("Player 2"));
        JLabel label = new JLabel("Bot " + (temp + 1));
        gridPanel.add(label);
        JComboBox<String> comboBox1 = new JComboBox<>();
        JComboBox<String> comboBox2 = new JComboBox<>();
        comboBox1.addItem("Shortest Path");
        comboBox1.addItem("Random Path");
        comboBox1.addItem("Greedy Path");
        comboBox1.addItem("Dumb Path");
        comboBox2.addItem("Shortest Path");
        comboBox2.addItem("Random Path");
        comboBox2.addItem("Greedy Path");
        comboBox2.addItem("Dumb Path");
        gridPanel.add(comboBox1);
        gridPanel.add(comboBox2);
    
        panel.add(gridPanel, BorderLayout.CENTER);
    
        JButton btn_bot = new JButton("Algorithms");
        btn_bot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int p1 = comboBox1.getSelectedIndex();
                int p2 = comboBox2.getSelectedIndex();
                try {
                    game.chooseAlgoritms(game.getPlayers().get(0), temp, p1 + 1);
                    game.chooseAlgoritms(game.getPlayers().get(1), temp, p2 + 1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if (temp != numberofbots - 1) {
                    JPanel algorithmPanel = AlgorithmPanel(temp + 1);
                    cards.add(algorithmPanel, "ALGORITHM_PANEL");
                    cardLayout.show(cards, "ALGORITHM_PANEL");
                } else {
                    cardLayout.show(cards, "START_PANEL");
                }
            }
        });
    
        panel.add(btn_bot, BorderLayout.SOUTH);
    
        return panel;
    }

    private JPanel StartPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        JButton btnStartGame = new JButton("Start Game");
        btnStartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel gamePanel = gamePanel();
                    cards.add(gamePanel, "GAME_PANEL");
                    cardLayout.show(cards, "GAME_PANEL");
            }
        });
        panel.add(btnStartGame);
        return panel;
    }
    
    private JPanel gamePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();
        try {
            Object[] vertices = new Object[game.getMap().getLocations().length];
            int centerX = 700;
            int centerY = 400;
            int radius = game.getMap().getLocations().length * 20;
    
            for (int i = 0; i < game.getMap().getLocations().length; i++) {
                double angle = (2 * Math.PI * i) / game.getMap().getLocations().length;
                int x = (int) (centerX + radius * Math.cos(angle));
                int y = (int) (centerY + radius * Math.sin(angle));
                int playerIndex = -1;

                if(game.getMap().getLocation(i).getBot() != null){
                    for(int j = 0; j < game.getPlayers().size(); j++){
                        if(game.getPlayers().get(j).getBotTurn() == game.getMap().getLocation(i).getBot()){
                            playerIndex = j;
                        }
                    }
                }

                String fillColor = (playerIndex == 0) ? "#00FF00" : (playerIndex == 1) ? "#FF0000" : "#FFFFFF";
    
                if (game.getMap().getLocation(i).getHasBot()) {
                    vertices[i] = graph.insertVertex(parent, null, i + "\nBot: " + game.getMap().getLocation(i).getBot().getIndex(), x, y, 60, 60, "fillColor=" + fillColor);
                } else {
                    vertices[i] = graph.insertVertex(parent, null, i, x, y, 20, 20, "fillColor=" + fillColor);
                }
            }
    
            for (int i = 0; i < game.getMap().getLocations().length; i++) {
                for (int j = 0; j < game.getMap().getLocations().length; j++) {
                    if (i != j && game.getMap().getNetwork().hasEdge(i, j)) {
                        graph.insertEdge(parent, null, game.getMap().getNetwork().getAdjMatrix()[i][j], vertices[i], vertices[j]);
                    }
                }
            }
        } finally {
            graph.getModel().endUpdate();
        }
    
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        panel.removeAll();
        panel.add(graphComponent);
    
        panel.revalidate();
        panel.repaint();
    
        JButton btnproximaronda = new JButton("avançar");
        btnproximaronda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    if(vez == 0){
                        game.play(game.getPlayers().get(0));
                        vez = 1;
                    }
                    else if(vez==1){
                        game.play(game.getPlayers().get(1));
                        vez = 0;
                    }
                    
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
    
                JPanel gamePanel = gamePanel();
                cards.add(gamePanel, "GAME_PANEL");
                cardLayout.show(cards, "GAME_PANEL");
            }
        });
        btnproximaronda.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnproximaronda.setAlignmentY(Component.CENTER_ALIGNMENT);
        btnproximaronda.setMaximumSize(new Dimension(Integer.MAX_VALUE, btnproximaronda.getPreferredSize().height));
        panel.add(btnproximaronda);
        return panel;
    }
}
