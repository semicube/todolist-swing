package controller;

import view.ToDoListView;

import java.awt.Color;
import java.awt.Font;

import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

public class AddFieldController implements FocusListener 
{
    private ToDoListView tdView;

    public AddFieldController(ToDoListView view)
    {
        this.tdView = view;
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == tdView.addTaskField && tdView.isaddTaskFieldEmpty == true)
        {
            tdView.addTaskField.setText("");
            tdView.addTaskField.setFont(new Font("SansSerif", Font.PLAIN, 12)); 
            tdView.addTaskField.setForeground(Color.BLACK); 
        }
        if (e.getSource() == tdView.addNoteField && tdView.isaddNoteFieldEmpty == true)
        {
            tdView.addNoteField.setText("");
            tdView.addNoteField.setFont(new Font("SansSerif", Font.PLAIN, 12)); 
            tdView.addNoteField.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) 
    {
        if (e.getSource() == tdView.addTaskField)
        {
            if (tdView.addTaskField.getText().length() == 0) 
            {  
                tdView.addTaskField.setText("Add a new Task ..."); 
                tdView.addTaskField.setFont(new Font("SansSerif", Font.ITALIC, 12));
                tdView.addTaskField.setForeground(Color.LIGHT_GRAY);
                tdView.isaddTaskFieldEmpty = true; 
            } 
            else tdView.isaddTaskFieldEmpty = false;
        }

        if (e.getSource() == tdView.addNoteField)
        {
            if (e.getSource() == tdView.addNoteField && tdView.addNoteField.getText().length() == 0) 
            {  
                tdView.addNoteField.setText("Notes ..."); 
                tdView.addNoteField.setFont(new Font("SansSerif", Font.ITALIC, 12));
                tdView.addNoteField.setForeground(Color.LIGHT_GRAY);
                tdView.isaddNoteFieldEmpty = true; 
            } 
            else tdView.isaddNoteFieldEmpty = false;
        }
    }

}