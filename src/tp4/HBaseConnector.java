package tp4;
/**
 * Ce code est un copier/coller d'Internet, il comporte les méthodes
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;


public class HBaseConnector {

    private static Configuration conf = null;

    /**
     * Initialization
     */
    static {
        conf = HBaseConfiguration.create();
    }

    /**
     * Create a table
     */
    public static void creatTable(String tableName, String[] familys) {

        try {
            HBaseAdmin admin = new HBaseAdmin(conf);
            if (admin.tableExists(tableName)) {
                System.out.println("table already exists!");
            } else {
                HTableDescriptor tableDesc = new HTableDescriptor(tableName);
                for (int i = 0; i < familys.length; i++) {
                    tableDesc.addFamily(new HColumnDescriptor(familys[i]));
                }
                admin.createTable(tableDesc);
                System.out.println("create table " + tableName + " ok.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * Une autre methode pour créer une Table HBase
     */
    public static void createTableHBase(String tablename, String family1, String family2) {

        String[] familys = {family1, family2};

        HBaseConnector.creatTable(tablename, familys);

    }

    /**
     * Delete a table
     */
    public static void deleteTable(String tableName) {
        try {
            HBaseAdmin admin = new HBaseAdmin(conf);
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
            System.out.println("delete table " + tableName + " ok.");
        } catch (MasterNotRunningException e) {
            e.printStackTrace();
        } catch (ZooKeeperConnectionException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Put (or insert) a row
     */
    public static void addRecord(String tableName, String rowKey, String family, String qualifier, String value) {
        try {
            HTable table = new HTable(conf, tableName);
            Put put = new Put(Bytes.toBytes(rowKey));
            put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
            table.put(put);
            System.out.println("insert recored " + rowKey + " to table " + tableName + " ok.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete a row
     */
    public static void delRecord(String tableName, String rowKey) {

        try {
            HTable table = new HTable(conf, tableName);
            List<Delete> list = new ArrayList<Delete>();
            Delete del = new Delete(rowKey.getBytes());
            list.add(del);
            table.delete(list);
            System.out.println("del recored " + rowKey + " ok.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * Get a row
     */
    public static void getOneRecord(String tableName, String rowKey) {

        try {
            HTable table = new HTable(conf, tableName);
            Get get = new Get(rowKey.getBytes());
            Result rs = table.get(get);
            for (KeyValue kv : rs.raw()) {
                System.out.print(new String(kv.getRow()) + " ");
                System.out.print(new String(kv.getFamily()) + ":");
                System.out.print(new String(kv.getQualifier()) + " ");
                System.out.print(kv.getTimestamp() + " ");
                System.out.println(new String(kv.getValue()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * Scan (or list) a table
     */
    public static void getAllRecord(String tableName) {
        try {
            HTable table = new HTable(conf, tableName);
            Scan s = new Scan();
            ResultScanner ss = table.getScanner(s);
            for (Result r : ss) {
                for (KeyValue kv : r.raw()) {
                    System.out.print(new String(kv.getRow()) + " ");
                    System.out.print(new String(kv.getFamily()) + ":");
                    System.out.print(new String(kv.getQualifier()) + " ");
                    System.out.print(kv.getTimestamp() + " ");
                    System.out.println(new String(kv.getValue()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

