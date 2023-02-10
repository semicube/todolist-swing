package model;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

import java.util.Date;
import java.text.SimpleDateFormat;

public class ToDoListModel
{
    private DefaultTableModel tbModel;

    private static final String conURL = "jdbc:sqlite:db/task.db";
    private Connection con;
    
    private Statement conStatement;
    private ResultSet rs;

    private static final String dbTableName = "TASKTABLE";

    public ToDoListModel(DefaultTableModel model)
    {
        this.tbModel = model;
    }

    public void fetchTasks()
    {
        try {
            con = DriverManager.getConnection(conURL);
            conStatement = con.createStatement();

            rs = conStatement.executeQuery("select * from " + dbTableName);
            
            tbModel.setRowCount(0);
            while (rs.next()) 
            {
                tbModel.addRow(new Object[] 
                { 
                    rs.getBoolean(1), 
                    rs.getString(2), 
                    rs.getString(3), 
                    rs.getString(4) 
                });
            }

            con.close();
        } 
        catch (SQLException e) 
        { 
            e.printStackTrace();
        }
    }

    public void removeTask(int rowIndex)
    {
        try {
            con = DriverManager.getConnection(conURL);
            conStatement = con.createStatement();

            conStatement.execute("delete from " + dbTableName + " where rowid in (select rowid from " + dbTableName + " LIMIT " + rowIndex + ",1)");
            tbModel.removeRow(rowIndex);

            con.close();
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    public void addTask(String taskName, String taskNotes)
    {
        try {
            con = DriverManager.getConnection(conURL);
            conStatement = con.createStatement();
            
            String formattedDate = (new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            String formattedTaskName = taskName.replace("'", "''");
            String formattedTaskNotes = taskNotes.replace("'", "''");

            String SQL = "insert into " + dbTableName + " values(0, '" + formattedTaskName + "', '" + formattedTaskNotes + "', '" + formattedDate + "')";
            conStatement.execute(SQL);
            tbModel.addRow(new Object[] { Boolean.FALSE, taskName, taskNotes, formattedDate });
            
            con.close();
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
  
}