package controller;

import model.ToDoListModel;
import view.ToDoListView;

import java.awt.Color;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddTaskController implements ActionListener
{
    private ToDoListView tdView; 

    public AddTaskController(ToDoListView view)
    {
        this.tdView = view;
    }

    public void actionPerformed(ActionEvent e)
    {
        String currentTaskName = tdView.addTaskField.getText();
        String currentTaskNotes = (tdView.isaddNoteFieldEmpty == true ? "" : tdView.addNoteField.getText() );

        if (tdView.isaddTaskFieldEmpty == true)
        {
            tdView.MustEnterTaskLabel.setText("Task name should not be empty!");
        }
        else 
        {
            tdView.MustEnterTaskLabel.setText("");

            new ToDoListModel(tdView.taskTableModel).addTask(currentTaskName, currentTaskNotes);
            
            tdView.isaddTaskFieldEmpty = true; 
            tdView.addTaskField.setText("Add a new Task ..."); 
            tdView.addTaskField.setFont(new Font("SansSerif", Font.ITALIC, 12));
            tdView.addTaskField.setForeground(Color.LIGHT_GRAY);

            tdView.isaddNoteFieldEmpty = true;
            tdView.addNoteField.setText("Notes ..."); 
            tdView.addNoteField.setFont(new Font("SansSerif", Font.ITALIC, 12));
            tdView.addNoteField.setForeground(Color.LIGHT_GRAY);
        }      
    }
}