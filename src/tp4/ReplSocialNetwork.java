package tp4;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Sophia on 30/10/2016.
 */
public class ReplSocialNetwork {


    public static void main(String args[]) throws Exception {

        // Cette instruction permet de créer une Table avec deux columns_families.
        // Je fais appel à la classe HbaseConnector qui comporte la méthode de création de classe sur Hbase
        //Je fais appel à la classe Conf qui comporte la configuration de ma table (voir classe Conf)
        HBaseConnector.createTableHBase(Conf.TableName_HBase, Conf.Family_Info, Conf.Family_friends);

        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(r);

        // Boucle du REPL
        String cmd = "help";
        while (!cmd.equals("exit")) {
            System.out.print("$REPL_Hbase>");  // Prompt
            cmd = br.readLine();               // Correspond au Read du REPL
            eval(cmd, br);  // Appel à la méthode eval developpée plus bas, correspond au Eval du REPL
        }
    }

    /**
     *   Cette methode java permet d'implementer l'Evaluation des commandes saisies.
     *   J'ai choisi les 4 commandes basiques CRUD (create, read,update et delete)
     *   En fonction de la commande saisie par l'utilisateur, on rentre dans l'un des cas suivants du switch
     */
    public static void eval(String cmd, BufferedReader br ) throws Exception {

        switch (cmd) {
            case "create user": {
                System.out.println("Veuillez saisir votre login/pseudo : ");
                String rowKey_login = br.readLine();

                System.out.println("Veuillez saisir votre Mail : ");
                String info_mail = br.readLine();

                System.out.println("Veuillez saisir votre Tel : ");
                String info_tel = br.readLine();

                System.out.println("Veuillez saisir votre Adresse : ");
                String info_adresse = br.readLine();

                System.out.println("Veuillez saisir votre Age : ");
                String info_age = br.readLine();

                System.out.println("Veuillez saisir votre Sexe : ");
                String info_sexe = br.readLine();


                System.out.println("Veuillez saisir votre bff (Best Friend Forever) : ");
                String friends_bff = br.readLine();

                System.out.println("Veuillez saisir un ami : ");
                String friends_other = br.readLine();

                //Appel a la methode addRecord pour inserer les infos dans la table

                HBaseConnector.addRecord(Conf.TableName_HBase, rowKey_login, Conf.Family_Info, Conf.Qualifier_mail, info_mail);
                HBaseConnector.addRecord(Conf.TableName_HBase, rowKey_login, Conf.Family_Info, Conf.Qualifier_tel,  info_tel);
                HBaseConnector.addRecord(Conf.TableName_HBase, rowKey_login, Conf.Family_Info, Conf.Qualifier_adresse, info_adresse);
                HBaseConnector.addRecord(Conf.TableName_HBase, rowKey_login, Conf.Family_Info, Conf.Qualifier_age,  info_age);
                HBaseConnector.addRecord(Conf.TableName_HBase, rowKey_login, Conf.Family_Info, Conf.Qualifier_sexe, info_sexe);

                HBaseConnector.addRecord(Conf.TableName_HBase, rowKey_login, Conf.Family_friends, Conf.Qualifier_bff,    friends_bff);
                HBaseConnector.addRecord(Conf.TableName_HBase, rowKey_login, Conf.Family_friends, Conf.Qualifier_others, friends_other);
            }
            break;

            case "read user": {
                System.out.println("Veuillez saisir le pseudo a afficher : ");
                String rowKey_login = br.readLine();

                //Appel a la methode getOneRecord pour afficher un row
                HBaseConnector.getOneRecord (Conf.TableName_HBase, rowKey_login);
            }
            break;

            case "update user": {

                String qualifier_cmd ="";

                System.out.println("Pour modifier vos données personnelles saisir d'abord votre login/pseudo ");
                String rowKey_login = br.readLine();

                while (!qualifier_cmd.equals("endupdate")) {

                    //Affiche d'abord les données de l'utilisateur avant que ce dernier Update ses données. Ainsi, il voit  exactement ce qu'il veut modifier
                    HBaseConnector.getOneRecord(Conf.TableName_HBase, rowKey_login);

                    System.out.println("Pour modifier votre mail veuillez saisir la commande mail (pareillement avec tel , adresse , age , sexe , bff , others) ");
                    System.out.println("Pour terminer votre Update tapez la commande endupdate");

                    qualifier_cmd = br.readLine();

                    System.out.println("Saisir la nouvelle valeur de votre : " + qualifier_cmd);
                    String value_qualifier = br.readLine();

                    if (value_qualifier.equals("bff") || value_qualifier.equals("others")) {
                        HBaseConnector.addRecord(Conf.TableName_HBase, rowKey_login, Conf.Family_friends, qualifier_cmd, value_qualifier);
                    } else {
                        HBaseConnector.addRecord(Conf.TableName_HBase, rowKey_login, Conf.Family_Info, qualifier_cmd, value_qualifier);
                    }
                }
            }
            break;

            case "delete user": {
                System.out.println("Veuillez saisir le pseudo du compte a supprimer : ");
                String delete_pseudo = br.readLine();

                //Appel a la methode delete
                HBaseConnector.delRecord(Conf.TableName_HBase, delete_pseudo);
            }
            break;

            case "help" :{
                System.out.println("Liste des commandes : create user, read user, update user, delete user ");
                System.out.println("Pour sortir du REPL taper la commande exit");
            }
            break;

            default:{
                System.out.println("Commande invalide pour plus d'infos utiliser la commande help");
            }
        }
    }
}
