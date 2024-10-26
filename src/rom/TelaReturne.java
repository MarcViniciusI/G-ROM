package rom;

import Modelos.ReturneInfo;
import Modelos.ReturneTask;
import Utils.IconUtils;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.TableCellRenderer;

public class TelaReturne extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private List<ReturneInfo> returneInfos;

    public TelaReturne() {
        setTitle("Returne");
        setSize(1200, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columns = {"Personagem", "Último Login", "Servidor","Chaotic", "ET", "Oracle", "Corredor",
            "Valhalla", "Purgatory", "Thanatos", "Phantom", "Museu", "Ilha", "WSA", "POG"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) return String.class; // Personagem
                if (column == 1) return String.class; // Último Login
                if (column == 2) return String.class; // Servidor
                return Boolean.class; // Outras colunas
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Apenas a coluna do nome do personagem não é editável
            }
        };
        table = new JTable(model);
        table.setRowHeight(94);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        loadData();
        createMenuBar();
        IconUtils.loadIcon(this);

        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE && e.getColumn() > 1) {
                    SwingUtilities.invokeLater(() -> saveData());
                }
            }
        });
        
        adjustColumnWidths();
    }

    private void adjustColumnWidths() {
        for (int i = 0; i < table.getColumnCount(); i++) {
            int width = 0;
            for (int j = 0; j < table.getRowCount(); j++) {
                TableCellRenderer renderer = table.getCellRenderer(j, i);
                Component comp = table.prepareRenderer(renderer, j, i);
                width = Math.max(width, comp.getPreferredSize().width);
            }
            table.getColumnModel().getColumn(i).setPreferredWidth(width + 10);
        }
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu personagemMenu = new JMenu("Personagem");

        JMenuItem addPersonagemItem = new JMenuItem("Adicionar Personagem");
        addPersonagemItem.addActionListener(e -> addCharacter());

        JMenuItem editPersonagemItem = new JMenuItem("Editar Personagem");
        editPersonagemItem.addActionListener(e -> editCharacter());

        JMenuItem deletePersonagemItem = new JMenuItem("Excluir Personagem");
        deletePersonagemItem.addActionListener(e -> deleteCharacter());

        personagemMenu.add(addPersonagemItem);
        personagemMenu.add(editPersonagemItem);
        personagemMenu.add(deletePersonagemItem);
        
        menuBar.add(personagemMenu);
        setJMenuBar(menuBar);
    }

    private void addCharacter() {
        String characterName = JOptionPane.showInputDialog(this, "Digite o nome do personagem:");
        if (characterName != null && !characterName.trim().isEmpty()) {
            // Criação de um JComboBox para selecionar o servidor
            String[] servers = {"Eternal Love", "Destiny Promise"};
            String selectedServer = (String) JOptionPane.showInputDialog(
                this,
                "Escolha o servidor:",
                "Servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                servers,
                servers[0]
            );

            Object[] newRow = new Object[table.getColumnCount()];
            newRow[0] = characterName.trim();
            newRow[1] = ""; // Inicialize o campo de data
            newRow[2] = selectedServer; // Adicione o servidor
            for (int i = 3; i < newRow.length; i++) { // Começa do índice 3 para os checkboxes
                newRow[i] = false;
            }
            model.addRow(newRow);
            saveData();
            adjustColumnWidths(); // Ajusta as larguras das colunas após adicionar um personagem
        }
    }

    private void saveData() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        returneInfos = new ArrayList<>();
        List<ReturneTask> returneTask = new ArrayList<>();

        for (int row = 0; row < model.getRowCount(); row++) {
            String characterName = (String) model.getValueAt(row, 0);
            String lastLoginDate = (String) model.getValueAt(row, 1);
            String server = (String) model.getValueAt(row, 2); // Obtenha o servidor
            returneInfos.add(new ReturneInfo(characterName, lastLoginDate, server)); // Salve também o servidor

            boolean[] tasks = new boolean[model.getColumnCount() - 3]; // Exclua as colunas de nome, data e servidor
            for (int col = 3; col < model.getColumnCount(); col++) {
                tasks[col - 3] = (boolean) model.getValueAt(row, col);
            }

            returneTask.add(new ReturneTask(characterName, tasks));
        }

        try (FileWriter writer = new FileWriter("data-de-login.json")) {
            gson.toJson(returneInfos, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter("returne-task.json")) {
            gson.toJson(returneTask, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        Gson gson = new Gson();

        // Verifique se o arquivo data-de-login.json existe
        File loginFile = new File("data-de-login.json");
        if (loginFile.exists()) {
            try (FileReader reader = new FileReader(loginFile)) {
                ReturneInfo[] characters = gson.fromJson(reader, ReturneInfo[].class);
                for (ReturneInfo character : characters) {
                    Object[] rowData = new Object[table.getColumnCount()];
                    rowData[0] = character.getCharacterName();
                    rowData[1] = character.getLastLoginDate();
                    rowData[2] = character.getServer(); // Carregue o servidor
                    for (int i = 3; i < rowData.length; i++) {
                        rowData[i] = false; // Inicialize os checkboxes como false
                    }
                    model.addRow(rowData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Verifique se o arquivo returne-task.json existe
        File taskFile = new File("returne-task.json");
        if (taskFile.exists()) {
            try (FileReader reader = new FileReader(taskFile)) {
                ReturneTask[] tasks = gson.fromJson(reader, ReturneTask[].class);
                for (ReturneTask task : tasks) {
                    for (int row = 0; row < model.getRowCount(); row++) {
                        if (model.getValueAt(row, 0).equals(task.getCharacterName())) {
                            for (int col = 3; col < model.getColumnCount(); col++) {
                                model.setValueAt(task.getTasks()[col - 3], row, col); // Ajuste para o novo índice
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        adjustColumnWidths(); // Ajusta as larguras das colunas após carregar os dados
    }

    private void editCharacter() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String currentCharacterName = (String) model.getValueAt(selectedRow, 0);
            String newCharacterName = JOptionPane.showInputDialog(this, "Edite o nome do personagem:", currentCharacterName);
            if (newCharacterName != null && !newCharacterName.trim().isEmpty()) {
                model.setValueAt(newCharacterName.trim(), selectedRow, 0);
            }

            // Editar o servidor
            String[] servers = {"Eternal Love", "Destiny Promise"};
            String selectedServer = (String) JOptionPane.showInputDialog(
                this,
                "Escolha o servidor:",
                "Servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                servers,
                model.getValueAt(selectedRow, 2) // Mantenha o servidor atual como padrão
            );

            model.setValueAt(selectedServer, selectedRow, 2); // Atualize o servidor

            saveData();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um personagem para editar!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCharacter() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este personagem?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                model.removeRow(selectedRow);
                saveData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um personagem para excluir.", "Nenhuma Seleção", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            TelaReturne tela = new TelaReturne();
            tela.setVisible(true);
        });
    }
}
