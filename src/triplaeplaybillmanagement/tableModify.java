/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package triplaeplaybillmanagement;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Jaideep
 */
public class tableModify extends JFrame {

    public static void changeTableHeader(JTableHeader head, JTable table){
        head = table.getTableHeader();
       
       head.setFont(new Font("Dialog", Font.BOLD, 15));
       head.setBackground(new Color(61,81,85));
       head.setForeground(Color.WHITE);
        
    }
    
    public static void alternativeRowColor(JTable myTable){
        myTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

        @Override
        public Component getTableCellRendererComponent(JTable table, 
                Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            Component c = super.getTableCellRendererComponent(table, 
                value, isSelected, hasFocus, row, column);
            c.setBackground(row%2==0 ? (new Color(226,220,220)) : (new Color(178,214,208)));                        
            return c;
        };
    });
    }
    
    
    
}
