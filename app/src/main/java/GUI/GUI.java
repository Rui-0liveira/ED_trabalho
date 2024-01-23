package GUI;
/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */

import com.mxgraph.model.mxCell;

import com.mxgraph.model.mxGeometry;


import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import com.mxgraph.view.mxStylesheet;

import com.mxgraph.view.mxStylesheet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Random;

import ed_trabalho.*;
import com.mxgraph.util.mxConstants;

/**
 * Classe que implementa a interface gráfica do jogo.
 */
public class GUI {

    private Game game;
    private JFrame frame;
    private JPanel cards;
    private CardLayout cardLayout;
    private JComboBox<String> flagsComboBox1 = new JComboBox<>();
    private JComboBox<String> flagsComboBox2 = new JComboBox<>();
    private int numberofbots;
    private Random random = new Random();
    private int vez = random.nextInt(2);
    private int mov[] = new int[2];
    private String movimento = "";

    /**
     * Construtor da classe GUI.
     * É responsavel pelo fluxo das paginas do jogo.
     */
    public GUI() {
        game = new Game();

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        JPanel playerPanel = createPlayerPanel();
        cards.add(playerPanel, "PLAYER_PANEL");

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

        cardLayout.show(cards, "PLAYER_PANEL"); 

        frame.add(cards);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Painel de seleção de jogadores
     * Permite o utilizador inserir o nome dos jogadores
     * @return painel de seleção de jogadores
     */
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


    /**
     * Painel de seleção de mapa
     * Permite o utilizador escolher entre importar um mapa ou criar um novo mapa
     * @return painel de seleção de mapa
     */
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


    /**
     * Painel de inserção de mapa
     * Permite o utilizador inserir o número de vértices e a densidade do mapa
     * O mapa pode ser direcional ou bidirecional
     * @return painel de inserção de mapa
     */
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
    

    /**
     * Preenche a ComboBox com os vértices do mapa
     * Necessaria para a seleção das flags seja feita corretamente
     */
    private void fillComboBox() {
        String[] op = new String[game.getMap().getLocations().length];
        for (int i = 0; i < game.getMap().getLocations().length; i++) {
            op[i] = String.valueOf(i);
        }
        flagsComboBox1.setModel(new DefaultComboBoxModel<>(op));
        flagsComboBox1.setSelectedIndex(0);
        flagsComboBox2.setModel(new DefaultComboBoxModel<>(op));
        flagsComboBox2.setSelectedIndex(0);
    }

    
    /**
     * Painel de seleção de flags
     * Permite o utilizador escolher a posição das flags
     * Não permite que duas flags sejam colocadas no mesmo vértice
     * @return painel de seleção de flags
     */
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
    
    /**
     * Painel de seleção e criação de bots
     * Permite o utilizador escolher o número de bots
     * Chama o painel de seleção de algoritmos
     * @return painel de seleção e criação de bots
     */
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


    /**
     * Painel de seleção de algoritmos
     * Permite o utilizador escolher o algoritmo para cada bot
     * @param temp numero do bot da pagina atual
     * @return painel de seleção de algoritmos
     */
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
                int stop = 0;
                try {
                    if(game.checkAlgoritms(game.getPlayers().get(0), p1 + 1) || game.checkAlgoritms(game.getPlayers().get(1), p2 + 1)){
                        JOptionPane.showMessageDialog(frame, "Invalid algorithm!");
                        stop = 1;
                    }
                    else{
                        game.chooseAlgoritms(game.getPlayers().get(0), temp, p1 + 1);
                        game.chooseAlgoritms(game.getPlayers().get(1), temp, p2 + 1);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if(stop==1){
                    JPanel algorithmPanel = AlgorithmPanel(temp);
                    cards.add(algorithmPanel, "ALGORITHM_PANEL");
                    cardLayout.show(cards, "ALGORITHM_PANEL");
                }
                else if (temp != numberofbots - 1) {
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

    /**
     * Painel de começar o jogo
     * Painel para iniciar o jogo
     * Apenas contem um butao para iniciar o jogo
     * @return painel de jogo
     */
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
    

    /**
     * Painel de jogo
     * Este painel é onde ocorre todo tipo de atividades durante a partida
     * A cada movimento o painel é atualizado com as novas informações
     * @return painel de jogo
     */
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

                if(game.getMap().getLocation(i).getHasBot()){
                    for(int j = 0; j < 2; j++){
                        if(game.getPlayers().get(j).isBot(game.getMap().getLocation(i).getBot())){
                            playerIndex = j;
                        }
                    }
                }

                String fillColor = (playerIndex == 0) ? "#0000FF" : (playerIndex == 1) ? "#FF0000" : "#FFFFFF";
                
                if (game.getMap().getLocation(i).getHasBot()) {
                    vertices[i] = graph.insertVertex(parent, null, i + "\nBot: " + game.getMap().getLocation(i).getBot().getIndex(), x, y, 60, 60, "fillColor=" + fillColor);
                } else {
                    if(game.getMap().getLocation(i).getHasFlag()){
                        fillColor = (game.getMap().getLocation(i).getFlag().getColour()=="RED") ? "#FF0000" : "#0000FF";
                        vertices[i] = graph.insertVertex(parent, null, i + "\nFlag: " + game.getMap().getLocation(i).getFlag().getColour(), x, y, 60, 60, "fillColor=" + fillColor);
                    }
                    else{
                        vertices[i] = graph.insertVertex(parent, null, i, x, y, 20, 20, "fillColor=" + fillColor);
                    }
                }
            }
            for (int i = 0; i < game.getMap().getLocations().length; i++) {
                for (int j = 0; j < game.getMap().getLocations().length; j++) {
                    if (i != j && game.getMap().getNetwork().hasEdge(i, j)) {
                        graph.insertEdge(parent, null, game.getMap().getNetwork().getAdjMatrix()[i][j], vertices[i], vertices[j]);
                    }
                }
            } 

            if (mov[0] != -1 && mov[1] != -1) {
                mxCell edge = (mxCell) graph.insertEdge(parent, null, game.getMap().getNetwork().getAdjMatrix()[mov[0]][mov[1]], vertices[mov[0]], vertices[mov[1]]);

                Hashtable<String, Object> estiloAresta = new Hashtable<>();
                estiloAresta.put(mxConstants.STYLE_STROKECOLOR, "#009900");
                estiloAresta.put(mxConstants.STYLE_FONTCOLOR, "#009900");

                mxStylesheet stylesheet = graph.getStylesheet();
                stylesheet.putCellStyle("estiloArestaVermelha", estiloAresta);
                graph.setCellStyle("estiloArestaVermelha", new Object[]{edge});
            }
        } finally {
            graph.getModel().endUpdate();
        }
    
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        panel.removeAll();
        panel.add(graphComponent);
    
        panel.revalidate();
        panel.repaint();

        for(int i=0;i<numberofbots;i++){
            if(game.Win(game.getPlayers().get(0).getBots().get(i), game.getPlayers().get(1))){
                JOptionPane.showMessageDialog(frame, "Player 1 won!(bot "+(i+1)+" chegou a bandeira)");
                System.exit(0);
            }
            else if(game.Win(game.getPlayers().get(1).getBots().get(i), game.getPlayers().get(0))){
                JOptionPane.showMessageDialog(frame, "Player 2 won!(bot "+(i+1)+" chegou a bandeira)");
                System.exit(0);
            }
        }

        JLabel lblMovimento = new JLabel("Movimento Atual:" + movimento);
        lblMovimento.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblMovimento);

        JButton btnproximaronda = new JButton("avançar");
        btnproximaronda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    if(vez == 0){
                        mov[0]= game.getPlayers().get(0).getBotTurn().getLocation();
                        movimento = "Player 1 moveu o bot "+ game.getPlayers().get(0).getBotTurn().getIndex() +" de "+ mov[0] +" para ";
                        mov[1] = game.play(game.getPlayers().get(0));
                        movimento += mov[1] + " ";
                        movimento += " com peso " + game.getMap().getNetwork().getAdjMatrix()[mov[0]][mov[1]];
                        vez = 1;
                        
                    }
                    else if(vez==1){
                        mov[0]= game.getPlayers().get(1).getBotTurn().getLocation();
                        movimento = "Player 2 moveu o bot "+ game.getPlayers().get(1).getBotTurn().getIndex() +" de "+ mov[0] +" para ";
                        mov[1] = game.play(game.getPlayers().get(1));
                        movimento += mov[1] + " ";
                        movimento += " com peso " + game.getMap().getNetwork().getAdjMatrix()[mov[0]][mov[1]];
                        vez = 0;
                    }
                    
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                lblMovimento.setText("Movimento Atual: " + movimento ); 
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