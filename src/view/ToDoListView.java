package view;

import model.ToDoListModel;

import controller.AddFieldController;
import controller.AddTaskController;
import controller.DeleteTaskController;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.font.TextAttribute;

import java.text.SimpleDateFormat;

import java.util.Map;
import java.util.Date;

public class ToDoListView extends JFrame 
{
    public final static String appTitle = "A Simple To-Do";

    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;

    private JLabel headingLabel;
    private JLabel dateLabel;

    private JPanel addPanel;

    public JTextField addTaskField;
    public JTextField addNoteField;
    private JButton addTaskButton;
    public boolean isaddTaskFieldEmpty;
    public boolean isaddNoteFieldEmpty;

    private JPanel MustEnterTaskPanel;
    public JLabel MustEnterTaskLabel;

    private final static String[] taskTableHeaderArray = {"", "Task", "Notes", "Created"};
    public DefaultTableModel taskTableModel;
    public JTable taskTable;
    private JScrollPane taskTableScrollPane;

    private JButton deleteTasksButton;

    public ToDoListView() 
    {
        initTopComponents();
        initCenterComponents();
        initBottomComponents();

        initPanels();
        initFrame();
    }

    private void initTopComponents()
    {
        headingLabel = new JLabel("✎🗒 To-do List");
        headingLabel.setFont(new Font("SansSerif", Font.BOLD, 30));

        String currentDate = (new SimpleDateFormat("MMM d, ''yy")).format(new Date());
        dateLabel = new JLabel("Today is " + currentDate);
        dateLabel.setFont(new Font("SansSerif", Font.ITALIC, 18));
        dateLabel.setForeground(Color.GRAY);

        isaddTaskFieldEmpty = true;
        addTaskField = new JTextField(20);
        addTaskField.setText("Add a new Task ..."); 
        addTaskField.setFont(new Font("SansSerif", Font.ITALIC, 14));
        addTaskField.setForeground(Color.LIGHT_GRAY); 
        addTaskField.setMaximumSize(new Dimension(30,40));
        addTaskField.setMargin(new Insets(0,5,0,0));
        addTaskField.addFocusListener(new AddFieldController(this));

        isaddNoteFieldEmpty = true;
        addNoteField = new JTextField(20);
        addNoteField.setText("Notes ..."); 
        addNoteField.setFont(new Font("SansSerif", Font.ITALIC, 14));
        addNoteField.setForeground(Color.LIGHT_GRAY);
        addNoteField.setMargin(new Insets(0,5,0,0));
        addNoteField.addFocusListener(new AddFieldController(this));
        
        addTaskButton = new JButton("Add Task");
        addTaskButton.setBackground(Color.WHITE);
        addTaskButton.setFocusPainted(false);
        addTaskButton.addActionListener(new AddTaskController(this));

        MustEnterTaskLabel = new JLabel();
        MustEnterTaskLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        MustEnterTaskLabel.setForeground(Color.RED);
        MustEnterTaskLabel.setBorder(new EmptyBorder(3,0,8,0));
        
    }

    private void initCenterComponents()
    {
        taskTableModel = new DefaultTableModel(taskTableHeaderArray, 0) 
        {
            public Class<?> getColumnClass(int columnIndex) 
            {
                return getValueAt(0, columnIndex).getClass();
            }
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) 
            {
                return (columnIndex == 0 ? true : false);
            }
        };
        fetchTaskTableRows();

        taskTable = new JTable(taskTableModel) 
        {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columnIndex) 
            {
                Component currentComponent = super.prepareRenderer(renderer, rowIndex, columnIndex);

                Map currentAttributes = currentComponent.getFont().getAttributes();
                currentAttributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
                
                if (getValueAt(rowIndex, 0) == Boolean.TRUE)
                {
                    currentComponent.setFont(new Font(currentAttributes));
                    currentComponent.setForeground(Color.LIGHT_GRAY);
                }
                else {
                    currentComponent.setForeground(Color.BLACK);
                }
                if (currentComponent instanceof JComponent) ((JComponent) currentComponent).setBorder(new EmptyBorder(0, 0, 0, 0));

                if (columnIndex == 0) setFont(new Font("SansSerif", Font.BOLD, 14));
                else if (columnIndex == 1) setFont(new Font("SansSerif", Font.ITALIC, 14));

                return currentComponent;
            }
        };

        taskTable.setShowGrid(false);
        taskTable.setRowHeight(30);
        taskTable.getSelectionModel().clearSelection();
        taskTable.setCellSelectionEnabled(false);
        taskTable.getTableHeader().setReorderingAllowed(false);

        taskTable.getColumnModel().getColumn(0).setMinWidth(30);
        taskTable.getColumnModel().getColumn(0).setMaxWidth(30);;

        taskTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        taskTable.getColumnModel().getColumn(1).setMaxWidth(500);
        taskTable.getColumnModel().getColumn(1).setMinWidth(175);

        taskTable.getColumnModel().getColumn(3).setMinWidth(100);
        taskTable.getColumnModel().getColumn(3).setMaxWidth(100);

        ((DefaultTableCellRenderer)taskTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        taskTableScrollPane = new JScrollPane(taskTable);
        taskTableScrollPane.getViewport().setBackground(Color.WHITE);
        taskTableScrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
    }

    private void initBottomComponents()
    {
        deleteTasksButton = new JButton("Delete Selected Task(s)");
        deleteTasksButton.setBackground(Color.WHITE);
        deleteTasksButton.setFocusPainted(false);
        deleteTasksButton.addActionListener(new DeleteTaskController(this));
    }

    private void initPanels()
    {
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        topPanel.setBackground(Color.WHITE);

        topPanel.add(headingLabel);
        topPanel.add(dateLabel);

        addPanel = new JPanel();
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.X_AXIS));
        addPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10));
        addPanel.setBackground(Color.WHITE);

        addPanel.add(addTaskField);
        addPanel.add(Box.createHorizontalStrut(2));
        addPanel.add(addNoteField);
        addPanel.add(Box.createHorizontalStrut(10));
        addPanel.add(addTaskButton);
        addPanel.add(Box.createHorizontalGlue());

        MustEnterTaskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        MustEnterTaskPanel.setBackground(Color.WHITE);
        MustEnterTaskPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10));
        
        MustEnterTaskPanel.add(MustEnterTaskLabel);

        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);

        centerPanel.add(addPanel);
        centerPanel.add(MustEnterTaskPanel);
        centerPanel.add(taskTableScrollPane);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
        bottomPanel.setBorder(new EmptyBorder(10, 0, 0, 1));
        bottomPanel.setBackground(Color.WHITE);

        bottomPanel.add(deleteTasksButton);
        bottomPanel.add(Box.createHorizontalGlue());
    }

    private void initFrame()
    {
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setTitle(appTitle);
        getRootPane().setBorder(new EmptyBorder(15, 20, 15, 15));
        setBackground(Color.WHITE);
        setMinimumSize(new Dimension(500, 600));
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        requestFocusInWindow();
    }

    private void fetchTaskTableRows()
    {
        new ToDoListModel(taskTableModel).fetchTasks();
    }
}