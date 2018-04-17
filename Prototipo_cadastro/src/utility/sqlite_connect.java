package utility;

import java.sql.*;
import java.util.*;

/*
*TO DO LIST
* CHANGE THE STATIC METHODS TO WORK NORMALLY - DONE
* MAKE THIS CLASS A SINGLETON
*/
 
public class sqlite_connect {
    
    public static final String JDBC = "jdbc:sqlite:";
    
    public sqlite_connect(){

    }
    
    public void createDB(String name){
        
        try(Connection conn = DriverManager.getConnection(JDBC + name)){
            String c = "sqlite3 " + name;
            PreparedStatement stmt = conn.prepareStatement(c);
            stmt.execute(c);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
       
    }
    
    public void create_table(String DBname, String TableName, String... parameters){

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DBname)){
            
                //String create = "CREATE TABLE " + TableName + " (";
                
                StringBuilder create = new StringBuilder();
                create.append("CREATE TABLE IF NOT EXISTS ").append(TableName).append(" (");
                
                int i = 0;
                while(i < parameters.length){
                    if(i != (parameters.length - 1)){
                        //create += parameters[i] + ", ";
                        create.append(parameters[i]).append(", ");
                    }else{
                        //create += parameters[i] + ");";
                        create.append(parameters[i]).append(");");
                    }
                    i++;
                }

                System.out.println(create);
                
                PreparedStatement stmt = conn.prepareStatement(create.toString());
                stmt.execute();
                System.out.println("Tabela Criada");
                
                conn.close();

            }catch (SQLException e){
                System.out.println(e.getMessage());
            }finally{
                //Verifica se a conexão está fechada
                //conn.close();
        }
    }
    
    public void alter_table(String DBname, String table_name, String where[], String parameters[]){
        
        //try(Connection conn = DriverManager.getConnection(JDBC + DBname)){
            
            String alter = "UPDATE " + table_name + "SET " ;
            String n = "UPADATE dados SET nome = nome";
        //}
    }
    
    //need to get the values 
    public void insert_table(String DBname, String table, String... parameters){
        
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DBname)){  
                
                //String insert = "INSERT INTO " + table + " VALUES (";
                
                StringBuilder insert = new StringBuilder();
                insert.append("INSERT INTO ").append(table).append(" VALUES (");
                
                int i = 0;
                while(i < parameters.length){
                    if(i != (parameters.length - 1)){
                        //insert += "'" + parameters[i] + "'" + ", ";
                        insert.append("'").append(parameters[i]).append("'").append(", ");
                    }else{
                        //insert += "'" + parameters[i] + "'" + ");";
                        insert.append("'").append(parameters[i]).append("'").append(");");
                    }
                    i++;
                }
                
                PreparedStatement stmt = conn.prepareStatement(insert.toString());
                stmt.executeUpdate();
                
                System.out.println("Valores Inseridos");
            }catch(SQLException e){
                System.out.println("Erro: " + e);
            }
    }

    //Need improvement. It needs to print the results in a generic way
    public void select_from(String DBname, String table, String... parameters){
        
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DBname)){
                /*
                *A classe String é imutável, ou seja, toda vez que é invocada cria outro objeto
                *Os chars internos não mudam
                
                *Já o StringBuilder pode mudar livremente seu tamanho e chars
                *quando o método .append() é chamado ele altera o vetor interno de chars
                */
                
                StringBuilder ler = new StringBuilder();
                ler.append("SELECT ");
                
                for(int i = 0; i < parameters.length; i++){
                    if(i != (parameters.length - 1)){
                        ler.append(parameters[i]).append(", ");
                    }else{
                        ler.append(parameters[i]).append(" ");
                    }
                }
                ler.append("FROM ").append(table).append(";");
                
                //PreparedStatement stmt = conn.prepareStatement(ler.toString());
                //ResultSet rs = stmt.executeQuery(ler.toString());
                
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(ler.toString());
                
                
                List<String> columnNames = new ArrayList<>();
                
                //ResultSetMetaData pega as informações sobre tipos e propriedades das colunas in a ReaultSet object
                ResultSetMetaData rsmd = rs.getMetaData();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    columnNames.add(rsmd.getColumnLabel(i));
                    //columnNames.add(rsmd.getCatalogName(i));
                }
                
                int rowIndex = 0;
                while(rs.next()){
                    rowIndex++;
                    //Pegas os dados como objetos
                    List<Object> rowData = new ArrayList<>();
                    for(int i = 1; i <= rsmd.getColumnCount(); i++){
                        rowData.add(rs.getObject(i));
                    } 
                    
                     System.out.println("Row " + rowIndex);
                     for(int colIndex = 0; colIndex < rsmd.getColumnCount(); colIndex++){
                         String objType = null;
                         String objContent = "";
                         Object columnObject = rowData.get(colIndex);
                         if(columnObject != null){
                             objContent = columnObject.toString() + " ";
                             objType = columnObject.getClass().getName();
                         }
                         //System.out.printf("  %s: %s(%s)%n",
                         //columnNames.get(colIndex), objContent, objType);
                         printResultSet("     " + columnNames.get(colIndex) + " " + objContent + objType);
                     }    
                }
                 
                //Verifica se a conexão está fechada
                if(conn.isClosed()) {
                	conn.close();
                	System.out.println("Conexão fechada");
                }
                   
            }catch(Exception e){
                System.out.println("Erro: " + e);
            }
    }
    
    
    private <T> void printResultSet(T... t){
        
        for(int i = 0; i <= t.length; i++){
            
            if(t[i] == null){
                //do nothing
            }else{
                System.out.println(t[i]);
            }
        }
           
        /*
        for(T element : t){
            if(element == null){
                //do nothing
            }else{
                System.out.println(element);
            }  
        }
        */
    }   
    
    
}