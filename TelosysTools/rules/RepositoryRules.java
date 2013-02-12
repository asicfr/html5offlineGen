
import java.util.StringTokenizer;

/**
 * Telosys Data Access Layer Tools
 * 
 * Example of specific rules for repository generation
 * 
 * @author Laurent GUERIN
 * 
 */

public class RepositoryRules
{
    //------------------------------------------------------------------------------
    // Private utilities 
    //------------------------------------------------------------------------------
    /**
     * Returns true if the first character is UpperCase
     * @param s
     * @return
     */
    private boolean isFirstCharUpperCase(String s)
    {
        if (s.substring(0, 1).toUpperCase().equals(s.substring(0, 1)))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //------------------------------------------------------------------------------
    /**
     * Transform the given token with only the first char in Upper Case  
     * ie : "aBcDeFg" --> "Abcdefg"
     * @param s 
     * @return transformed string
     */
    private String transformToken(String s)
    {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    //------------------------------------------------------------------------------
    /**
     * Transform the given string using "_" as word separator  
     * ie : "ORDER_ITEM" --> "OrderItem"
     * @param sName
     * @return transformed string
     */
    private String transform(String sName)
    {
        if (sName != null)
        {
            String sClassName = "";
            String sToken = null;
            String s = sName.trim(); // to be secure
            StringTokenizer st = new StringTokenizer(s, "_");
            while (st.hasMoreTokens())
            {
                sToken = st.nextToken();
                sClassName = sClassName + transformToken( sToken ) ;
            }
            return sClassName;
        }
        else
        {
            return null;
        }
    }

    //------------------------------------------------------------------------------
    // Informations about the current class
    //------------------------------------------------------------------------------
    public String about()
    {
        String sTable = "AAA_BBB_CCC" ;
        String sAttrib = "AAA_BBB_CCC" ;
        return ("Specific rules for the DEMO project.\n"
                + "Transformations : \n" 
                + ". table : '" + sTable + "' \n"
                + "   -> VO '" + getClassNameForVO(sTable) + "' \n"
                + "   -> VOList '" + getClassNameForVOList(sTable) + "' \n"
                + "   -> DAO '" + getClassNameForDAO(sTable) + "' \n"
                + ". attribute : '" + sAttrib + "' -> '" + getAttributeName(sAttrib, "", 0) + "' \n"
                );
    }

    //==============================================================================
    // Default generation transformations
    //==============================================================================

    //------------------------------------------------------------------------------
    public String getAttributeName(String sColumnName, String sColumnTypeName, int iColumnTypeCode)
    {
        //--- Colum name with the first char in UpperCase
        return transform(sColumnName);
    }

    //------------------------------------------------------------------------------
    /**
     * Returns the Java attribute type according with the given database column type  
     * @param sColumnType : Database column type name
     * @param iColumnTypeCode : JDBC Type (cf "java.sql.Types" )
     * @return 
     */
    public String getAttributeType(String sColumnType, int iColumnTypeCode)
    {
        if (sColumnType.equalsIgnoreCase("varchar"))
        {
            return "String";
        }
        else if (sColumnType.equalsIgnoreCase("int"))
        {
            return "int";
        }
        else
        {
            return "String";
        }
    }

    //------------------------------------------------------------------------------
    /**
     * Returns the Java class name for the "VO" class
     * @param sTableName : Database table name
     * @return 
     */
    public String getClassNameForVO(String sTableName)
    {
        return transform(sTableName) + "VO";
    }

    //------------------------------------------------------------------------------
    /**
     * Returns the Java class name for the "VOList" class
     * @param sTableName : Database table name
     * @return 
     */
    public String getClassNameForVOList(String sTableName)
    {
        return transform(sTableName) + "VOList";
    }

    //------------------------------------------------------------------------------
    /**
     * Returns the Java class name for the "DAO" class
     * @param sTableName : Database table name
     * @return 
     */
    public String getClassNameForDAO(String sTableName)
    {
        return transform(sTableName) + "DAO";
    }

    //------------------------------------------------------------------------------
    /**
     * Returns the Java class name for the "DAO Test" class
     * @param sTableName : Database table name
     * @return 
     */
    public String getClassNameForTestDAO(String sTableName)
    {
        return "Test" + transform(sTableName) + "DAO";
    }

    //------------------------------------------------------------------------------
    /**
     * Returns the Java class name for the "DAO List Test" class
     * @param sTableName : Database table name
     * @return 
     */
    public String getClassNameForTestDAOList(String sTableName)
    {
        return "Test" + transform(sTableName) + "DAOList";
    }

    //==============================================================================
    // Input checking
    //==============================================================================
    //------------------------------------------------------------------------------
    // Java field types authorized ( combo box initialization )
    //------------------------------------------------------------------------------
    public String[] getFieldTypes()
    {
        return new String[] { "String", "int", "double", "Date", "boolean" };
    }

    //------------------------------------------------------------------------------
    // Project uses or doesn't use boolean types
    //------------------------------------------------------------------------------
    public boolean storeBoolean()
    {
        return false; // Boolean not used
        //return true ; // Boolean is used
    }

    //------------------------------------------------------------------------------
    // Attribute name checking
    //------------------------------------------------------------------------------
    public boolean checkAttributeName(String sBaseName, String sJavaName)
    {
        return isFirstCharUpperCase(sJavaName);
    }

    //------------------------------------------------------------------------------
    // Attribute type checking
    //------------------------------------------------------------------------------
    public boolean checkAttributeType(int iJDBCType, String sJavaType)
    {
        return false ;
    }

    //------------------------------------------------------------------------------
    // Class name checking
    //------------------------------------------------------------------------------
    public boolean checkClassNameForVO(String sName)
    {
        return isFirstCharUpperCase(sName);
    }

    //------------------------------------------------------------------------------
    public boolean checkClassNameForVOList(String sName)
    {
        return isFirstCharUpperCase(sName);
    }

    //------------------------------------------------------------------------------
    public boolean checkClassNameForDAO(String sName)
    {
        return isFirstCharUpperCase(sName);
    }

    //------------------------------------------------------------------------------
    public boolean checkClassNameForTestDAO(String sName)
    {
        return isFirstCharUpperCase(sName);
    }

    //------------------------------------------------------------------------------
    public boolean checkClassNameForTestDAOList(String sName)
    {
        return isFirstCharUpperCase(sName);
    }
    
    //------------------------------------------------------------------------------
    // For unit tests
    public static void main(String[] args)
    {
    	RepositoryRules x = new RepositoryRules();
        
        System.out.println(x.transformToken("ABCDEF"));
        System.out.println(x.transformToken("aBcDeF"));
        System.out.println(x.transformToken("abcdef"));
        
        System.out.println("---");
        System.out.println(x.transform("ABCDEF"));
        System.out.println(x.transform("aBcDeF"));
        System.out.println(x.transform("abcdef"));
        
        System.out.println("---");
        System.out.println(x.transform("ABC_DEF"));
        System.out.println(x.transform("aBc_DeF"));
        System.out.println(x.transform("abc_def"));
        
        System.out.println("---");
        System.out.println(x.getClassNameForVO("ABC_DEF"));
        System.out.println(x.getClassNameForVOList("ABC_DEF"));
        System.out.println(x.getClassNameForDAO("ABC_DEF"));
        System.out.println("---");
        System.out.println(x.getAttributeName("REF_ID", "", 0));
        
        System.out.println("---");
        System.out.println(x.about());
    }
}