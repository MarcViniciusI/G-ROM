/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package rom;

import Utils.IconUtils;

/**
 *
 * @author marco
 */
public class TelaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form TelaPrincipal
     */
    public TelaPrincipal() {
        initComponents();
        IconUtils.loadIcon(this);
    }

       
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Desktop = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        MnItemSemanais = new javax.swing.JMenuItem();
        MnItemReturne = new javax.swing.JMenuItem();
        MnItemFarm = new javax.swing.JMenuItem();
        MnSobre = new javax.swing.JMenu();
        SobreDesen = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tela Principal");

        javax.swing.GroupLayout DesktopLayout = new javax.swing.GroupLayout(Desktop);
        Desktop.setLayout(DesktopLayout);
        DesktopLayout.setHorizontalGroup(
            DesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        DesktopLayout.setVerticalGroup(
            DesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jMenu2.setText("Gerenciamento");

        MnItemSemanais.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        MnItemSemanais.setText("Semanais");
        MnItemSemanais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnItemSemanaisActionPerformed(evt);
            }
        });
        jMenu2.add(MnItemSemanais);
        
        MnItemReturne.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        MnItemReturne.setText("Returne");
        MnItemReturne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnItemReturneActionPerformed(evt);
            }
        });
        jMenu2.add(MnItemReturne);
        
        MnItemFarm.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        MnItemFarm.setText("Farm (Proximas Atualizações)");
        MnItemFarm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnItemFarmActionPerformed(evt);
            }
        });
        jMenu2.add(MnItemFarm);

        jMenuBar1.add(jMenu2);

        MnSobre.setText("Sobre");

        SobreDesen.setText("Sobre o Desenvolvimento");
        SobreDesen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SobreDesenActionPerformed(evt);
            }
        });
        MnSobre.add(SobreDesen);

        jMenuBar1.add(MnSobre);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Desktop)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Desktop)
        );

        setSize(new java.awt.Dimension(412, 606));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void SobreDesenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SobreDesenActionPerformed
        TelaDesenvolvedor sobre = new TelaDesenvolvedor();
        sobre.setVisible(true);
        Desktop.add(sobre);
    }//GEN-LAST:event_SobreDesenActionPerformed

    private void MnItemSemanaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnItemSemanaisActionPerformed
        TaskManager semanais = new TaskManager();
        semanais.setVisible(true);
    }//GEN-LAST:event_MnItemSemanaisActionPerformed

    private void MnItemReturneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnItemReturneActionPerformed
        TelaReturne returne = new TelaReturne();
        returne.setVisible(true);
    }//GEN-LAST:event_MnItemReturneActionPerformed
    
    private void MnItemFarmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnItemFarmActionPerformed
        TelaFarm farm = new TelaFarm();
        farm.setVisible(true);
    }//GEN-LAST:event_MnItemFarmActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane Desktop;
    private javax.swing.JMenuItem MnItemFarm;
    private javax.swing.JMenuItem MnItemReturne;
    private javax.swing.JMenuItem MnItemSemanais;
    private javax.swing.JMenu MnSobre;
    private javax.swing.JMenuItem SobreDesen;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration//GEN-END:variables
}