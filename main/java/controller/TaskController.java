/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Task;
import util.ConnectionFactory;

/**
 * criação de objetos criação de metodos para inserção,
 * modificação, atualização e exclusão de informaçoes no banco de dados
 */
public class TaskController {

    public void save(Task task) {
        String sql = "INSERT INTO tasks(idProject,"
                + "name,"
      
                + "description,"
                + "completed,"
                + "notes,"
                + "deadline,"
                + "createdAt,"
                + "updatedAt)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        // "setar" os values do insert into acima
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new java.sql.Date(task.getDeadline().getTime()));
            statement.setDate(7, new java.sql.Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new java.sql.Date(task.getUpdatedAt().getTime()));

            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar tarefa " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public void update(Task task) {
        String sql = "UPDATE tasks SET idProject = ?,"
                + "name = ?,"
                + "description = ?,"
                + "completed = ?,"
                + "notes = ?,"
                + "deadline = ?,"
                + "createdAt = ?,"
                + "updatedAt = ?"
                + "WHERE ID = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //estabelecendo conexao com o banco de dados
            connection = ConnectionFactory.getConnection();
            //preparando a query
            statement = connection.prepareStatement(sql);
            //setando os valores do statement
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new java.sql.Date(task.getDeadline().getTime()));
            statement.setDate(7, new java.sql.Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new java.sql.Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            //executando a query
            
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public void removeById(int taskId) {

        String sql = "DELETE FROM tasks WHERE ID = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        //tudo o que for aberto tem que ser fechado. NEste caso, cennection e statement
        try {
            // criacado da conexao om o banco de dados
            connection = ConnectionFactory.getConnection();
            //preparando a query
            statement = connection.prepareStatement(sql);//objeto que ajuda a preparar o comando que vai ser executado no sql
            //setando os valores
            statement.setInt(1, taskId);
            //executando a query
            statement.execute();
        } catch (SQLException ex) {//qual o tipo de erro sera tratado
            throw new RuntimeException("Erro ao deletar a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public List<Task> getAll(int idProject) {
        String sql = "SELECT * FROM tasks where idProject = ?";

        List<Task> tasks = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;//classe que representa o retorno, guarda a resposta do select do banco de dados

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, idProject);

            resultSet = statement.executeQuery();

            //Enquanto existir dados no banco de dados, faça
            while (resultSet.next()) {

                Task task = new Task();
                task.setId(resultSet.getInt("ID"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setNotes(resultSet.getString("notes"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));

                //Adiciono o contato recuperado, a lista de contatos
                tasks.add(task);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar as tarefas", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        // listas de tarefas que for criada e carregada do banco de dados sera retornada
        return tasks;
    }
}