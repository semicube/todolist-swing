package controller;

import model.ToDoListModel;
import view.ToDoListView;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DeleteTaskController implements ActionListener
{
    private ToDoListView tdView;

    public DeleteTaskController(ToDoListView view)
    {
        this.tdView = view;
    }

    public void actionPerformed(ActionEvent e)
    {
        int currentRow = 0;
        while (currentRow < tdView.taskTable.getRowCount())
        {
            if (tdView.taskTable.getValueAt(currentRow, 0) == Boolean.TRUE)
            {
                new ToDoListModel(tdView.taskTableModel).removeTask(currentRow);
            } 
            else 
            { 
                currentRow++; 
            }
        }
    }
}