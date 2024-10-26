package rom;

import Modelos.CharacterInfo;
import Modelos.TaskInfo;
import Utils.IconUtils;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.FontMetrics;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TaskManager extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public TaskManager() {
        setTitle("Gerenciador de Tarefas Semanais");
        setSize(1200, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columns = {"Foto", "Personagem", "Cake", "Chaos MVP", "Chaos Mini", "ET", "Oracle", "Corredor",
            "Valhalla", "Purgatory", "Thanatos", "Phantom", "Museu", "Ilha", "PSR", "WSA", "POG"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) {
                    return Icon.class;
                }
                return column == 1 ? String.class : Boolean.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0 && column != 1;
            }
        };
        table = new JTable(model);
        table.setRowHeight(94);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getColumnModel().getColumn(0).setPreferredWidth(103);
        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        for (int i = 2; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(70);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
                
        adjustColumnWidths(table);

        loadData();
        
        createMenuBar();

        IconUtils.loadIcon(this);

        // Adiciona um TableModelListener para salvar automaticamente quando um checkbox é clicado
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE && e.getColumn() > 1) {
                    SwingUtilities.invokeLater(() -> saveData());
                }
            }
        });
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

        JMenuItem resetTasksItem = new JMenuItem("Resetar Tarefas");
        resetTasksItem.addActionListener(e -> resetTasks());

        menuBar.add(personagemMenu);
        menuBar.add(resetTasksItem);

        setJMenuBar(menuBar);
    }

    private void addCharacter() {
        String characterName = JOptionPane.showInputDialog(this, "Digite o nome do personagem:");
        if (characterName != null && !characterName.trim().isEmpty()) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Escolha uma foto");
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String photoPath = selectedFile.getAbsolutePath();

                ImageIcon originalIcon = new ImageIcon(photoPath);
                Image originalImage = originalIcon.getImage();

                int originalWidth = originalIcon.getIconWidth();
                int originalHeight = originalIcon.getIconHeight();
                double widthRatio = 103.0 / originalWidth;
                double heightRatio = 94.0 / originalHeight;
                double scaleFactor = Math.min(widthRatio, heightRatio);

                int newWidth = (int) (originalWidth * scaleFactor);
                int newHeight = (int) (originalHeight * scaleFactor);

                Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                ImageIcon centeredIcon = createCenteredIcon(scaledIcon, 103, 94);
                centeredIcon.setDescription(photoPath);

                Object[] newRow = new Object[table.getColumnCount()];
                newRow[0] = centeredIcon;
                newRow[1] = characterName.trim();
                for (int i = 2; i < newRow.length; i++) {
                    newRow[i] = false;
                }
                model.addRow(newRow);
                
                saveData();
            }
        }
    }

    private void saveData() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        List<CharacterInfo> characterInfos = new ArrayList<>();
        List<TaskInfo> taskInfos = new ArrayList<>();

        for (int row = 0; row < model.getRowCount(); row++) {
            String characterName = (String) model.getValueAt(row, 1);
            Icon icon = (Icon) model.getValueAt(row, 0);
            String photoPath = icon instanceof ImageIcon ? ((ImageIcon) icon).getDescription() : "";

            characterInfos.add(new CharacterInfo(characterName, photoPath));

            boolean[] tasks = new boolean[model.getColumnCount() - 2];
            for (int col = 2; col < model.getColumnCount(); col++) {
                tasks[col - 2] = (boolean) model.getValueAt(row, col);
            }

            taskInfos.add(new TaskInfo(characterName, tasks));
        }

        try (FileWriter writer = new FileWriter("personagens.json")) {
            gson.toJson(characterInfos, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter("tarefas.json")) {
            gson.toJson(taskInfos, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader("personagens.json")) {
            CharacterInfo[] characters = gson.fromJson(reader, CharacterInfo[].class);
            for (CharacterInfo character : characters) {
                ImageIcon icon = new ImageIcon(character.getPhotoPath());
                if (icon.getIconWidth() <= 0 || icon.getIconHeight() <= 0) {
                    JOptionPane.showMessageDialog(this, "Não foi possível carregar a imagem: " + character.getPhotoPath(), "Erro de Imagem", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                Object[] rowData = new Object[table.getColumnCount()];
                rowData[0] = icon;
                rowData[1] = character.getName();
                for (int i = 2; i < rowData.length; i++) {
                    rowData[i] = false;
                }
                model.addRow(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader("tarefas.json")) {
            TaskInfo[] tasks = gson.fromJson(reader, TaskInfo[].class);
            for (TaskInfo task : tasks) {
                for (int row = 0; row < model.getRowCount(); row++) {
                    if (model.getValueAt(row, 1).equals(task.getCharacterName())) {
                        for (int col = 2; col < model.getColumnCount(); col++) {
                            model.setValueAt(task.getTasks()[col - 2], row, col);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void adjustColumnWidths(JTable table) {
        for (int i = 0; i < table.getColumnCount(); i++) {
            String columnTitle = table.getColumnName(i);
            FontMetrics metrics = table.getFontMetrics(table.getFont());
            int titleWidth = metrics.stringWidth(columnTitle);
            int padding = 10;
            int preferredWidth = Math.max(titleWidth + padding, table.getColumnModel().getColumn(i).getPreferredWidth());
            table.getColumnModel().getColumn(i).setPreferredWidth(preferredWidth);
        }
    }

    private ImageIcon createCenteredIcon(ImageIcon icon, int width, int height) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();

        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, width, height);
        g2.setComposite(AlphaComposite.SrcOver);

        int x = (width - icon.getIconWidth()) / 2;
        int y = (height - icon.getIconHeight()) / 2;

        g2.drawImage(icon.getImage(), x, y, null);
        g2.dispose();

        return new ImageIcon(bufferedImage);
    }

    private void editCharacter() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String currentCharacterName = (String) model.getValueAt(selectedRow, 1);
            String newCharacterName = JOptionPane.showInputDialog(this, "Edite o nome do personagem:", currentCharacterName);
            if (newCharacterName != null && !newCharacterName.trim().isEmpty()) {
                model.setValueAt(newCharacterName.trim(), selectedRow, 1);
            }

            int changePhoto = JOptionPane.showConfirmDialog(this, "Deseja alterar a foto?", "Alterar Foto", JOptionPane.YES_NO_OPTION);
            if (changePhoto == JOptionPane.YES_OPTION) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Escolha uma nova foto");
                int result = fileChooser.showOpenDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String photoPath = selectedFile.getAbsolutePath();

                    ImageIcon originalIcon = new ImageIcon(photoPath);
                    Image originalImage = originalIcon.getImage();

                    int originalWidth = originalIcon.getIconWidth();
                    int originalHeight = originalIcon.getIconHeight();
                    double widthRatio = 103.0 / originalWidth;
                    double heightRatio = 94.0 / originalHeight;
                    double scaleFactor = Math.min(widthRatio, heightRatio);

                    int newWidth = (int) (originalWidth * scaleFactor);
                    int newHeight = (int) (originalHeight * scaleFactor);

                    Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);

                    ImageIcon centeredIcon = createCenteredIcon(scaledIcon, 103, 94);
                    centeredIcon.setDescription(photoPath);

                    model.setValueAt(centeredIcon, selectedRow, 0);
                }
            }
            
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

    private void resetTasks() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja resetar todas as tarefas?", 
            "Confirmação de Reset", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 2; col < model.getColumnCount(); col++) {
                    model.setValueAt(false, row, col);
                }
            }
            saveData();
            JOptionPane.showMessageDialog(this, "Todas as tarefas foram resetadas.", "Reset Concluído", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            TaskManager manager = new TaskManager();
            manager.setVisible(true);
        });
    }
}
