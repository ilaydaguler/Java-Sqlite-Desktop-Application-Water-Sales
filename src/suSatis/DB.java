
package suSatis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import props.Musteriler;

public class DB {
    
    private final String driver = "org.sqlite.JDBC";
    private final String url = "jdbc:sqlite:db/su_satis.db";
    
    private Connection conn=null;
    private PreparedStatement pre=null;
    
    public static Musteriler musteriler= new Musteriler();
 
    
    public DB(){
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url);
            System.out.println("Connection Success");
            
        }catch (Exception e ){
            System.err.println("Connection Error: " + e);
        }
    }
    
    
    public int musteriInsert(String adi, String soyadi, String telefon,String adres){
        int status = 0;
        try {
            String sql= "insert into musteriler values (null,?,?,?,? ) ";
            pre = conn.prepareStatement(sql);
            pre.setString(1, adi);
            pre.setString(2, soyadi);
            pre.setString(3,telefon);
            pre.setString(4, adres);
            status = pre.executeUpdate();
            
        } catch (Exception e) {
            System.err.println("musteri insert error " + e);
            if (e.toString().contains("SQLITE_CONSTRAINT_UNIQUE")) {
                status =-1;
                
            }
        }
        
        return status;
    }
    
    public DefaultTableModel musteriSearch(String adi, String soyadi){
        DefaultTableModel dtm= new DefaultTableModel();
        //add column 
        dtm.addColumn("id");
        dtm.addColumn("Adı");
        dtm.addColumn("soyadı");
        dtm.addColumn("Telefon");
        dtm.addColumn("Adres");
        
        //add rows
        try {
            String sql = "select * from musteriler  where adi = ? and soyadi = ? ";
            pre = conn.prepareStatement(sql);
            pre.setString(1, adi);
            pre.setString(2, soyadi);
            ResultSet rs= pre.executeQuery();
            while (rs.next()) {
                int id= rs.getInt("id");
                String madi=rs.getString("adi");
                String msoyadi=rs.getString("soyadi");
                String telefon=rs.getString("telefon");
                String adres=rs.getString("adres");
                
                Object[] row = {id ,madi, msoyadi, telefon, adres};
                dtm.addRow(row);
            }
                    
        } catch (Exception e) {
            System.err.println("arama error" + e);
        }
        
        return dtm;
    }
        
    //msuteri list
    public DefaultTableModel allMusteri(){
        DefaultTableModel dtm= new DefaultTableModel();
        //add column 
        dtm.addColumn("id");
        dtm.addColumn("Adı");
        dtm.addColumn("Soyadı");
        dtm.addColumn("Telefon");
        dtm.addColumn("Adres");
        
        //add rows
        
        try {
            String sql = "select * from musteriler";
            pre = conn.prepareStatement(sql);
            ResultSet rs= pre.executeQuery();
            while (rs.next()) {
                int id= rs.getInt("id");
                String adi=rs.getString("adi");
                String soyadi=rs.getString("soyadi");
                String telefon=rs.getString("telefon");
                String adres=rs.getString("adres");
                
                Object[] row = {id, adi, soyadi,telefon,adres};
                dtm.addRow(row);
            }
                    
        } catch (Exception e) {
            System.err.println("allMusteri error" + e);
        }
        
        return dtm;
    }
    public int musteriUpdate(String adi,  String soyadi,String telefon,String adres,int id){
        int status=0;
        
        try {
            String sql = "update musteriler set adi = ?, soyadi = ?, telefon = ?, adres=? where id = ? ";
            pre = conn.prepareStatement(sql);
            pre.setString(1,adi);
            pre.setString(2, soyadi);
            pre.setString(3, telefon);
            pre.setString(4, adres);
            pre.setInt(5, id);
            status = pre.executeUpdate();
            
                    
        } catch (Exception e) {
            System.err.println("musteriUpdate error " + e);
             
        }
        return status;
    }
    
    public int musteriDelete (int id){
        int status = 0;
        try {
            String sql = "delete from musteriler where id = ?";
            pre= conn.prepareStatement(sql);
            pre.setInt(1, id);
            status = pre.executeUpdate();
        } catch (Exception e) {
            System.err.println("musteriDelete error" + e);
        }
        return status;
    }
    
        public int siparisInsert(String adi, String soyadi,String adres,String tutar){
        int status = 0;
        try {
            String sql= "insert into siparisler values (null,?,?,'Hazırlanıyor',?,? ) ";
            pre = conn.prepareStatement(sql);
            pre.setString(1, adi);
            pre.setString(2, soyadi);
            pre.setString(3,adres);
           
            pre.setString(4, tutar);
            status = pre.executeUpdate();
            
        } catch (Exception e) {
            System.err.println("siparis insert error " + e);
            
        }
        
        return status;
    }
    public DefaultTableModel allSiparis(){
        DefaultTableModel dtm= new DefaultTableModel();
        //add column 
        dtm.addColumn("sid");
        dtm.addColumn("Adı");
        dtm.addColumn("Soyadı");
        dtm.addColumn("Durum");
        dtm.addColumn("Adres");
        dtm.addColumn("Tutar");
        
        //add rows
        
        try {
            String sql = "select * from siparisler";
            pre = conn.prepareStatement(sql);
            ResultSet rs= pre.executeQuery();
            while (rs.next()) {
                int sid= rs.getInt("sid");
                String adi=rs.getString("adi");
                String soyadi=rs.getString("soyadi");
                String durum=rs.getString("durum");
                String adres=rs.getString("adres");
                String tutar=rs.getString("tutar");
                
                Object[] row = {sid, adi, soyadi,durum,adres,tutar};
                dtm.addRow(row);
            }
                    
        } catch (Exception e) {
            System.err.println("allMusteri error" + e);
        }
        
        return dtm;
    }
    public int siparisYolaCikti(int sid){
        int status=0;
        
        try {
            String sql = "update siparisler set durum = 'Yola çıktı'where sid = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, sid);
            status = pre.executeUpdate();
            
                    
        } catch (Exception e) {
            System.err.println("yolacikti error " + e);
             
        }
        return status;
        
    }
    public int siparisTeslimEdildi(int sid){
        int status=0;
        
        try {
            String sql = "update siparisler set durum = 'Teslim Edildi'where sid = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, sid);
            status = pre.executeUpdate();
            
                    
        } catch (Exception e) {
            System.err.println("teslimedildi error " + e);
             
        }
        return status;
        
    }
    public int siparisDelete (int sid){
        int status = 0;
        try {
            String sql = "delete from siparisler where sid = ?";
            pre= conn.prepareStatement(sql);
            pre.setInt(1, sid);
            status = pre.executeUpdate();
        } catch (Exception e) {
            System.err.println("siparisDelete error" + e);
        }
        return status;
    }
    
    public int allSiparisDelete (){
        int status=0;
        try {
            String sql = " delete from siparisler";
            pre=conn.prepareStatement(sql);
            status = pre.executeUpdate();
        } catch (Exception e) {
            System.err.println("siparisallDelete error" + e);
        }
        return status;
    }
    
    public DefaultTableModel siparisFilter(){
        DefaultTableModel dtm= new DefaultTableModel();
        //add column 
        dtm.addColumn("id");
        dtm.addColumn("Adı");
        dtm.addColumn("soyadı");
        dtm.addColumn("Durum");
        dtm.addColumn("Adres");
        dtm.addColumn("Tutar");
        
        //add rows
        try {
            String sql = "select * from siparisler where durum = 'Hazırlanıyor' ";
            pre = conn.prepareStatement(sql);
            ResultSet rs= pre.executeQuery();
            while (rs.next()) {
                int sid= rs.getInt("sid");
                String sadi=rs.getString("adi");
                String ssoyadi=rs.getString("soyadi");
                String durum=rs.getString("durum");
                String adres=rs.getString("adres");
                String tutar=rs.getString("tutar");
                
                Object[] row = {sid ,sadi, ssoyadi, durum, adres,tutar};
                dtm.addRow(row);
            }
                    
        } catch (Exception e) {
            System.err.println("filter error" + e);
        }
        
        return dtm;
    }

    public void close(){
        try {
            if(conn != null && !conn.isClosed()){
                conn.close();
            }
            if(pre != null){
                pre.close();
            }
        } catch (Exception e) {
            System.err.println("Close Error: ");
        }
    }
    
}
