package GUI;
/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import ed_trabalho.*;

public class GUI {

    private Game game;
    private JFrame frame;
    private JPanel cards;
    private CardLayout cardLayout;
    private JComboBox<String> flagsComboBox1 = new JComboBox<>();
    private JComboBox<String> flagsComboBox2 = new JComboBox<>();
    private int numberofbots;

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

        JPanel algorithmPanel = AlgorithmPanel();
        cards.add(algorithmPanel, "ALGORITHM_PANEL");

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
    
        JButton btnCriarMapa = new JButton("Create Map");
        btnCriarMapa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numVert = Integer.parseInt(txtNumVertices.getText());
                float densidade = Float.parseFloat(txtDensidade.getText());
                try {
                    game.createMap(numVert, densidade);
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
        
                try {
                    int op = game.chooseFlags(flag1, flag2);
                    if (op == -1) {
                        JOptionPane.showMessageDialog(frame, "Invalid flag location!");
                        cardLayout.show(cards, "FLAGS_PANEL");
                    } else if (op == 0) {
                        JOptionPane.showMessageDialog(frame, "Flags in the same location!");
                        cardLayout.show(cards, "FLAGS_PANEL");
                    } else if (op == 1) {
                        JOptionPane.showMessageDialog(frame, "Flags chosen successfully!");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
        
                cardLayout.show(cards, "BOT_PANEL"); // Alterna para a página de seleção de algoritmos
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

        JLabel lbplayer = new JLabel("Insert the name of bots");
        JTextField txtplayer = new JTextField(20);
        JButton btn_players = new JButton("Bots");
        btn_players.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name1 = txtplayer.getText();
                numberofbots = Integer.parseInt(name1);
                cardLayout.show(cards, "ALGORITHM_PANEL"); // Alterna para a página de seleção de mapa
            }
        });
        
        panel.add(lbplayer);
        panel.add(txtplayer);
        panel.add(btn_players);

        return panel;
    }

    //funçao para escolher o algoritmo para cada bot para cada um dos dois players
    private JPanel AlgorithmPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lbplayer = new JLabel("Choose the algorithm");
        

        return panel;
    }
}
