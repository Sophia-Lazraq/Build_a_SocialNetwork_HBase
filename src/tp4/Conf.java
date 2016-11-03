package tp4;

/**
 * Structure de la base NoSQL HBase
 *
 *
 * TableName_HBase  = "SocialNetwork_Sophia"
 *
 * RowKey = login  (name/pseudo doit être unique parce que c'est utilisé comme clé primaire)
 *
 * Family = "info"
 *                   Qualifier = "mail"
 *                   Qualifier = "tel"
 *                   Qualifier = "adresse"
 *                   Qualifier = "age"
 *                   Qualifier = "sexe"
 *
 * Family = "friends"
 *                   Qualifier = "bff"
 *                   Qualifier = "others"

 */

public class Conf {

     public static final String TableName_HBase = "SocialNetwork_Sophia";

     public static final String  Family_Info = "info";
     public static final String  Qualifier_mail="mail";
     public static final String  Qualifier_tel="tel";
     public static final String  Qualifier_adresse="adresse";
     public static final String  Qualifier_age="age";
     public static final String  Qualifier_sexe="sexe";

     public static final String  Family_friends = "friends";
     public static final String  Qualifier_bff="bff";
     public static final String  Qualifier_others="others";

}
