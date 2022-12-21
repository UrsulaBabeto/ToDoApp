package util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Task;

/**
 *
 */
public class TaskTableModel extends AbstractTableModel {

    String[] columns = {"Nome", "Descrição", "Prazo", "Tarefa Concluida", "Editar", "Excluir"};
    List<Task> tasks = new ArrayList<>();

    @Override
    public int getRowCount() {
        return tasks.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns[columnIndex];
    }

    //metodo para permitir editação linha e coluna
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 3; //se a comparação retornar vdd permite a edição
    }
    
    @Override
    //retorna qual o tipo de classe que esta em determinada columa(string, boolena, numero etc
    //por padrão ele retorna String, herança do pai: AbstractTableModel
    public Class<?> getColumnClass(int columnIndex){
        if(tasks.isEmpty()){
        return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return tasks.get(rowIndex).getName();
            case 1:
                return tasks.get(rowIndex).getDescription();
            case 2:
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                return dateFormat.format(tasks.get(rowIndex).getDeadline());
            case 3:
                return tasks.get(rowIndex).isIsCompleted();
            case 4:
                return "";
            case 5:
                return "";
            default:
                return "Dados não encontrados";
        }
    }
    
    @Override
    //converte os dados boolean para object e volta para boolean 
    public void setValueAt(Object value, int rowIndex, int columnIndex){
        tasks.get(rowIndex).setIsCompleted((boolean) value);
    }

    public void addRow(Task task) {
        this.tasks.add(task);
    }
    
    public String[] getColumns() {
        return columns;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    
     public void setColumns(String[] columns) {
        this.columns = columns;
    }
    

}
